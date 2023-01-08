package com.vipicu.maker.generator.mock;

import com.apifan.common.random.entity.DataField;
import com.vipicu.maker.generator.entity.SysGeneratorField;

/**
 * 固定值生成器
 *
 * @author oohmygosh
 * @since 2022/12/26
 */
public class FixedValueGenerator implements MockDataGenerator {
    @Override
    public DataField doGenerator(SysGeneratorField field) {
        return new DataField(field.getName(), field::getMockParam);
    }
}
