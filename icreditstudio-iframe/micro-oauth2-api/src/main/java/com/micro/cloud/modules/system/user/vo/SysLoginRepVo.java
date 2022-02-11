package com.micro.cloud.modules.system.user.vo;

import com.micro.cloud.modules.system.resource.vo.SysResourceRespVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 * 用户登录参数
 *
 * @author roy
 */
@ApiModel(value = "用户登录请求出参")
public class SysLoginRepVo implements Serializable {

  private static final long serialVersionUID = -5610974785527188221L;

  /** 访问令牌 */
  @ApiModelProperty(
      value = "token",
      required = true,
      example = "比较长，暂时省略",
      notes = "放入Header中，前缀为Bearer")
  private String token;
  /** 刷新令牌 */
  @ApiModelProperty(
      value = "refreshToken",
      required = true,
      example = "比较长，暂时省略",
      notes = "刷新token时使用")
  private String refreshToken;
  /** 访问令牌头前缀 */
  @ApiModelProperty(value = "请求头前缀", required = true, example = "Bearer", notes = "token 前缀为Bearer")
  private String tokenHead;
  /** 有效时间（秒） */
  @ApiModelProperty(value = "过期时间", required = true, example = "36000", notes = "过期后退出登录")
  private int expiresIn;

  @ApiModelProperty(value = "用户通用信息", required = true)
  private CommonUserInfoRepVO userInfo;

  /* @ApiModelProperty(value = "用户id", required = true, example = "2021110")
  private String userId;

  @ApiModelProperty(value = "用户部门id", required = true, example = "1011102", notes = "没有则为空字符串")
  private String departId;*/

  /*@ApiModelProperty(value = "用户资源列表", required = true, example = "xxxxx", notes = "作为页面展示依据")
  private List<String> resources;*/

  @ApiModelProperty(value = "用户资源列表", required = true, example = "xxxxx", notes = "作为页面展示依据")
  private Map<String, List<String>> resources;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
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

  public CommonUserInfoRepVO getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(CommonUserInfoRepVO userInfo) {
    this.userInfo = userInfo;
  }

  /* public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getDepartId() {
    return departId;
  }

  public void setDepartId(String departId) {
    this.departId = departId;
  }*/

  public Map<String, List<String>> getResources() {
    return resources;
  }

  public void setResources(Map<String, List<String>> resources) {
    this.resources = resources;
  }

  @Override
  public String toString() {
    return "SysLoginRepVo{"
        + "token='"
        + token
        + '\''
        + ", refreshToken='"
        + refreshToken
        + '\''
        + ", tokenHead='"
        + tokenHead
        + '\''
        + ", expiresIn="
        + expiresIn
        + ", userInfo="
        + userInfo
        + ", resources="
        + resources
        + '}';
  }

  /*
  public List<String> getResources() {
    return resources;
  }

  public void setResources(List<String> resources) {
    this.resources = resources;
  }*/

  /* @Override
  public String toString() {
    return "SysLoginRepVo{"
        + "token='"
        + token
        + '\''
        + ", refreshToken='"
        + refreshToken
        + '\''
        + ", tokenHead='"
        + tokenHead
        + '\''
        + ", expiresIn="
        + expiresIn
        + ", userId='"
        + userId
        + '\''
        + ", departId='"
        + departId
        + '\''
        + ", resources="
        + resources
        + '}';
  }*/
}
