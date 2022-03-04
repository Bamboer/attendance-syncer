package com.dmai.attendance.syncer.api;


import lombok.Getter;

/**
 * @author Fuxin
 * @since 2019/4/26 16:52
 */
@Getter
public class StatusCode {

    /** 正常响应 */
    public static final StatusCode SUCCESS = new StatusCode(200, "E0001", "SUCCESS");

    /** 无效请求 */
    public static final StatusCode INVALID_REQUEST = new StatusCode(400, "E0002", "Invalid request.");

    /** 无效 token */
    public static final StatusCode ILLEGAL_TOKEN = new StatusCode(401, "E0003", "Illegal token");

    /** 用户名和密码不正确 */
    public static final StatusCode NOT_MATCH = new StatusCode(410, "E0004", "Username or password is not correct.");

    /** 已经存在 */
    public static final StatusCode ALREADY_EXISTS = new StatusCode(411, "E0004", "Already exists.");

    /** 系统错误 */
    public static final StatusCode SYSTEM_ERROR = new StatusCode(500, "E9999", "System Error.");

    private int status;
    private String code;
    private String message;

    public StatusCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public StatusCode status(int status) {
        return new StatusCode(status, code, message);
    }


    public StatusCode message(String message) {
        return new StatusCode(status, code, message);
    }
}
