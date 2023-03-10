package com.vipicu.maker.generator;

import com.vipicu.maker.generator.utils.SpringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 代码生成机应用程序
 *
 * @author oohmygosh
 * @since 2023/01/08
 */
@SpringBootApplication
@MapperScan({"com.vipicu.maker.generator.mapper"})
public class MakerGeneratorApplication {

    public static void main(String[] args) {
        SpringUtils.setApplicationContext(SpringApplication.run(MakerGeneratorApplication.class, args));
    }

}
