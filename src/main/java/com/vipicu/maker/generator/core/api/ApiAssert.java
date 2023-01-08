package com.vipicu.maker.generator.core.api;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.vipicu.maker.generator.core.excetion.ApiException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * ----------------------------------------
 * REST API 业务断言
 * <p>参考：org.junit.Assert</p>
 *
 * @author oohmygosh
 * @since 2021-10-28
 */
public class ApiAssert {

    protected ApiAssert() {
        // to do noting
    }

    /**
     * 失败结果
     *
     * @param errorCode 异常错误码
     */
    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }

    public static void fail(boolean condition, IErrorCode errorCode) {
        if (condition) {
            fail(errorCode);
        }
    }

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(boolean condition, Supplier<String> supplier) {
        fail(condition, supplier.get());
    }

    public static void fail(boolean condition, String message) {
        if (condition) {
            fail(message);
        }
    }

    public static void isEmpty(Object obj, String message) {
        fail(ObjectUtils.isEmpty(obj), message);
    }

    public static void nonEmpty(Object obj, String message) {
        fail(!ObjectUtils.isEmpty(obj), message);
    }

    public static void equals(Object a, Object b, String message) {
        fail(Objects.equals(a, b), message);
    }

    public static void nonEquals(Object a, Object b, String message) {
        fail(!Objects.equals(a, b), message);
    }

    public static void fail(String message, MessageSource messageSource) {
        fail(messageSource.getMessage(message, null, LocaleContextHolder.getLocale()));
    }

    public static void fail(boolean condition, String message, MessageSource messageSource) {
        if (condition) {
            fail(message, messageSource);
        }
    }

    public static void fail(String message, Object[] args, MessageSource messageSource) {
        fail(messageSource.getMessage(message, args, LocaleContextHolder.getLocale()));
    }

    public static void fail(boolean condition, String message, Object[] args, MessageSource messageSource) {
        if (condition) {
            fail(message, args, messageSource);
        }
    }
}
