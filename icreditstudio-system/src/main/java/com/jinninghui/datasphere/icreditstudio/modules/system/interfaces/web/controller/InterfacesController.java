package com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.web.controller;

import com.jinninghui.datasphere.icreditstudio.common.log.Log;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.service.param.InterfacesDelParam;
import com.hashtech.businessframework.result.BaseController;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.entity.InterfacesEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.service.InterfacesService;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.web.request.InterfacesDelRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.web.request.InterfacesEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.web.request.InterfacesEntitySaveParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.web.result.InterfacesInfoExpert;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author hzh
 */
@RestController
@RequestMapping("interfaces/interfaces")
@RequiredArgsConstructor
public class InterfacesController extends BaseController<InterfacesEntity, InterfacesService> {

    private final InterfacesService interfacesService;

    /**
     * 分页查询列表
     */
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", httpMethod = "POST")
    @PostMapping("/pageList")
    public BusinessResult<BusinessPageResult> pageList(@ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody InterfacesEntityPageRequest pageRequest) {

        BusinessPageResult page = interfacesService.queryPage(pageRequest);

        return BusinessResult.success(page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "信息", notes = "信息", httpMethod = "GET")
    @GetMapping("/info/{interfaceId}")
    public BusinessResult<InterfacesEntity> info(@ApiParam(name = "interfaceId", value = "主键", required = true) @PathVariable("interfaceId") Long interfaceId) {

        InterfacesEntity interfaces = interfacesService.getById(interfaceId);

        return BusinessResult.success(interfaces);
    }

    /**
     * 修改
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.UPDATE, extend = "接口")
    @PostMapping("/update")
    public BusinessResult<Boolean> update(@RequestBody InterfacesEntitySaveParam request) {
        InterfacesEntitySaveParam param = new InterfacesEntitySaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return interfacesService.update(param);
    }

    /**
     * 保存
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.ADD, extend = "接口")
    @PostMapping("/save")
    public BusinessResult<Boolean> save(@RequestBody InterfacesEntitySaveParam request) {
        InterfacesEntitySaveParam param = new InterfacesEntitySaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return interfacesService.save(param);
    }

    /**
     * 删除
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.DEL, extend = "接口")
    @PostMapping("/delete")
    public BusinessResult<Boolean> delete(@RequestBody InterfacesDelRequest request) {
        InterfacesDelParam param = new InterfacesDelParam();
        BeanCopyUtils.copyProperties(request, param);
        return interfacesService.delete(param);
    }

    /**
     * 导出excel
     */
    @ApiOperation(value = "导出excel", notes = "导出excel", httpMethod = "GET")
    @GetMapping(value = "/exportExcel")
    public BusinessResult<?> exportExcel(HttpServletRequest request, HttpServletResponse response, InterfacesEntity interfaces) {

        return interfacesService.exportExcel(request, response, interfaces);
    }

    /**
     * 通过excel导入数据
     */
    @ApiOperation(value = "通过excel导入数据", notes = "通过excel导入数据", httpMethod = "POST")
    @PostMapping(value = "/importExcel")
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return interfacesService.importExcel(request, response, InterfacesInfoExpert.class);
    }

}
