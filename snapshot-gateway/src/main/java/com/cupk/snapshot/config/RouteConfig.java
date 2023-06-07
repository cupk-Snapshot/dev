package com.cupk.snapshot.config;

import cn.hutool.db.nosql.redis.RedisDS;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import redis.clients.jedis.params.SetParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class RouteConfig {

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
                                        RedisDS.create().getJedis().set("access_token:" + accessToken, "", new SetParams().ex(expiresIn));
                                    }
                                }
                            }
                            return Mono.just(s);
                        })).uri("lb://snapshot-oauth2-server"))
                .build();
    }

}
