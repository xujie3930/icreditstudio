package com.micro.cloud.common;

import com.micro.cloud.snowflake.sequence.SnowflakeSequenceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * 〈模块通用配置〉
 *
 * @author roy
 * @create 2021/11/6
 * @since 1.0.0
 */
@Configuration
public class CommonConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SnowflakeSequenceService snowflakeSequenceService() {
    return new SnowflakeSequenceService();
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
