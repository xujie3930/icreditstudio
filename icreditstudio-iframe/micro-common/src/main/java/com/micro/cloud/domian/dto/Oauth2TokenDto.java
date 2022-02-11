package com.micro.cloud.domian.dto;

import java.io.Serializable;

/** Oauth2获取Token返回信息封装 Created by xulei on 2021/11/3 */
public class Oauth2TokenDto implements Serializable {

  private static final long serialVersionUID = 7236234374212024417L;

  /** 访问令牌 */
  private String token;
  /** 刷新令牌 */
  private String refreshToken;
  /** 访问令牌头前缀 */
  private String tokenHead;
  /** 有效时间（秒） */
  private int expiresIn;

  public Oauth2TokenDto() {}

  public Oauth2TokenDto(String token, String refreshToken, String tokenHead, int expiresIn) {
    this.token = token;
    this.refreshToken = refreshToken;
    this.tokenHead = tokenHead;
    this.expiresIn = expiresIn;
  }

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
}
