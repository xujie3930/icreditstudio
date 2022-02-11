package com.micro.cloud.modules.form.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.micro.cloud.api.CommonResult;
import com.micro.cloud.modules.form.dal.dataobject.Company;
import com.micro.cloud.modules.form.param.FormDataParam;
import com.micro.cloud.modules.form.param.SaveFormSchemaParam;
import com.micro.cloud.modules.form.service.WorkflowFormService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈流程表单前端控制器〉
 *
 * @author roy
 * @create 2021/12/3
 * @since 1.0.0
 */
@Api(tags = "流程表单管理")
@RestController
@RequestMapping("/process")
public class WorkflowFormController {

  @Resource private WorkflowFormService workflowFormService;

  @Value("${project.queryPcListUrl}")
  private String queryPcListUrl;

  @Resource private RestTemplate restTemplate;

  @ApiOperation(value = "保存表单结构")
  @PostMapping(value = "/save/form/schema")
  public CommonResult<Boolean> saveFormSchema(@Validated @RequestBody SaveFormSchemaParam param) {
    try {
      Boolean result = workflowFormService.saveFormSchema(param);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @PostMapping("/saveFormData")
  @ApiOperation(value = "保存表单数据")
  public CommonResult saveFormData(@RequestBody FormDataParam formData) {
    try {
      String result = workflowFormService.saveFormData(formData);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation(value = "更新表单数据")
  @PostMapping("/updateFormData")
  public CommonResult<Boolean> updateFormData(@RequestBody FormDataParam formData) {
    try {
      workflowFormService.updateFormData(formData);
      return CommonResult.success(true);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation(value = "删除表单数据")
  @PostMapping("/deleteFormData")
  public CommonResult<Boolean> deleteFormData(@RequestBody FormDataParam formData) {
    try {
      workflowFormService.deleteFormData(formData);
      return CommonResult.success(true);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation(value = "获取表单数据")
  @PostMapping("/getFormData")
  public CommonResult<Map<String, Object>> getFormData(@RequestBody FormDataParam formData) {
    Map<String, Object> result = workflowFormService.getFormData(formData);
    return CommonResult.success(result);
  }

  /** 获取参建单位列表 */
  @ApiOperation(value = "获取参建单位列表", notes = "获取参建单位列表", httpMethod = "POST")
  @PostMapping("/project/queryPcList")
  public CommonResult queryPcList() {
    List<Company> dataList = new ArrayList<>();
    String strObj = restTemplate.getForObject(queryPcListUrl, String.class);
    JSONObject genObj = JSONObject.parseObject(strObj);
    JSONArray genArray = JSONArray.parseArray(genObj.getString("data"));
    Company com = new Company();
    com.setId("0");
    com.setCompanyName("公建");
    dataList.add(com);
    if (genArray.toString() != null && genArray.size() > 0) {
      for (Object group : genArray) {
        JSONObject groupObj = (JSONObject) group;
        String companyName = groupObj.getString("companyName");
        String id = groupObj.getString("id");
        Company company = new Company();
        company.setId(id);
        company.setCompanyName(companyName);
        dataList.add(company);
      }
    }
    return CommonResult.success(dataList);
  }
}
