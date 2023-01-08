package com.vipicu.maker.generator.type;


import com.vipicu.maker.generator.entity.po.TableField;
import com.vipicu.maker.generator.entity.po.TableInfo;
import com.vipicu.maker.generator.enums.DateType;
import com.vipicu.maker.generator.enums.IColumnType;
import com.vipicu.maker.generator.enums.JavaType;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * 类型注册处理类
 *
 * @author oohmygosh
 * @since 2022/12/20
 */
public class TypeRegistry {

    private final Map<Integer, IColumnType> typeMap = new HashMap<>();

    private final TableInfo tableInfo;

    public TypeRegistry(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
        // byte[]
        typeMap.put(Types.BINARY, JavaType.BYTE_ARRAY);
        typeMap.put(Types.BLOB, JavaType.BYTE_ARRAY);
        typeMap.put(Types.LONGVARBINARY, JavaType.BYTE_ARRAY);
        typeMap.put(Types.VARBINARY, JavaType.BYTE_ARRAY);
        //byte
        typeMap.put(Types.TINYINT, JavaType.BYTE);
        //long
        typeMap.put(Types.BIGINT, JavaType.LONG);
        //boolean
        typeMap.put(Types.BIT, JavaType.BOOLEAN);
        typeMap.put(Types.BOOLEAN, JavaType.BOOLEAN);
        //short
        typeMap.put(Types.SMALLINT, JavaType.SHORT);
        //string
        typeMap.put(Types.CHAR, JavaType.STRING);
        typeMap.put(Types.CLOB, JavaType.STRING);
        typeMap.put(Types.VARCHAR, JavaType.STRING);
        typeMap.put(Types.LONGVARCHAR, JavaType.STRING);
        typeMap.put(Types.LONGNVARCHAR, JavaType.STRING);
        typeMap.put(Types.NCHAR, JavaType.STRING);
        typeMap.put(Types.NCLOB, JavaType.STRING);
        typeMap.put(Types.NVARCHAR, JavaType.STRING);
        //date
        typeMap.put(Types.DATE, JavaType.DATE);
        //timestamp
        typeMap.put(Types.TIMESTAMP, JavaType.TIMESTAMP);
        //double
        typeMap.put(Types.FLOAT, JavaType.DOUBLE);
        typeMap.put(Types.REAL, JavaType.DOUBLE);
        //int
        typeMap.put(Types.INTEGER, JavaType.INTEGER);
        //bigDecimal
        typeMap.put(Types.NUMERIC, JavaType.BIG_DECIMAL);
        typeMap.put(Types.DECIMAL, JavaType.BIG_DECIMAL);
    }

    public IColumnType getColumnType(TableField field) {
        //TODO 是否用包装类??? 可以尝试判断字段是否允许为null来判断是否用包装类
        int typeCode = field.getJdbcType().TYPE_CODE;
        return switch (typeCode) {
            // TODO 需要增加类型处理，尚未补充完整
            case Types.BIT -> getBitType(field);
            case Types.DATE -> getDateType();
            case Types.TIME -> getTimeType();
            case Types.DECIMAL, Types.NUMERIC -> getNumber(field);
            case Types.TIMESTAMP -> getTimestampType();
            default -> typeMap.getOrDefault(typeCode, JavaType.OBJECT);
        };
    }

    private IColumnType getBitType(TableField field) {
        if (field.getLength() > 1) {
            return JavaType.BYTE_ARRAY;
        }
        return JavaType.BOOLEAN;
    }

    private IColumnType getNumber(TableField metaInfo) {
        if (metaInfo.getScale() > 0 || metaInfo.getLength() > 18) {
            return typeMap.get(metaInfo.getJdbcType().TYPE_CODE);
        } else if (metaInfo.getLength() > 9) {
            return JavaType.LONG;
        } else if (metaInfo.getLength() > 4) {
            return JavaType.INTEGER;
        } else {
            return JavaType.SHORT;
        }
    }

    private IColumnType getDateType() {
        JavaType javaType;
        DateType dateType = tableInfo.getDateType();
        javaType = switch (dateType) {
            case SQL_PACK -> JavaType.DATE_SQL;
            case TIME_PACK -> JavaType.LOCAL_DATE;
            default -> JavaType.DATE;
        };
        return javaType;
    }

    private IColumnType getTimeType() {
        JavaType javaType;
        DateType dateType = tableInfo.getDateType();
        if (dateType == DateType.TIME_PACK) {
            javaType = JavaType.LOCAL_TIME;
        } else {
            javaType = JavaType.TIME;
        }
        return javaType;
    }

    private IColumnType getTimestampType() {
        JavaType javaType;
        DateType dateType = tableInfo.getDateType();
        if (dateType == DateType.TIME_PACK) {
            javaType = JavaType.LOCAL_DATE_TIME;
        } else {
            javaType = JavaType.TIMESTAMP;
        }
        return javaType;
    }

}
