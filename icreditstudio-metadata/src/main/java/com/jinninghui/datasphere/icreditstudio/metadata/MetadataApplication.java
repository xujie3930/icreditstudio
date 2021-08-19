package com.jinninghui.datasphere.icreditstudio.metadata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableDiscoveryClient
public class MetadataApplication {
    public static void main(String[] args) {
        SpringApplication.run(MetadataApplication.class, args);
    }
}
