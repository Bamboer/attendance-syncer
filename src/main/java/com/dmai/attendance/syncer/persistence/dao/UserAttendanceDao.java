package com.dmai.attendance.syncer.persistence.dao;


import com.dmai.attendance.syncer.persistence.entity.UserAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserAttendanceDao extends MongoRepository<UserAttendance,String> {
    List<UserAttendance> queryAllByName(String name);

    List<UserAttendance> queryByDateTimeBetween(LocalDateTime before, LocalDateTime after);

    List<UserAttendance> queryByDateTimeBetweenAndName(LocalDateTime before, LocalDateTime after, String name);

    Page<UserAttendance> queryByName(String name, Pageable pageable);
}
