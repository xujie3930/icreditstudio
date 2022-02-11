package com.micro.cloud.modules.system.user.controller;

import com.micro.cloud.api.CommonResult;
import com.micro.cloud.api.PageUtils;

import com.micro.cloud.modules.system.org.vo.SysUserRoleRefPageReqVO;
import com.micro.cloud.modules.system.user.dataobject.SysUserRoleRef;
import com.micro.cloud.modules.system.user.service.SysUserRoleRefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前端控制器
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Api(tags = "用户-角色")
@RestController
@RequestMapping("/sys/user-role-ref")
public class SysUserRoleRefController {

  @Autowired private SysUserRoleRefService sysUserRoleRefService;

  @ApiOperation("获取用户角色对应关系分页")
  @PostMapping("/page")
  public CommonResult<PageUtils<SysUserRoleRef>> page(@RequestBody SysUserRoleRefPageReqVO reqVO)
      throws Exception {
    //    log.info(reqVO.getPageNo().toString(),reqVO.getPageSize());
    List<SysUserRoleRef> list = sysUserRoleRefService.page(reqVO);
    /* PageUtils<SysUserRoleRef> pageUtils=new PageUtils<>();*/
    return CommonResult.success(PageUtils.restPage(list, reqVO.getPageNo(), reqVO.getPageSize()));
  }
}
