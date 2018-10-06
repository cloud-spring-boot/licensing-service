package com.max.licensing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LicensingApplication {
    public static void main(String[] args) {
        SpringApplication.run(LicensingApplication.class, args);
    }
}
