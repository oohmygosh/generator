package com.vipicu.maker.generator.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.core.api.ApiAssert;
import com.vipicu.maker.generator.core.service.BaseServiceImpl;
import com.vipicu.maker.generator.entity.SysGeneratorStrategy;
import com.vipicu.maker.generator.mapper.SysGeneratorStrategyMapper;
import com.vipicu.maker.generator.service.ISysGeneratorStrategyService;
import org.springframework.stereotype.Service;

/**
 * 代码生成策略 服务实现类
 *
 * @author oohmygosh
 * @since 2022-12-17
 */
@Service
public class SysGeneratorStrategyServiceImpl extends BaseServiceImpl<SysGeneratorStrategyMapper, SysGeneratorStrategy> implements ISysGeneratorStrategyService {
    @Override
    public Page<SysGeneratorStrategy> page(Page<SysGeneratorStrategy> page, SysGeneratorStrategy sysGeneratorStrategy) {
        LambdaQueryWrapper<SysGeneratorStrategy> lqw = Wrappers.lambdaQuery(sysGeneratorStrategy);
        return super.page(page, lqw);
    }

    @Override
    public boolean updateById(SysGeneratorStrategy sysGeneratorStrategy) {
        ApiAssert.fail(null == sysGeneratorStrategy.getId(), "主键不存在无法更新");
        return super.updateById(sysGeneratorStrategy);
    }
}
