package com.vipicu.maker.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.vipicu.maker.generator.core.api.ApiAssert;
import com.vipicu.maker.generator.core.service.BaseServiceImpl;
import com.vipicu.maker.generator.entity.SysGeneratorTemplate;
import com.vipicu.maker.generator.mapper.SysGeneratorTemplateMapper;
import com.vipicu.maker.generator.service.ISysGeneratorTemplateService;
import org.springframework.stereotype.Service;

/**
 * 生成器模板 服务实现类
 *
 * @author oohmygosh
 * @since 2022-12-19
 */
@Service
public class SysGeneratorTemplateServiceImpl extends BaseServiceImpl<SysGeneratorTemplateMapper, SysGeneratorTemplate> implements ISysGeneratorTemplateService {

    @Override
    public Page<SysGeneratorTemplate> page(Page<SysGeneratorTemplate> page, SysGeneratorTemplate sysGeneratorTemplate) {
        LambdaQueryWrapper<SysGeneratorTemplate> lqw = Wrappers.lambdaQuery(sysGeneratorTemplate);
        return super.page(page, lqw);
    }

    @Override
    public boolean updateById(SysGeneratorTemplate sysGeneratorTemplate) {
        ApiAssert.fail(null == sysGeneratorTemplate.getId(), "主键不存在无法更新");
        return super.updateById(sysGeneratorTemplate);
    }
}
