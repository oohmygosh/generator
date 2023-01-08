package com.vipicu.maker.generator.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * Spring 工具类
 *
 * @author oohmygosh
 * @since 2022-06-08
 */
public class SpringUtils {
    private static ApplicationContext APPLICATION_CONTEXT;

    /**
     * <p>
     * 获取当前请求
     * </p>
     */
    public static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (null == requestAttributes) {
            // 这里不能抛出异常，存在为 null 的场景
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    /**
     * <p>
     * 获取 applicationContext
     * </p>
     */
    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    /**
     * <p>
     * 设置 applicationContext
     * </p>
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        if (null == APPLICATION_CONTEXT) {
            APPLICATION_CONTEXT = applicationContext;
        }
    }

    /**
     * <p>
     * 通过class获取Bean
     * </p>
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 动态注入单例bean实例
     *
     * @param beanName        {@link ConfigurableApplicationContext}
     * @param beanName        bean名称
     * @param singletonObject 单例bean实例
     * @return 注入实例
     */
    public static Object registerSingletonBean(String beanName, Object singletonObject) {
        ApplicationContext applicationContext = getApplicationContext();
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        beanFactory.registerSingleton(beanName, singletonObject);
        return context.getBean(beanName);
    }
}
