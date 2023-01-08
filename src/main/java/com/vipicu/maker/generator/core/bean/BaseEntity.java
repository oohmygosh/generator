package com.vipicu.maker.generator.core.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vipicu.maker.generator.core.ApiConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**

 * ----------------------------------------
 * 业务框架基础实体
 *
 * @author oohmygosh
 * @since 2021-10-28
 */
@Setter
@Getter
public class BaseEntity extends SuperEntity {

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人ID")
    protected Long createId;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人")
    protected String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = ApiConstants.DATE_MM)
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    protected LocalDateTime createTime;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.UPDATE)
    @Schema(description = "修改人")
    protected String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = ApiConstants.DATE_MM)
    @TableField(fill = FieldFill.UPDATE)
    @Schema(description = "修改时间")
    protected LocalDateTime updateTime;

    /**
     * 删除 0、否 1、是
     */
    @JsonIgnore
    @TableLogic(value = "0", delval = "1")
    @Schema(description = "删除 0:否 1:是")
    private Integer deleted;

}
