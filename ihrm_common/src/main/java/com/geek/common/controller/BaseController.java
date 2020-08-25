package com.geek.common.controller;

import com.geek.domain.system.response.ProfileResult;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String companyId;
    protected String companyName;

    protected Claims claims;

    // 使用 jwt 方式获取。
    // Controller 之前运行。
//    @ModelAttribute
//    public void setResAndReq(HttpServletRequest request, HttpServletResponse response) {
//        this.response = response;
//        this.request = request;
//
//        Object object = request.getAttribute("user_claims");
//        if (object != null) {
//            this.claims = (Claims) object;
//            this.companyId = (String) claims.get("companyId");
//            this.companyName = (String) claims.get("companyName");
//        }
//
//        // 以后解决 companyId。
////        this.companyId = "1";
////        this.companyName = "lyfGeek";
//    }


    // 使用 shiro 方式获取。
    // Controller 之前运行。
    @ModelAttribute
    public void setResAndReq(HttpServletRequest request, HttpServletResponse response) {
        this.response = response;
        this.request = request;

        // 获取 session 中的安全数据。
        Subject subject = SecurityUtils.getSubject();
        // 获取所有安全数据集合。
        PrincipalCollection principals = subject.getPrincipals();

        if (principals != null && !principals.isEmpty()) {
            // 获取安全数据。
            ProfileResult profileResult = (ProfileResult) principals.getPrimaryPrincipal();
            this.companyId = profileResult.getCompanyId();
            this.companyName = profileResult.getCompany();
        }
    }
}
