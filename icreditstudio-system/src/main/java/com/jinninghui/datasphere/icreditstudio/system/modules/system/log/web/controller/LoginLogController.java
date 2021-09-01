package com.jinninghui.datasphere.icreditstudio.system.modules.system.log.web.controller;

import com.jinninghui.datasphere.icreditstudio.system.modules.system.log.entity.LoginLogEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.log.service.LoginLogService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.log.web.request.LoginLogEntityDelRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.log.web.request.LoginLogEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.log.web.request.LoginLogEntityRequest;
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
@RequestMapping("log/loginlog")
public class LoginLogController extends BaseController<LoginLogEntity, LoginLogService> {

    @Autowired
    private LoginLogService loginLogService;

    /**
     * 分页查询列表
     */
//    @Log(type = Log.Type.AUDIT,operateType = Log.OperateType.SELECT)
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", httpMethod = "POST")
    @PostMapping("/pageList")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<BusinessPageResult> pageList(@ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody LoginLogEntityPageRequest pageRequest) {

        BusinessPageResult page = loginLogService.queryPage(pageRequest);

        return BusinessResult.success(page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "信息", notes = "信息", httpMethod = "GET")
    @GetMapping("/info/{id}")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<LoginLogEntity> info(@ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") String id) {

        LoginLogEntity loginLog = loginLogService.getById(id);

        return BusinessResult.success(loginLog);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping("/save")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<LoginLogEntity> save(@ApiParam(name = "保存对象", value = "传入json格式", required = true) @RequestBody LoginLogEntityRequest request) {

        LoginLogEntity loginLog = BeanCopyUtils.copyProperties(request, LoginLogEntity.class);

        loginLogService.save(loginLog);

        return BusinessResult.success(loginLog);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @PostMapping("/update")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<LoginLogEntity> update(@ApiParam(name = "修改对象", value = "传入json格式", required = true) @RequestBody LoginLogEntityRequest request) {

        LoginLogEntity loginLog = BeanCopyUtils.copyProperties(request, LoginLogEntity.class);

        loginLogService.updateById(loginLog);

        return BusinessResult.success(loginLog);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping("/delete")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<Boolean> delete(@ApiParam(name = "删除ID", value = "传入json格式", required = true) @RequestBody LoginLogEntityDelRequest params) {

        loginLogService.removeByIds(params.getIds());

        return BusinessResult.success(true);
    }

    /**
     * 导出excel
     */
    @ApiOperation(value = "导出excel", notes = "导出excel", httpMethod = "GET")
    @GetMapping(value = "/exportExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> exportExcel(HttpServletRequest request, HttpServletResponse response, LoginLogEntity loginLog) {

        return super.exportExcel(request, response, loginLog, LoginLogEntity.class, "loginLog");
    }

    /**
     * 通过excel导入数据
     */
    @ApiOperation(value = "通过excel导入数据", notes = "通过excel导入数据", httpMethod = "POST")
    @PostMapping(value = "/importExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return super.importExcel(request, response, LoginLogEntity.class);
    }

}
