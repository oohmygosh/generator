package com.vipicu.maker.generator.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.vipicu.maker.generator.core.validation.Create;
import com.vipicu.maker.generator.enums.RenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * 生成器模板
 *
 * @author oohmygosh
 * @since 2022-12-19
 */
@Getter
@Setter
@Schema(name = "SysGeneratorTemplate", description = "生成器模板")
@TableName("sys_generator_template")
public class SysGeneratorTemplate extends TableBase {

    @Schema(description = "0:entity 1:mapper 2:service 3:serviceImpl 4:controller 5:xml")
    @NotNull(groups = Create.class)
    @PositiveOrZero
    private RenderType type;

    @Schema(description = "0:velocity 1:beetl 2:freemarker")
    @NotNull(groups = Create.class)
    @PositiveOrZero
    private Integer templateType;

    @Schema(description = "模板")
    private String template;

    @Schema(description = "应用")
    private String app;
}
