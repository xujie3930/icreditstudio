package com.micro.cloud.modules.system.user.controller;

import com.micro.cloud.api.CommonResult;
import com.micro.cloud.modules.system.user.service.SysUserAccountService;
import com.micro.cloud.modules.system.user.vo.SysUserAccountCreateReqVO;
import com.micro.cloud.modules.system.user.vo.external.ExternalUserCreateReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端控制器
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Api(tags = "用户账号管理")
@RestController
@RequestMapping("/sys/user/account")
public class SysUserAccountController {

  @Autowired
  private SysUserAccountService userAccountService;

  @ApiOperation("新增用户账号")
  @PostMapping("/create")
  public CommonResult<String> createUser(
      @Valid @RequestBody SysUserAccountCreateReqVO reqVO,
      @RequestHeader(value = "id") String userId) {
    try {
      String accountId = userAccountService.createUserAccount(reqVO, userId);
      return CommonResult.success(accountId);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }
}
