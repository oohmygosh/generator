package com.vipicu.maker.generator.mock;


import com.vipicu.maker.generator.enums.MockType;

import java.util.EnumMap;

/**
 * 模拟发电机注册表
 *
 * @author oohmygosh
 * @since 2022/12/26
 */
public class MockGeneratorRegistry {
    private static final EnumMap<MockType, MockDataGenerator> GENERATOR_ENUM_MAP = new EnumMap<>(MockType.class) {
        {
            put(MockType.FIXED,new FixedValueGenerator());
            put(MockType.INCREASE,new IncreaseGenerator());
            put(MockType.RANDOM,new RandomGenerator());
            put(MockType.REGX,new RegxGenerator());
        }
    };

    public static MockDataGenerator getGenerator(MockType mockType){
        return GENERATOR_ENUM_MAP.get(mockType);
    }
}
