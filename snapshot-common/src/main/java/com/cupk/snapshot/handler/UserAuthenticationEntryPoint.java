package com.cupk.snapshot.handler;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.cupk.snapshot.constant.HttpStatus;
import com.cupk.snapshot.domain.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(UserAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.error("异常类型：{}，异常信息：{}", e.getClass(), e.getMessage());
        // InsufficientAuthenticationException：access_token过期
        response.setStatus(HttpStatus.HTTP_OK);
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        R res = R.error(HttpStatus.HTTP_UNAUTHORIZED, "access_token错误或已过期");
        String str = JSONUtil.toJsonStr(res);
        response.getWriter().write(str);
    }
}