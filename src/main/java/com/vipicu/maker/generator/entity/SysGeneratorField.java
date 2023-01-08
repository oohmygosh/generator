package com.vipicu.maker.generator.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.vipicu.maker.generator.core.validation.Create;
import com.vipicu.maker.generator.enums.JdbcType;
import com.vipicu.maker.generator.enums.MockType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

/**
 * 代码生成表字段
 *
 * @author oohmygosh
 * @since 2022-12-17
 */
@Getter
@Setter
@Schema(name = "SysGeneratorField", description = "代码生成表字段")
@TableName("sys_generator_field")
public class SysGeneratorField extends TableBase {

    @Schema(description = "表Id")
	@NotNull(groups = Create.class)
	@PositiveOrZero
	private Long tableId;

    @Schema(description = "字段名")
	@Size(max = 30)
	private String name;

	@TableField(exist = false)
	private Integer deleted;

	@Schema(description = "主键")
	@TableField(exist = false)
	private boolean primaryKey;

	@Schema(description = "自增")
	@TableField(exist = false)
	private boolean autoIncrement;

	@Schema(description = "长度")
	@TableField(exist = false)
	private int length;

	@Schema(description = "空字段")
	@TableField(exist = false)
	private boolean nullable;

	@Schema(description = "注释")
	@TableField(exist = false)
	private String comment;

	@Schema(description = "默认值")
	@TableField(exist = false)
	private String defaultValue;

	@Schema(description = "范围")
	@TableField(exist = false)
	private int scale;

	@Schema(description = "类型")
	@TableField(exist = false)
	private JdbcType jdbcType;

	@Schema(description = "属性名")
	@TableField(exist = false)
	private String propertyName;

	@Schema(description = "模拟数据类型")
	private MockType mockType;

	@Schema(description = "模拟数据参数")
	private String mockParam;
}
