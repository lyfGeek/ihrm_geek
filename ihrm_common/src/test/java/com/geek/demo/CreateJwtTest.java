package com.geek.demo;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class CreateJwtTest {

    /**
     * 通过 jjwt 创建 token。
     *
     * @param args
     */
    public static void main(String[] args) {
        JwtBuilder jwtBuilder = Jwts.builder().setId("88")
                .setSubject("小白")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "ihrm")

                // 自定义存放数据。
                .claim("companyId", "123456")
                .claim("companyName", "lyfGeek");
        String token = jwtBuilder.compact();
        System.out.println("token = " + token);
    }
}
