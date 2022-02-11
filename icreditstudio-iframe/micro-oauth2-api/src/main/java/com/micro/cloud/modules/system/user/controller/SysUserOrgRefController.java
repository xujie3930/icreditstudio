package com.micro.cloud.modules.system.user.controller;

import com.micro.cloud.api.CommonResult;
import com.micro.cloud.api.PageUtils;
import com.micro.cloud.modules.system.org.vo.SysUserOrgRefPageReqVO;
import com.micro.cloud.modules.system.user.dataobject.SysUserOrgRef;
import com.micro.cloud.modules.system.user.service.SysUserOrgRefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端控制器
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Api(tags = "用户-组织机构")
@RestController
@RequestMapping("/sys/user-org-ref")
public class SysUserOrgRefController {

  @Autowired
  private SysUserOrgRefService sysUserOrgRefService;

  @ApiOperation("获取部门分页")
  @PostMapping("/page")
  public CommonResult<PageUtils<SysUserOrgRef>> page(@RequestBody SysUserOrgRefPageReqVO reqVO)
      throws Exception {
    List<SysUserOrgRef> list = sysUserOrgRefService.page(reqVO);
    return CommonResult.success(PageUtils.restPage(list, reqVO.getPageNo(), reqVO.getPageSize()));
  }
}
