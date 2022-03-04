package com.dmai.attendance.syncer.sdk;

import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Fuxin
 * @since 2019/4/26 10:25
 *
 * 对接考勤记录的API
 */
@Getter
@RequiredArgsConstructor
public class AttendanceStub {

    private Variant dwMachineNumber = new Variant(1);

    //用户号
    private Variant dwEnrollNumber = new Variant("", true);

    //考勤记录的验证方式
    private Variant dwVerifyMode = new Variant(-1, true);
    //考勤记录的考勤状态
    private Variant dwInOutMode = new Variant(-1, true);
    //考勤记录-年
    private Variant year = new Variant(-1, true);
    //考勤记录-月
    private Variant month = new Variant(-1, true);
    //考勤记录-日
    private Variant day = new Variant(-1, true);
    //考勤记录-时
    private Variant hour = new Variant(-1, true);
    //考勤记录-分
    private Variant minute = new Variant(-1, true);
    //考勤记录-秒
    private Variant second = new Variant(-1, true);
    //记录的 Workcode 值
    private Variant dwWorkCode = new Variant(-1, true);

    // 第一次初始数据库时使用全量，后续只同步最近一个月的记录
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Dispatch zkem;

    public boolean readCheckInfo() {
        return Dispatch.call(zkem, "SSR_GetGeneralLogData",
                dwMachineNumber,
                dwEnrollNumber,
                dwVerifyMode,
                dwInOutMode,
                year,
                month,
                day,
                hour,
                minute,
                second,
                dwWorkCode).getBoolean();
    }

    // 所有记录，全量数据量大时考勤机会长时间不返回
    public boolean readGeneralLogData() {
        return Dispatch.call(zkem, "ReadGeneralLogData", dwMachineNumber).getBoolean();
    }

    // 根据时间选择记录
    public boolean readTimeGLogData(LocalDateTime start, LocalDateTime end) {
        Variant sTime = new Variant(start.format(FORMATTER), true);
        Variant eTime = new Variant(end.format(FORMATTER), true);
        return Dispatch.call(zkem, "ReadTimeGLogData", dwMachineNumber, sTime, eTime).getBoolean();
    }
}