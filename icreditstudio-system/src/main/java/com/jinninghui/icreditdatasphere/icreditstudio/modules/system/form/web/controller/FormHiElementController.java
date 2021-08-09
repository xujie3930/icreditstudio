package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.web.controller;

import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.entity.FormHiElementEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.service.FormHiElementService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.web.request.FormHiElementEntityDelRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.web.request.FormHiElementEntityPageRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.form.web.request.FormHiElementEntityRequest;
import com.hashtech.businessframework.log.Logable;
import com.hashtech.businessframework.result.BaseController;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
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
@RequestMapping("form/hi/element")
public class FormHiElementController extends BaseController<FormHiElementEntity, FormHiElementService> {

    @Autowired
    private   FormHiElementService formHiElementService;

    /**
     * 分页查询列表
     */
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", httpMethod = "POST")
    @PostMapping("/pageList")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<BusinessPageResult> pageList(@ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody FormHiElementEntityPageRequest pageRequest){

        BusinessPageResult page = formHiElementService.queryPage(pageRequest);

        return BusinessResult.success(page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "信息", notes = "信息", httpMethod = "GET")
    @GetMapping("/info/{id}")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<FormHiElementEntity> info(@ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") String id){

        FormHiElementEntity formHiElement = formHiElementService.getById(id);

        return BusinessResult.success(formHiElement);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping("/save")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<FormHiElementEntity> save(@ApiParam(name = "保存对象", value = "传入json格式", required = true) @RequestBody FormHiElementEntityRequest request){

        FormHiElementEntity formHiElement = BeanCopyUtils.copyProperties(request, FormHiElementEntity.class);

		formHiElementService.save(formHiElement);

        return BusinessResult.success(formHiElement);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @PostMapping("/update")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<FormHiElementEntity> update(@ApiParam(name = "修改对象", value = "传入json格式", required = true) @RequestBody FormHiElementEntityRequest request){

        FormHiElementEntity formHiElement = BeanCopyUtils.copyProperties(request, FormHiElementEntity.class);

		formHiElementService.updateById(formHiElement);

        return BusinessResult.success(formHiElement);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping("/delete")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<Boolean> delete(@ApiParam(name = "删除ID", value = "传入json格式", required = true) @RequestBody FormHiElementEntityDelRequest params){

        formHiElementService.removeByIds(params.getIds());

        return BusinessResult.success(true);
    }

    /**
     * 导出excel
     */
    @ApiOperation(value = "导出excel", notes = "导出excel", httpMethod = "GET")
    @GetMapping(value = "/exportExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> exportExcel(HttpServletRequest request,HttpServletResponse response, FormHiElementEntity formHiElement) {

        return super.exportExcel(request,response, formHiElement, FormHiElementEntity.class, "formHiElement");
    }

    /**
     * 通过excel导入数据
     */
    @ApiOperation(value = "通过excel导入数据", notes = "通过excel导入数据", httpMethod = "POST")
    @PostMapping(value = "/importExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return super.importExcel(request, response, FormHiElementEntity.class);
    }

}
