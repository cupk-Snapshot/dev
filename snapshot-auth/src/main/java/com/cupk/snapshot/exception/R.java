package com.cupk.snapshot.exception;

import java.io.Serializable;

/**
 * 统一响应格式
 *
 * @param <T>
 */
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private T data;

    private R() {}

    public static <T> R<T> success() {
        return result(HttpStatus.SUCCESS.getCode(), HttpStatus.SUCCESS.getMsg(), null);
    }

    public static <T> R<T> success(T data) {
        return result(HttpStatus.SUCCESS.getCode(), HttpStatus.SUCCESS.getMsg(), data);
    }

    public static <T> R<T> success(String msg, T data) {
        return result(HttpStatus.SUCCESS.getCode(), msg, data);
    }

    public static <T> R<T> error() {
        return result(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), HttpStatus.INTERNAL_SERVER_ERROR.getMsg(), null);
    }

    public static <T> R<T> error(String msg) {
        return result(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), msg, null);
    }

    public static <T> R<T> error(T data) {
        return result(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), HttpStatus.INTERNAL_SERVER_ERROR.getMsg(), data);
    }

    public static <T> R<T> error(String msg, T data) {
        return result(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), msg, data);
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
