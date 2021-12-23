package com.jinninghui.datasphere.icreditstudio.datasource.common.config;

import com.jinninghui.datasphere.icreditstudio.framework.validate.RequestLimitingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author xujie
 * @description 限流构造器
 * @create 2021-12-23 10:10
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 请求限流拦截器
     */
    @Autowired
    protected RequestLimitingInterceptor requestLimitingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 请求限流
        registry.addInterceptor(requestLimitingInterceptor).addPathPatterns("/**");
    }

}
