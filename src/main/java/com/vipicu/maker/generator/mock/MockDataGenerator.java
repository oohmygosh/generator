package com.vipicu.maker.generator.mock;

import com.apifan.common.random.entity.DataField;
import com.vipicu.maker.generator.entity.SysGeneratorField;

/**
 * 模拟数据生成器
 *
 * @author oohmygosh
 * @since 2022/12/26
 */
public interface MockDataGenerator {
    /**
     * 生成
     *
     * @param field 字段
     * @return {@link DataField}
     */
    DataField doGenerator(SysGeneratorField field);
}
