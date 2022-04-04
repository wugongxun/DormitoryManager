package com.wgx.dormitorymanager2.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * author:wgx
 * version:1.0
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截的请求路径是" + request.getRequestURI());
        HttpSession session = request.getSession();
        Object student = session.getAttribute("student");
        Object administrator = session.getAttribute("administrator");
        if (student != null || administrator != null) {
            return true;
        }else {
            response.sendRedirect("/login.html");
            return false;
        }
    }
}
