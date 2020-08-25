package com.geek.common.shiro.session;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 自定义 sessionManager。
 */
public class CustomSessionManager extends DefaultWebSessionManager {

    /**
     * 指定 sessionId 的获取方式。
     * - 头信息中有 sessionId。
     * 请求头：Authorization: sessionId。
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
//        return super.getSessionId(request, response);
        // 获取请求头 Authorization 中的数据。
        String sessionId = WebUtils.toHttp(request).getHeader("Authorization");
        if (StringUtils.isEmpty(sessionId)) {
            // 如果没有携带，生成新的 sessionId。
            return super.getSessionId(request, response);
        } else {
            // 请求头信息：Bearer `sessionId`。
            sessionId = sessionId.replaceAll("Bearer ", "");

            // bearer
            // n. (尤指在礼仪中)持...者；传达消息者；送信人；持有者；正式持有人；持票人

            // 返回 sessionId。
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, "header");
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;
        }
    }
}
