package com.micro.cloud.api;

import java.util.Map;

/**
 * 认证服务返回TokenInfo
 * @author EDZ
 */
public class TokenInfo {

  private String accessToken;

  private String tokenType;

  private String refreshToken;

  private String scope;

  private Map<String, String> additionalInfo;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public Map<String, String> getAdditionalInfo() {
    return additionalInfo;
  }

  public void setAdditionalInfo(Map<String, String> additionalInfo) {
    this.additionalInfo = additionalInfo;
  }

  @Override
  public String toString() {
    return "TokenInfo{" +
        "accessToken='" + accessToken + '\'' +
        ", tokenType='" + tokenType + '\'' +
        ", refreshToken='" + refreshToken + '\'' +
        ", scope='" + scope + '\'' +
        ", additionalInfo=" + additionalInfo +
        '}';
  }
}
