package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.web.controller;

import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.entity.FormHiDefinitionEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.service.FormHiDefinitionService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.web.request.FormHiDefinitionEntityPageRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.web.request.FormHiDefinitionEntityRequest;
import com.hashtech.businessframework.log.Logable;
import com.hashtech.businessframework.result.BaseController;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 *
 * @author 1
 */
@RestController
@RequestMapping("form/hi/definition")
@Api(tags = "表单定义历史")
public class FormHiDefinitionController extends BaseController<FormHiDefinitionEntity, FormHiDefinitionService> {

    @Autowired
    private FormHiDefinitionService formHiDefinitionService;

    /**
     * 分页查询列表
     */
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", httpMethod = "POST")
    @PostMapping("/pageList")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<BusinessPageResult> pageList(@ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody FormHiDefinitionEntityPageRequest pageRequest){

        BusinessPageResult page = formHiDefinitionService.queryPage(pageRequest);

        return BusinessResult.success(page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "信息", notes = "信息", httpMethod = "GET")
    @GetMapping("/info/{id}")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<FormHiDefinitionEntity> info(@ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") String id){

        FormHiDefinitionEntity formHiDefintion = formHiDefinitionService.getById(id);

        return BusinessResult.success(formHiDefintion);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping("/save")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<FormHiDefinitionEntity> save(@ApiParam(name = "保存对象", value = "传入json格式", required = true) @RequestBody FormHiDefinitionEntityRequest request){

        FormHiDefinitionEntity formHiDefintion = BeanCopyUtils.copyProperties(request, FormHiDefinitionEntity.class);

		formHiDefinitionService.save(formHiDefintion);

        return BusinessResult.success(formHiDefintion);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @PostMapping("/update")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<FormHiDefinitionEntity> update(@ApiParam(name = "修改对象", value = "传入json格式", required = true) @RequestBody FormHiDefinitionEntityRequest request){

        FormHiDefinitionEntity formHiDefintion = BeanCopyUtils.copyProperties(request, FormHiDefinitionEntity.class);

		formHiDefinitionService.updateById(formHiDefintion);

        return BusinessResult.success(formHiDefintion);
    }

    /**
     * 根绝 id 删除表单定义历史  -- 逻辑删除，将状态修改为 "已删除"
     * @param id
     * @return
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping("/delete")
    public BusinessResult<Boolean> delete(@ApiParam(name = "id", value = "主键", required = true) @RequestParam("id") String id){
        return formHiDefinitionService.deleteHiFormById(id);
    }

    /**
     * 导出excel
     */
    @ApiOperation(value = "导出excel", notes = "导出excel", httpMethod = "GET")
    @GetMapping(value = "/exportExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> exportExcel(HttpServletRequest request,HttpServletResponse response, FormHiDefinitionEntity formHiDefintion) {

        return super.exportExcel(request,response, formHiDefintion, FormHiDefinitionEntity.class, "formHiDefintion");
    }

    /**
     * 通过excel导入数据
     */
    @ApiOperation(value = "通过excel导入数据", notes = "通过excel导入数据", httpMethod = "POST")
    @PostMapping(value = "/importExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return super.importExcel(request, response, FormHiDefinitionEntity.class);
    }

}
