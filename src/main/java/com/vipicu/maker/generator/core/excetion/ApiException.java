package com.vipicu.maker.generator.core.excetion;

import com.vipicu.maker.generator.core.api.IErrorCode;

import java.io.PrintWriter;
import java.io.Serial;
import java.io.StringWriter;

/**
 * REST API 请求异常类
 *
 * @author oohmygosh
 * @since 2023/01/08
 */
public class ApiException extends RuntimeException {
    /**
     * serialVersionUID
     */
    @Serial
    private static final long serialVersionUID = -5885155226898287919L;
    /**
     * 错误码
     */
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
