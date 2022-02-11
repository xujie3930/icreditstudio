package com.micro.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(
    scanBasePackages = {"${micro.info.base-package}"},
    exclude = {DataSourceAutoConfiguration.class})
public class Oauth2AuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(Oauth2AuthApplication.class, args);
  }
}
