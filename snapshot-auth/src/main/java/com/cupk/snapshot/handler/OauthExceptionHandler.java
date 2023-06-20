package com.cupk.snapshot.handler;

import com.cupk.snapshot.constant.HttpStatus;
import com.cupk.snapshot.domain.R;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * Created by Guo Tianyou on 2023/6/6.
 */
@ControllerAdvice(basePackages = "com.cupk.snapshot.controller")
public class OauthExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(OauthExceptionHandler.class);

    /**
     * Oauth2认证授权异常
     * @param e OAuth2Exception
     */
    @ResponseBody
    @ExceptionHandler(value = OAuth2Exception.class)
    public R handle(OAuth2Exception e) {
        log.error("【snapshot-auth】  [ Exception message : {} ]", e.getMessage());
        int code = e.getHttpErrorCode();

        if (e instanceof InvalidGrantException) {
            return R.error(code, "用户名或密码错误");
        } else if (e instanceof InvalidTokenException) {
            return R.error(code, "Token错误或已过期");
        } else if (e instanceof UnsupportedGrantTypeException) {
            return R.error(code, "不支持的授权模式");
        }
        return R.error("OAuth2异常");
    }

    /**
     * 腾讯云短信服务异常
     * @param e TencentCloudSDKException
     */
    @ResponseBody
    @ExceptionHandler(value = TencentCloudSDKException.class)
    public R handle(TencentCloudSDKException e) {
        log.error("【snapshot-auth】  [ Exception message : {} ]", e.getMessage());
        return R.error("短信发送失败");
    }


    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public R handle(Exception e) {
        log.error("【snapshot-auth】  [ Exception message : {} ]", e.getMessage());
        return R.error(e.getMessage());
    }

}