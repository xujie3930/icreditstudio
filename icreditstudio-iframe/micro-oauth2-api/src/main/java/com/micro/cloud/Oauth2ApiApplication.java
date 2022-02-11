package com.micro.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SuppressWarnings("SpringComponentScan")
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"${micro.info.base-package}"})
public class Oauth2ApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(Oauth2ApiApplication.class, args);
  }

}
