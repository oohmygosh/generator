package com.vipicu.maker.generator.mock;

import com.apifan.common.random.entity.DataField;
import com.mifmif.common.regex.Generex;
import com.vipicu.maker.generator.entity.SysGeneratorField;

/**
 * regx发电机
 *
 * @author oohmygosh
 * @since 2022/12/26
 */
public class RegxGenerator implements MockDataGenerator {
    @Override
    public DataField doGenerator(SysGeneratorField field) {
        return new DataField(field.getName(),() -> new Generex(field.getMockParam()).random());
    }
}
