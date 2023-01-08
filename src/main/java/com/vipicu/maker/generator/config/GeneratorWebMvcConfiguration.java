package com.vipicu.maker.generator.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 发电机web mvc配置
 *
 * @author oohmygosh
 * @since 2023/01/08
 */
public class GeneratorWebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedOriginPatterns("*")
                .allowedHeaders("*");
    }
}
