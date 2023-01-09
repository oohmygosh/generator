package com.vipicu.maker.generator.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.vipicu.maker.generator.core.bean.BaseEntity;
import com.vipicu.maker.generator.core.validation.Create;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashSet;
import java.util.Set;

/**
 * 代码生成策略
 *
 * @author oohmygosh
 * @since 2022-12-17
 */
@Getter
@Setter
@Schema(name = "SysGeneratorStrategy", description = "代码生成策略")
@TableName(value = "sys_generator_strategy",autoResultMap = true)
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

    @Schema(description = "表前缀")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Set<String> tablePrefix = new HashSet<>();

    @Schema(description = "表后缀")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Set<String> tableSuffix = new HashSet<>();

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
