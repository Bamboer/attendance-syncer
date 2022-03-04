package com.dmai.attendance.syncer.sdk;

import lombok.Data;

/**
 * 对应于考勤机里面的用户信息
 *
 * @author Fuxin
 * @since 2019/4/26 11:15
 */
@Data
public class UserRecord {
    private String dwEnrollNumber;
    private String name;
    private boolean enabled;
}
