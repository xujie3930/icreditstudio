package com.jinninghui.icreditdatasphere.icreditstudio.gateway;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@EnableAspectJAutoProxy
@EnableZuulProxy
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
@RestController
@RefreshScope
@EnableEncryptableProperties
@EnableScheduling
public class ApiGatewayApplication {
    public static void main(String[] args) {
        System.setProperty("jasypt.encryptor.password", "salt");
        SpringApplication springApplication = new SpringApplication(ApiGatewayApplication.class);
        springApplication.run(args);
    }


}

