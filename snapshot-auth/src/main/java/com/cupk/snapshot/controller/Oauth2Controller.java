package com.cupk.snapshot.controller;

import com.cupk.snapshot.model.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenKeyEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义Oauth2令牌接口
 * Created by Guo Tianyou on 2023/6/6.
 */
@Controller
@RequestMapping("/oauth")
public class Oauth2Controller {
    /*
     * 令牌请求端点
     */
    @Autowired
    private TokenEndpoint tokenEndpoint;

    /*
     * 令牌校验端点
     */
    @Autowired
    private CheckTokenEndpoint checkTokenEndpoint;

    /*
     * 令牌授权端点
     */
    @Autowired
    private AuthorizationEndpoint authorizationEndpoint;

    /*
     * 获取公钥端口
     */
    @Resource
    private TokenKeyEndpoint tokenKeyEndpoint;

    /**
     * 获取AccessToken
     */
    @GetMapping(value = "/token")
    @ResponseBody
    public R getAccessToken(Principal principal, @RequestParam Map<String, String> parameters)
            throws HttpRequestMethodNotSupportedException {
        return postAccessToken(principal, parameters);
    }

    @PostMapping(value = "/token")
    @ResponseBody
    public R postAccessToken(Principal principal, @RequestParam Map<String, String> parameters)
            throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("access_token", oAuth2AccessToken.getValue());
                put("refresh_token", oAuth2AccessToken.getRefreshToken().getValue());
                put("expires_in", oAuth2AccessToken.getExpiresIn());
                put("user", oAuth2AccessToken.getAdditionalInformation());
            }
        };
        return R.success(map);
    }

    /**
     * 校验AccessToken
     */
    @RequestMapping(value = "/check_token")
    @ResponseBody
    public R checkToken(@RequestParam("token") String value) {
        Map<String, ?> map = checkTokenEndpoint.checkToken(value);
        return R.success(map);
    }

    /**
     * 获取授权码
     */
    @RequestMapping(value = "/authorize")
    public ModelAndView authorize(Map<String, Object> model, @RequestParam Map<String, String> parameters,
                                  SessionStatus sessionStatus, Principal principal) {
        return authorizationEndpoint.authorize(model, parameters, sessionStatus, principal);
    }

    @GetMapping("/token_key")
    @ResponseBody
    public R getKey(Principal principal) {
        Map<String, String> map = tokenKeyEndpoint.getKey(principal);
        return R.success(map);
    }
}