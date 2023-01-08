package com.vipicu.maker.generator.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.vipicu.maker.generator.config.LombokConfigTypeHandle;
import com.vipicu.maker.generator.core.bean.BaseEntity;
import com.vipicu.maker.generator.core.validation.Create;
import com.vipicu.maker.generator.entity.po.LombokConfig;
import com.vipicu.maker.generator.enums.NamingStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * 表生成策略
 *
 * @author oohmygosh
 * @since 2022-12-19
 */
@Getter
@Setter
@Schema(name = "SysGeneratorTableStrategy", description = "表生成策略")
@TableName(value = "sys_generator_table_strategy", autoResultMap = true)
public class TableStrategy extends BaseEntity {

    @Schema(description = "表Id")
    @NotNull(groups = Create.class)
    @PositiveOrZero
    private Long tableId;

    @Schema(description = "包路径")
    @Size(max = 50)
    private String packagePath;

    @Schema(description = "作者")
    @Size(max = 50)
    private String author;

    @Schema(description = "Swagger配置")
    @PositiveOrZero
    private Boolean swagger;

    @Schema(description = "@Mapper注解")
    @PositiveOrZero
    private Boolean mapper;

    @Schema(description = "lombok配置")
    @TableField(typeHandler = LombokConfigTypeHandle.class)
    private LombokConfig lombok;

    @Schema(description = "超类Id")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Long> superclass;

    @Schema(description = "模板Id")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Long> templates;

    @TableField(exist = false)
    private Integer deleted;

    @TableField(exist = false)
    @Schema(description = "命名策略")
    private NamingStrategy naming = NamingStrategy.underline_to_camel;

    @TableField(exist = false)
    @Schema(description = "表前缀")
    private Set<String> tablePrefix = new HashSet<>();

    @TableField(exist = false)
    @Schema(description = "表后缀")
    private Set<String> tableSuffix = new HashSet<>();

    @TableField(exist = false)
    @Schema(description = "字段前缀")
    private Set<String> fieldPrefix = new HashSet<>();

    @TableField(exist = false)
    @Schema(description = "字段后缀")
    private Set<String> fieldSuffix = new HashSet<>();

    @Schema(description = "mock条数")
    private Integer mockNum;

    @Schema(description = "参数校验")
    private Boolean validation;


    public static TableStrategy getDefaultStrategy(Long tableId){
        TableStrategy strategy = new TableStrategy();
        strategy.setTableId(tableId);
        strategy.setLombok(new LombokConfig(false,false,false,false,false,false,true));
        strategy.setMapper(true);
        strategy.setSwagger(true);
        strategy.setSuperclass(new ArrayList<>(Arrays.asList(0L,1L,2L,3L,4L)));
        strategy.setTemplates(new ArrayList<>(Arrays.asList(0L,1L,2L,3L,4L)));
        return strategy;
    }
}
