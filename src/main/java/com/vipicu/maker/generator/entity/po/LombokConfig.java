package com.vipicu.maker.generator.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * lombok配置
 *
 * @author oohmygosh
 * @since 2023/01/08
 */
@Getter
@Setter
@Schema(name = "LombokConfig", description = "实体Lombok配置")
@AllArgsConstructor
@NoArgsConstructor
public class LombokConfig {
        @Schema(description = "@Builder")
        private Boolean builder;
        @Schema(description = "@NoArgsConstructor")
        private Boolean noArgsConstructor;
        @Schema(description = "@AllArgsConstructor")
        private Boolean allArgsConstructor;
        @Schema(description = "@ToString")
        private Boolean toString;
        @Schema(description = "@EqualsAndHashCode")
        private Boolean equalsAndHashCode;
        @Schema(description = "@Data")
        private Boolean data;
        @Schema(description = "@Getter & @Setter")
        private Boolean getterAndSetter;
}
