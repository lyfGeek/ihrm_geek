package com.geek.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.Map;

@Data
@ConfigurationProperties("jwt.config")
public class JwtUtils {

    // 签名私钥。
    private String key;
    // 签名失效时间。
    private Long ttl;

    /**
     * 设置认证 token。
     *
     * @param id
     * @param name
     * @param map
     * @return
     */
    public String createJwt(String id, String name, Map<String, Object> map) {
        // 设置失效时间。
        long now = System.currentTimeMillis();// 当前时间毫秒。
        long exp = now + ttl;
        // 创建 JwtBuilder。
        JwtBuilder jwtBuilder = Jwts.builder().setId(id)
                .setSubject(name)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key);
        // 自定义存放数据。
        // 根据 Map 设置 claim。
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            jwtBuilder.claim(entry.getKey(), entry.getValue());
        }
//        jwtBuilder.setClaims(map);// 会覆盖 jwtBuilder。
        // 失效时间。
        jwtBuilder.setExpiration(new Date(exp));
        // 创建 Token。
        String token = jwtBuilder.compact();
        return token;
    }

    /**
     * 解析 token。
     *
     * @param token
     * @return
     */
    public Claims parseJwt(String token) {
        Claims body = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return body;
    }

}
