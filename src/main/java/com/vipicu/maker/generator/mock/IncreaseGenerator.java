package com.vipicu.maker.generator.mock;

import com.apifan.common.random.entity.DataField;
import com.vipicu.maker.generator.entity.SysGeneratorField;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自增
 *
 * @author oohmygosh
 * @since 2022/12/26
 */
public class IncreaseGenerator implements MockDataGenerator {
    @Override
    public DataField doGenerator(SysGeneratorField field) {
        AtomicInteger atomicInteger = new AtomicInteger(Integer.parseInt(field.getMockParam()));
        return new DataField(field.getName(), atomicInteger::getAndIncrement);
    }
}
