package com.vipicu.maker.generator.core.bean;


import com.vipicu.maker.generator.core.validation.Create;
import com.vipicu.maker.generator.core.validation.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**

 * ----------------------------------------
 * 基础类
 *
 * @author oohmygosh
 * @since 2021-10-28
 */
@Setter
@Getter
public class SuperEntity implements BeanConvert {

    /**
     * 主键
     */
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    @Schema(description = "主键")
    private Long id;

}
