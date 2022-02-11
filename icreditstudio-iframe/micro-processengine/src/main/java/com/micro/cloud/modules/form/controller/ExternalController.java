package com.micro.cloud.modules.form.controller;

import com.micro.cloud.api.CommonResult;
import com.micro.cloud.modules.form.param.FormDataParam;
import com.micro.cloud.modules.form.service.WorkflowFormService;
import com.micro.cloud.modules.form.validate.FormDataValidate;
import com.micro.cloud.modules.process.vo.ProcessInstanceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "外部流程管理")
@RestController
@RequestMapping("/external")
public class ExternalController {

  private final WorkflowFormService workflowFormService;

  public ExternalController(WorkflowFormService workflowFormService) {
    this.workflowFormService = workflowFormService;
  }

  @PostMapping("/saveFormData")
  @ApiOperation(value = "保存表单数据")
  @FormDataValidate
  public CommonResult<ProcessInstanceVO> saveFormData(
      @RequestBody FormDataParam formData,
      @RequestHeader("userId") String creatorId,
      @RequestHeader("realName") String username) {
    try {
      ProcessInstanceVO result =
          workflowFormService.ExternalSaveFormData(formData, creatorId, username);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }
}
