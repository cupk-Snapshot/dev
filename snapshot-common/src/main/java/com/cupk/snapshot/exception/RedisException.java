package com.cupk.snapshot.exception;

/**
 * Created by Guo Tianyou on 2023/6/15.
 */
public class RedisException extends RuntimeException{

    public RedisException(String message) {
        super(message);
    }
}
