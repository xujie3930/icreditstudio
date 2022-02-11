package com.micro.cloud.modules.system.user.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.micro.cloud.api.CommonResult;
import com.micro.cloud.modules.system.user.service.CommonUserService;
import com.micro.cloud.modules.system.user.vo.*;
import com.micro.cloud.modules.system.user.vo.org.OrgUserPageRepVO;
import com.micro.cloud.modules.system.util.Md5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 个人用户管理
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Api(tags = "用户管理-通用接口")
@RestController
@RequestMapping("/sys/user")
public class SysCommonUserController {

  private final CommonUserService userService;

  @Autowired private RestTemplate restTemplate;

  @Value("${project.tokenUrl}")
  private String tokenUrl;

  @Value("${project.projectUrl}")
  private String projectUrl;

  public SysCommonUserController(CommonUserService userService) {
    this.userService = userService;
  }

  @ApiOperation(value = "登录")
  @PostMapping(value = "/login")
  public CommonResult<SysLoginRepVo> login(@Validated @RequestBody SysLoginReqVo reqVo) {
    try {
      SysLoginRepVo result = userService.login(reqVo);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation(value = "登录-无图片验证码")
  @PostMapping(value = "/login-no-captcha")
  public CommonResult<SysLoginRepVo> login(@Validated @RequestBody SysLoginWithoutCaptchaReqVo reqVo) {
    try {
      SysLoginRepVo result = userService.loginWithNoCaptcha(reqVo);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation(value = "用户实名认证")
  @PostMapping(value = "/auth")
  public CommonResult<Boolean> auth(@Validated @RequestBody SysUserAuthReqVO vo) {
    try {
      Boolean result = userService.auth(vo);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation(value = "重制用户密码")
  @PostMapping(value = "/password/reset")
  public CommonResult<Boolean> passwordReset(@RequestBody PasswordResetReqVO reqVO) {
    try {
      Boolean result = userService.passwordReset(reqVO);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation(value = "用户更改密码")
  @PostMapping(value = "/password/update")
  public CommonResult<Boolean> updatePassword(@RequestBody UpdatePasswordReqVO reqVO) {
    try {
      Boolean result = userService.updatePassword(reqVO);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation(value = "退出登录")
  @GetMapping(value = "/logout")
  public CommonResult<Boolean> logout(HttpServletRequest request) {
    Boolean result = userService.logout(request);
    return CommonResult.success(result);
  }

  @ApiOperation(value = "获取当前登录用户信息")
  @GetMapping(value = "/info")
  public CommonResult<CommonUserInfoVo> getUserInfo(HttpServletRequest request) {
    CommonUserInfoVo result = userService.getCurrentUserInfo(request);
    return CommonResult.success(result);
  }

  @ApiOperation(value = "用户更新账号信息")
  @PostMapping(value = "/info/update")
  public CommonResult<Boolean> update(@Validated @RequestBody CommonUserInfoVo vo) {
    Boolean result = userService.updateCommonInfo(vo);
    return CommonResult.success(result);
  }

  @ApiOperation(value = "用户名密码校验")
  @PostMapping(value = "/validate")
  public CommonResult<Boolean> validate(@Validated @RequestBody UserValidateReqVo reqVo) {
    try {
      Boolean result = userService.validate(reqVo);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation(value = "用户更换手机号校验")
  @PostMapping(value = "/validate/phone")
  public CommonResult<Boolean> validatePhone(@Validated @RequestBody PhoneValidateReqVo reqVo) {
    try {
      Boolean result = userService.validatePhone(reqVo);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation(value = "根据用户id集合批量获取对应部门信息")
  @PostMapping(value = "/batch/info")
  public CommonResult<Map<String, OrgUserPageRepVO>> getOrgInfoByUserIds(
      @RequestParam(value = "ids") List<String> ids) {
    Map<String, OrgUserPageRepVO> result = userService.getOrgInfoByUserIds(ids);
    return CommonResult.success(result);
  }

  @ApiOperation(value = "系统获取token")
  @PostMapping(value = "/generate/token")
  public CommonResult<GenerateTokenRepVo> generateToken(
      @Validated @RequestBody GenerateTokenReqVo reqVo) {
    try {
      GenerateTokenRepVo result = userService.generateToken(reqVo);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  /** 获取项目管理Url */
  @PostMapping("/getProjectManageUrl")
  @ApiOperation(value = "获取项目管理Url", notes = "获取项目管理Url", httpMethod = "POST")
  public CommonResult getProjectUrl(@RequestBody PublicBuildingPjRequest request) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    String rtime = sdf.format(new Date());
    String uid = request.getUserName();
    if (StringUtils.isEmpty(uid)) {
      return CommonResult.failed("0001", "账号为空");
    }
    JSONObject json = new JSONObject();
    json.put("uid", uid);
    json.put("rtime", rtime);
    json.put("key", Md5Utils.md5Password(uid + rtime));
    HttpHeaders headers = new HttpHeaders();
    MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
    headers.setContentType(type);
    headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    HttpEntity<String> formEntity = new HttpEntity<String>(json.toString(), headers);
    String s = restTemplate.postForEntity(tokenUrl, formEntity, String.class).getBody();
    JSONObject resObj = JSONObject.parseObject(s);
    String data = resObj.getString("data");
    JSONObject obj = JSONObject.parseObject(data);
    String token = obj.getString("accessToken");
    String pUrl = projectUrl;
    if (StringUtils.isNotEmpty(request.getUrl())) {
      pUrl = request.getUrl();
    }
    String proUrl = pUrl + "?uid=" + uid + "&token=" + token;
    return CommonResult.success(proUrl);
  }
}
