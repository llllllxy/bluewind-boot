package com.bluewind.boot.common.config.security;

import com.bluewind.boot.common.consts.SystemConst;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.common.utils.RedisUtil;
import com.bluewind.boot.common.utils.web.CookieUtils;
import com.bluewind.boot.common.utils.web.ServletUtils;
import com.bluewind.boot.module.sys.sysrolepermission.service.SysRolePermissionService;
import com.bluewind.boot.module.sys.sysuserinfo.entity.SysUserInfo;
import com.bluewind.boot.module.sys.sysuserrole.service.SysUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
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
 * @description: 用户会话验证拦截器
 **/
@Component
public class AuthenticeInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(AuthenticeInterceptor.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * 进入controller层之前拦截请求
     * 返回值：表示是否将当前的请求拦截下来  false：拦截请求，请求别终止。true：请求不被拦截，继续执行
     * Object obj:表示被拦的请求的目标对象（controller中方法）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 判断请求类型，如果是OPTIONS，直接返回
        String options = HttpMethod.OPTIONS.toString();
        if (logger.isInfoEnabled()) {
            logger.info("AuthenticeInterceptor -- preHandle -- httpMethod=" + options);
            logger.info("AuthenticeInterceptor -- preHandle -- request.getMethod()=" + request.getMethod());
        }
        if (options.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        // 从请求中获取token，先从Header里取，取不到的话再从cookie里取（适配前后端分离的模式）
        String token = request.getHeader(SystemConst.SYSTEM_USER_TOKEN);
        if (StringUtils.isBlank(token)) {
            token = CookieUtils.getCookie(request, SystemConst.SYSTEM_USER_TOKEN);
        }
        if (logger.isInfoEnabled()) {
            logger.info("AuthenticeInterceptor -- preHandle -- token = {}", token);
        }
        if (StringUtils.isBlank(token)) {
            // 拦截后跳转至登录页
            response.sendRedirect(contextPath + "/admin/login");
            return false;
        } else {
            if (token.startsWith(SystemConst.TOKEN_PREFIX)) {
                token = token.replace(SystemConst.TOKEN_PREFIX, "");
            } else { // token不是以SystemConst.TOKEN_PREFIX开头的，返回false
                // 拦截后跳转至登录页
                response.sendRedirect(contextPath + "/admin/login");
                return false;
            }
            token = JwtTokenUtil.parseJWT(token);
            if (StringUtils.isBlank(token)) { // JWT验证未通过，返回false
                // 拦截后跳转至登录页
                response.sendRedirect(contextPath + "/admin/login");
                return false;
            }

            // 判断这个token在redis里面存在不，存在的话，说明有效
            boolean isExists = redisUtil.hasKey(SystemConst.SYSTEM_USER_KEY + ":" + token);
            if (isExists) {
                if (logger.isInfoEnabled()) {
                    logger.info("AuthenticeInterceptor --> preHandle --> " + token + "会话验证通过！");
                }
                // 刷新会话缓存时间
                redisUtil.expire(SystemConst.SYSTEM_USER_KEY + ":" + token, 1800);
                return true;
            } else {
                // 拦截后跳转至登录页
                response.sendRedirect(contextPath + "/admin/login");
                return false;
            }
        }
    }


    /**
     * 处理请求完成后，视图渲染之前的处理操作(Controller方法调用之后)
     * 此处可以通过ModelAndView(模型和视图对象)对模型数据进行处理或对视图进行处理，modelAndView也可能为null
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (logger.isInfoEnabled()) {
            logger.info("AuthenticeInterceptor -- postHandle -- start");
        }
        // 从请求中获取token，先从Header里取，取不到的话再从cookie里取（适配前后端分离的模式）
        String token = request.getHeader(SystemConst.SYSTEM_USER_TOKEN);
        if (StringUtils.isBlank(token)) {
            token = CookieUtils.getCookie(request, SystemConst.SYSTEM_USER_TOKEN);
        }
        if (StringUtils.isNotBlank(token) && token.startsWith(SystemConst.TOKEN_PREFIX)) {
            token = token.replace(SystemConst.TOKEN_PREFIX, "");
            token = JwtTokenUtil.parseJWT(token);
        }
        if (logger.isInfoEnabled()) {
            logger.info("AuthenticeInterceptor -- postHandle -- token = {}", token);
        }
        SysUserInfo userInfo = (SysUserInfo) redisUtil.get(SystemConst.SYSTEM_USER_KEY + ":" + token);

        try {
            // 获取用户角色信息
            Set<String> roleSet = sysUserRoleService.listUserRoleByUserId(userInfo.getUserId());
            // 获取用户权限列表
            Set<String> permissionSet = sysRolePermissionService.listRolePermissionByUserId(userInfo.getUserId());

            if (!ServletUtils.isAjaxRequest(request) && modelAndView != null) {
                modelAndView.addObject("roleSet", roleSet);
                modelAndView.addObject("permissionSet", permissionSet);
            }
        } catch (Exception e) {
            logger.error("AuthenticeInterceptor -- postHandle -- Exception = {e}", e);
        }
    }

    /**
     * 在整个请求结束之后被调用，也就是视图渲染完毕之后的操作（主要是用于进行性能监控、资源清理工作）
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
