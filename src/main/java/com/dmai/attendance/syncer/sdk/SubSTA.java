package com.dmai.attendance.syncer.sdk;

import com.jacob.com.STA;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Fuxin
 * @since 2019/8/20 13:55
 */
@Slf4j
public class SubSTA extends STA {
    @Override
    public void OnQuit() {
        super.OnQuit();
        log.info(">>>>>>>>>>>> quit!");
    }

    @Override
    public void run() {
        try {
            super.run();
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
    }

    @Override
    public boolean OnInit() {
        log.info(">>>>>>>>>> OnInit");
        return super.OnInit();
    }
}
