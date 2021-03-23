package com.igroupes.rtadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = "com.igroupes")
@ServletComponentScan(basePackages = "com.igroupes")
@EnableEurekaClient
public class RtAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(RtAdminApplication.class, args);
    }

}
