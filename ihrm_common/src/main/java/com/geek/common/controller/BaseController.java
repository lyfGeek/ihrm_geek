package com.geek.common.controller;

import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String companyId;
    protected String companyName;

    protected Claims claims;

    // Controller 之前运行。
    @ModelAttribute
    public void setResAndReq(HttpServletRequest request, HttpServletResponse response) {
        this.response = response;
        this.request = request;

        Object object = request.getAttribute("user_claims");
        if (object != null) {
            this.claims = (Claims) object;
            this.companyId = (String) claims.get("companyId");
            this.companyName = (String) claims.get("companyName");
        }

        // 以后解决 companyId。
//        this.companyId = "1";
//        this.companyName = "lyfGeek";


    }
}
