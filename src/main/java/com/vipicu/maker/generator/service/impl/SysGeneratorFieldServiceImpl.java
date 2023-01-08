package com.vipicu.maker.generator.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.vipicu.maker.generator.core.api.ApiAssert;
import com.vipicu.maker.generator.entity.SysGeneratorDb;
import com.vipicu.maker.generator.entity.SysGeneratorField;
import com.vipicu.maker.generator.entity.SysGeneratorTable;
import com.vipicu.maker.generator.enums.IDbType;
import com.vipicu.maker.generator.enums.JdbcType;
import com.vipicu.maker.generator.jdbc.update.DbUpdateDecorator;
import com.vipicu.maker.generator.mapper.SysGeneratorFieldMapper;
import com.vipicu.maker.generator.service.ISysGeneratorFieldService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * 代码生成表字段 服务实现类
 *
 * @author oohmygosh
 * @since 2022-12-17
 */
@Service
@AllArgsConstructor
public class SysGeneratorFieldServiceImpl extends GeneratorServiceImpl<SysGeneratorFieldMapper, SysGeneratorField> implements ISysGeneratorFieldService {


    @Override
    public Page<SysGeneratorField> page(Page<SysGeneratorField> page, SysGeneratorField sysGeneratorField) {
        LambdaQueryWrapper<SysGeneratorField> lqw = Wrappers.lambdaQuery(sysGeneratorField);
        return super.page(page, lqw);
    }

    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysGeneratorField entity) {
        Map<String, String> dbAndTable = baseMapper.getDb(entity.getTableId());
        ApiAssert.fail(dbAndTable == null, "数据不存在!");
        SysGeneratorTable table = new SysGeneratorTable();
        table.copyProperty(dbAndTable);
        SysGeneratorDb db = new SysGeneratorDb();
        db.copyProperty(dbAndTable);
        assert dbAndTable != null;
        if (IDbType.POSTGRE_SQL == IDbType.getDbType(dbAndTable.get("dbType"))) {
            try (Connection connection = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword())) {
                DbUpdateDecorator decorator = new DbUpdateDecorator(connection);
                try {
                    connection.setAutoCommit(false);
                    decorator.execUpdate(decorator.addField(), table.getName(), entity.getName(), entity.getJdbcType().name() + (haveLength(entity.getJdbcType()) ? "(" + entity.getLength() + ")" : ""));
                    decorator.execUpdate(decorator.updateFiledComment(), table.getName(), entity.getName(), entity.getComment());
                    connection.commit();
                } catch (SQLException e) {
                    connection.rollback();
                }
            }
        }
        return super.save(entity);
    }

    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public List<SysGeneratorField> fetchTableField(Long id) {
        Map<String, String> dbAndTable = baseMapper.getDb(id);
        ApiAssert.fail(dbAndTable == null, "数据不存在!");
        SysGeneratorTable table = new SysGeneratorTable();
        table.copyProperty(dbAndTable);
        SysGeneratorDb db = new SysGeneratorDb();
        db.copyProperty(dbAndTable);
        return saveOrUpdateByDb(db,
                (dataWrapper -> dataWrapper.getColumnsInfo(table.getName(), true)),
                Optional.ofNullable(super.list(Wrappers.<SysGeneratorField>lambdaQuery().eq(SysGeneratorField::getTableId, id)))
                        .orElse(Collections.emptyList()), (result -> result.setTableId(id)));
    }


    public boolean haveLength(JdbcType jdbcType) {
        switch (jdbcType) {
            case VARCHAR, CHAR, BINARY, VARBINARY -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<?> list) {
        list.forEach(item -> {
            if (item instanceof Long id) {
                SysGeneratorField field = super.getById(id);
                Map<String, String> dbAndTable = getBaseMapper().getDb(field.getTableId());
                ApiAssert.fail(dbAndTable == null, "数据不存在!");
                SysGeneratorTable table = new SysGeneratorTable();
                table.copyProperty(dbAndTable);
                SysGeneratorDb db = new SysGeneratorDb();
                db.copyProperty(dbAndTable);
                try (Connection connection = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword())) {
                    DbUpdateDecorator decorator = new DbUpdateDecorator(connection);
                    try {
                        connection.setAutoCommit(false);
                        decorator.execUpdate(decorator.deletedField(), table.getName(), field.getName());
                        connection.commit();
                    } catch (Exception e) {
                        connection.rollback();
                        throw new RuntimeException(e);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return super.removeByIds(list);
    }
}
