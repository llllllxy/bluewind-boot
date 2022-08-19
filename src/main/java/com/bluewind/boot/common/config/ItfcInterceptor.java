package com.bluewind.boot.common.config;

import com.bluewind.boot.common.annotation.ItfcPermissions;
import com.bluewind.boot.common.consts.SystemConst;
import com.bluewind.boot.common.utils.DateTool;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.common.utils.RedisUtil;
import com.bluewind.boot.common.utils.encrypt.MD5Utils;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * @author liuxingyu01
 * @date 2020-03-22-11:23
 * @description: 三方itfc服务拦截器
 **/
@Component
public class ItfcInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(ItfcInterceptor.class);

    private final static String TIME_FORMAT = "yyyyMMddHHmmss";

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
            outWrite(response, "10106", "不是HandlerMethod！");
            return false;
        }
        // 判断请求类型，如果是OPTIONS请求，则直接返回
        String options = HttpMethod.OPTIONS.toString();
        if (options.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // 从请求头或者url里获取itfckey等信息
        String itfckey = request.getHeader("itfckey") == null ? request.getParameter("itfckey") : request.getHeader("itfckey");
        String reqtime = request.getHeader("reqtime") == null ? request.getParameter("reqtime") : request.getHeader("reqtime");
        // itfc_key + itfckey_secret + reqtime然后进行MD5加密(16进制的)
        String signature = request.getHeader("signature") == null ? request.getParameter("signature") : request.getHeader("signature");

        if (logger.isInfoEnabled()) {
            logger.info("ItfcInterceptor preHandle itfckey : {}, signature: {}, reqtime: {}", itfckey, signature, reqtime);
        }

        if (StringUtils.isBlank(itfckey)) {
            outWrite(response, "10106", "itfckey不允许为空，认证未通过!");
            return false;
        }

        if (StringUtils.isBlank(signature)) {
            outWrite(response, "10106", "signature不允许为空，认证未通过!");
            return false;
        }

        // 判断时间戳是否合格
        if (StringUtils.isBlank(reqtime)) {
            outWrite(response, "10106", "reqtime不允许为空，认证未通过!");
            return false;
        } else { // 判断时间戳是否是前后五分钟内
            String nowTime = DateTool.getCurrentTime(TIME_FORMAT);
            try {
                long timeDiff = timeDiffSeconds(nowTime, reqtime);
                // 超过五分钟的时间戳也不合格
                if (timeDiff > 300) {
                    outWrite(response, "10106", "reqtime时间戳只有在当前时间的前后5分钟内有效，认证未通过!");
                    return false;
                }
            } catch (Exception e) {
                logger.debug("ItfcInterceptor -- preHandle -- timeDiffSeconds - Exception = {e}", e);
                outWrite(response, "10106", "reqtime格式不正确，认证未通过!");
                return false;
            }
        }


        Map<String, Object> itfcConfigMap;
        // 先尝试从redis缓存里面取
        itfcConfigMap = (Map<String, Object>) redisUtil.get(SystemConst.SYSTEM_ITFC_KEY + ":" + itfckey);
        // redis里拿不到的话，就从数据库里查
        if (MapUtils.isEmpty(itfcConfigMap)) {
            String sql1 = "select itfc_key, itfc_key_secret, ifnull(valid_period, '20501231') valid_period from sys_itfc_key " +
                    "where status = '0' and del_flag = '0' and itfc_key = '" + itfckey + "'";
            List<Map<String, Object>> configList = MybatisSqlTool.selectAnySql(sql1);
            if (CollectionUtils.isNotEmpty(configList)) {
                itfcConfigMap = configList.get(0);
                // 缓存到redis中，减小数据库压力(3600秒)
                redisUtil.set(SystemConst.SYSTEM_ITFC_KEY + ":" + itfckey, itfcConfigMap, 3600);
            }
        }
        if (MapUtils.isEmpty(itfcConfigMap)) {
            outWrite(response, "10106", "itfckey不正确，认证未通过！");
            return false;
        }

        String validPeriod = (String) itfcConfigMap.get("valid_period");
        String itfcKeySecret = (String) itfcConfigMap.get("itfc_key_secret");

        // 判断key值有效期，如果超过有效期的话，则不允许访问
        String today = DateTool.getToday("yyyyMMdd");
        if (validPeriod.compareTo(today) < 0) { // 有效期小于今天，说明过期了，拉倒了，不让访问
            outWrite(response, "10106", "itfckey已过期，认证未通过！");
            return false;
        }

        // 检验signature是否正确，规则是 ak + sk 然后进行MD5加密(16进制的)
        String buildSignature = MD5Utils.MD5Encode(itfckey + itfcKeySecret + reqtime);
        if (!signature.equalsIgnoreCase(buildSignature)) {
            outWrite(response, "10106", "signature不正确，认证未通过!");
            return false;
        }

        // 获取permission
        Set<String> permissionSet = (Set<String>) redisUtil.get(SystemConst.SYSTEM_ITFC_KEY_PERMISSION + ":" + itfckey);

        if (CollectionUtils.isEmpty(permissionSet)) {
            String sql2 = "select srp.sign from sys_itfc_permission srp " +
                    "left join sys_itfc_key_permission srkp on srkp.itfc_permission = srp.permission_id " +
                    "where srkp.itfc_key = '" + itfckey + "'";
            List<Map<String, Object>> permissionList = MybatisSqlTool.selectAnySql(sql2);
            Set<String> set = new HashSet<>();
            if (CollectionUtils.isNotEmpty(permissionList)) {
                for (Map map : permissionList) {
                    String sign = Optional.ofNullable(map.get("sign")).orElse("").toString();
                    if (StringUtils.isNotBlank(sign)) {
                        set.add(sign);
                    }
                }
            }
            permissionSet = set;
            // 缓存到redis中，减小数据库压力(3600秒)
            redisUtil.set(SystemConst.SYSTEM_ITFC_KEY_PERMISSION + ":" + itfckey, permissionSet, 3600);
        }

        // 下面进入到接口校验环节
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        ItfcPermissions annotation = handlerMethod.getMethodAnnotation(ItfcPermissions.class);
        // 接口上没有注解，说明只要有key，就可以访问这个接口
        if (annotation == null) {
            logger.info("ItfcInterceptor --> preHandle --> 这个接口没有加注解，所以可以直接访问");
            return true;
        } else {
            String annoValue = annotation.value();
            logger.info("ItfcInterceptor --> preHandle --> annoValue = " + annoValue);

            if (CollectionUtils.isEmpty(permissionSet)) {
                logger.info("ItfcInterceptor --> preHandle --> permissionSet为空！");
                outWrite(response, "10106", "您的itfckey没有任何权限！");
                return false;
            } else {
                if (permissionSet.contains(annoValue)) {
                    logger.info("ItfcInterceptor --> preHandle --> 有权限访问，通过！");
                    return true;
                } else {
                    logger.info("ItfcInterceptor --> preHandle --> permissionSet为空！");
                    outWrite(response, "10106", "此接口您没有权限访问！");
                    return false;
                }
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

    /**
     * 输出 JSON
     * @param response
     * @param code
     * @param message
     * @throws IOException
     */
    public void outWrite(HttpServletResponse response, String code, String message) throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("code", code);
        data.put("message", message);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.write(JsonTool.toJsonString(data));
        out.close();
    }


    /**
     * 计量时间差 (time2 - time1)，返回秒数 比如 DateTool.timeDiffSeconds("2012-10-25
     * 02:49:15","2012-10-25 02:50:30") 返回值为 75
     *
     * @param previousTime 之前的时间
     * @param nextTime 之后的时间
     * @return
     */
    public long timeDiffSeconds(String previousTime, String nextTime) {
        DateTimeFormatter df1 = DateTimeFormatter.ofPattern(TIME_FORMAT);
        LocalDateTime previousDateTime = LocalDateTime.parse(previousTime, df1);

        DateTimeFormatter df2 = DateTimeFormatter.ofPattern(TIME_FORMAT);
        LocalDateTime nextDateTime = LocalDateTime.parse(nextTime, df2);

        Duration duration = Duration.between(previousDateTime, nextDateTime);
        long millis = duration.toMillis(); // 相差毫秒数(所有的)

        return Math.abs(Math.round(millis / 1000));
    }

}
