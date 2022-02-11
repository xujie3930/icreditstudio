package com.micro.cloud.modules.system.dict.controller;

import com.micro.cloud.api.CommonPage;
import com.micro.cloud.api.CommonResult;
import com.micro.cloud.modules.system.dict.service.SysDictTypeService;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeCreateReqVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypePageReqVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeRespVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeSimpleRespVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeUpdateReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典类型管理
 *
 * @author roy
 */
@Api(tags = "字典类型")
@RestController
@RequestMapping("/sys/dict-type")
@Validated
public class SysDictTypeController {

  @Autowired private SysDictTypeService dictTypeService;

  @PostMapping("/create")
  @ApiOperation("创建字典类型")
  public CommonResult<String> createDictType(@Valid @RequestBody SysDictTypeCreateReqVO reqVO) {
    String dictTypeId = dictTypeService.createDictType(reqVO);
    return CommonResult.success(dictTypeId);
  }

  @PutMapping("/update")
  @ApiOperation("修改字典类型")
  public CommonResult<Boolean> updateDictType(@Valid @RequestBody SysDictTypeUpdateReqVO reqVO) {
    dictTypeService.updateDictType(reqVO);
    return CommonResult.success(true);
  }

  @DeleteMapping("/delete/{id}")
  @ApiOperation("删除字典类型")
  @ApiImplicitParam(
      name = "id",
      value = "编号",
      required = true,
      example = "1024",
      dataTypeClass = Long.class)
  public CommonResult<Boolean> deleteDictType(@PathVariable(value = "id") String id) {
    dictTypeService.deleteDictType(id);
    return CommonResult.success(true);
  }

  @ApiOperation("/获得字典类型的分页列表")
  @GetMapping("/page")
  public CommonResult<CommonPage<SysDictTypeRespVO>> pageDictTypes(
      @Valid SysDictTypePageReqVO reqVO) {
    List<SysDictTypeRespVO> result = dictTypeService.getDictTypePage(reqVO);
    return CommonResult.success(CommonPage.restPage(result));
  }

  @ApiOperation("/查询字典类型详细")
  @GetMapping(value = "/get/{id}")
  public CommonResult<SysDictTypeRespVO> getDictType(@PathVariable(value = "id") String id) {
    SysDictTypeRespVO result = dictTypeService.getDictType(id);
    return CommonResult.success(result);
  }

  @GetMapping("/list-all-simple")
  @ApiOperation(value = "获得全部字典类型列表", notes = "包括开启 + 禁用的字典类型，主要用于前端的下拉选项")
  public CommonResult<List<SysDictTypeSimpleRespVO>> listSimpleDictTypes() {
    List<SysDictTypeSimpleRespVO> result = dictTypeService.getDictTypeList();
    return CommonResult.success(result);
  }
}
