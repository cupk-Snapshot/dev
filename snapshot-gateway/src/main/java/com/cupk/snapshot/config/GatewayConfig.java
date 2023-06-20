package com.cupk.snapshot.config;

import cn.hutool.json.JSONUtil;
import com.cupk.snapshot.exception.RedisException;
import com.cupk.snapshot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class GatewayConfig {

    @Autowired
    private RedisUtils redisUtils;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("snapshot-oauth2-server-route", r -> r
                        .path("/oauth/**")
                        .filters(f -> f.modifyResponseBody(String.class, String.class, (serverWebExchange, s) -> {
                            String path = serverWebExchange.getRequest().getPath().value();
                            if (path.equalsIgnoreCase("/oauth/token")) {// 是否为登录请求
                                if (!StringUtils.isEmpty(s)) {
                                    Map data = JSONUtil.parseObj(s).get("data", Map.class);
                                    if (!ObjectUtils.isEmpty(data)) {
                                        String accessToken = data.get("access_token").toString();
                                        Integer expiresIn = (Integer) data.get("expires_in");
                                        // 把JwtToken存到Redis中
                                        try {
                                            redisUtils.set("access_token:" + accessToken, "", expiresIn, TimeUnit.SECONDS);
                                        } catch (Exception e) {
                                            throw new RedisException("redis连接异常");
                                        }
                                    }
                                }
                            }
                            return Mono.just(s);
                        })).uri("lb://snapshot-oauth2-server"))
                .build();
    }

    /**
     * CORS跨域过滤器
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

}
