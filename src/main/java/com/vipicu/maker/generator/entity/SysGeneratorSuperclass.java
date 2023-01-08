package com.vipicu.maker.generator.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.vipicu.maker.generator.config.SuperclassTypeHandler;
import com.vipicu.maker.generator.core.validation.Create;
import com.vipicu.maker.generator.enums.RenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 父类
 *
 * @author oohmygosh
 * @since 2022-12-20
 */
@Getter
@Setter
@Schema(name = "SysGeneratorSuperclass", description = "父类")
@TableName(value = "sys_generator_superclass",autoResultMap = true)
public class SysGeneratorSuperclass extends TableBase {

    @Schema(description = "0:entity 1:mapper 2:service 3:serviceImpl 4:controller")
    @NotNull(groups = Create.class)
    @PositiveOrZero
    private RenderType type;

    @Schema(description = "公共字段")
    @TableField(typeHandler = SuperclassTypeHandler.class)
    private List<CommonField> field;

    @Schema(description = "应用")
    @NotBlank(groups = Create.class)
    @Size(max = 30)
    private String app;

    @Schema(description = "包路径")
    @NotBlank(groups = Create.class)
    private String path;

    @Getter
    @Setter
    public static class CommonField {
        /**
         * 名字
         */
        private String name;

        /**
         * 字段填满
         */
        private FieldFill fieldFill;

        /**
         * 包路径
         */
        private String packagePath;
    }

}
