package com.geek.common.interceptor;

import com.geek.common.entity.ResultCode;
import com.geek.common.exception.CommonException;
import com.geek.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器。
 */
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    // 进入到控制器方法之前。
    // true 可以继续执行控制器的方法。
    // false 拦截。

    /**
     * 简化获取 token 数据。
     * - 通过 request 获取请求 token 信息。
     * - 从 token 中解析获取 claims。
     * - 将 claims 绑定到 requests 域中。
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        return super.preHandle(request, response, handler);

        // 从请求头信息中获取 token 数据。
        // 获取请求头信息。名称 Authorization。
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer")) {
            // 获取 token 数据。
            String token = authorization.replace("Bearer ", "");
            // 解析 token。
            // 获取 claims。
            Claims claims = jwtUtils.parseJwt(token);
            if (claims != null) {

                // 通过 Claims 获取到当前用户可访问的 api 权限。
                String apis = (String) claims.get("apis");// api-user-delete, api-user-update...
                // 通过 Handler
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                // 获取接口上的 RequestMapping 注解。
                RequestMapping annotation = handlerMethod.getMethodAnnotation(RequestMapping.class);
                // 获取当前请求接口中的 name 属性。
                String name = annotation.name();
                // 判断当前用户是否具有相应的请求权限。
                if (apis.contains(name)) {
                    request.setAttribute("user_claims", claims);
                    return true;
                } else {
                    throw new CommonException(ResultCode.UNAUTHENRISE);
                }
            }
        }

        throw new CommonException(ResultCode.UNAUTHENTICATED);
    }

    // 进入到控制器方法之后。
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        super.postHandle(request, response, handler, modelAndView);
    }

    // 相应结束之前。
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        super.afterCompletion(request, response, handler, ex);
    }
}
