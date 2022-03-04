package com.dmai.attendance.syncer.task;

import com.dmai.attendance.syncer.component.AttendanceReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class AttendanceRecordTask {

    private final AttendanceReader reader;

    @Scheduled(cron = "${dmai.attendance.record-cron-expression}")
    public void syncData() {
        try {
            log.info("Starting sync attendance records...");
            LocalDateTime end = LocalDateTime.now();
            LocalDateTime start = end.minusDays(1);
            reader.syncAllRecords(start, end);
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
    }
}
