package com.dmai.attendance.syncer.controller;

import com.dmai.attendance.syncer.api.BaseResponse;
import com.dmai.attendance.syncer.api.SearchAttendanceRequest;
import com.dmai.attendance.syncer.component.AttendanceReader;
import com.dmai.attendance.syncer.config.ServerProperties;
import com.dmai.attendance.syncer.service.UserAttendanceService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceReader reader;
    private final UserAttendanceService attendanceService;

    @PostConstruct
    private void postConstruct() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(reader::readAllAndSave, 5, TimeUnit.SECONDS);
    }

    @Ignore
    @RequestMapping("/")
    public String index() {
        return "Hello!  Attendance syncer !";
    }

    @PostMapping("/api/attendance")
    public BaseResponse<?> searchAttendance(@RequestBody SearchAttendanceRequest request) {
        return BaseResponse.success(attendanceService.findWorkAttendance(request));
    }
}
