package com.dmai.attendance.syncer.service;

import com.dmai.attendance.syncer.api.SearchAttendanceRequest;
import com.dmai.attendance.syncer.persistence.entity.UserAttendance;

import java.util.List;

public  interface UserAttendanceService {
    List<UserAttendance>findWorkAttendance(SearchAttendanceRequest request);
}