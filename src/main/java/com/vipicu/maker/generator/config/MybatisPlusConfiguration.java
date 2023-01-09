package com.vipicu.maker.generator.config;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.vipicu.maker.generator.core.mapper.MakerSqlInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

/**

 * MybatisPlus配置
 *
 * @author oohmygosh
 * @since 2021-10-28
 */
@Lazy
@EnableTransactionManagement
@Configuration(proxyBeanMethods = false)
public class MybatisPlusConfiguration {

    /**
     * mybatis-plus 插件<br>
     * 文档：<a href="http://baomidou.com">http://baomidou.com</a>
     */
    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor mybatisPlusInterceptor(@Autowired(required = false) List<InnerInterceptor> innerInterceptorList) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        if (CollectionUtils.isNotEmpty(innerInterceptorList)) {
            // 注入自定义插件
            innerInterceptorList.forEach(interceptor::addInnerInterceptor);
        }
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
        return interceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public MakerSqlInjector batchSqlInjector() {
        return new MakerSqlInjector();
    }

    @Bean
    @ConditionalOnMissingBean
    public MakerMetaObjectHandler metaObjectHandler() {
        return new MakerMetaObjectHandler();
    }

}
