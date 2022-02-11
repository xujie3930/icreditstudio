package com.micro.cloud.config.captcha;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author roy
 */
@Configuration
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaConfiguration {
}
