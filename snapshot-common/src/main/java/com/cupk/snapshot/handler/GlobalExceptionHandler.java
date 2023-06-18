package com.cupk.snapshot.handler;

import com.cupk.snapshot.constant.HttpStatus;
import com.cupk.snapshot.domain.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * Created by Guo Tianyou on 2023/6/6.
 */
@ControllerAdvice(basePackages = "com.cupk.snapshot.controller")
public class GlobalExceptionHandler<T> {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R<T> exception(Exception e) {
        return R.error(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public R exception(AccessDeniedException e) {
        return R.error(HttpStatus.HTTP_UNAUTHORIZED, "您没有权限访问");
    }

}
