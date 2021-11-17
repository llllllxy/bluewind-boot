package com.bluewind.boot.common.config.security;

import com.bluewind.boot.common.config.security.annotation.RequiresRoles;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.consts.SystemConst;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.common.utils.RedisUtil;
import com.bluewind.boot.common.utils.web.CookieUtils;
import com.bluewind.boot.common.utils.web.ServletUtils;
import com.bluewind.boot.common.config.security.enums.Logical;
import com.bluewind.boot.module.sys.sysuserinfo.entity.SysUserInfo;
import com.bluewind.boot.module.sys.sysuserrole.service.SysUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 * @author liuxingyu01
 * @date 2020-08-08-11:23
 * @description: 用户角色验证拦截器
 **/
@Component
public class RoleInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(RoleInterceptor.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysUserRoleService sysUserRoleService;

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
            logger.info("RoleInterceptor -- preHandle -- request.getMethod()=" + request.getMethod());
        }
        if (options.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        RequiresRoles annotation = ((HandlerMethod) handler).getMethodAnnotation(RequiresRoles.class);
        if (annotation == null) {
            annotation = ((HandlerMethod) handler).getBeanType().getAnnotation(RequiresRoles.class);
        }
        // 接口上没有注解，说明这个接口无角色控制
        if (annotation == null) {
            return true;
        } else {
            // 从请求中获取token
            String token = request.getHeader(SystemConst.SYSTEM_USER_TOKEN);
            if (StringUtils.isBlank(token)) {
                token = CookieUtils.getCookie(request, SystemConst.SYSTEM_USER_TOKEN);
            }
            if (StringUtils.isNotBlank(token) && token.startsWith(SystemConst.TOKEN_PREFIX)) {
                token = token.replace(SystemConst.TOKEN_PREFIX, "");
                token = JwtTokenUtil.parseJWT(token);
            }
            if (logger.isInfoEnabled()) {
                logger.info("RoleInterceptor -- preHandle -- token = {}", token);
            }
            SysUserInfo userInfo = (SysUserInfo) redisUtil.get(SystemConst.SYSTEM_USER_KEY + ":" + token);
            // 获取用户权限列表
            Set<String> roleSet = sysUserRoleService.listUserRoleByUserId(userInfo.getUserId());
            if (logger.isInfoEnabled()) {
                logger.info("RoleInterceptor -- preHandle -- roleSet = {}", roleSet);
            }

            String[] roles = annotation.value();
            Logical logical = annotation.logical();

            if (logical == Logical.OR) {
                // 如果有任何一个角色，返回true，否则返回false（拥有其一）
                for (String ro : roles) {
                    if (roleSet.contains(ro)) {
                        return true;
                    }
                }
                responseError(request, response);
                return false;
            } else if (logical == Logical.AND) {
                // 只要有一个角色不是true的，就返回false（同时拥有）
                for (String ro : roles) {
                    if (!roleSet.contains(ro)) {
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
