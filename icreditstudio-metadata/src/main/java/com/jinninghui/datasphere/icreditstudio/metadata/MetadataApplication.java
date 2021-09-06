package com.jinninghui.datasphere.icreditstudio.metadata;

import com.jinninghui.datasphere.icreditstudio.framework.sequence.configuration.EnableSequenceService;
import com.jinninghui.datasphere.icreditstudio.framework.validate.EnableValidator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableSequenceService
@EnableValidator
@EnableScheduling
@MapperScan(basePackages = {"com.jinninghui.**.mapper"})
@EnableAspectJAutoProxy(exposeProxy = true)//exposeProxy类内部可以获取到当前类的代理对象
public class MetadataApplication {
    public static void main(String[] args) {
        SpringApplication.run(MetadataApplication.class, args);
    }
}