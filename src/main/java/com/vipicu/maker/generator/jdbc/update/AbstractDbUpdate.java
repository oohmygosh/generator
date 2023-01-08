package com.vipicu.maker.generator.jdbc.update;

/**
 * 抽象数据库更新
 *
 * @author oohmygosh
 * @since 2022/12/22
 */
public abstract class AbstractDbUpdate implements DataBaseUpdate {
    @Override
    public String deleteTable() {
        return "drop table if exists %s";
    }

    @Override
    public String deletedField() {
        return """
                alter table %s
                    drop column %s;
                """;
    }
}
