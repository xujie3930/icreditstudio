package com.jinninghui.datasphere.icreditstudio.workspace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableDiscoveryClient
public class WorkspaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkspaceApplication.class, args);
    }
}
