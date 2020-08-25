package com.geek.common.handler;

import com.geek.common.entity.Result;
import com.geek.common.entity.ResultCode;
import com.geek.common.exception.CommonException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义公共异常处理器。
 */
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(HttpServletRequest request, HttpServletResponse response, Exception e) {

        if (e.getClass() == CommonException.class) {
            CommonException commonException = (CommonException) e;
            return new Result(commonException.getResultCode());
        } else {
            return new Result(ResultCode.SERVER_ERROR);
        }
    }

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public Result error(HttpServletRequest request, HttpServletResponse response, AuthorizationException e) {
        return new Result(ResultCode.UNAUTHENRISE);
    }
}
