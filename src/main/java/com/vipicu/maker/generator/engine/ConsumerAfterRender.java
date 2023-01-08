package com.vipicu.maker.generator.engine;


import com.vipicu.maker.generator.entity.po.RenderResult;

import java.util.List;

/**
 * 渲染后消费
 *
 * @author oohmygosh
 * @since 2022/12/21
 */
@FunctionalInterface
public interface ConsumerAfterRender {
    /**
     * 消费者
     *
     * @param result 结果
     */
    void consumer(List<RenderResult> result);
}
