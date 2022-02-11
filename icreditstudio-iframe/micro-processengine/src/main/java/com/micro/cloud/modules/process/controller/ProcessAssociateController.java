package com.micro.cloud.modules.process.controller;

import com.micro.cloud.api.CommonResult;
import com.micro.cloud.modules.process.param.ProcessAssociateSettingParam;
import com.micro.cloud.modules.process.param.SaveAssociateProcessParam;
import com.micro.cloud.modules.process.service.ProcessAssociateSettingService;
import com.micro.cloud.modules.process.service.ProcessAssociatedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 〈流程关联设置controller〉
 *
 * @author roy
 * @create 2021/12/10
 * @since 1.0.0
 */
@Api(tags = "流程关联设置")
@RestController
@RequestMapping("/process/associate")
public class ProcessAssociateController {

  @Autowired private ProcessAssociateSettingService settingService;

  @Autowired private ProcessAssociatedService associatedService;

  @ApiOperation(value = "保存关联设置")
  @PostMapping("/save/setting")
  public CommonResult<String> saveSetting(@RequestBody ProcessAssociateSettingParam param) {
    try {
      String result = settingService.saveSetting(param);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation(value = "保存流程关联数据")
  @PostMapping("/save/data")
  public CommonResult<Boolean> saveAssociateData(
      @Validated @RequestBody SaveAssociateProcessParam param) {
    try {
      Boolean result = associatedService.saveData(param);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation(value = "删除流程关联数据")
  @PostMapping("/del/data")
  public CommonResult<Boolean> delAssociateData(@RequestParam List<String> ids) {
    try {
      Boolean result = associatedService.delData(ids);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }


}
