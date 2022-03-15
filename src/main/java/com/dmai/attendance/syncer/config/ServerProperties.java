package com.dmai.attendance.syncer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author Fuxin
 * @since 2019/4/26 17:09
 */
@Data
@Component
@ConfigurationProperties("dmai.attendance")
public class ServerProperties {

    private List<String> hosts;

    private String recordCronExpression;

}
