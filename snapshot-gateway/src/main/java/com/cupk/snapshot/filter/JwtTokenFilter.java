package com.cupk.snapshot.filter;

import com.cupk.snapshot.utils.RedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

/**
 * JwtToken过滤器
 * Created by Guo Tianyou on 2023/6/9.
 */
@Component
public class JwtTokenFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        //放行全部令牌接口，注册接口
        if(path.startsWith("/oauth") || path.equals("/user/signup")) {
            return chain.filter(exchange);
        }
        List<String> list = request.getHeaders().get("Authorization");
        if(!CollectionUtils.isEmpty(list)) {
            String accessToken = list.get(0).replaceFirst("Bearer ", "");
            if(!StringUtils.isEmpty(accessToken)) {
                boolean flag = redisUtils.exists("access_token:" + accessToken);
                if(flag) {
                    return chain.filter(exchange);
                }
            }
        }
        //未携带access_token或携带错误access_token
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add("content-type", "application/json;charset=UTF-8");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 401);
        map.put("msg", "您没有权限访问");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(map);
            DataBuffer wrap = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(wrap));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 过滤器加载优先级
     */
    @Override
    public int getOrder() {
        return 0;
    }

}
