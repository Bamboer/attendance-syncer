package com.dmai.attendance.syncer.api;

import lombok.Data;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Fuxin
 * @since 2019/4/26 18:16
 */
@Data
public class
SearchAttendanceRequest {

    private LocalDate fromDate;

    private LocalDate toDate;

    private String name;

    private String department;

    private String subDepartment;

    /**
     * 调整请求日期参数
     *
     * @return 调整好的日期
     */
    public ImmutablePair<LocalDate, LocalDate> checkAndGetDate() {
        // 调整请求参数
        LocalDate fromDate = Optional.ofNullable(this.fromDate)
                .orElseGet(() -> LocalDate.now().minusMonths(1).withDayOfMonth(1));
        // 不能小于开始日期
        //不能大于当前日期
        LocalDate toDate = Optional.ofNullable(this.toDate)
                .filter(date -> date.isAfter(fromDate) || date.isEqual(fromDate))
                .filter(date -> !date.isAfter(LocalDate.now()))
                .orElseGet(LocalDate::now);
        return ImmutablePair.of(fromDate, toDate);
    }

    /**
     * 调整请求日期参数
     *
     * @return 调整好的日期
     */
    public ImmutablePair<LocalDate, LocalDate> checkAndSetThisMonth() {
        // 调整请求参数
        LocalDate fromDate = Optional.ofNullable(this.fromDate)
                .orElseGet(() -> LocalDate.now().withDayOfMonth(1));
        // 不能小于开始日期
        //不能大于当前日期
        LocalDate toDate = Optional.ofNullable(this.toDate)
                .filter(date -> date.isAfter(fromDate) || date.isEqual(fromDate))
                .filter(date -> !date.isAfter(LocalDate.now())) //不能大于当前日期
                .orElseGet(LocalDate::now);
        setFromDate(fromDate);
        setToDate(toDate);
        return ImmutablePair.of(fromDate, toDate);
    }

}
