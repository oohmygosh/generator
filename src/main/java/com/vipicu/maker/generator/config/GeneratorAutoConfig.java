package com.vipicu.maker.generator.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 发电机自动配置
 *
 * @author oohmygosh
 * @since 2023/01/08
 */
@Configuration
public class GeneratorAutoConfig {
    public static final String API_PREFIX = "${maker.generator.api-prefix:/generator}";


    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Generator")
                        .version("1.0")
                        .description( "Maker代碼生成器")
                        .license(new License().name("Apache 2.0")
                                .url("http://43.139.4.6")));
    }

    @Bean
    public WebMvcConfigurer generatorWebMvcConfigurer(){
        return new GeneratorWebMvcConfiguration();
    }

}
