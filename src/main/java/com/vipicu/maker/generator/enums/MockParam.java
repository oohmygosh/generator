package com.vipicu.maker.generator.enums;

import lombok.Getter;

/**
 * 模拟参数
 *
 * @author oohmygosh
 * @since 2022/12/26
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
public enum MockParam {
    EMAIL("邮箱"),
    PHONE_NUM("手机号"),
    DATE("日期"),
    ADDRESS("地址"),
    ID_CARD("身份证"),
    PASSWORD("密码"),
    CHINESE_NAME("中文名字"),
    ENGLISH_NAME("英文名字"),
    PLATE_NUM("车牌"),
    PRIVATE_IPV4("局域网Ipv4"),
    PUBLIC_IPV4("公网Ipv4"),
    IPV6("Ipv6"),
    DOMAIN("域名"),
    ;

    private final String desc;

    MockParam(String desc) {
        this.desc = desc;
    }

    public static MockParam getMockParam(String desc) {
        for (MockParam value : MockParam.values()) {
            if (value.desc.equalsIgnoreCase(desc))
                return value;
        }
        return null;
    }

}
