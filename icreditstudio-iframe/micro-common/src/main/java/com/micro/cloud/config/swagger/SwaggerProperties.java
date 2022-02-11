package com.micro.cloud.config.swagger;

import javax.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Swagger 配置属性
 *
 * @author roy
 */
@ConfigurationProperties("micro.swagger")
public class SwaggerProperties {

  /** 标题 */
  @NotEmpty(message = "标题不能为空")
  private String title;
  /** 描述 */
  @NotEmpty(message = "描述不能为空")
  private String description;
  /** 作者 */
  @NotEmpty(message = "作者不能为空")
  private String author;
  /** 版本 */
  @NotEmpty(message = "版本不能为空")
  private String version;
  /** 扫描的包 */
  @NotEmpty(message = "扫描的 package 不能为空")
  private String basePackage;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getBasePackage() {
    return basePackage;
  }

  public void setBasePackage(String basePackage) {
    this.basePackage = basePackage;
  }

  @Override
  public String toString() {
    return "SwaggerProperties{"
        + "title='"
        + title
        + '\''
        + ", description='"
        + description
        + '\''
        + ", author='"
        + author
        + '\''
        + ", version='"
        + version
        + '\''
        + ", basePackage='"
        + basePackage
        + '\''
        + '}';
  }
}
