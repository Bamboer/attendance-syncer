package com.dmai.attendance.syncer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableMongoAuditing
@SpringBootApplication(scanBasePackages = {"com.dmai.attendance.syncer"})
@EnableScheduling
public class WorkAttendanceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkAttendanceApplication.class, args);
    }
}