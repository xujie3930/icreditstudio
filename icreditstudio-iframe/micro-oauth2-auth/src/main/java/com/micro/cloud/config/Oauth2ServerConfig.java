package com.micro.cloud.config;

import com.micro.cloud.component.JwtTokenEnhancer;
import com.micro.cloud.constant.SysOauthConstant;
import com.micro.cloud.redis.service.RedisService;
import com.micro.cloud.service.OverrideTokenServices;
import com.micro.cloud.service.UserServiceImpl;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

/** 认证服务器配置 Created by xulei on 2021/11/3 */
@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private UserServiceImpl userDetailsService;

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private JwtTokenEnhancer jwtTokenEnhancer;

  @Autowired private TokenStore tokenStore;

  @Autowired private RedisService redisService;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients
        .inMemory()
        // 配置client_id
        .withClient(SysOauthConstant.JNH_CLIENT_ID)
        // 配置client_secret
        .secret(passwordEncoder.encode(SysOauthConstant.JNH_CLIENT_SECRET))
        // 配置申请的权限范围
        .scopes("all")
        // 配置grant_type，表示授权类型
        .authorizedGrantTypes("authorization_code", "password", "refresh_token")
        // 配置访问token的有效期
        .accessTokenValiditySeconds(SysOauthConstant.ACCESS_TOKEN_VALIDITY_SECONDS)
        // 配置刷新token的有效期
        .refreshTokenValiditySeconds(SysOauthConstant.REFRESH_TOKEN_VALIDITY_SECONDS)
        // 自动触发授权,免去手动点击授权的交互动作
        .autoApprove(true)
        // 配置redirect_uri，用于授权成功后跳转/后续修改为系统主页
        .redirectUris("http://ip:port/api/");
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
    List<TokenEnhancer> delegates = new ArrayList<>();
    delegates.add(jwtTokenEnhancer);
    delegates.add(accessTokenConverter());
    // 配置JWT的内容增强器
    enhancerChain.setTokenEnhancers(delegates);
    endpoints
        .authenticationManager(authenticationManager)
        // 配置加载用户信息的服务
        .userDetailsService(userDetailsService)
        .accessTokenConverter(accessTokenConverter())
        .tokenEnhancer(enhancerChain)
        // 设置基于redis的token存储策略
        //        .tokenStore(tokenStore)
        .tokenServices(tokenServices(endpoints));
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security
        .tokenKeyAccess("permitAll()")
        .checkTokenAccess("permitAll()")
        .allowFormAuthenticationForClients();
    //  security.allowFormAuthenticationForClients();
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    jwtAccessTokenConverter.setKeyPair(keyPair());
    return jwtAccessTokenConverter;
  }

  @Bean
  public KeyPair keyPair() {
    // 从classpath下的证书中获取秘钥对
    KeyStoreKeyFactory keyStoreKeyFactory =
        new KeyStoreKeyFactory(
            new ClassPathResource("jwt.jks"), SysOauthConstant.JWT_JKS_PASS.toCharArray());
    return keyStoreKeyFactory.getKeyPair("jwt", SysOauthConstant.JWT_JKS_PASS.toCharArray());
  }

  private AuthorizationServerTokenServices tokenServices(
      AuthorizationServerEndpointsConfigurer endpoints) {
    OverrideTokenServices tokenServices = new OverrideTokenServices();
    tokenServices.setTokenStore(tokenStore);
    // 支持刷新token
    tokenServices.setSupportRefreshToken(true);
    tokenServices.setReuseRefreshToken(true);
    tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
    tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
    tokenServices.setAuthenticationManager(authenticationManager);
    tokenServices.setRedisService(redisService);
    return tokenServices;
  }
}
