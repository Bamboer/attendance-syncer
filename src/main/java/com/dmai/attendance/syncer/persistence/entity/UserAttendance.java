package com.dmai.attendance.syncer.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Data
@Document(collection = "user_attendance")
public class UserAttendance implements Serializable {
    private  static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-M-D H:m:s");
    private  static final ZoneOffset ZONE_OFFSET = ZoneOffset.of("+8");

    @Id
    private String id;
    private String userId;
    private String name;
    private LocalDateTime dateTime;
    private  Type type = Type.SYSTEM;
    /** 补签原因，补签的记录才有值 */
    private String reason;

    // id 有值时，这个字段就不会自动生成
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public enum Type {
        SYSTEM, // 考勤机的打卡记录
        PATCH, // 补卡记录
    }

    public static UserAttendance of(String userId, String name, String dateStr) {
        UserAttendance userAttendance = new UserAttendance();
        LocalDateTime dateTime = LocalDateTime.from(FORMATTER.parse(dateStr));
        userAttendance.setName(name);
        userAttendance.setUserId(userId);
        userAttendance.setDateTime(dateTime);
        userAttendance.buildAndSetId();
        return userAttendance;
    }

    public void buildAndSetId() {
        this.id = userId + dateTime.toInstant(ZONE_OFFSET).toEpochMilli();
    }

}
