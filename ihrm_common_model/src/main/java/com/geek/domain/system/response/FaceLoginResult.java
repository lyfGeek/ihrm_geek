package com.geek.domain.system.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class FaceLoginResult implements Serializable {
    /**
     * 二维码使用状态。
     * -1 ~ 未使用。
     * 0 ~ 失败。
     * 1 ~ 登录成功。返回用户 userId 和 token。
     */
    private String state;
    /**
     * 登录信息。
     */
    private String token;
    /**
     * 用户 ID。
     */
    private String userId;

    public FaceLoginResult(String state, String token, String userId) {
        this.state = state;
        this.token = token;
        this.userId = userId;
    }

    public FaceLoginResult(String state) {
        this.state = state;
    }
}
