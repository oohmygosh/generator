package com.vipicu.maker.generator.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.core.service.IBaseService;
import com.vipicu.maker.generator.entity.SysGeneratorDb;

/**
 * 代码生成目标数据库 服务类
 *
 * @author oohmygosh
 * @since 2022-12-14
 */
public interface ISysGeneratorDbService extends IBaseService<SysGeneratorDb> {

    /**
     * 页面
     *
     * @param page           页面
     * @param sysGeneratorDb sys发电机db
     * @return {@link Page}<{@link SysGeneratorDb}>
     */
    Page<SysGeneratorDb> page(Page<SysGeneratorDb> page, SysGeneratorDb sysGeneratorDb);


}
