package com.jinninghui.datasphere.icreditstudio.system;

import com.jinninghui.datasphere.icreditstudio.framework.sequence.configuration.EnableSequenceService;
import com.jinninghui.datasphere.icreditstudio.system.common.config.IFrameBanner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liyanhui
 */
@EnableDiscoveryClient
@SpringBootApplication()
@EnableFeignClients
@EnableSequenceService
//@EnableValidator
@EnableScheduling
@MapperScan(basePackages = {"com.jinninghui.**.mapper"})
@EnableAspectJAutoProxy(exposeProxy = true)//exposeProxy类内部可以获取到当前类的代理对象
@ComponentScan("com.jinninghui")
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(BackendApplication.class);
        springApplication.setBanner(new IFrameBanner());
        springApplication.run(args);
    }
}
