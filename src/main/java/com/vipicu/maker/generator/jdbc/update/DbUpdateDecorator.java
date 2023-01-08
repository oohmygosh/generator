package com.vipicu.maker.generator.jdbc.update;

import com.baomidou.mybatisplus.core.toolkit.sql.SqlInjectionUtils;


import com.vipicu.maker.generator.core.excetion.ApiException;
import com.vipicu.maker.generator.enums.IDbType;
import com.vipicu.maker.generator.jdbc.DatabaseMetaDataWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

/**
 * 数据库更新装饰
 *
 * @author oohmygosh
 * @since 2022/12/23
 */
@Slf4j
public class DbUpdateDecorator extends AbstractDbUpdate {

    private final DataBaseUpdate dataBaseUpdate;
    private final Connection connection;
    private final IDbType dbType;
    private DatabaseMetaDataWrapper metaDataWrapper;

    @SneakyThrows
    public DbUpdateDecorator(Connection connection) {
        this.connection = connection;
        this.dataBaseUpdate = Optional.ofNullable(DbUpdateRegistry.getDbUpdate(IDbType.getDbType(connection.getMetaData().getDatabaseProductName()))).orElseThrow(() -> new ApiException("暂不支持该数据库!"));
        this.dbType = IDbType.getDbType(connection.getMetaData().getDatabaseProductName());
    }

    public DatabaseMetaDataWrapper getMetaDataWrapper() {
        if (metaDataWrapper == null)
            metaDataWrapper = new DatabaseMetaDataWrapper(connection);
        return metaDataWrapper;
    }

    public void execUpdate(String sql, Object... param) throws SQLException {
        for (Object str : param) {
            if (str != null && SqlInjectionUtils.check(str.toString())) {
                System.out.println("异常参数!" + str);
            }
        }
        sql = sql.formatted(param);
        log.info("执行sql:" + sql);
        System.out.println("执行sql:" + sql);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("执行sql{},异常{}", sql, e);
            throw new SQLException(e);
        }
    }

    public void updateFieldPrimary(String tableName, String fieldName, boolean primaryKey) {
        DatabaseMetaDataWrapper databaseMetaDataWrapper = getMetaDataWrapper();
        Map<String, DatabaseMetaDataWrapper.Column> columnsInfo = databaseMetaDataWrapper.getColumnsInfo(tableName, true);
        if (primaryKey && columnsInfo.get(fieldName).isPrimaryKey()) return;
        if ((primaryKey || columnsInfo.get(fieldName).isPrimaryKey())  && dbType == IDbType.POSTGRE_SQL) {
            Optional.ofNullable(databaseMetaDataWrapper.getPkName(tableName)).ifPresent(pkName -> {
                try {
                    execUpdate(deleteFieldPrimaryKey(), tableName, pkName);
                } catch (SQLException e) {
                    log.error("删除{}主键失败!", tableName);
                }
            });
            if (!columnsInfo.get(fieldName).isPrimaryKey())
                try {
                    execUpdate(addFieldPrimaryKey(), tableName, tableName, fieldName);
                } catch (SQLException e) {
                    log.error("添加{}主键失败", tableName);
                }
        }
    }

    public void updateFieldType(String tableName, String fieldName, String type) throws SQLException {
        if (dbType == IDbType.POSTGRE_SQL) {
            execUpdate(updateFieldType(), tableName, fieldName, type, fieldName, type);
        }
    }

    public void updateFieldDefault(String tableName, String fieldName, String defaultValue) throws SQLException {
        DatabaseMetaDataWrapper metaDataWrapper = getMetaDataWrapper();
        if (dbType == IDbType.POSTGRE_SQL) {
            Map<String, DatabaseMetaDataWrapper.Column> columnsInfo = metaDataWrapper.getColumnsInfo(tableName, false);
            Optional.ofNullable(columnsInfo.get(fieldName)).ifPresent(column -> {
                if (StringUtils.hasLength(column.getDefaultValue())) {
                    try {
                        execUpdate(deleteFieldDefaultValue(), tableName, fieldName);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            if (StringUtils.hasLength(defaultValue))
                execUpdate(updateFieldDefaultValue(), tableName, fieldName, defaultValue);
        }
    }

    @Override
    public String addField() {
        return dataBaseUpdate.addField();
    }

    @Override
    public String addTable() {
        return dataBaseUpdate.addTable();
    }

    @Override
    public String updateTableName() {
        return dataBaseUpdate.updateTableName();
    }

    @Override
    public String updateTableComment() {
        return dataBaseUpdate.updateTableComment();
    }

    @Override
    public String updateFieldName() {
        return dataBaseUpdate.updateFieldName();
    }

    @Override
    public String updateFiledComment() {
        return dataBaseUpdate.updateFiledComment();
    }

    @Override
    public String updateFieldType() {
        return dataBaseUpdate.updateFieldType();
    }

    @Override
    public String updateFieldDefaultValue() {
        return dataBaseUpdate.updateFieldDefaultValue();
    }

    @Override
    public String addFieldPrimaryKey() {
        return dataBaseUpdate.addFieldPrimaryKey();
    }

    @Override
    public String deleteFieldPrimaryKey() {
        return dataBaseUpdate.deleteFieldPrimaryKey();
    }

    @Override
    public String updateFieldIncrement() {
        return dataBaseUpdate.updateFieldIncrement();
    }

    @Override
    public String updateFieldNullable(boolean flag) {
        return dataBaseUpdate.updateFieldNullable(flag);
    }

    @Override
    public String deleteFieldDefaultValue() {
        return dataBaseUpdate.deleteFieldDefaultValue();
    }
}
