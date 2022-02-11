package com.micro.cloud.modules.system.org.controller;

import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.micro.cloud.api.CommonPage;
import com.micro.cloud.api.CommonResult;
import com.micro.cloud.modules.system.org.param.OrgUserParam;
import com.micro.cloud.modules.system.org.service.SysOrgService;
import com.micro.cloud.modules.system.org.vo.OrgUpdateStatusReqVO;
import com.micro.cloud.modules.system.org.vo.SysDepartTreeModel;
import com.micro.cloud.modules.system.org.vo.SysOrgCreateReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgInfoRespVO;
import com.micro.cloud.modules.system.org.vo.SysOrgListReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgNodeVO;
import com.micro.cloud.modules.system.org.vo.SysOrgPageReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgRespVO;
import com.micro.cloud.modules.system.org.vo.SysOrgSimpleRespVO;
import com.micro.cloud.modules.system.org.vo.SysOrgTreeReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgUpdateReqVO;
import com.micro.cloud.modules.system.role.vo.SysRoleUserPageReqVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserInfoVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserSimpleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.util.UriEncoder;

/**
 * 〈组织机构管理〉
 *
 * @author roy
 * @create 2021/11/11
 * @since 1.0.0
 */
@Api(tags = "组织机构管理")
@RestController
@RequestMapping("/sys/org")
@Validated
public class SysOrgController {

  private Logger logger = LoggerFactory.getLogger(SysOrgController.class);

  @Autowired private SysOrgService deptService;

  @ApiOperation("创建部门")
  @PostMapping("/create")
  public CommonResult<String> create(@Valid @RequestBody SysOrgCreateReqVO reqVO) {
    try {
      String deptId = deptService.createDept(reqVO);
      return CommonResult.success(deptId);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation("更新部门")
  @PostMapping("/update")
  public CommonResult<Boolean> update(@Valid @RequestBody SysOrgUpdateReqVO reqVO) {
    try {
      deptService.updateDept(reqVO);
      return CommonResult.success(true);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation("更新部门状态")
  @PostMapping("/update-status")
  public CommonResult<Boolean> updateStatus(@Valid @RequestBody OrgUpdateStatusReqVO reqVO) {
    try {
      Boolean result = deptService.changeStatusBatch(reqVO);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation("删除部门")
  @GetMapping("/delete/{id}")
  public CommonResult<Boolean> delete(@PathVariable("id") String id) {
    try {
      deptService.deleteDept(id);
      return CommonResult.success(true);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation("批量删除部门")
  @PostMapping("/deleteOrgBatch")
  public CommonResult<Boolean> deleteBatch(@RequestBody List<String> ids) {
    try {
      deptService.deleteOrgBatch(ids);
      return CommonResult.success(true);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation("获取部门分页")
  @PostMapping("/page")
  public CommonResult<CommonPage<SysOrgRespVO>> page(
      @RequestBody SysOrgPageReqVO reqVO, @RequestHeader(value = "userInfo") String userInfo) {
    IPage<SysOrgRespVO> page = deptService.page(reqVO);
    return CommonResult.success(CommonPage.restPage(page, reqVO));
  }

  @ApiOperation(value = "获取部门精简信息列表", notes = "只包含被开启的部门，主要用于前端的下拉选项")
  @PostMapping("/list-simple")
  public CommonResult<List<SysOrgSimpleRespVO>> getSimpleOrg(@RequestBody SysOrgListReqVO reqVO) {
    // 获得部门列表，只要开启状态的
    List<SysOrgSimpleRespVO> result = deptService.getSimpleOrg(reqVO);
    return CommonResult.success(result);
  }

  @ApiOperation("获得部门信息")
  @GetMapping("/get/{id}")
  public CommonResult<SysOrgInfoRespVO> get(@PathVariable("id") String id) {
    return CommonResult.success(deptService.getDept(id));
  }

  /**
   * 异步获取部门树形
   *
   * @param reqVO 前端回显传递
   * @return 树形结构
   */
  @ApiOperation("获取组织机构树形结构")
  @PostMapping(value = "/tree-sync")
  public CommonResult<List<SysOrgNodeVO>> queryTreeSync(@RequestBody SysOrgTreeReqVO reqVO) {
    try {
      List<SysOrgNodeVO> result = deptService.queryTreeAsync(reqVO);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed("部门树形获取失败");
    }
  }

  /**
   * 部门搜索功能方法,根据关键字模糊搜索相关部门
   *
   * @param reqVO 关键字
   * @return
   */
  @ApiOperation("根据关键字搜索")
  @PostMapping(value = "/searchBy")
  public CommonResult<List<SysDepartTreeModel>> searchBy(@RequestBody SysOrgTreeReqVO reqVO) {
    // 部门查询，myDeptSearch为1时为我的部门查询，登录用户为上级时查只查负责部门下数据
    List<SysDepartTreeModel> treeList = deptService.searchBy(reqVO);
    return CommonResult.success(treeList);
  }

  @ApiOperation("获取部门下用户")
  @PostMapping(value = "/user/list")
  public CommonResult<List<InternalUserSimpleVO>> userRoleList(@RequestBody OrgUserParam param) {
    List<InternalUserSimpleVO> result = deptService.getUserByDeptId(param);
    return CommonResult.success(result);
  }

  @ApiOperation("获取所有部门分页")
  @PostMapping("/selAllOrg")
  public CommonResult<CommonPage<SysOrgRespVO>> selAllpage(
          @RequestBody SysOrgPageReqVO reqVO, @RequestHeader(value = "userInfo") String userInfo) {
    IPage<SysOrgRespVO> page = deptService.selAllOrg(reqVO);
    return CommonResult.success(CommonPage.restPage(page, reqVO));
  }
}
