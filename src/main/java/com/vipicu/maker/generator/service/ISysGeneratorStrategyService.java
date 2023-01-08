package com.vipicu.maker.generator.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.core.service.IBaseService;
import com.vipicu.maker.generator.entity.SysGeneratorStrategy;


/**
 * 代码生成策略 服务类
 *
 * @author oohmygosh
 * @since 2022-12-17
 */
public interface ISysGeneratorStrategyService extends IBaseService<SysGeneratorStrategy> {
    /**
     * 页面
     *
     * @param page                 页面
     * @param sysGeneratorStrategy sys发电机策略
     * @return {@link Page}<{@link SysGeneratorStrategy}>
     */
    Page<SysGeneratorStrategy> page(Page<SysGeneratorStrategy> page, SysGeneratorStrategy sysGeneratorStrategy);

}
