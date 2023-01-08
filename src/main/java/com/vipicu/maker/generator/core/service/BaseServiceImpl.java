package com.vipicu.maker.generator.core.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vipicu.maker.generator.core.mapper.CrudMapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * ----------------------------------------
 * 自定义 IBaseService 实现
 *
 * @author oohmygosh
 * @since 2021-10-28
 */
public class BaseServiceImpl<M extends CrudMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {

    @Override
    public List<T> listByIds(Collection<? extends Serializable> idList) {
        if (idList.isEmpty())
            return null;
        return super.listByIds(idList);
    }
}
