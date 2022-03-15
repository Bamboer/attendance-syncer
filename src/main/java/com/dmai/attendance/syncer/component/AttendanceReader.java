package com.dmai.attendance.syncer.component;

import com.dmai.attendance.syncer.config.ServerProperties;
import com.dmai.attendance.syncer.persistence.dao.UserAttendanceDao;
import com.dmai.attendance.syncer.persistence.entity.UserAttendance;
import com.dmai.attendance.syncer.sdk.*;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;




/**
 * @author Fuxin
 * @since 2019/4/26 10:51
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AttendanceReader {

    private static final Executor EXECUTOR = Executors.newCachedThreadPool();

    private final UserAttendanceDao attendanceDao;
    private final UserCache cache;
    private final ServerProperties properties;

    public void readAllAndSave() {
        start(properties.getHosts());
    }

    private void start(List<String> hostList) {
        log.info("Connecting host: {}", hostList);
        hostList.forEach(ip -> CompletableFuture.runAsync(() -> {
            try {
                //接收事件处理
                SensorEvents events = new SensorEvents(attendanceDao, cache, this);
                Dispatch zkem = createZkemKeeper(ip, dispatch ->  new DispatchEvents(dispatch, events, dispatch.getProgramId()));
                readAndSave(zkem, ip);
                //注册事件， 实时记录考勤记录
                Dispatch.call(zkem, "RegEvent", new Variant(1L), new Variant(1));
                STA sta = new SubSTA();
                sta.doMessagePump();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }, EXECUTOR));
    }

    public UserRecord findUserByUserId(String userId) {
        UserRecord userRecord = null;
        for (String host : properties.getHosts()) {
            Dispatch zkem = createZkemKeeper(host);
            userRecord = findUserByUserId(zkem, userId);
            disconnect(zkem);
            if (Objects.nonNull(userRecord)) break;
        }
        return userRecord;
    }

    public UserRecord findUserByUserId(Dispatch zkem, String userId) {
        UserInfoStub userInfoStub = new UserInfoStub(zkem);
        if (userInfoStub.fetchUserInfoById(userId)) {
            String name = userInfoStub.Name.toString();
            if (!"".equals(name)) {
                return buildUserRecord(userInfoStub, name);
            }
        }
        return null;
    }

    private UserRecord buildUserRecord(UserInfoStub userInfoStub, String name) {
        UserRecord userRecord = new UserRecord();
        String finalName = name.indexOf("\u0000") > 0 ? name.substring(0, name.indexOf("\u0000")) : name;
        userRecord.setDwEnrollNumber(userInfoStub.getDwEnrollNumber().toString());
        userRecord.setName(finalName);
        userRecord.setEnabled(userInfoStub.getEnabled().getBooleanRef());
        return userRecord;
    }

    private Dispatch createZkemKeeper(String ip) {
        return createZkemKeeper(ip, Function.identity());
    }

    private <R> Dispatch createZkemKeeper(String ip, Function<Dispatch, R> event) {
        log.info("Staring to connect {}", ip);
        // 如果初始化的时候找不到 jre 下面的 jacob.dll 会卡住
//        LibraryLoader.loadJacobLibrary();
        ComThread.InitMTA();
        log.info("InitMTA end.");
        // https://www.javaquery.com/2013/12/comjacobcomcomfailexception-cant-get.html，考虑解决 Can't get object clsid from progid 的问题
        // TODO 使用配置文件配置
        String clsid = "clsid:{00853A19-BD51-419B-9269-2DABE57EB61F}";
        Dispatch zkem = new ActiveXComponent(clsid);
        //接收事件处理
        event.apply(zkem);
        //connect
        boolean b = connect(zkem, ip);
        log.info("已经连接 ip: {}, 是否成功: {}", ip, b);
        return zkem;
    }

    private void readAndSave(Dispatch zkem, String ip) {
        UserInfoStub userInfoStub = new UserInfoStub(zkem);
        initUserRecord(userInfoStub);
        boolean syncAll = Boolean.valueOf(System.getProperty("attendance.sync.all"));
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusDays(syncAll ? 180 : 7);
        syncAttendanceRecords(zkem, ip, start, end);
    }

    private void initUserRecord(UserInfoStub userInfoStub) {
        if (userInfoStub.readAllUserID()) {
            while (userInfoStub.fetchALlUserInfo()) {
                String name = userInfoStub.getName().toString();
                UserRecord userRecord = buildUserRecord(userInfoStub, name);
                cache.put(userRecord.getDwEnrollNumber(), userRecord);
            }
        }
        log.info("Sync total users: " + cache.size());
    }

    public void initUserRecord() {
        for (String host : properties.getHosts()) {
            Dispatch zkem = createZkemKeeper(host);
            UserInfoStub userInfoStub = new UserInfoStub(zkem);
            initUserRecord(userInfoStub);
            disconnect(zkem);
        }
    }

    public void syncAllRecords(LocalDateTime start, LocalDateTime end) {
        for (String host : properties.getHosts()) {
            Dispatch zkem = createZkemKeeper(host);
            syncAttendanceRecords(zkem, host, start, end);
            disconnect(zkem);
        }
    }

    public void syncAttendanceRecords(Dispatch zkem, String ip, LocalDateTime start, LocalDateTime end) {
        AttendanceStub attendanceStub = new AttendanceStub(zkem);
        List<UserAttendance> all = new ArrayList<>();
        if (attendanceStub.readTimeGLogData(start, end) ) {
            while (attendanceStub.readCheckInfo()) {
                AttendanceRecord record = new AttendanceRecord();
                record.setDateStr(String.format("%s-%s-%s %s:%s:%s",
                        attendanceStub.getYear(),
                        attendanceStub.getMonth(),
                        attendanceStub.getDay(),
                        attendanceStub.getHour(),
                        attendanceStub.getMinute(),
                        attendanceStub.getSecond()));
                record.setDwEnrollNumber(attendanceStub.getDwEnrollNumber().toString());
                record.setDwInOutMode(Integer.valueOf(attendanceStub.getDwInOutMode().toString()));
                UserRecord userRecord = cache.computeIfAbsent(record.getDwEnrollNumber(), userId -> findUserByUserId(zkem, userId));
                if (userRecord != null) {
                    UserAttendance userAttendance = UserAttendance.of(record.getDwEnrollNumber(), userRecord.getName(), record.getDateStr());
                    all.add(userAttendance);
                }
            }
        }
        attendanceDao.saveAll(all);
        log.info(String.format("[%s]Sync total records: %s", ip, all.size()));
    }

    private boolean connect(Dispatch zkem, String host) {
        return Dispatch.call(zkem, "Connect_Net", new Variant(host), new Variant(4370)).getBoolean();
    }

    private void disconnect(Dispatch zkem) {
        Dispatch.call(zkem, "Disconnect");
    }

}
