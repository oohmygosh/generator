package com.vipicu.maker.generator.config;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vipicu.maker.generator.entity.SysGeneratorSuperclass;

import java.util.List;

/**
 * 超类类型处理程序
 *
 * @author oohmygosh
 * @since 2022/12/20
 */
public class SuperclassTypeHandler extends JacksonTypeHandler {
    public SuperclassTypeHandler(Class<?> type) {
        super(type);
    }

    @Override
    protected Object parse(String json) {
        try {
            return getObjectMapper().readValue(json, new TypeReference<List<SysGeneratorSuperclass.CommonField>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
