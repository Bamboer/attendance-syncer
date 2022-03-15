package com.dmai.attendance.syncer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableMongoAuditing
@SpringBootApplication(scanBasePackages = {"com.dmai.attendance.syncer"})
public class AttendanceSyncer {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceSyncer.class, args);
    }
}
