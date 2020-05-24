package com.igroupes.rtadmin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rtadmin.login")
public class LoginProperties {
    private Long expireTime;
    private String tokenKey;
}
