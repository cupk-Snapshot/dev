package com.cupk.snapshot.controller;

import com.cupk.snapshot.exception.R;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局处理Oauth2抛出的异常
 * Created by macro on 2020/7/17.
 */
@ControllerAdvice(basePackages = "com.cupk.snapshot.controller")
public class Oauth2ExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public R handleOauth2(OAuth2Exception e) {
        e.printStackTrace();
        return R.error("oauth 错误");
    }
}