package com.vipicu.maker.generator.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.core.service.IBaseService;
import com.vipicu.maker.generator.entity.SysGeneratorField;

import java.util.List;

/**
 * 代码生成表字段 服务类
 *
 * @author oohmygosh
 * @since 2022-12-17
 */
public interface ISysGeneratorFieldService extends IBaseService<SysGeneratorField> {

    /**
     * 分页查询
     *
     * @param page              分页参数
     * @param sysGeneratorField entity
     * @return {@link Page}<{@link SysGeneratorField}>
     */
    Page<SysGeneratorField> page(Page<SysGeneratorField> page, SysGeneratorField sysGeneratorField);

    /**
     * 获取表字段
     *
     * @param id id
     * @return {@link List}<{@link SysGeneratorField}>
     */
    List<SysGeneratorField> fetchTableField(Long id);
}
