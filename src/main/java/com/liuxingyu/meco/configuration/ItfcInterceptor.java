package com.liuxingyu.meco.configuration;

import com.liuxingyu.meco.common.annotation.ItfcPermissions;
import com.liuxingyu.meco.common.utils.DateTool;
import com.liuxingyu.meco.common.utils.JsonTool;
import com.liuxingyu.meco.common.utils.RedisUtil;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                ItfcPermissions annotation = handlerMethod.getMethodAnnotation(ItfcPermissions.class);
                if (annotation == null) { // 接口上没有注解，说明只要有key，就可以访问这个接口
                    boolean isExists = redisUtil.hasKey("itfcKey:" + authorization);
                    if (isExists) {
                        logger.info("RestInterceptor --> preHandle --> 这个接口没有加注解，所以有key就可以访问");
                        return true;
                    } else {
                        logger.info("RestInterceptor --> preHandle --> 您的Authorization值在redis中不存在");
                        outWrite(response, "10106", "您的Authorization值不正确，请联系管理员！");
                        return false;
                    }
                } else {
                    boolean isExists = redisUtil.hasKey("itfcKey:" + authorization);
                    if (isExists) {
                        // 获取key有效期
                        String validPeriod = (String) redisUtil.get("itfcPeriod:" + authorization);
                        // 判断key值有效期，如果超过有效期的话，则不允许访问
                        String today = DateTool.getToday("yyyyMMdd");
                        // 有效期小于今天，说明过期了，拉倒了，不让访问
                        if (validPeriod.compareTo(today) < 0) {
                            logger.info("RestInterceptor --> preHandle --> validPeriod < today！");
                            outWrite(response, "10106", "您的Authorization已过期！");
                            return false;
                        } else {
                            String permission = annotation.value();
                            logger.info("RestInterceptor --> preHandle --> permission = " + permission);
                            Set<String> permissionSet = (Set<String>) redisUtil.get("itfcKey:" + authorization);
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
                    } else {
                        logger.info("RestInterceptor --> preHandle --> 您的Authorization值在redis中不存在");
                        outWrite(response, "10106", "您的Authorization值不正确，请联系管理员！");
                        return false;
                    }
                }
            } else {
                logger.info("RestInterceptor --> preHandle --> 不是HandlerMethod！");
                outWrite(response, "10106", "不是HandlerMethod！");
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
