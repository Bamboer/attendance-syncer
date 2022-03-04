package com.dmai.attendance.syncer.api;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;

/**
 * @author Fuxin
 * @since 2019/4/28 14:20
 */
@Builder
@Getter
public class WorkAttendance {

    private String attendanceUserId;

    private String name;

    public Map<String, ClockInOutTime> clockInOutDate;

    @Data
    public static class ClockInOutTime {

        /**
         * 上班打卡日期
         */
        private String arriveDate;

        /**
         * 上班打卡时间
         */
        private String arriveTime;

        /**
         * 有效上班打卡时间
         */
        private String arriveTimeValid;

        /**
         * 下班打卡时间
         */
        private String leaveDate;

        /**
         * 下班打卡时间
         */
        private String leaveTime;

        /**
         * 早退时长
         */
        private String leaveEarlyTime;

        /**
         * 早退分钟数
         */
        private Long leaveEarlyMinutes;

        /**
         * 迟到时长
         */
        private String arriveLateTime;

        /**
         * 迟到分钟数
         */
        private Long arriveLateMinutes;

        /**
         * 工作时长
         */
        private String workingTime;

        /**
         * 工作分钟数
         */
        private Long workingMinutes;

        /**
         * 实际工作时长
         */
        private String actualWorkingTime;

        /**
         * 实际工作分钟数
         */
        private Long actualWorkingMinutes;

        /**
         * 加班时长
         */
        private String workOvertime;

        /**
         * 加班分钟数
         */
        private Long workOvertimeMinutes;

        /**
         * 上班补卡
         */
        private Boolean arrivePatched = Boolean.FALSE;

        /**
         * 下班补卡
         */
        private Boolean leavePatched = Boolean.FALSE;

        /**
         * 星期几
         */
        private String weekDayStr;

        /**
         * 是否工作日
         */
        private boolean workingDay;

        /**
         * 请假时长，天
         */
        private double approvalLeaveTime;

        /**
         * 出差时长，天
         */
        private double approvalBusinessTime;

        /**
         * 外出时长，小时
         */
        private double approvalOuterTime;

        private String approvalStartTime;

        private String approvalEndTime;

        private String approvalTypeName;

        private String approvalSubtypeName;

        private AttendanceType attendanceType = AttendanceType.NORMAL;

        private String attendanceTypeName;

        public String getAttendanceTypeName() {
            return Objects.nonNull(attendanceTypeName)
                    ? attendanceTypeName
                    : attendanceType.getName();
        }
    }

    public enum AttendanceType {
        LEAVE("请假"),
        BUSINESS("出差"),
        OUTER("外出"),
        LEAVE_EARLY("早退"),
        ARRIVE_LATE("迟到"),
        MISSING_CARD("缺卡"),
        ABSENTEEISM("旷工"),
        REST("休息"),
        NORMAL("正常"),
        TODAY("今天"),
        OVERTIME("加班");
        private String name;

        AttendanceType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
