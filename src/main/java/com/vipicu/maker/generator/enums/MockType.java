package com.vipicu.maker.generator.enums;

import lombok.Getter;

/**
 * 模拟类型
 *
 * @author oohmygosh
 * @since 2022/12/26
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
public enum MockType {
    INCREASE(1,"递增"),
    FIXED(2,"固定"),
    RANDOM(3,"随机"),
    REGX(4,"正则"),
    ;

    private final String desc;
    private final Integer code;

    MockType(int code,String desc) {
        this.code = code;
        this.desc = desc;
    }
}
