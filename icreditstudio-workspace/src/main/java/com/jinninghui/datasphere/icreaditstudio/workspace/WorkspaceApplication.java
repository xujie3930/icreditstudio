package com.jinninghui.datasphere.icreaditstudio.workspace;

import com.jinninghui.datasphere.icreditstudio.framework.sequence.configuration.EnableSequenceService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@SpringBootApplication()
@EnableSequenceService
//@EnableValidator
@EnableScheduling
@MapperScan(basePackages = {"com.jinninghui.**.mapper"})
@EnableFeignClients
@EnableAspectJAutoProxy(exposeProxy = true)//exposeProxy类内部可以获取到当前类的代理对象
@ComponentScan("com.jinninghui")
public class WorkspaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkspaceApplication.class, args);
    }
}
