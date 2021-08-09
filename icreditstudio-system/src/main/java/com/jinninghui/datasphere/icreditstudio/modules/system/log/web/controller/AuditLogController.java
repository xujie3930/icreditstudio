package com.jinninghui.datasphere.icreditstudio.modules.system.log.web.controller;

import com.jinninghui.datasphere.icreditstudio.modules.system.log.entity.AuditLogEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.log.service.AuditLogService;
import com.jinninghui.datasphere.icreditstudio.modules.system.log.web.request.AuditLogEntityDelRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.log.web.request.AuditLogEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.log.web.request.AuditLogEntityRequest;
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
@RequestMapping("log/auditlog")
public class AuditLogController extends BaseController<AuditLogEntity, AuditLogService> {

    @Autowired
    private   AuditLogService auditLogService;

    /**
     * 分页查询列表
     */
//    @Log(type = Log.Type.AUDIT,operateType = Log.OperateType.SELECT)
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", httpMethod = "POST")
    @PostMapping("/pageList")
    @Logable
    public BusinessResult<BusinessPageResult> pageList(@ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody AuditLogEntityPageRequest pageRequest){

        BusinessPageResult page = auditLogService.queryPage(pageRequest);

        return BusinessResult.success(page);
    }


    /**
     * 信息
     */
//    @Log(type = Log.Type.AUDIT,operateType = Log.OperateType.SELECT)
    @ApiOperation(value = "信息", notes = "信息", httpMethod = "GET")
    @GetMapping("/info/{id}")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<AuditLogEntity> info(@ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") String id){

        AuditLogEntity auditLog = auditLogService.getById(id);

        return BusinessResult.success(auditLog);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping("/save")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<AuditLogEntity> save(@ApiParam(name = "保存对象", value = "传入json格式", required = true) @RequestBody AuditLogEntityRequest request){

        AuditLogEntity auditLog = BeanCopyUtils.copyProperties(request, AuditLogEntity.class);

		auditLogService.save(auditLog);

        return BusinessResult.success(auditLog);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @PostMapping("/update")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<AuditLogEntity> update(@ApiParam(name = "修改对象", value = "传入json格式", required = true) @RequestBody AuditLogEntityRequest request){

        AuditLogEntity auditLog = BeanCopyUtils.copyProperties(request, AuditLogEntity.class);

		auditLogService.updateById(auditLog);

        return BusinessResult.success(auditLog);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping("/delete")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<Boolean> delete(@ApiParam(name = "删除ID", value = "传入json格式", required = true) @RequestBody AuditLogEntityDelRequest params){

        auditLogService.removeByIds(params.getIds());

        return BusinessResult.success(true);
    }

    /**
     * 导出excel
     */
    @ApiOperation(value = "导出excel", notes = "导出excel", httpMethod = "GET")
    @GetMapping(value = "/exportExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> exportExcel(HttpServletRequest request,HttpServletResponse response, AuditLogEntity auditLog) {

        return super.exportExcel(request,response, auditLog, AuditLogEntity.class, "auditLog");
    }

    /**
     * 通过excel导入数据
     */
    @ApiOperation(value = "通过excel导入数据", notes = "通过excel导入数据", httpMethod = "POST")
    @PostMapping(value = "/importExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return super.importExcel(request, response, AuditLogEntity.class);
    }

}
