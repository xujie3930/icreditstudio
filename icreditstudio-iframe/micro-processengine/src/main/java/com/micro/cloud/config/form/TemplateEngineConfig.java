package com.micro.cloud.config.form;

import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.engine.velocity.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 〈TemplateEngine配置〉
 *
 * @author roy
 * @create 2021/12/4
 * @since 1.0.0
 */
@Configuration
public class TemplateEngineConfig {

  @Bean
  public TemplateEngine templateEngine() {
    // 初始化 TemplateEngine 属性
    TemplateConfig config = new TemplateConfig();
    config.setResourceMode(TemplateConfig.ResourceMode.CLASSPATH);
    return new VelocityEngine(config);
  }
}
