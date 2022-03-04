package com.dmai.attendance.syncer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties("dmai.attendance")
public class ServerProperties {
    private List<String> hosts;
//    private String secret;
    private String recordCronExpression;
}
