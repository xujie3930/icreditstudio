package com.micro.cloud.modules.system.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 用户登录参数
 *
 * @author roy
 */
@ApiModel(value = "外部系统获取token返回结果")
public class GenerateTokenRepVo implements Serializable {

  private static final long serialVersionUID = -5610974785527188221L;

  /** 访问令牌 */
  @ApiModelProperty(
      value = "token",
      required = true,
      example = "比较长，暂时省略",
      notes = "放入Header中，前缀为Bearer")
  private String token;

  @ApiModelProperty(value = "请求头前缀", required = true, example = "Bearer", notes = "token 前缀为Bearer")
  private String tokenHead;
  /** 有效时间（秒） */
  @ApiModelProperty(value = "过期时间", required = true, example = "36000", notes = "过期后退出登录")
  private int expiresIn;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getTokenHead() {
    return tokenHead;
  }

  public void setTokenHead(String tokenHead) {
    this.tokenHead = tokenHead;
  }

  public int getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(int expiresIn) {
    this.expiresIn = expiresIn;
  }

  @Override
  public String toString() {
    return "GenerateTokenRepVo{"
        + "token='"
        + token
        + '\''
        + ", tokenHead='"
        + tokenHead
        + '\''
        + ", expiresIn="
        + expiresIn
        + '}';
  }
}
