package com.geek.common.controller;

import com.geek.common.entity.Result;
import com.geek.common.entity.ResultCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    // 公共错误跳转。
    @RequestMapping("/autherror")
    public Result autherror(int code) {
        return code == 1 ? new Result(ResultCode.UNAUTHENTICATED) : new Result(ResultCode.UNAUTHENRISE);
    }
}
