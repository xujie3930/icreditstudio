package com.jinninghui.datasphere.icreditstudio.metadata.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Peng
 */
@Configuration
public class BeanConfig {

    @Bean
    public ThreadPoolExecutor executor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                Runtime.getRuntime().availableProcessors(),
                10,
                TimeUnit.MINUTES, new LinkedBlockingQueue<>(10)
        );
        return executor;
    }
}
