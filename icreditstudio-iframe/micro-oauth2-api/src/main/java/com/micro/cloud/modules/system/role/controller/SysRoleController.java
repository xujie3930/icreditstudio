package com.micro.cloud.modules.system.role.controller;

import com.micro.cloud.api.CommonPage;
import com.micro.cloud.api.CommonResult;
import com.micro.cloud.modules.system.role.param.RoleUserParam;
import com.micro.cloud.modules.system.role.service.SysRoleService;
import com.micro.cloud.modules.system.role.vo.*;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserInfoVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserSimpleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "角色")
@RestController
@RequestMapping("/sys/role")
@Validated
public class SysRoleController {

  @Autowired private SysRoleService roleService;

  @ApiOperation("创建角色")
  @PostMapping("/create")
  public CommonResult<String> createRole(
      @Valid @RequestBody
          SysRoleCreateReqVO reqVO /*, @RequestHeader(value = "id") String creator*/) {
    try {
      String creator = "";
      String roleId = roleService.create(reqVO, creator);
      return CommonResult.success(roleId);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation("修改角色")
  @PostMapping("/update")
  public CommonResult<Boolean> updateRole(
      @Valid @RequestBody
          SysRoleUpdateReqVO reqVO /*, @RequestHeader(value = "id") String updater*/) {
    try {
      String updater = "";
      Boolean result = roleService.updateRole(reqVO, updater);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation("修改角色状态")
  @PostMapping("/update-status")
  public CommonResult<Boolean> updateStatus(
      @Valid @RequestBody SysRoleUpdateStatusReqVO reqVO /*,
      @RequestHeader(value = "id") String updaterId*/) {
    try {
      String updaterId = "";
      Boolean result = roleService.updateStatus(reqVO, updaterId);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation("删除角色")
  @GetMapping("/delete/{id}")
  public CommonResult<Boolean> delete(
      @PathVariable("id") String id /*, @RequestHeader(value = "id") String deleter*/) {
    try {
      String deleter = "";
      Boolean result = roleService.delete(id, deleter);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation("获得角色信息")
  @GetMapping("/get/{id}")
  public CommonResult<SysRoleRespVO> get(@PathVariable("id") String id) {
    SysRoleRespVO role = roleService.get(id);
    return CommonResult.success(role);
  }

  @ApiOperation("获得角色分页")
  @PostMapping("/page")
  public CommonResult<CommonPage<SysRoleRespVO>> page(@RequestBody SysRolePageReqVO reqVO) {
    CommonPage<SysRoleRespVO> result = roleService.page(reqVO);
    return CommonResult.success(result);
  }

  @ApiOperation(value = "获取角色精简信息列表", notes = "只包含被开启的角色，主要用于前端的下拉选项")
  @PostMapping("/list-simple")
  public CommonResult<List<SysRoleSimpleRespVO>> getSimpleRoles(
      @Valid @RequestBody RoleSimpleListReqVO reqVO) {
    // 获得角色列表，只要开启状态的
    List<SysRoleSimpleRespVO> result = roleService.getSimpleRoles(reqVO);
    return CommonResult.success(result);
  }

  @ApiOperation("获取对应角色下用户(分页)")
  @PostMapping(value = "/user/page")
  public CommonResult<CommonPage<InternalUserInfoVO>> userRoleList(
      @RequestBody SysRoleUserPageReqVO reqVO) {
    List<InternalUserInfoVO> result = roleService.getUserByRoleId(reqVO);
    return CommonResult.success(CommonPage.restPage(result));
  }

  @ApiOperation("获取对应角色下用户(列表)")
  @PostMapping(value = "/user/list")
  public CommonResult<List<InternalUserSimpleVO>> userList(@RequestBody RoleUserParam param) {
    List<InternalUserSimpleVO> result = roleService.getSimpleUserInfo(param);
    return CommonResult.success(result);
  }

  @ApiOperation("角色授权(菜单)")
  @PostMapping(value = "/auth")
  public CommonResult<Boolean> auth(@RequestBody RoleBindResourcesReqVO reqVO) {
    Boolean result = roleService.bindResource(reqVO);
    return CommonResult.success(result);
  }

  @ApiOperation("角色添加人员时查询列表")
  @PostMapping(value = "/remaining/user-page")
  public CommonResult<CommonPage<RemainingUserPageRepVO>> remainingUser(
      @Valid @RequestBody SysRoleUserPageReqVO reqVO) {
    List<RemainingUserPageRepVO> result = roleService.remainingUser(reqVO);
    return CommonResult.success(CommonPage.restPage(result));
  }

  @ApiOperation("角色添加人员")
  @PostMapping(value = "/allocate/user")
  public CommonResult<Boolean> allocateUser(@RequestBody RoleBindUsersReqVO reqVO) {
    Boolean result = roleService.allocateUser(reqVO);
    return CommonResult.success(result);
  }

  @ApiOperation("角色移除人员")
  @PostMapping(value = "/remove/user")
  public CommonResult<Boolean> removeUser(@RequestBody RoleBindUsersReqVO reqVO) {
    Boolean result = roleService.removeUser(reqVO);
    return CommonResult.success(result);
  }
}
