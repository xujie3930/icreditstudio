package com.micro.cloud.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * mybatis 相关配置
 * @author EDZ
 */
@Configuration
@EnableTransactionManagement
@MapperScan(value = {"com.mirco.cloud.modules.**.mapper"}, annotationClass = Mapper.class)
public class MyBatisConfig {
}
