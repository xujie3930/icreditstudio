package com.micro.cloud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;


public class CorsConfig {

  @Value("${cors.allowedorigin}")
  private String corsAllowedorigin;

  @Bean
  public CorsWebFilter corsFilter() {
    UrlBasedCorsConfigurationSource source =
        new UrlBasedCorsConfigurationSource(new PathPatternParser());
    source.registerCorsConfiguration("/**", buildConfig());
    return new CorsWebFilter(source);
  }

  private CorsConfiguration buildConfig() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    // 在生产环境上最好指定域名，以免产生跨域安全问题
    corsConfiguration.addAllowedOrigin(corsAllowedorigin);
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.setAllowCredentials(false);
    return corsConfiguration;
  }
}
