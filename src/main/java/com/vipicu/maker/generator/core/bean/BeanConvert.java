package com.vipicu.maker.generator.core.bean;

import com.vipicu.maker.generator.core.excetion.ApiException;
import jodd.bean.BeanCopy;

/**
 * bean转换
 *
 * @author oohmygosh
 * @since 2023/01/08
 */
public interface BeanConvert {

    /**
     * 获取自动转换后的JavaBean对象
     *
     * @param clazz 转换对象类
     * @param <T>   转换对象
     * @return T 待转换对象
     */
    default <T> T convert(Class<T> clazz) {
        try {
            T t = clazz.getDeclaredConstructor().newInstance();
            BeanCopy.beans(this, t).copy();
            return t;
        } catch (Exception e) {
            throw new ApiException("转换对象失败", e);
        }
    }

    /**
     * 复制属性
     *
     * @param o o
     */
    default void copyProperty(Object o){
        BeanCopy.beans(o,this).copy();
    }
}
