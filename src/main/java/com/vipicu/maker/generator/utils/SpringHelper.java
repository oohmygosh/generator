package com.vipicu.maker.generator.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**

 * ----------------------------------------
 * Spring Boot 相关辅助类
 *
 * @author oohmygosh
 * @since 2021-10-28
 */
public class SpringHelper {
    private static ApplicationContext APPLICATION_CONTEXT;

    /**
     * 获取当前请求
     */
    public static HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (null == requestAttributes) {
            // 这里不能抛出异常，存在为 null 的场景
            return null;
        }
        return  ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    /**
     * 获取 applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    /**
     * 设置 applicationContext
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        if (null == APPLICATION_CONTEXT) {
            APPLICATION_CONTEXT = applicationContext;
        }
    }

    /**
     * 通过class获取Bean
     *
     * @param clazz clazz
     * @return {@link T}
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
