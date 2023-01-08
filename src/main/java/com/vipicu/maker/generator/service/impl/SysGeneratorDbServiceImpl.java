package com.vipicu.maker.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.core.api.ApiAssert;
import com.vipicu.maker.generator.core.service.BaseServiceImpl;
import com.vipicu.maker.generator.entity.SysGeneratorDb;
import com.vipicu.maker.generator.entity.SysGeneratorStrategy;
import com.vipicu.maker.generator.entity.SysGeneratorTable;
import com.vipicu.maker.generator.enums.IDbType;
import com.vipicu.maker.generator.jdbc.DatabaseMetaDataWrapper;
import com.vipicu.maker.generator.mapper.SysGeneratorDbMapper;
import com.vipicu.maker.generator.mapper.SysGeneratorTableMapper;
import com.vipicu.maker.generator.service.ISysGeneratorDbService;
import com.vipicu.maker.generator.service.ISysGeneratorStrategyService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 代码生成目标数据库 服务实现类
 *
 * @author oohmygosh
 * @since 2022-12-14
 */
@Service
@AllArgsConstructor
public class SysGeneratorDbServiceImpl extends BaseServiceImpl<SysGeneratorDbMapper, SysGeneratorDb> implements ISysGeneratorDbService {

    private final SysGeneratorTableMapper generatorDbService;
    private final ISysGeneratorStrategyService strategyService;

    @Override
    public Page<SysGeneratorDb> page(Page<SysGeneratorDb> page, SysGeneratorDb sysGeneratorDb) {
        LambdaQueryWrapper<SysGeneratorDb> lqw = Wrappers.lambdaQuery(sysGeneratorDb);
        return super.page(page, lqw);
    }

    @Override
    public boolean updateById(SysGeneratorDb sysGeneratorDb) {
        ApiAssert.fail(null == sysGeneratorDb.getId(), "主键不存在无法更新");
        verifyDb(sysGeneratorDb);
        return super.updateById(sysGeneratorDb);
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        // 删除策略
        list.forEach(id -> strategyService.remove(Wrappers.<SysGeneratorStrategy>lambdaQuery().eq(SysGeneratorStrategy::getPid, id)));
        return super.removeByIds(list);
    }

    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysGeneratorDb entity) {
        verifyDb(entity);
        boolean save = super.save(entity);
        ApiAssert.fail(!save, "添加失败!");
        List<SysGeneratorTable> tables = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(entity.getUrl(),entity.getUsername(),entity.getPassword())) {
            DatabaseMetaDataWrapper dataWrapper = new DatabaseMetaDataWrapper(conn);
            dataWrapper.getTables(null, new String[]{"TABLE"}).forEach(t -> {
                SysGeneratorTable table = new SysGeneratorTable();
                table.setName(t.getName());
                table.setComment(t.getComment());
                table.setDbId(entity.getId());
                tables.add(table);
            });
        }
        generatorDbService.insertBatch(tables);
        strategyService.save(new SysGeneratorStrategy(entity.getId()));
        return true;
    }

    private void verifyDb(SysGeneratorDb sysGeneratorDb) {
        try (Connection connection = DriverManager.getConnection(sysGeneratorDb.getUrl(), sysGeneratorDb.getUsername(), sysGeneratorDb.getPassword())) {
            // 从 Connection 获取数据库类型并设置
            sysGeneratorDb.setDbType(IDbType.getDbType(connection.getMetaData().getDatabaseProductName().toLowerCase()));
            ApiAssert.fail( sysGeneratorDb.getDbType() == null, "目前仅支持" + IDbType.getSupported());
        } catch (SQLException e) {
            ApiAssert.fail("数据库连接失败,请检查连接参数!!");
            e.printStackTrace();
        }
    }
}
