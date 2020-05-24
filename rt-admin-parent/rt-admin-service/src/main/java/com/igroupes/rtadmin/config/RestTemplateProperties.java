package com.igroupes.rtadmin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rest-template")
public class RestTemplateProperties {
    private int maxTotalConnect; 
    private int maxConnectPerRoute; 
    private int connectTimeout; 
    private int readTimeout;
}
