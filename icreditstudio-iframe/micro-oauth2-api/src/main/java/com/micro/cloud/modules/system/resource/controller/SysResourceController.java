package com.micro.cloud.modules.system.resource.controller;

import com.micro.cloud.api.CommonResult;
import com.micro.cloud.enums.SysCommonStatusEnum;
import com.micro.cloud.modules.system.resource.dataobject.SysResource;
import com.micro.cloud.modules.system.resource.service.SysResourceService;
import com.micro.cloud.modules.system.resource.vo.SysResourceCreateReqVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceListReqVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceNodeVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceRespVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceSimpleRespVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceTreeReqVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceUpdateReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

/** @author roy */
@Api(tags = "菜单")
@RestController
@RequestMapping("/sys/resource")
@Validated
public class SysResourceController {

  private final Logger logger = LoggerFactory.getLogger(SysResourceController.class);

  @Autowired private SysResourceService resourceService;

  @PostMapping("/create")
  @ApiOperation("创建菜单")
  public CommonResult<String> createMenu(@Valid @RequestBody SysResourceCreateReqVO reqVO) {
    try {

      String resourceId = resourceService.create(reqVO);
      return CommonResult.success(resourceId);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @PostMapping("/update")
  @ApiOperation("修改菜单")
  public CommonResult<Boolean> updateMenu(@Valid @RequestBody SysResourceUpdateReqVO reqVO) {
    try {
      Boolean result = resourceService.updateResource(reqVO);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @GetMapping("/get/{id}")
  @ApiOperation("获取菜单信息")
  public CommonResult<SysResourceRespVO> get(@PathVariable("id") String id) {
    SysResourceRespVO result = resourceService.get(id);
    return CommonResult.success(result);
  }

  @GetMapping("/delete/{id}")
  @ApiOperation("删除菜单")
  public CommonResult<Boolean> delete(@PathVariable("id") String id) {
    try {
      Boolean result = resourceService.delete(id);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @GetMapping("/list")
  @ApiOperation("获取菜单列表")
  public CommonResult<List<SysResourceRespVO>> list(SysResourceListReqVO reqVO) {
    List<SysResourceRespVO> result = resourceService.getList(reqVO);
    return CommonResult.success(result);
  }

  @GetMapping("/list-all-simple")
  @ApiOperation(value = "获取菜单精简信息列表", notes = "只包含被开启的菜单，主要用于前端的下拉选项")
  public CommonResult<List<SysResourceSimpleRespVO>> getSimpleMenus() {
    // 获得菜单列表，只要开启状态的
    SysResourceListReqVO reqVO = new SysResourceListReqVO();
    reqVO.setStatus(SysCommonStatusEnum.ENABLE.getStatus());
    List<SysResourceSimpleRespVO> result = resourceService.getSimpleList(reqVO);
    return CommonResult.success(result);
  }

  /**
   * 权限树形结构(异步)
   *
   * @param reqVO 权限树形结构查询参数
   * @return 树形结构
   */
  @ApiOperation("权限树形(同步)")
  @PostMapping(value = "/tree-sync")
  public CommonResult<List<SysResourceNodeVO>> queryDepartTreeSync(
      @RequestBody SysResourceTreeReqVO reqVO) {
    try {
      List<SysResourceNodeVO> result = resourceService.queryTreeSync(reqVO);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed("权限树形结构获取失败");
    }
  }

  /**
   * 获取nacos客户端列表
   *
   * @return 树形结构
   */
  @ApiOperation("同步客户端接口列表数据")
  @GetMapping(value = "/clients-info/{uri}")
  public CommonResult<Boolean> generateClientsApiInfo(@PathVariable String uri) {
    try {
      Boolean result = resourceService.generateClientsApiInfo(uri);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed("数据同步失败");
    }
  }
}
