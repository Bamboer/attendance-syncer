package com.dmai.attendance.syncer.sdk;

import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Fuxin
 * @since 2019/4/25 18:15
 *
 * 对接考勤用户信息的API
 */
@Getter
@RequiredArgsConstructor
public class UserInfoStub {
    public Variant dwMachineNumber = new Variant(1, true);
    public Variant dwEnrollNumber = new Variant("", true);
    public Variant Name = new Variant("", true);
    public Variant Password = new Variant("", true);
    public Variant Privilege = new Variant(-1, true);
    public Variant Enabled = new Variant(false, true);

    private final Dispatch zkem;

    public boolean fetchALlUserInfo() {
        return Dispatch.call(zkem, "SSR_GetAllUserInfo", dwMachineNumber,
                dwEnrollNumber,
                Name,
                Password,
                Privilege,
                Enabled).getBoolean();
    }

    public boolean readAllUserID() {
        return Dispatch.call(zkem, "ReadAllUserID", dwMachineNumber).getBoolean();
    }

    public boolean fetchUserInfoById(String userId) {
        dwEnrollNumber = new Variant(userId);
        return  Dispatch.call(zkem, "SSR_GetUserInfo", dwMachineNumber,
                dwEnrollNumber,
                Name,
                Password,
                Privilege,
                Enabled).getBoolean();
    }

}