package com.cupk.snapshot.domain;

import com.cupk.snapshot.constant.HttpStatus;

import java.io.Serializable;

/**
 * 统一响应格式
 * Created by Guo Tianyou on 2023/6/5.
 */
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private T data;

    private R() {
    }

    public static <T> R<T> success() {
        return result(HttpStatus.HTTP_OK, "success", null);
    }

    public static <T> R<T> success(String msg) {
        return result(HttpStatus.HTTP_OK, msg, null);
    }

    public static <T> R<T> success(T data) {
        return result(HttpStatus.HTTP_OK, "success", data);
    }

    public static <T> R<T> success(String msg, T data) {
        return result(HttpStatus.HTTP_OK, msg, data);
    }

    public static <T> R<T> error(String msg) {
        return result(HttpStatus.HTTP_INTERNAL_ERROR, msg, null);
    }

    public static <T> R<T> error(int code, String msg) {
        return result(code, msg, null);
    }

    private static <T> R<T> result(int code, String msg, T data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
