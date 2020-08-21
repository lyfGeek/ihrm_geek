package com.geek.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class ParseJwtTest {

    /**
     * 解析 jwt Token 字符串。
     *
     * @param args
     */
    public static void main(String[] args) {
//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4OCIsInN1YiI6IuWwj-eZvSIsImlhdCI6MTU5NzcyMDgxMX0.A4S0uFpGVnyxsBUq8bLIPSLZK1TlEHFlLKcMv4gGBC0";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4OCIsInN1YiI6IuWwj-eZvSIsImlhdCI6MTU5NzcyMTY5NSwiY29tcGFueUlkIjoiMTIzNDU2IiwiY29tcGFueU5hbWUiOiJseWZHZWVrIn0.XtTCR_pyUrgI947ZQndn08r_eAlKRl7-At13YVDhLN0";

        Jws<Claims> jws = Jwts.parser().setSigningKey("ihrm").parseClaimsJws(token);

        Claims body = Jwts.parser().setSigningKey("ihrm").parseClaimsJws(token).getBody();

        System.out.println(body.getId());
        System.out.println(body.getSubject());
        System.out.println(body.getIssuedAt());

        System.out.println(jws);

        // 解析自定义 claim 中内容。
        String companyId = body.get("companyId").toString();
        String companyName = body.get("companyName").toString();

        System.out.println("companyId = " + companyId);
        System.out.println("companyName = " + companyName);
    }
}
