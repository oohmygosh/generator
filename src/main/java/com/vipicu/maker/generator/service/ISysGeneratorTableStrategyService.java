package com.vipicu.maker.generator.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vipicu.maker.generator.core.service.IBaseService;
import com.vipicu.maker.generator.entity.TableStrategy;


/**
 * 表生成策略 服务类
 *
 * @author oohmygosh
 * @since 2022-12-19
 */
public interface ISysGeneratorTableStrategyService extends IBaseService<TableStrategy> {

        /**
         * 分页查询
         *
         * @param page                      配置
         * @param sysGeneratorTableStrategy 实体
         * @return {@link Page}<{@link TableStrategy}>
         */
        Page<TableStrategy> page(Page<TableStrategy> page, TableStrategy sysGeneratorTableStrategy);

}
