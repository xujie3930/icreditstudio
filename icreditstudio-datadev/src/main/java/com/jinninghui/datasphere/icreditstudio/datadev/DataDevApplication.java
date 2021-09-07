package com.jinninghui.datasphere.icreditstudio.datadev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableDiscoveryClient
@ComponentScan("com.jinninghui")
public class DataDevApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataDevApplication.class, args);
    }
}
