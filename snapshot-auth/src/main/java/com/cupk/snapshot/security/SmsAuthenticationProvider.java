package com.cupk.snapshot.security;

import cn.hutool.core.util.ReUtil;
import cn.hutool.db.nosql.redis.RedisDS;
import com.cupk.snapshot.domain.model.User;
import com.cupk.snapshot.exception.SmsCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * Created by Guo Tianyou on 2023/6/9.
 */
@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;

        String phoneNum = (String) smsAuthenticationToken.getPrincipal();
        String smsCode = (String) smsAuthenticationToken.getCredentials();

        String code = RedisDS.create().getJedis().get("code:" + phoneNum);
        if(smsCode.equals(code)) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(phoneNum);
            return new SmsAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }else {
            throw new SmsCodeException("验证码错误");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
