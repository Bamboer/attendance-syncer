package com.dmai.attendance.syncer.api;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Fuxin
 * @since 2019/4/26 16:42
 */
@Builder
@Getter
public class BaseResponse<T> {

    private Integer status;

    private String message;

    private T data;

    public static BaseResponse success() {
        return buildResponse(StatusCode.SUCCESS);
    }

    public static <U> BaseResponse<U> success(U data) {
        return buildResponse(StatusCode.SUCCESS, data);
    }

    public static  BaseResponse error() {
        return buildResponse(StatusCode.SYSTEM_ERROR);
    }

    public static <U> BaseResponse<U> error(U data) {
        return buildResponse(StatusCode.SYSTEM_ERROR, data);
    }

    public static BaseResponse buildResponse(StatusCode statusCode) {
        return BaseResponse.builder()
                .status(statusCode.getStatus())
                .message(statusCode.getMessage())
                .build();
    }

    public static <U> BaseResponse<U> buildResponse(StatusCode statusCode, U data) {
        return BaseResponse.<U>builder()
                .status(statusCode.getStatus())
                .message(statusCode.getMessage())
                .data(data)
                .build();
    }

}
