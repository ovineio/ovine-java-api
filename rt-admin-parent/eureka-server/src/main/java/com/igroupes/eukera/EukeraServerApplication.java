package com.igroupes.eukera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EukeraServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EukeraServerApplication.class, args);
    }
}
