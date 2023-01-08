package com.vipicu.maker.generator.entity.po;

import com.vipicu.maker.generator.core.bean.BeanConvert;
import com.vipicu.maker.generator.enums.JdbcType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 表字段
 *
 * @author oohmygosh
 * @since 2022/12/20
 */
@Getter
@Setter
@Builder
public class TableField implements BeanConvert {
    /**
     * 字段名
     */
    private String name;

    /**
     * 属性名
     */
    private String propertyName;

    /**
     * 首字大写
     */
    private String capitalName;

    /**
     * 主键
     */
    private boolean primaryKey;

    /**
     * 可为空
     */
    private boolean nullable;

    /**
     * 长度
     */
    private int length;

    /**
     * 注释
     */
    private String comment;

    /**
     * 属性类型
     */
    private String propertyType;

    /**
     * jdbc类型
     */
    private JdbcType jdbcType;

    /**
     * 规模
     */
    private int scale;

    /**
     * 自定义参数
     */
    private Map<String,Object> customMap;

}
