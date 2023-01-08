package com.vipicu.maker.generator.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.core.service.IBaseService;
import com.vipicu.maker.generator.entity.SysGeneratorSuperclass;


/**
 * 父类 服务类
 *
 * @author oohmygosh
 * @since 2022-12-20
 */
public interface ISysGeneratorSuperclassService extends IBaseService<SysGeneratorSuperclass> {

    /**
     * 分页查询
     *
     * @param page                   分页参数
     * @param sysGeneratorSuperclass 实体
     * @return {@link Page}<{@link SysGeneratorSuperclass}>
     */
    Page<SysGeneratorSuperclass> page(Page<SysGeneratorSuperclass> page, SysGeneratorSuperclass sysGeneratorSuperclass);

}
