package com.micro.cloud.config;

import cn.hutool.core.util.ArrayUtil;
import com.micro.cloud.constant.AuthConstant;
import com.micro.cloud.authorization.AuthorizationManager;
import com.micro.cloud.component.RestAuthenticationEntryPoint;
import com.micro.cloud.component.RestfulAccessDeniedHandler;
import com.micro.cloud.filter.CorsFilter;
import com.micro.cloud.filter.IgnoreUrlsRemoveJwtFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/** 资源服务器配置 Created by xulei on 2021/11/3 */
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

  @Autowired private AuthorizationManager authorizationManager;
  @Autowired private IgnoreUrlsConfig ignoreUrlsConfig;
  @Autowired private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
  @Autowired private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
  @Autowired private IgnoreUrlsRemoveJwtFilter ignoreUrlsRemoveJwtFilter;

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    // 关掉跨域
    http.cors().and().csrf().disable();
    // 增加自定义拦截器
    http.addFilterAt(
        new CorsFilter(), SecurityWebFiltersOrder.SECURITY_CONTEXT_SERVER_WEB_EXCHANGE);
    http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
    // 如果是token过期了，需要加到oauth2ResourceServer上，才会起作用，下面不知道为什么不起作用
    http.oauth2ResourceServer().authenticationEntryPoint(restAuthenticationEntryPoint);
    http.addFilterBefore(ignoreUrlsRemoveJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);
    http.authorizeExchange()
        .pathMatchers(ArrayUtil.toArray(ignoreUrlsConfig.getUrls(), String.class))
        .permitAll() // 白名单配置
        .pathMatchers(
            "/webjars/**",
            "/js/**",
            "/config/**",
            "/images/**",
            "/css/**",
            "/commonlib/**",
            "/thirdlibs/**",
            "/favicon.ico",
            "/oauth/**",
            "*.js",
            "/**/*.json",
            "/**/*.css",
            "/**/*.js",
            "/portal/**",
            "/**/*.map",
            "/**/*.html",
            "/**/*.png")
        .permitAll()
        .anyExchange()
        .access(authorizationManager) // 鉴权管理器配置
        .and()
        .exceptionHandling()
        .accessDeniedHandler(restfulAccessDeniedHandler) // 处理未授权
        .authenticationEntryPoint(restAuthenticationEntryPoint) // 处理未认证
        .and()
        .headers()
        .frameOptions()
        .disable() // 允许iframe
    ;

    return http.build();
  }

  @Bean
  public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>>
      jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
        new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
  }
}
