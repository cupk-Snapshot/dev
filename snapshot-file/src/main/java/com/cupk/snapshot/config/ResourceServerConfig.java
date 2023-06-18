package com.cupk.snapshot.config;

import cn.hutool.core.bean.BeanUtil;
import com.cupk.snapshot.domain.R;
import com.cupk.snapshot.handler.UserAuthenticationEntryPoint;
import com.cupk.snapshot.service.Oauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private UserAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private Oauth2Service tokenKeyService;

    private String publicKey;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * JwtAccessToken转换器，解析前台传来的token
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // 设置签名密钥，对称加密
//        jwtAccessTokenConverter.setSigningKey("sign-key");
        // 设置签名密钥，非对称加密
        jwtAccessTokenConverter.setVerifierKey(publicKey);
        return jwtAccessTokenConverter;
    }

    /**
     * 初始化时远程调用授权服务器，获取JWT签名公钥
     */
    @PostConstruct
    public void init() {
        R key = tokenKeyService.getKey();
        Map<String, Object> map = BeanUtil.beanToMap(key.getData(), new HashMap<>(), false, true);
        this.publicKey = (String) map.get("value");
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore());
        // AccessDeniedException会被全局异常处理捕获
        resources.authenticationEntryPoint(authenticationEntryPoint);
    }

    /**
     *
     * @param http the current http filter configuration
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/v2/**","/swagger-resources","/webjars/**","/favicon.ico","/doc.html").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
    }
}
