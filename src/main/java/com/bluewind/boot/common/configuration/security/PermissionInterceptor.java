package com.bluewind.boot.common.configuration.security;

import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.consts.SystemConst;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.common.utils.RedisUtil;
import com.bluewind.boot.common.utils.web.CookieUtils;
import com.bluewind.boot.common.utils.web.ServletUtils;
import com.bluewind.boot.common.configuration.security.annotation.RequiresPermissions;
import com.bluewind.boot.common.configuration.security.enums.Logical;
import com.bluewind.boot.module.sys.sysrolepermission.service.SysRolePermissionService;
import com.bluewind.boot.module.sys.sysuserinfo.entity.SysUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 * @author liuxingyu01
 * @date 2020-08-08-11:23
 * @description: 用户权限验证拦截器
 **/
@Component
public class PermissionInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);

    @Autowired
    private RedisUtil redisUtil;

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
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 判断请求类型，如果是OPTIONS，直接返回
        String options = HttpMethod.OPTIONS.toString();
        if (logger.isInfoEnabled()) {
            logger.info("PermissionInterceptor -- preHandle -- request.getMethod()=" + request.getMethod());
        }
        if (options.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        // 获取RequiresPermissions注解
        RequiresPermissions annotation = ((HandlerMethod) handler).getMethodAnnotation(RequiresPermissions.class);
        if (annotation == null) {
            annotation = ((HandlerMethod) handler).getBeanType().getAnnotation(RequiresPermissions.class);
        }
        // 接口上没有注解，说明这个接口无权限控制，直接通过
        if (annotation == null) {
            return true;
        } else {
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
                logger.info("PermissionInterceptor -- preHandle -- token = {}", token);
            }
            SysUserInfo userInfo = (SysUserInfo) redisUtil.get(SystemConst.SYSTEM_USER_KEY + ":" + token);
            // 获取用户权限列表
            Set<String> permissionSet = sysRolePermissionService.listRolePermissionByUserId(userInfo.getId());
            if (logger.isInfoEnabled()) {
                logger.info("PermissionInterceptor -- preHandle -- permissionSet = {}", permissionSet);
            }

            String[] permissions = annotation.value();
            Logical logical = annotation.logical();
            if (logical == Logical.OR) {
                // 如果有任何一个权限，返回true，否则返回false（拥有其一）
                for (String perm : permissions) {
                    if (permissionSet.contains(perm)) {
                        return true;
                    }
                }
                responseError(request, response);
                return false;
            } else if (logical == Logical.AND) {
                // 只要有一个权限不是true的，就返回false（同时拥有）
                for (String perm : permissions) {
                    if (!permissionSet.contains(perm)) {
                        responseError(request, response);
                        return false;
                    }
                }
                return true;
            } else {
                responseError(request, response);
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
        // System.out.println("执行了postHandle方法");
    }


    /**
     * 视图渲染之后的操作
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
        // System.out.println("执行到了afterCompletion方法");
    }


    /**
     * 无权限时的返回
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void responseError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 如果是ajax请求，怎返回json串
        if (ServletUtils.isAjaxRequest(request)) {
            outWrite(response);
        } else {
            // 拦截后跳转到无权限403页面
            response.sendRedirect(contextPath + "/error/error403");
        }
    }


    /**
     * outWrite
     *
     * @param response
     * @throws IOException
     */
    private void outWrite(HttpServletResponse response) throws IOException {
        BaseResult baseResult = new BaseResult(BaseResult.CODE_NO_PERMISSION, "接口无权限，请联系系统管理员", null);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(JsonTool.objectToJsonString(baseResult));
        out.close();
    }
}
