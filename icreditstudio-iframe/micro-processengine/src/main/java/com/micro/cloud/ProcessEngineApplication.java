package com.micro.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"${micro.info.base-package}"})
public class ProcessEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcessEngineApplication.class, args);
    }

}
