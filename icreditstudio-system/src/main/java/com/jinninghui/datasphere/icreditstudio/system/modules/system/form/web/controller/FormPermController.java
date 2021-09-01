package com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.controller;

import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.entity.FormPermEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.service.FormPermService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request.FormPermEntityDelRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request.FormPermEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request.FormPermEntityRequest;
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
@RequestMapping("form/perm")
public class FormPermController extends BaseController<FormPermEntity, FormPermService> {

    @Autowired
    private FormPermService formPermService;

    /**
     * 分页查询列表
     */
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", httpMethod = "POST")
    @PostMapping("/pageList")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<BusinessPageResult> pageList(@ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody FormPermEntityPageRequest pageRequest) {

        BusinessPageResult page = formPermService.queryPage(pageRequest);

        return BusinessResult.success(page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "信息", notes = "信息", httpMethod = "GET")
    @GetMapping("/info/{formId}")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<FormPermEntity> info(@ApiParam(name = "formId", value = "主键", required = true) @PathVariable("formId") String formId) {

        FormPermEntity formPerm = formPermService.getById(formId);

        return BusinessResult.success(formPerm);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping("/save")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<FormPermEntity> save(@ApiParam(name = "保存对象", value = "传入json格式", required = true) @RequestBody FormPermEntityRequest request) {

        FormPermEntity formPerm = BeanCopyUtils.copyProperties(request, FormPermEntity.class);

        formPermService.save(formPerm);

        return BusinessResult.success(formPerm);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @PostMapping("/update")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<FormPermEntity> update(@ApiParam(name = "修改对象", value = "传入json格式", required = true) @RequestBody FormPermEntityRequest request) {

        FormPermEntity formPerm = BeanCopyUtils.copyProperties(request, FormPermEntity.class);

        formPermService.updateById(formPerm);

        return BusinessResult.success(formPerm);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping("/delete")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<Boolean> delete(@ApiParam(name = "删除ID", value = "传入json格式", required = true) @RequestBody FormPermEntityDelRequest params) {

        formPermService.removeByIds(params.getIds());

        return BusinessResult.success(true);
    }

    /**
     * 导出excel
     */
    @ApiOperation(value = "导出excel", notes = "导出excel", httpMethod = "GET")
    @GetMapping(value = "/exportExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> exportExcel(HttpServletRequest request, HttpServletResponse response, FormPermEntity formPerm) {

        return super.exportExcel(request, response, formPerm, FormPermEntity.class, "formPerm");
    }

    /**
     * 通过excel导入数据
     */
    @ApiOperation(value = "通过excel导入数据", notes = "通过excel导入数据", httpMethod = "POST")
    @PostMapping(value = "/importExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return super.importExcel(request, response, FormPermEntity.class);
    }

}
