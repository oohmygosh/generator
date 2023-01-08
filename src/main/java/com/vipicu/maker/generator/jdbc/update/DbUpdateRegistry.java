package com.vipicu.maker.generator.jdbc.update;



import com.vipicu.maker.generator.enums.IDbType;

import java.util.EnumMap;
import java.util.Map;

/**
 * 数据库更新注册表
 *
 * @author oohmygosh
 * @since 2022/12/23
 */
public class DbUpdateRegistry {
    private static final Map<IDbType, DataBaseUpdate> DB_UPDATE_ENUM_MAP = new EnumMap<>(IDbType.class);

    private DbUpdateRegistry(){}

    static {
        DB_UPDATE_ENUM_MAP.put(IDbType.POSTGRE_SQL,new PostgreSqlUpdate());
        //DB_UPDATE_ENUM_MAP.put(IDbType.MYSQL,new MysqlUpdate());
    }

    public static DataBaseUpdate getDbUpdate(IDbType dbType){
        return DB_UPDATE_ENUM_MAP.get(dbType);
    }

}
