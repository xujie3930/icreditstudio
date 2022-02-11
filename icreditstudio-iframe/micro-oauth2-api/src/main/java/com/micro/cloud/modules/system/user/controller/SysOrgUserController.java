package com.micro.cloud.modules.system.user.controller;

import com.micro.cloud.api.CommonPage;
import com.micro.cloud.api.CommonResult;
import com.micro.cloud.modules.system.user.service.OrgUserService;
import com.micro.cloud.modules.system.user.vo.UserDelReqVO;
import com.micro.cloud.modules.system.user.vo.external.ExternalUserRegisterReqVO;
import com.micro.cloud.modules.system.user.vo.external.ExternalUserUpdateStatusReqVO;
import com.micro.cloud.modules.system.user.vo.org.OrgUserCreateReqVO;
import com.micro.cloud.modules.system.user.vo.org.OrgUserInfoVO;
import com.micro.cloud.modules.system.user.vo.external.ExternalUserCreateReqVO;
import com.micro.cloud.modules.system.user.vo.SysUserPageReqVO;
import com.micro.cloud.modules.system.user.vo.SysUserUpdateReqVO;
import com.micro.cloud.modules.system.user.vo.org.OrgUserRegisterReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 个人用户管理
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Api(tags = "用户管理-机构用户(外部)")
@RestController
@RequestMapping("/sys/user/org")
public class SysOrgUserController {

  private final OrgUserService userService;

  public SysOrgUserController(OrgUserService userService) {
    this.userService = userService;
  }

  @ApiOperation(value = "用户注册")
  @PostMapping(value = "/register")
  public CommonResult<Boolean> register(@Validated @RequestBody OrgUserRegisterReqVO vo) {
    try {
      Boolean result = userService.register(vo);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  /**
   * 用户分页查询
   *
   * @param vo 分页请求参数
   * @param userId 用户id
   * @return 分页记录
   */
  @ApiOperation(value = "机构用户分页查询", notes = "分页查询", httpMethod = "POST")
  @PostMapping("/page")
  public CommonResult<CommonPage<OrgUserInfoVO>> page(
      @ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody
          SysUserPageReqVO vo,
      @RequestHeader(value = "userId") String userId) {
    List<OrgUserInfoVO> result = userService.page(vo, userId);
    return CommonResult.success(CommonPage.restPage(result));
  }

  /**
   * 根据用户id查看用户详情
   *
   * @param id 用户id
   * @return 用户详情
   */
  @ApiOperation(value = "机构用户信息查询", notes = "信息", httpMethod = "GET")
  @GetMapping("/info/{id}")
  public CommonResult<OrgUserInfoVO> info(
      @ApiParam(name = "id", value = "用户id", required = true) @PathVariable("id") String id /*,
      @RequestHeader(value = "orgId") String orgId*/) {
    String orgId = "";
    OrgUserInfoVO info = userService.info(id, orgId);
    return CommonResult.success(info);
  }

  /** 保存 */
  @ApiOperation(value = "新增机构用户", notes = "新增", httpMethod = "POST")
  @PostMapping("/create")
  public CommonResult<String> create(
      @ApiParam(name = "保存对象", value = "传入json格式", required = true) @Validated @RequestBody
          OrgUserCreateReqVO vo) {
    try {
      String creatorId = "";
      String orgId = "";
      String userId = userService.create(vo, creatorId, orgId);
      return CommonResult.success(userId);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  /** 修改 */
  @ApiOperation(value = "修改用户信息", notes = "修改", httpMethod = "POST")
  @PostMapping("/update")
  public CommonResult<Boolean> update(
      @Validated @RequestBody
          OrgUserInfoVO reqVO /*, @RequestHeader(value = "id") String updaterId*/) {
    try {
      String updaterId = "";
      Boolean result = userService.update(reqVO, updaterId);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  /**
   * 删除用户
   *
   * @param id 用户id
   * @return 是否成功
   */
  @ApiOperation(value = "删除")
  @GetMapping("/delete/{id}")
  public CommonResult<Boolean> delete(@PathVariable(value = "id") String id) {
    try {
      Boolean result = userService.removeById(id);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  /**
   * 批量删除用户
   *
   * @param dto 批量删除信息
   * @return 是否成功
   */
  @ApiOperation(value = "批量删除")
  @PostMapping("/delete/batch")
  public CommonResult<Boolean> deleteBatch(@RequestBody UserDelReqVO dto) {
    try {
      Boolean result = userService.removeByIds(dto.getIds());
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  /**
   * 修改用户状态
   *
   * @param reqVO
   * @return
   */
  @ApiOperation("修改用户状态")
  @PostMapping("/update-status")
  public CommonResult<Boolean> updateUserStatus(
      @Valid @RequestBody ExternalUserUpdateStatusReqVO reqVO) {
    try {
      userService.updateUserStatusBatch(reqVO.getIds(), reqVO.getStatus());
      return CommonResult.success(true);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  /**
   * 批量修改用户状态
   *
   * @param reqVO
   * @return
   */
  @ApiOperation("批量修改用户状态")
  @PostMapping("/update-status/batch")
  public CommonResult<Boolean> updateUserStatusBatch(
      @Valid @RequestBody ExternalUserUpdateStatusReqVO reqVO) {
    try {
      Boolean result = userService.updateUserStatusBatch(reqVO.getIds(), reqVO.getStatus());
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }
}
