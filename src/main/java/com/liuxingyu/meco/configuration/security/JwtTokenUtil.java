package com.liuxingyu.meco.configuration.security;

import com.liuxingyu.meco.common.consts.SystemConst;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author liuxingyu01
 * @date 2021-08-21-16:43
 * Payload载荷数据介绍
 * iss(issuer): 表示签发人
 * sub(subject): 主题
 * aud(audience): 受众
 * exp(expiration time): 表示token过期时间
 * nbf(Not Before): token的生效时间
 * iat(Issued At): jwt的签发时间
 * jti(JWT ID): 编号，jwt的唯一身份标识,主要用来作为一次性token,从而回避重放攻击
 **/
public class JwtTokenUtil {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final String secret = "un#!qb4sDI*L0pd%ka$@&ZgKSOtrw8@*";

    private static final String expiresSecond = "";


    /**
     * 构建jwt
     * @param userKey 用户信息redis-key
     * @return
     */
    public static String createJWT(String userKey) {
        try {
            //添加构成JWT的参数
            JwtBuilder builder = Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setHeaderParam("alg", "HS256")
                    // 可以将基本不重要的对象信息放到claims
                    .claim(SystemConst.SYSTEM_USER_KEY, userKey) // 将token放进去jwt
                    .setSubject("meco")           // 代表这个JWT的主体，即它的所有人
                    .signWith(SignatureAlgorithm.HS256, secret); // 使用HS256加密算法
            //生成JWT
            return builder.compact();
        } catch (Exception e) {
            log.error("JwtTokenUtil - createJWT - 获取签名失败 e=", e);
            return "";
        }
    }


    /**
     * 解析jwt
     * @param jsonWebToken 前端传来的token
     * @return Claims对象
     */
    public static String parseJWT(String jsonWebToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jsonWebToken).getBody();
            return (String) claims.get(SystemConst.SYSTEM_USER_KEY);
        } catch (Exception e) {
            log.error("JwtTokenUtil-- parseJWT -- token解析异常 - e=", e);
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println(createJWT("ifwjeifwefwkefwkefkwekf"));
    }

}
