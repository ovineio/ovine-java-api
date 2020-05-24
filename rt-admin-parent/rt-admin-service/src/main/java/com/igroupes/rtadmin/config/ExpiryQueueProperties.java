package com.igroupes.rtadmin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rtadmin.expiry-queue")
public class ExpiryQueueProperties {
    private Integer verifyCodeInterval;
    private Integer verifyCodeTimeout;
}
