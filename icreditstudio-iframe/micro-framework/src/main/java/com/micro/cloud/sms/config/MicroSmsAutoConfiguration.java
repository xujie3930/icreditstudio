package com.micro.cloud.sms.config;

import com.micro.cloud.sms.core.client.SmsClientFactory;
import com.micro.cloud.sms.core.client.impl.SmsClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 短信配置类
 *
 * @author roy
 */
@Configuration
public class MicroSmsAutoConfiguration {

  @Bean
  public SmsClientFactory smsClientFactory() {
    return new SmsClientFactoryImpl();
  }
}
