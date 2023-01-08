package com.vipicu.maker.generator.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.core.api.ApiAssert;
import com.vipicu.maker.generator.core.service.BaseServiceImpl;
import com.vipicu.maker.generator.entity.SysGeneratorSuperclass;
import com.vipicu.maker.generator.mapper.SysGeneratorSuperclassMapper;
import com.vipicu.maker.generator.service.ISysGeneratorSuperclassService;
import org.springframework.stereotype.Service;

/**
 * 父类 服务实现类
 *
 * @author oohmygosh
 * @since 2022-12-20
 */
@Service
public class SysGeneratorSuperclassServiceImpl extends BaseServiceImpl<SysGeneratorSuperclassMapper, SysGeneratorSuperclass> implements ISysGeneratorSuperclassService {

    @Override
    public Page<SysGeneratorSuperclass> page(Page<SysGeneratorSuperclass> page, SysGeneratorSuperclass sysGeneratorSuperclass) {
        LambdaQueryWrapper<SysGeneratorSuperclass> lqw = Wrappers.lambdaQuery(sysGeneratorSuperclass);
        return super.page(page, lqw);
    }

    @Override
    public boolean updateById(SysGeneratorSuperclass sysGeneratorSuperclass) {
        ApiAssert.fail(null == sysGeneratorSuperclass.getId(), "主键不存在无法更新");
        return super.updateById(sysGeneratorSuperclass);
    }
}
