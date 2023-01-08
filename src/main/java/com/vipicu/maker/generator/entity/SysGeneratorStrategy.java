package com.vipicu.maker.generator.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.vipicu.maker.generator.core.bean.BaseEntity;
import com.vipicu.maker.generator.core.validation.Create;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * 代码生成策略
 *
 * @author oohmygosh
 * @since 2022-12-17
 */
@Getter
@Setter
@Schema(name = "SysGeneratorStrategy", description = "代码生成策略")
@TableName("sys_generator_strategy")
@NoArgsConstructor
public class SysGeneratorStrategy extends BaseEntity {

    @Schema(description = "父Id")
    @NotNull(groups = Create.class)
    @PositiveOrZero
    private Long pid;

    @Schema(description = "包路径")
    private String packagePath = "com.vipicu.maker";

    @Schema(description = "作者")
    private String author = "oohmygosh";

    @TableField(exist = false)
    private Integer deleted;

    public SysGeneratorStrategy(String packagePath, String author) {
        this.packagePath = packagePath;
        this.author = author;
    }

    public SysGeneratorStrategy(Long pid){
        this.pid = pid;
    }

}
