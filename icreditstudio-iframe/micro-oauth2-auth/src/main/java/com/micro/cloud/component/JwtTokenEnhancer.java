package com.micro.cloud.component;

import com.micro.cloud.domain.SecurityUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/** JWT内容增强器 Created by xulei on 2021/11/3 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {
  private Logger logger = LoggerFactory.getLogger(JwtTokenEnhancer.class);

  @Override
  public OAuth2AccessToken enhance(
      OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
    Map<String, Object> info = new HashMap<>(16);
    final Map<String, Object> additionalMap = new HashMap<>(16);
    // 设置到JWT负载信息
    info.put("id", securityUser.getId());
    info.put("userName", securityUser.getUsername());
    info.put("realName", securityUser.getRealName());
    info.put("phone", securityUser.getPhone());
    info.put("email", securityUser.getEmail());
    info.put("departId", securityUser.getDepartId());
    info.put("departName", securityUser.getDepartName());
    info.put("userType", securityUser.getUserType());
    info.put("roles", securityUser.getRoles());
    additionalMap.put("additionalInfo", info);
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalMap);
    return accessToken;
  }
}
