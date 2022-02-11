package com.micro.cloud.config;

import com.micro.cloud.redis.config.BaseRedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * redis相关配置
 *
 * @author roy
 */
@Configuration
public class RedisConfig extends BaseRedisConfig {

  @Autowired private RedisConnectionFactory redisConnectionFactory;

  /**
   * 设置基于redis的token存储策略
   *
   * @return redis存储策略
   */
  @Bean
  public TokenStore tokenStore() {
    return new RedisTokenStore(redisConnectionFactory);
  }
}
