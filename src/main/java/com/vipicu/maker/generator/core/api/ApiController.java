package com.vipicu.maker.generator.core.api;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**

 * ----------------------------------------
 * REST API 通用控制器
 *
 * @author oohmygosh
 * @since 2021-10-28
 */
public class ApiController {
    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;

}
