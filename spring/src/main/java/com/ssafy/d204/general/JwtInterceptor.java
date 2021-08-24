package com.ssafy.d204.general;

import com.ssafy.d204.api.service.JwtService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    public static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler)
        throws Exception {
        // CORS 문제가 발생하지 않도록 OPTIONS는 따로 걸러내지 않음
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        } else {
            String token = request.getHeader("auth-token");
            if (token != null && token.length() > 0) {
                jwtService.jwtValidation(token);
                return true;
            } else {
                throw new RuntimeException("No tokens found in HTTP header.");
            }
        }
    }
}
