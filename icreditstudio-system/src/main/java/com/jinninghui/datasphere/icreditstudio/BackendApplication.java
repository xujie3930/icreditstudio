package com.jinninghui.datasphere.icreditstudio;

import com.jinninghui.datasphere.icreditstudio.common.config.IFrameBanner;
import com.hashtech.businessframework.job.configuration.EnableElasticJob;
import com.hashtech.businessframework.sequence.configuration.EnableSequenceService;
import com.hashtech.businessframework.validate.EnableValidator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liyanhui
 */
@SpringBootApplication()
@EnableSequenceService
@EnableValidator
@EnableScheduling
@MapperScan(basePackages = {"com.jinninghui.**.mapper"})
@EnableAspectJAutoProxy(exposeProxy = true)//exposeProxy类内部可以获取到当前类的代理对象
@EnableElasticJob
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(BackendApplication.class);
        springApplication.setBanner(new IFrameBanner());
        springApplication.run(args);
    }
}

