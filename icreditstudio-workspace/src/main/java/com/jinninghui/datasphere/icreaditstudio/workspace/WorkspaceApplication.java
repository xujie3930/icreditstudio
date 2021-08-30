package com.jinninghui.datasphere.icreaditstudio.workspace;

import com.jinninghui.datasphere.icreditstudio.framework.sequence.configuration.EnableSequenceService;
import com.jinninghui.datasphere.icreditstudio.framework.validate.EnableValidator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication()
@EnableSequenceService
@EnableValidator
@EnableScheduling
@MapperScan(basePackages = {"com.jinninghui.**.mapper"})
@EnableFeignClients
@EnableAspectJAutoProxy(exposeProxy = true)//exposeProxy类内部可以获取到当前类的代理对象
public class WorkspaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkspaceApplication.class, args);
    }
}
