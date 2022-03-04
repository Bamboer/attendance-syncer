package com.dmai.attendance.syncer.sdk;

import lombok.Data;

/**
 * @author Fuxin
 * @since 2019/4/26 11:04
 */
@Data
public class AttendanceRecord {

    private String dwEnrollNumber;

    private Integer dwInOutMode;

    private String dateStr;
}
