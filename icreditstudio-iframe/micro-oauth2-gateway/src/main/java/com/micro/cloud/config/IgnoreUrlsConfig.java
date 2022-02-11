package com.micro.cloud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 网关白名单配置
 * Created by xulei on 2021/11/3
 */
@Component
@ConfigurationProperties(prefix="secure.ignore")
public class IgnoreUrlsConfig {
    private List<String> urls;

    public IgnoreUrlsConfig(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
