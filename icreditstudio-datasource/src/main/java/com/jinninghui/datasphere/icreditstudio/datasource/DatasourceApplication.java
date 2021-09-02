package com.jinninghui.datasphere.icreditstudio.datasource;

import com.jinninghui.datasphere.icreditstudio.framework.sequence.configuration.EnableSequenceService;
import com.jinninghui.datasphere.icreditstudio.framework.validate.EnableValidator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication()
@EnableSequenceService
//@EnableValidator
@EnableScheduling
@MapperScan(basePackages = {"com.jinninghui.**.mapper"})
@EnableAspectJAutoProxy(exposeProxy = true)//exposeProxy类内部可以获取到当前类的代理对象
public class DatasourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DatasourceApplication.class, args);
    }
}
