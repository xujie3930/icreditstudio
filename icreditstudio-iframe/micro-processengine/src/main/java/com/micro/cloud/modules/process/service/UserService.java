package com.micro.cloud.modules.process.service;

import com.micro.cloud.api.CommonResult;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 〈用户信息等相关接口〉
 *
 * @author roy
 * @create 2021/12/15
 * @since 1.0.0
 */
@FeignClient("micro-oauth2-api")
public interface UserService {

  /**
   * 批量获取用户部门等相关信息
   *
   * @param ids 用户id集合
   * @return 用户部门等信息
   */
  @PostMapping("/sys/user/batch/info")
  CommonResult<?> getOrgInfoByUserIds(@RequestParam(value = "ids") List<String> ids);
}
