package com.micro.cloud.config;

import com.micro.cloud.service.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SpringSecurity配置 Created by xulei on 2021/11/20
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint())
                .permitAll()
                .antMatchers("/rsa/publicKey")
                .permitAll()
                .antMatchers("/oauth/token")
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                // .anyRequest().authenticated()//所有请求都需要通过认证
                .and()
                .httpBasic() // Basic提交
                .and()
                .cors() // 需要添加此配置项
                .and()
                .csrf()
                .disable() // 关闭跨域保护
                .logout() // 指定退出登录后重定向地址
                .logoutSuccessUrl("http://www.baidu.com");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService())
                .passwordEncoder(
                        new PasswordEncoder() {
                            @Override
                            public String encode(CharSequence rawPassword) {
                                return (String) rawPassword;
                            }

                            @Override
                            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                                return StringUtils.equals(rawPassword, encodedPassword);
                            }
                        });
    }

    @Bean
    public UserDetailsService customUserService() {
        return new UserServiceImpl();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

 /* @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }*/
}
