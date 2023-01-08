package com.vipicu.maker.generator.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.core.service.IBaseService;
import com.vipicu.maker.generator.entity.SysGeneratorTemplate;


/**
 * 生成器模板 服务类
 *
 * @author oohmygosh
 * @since 2022-12-19
 */
public interface ISysGeneratorTemplateService extends IBaseService<SysGeneratorTemplate> {

    /**
     * 分页查询
     *
     * @param page                 分页参数
     * @param sysGeneratorTemplate 生成器模板
     * @return {@link Page}<{@link SysGeneratorTemplate}>
     */
    Page<SysGeneratorTemplate> page(Page<SysGeneratorTemplate> page, SysGeneratorTemplate sysGeneratorTemplate);

}
