package com.cupk.snapshot.advise;

import com.cupk.snapshot.domain.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@RestControllerAdvice(basePackages = "com.cupk.snapshot.controller")
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 判断返回类型是否为R。返回true执行beforeBodyWrite()方法，否则直接返回
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getParameterType().equals(R.class);
    }

    /**
     * 封装响应体为统一响应格式，并以Json格式返回
     *
     * @param body @ResponseBody修饰的方法的返回值
     * @return Json格式的响应体
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof String) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(R.success((String) body));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return R.success(body);
    }
}
