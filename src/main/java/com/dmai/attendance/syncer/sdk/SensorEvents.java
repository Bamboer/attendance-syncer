package com.dmai.attendance.syncer.sdk;


import com.dmai.attendance.syncer.component.AttendanceReader;
import com.dmai.attendance.syncer.component.UserCache;
import com.dmai.attendance.syncer.persistence.dao.UserAttendanceDao;
import com.dmai.attendance.syncer.persistence.entity.UserAttendance;
import com.jacob.com.Variant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Fuxin
 * @since 2019/4/26 11:04
 *
 * 实时事件，主要实时记录打卡情况
 * 当打卡时，会触发 OnAttTransactionEx 事件
 */

@Slf4j
@RequiredArgsConstructor
public class SensorEvents implements Serializable {
    public static final long serialVersionUID = -1L;

    private transient final UserAttendanceDao attendanceDao;
    private transient final UserCache cache;
    private transient final AttendanceReader reader;


    public void OnConnected(Variant[] args) {
        log.info("OnConnected====");
    }

    public void OnDisConnected(Variant[] args) {
        log.info("OnDisConnected");
    }

    public void OnAlarm(Variant[] args) {
        log.info("OnAlarm====" + args);
    }

    public long OnDoor(Variant[] args) {
        log.info("OnDoorEvent====" + args);
        if (args[0].getInt() == 4) {
            log.info("open====" + args);
        } else if (args[0].getInt() == 5) {
            log.info("close====" + args);
        } else if (args[0].getInt() == 53) {
            log.info("closing====" + args);
        } else if (args[0].getInt() == 1) {
            log.info("open error====" + args);
        }
        return 1;
    }

    public void OnAttTransactionEx(Variant[] args) {
        String dateStr = String.format("%s-%s-%s %s:%s:%s", args[4], args[5], args[6], args[7], args[8], args[9]);
        String userId = args[0].toString();
        UserRecord userRecord = cache.computeIfAbsent(userId, reader::findUserByUserId);
        String name = Objects.nonNull(userRecord) ? userRecord.getName() : "";
        log.info("验证通过事件：OnAttTransactionEx===》" + "考勤编号:" + args[0] + "---姓名：" + name + "---打卡时间/" + "考勤时间:" + dateStr);
        UserAttendance userAttendance = UserAttendance.of(userId, name, dateStr);
        attendanceDao.save(userAttendance);
    }

    public void OnEnrollFingerEx(Variant[] args) {
        log.info("OnEnrollFingerEx====" + args[0]);
    }

    public void OnFinger(Variant[] args) {
        log.info("OnFinger");
    }

    public void OnFingerFeature(Variant[] args) {
        log.info("OnFingerFeature====" + args);
    }

    public void OnHIDNum(Variant[] args) {
        log.info("OnHIDNum====" + args);
    }

    public void OnNewUser(Variant[] args) {
        log.info("OnNewUser====" + args);
    }

    public void OnVerify(Variant[] args) {
        log.info("OnVerify====" + args);
    }

    public void OnWriteCard(Variant[] args) {
        log.info("OnWriteCard====" + args);
    }

    public void OnEmptyCard(Variant[] args) {
        log.info("OnEmptyCard:" + args);
    }

    public void OnEMData(Variant[] args) {
        log.info("OnEMData:" + args);
    }

}