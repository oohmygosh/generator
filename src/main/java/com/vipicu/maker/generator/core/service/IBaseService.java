package com.vipicu.maker.generator.core.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vipicu.maker.generator.core.api.ApiAssert;

import java.util.function.Supplier;

/**

 * ----------------------------------------
 * 自定义 Service 基类
 *
 * @author oohmygosh
 * @since 2021-10-28
 */
public interface IBaseService<T> extends IService<T> {

    /**
     * 校验指定条件是否存在
     *
     * @param condition 判断条件
     * @param supplier  查询条件
     * @param message   存在提示
     */
    default void checkExists(boolean condition, Supplier<LambdaQueryWrapper<T>> supplier, String message) {
        if (condition) {
            checkExists(supplier.get(), message);
        }
    }

    /**
     * 校验指定条件是否存在
     *
     * @param lqw     查询条件 LambdaQueryWrapper
     * @param message 存在提示
     */
    default void checkExists(LambdaQueryWrapper<T> lqw, String message) {
        ApiAssert.nonEmpty(getOne(lqw.last("limit 1")), message);
    }

    /**
     * 根据 ID 查询 检查数据合法性
     *
     * @param id 主键ID
     * @return {@link T}
     */
    default T checkById(Long id) {
        T t = this.getById(id);
        ApiAssert.fail(null == t, "指定ID查询数据不存在");
        return t;
    }
}
