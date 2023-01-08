package com.vipicu.maker.generator.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库类型
 *
 * @author oohmygosh
 * @since 2022/12/19
 */
@Getter
@AllArgsConstructor
public enum IDbType {
    /**
     * POSTGRESQL
     */
    POSTGRE_SQL("PostgreSQL","postgreSQL数据库"),
    /**
     * MYSQL
     */
    MYSQL("mysql","mysql数据库");
    @EnumValue
    private final String db;
    private final String desc;

    public static IDbType getDbType(String dbName){
        for (IDbType db : IDbType.values()) {
            if (db.db.equalsIgnoreCase(dbName)) {
                return db;
            }
        }
        return null;
    }

    public static String getSupported(){
        StringBuilder sb = new StringBuilder();
        for (IDbType value : IDbType.values()) {
            sb.append(value.db).append("、");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @JsonValue
    public String getDb() {
        return db;
    }
}
