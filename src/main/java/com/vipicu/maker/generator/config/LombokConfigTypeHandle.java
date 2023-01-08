package com.vipicu.maker.generator.config;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vipicu.maker.generator.entity.po.LombokConfig;

/**
 * lombok配置类型处理
 *
 * @author oohmygosh
 * @since 2022/12/19
 */
public class LombokConfigTypeHandle extends JacksonTypeHandler {
    public LombokConfigTypeHandle(Class<?> type) {
        super(type);
    }

    @Override
    protected Object parse(String json) {
        try {
            return getObjectMapper().readValue(json, new TypeReference<LombokConfig>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Lombok 转换失败");
        }
    }
}
