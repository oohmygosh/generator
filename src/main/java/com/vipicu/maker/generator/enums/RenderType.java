package com.vipicu.maker.generator.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 类型
 *
 * @author oohmygosh
 * @since 2022/12/19
 */
@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RenderType {
    /**
     * ENTITY
     */
    ENTITY(0, "Entity", "java"),
    /**
     * MAPPER
     */
    MAPPER(1, "Mapper", "java"),
    /**
     * 服务
     */
    SERVICE(2, "Service", "java"),
    /**
     * 服务impl
     */
    SERVICE_IMPL(3, "ServiceImpl", "java"),
    /**
     * 控制器
     */
    CONTROLLER(4, "Controller", "java"),
    /**
     * typescript
     */
    TS(5, "TypeScript", "typescript"),
    /**
     * xml
     */
    XML(6, "mapperXml", "xml"),
    /**
     * json
     */
    JSON(7, "json", "json"),
    /**
     * sql
     */
    SQL(8, "sql", "sql"),
    ;
    @EnumValue
    private final Integer code;
    private final String desc;
    private final String language;

}
