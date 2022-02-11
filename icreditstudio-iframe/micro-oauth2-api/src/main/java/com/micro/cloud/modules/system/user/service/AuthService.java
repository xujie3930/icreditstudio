package com.micro.cloud.modules.system.user.service;

import com.micro.cloud.api.CommonResult;
import com.micro.cloud.domian.dto.Oauth2TokenDto;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** @author roy */
@FeignClient("micro-oauth2-auth")
public interface AuthService {

  /**
   * oauth2 获取token
   *
   * @param parameters 获取token
   * @return
   */
  @PostMapping(value = "/oauth/token")
  CommonResult<Oauth2TokenDto> getAccessToken(@RequestParam Map<String, String> parameters);
}
