package com.vipicu.maker.generator.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.vipicu.maker.generator.core.bean.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 表基础
 *
 * @author oohmygosh
 * @since 2022/12/19
 */
@Getter
@Setter
@Schema(description = "表基类")
public class TableBase extends BaseEntity {
    @Schema(description = "名称")
    protected String name;
    @TableField(exist = false)
    private Integer deleted;
}
