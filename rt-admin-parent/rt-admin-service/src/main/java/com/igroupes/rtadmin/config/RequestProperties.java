package com.igroupes.rtadmin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rtadmin.request")
public class RequestProperties {
    private String logRequestTypeFilter;
    private String limitExcludePath;
    private String logExcludePath;
    private String logRecordExcludeField;
}
