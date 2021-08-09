package com.jinninghui.icreditdatasphere.icreditstudio.common.config;

import com.google.common.collect.Lists;
import com.jinninghui.icreditdatasphere.icreditstudio.common.interceptor.AuthInterceptor;
import com.jinninghui.icreditdatasphere.icreditstudio.common.interceptor.UserDisabledInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UserDisabledInterceptor disabledInterceptor;
    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
                .excludePathPatterns(Lists.newArrayList(
                        "/uaa/**", "/interfaces","/tokenAuth","/resources/initAuth"))
                .excludePathPatterns("/doc.html").excludePathPatterns("/webjars/**");
        registry.addInterceptor(disabledInterceptor).addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置拦截器访问静态资源
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
