package com.vipicu.maker.generator.jdbc.update;

/**
 * 数据库更新
 *
 * @author oohmygosh
 * @since 2022/12/22
 */
public interface DataBaseUpdate {

    /**
     * 添加字段
     *
     * @return {@link String}
     */
    String addField();

    /**
     * 添加表
     *
     * @return {@link String}
     */
    String addTable();

    /**
     * 更新表名
     *
     * @return {@link String}
     */
    String updateTableName();

    /**
     * 更新表注释
     *
     * @return {@link String}
     */
    String updateTableComment();

    /**
     * 删除表
     *
     * @return {@link String}
     */
    String deleteTable();

    /**
     * 更新字段名
     *
     * @return {@link String}
     */
    String updateFieldName();

    /**
     * 更新字段注释
     *
     * @return {@link String}
     */
    String updateFiledComment();

    /**
     * 更新字段类型
     *
     * @return {@link String}
     */
    String updateFieldType();

    /**
     * 更新字段默认值
     *
     * @return {@link String}
     */
    String updateFieldDefaultValue();

    /**
     * 添加字段主键
     *
     * @return {@link String}
     */
    String addFieldPrimaryKey();

    /**
     * 删除字段主键
     *
     * @return {@link String}
     */
    String deleteFieldPrimaryKey();

    /**
     * 更新字段增加
     *
     * @return {@link String}
     */
    String updateFieldIncrement();

    /**
     * 更新字段可以为空
     *
     * @param flag 国旗
     * @return {@link String}
     */
    String updateFieldNullable(boolean flag);

    /**
     * 删除字段
     *
     * @return {@link String}
     */
    String deletedField();

    /**
     * 删除字段默认值
     *
     * @return {@link String}
     */
    String deleteFieldDefaultValue();
}
