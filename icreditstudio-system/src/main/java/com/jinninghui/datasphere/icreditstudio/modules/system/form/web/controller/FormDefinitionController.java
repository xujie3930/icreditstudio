package com.jinninghui.datasphere.icreditstudio.modules.system.form.web.controller;

import com.jinninghui.datasphere.icreditstudio.common.log.Log;
import com.jinninghui.datasphere.icreditstudio.modules.system.form.entity.FormDefinitionEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.form.service.FormDefinitionService;
import com.jinninghui.datasphere.icreditstudio.modules.system.form.service.result.FormDefinitionResult;
import com.hashtech.businessframework.log.Logable;
import com.hashtech.businessframework.result.BaseController;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import com.jinninghui.datasphere.icreditstudio.modules.system.form.service.param.*;
import com.jinninghui.datasphere.icreditstudio.modules.system.form.web.request.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 表单定义模板表
 *
 * @author 1
 */
@RestController
@RequestMapping("form/definition")
@Api(tags = "表单定义")
public class FormDefinitionController extends BaseController<FormDefinitionEntity, FormDefinitionService> {

    @Autowired
    private FormDefinitionService formDefinitionService;

    /**
     * 分页查询列表
     */
    @PostMapping("/pageList")
    @Logable
    public BusinessResult<BusinessPageResult> pageList(@RequestBody FormDefinitionPageRequest request) {

        FormDefinitionPageParam param = new FormDefinitionPageParam();
        BeanCopyUtils.copyProperties(request, param);
        return formDefinitionService.queryPage(param);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "信息", notes = "信息", httpMethod = "GET")
    @GetMapping("/info/{id}")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<FormDefinitionEntity> info(@ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") String id) {

        FormDefinitionEntity formDefinition = formDefinitionService.getById(id);

        return BusinessResult.success(formDefinition);
    }

    /**
     * 删除  --  逻辑删除，将状态改为“已删除”
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.DEL, extend = "表单定义")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping("/delete")
    public BusinessResult<Boolean> delete(@ApiParam(name = "表单定义主键", value = "传入json格式", required = true) @RequestBody FormDefinitionEntityDelRequest request) {
        FormDefinitionEntityDelParam param = new FormDefinitionEntityDelParam();
        BeanCopyUtils.copyProperties(request, param);
        return formDefinitionService.deleteFormByIds(param);
    }

    /**
     * 表单定义保存
     */
    @PostMapping("/save")
    @Logable
    public BusinessResult<Boolean> save(@RequestHeader("x-userid") String userId, @RequestBody FormDefinitionSaveRequest request) {
        FormDefinitionSaveParam param = new FormDefinitionSaveParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setUserId(userId);
        return formDefinitionService.saveDef(param);
    }

    /**
     * 表单定义保存
     */
    @PostMapping("/publish")
    @Logable
    public BusinessResult<Boolean> publish(@RequestHeader("x-userid") String userId, @RequestBody FormDefinitionPublishRequest request) {
        FormDefinitionPublishParam param = new FormDefinitionPublishParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setUserId(userId);
        return formDefinitionService.publish(param);
    }

    @PostMapping("/formDetail")
    @Logable
    public BusinessResult<FormDefinitionResult> formDetail(@RequestBody FormDetailQueryRequest request) {
        FormDetailQueryParam param = new FormDetailQueryParam();
        BeanCopyUtils.copyProperties(request, param);
        return formDefinitionService.formDetail(param);
    }

    @PostMapping("/disable")
    @Logable
    public BusinessResult<Boolean> disable(@RequestBody FormDisableRequest request) {
        FormDisableParam param = new FormDisableParam();
        BeanCopyUtils.copyProperties(request, param);
        return formDefinitionService.disable(param);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @PostMapping("/update")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<FormDefinitionEntity> update(@ApiParam(name = "修改对象", value = "传入json格式", required = true) @RequestBody FormDefinitionSaveRequest request) {

        FormDefinitionEntity formDefinition = BeanCopyUtils.copyProperties(request, FormDefinitionEntity.class);

        formDefinitionService.updateById(formDefinition);

        return BusinessResult.success(formDefinition);
    }

    /**
     * 导出excel
     */
    @ApiOperation(value = "导出excel", notes = "导出excel", httpMethod = "GET")
    @GetMapping(value = "/exportExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> exportExcel(HttpServletRequest request, HttpServletResponse response, FormDefinitionEntity formDefinition) {

        return super.exportExcel(request, response, formDefinition, FormDefinitionEntity.class, "formDefinition");
    }

    /**
     * 通过excel导入数据
     */
    @ApiOperation(value = "通过excel导入数据", notes = "通过excel导入数据", httpMethod = "POST")
    @PostMapping(value = "/importExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return super.importExcel(request, response, FormDefinitionEntity.class);
    }

}
