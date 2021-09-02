package com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.controller;

import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.entity.FormElementEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.service.FormElementService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request.FormElementEntityDelRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request.FormElementEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request.FormElementEntityRequest;
import com.jinninghui.datasphere.icreditstudio.framework.log.Logable;
import com.jinninghui.datasphere.icreditstudio.framework.result.BaseController;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.validate.BusinessParamsValidate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author 1
 */
@RestController
@RequestMapping("form/element")
public class FormElementController extends BaseController<FormElementEntity, FormElementService> {

    @Autowired
    private FormElementService formElementService;

    /**
     * 分页查询列表
     */
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", httpMethod = "POST")
    @PostMapping("/pageList")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<BusinessPageResult> pageList(@ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody FormElementEntityPageRequest pageRequest) {

        BusinessPageResult page = formElementService.queryPage(pageRequest);

        return BusinessResult.success(page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "信息", notes = "信息", httpMethod = "GET")
    @GetMapping("/info/{id}")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<FormElementEntity> info(@ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") String id) {

        FormElementEntity formElement = formElementService.getById(id);

        return BusinessResult.success(formElement);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping("/save")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<FormElementEntity> save(@ApiParam(name = "保存对象", value = "传入json格式", required = true) @RequestBody FormElementEntityRequest request) {

        FormElementEntity formElement = BeanCopyUtils.copyProperties(request, FormElementEntity.class);

        formElementService.save(formElement);

        return BusinessResult.success(formElement);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @PostMapping("/update")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<FormElementEntity> update(@ApiParam(name = "修改对象", value = "传入json格式", required = true) @RequestBody FormElementEntityRequest request) {

        FormElementEntity formElement = BeanCopyUtils.copyProperties(request, FormElementEntity.class);

        formElementService.updateById(formElement);

        return BusinessResult.success(formElement);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping("/delete")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<Boolean> delete(@ApiParam(name = "删除ID", value = "传入json格式", required = true) @RequestBody FormElementEntityDelRequest params) {

        formElementService.removeByIds(params.getIds());

        return BusinessResult.success(true);
    }

    /**
     * 导出excel
     */
    @ApiOperation(value = "导出excel", notes = "导出excel", httpMethod = "GET")
    @GetMapping(value = "/exportExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> exportExcel(HttpServletRequest request, HttpServletResponse response, FormElementEntity formElement) {

        return super.exportExcel(request, response, formElement, FormElementEntity.class, "formElement");
    }

    /**
     * 通过excel导入数据
     */
    @ApiOperation(value = "通过excel导入数据", notes = "通过excel导入数据", httpMethod = "POST")
    @PostMapping(value = "/importExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return super.importExcel(request, response, FormElementEntity.class);
    }

}
