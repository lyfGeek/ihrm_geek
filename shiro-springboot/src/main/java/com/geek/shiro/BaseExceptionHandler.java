package com.geek.shiro;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义的公共异常处理器。
 * - 声明异常处理器。
 * - 对异常统一处理。
 */
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public String error(HttpServletRequest request, HttpServletResponse response, AuthorizationException e) {
        return "exception 未授权。";
    }
}
