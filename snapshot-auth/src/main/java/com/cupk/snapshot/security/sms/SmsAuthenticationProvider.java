package com.cupk.snapshot.security.sms;

import com.cupk.snapshot.exception.RedisException;
import com.cupk.snapshot.exception.SmsCodeException;
import com.cupk.snapshot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * SmsAuthenticationProvider
 * Created by Guo Tianyou on 2023/6/9.
 */
@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;

        String phoneNum = (String) smsAuthenticationToken.getPrincipal();
        String smsCode = (String) smsAuthenticationToken.getCredentials();

        // 从Redis中获取验证码
        String code = null;
        try {
            code = redisUtils.get("code:" + phoneNum).toString();
        } catch (Exception e) {
            throw new RedisException("redis连接异常");
        }

        if (smsCode.equals(code)) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(phoneNum);
            return new SmsAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } else {
            throw new SmsCodeException("验证码错误");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
