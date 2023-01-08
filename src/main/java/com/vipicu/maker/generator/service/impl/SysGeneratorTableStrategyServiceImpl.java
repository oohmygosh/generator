package com.vipicu.maker.generator.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.core.api.ApiAssert;
import com.vipicu.maker.generator.core.service.BaseServiceImpl;
import com.vipicu.maker.generator.entity.TableStrategy;
import com.vipicu.maker.generator.mapper.SysGeneratorTableStrategyMapper;
import com.vipicu.maker.generator.service.ISysGeneratorTableStrategyService;
import org.springframework.stereotype.Service;

/**
 * 表生成策略 服务实现类
 *
 * @author oohmygosh
 * @since 2022-12-19
 */
@Service
public class SysGeneratorTableStrategyServiceImpl extends BaseServiceImpl<SysGeneratorTableStrategyMapper, TableStrategy> implements ISysGeneratorTableStrategyService {

    @Override
    public Page<TableStrategy> page(Page<TableStrategy> page, TableStrategy sysGeneratorTableStrategy) {
        LambdaQueryWrapper<TableStrategy> lqw = Wrappers.lambdaQuery(sysGeneratorTableStrategy);
        return super.page(page, lqw);
    }

    @Override
    public boolean updateById(TableStrategy sysGeneratorTableStrategy) {
        ApiAssert.fail(null == sysGeneratorTableStrategy.getId(), "主键不存在无法更新");
        return super.updateById(sysGeneratorTableStrategy);
    }
}
