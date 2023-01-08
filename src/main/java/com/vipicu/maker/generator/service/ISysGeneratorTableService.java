package com.vipicu.maker.generator.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.core.service.IBaseService;
import com.vipicu.maker.generator.entity.SysGeneratorTable;


import java.util.List;

/**
 * 代码生成表资源 服务类
 *
 * @author oohmygosh
 * @since 2022-12-16
 */
public interface ISysGeneratorTableService extends IBaseService<SysGeneratorTable> {

    /**
     * 页面
     *
     * @param page              页面
     * @param sysGeneratorTable sys发电机表
     * @return {@link Page}<{@link SysGeneratorTable}>
     */
    Page<SysGeneratorTable> page(Page<SysGeneratorTable> page, SysGeneratorTable sysGeneratorTable);

    /**
     * 获取所有表
     *
     * @param dbId db id
     * @return {@link List}<{@link SysGeneratorTable}>
     */
    List<SysGeneratorTable> fetchAllTable(Long dbId);

}
