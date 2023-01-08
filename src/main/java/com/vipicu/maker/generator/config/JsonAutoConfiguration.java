package com.vipicu.maker.generator.config;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.vipicu.maker.generator.core.editors.DoubleEditor;
import com.vipicu.maker.generator.core.editors.IntegerEditor;
import com.vipicu.maker.generator.core.editors.LongEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.beans.PropertyEditorSupport;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**

 * ----------------------------------------
 * Json 处理相关配置
 *
 * @author oohmygosh
 * @since 2021-10-28
 */
@Lazy
@RestControllerAdvice
@Configuration(proxyBeanMethods = false)
public class JsonAutoConfiguration {

    /**
     * 字符串转换处理
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 日期转换
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new DateFormat() {
            private final List<? extends DateFormat> DATE_FORMATS = Arrays.asList(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm"),
                    new SimpleDateFormat("yyyy-MM-dd"),
                    new SimpleDateFormat("yyyy-MM"));

            @Override
            public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
                throw new UnsupportedOperationException("This custom date formatter can only be used to *parse* Dates.");
            }

            @Override
            public Date parse(String source, ParsePosition pos) {
                for (final DateFormat dateFormat : DATE_FORMATS) {
                    Date date = dateFormat.parse(source, pos);
                    if (null != date) {
                        return date;
                    }
                }
                return null;
            }
        }, true));
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {

        });
        // 其他类型转换
        binder.registerCustomEditor(Long.class, new LongEditor());
        binder.registerCustomEditor(Double.class, new DoubleEditor());
        binder.registerCustomEditor(Integer.class, new IntegerEditor());
    }

    /**
     * 消息转换
     */
    @Bean
    public HttpMessageConverters jacksonHttpMessageConverters() {
        final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        final ObjectMapper objectMapper = builder.build();
        SimpleModule simpleModule = new SimpleModule();
        // Long 转为 String 防止 js 丢失精度
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        // 忽略 transient 关键词属性
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        jacksonConverter.setDefaultCharset(StandardCharsets.UTF_8);
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        stringConverter.setDefaultCharset(StandardCharsets.UTF_8);
        return new HttpMessageConverters(jacksonConverter, stringConverter);
    }
}
