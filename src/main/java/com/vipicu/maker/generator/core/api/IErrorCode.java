package com.vipicu.maker.generator.core.api;

/**

 * ----------------------------------------
 * REST API 错误码接口
 *
 * @author oohmygosh
 * @since 2021-10-28
 */
public interface IErrorCode {
    /**
     * 错误编码 -1、失败 0、成功
     *
     * @return long
     */
    long getCode();

    /**
     * 错误描述
     *
     * @return {@link String}
     */
    String getMsg();
}
