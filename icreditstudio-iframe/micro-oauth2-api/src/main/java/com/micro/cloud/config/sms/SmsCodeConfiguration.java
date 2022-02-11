package com.micro.cloud.config.sms;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author roy
 */
@Configuration
@EnableConfigurationProperties(SmsCodeProperties.class)
public class SmsCodeConfiguration {
}
