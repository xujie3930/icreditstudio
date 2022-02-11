package com.micro.cloud;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableAdminServer //开启spring boot admin相关功能
@SpringBootApplication(scanBasePackages = {"com.micro.cloud"})
public class MicroAdminApplication {
  public static void main(String[] args) {
    SpringApplication.run(MicroAdminApplication.class, args);
  }
}
