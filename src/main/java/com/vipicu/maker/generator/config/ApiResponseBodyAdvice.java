package com.vipicu.maker.generator.config;


import com.vipicu.maker.generator.core.api.ApiResult;
import com.vipicu.maker.generator.utils.JacksonUtils;
import io.micrometer.common.lang.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * 响应方法返回值处理类
 *
 * @author oohmygosh
 * @since 2021-12-10
 */
@RestControllerAdvice({
        "com.vipicu.maker.generator"
})
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, @NonNull Class converterType) {
        return !Objects.requireNonNull(returnType.getMethod()).getReturnType().isAssignableFrom(Void.TYPE);
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType, @NonNull Class selectedConverterType,
                                  @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        if (body instanceof ApiResult) {
            return body;
        }
        ApiResult<?> apiResult = ApiResult.ok(body);
        if (returnType.getParameterType().isAssignableFrom(String.class)) {
            // 字符串类型特殊处理
            return JacksonUtils.toJSONString(apiResult);
        }
        return apiResult;
    }
}
