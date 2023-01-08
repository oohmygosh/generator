package com.vipicu.maker.generator.entity.param;

import com.vipicu.maker.generator.entity.SysGeneratorTable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 生成参数
 *
 * @author oohmygosh
 * @since 2022/12/16
 */
@Schema(name = "GeneratorParam",description = "代码生成参数")
@Getter
@Setter
public class GeneratorParam {
    /**
     * 数据源id
     */
    private Long id;
    /**
     * 表格
     */
    private List<SysGeneratorTable> tables;
}
