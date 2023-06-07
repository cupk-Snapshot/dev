package com.cupk.snapshot.handler;

import com.cupk.snapshot.response.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler<T> {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R<T> exception(Exception e) {
        return R.error(e.getMessage());
    }
}
