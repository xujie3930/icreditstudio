package com.micro.cloud.modules.system.dict.controller;

import com.micro.cloud.api.CommonPage;
import com.micro.cloud.api.CommonResult;
import com.micro.cloud.modules.system.dict.convert.SysDictDataConvert;
import com.micro.cloud.modules.system.dict.dataobject.SysDictData;
import com.micro.cloud.modules.system.dict.service.SysDictDataService;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataCreateReqVO;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataPageReqVO;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataRespVO;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataSimpleRespVO;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataUpdateReqVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeSimpleRespVO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** @author roy */
@Api(tags = "字典数据")
@RestController
@RequestMapping("/sys/dict-data")
@Validated
public class SysDictDataController {

  @Autowired private SysDictDataService dictDataService;

  @PostMapping("/create")
  @ApiOperation("新增字典数据")
  public CommonResult<String> createDictData(@Valid @RequestBody SysDictDataCreateReqVO reqVO) {
    try {
      String dictDataId = dictDataService.createDictData(reqVO);
      return CommonResult.success(dictDataId);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @PutMapping("update")
  @ApiOperation("修改字典数据")
  public CommonResult<Boolean> updateDictData(@Valid @RequestBody SysDictDataUpdateReqVO reqVO) {
    try {
      Boolean result = dictDataService.updateDictData(reqVO);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @DeleteMapping("/delete/{id}")
  @ApiOperation("删除字典数据")
  public CommonResult<Boolean> deleteDictData(@PathVariable(value = "id") String id) {
    try {
      Boolean result = dictDataService.deleteDictData(id);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation(value = "获得全部字典数据列表", notes = "一般用于管理后台缓存字典数据在本地")
  @PostMapping("/list-all-simple")
  public CommonResult<List<SysDictDataSimpleRespVO>> listSimple() {
    List<SysDictData> list = dictDataService.getDictDatas();
    return CommonResult.success(SysDictDataConvert.INSTANCE.convertList(list));
  }

  @GetMapping("/page")
  @ApiOperation("/获得字典类型的分页列表")
  public CommonResult<CommonPage<SysDictDataRespVO>> getDictTypePage(
      @Valid SysDictDataPageReqVO reqVO) {
    List<SysDictDataRespVO> result = dictDataService.getDictDataPage(reqVO);
    return CommonResult.success(CommonPage.restPage(result));
  }

  @GetMapping(value = "/get")
  @ApiOperation("/查询字典数据详细")
  public CommonResult<SysDictDataRespVO> getDictData(@RequestParam("id") String id) {
    return CommonResult.success(
        SysDictDataConvert.INSTANCE.convert(dictDataService.getDictData(id)));
  }
}
