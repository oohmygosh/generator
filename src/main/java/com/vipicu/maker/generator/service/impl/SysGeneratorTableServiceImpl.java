package com.vipicu.maker.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.core.api.ApiAssert;
import com.vipicu.maker.generator.entity.SysGeneratorDb;
import com.vipicu.maker.generator.entity.SysGeneratorTable;
import com.vipicu.maker.generator.jdbc.DatabaseMetaDataWrapper;
import com.vipicu.maker.generator.jdbc.update.DbUpdateDecorator;
import com.vipicu.maker.generator.mapper.SysGeneratorTableMapper;
import com.vipicu.maker.generator.service.ISysGeneratorDbService;
import com.vipicu.maker.generator.service.ISysGeneratorTableService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 代码生成表资源 服务实现类
 *
 * @author oohmygosh
 * @since 2022-12-16
 */
@Service
@AllArgsConstructor
public class SysGeneratorTableServiceImpl extends GeneratorServiceImpl<SysGeneratorTableMapper, SysGeneratorTable> implements ISysGeneratorTableService {

    private final ISysGeneratorDbService generatorDbService;

    @Override
    public Page<SysGeneratorTable> page(Page<SysGeneratorTable> page, SysGeneratorTable sysGeneratorTable) {
        LambdaQueryWrapper<SysGeneratorTable> lqw = Wrappers.lambdaQuery(sysGeneratorTable);
        return super.page(page, lqw);
    }

    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public List<SysGeneratorTable> fetchAllTable(Long dbId) {
        SysGeneratorDb db = generatorDbService.getById(dbId);
        if (db == null)
            return null;
        List<SysGeneratorTable> sysGeneratorTables = super.list(Wrappers.<SysGeneratorTable>lambdaQuery().eq(SysGeneratorTable::getDbId, dbId));
        return saveOrUpdateByDb(
                db, (dataWrapper -> dataWrapper.getTables(null, new String[]{"TABLE"}).stream().collect(Collectors.toMap(DatabaseMetaDataWrapper.Table::getName, v -> v, (k1, k2) -> k1, TreeMap::new))),
                sysGeneratorTables, (table -> table.setDbId(dbId)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<?> list) {
        ApiAssert.fail(list == null || list.isEmpty(), "参数异常!");
        assert list != null;
        list.forEach(id -> {
            if (id instanceof Long i) {
                SysGeneratorDb db = baseMapper.getDbByTableId(i);
                try (Connection connection = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword())) {
                    connection.setAutoCommit(false);
                    DbUpdateDecorator decorator = new DbUpdateDecorator(connection);
                    try {
                        decorator.execUpdate(decorator.deleteTable(), super.getById(i).getName());
                        connection.commit();
                    }catch (Exception e){
                        connection.rollback();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
        return super.removeByIds(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysGeneratorTable entity) {
        ApiAssert.fail(null == entity.getDbId(), "参数异常!");
        SysGeneratorDb db = generatorDbService.getById(entity.getDbId());
        try (Connection connection = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword())) {
            connection.setAutoCommit(false);
            DbUpdateDecorator decorator = new DbUpdateDecorator(connection);
            try {
                decorator.execUpdate(decorator.addTable(), entity.getName());
                decorator.execUpdate(decorator.updateTableComment(), entity.getName(), entity.getComment());
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                ApiAssert.fail("添加失败!");
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return super.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(SysGeneratorTable sysGeneratorTable) {
        ApiAssert.fail(null == sysGeneratorTable.getId() || sysGeneratorTable.getDbId() == null, "表或数据源不存在无法更新");
        SysGeneratorDb db = generatorDbService.getById(sysGeneratorTable.getDbId());
        SysGeneratorTable old = super.getById(sysGeneratorTable.getId());
        try (Connection connection = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword())) {
            connection.setAutoCommit(false);
            DbUpdateDecorator decorator = new DbUpdateDecorator(connection);
            try {
                if (!old.getName().equalsIgnoreCase(sysGeneratorTable.getName()))
                    decorator.execUpdate(decorator.updateTableName(), old.getName(), sysGeneratorTable.getName());
                decorator.execUpdate(decorator.updateTableComment(), sysGeneratorTable.getName(), sysGeneratorTable.getComment());
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                ApiAssert.fail("修改失败!!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return super.updateById(sysGeneratorTable);
    }
}
