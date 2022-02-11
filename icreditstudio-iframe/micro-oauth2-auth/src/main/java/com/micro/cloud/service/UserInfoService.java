package com.micro.cloud.service;

import com.micro.cloud.domian.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 〈获取用户信息服务接口〉
 *
 * @author roy
 * @create 2021/11/10
 * @since 1.0.0
 */
@FeignClient("micro-oauth2-api")
public interface UserInfoService {

  /**
   * 远程接口调用: 根据用户名获取用户信息
   *
   * @param username 用户名
   * @return 用户信息
   */
  @GetMapping("/sys/user/info/{username}")
  UserDTO getInfoByUsername(@PathVariable(value = "username") String username);
}
