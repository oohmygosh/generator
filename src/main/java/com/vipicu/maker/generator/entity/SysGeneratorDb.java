package com.vipicu.maker.generator.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vipicu.maker.generator.core.bean.BaseEntity;
import com.vipicu.maker.generator.enums.IDbType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

/**
 * 代码生成目标数据库
 *
 * @author oohmygosh
 * @since 2022-12-14
 */
@Getter
@Setter
@Schema(name = "SysGeneratorDb", description = "GeneratorDb")
@TableName("sys_generator_db")
public class SysGeneratorDb extends BaseEntity {

    @Schema(description = "数据库连接URL")
    @Size(max = 50)
    private String url;

    @Schema(description = "用户名")
    @Size(max = 50)
    private String username;

    @Schema(description = "密码")
    @Size(max = 50)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(description = "数据库名称")
    @Size(max = 50)
    private String dbName;

    @Schema(description = "数据库类型")
    @Size(max = 50)
    private IDbType dbType;

    @Schema(description = "数据库描述")
    @Size(max = 50)
    private String comment;

}