package com.vipicu.maker.generator.jdbc;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.vipicu.maker.generator.core.bean.BeanConvert;
import com.vipicu.maker.generator.enums.JdbcType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库数据元包装类
 *
 * @author oohmygosh
 * @since 2022/12/16
 */
public class DatabaseMetaDataWrapper {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseMetaDataWrapper.class);

    private final DatabaseMetaData databaseMetaData;
    /**
     * 目录
     */
    private final String catalog;
    /**
     * 模式
     */
    private final String schema;
    Pattern compile = Pattern.compile("'(.*)'");

    public DatabaseMetaDataWrapper(@NotNull Connection connection) {
        try {
            this.databaseMetaData = connection.getMetaData();
            this.catalog = connection.getCatalog();
            this.schema = connection.getSchema();
        } catch (SQLException e) {
            throw new RuntimeException("获取元数据错误:", e);
        }
    }


    public Map<String, Column> getColumnsInfo(String tableNamePattern, boolean queryPrimaryKey) {
        return getColumnsInfo(this.catalog, this.schema, tableNamePattern, queryPrimaryKey);
    }

    public String getPkName(String tableNamePattern) {
        return getPkName(this.catalog, this.schema, tableNamePattern);
    }

    public String getPkName(String catalog, String schema, String tableName) {
        try (ResultSet primaryKeysResultSet = databaseMetaData.getPrimaryKeys(catalog, schema, tableName)) {
            while (primaryKeysResultSet.next()) {
                String pkName = primaryKeysResultSet.getString("PK_NAME");
                if (StringUtils.isNotBlank(pkName))
                    return pkName;
            }
        } catch (SQLException e) {
            throw new RuntimeException("读取表主键名:" + tableName + "错误:", e);
        }
        return null;
    }

    /**
     * 获取表字段信息
     *
     * @return 表字段信息 (小写字段名->字段信息)
     */
    public Map<String, Column> getColumnsInfo(String catalog, String schema, String tableName, boolean queryPrimaryKey) {
        Set<String> primaryKeys = new HashSet<>();
        if (queryPrimaryKey) {
            try (ResultSet primaryKeysResultSet = databaseMetaData.getPrimaryKeys(catalog, schema, tableName)) {
                while (primaryKeysResultSet.next()) {
                    String columnName = primaryKeysResultSet.getString("COLUMN_NAME");
                    primaryKeys.add(columnName);
                }
                if (primaryKeys.size() > 1) {
                    logger.warn("当前表:{}，存在多主键情况！", tableName);
                }
            } catch (SQLException e) {
                throw new RuntimeException("读取表主键信息:" + tableName + "错误:", e);
            }
        }
        Map<String, Column> columnsInfoMap = new LinkedHashMap<>();
        try (ResultSet resultSet = databaseMetaData.getColumns(catalog, schema, tableName, "%")) {
            while (resultSet.next()) {
                Column column = new Column();
                String name = resultSet.getString("COLUMN_NAME");
                column.name = name;
                column.primaryKey = primaryKeys.contains(name);
                String typeName = resultSet.getString("TYPE_NAME");
                JdbcType jdbcType = JdbcType.forName(typeName);
                column.jdbcType = jdbcType != null ? jdbcType : JdbcType.forCode(resultSet.getInt("DATA_TYPE"));
                column.length = resultSet.getInt("COLUMN_SIZE");
                column.scale = resultSet.getInt("DECIMAL_DIGITS");
                column.comment = formatComment(resultSet.getString("REMARKS"));
                column.defaultValue = resultSet.getString("COLUMN_DEF");
                if (column.defaultValue != null && column.defaultValue.matches(".*")) {
                    Matcher matcher = compile.matcher(column.defaultValue);
                    while (matcher.find())
                        column.defaultValue = matcher.group().replaceAll("'", "");
                }
                column.nullable = resultSet.getInt("NULLABLE") == DatabaseMetaData.columnNullable;
                try {
                    column.autoIncrement = "YES".equals(resultSet.getString("IS_AUTOINCREMENT"));
                } catch (SQLException sqlException) {
                    logger.warn("获取IS_AUTOINCREMENT出现异常:", sqlException);
                    //TODO 目前测试在oracle旧驱动下存在问题，降级成false.
                }
                columnsInfoMap.put(name.toLowerCase(), column);
            }
            return columnsInfoMap;
        } catch (SQLException e) {
            throw new RuntimeException("读取表字段信息:" + tableName + "错误:", e);
        }
    }

    public String formatComment(String comment) {
        return StringUtils.isBlank(comment) ? StringPool.EMPTY : comment.replaceAll("\r\n", "\t");
    }

    public Table getTableInfo(String tableName) {
        return getTableInfo(this.catalog, this.schema, tableName);
    }

    public List<Table> getTables(String tableNamePattern, String[] types) {
        return getTables(this.catalog, this.schema, tableNamePattern, types);
    }

    public List<Table> getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) {
        List<Table> tables = new ArrayList<>();
        try (ResultSet resultSet = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, types)) {
            Table table;
            while (resultSet.next()) {
                table = new Table();
                table.name = resultSet.getString("TABLE_NAME");
                table.comment = formatComment(resultSet.getString("REMARKS"));
                table.tableType = resultSet.getString("TABLE_TYPE");
                tables.add(table);
            }
        } catch (SQLException e) {
            throw new RuntimeException("读取数据库表信息出现错误", e);
        }
        return tables;
    }

    public Table getTableInfo(String catalog, String schema, String tableName) {
        Table table = new Table();
        //TODO 后面要根据表是否为视图来查询，后面重构表查询策略。
        try (ResultSet resultSet = databaseMetaData.getTables(catalog, schema, tableName, new String[]{"TABLE", "VIEW"})) {
            table.name = tableName;
            while (resultSet.next()) {
                table.comment = formatComment(resultSet.getString("REMARKS"));
                table.tableType = resultSet.getString("TABLE_TYPE");
            }
        } catch (SQLException e) {
            throw new RuntimeException("读取表信息:" + tableName + "错误:", e);
        }
        return table;
    }

    @ToString
    @Getter
    public static class Table implements BeanConvert {

        /**
         * 表名
         */
        private String name;

        /**
         * 注释
         */
        private String comment;

        /**
         * 表类型
         */
        private String tableType;

        /**
         * 是否是视图
         *
         * @return boolean
         */
        public boolean isView() {
            return "VIEW".equals(tableType);
        }

    }

    @ToString
    @Getter
    public static class Column implements BeanConvert {

        /**
         * 主键
         */
        private boolean primaryKey;

        /**
         * 自增
         */
        private boolean autoIncrement;

        /**
         * 名字
         */
        private String name;

        /**
         * 长度
         */
        private int length;

        /**
         * 可以为空?
         */
        private boolean nullable;

        /**
         * 注释
         */
        private String comment;

        /**
         * 默认值
         */
        private String defaultValue;

        /**
         * 规模
         */
        private int scale;

        /**
         * jdbc类型
         */
        private JdbcType jdbcType;

    }
}
