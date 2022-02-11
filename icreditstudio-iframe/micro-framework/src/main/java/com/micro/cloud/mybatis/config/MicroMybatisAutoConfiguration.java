package com.micro.cloud.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.micro.cloud.mybatis.core.handler.DefaultDBFieldHandler;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBaits 配置类
 *
 * @author roy
 */
@Configuration
@MapperScan(
    value = {"${micro.info.base-package}"},
    annotationClass = Mapper.class)
public class MicroMybatisAutoConfiguration {

  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
    // 分页插件
    mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
    return mybatisPlusInterceptor;
  }

  @Bean
  public MetaObjectHandler defaultMetaObjectHandler() {
    // 自动填充参数类
    return new DefaultDBFieldHandler();
  }
}
