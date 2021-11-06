package com.bluewind.boot.common.config;

import com.bluewind.boot.common.annotation.ItfcPermissions;
import com.bluewind.boot.common.consts.SystemConst;
import com.bluewind.boot.common.utils.DateTool;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.common.utils.RedisUtil;
import com.bluewind.boot.common.utils.mybatis.MybatisSqlTool;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author liuxingyu01
 * @date 2020-03-22-11:23
 * @description: itfc服务拦截器
 **/
@Component
public class ItfcInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(ItfcInterceptor.class);

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 进入controller层之前拦截请求
     * 返回值：表示是否将当前的请求拦截下来  false：拦截请求，请求别终止。true：请求不被拦截，继续执行
     * Object obj:表示被拦的请求的目标对象（controller中方法）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (!(handler instanceof HandlerMethod)) {
            logger.info("RestInterceptor --> preHandle --> 不是HandlerMethod！");
            outWrite(response, "10106", "不是HandlerMethod！");
            return false;
        }

        // 判断请求类型，如果是OPTIONS，直接返回
        String options = HttpMethod.OPTIONS.toString();
        logger.info("RestInterceptor -- preHandle -- httpMethod=" + options);
        logger.info("RestInterceptor -- preHandle -- request.getMethod()=" + request.getMethod());
        if (options.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // 获取请求头信息authorization信息
        String authorization = request.getHeader("Authorization");
        if (logger.isInfoEnabled()) {
            logger.info("RestInterceptor --> preHandle --> authorization= " + authorization);
        }
        if (StringUtils.isBlank(authorization)) {
            logger.info("RestInterceptor --> preHandle --> Authorization信息为空！");
            outWrite(response, "10106", "Authorization信息为空，请设置Authorization后再访问！");
            return false;
        } else {
            Map<String, Object> itfcConfigMap;
            // 先尝试从redis里面取
            itfcConfigMap = (Map<String, Object>) redisUtil.get(SystemConst.SYSTEM_ITFC_KEY + ":" + authorization);
            if (itfcConfigMap == null || itfcConfigMap.isEmpty()) {
                // redis里拿不到的话，就从数据库里查
                String sql1 = "select itfc_key, valid_period from sys_itfc_key " +
                        "where status = 0 and del_flag = 0 and itfc_key = '" + authorization + "'";
                List<Map> keyList = MybatisSqlTool.selectAnySql(sql1);
                if (CollectionUtils.isNotEmpty(keyList)) {
                    String valid_period = (String) keyList.get(0).get("valid_period");
                    // 如果过期时间为空的话，则默认为不做限制，所以设置到2050年
                    if (StringUtils.isBlank(valid_period)) {
                        valid_period = "20500625";
                    }

                    String sql2 = "select srp.sign from sys_itfc_permission srp " +
                            "left join sys_itfc_key_permission srkp on srkp.itfc_permission = srp.permission_id " +
                            "where srkp.itfc_key = '" + authorization + "'";
                    List<Map> permissionList = MybatisSqlTool.selectAnySql(sql2);
                    Set<String> set = new HashSet<>();
                    if (CollectionUtils.isNotEmpty(permissionList)) {
                        for (Map map : permissionList) {
                            String sign = Optional.ofNullable(map.get("sign")).orElse("").toString();
                            if (StringUtils.isNotBlank(sign)) {
                                set.add(sign);
                            }
                        }
                    }

                    itfcConfigMap = new HashMap<>();
                    itfcConfigMap.put("valid_period", valid_period);
                    itfcConfigMap.put("permission_set", set);
                    // 缓存到redis中，减小数据库压力(3600秒)
                    redisUtil.set(SystemConst.SYSTEM_ITFC_KEY + ":" + authorization, itfcConfigMap, 3600);
                }
            }

            // itfcConfigMap不为空时，说明这个itfc-key存在，进入下一步处理
            if (MapUtils.isNotEmpty(itfcConfigMap)) {
                String validPeriod = (String) itfcConfigMap.get("valid_period");
                // 判断key值有效期，如果超过有效期的话，则不允许访问
                String today = DateTool.getToday("yyyyMMdd");
                // 有效期小于今天，说明过期了，拉倒了，不让访问
                if (validPeriod.compareTo(today) < 0) {
                    logger.info("RestInterceptor --> preHandle --> validPeriod < today！");
                    outWrite(response, "10106", "您的Authorization已过期！");
                    return false;
                } else {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    ItfcPermissions annotation = handlerMethod.getMethodAnnotation(ItfcPermissions.class);
                    // 接口上没有注解，说明只要有key，就可以访问这个接口
                    if (annotation == null) {
                        logger.info("RestInterceptor --> preHandle --> 这个接口没有加注解，所以有key就可以访问");
                        return true;
                    } else {
                        String permission = annotation.value();
                        logger.info("RestInterceptor --> preHandle --> permission = " + permission);
                        Set<String> permissionSet = (Set<String>) itfcConfigMap.get("permission_set");

                        if (permissionSet == null) {
                            logger.info("RestInterceptor --> preHandle --> permissionSet为空！");
                            outWrite(response, "10106", "您的Authorization没有任何权限！");
                            return false;
                        } else {
                            if (permissionSet.contains(permission)) {
                                logger.info("RestInterceptor --> preHandle --> 有权限访问，通过！");
                                return true;
                            } else {
                                logger.info("RestInterceptor --> preHandle --> permissionSet为空！");
                                outWrite(response, "10106", "此接口您没有权限访问！");
                                return false;
                            }
                        }
                    }
                }
            } else {
                logger.info("RestInterceptor --> preHandle --> 您的Authorization值在redis中不存在");
                outWrite(response, "10106", "您的Authorization值不正确，请联系管理员！");
                return false;
            }
        }

    }


    /**
     * 处理请求完成后视图渲染之前的处理操作
     * 通过ModelAndView参数改变显示的视图，或发往视图的方法
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        //System.out.println("执行了postHandle方法");
    }

    /**
     * 视图渲染之后的操作
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
        //System.out.println("执行到了afterCompletion方法");
    }

    public void outWrite(HttpServletResponse response, String code, String message) throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("code", code);
        data.put("message", message);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.write(JsonTool.mapToJsonString(data));
        out.close();
    }

}
