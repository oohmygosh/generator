package com.vipicu.maker.generator.core.api;


import com.vipicu.maker.generator.core.excetion.ApiException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

/**

 * ----------------------------------------
 * API 返回结果
 *
 * @author oohmygosh
 * @since 2021-10-28
 */
@Getter
@Setter
@Accessors(chain = true)
public class ApiResult<T> implements Serializable {
    /**
     * serialVersionUID
     */
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 业务错误码
     */
    @Schema(description = "响应码")
    private long code;
    /**
     * 结果集
     */
    @Schema(description = "结果集")
    private T data;
    /**
     * 描述
     */
    @Schema(description = "描述")
    private String message;

    public ApiResult() {
        // to do nothing
    }

    public ApiResult(IErrorCode errorCode) {
        errorCode = Optional.ofNullable(errorCode).orElse(ApiErrorCode.FAILED);
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
    }

    public static <T> ApiResult<T> ok(T data) {
        ApiErrorCode aec = ApiErrorCode.SUCCESS;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            aec = ApiErrorCode.FAILED;
        }
        return result(data, aec.getCode(), aec.getMsg());
    }

    public static <T> ApiResult<T> failed(String msg) {
        return result(null, ApiErrorCode.FAILED.getCode(), msg);
    }

    public static <T> ApiResult<T> failed(IErrorCode errorCode) {
        return result(null, errorCode);
    }

    public static <T> ApiResult<T> result(T data, IErrorCode errorCode) {
        return result(data, errorCode.getCode(), errorCode.getMsg());
    }

    private static <T> ApiResult<T> result(T data, long code, String message) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMessage(message);
        return apiResult;
    }

    public boolean ok() {
        return ApiErrorCode.SUCCESS.getCode() == code;
    }

    /**
     * 服务间调用非业务正常，异常直接释放
     */
    public T serviceData() {
        if (!ok()) {
            throw new ApiException(this.message);
        }
        return data;
    }
}
