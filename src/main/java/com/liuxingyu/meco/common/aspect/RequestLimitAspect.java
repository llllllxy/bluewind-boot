package com.liuxingyu.meco.common.aspect;

import com.liuxingyu.meco.common.annotation.RequestLimit;
import com.liuxingyu.meco.common.utils.IPUtils;
import com.liuxingyu.meco.common.base.BaseResult;
import com.liuxingyu.meco.common.utils.RedisUtil;
import com.liuxingyu.meco.common.utils.web.CookieUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author liuxingyu01
 * @date 2021-03-23-10:41
 * @description 请求频率限制（根据请求的IP进行限制）
 **/
@Aspect
@Component
public class RequestLimitAspect {
    final static Logger logger = LoggerFactory.getLogger(RequestLimitAspect.class);

    private static final String REQ_LIMIT = "reqLimit";

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 定义拦截规则：拦截com.liuxingyu.industry包下面的所有类中，有@RequestLimit Annotation注解的方法。
     */
    @Around("execution(* com.liuxingyu.meco..*(..)) && @annotation(com.liuxingyu.meco.common.annotation.RequestLimit)")
    public Object method(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod(); // 获取被拦截的方法
        RequestLimit limt = method.getAnnotation(RequestLimit.class);
        // No request for limt,continue processing request
        if (limt == null) {
            return pjp.proceed();
        }

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        int time = limt.time();
        int count = limt.count();
        int waits = limt.waits();

        // 唯一值（取不到session_token则使用请求ip）
        String token = CookieUtils.getCookie(request, "session_token");
        if (token == null || "".equals(token)) {
            token = IPUtils.getIpAddress(request);
        }

        String url = request.getRequestURI();

        // 组织存入redis的key
        String key = requestLimitKey(url, token);

        int nowCount = redisUtil.get(key) == null ? 0 : Integer.parseInt((String) redisUtil.get(key));

        if (nowCount == 0) {
            nowCount++;
            redisUtil.set(key, String.valueOf(nowCount), time);
            return pjp.proceed();
        } else {
            nowCount++;
            redisUtil.set(key, String.valueOf(nowCount), redisUtil.getExpire(key));
            if (nowCount >= count) {
                logger.warn("用户[" + token + "]访问地址[" + url + "]访问次数["
                        + nowCount + "]超过了限定的次数[" + count + "]限定时间[" + waits
                        + "秒]");
                // 如果超出单位时间内访问次数限制，则惩罚waits时间内禁止访问
                redisUtil.expire(key, waits, TimeUnit.SECONDS);
                return returnLimit(request);
            }
        }

        return pjp.proceed();
    }


    /**
     * requestLimitKey: url_ip
     *
     * @param url 访问url
     * @param token  访问的ip地址
     * @return
     */
    private static String requestLimitKey(String url, String token) {
        url = url.replace("/", ":");
        StringBuffer sb = new StringBuffer();
        sb.append(REQ_LIMIT);
        sb.append(url);
        sb.append(":");
        sb.append(token);
        return sb.toString();
    }


    /**
     * 返回拒绝信息
     *
     * @param request
     * @return
     * @throws IOException
     */
    private String returnLimit(HttpServletRequest request) throws IOException {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getResponse();
        PrintWriter out = response.getWriter();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        out.println("{\"code\":" + BaseResult.CODE_LIMIT + ",\"msg\":\"您的访问速度过快，请稍后再试!\"}");
        out.flush();
        out.close();
        return null;
    }
}
