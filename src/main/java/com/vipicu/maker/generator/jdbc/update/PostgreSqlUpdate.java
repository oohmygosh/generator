package com.vipicu.maker.generator.jdbc.update;

/**
 * postgre sql更新
 *
 * @author oohmygosh
 * @since 2022/12/22
 */
public class PostgreSqlUpdate extends AbstractDbUpdate {

    @Override
    public String addField() {
        return """
                alter table %s
                    add %s %s;
                """;
    }

    @Override
    public String addTable() {
        return """
                create table %s
                (
                );
                """;
    }

    public String updateTableComment(){
        return """
                comment on table %s is '%s';
                """;
    }

    @Override
    public String updateTableName() {
        return """
                alter table %s rename to %s;
                """;
    }

    @Override
    public String updateFieldName() {
        return """
                alter table %s rename column %s to %s;
                """;
    }

    @Override
    public String updateFiledComment() {
        return """
                comment on column %s.%s is '%s';
                """;
    }

    /**
     * alter table sys_configure
     *     alter column create_id type varchar(10) using create_id::varchar(10);
     *
     * alter table sys_configure
     *     alter column create_by type bool using create_by::bool;
     */
    @Override
    public String updateFieldType() {
        return """
                alter table %s alter column %s type %s using %s::%s;
                """;
    }

    @Override
    public String updateFieldDefaultValue() {
        return """
                alter table %s alter column %s set default %s;
                """;
    }

    @Override
    public String deleteFieldDefaultValue() {
        return """
                alter table %s alter column %s  drop default;
                """;
    }

    /**
     * alter table sys_configure
     *     drop constraint sys_configure_pkey;
     *
     * alter table sys_configure
     *     add constraint sys_configure_pk
     *         primary key (create_time);
     */
    @Override
    public String addFieldPrimaryKey() {
        return """
                alter table %s add constraint %s_pk primary key (%s);
                """;
    }

    @Override
    public String deleteFieldPrimaryKey() {
        return """
                alter table %s drop constraint %s;
                """;
    }

    @Override
    public String updateFieldIncrement() {
        return null;
    }

    @Override
    public String updateFieldNullable(boolean flag) {
        if (!flag)
            return """
                    alter table %s alter column %s set not null;
                    """;
        return """
                alter table %s alter column %s drop not null;
                """;
    }

    @Override
    public String deletedField() {
        return """
                alter table sys_department drop column create_by;
                """;
    }
}
