package com.dmai.attendance.syncer.service.impl;

import com.dmai.attendance.syncer.api.SearchAttendanceRequest;
import com.dmai.attendance.syncer.persistence.dao.UserAttendanceDao;
import com.dmai.attendance.syncer.persistence.entity.UserAttendance;
import com.dmai.attendance.syncer.service.UserAttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.*;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserAttendanceServiceImpl implements UserAttendanceService {

    /**
     * 凌晨 5 点之前被认定是昨天的离开时间
     */
    private static final LocalTime MIN_WORK_TIME = LocalTime.of(5, 0);
    private final UserAttendanceDao attendanceDao;

    @Override
    public List<UserAttendance> findWorkAttendance(SearchAttendanceRequest request){
        ImmutablePair<LocalDate, LocalDate> pair = request.checkAndGetDate();
        log.info("check date :{}",pair);
        log.info("request info :{}",request);
        LocalDateTime fromDateTime = pair.getLeft().atTime(MIN_WORK_TIME);
        LocalDateTime toDateTime = pair.getRight().plusDays(1).atTime(MIN_WORK_TIME);
        List<UserAttendance> attendances = Optional.ofNullable(request.getName())
                .map(name -> attendanceDao.queryByDateTimeBetweenAndName(fromDateTime, toDateTime, name))
                .orElseGet(() -> attendanceDao.queryByDateTimeBetween(fromDateTime, toDateTime));
        log.info("check result: {}",attendances);
        return  attendances ;
    }
}
