package com.vipicu.maker.generator.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.vipicu.maker.generator.core.validation.Create;
import com.vipicu.maker.generator.core.validation.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 代码生成表资源
 *
 * @author oohmygosh
 * @since 2022-12-16
 */
@Getter
@Setter
@Schema(name = "SysGeneratorTable", description = "代码生成表资源")
@TableName("sys_generator_table")
public class SysGeneratorTable extends TableBase {
    @NotNull(groups = Create.class)
    @Schema(description = "数据源Id")
    @PositiveOrZero
    private Long dbId;

    @NotBlank(groups = {Create.class, Update.class})
    @Schema(description = "表名")
    @Size(max = 50)
    private String name;

    @Schema(description = "表注释")
    @TableField(exist = false)
    private String comment;

    @TableField(exist = false)
    private Integer deleted;

    @TableField(exist = false)
    @Schema(description = "表策略")
    private TableStrategy tableStrategy;

    @TableField(exist = false)
    @Schema(description = "表字段")
    private List<SysGeneratorField> fields;

}
