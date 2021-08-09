package com.jinninghui.datasphere.icreditstudio.modules.system.resources.web.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hashtech.businessframework.result.BaseController;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import com.jinninghui.datasphere.icreditstudio.common.log.Log;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.entity.ResourcesEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.service.ResourcesService;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.service.param.ResEntityDelParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.service.param.ResourcesEntityQueryParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.service.param.ResourcesEntitySaveParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.service.param.RolesToResourceSaveParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.service.result.ResourcesEntityResult;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.web.request.*;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.web.result.ResourcesEntityExport;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.entity.RoleEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @author hzh
 */
@RestController
@RequestMapping("resources/resources")
@RequiredArgsConstructor
public class ResourcesController extends BaseController<ResourcesEntity, ResourcesService> {

    private final ResourcesService resourcesService;

    /**
     * 分页查询列表
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @PostMapping("/pageList")
    public BusinessResult<BusinessPageResult> pageList(@RequestBody ResourcesEntityPageRequest pageRequest) {

        BusinessPageResult page = resourcesService.queryPage(pageRequest);

        return BusinessResult.success(page);
    }

    /**
     * 查询列表
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT, extend = "模块")
    @PostMapping("/queryList")
    public BusinessResult<List<ResourcesEntityResult>> queryList(@RequestBody ResourcesEntityQueryRequest request,
                                                                 @RequestHeader("x-userid") String accessUserId) {
        ResourcesEntityQueryParam param = new ResourcesEntityQueryParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setAccessUserId(accessUserId);
        return resourcesService.queryList(param);
    }


    /**
     * 信息
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @GetMapping("/info/{id}")
    public BusinessResult<ResourcesEntity> info(@PathVariable("id") String id) {

        ResourcesEntity resources = resourcesService.getById(id);

        return BusinessResult.success(resources);
    }

    /**
     * 保存
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.ADD, extend = "模块")
    @PostMapping("/save")
    @BusinessParamsValidate
    public BusinessResult<Boolean> save(@RequestBody ResourcesEntitySaveRequest request) {
        ResourcesEntitySaveParam param = new ResourcesEntitySaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return resourcesService.save(param);
    }

    /**
     * 修改
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.UPDATE, extend = "模块")
    @PostMapping("/update")
    public BusinessResult<ResourcesEntityResult> update(@RequestBody ResourcesEntitySaveRequest request) {
        ResourcesEntitySaveParam param = new ResourcesEntitySaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return resourcesService.edit(param);
    }

    /**
     * 删除
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.DEL, extend = "模块")
    @PostMapping("/delete")
    public BusinessResult<Boolean> delete(@RequestBody ResEntityDelRequest request) {
        ResEntityDelParam param = new ResEntityDelParam();
        BeanCopyUtils.copyProperties(request, param);
        return resourcesService.delete(param);
    }

    /**
     * 导出excel
     */
    @ApiOperation(value = "导出excel", notes = "导出excel", httpMethod = "GET")
    @GetMapping(value = "/exportExcel")
    public BusinessResult<?> exportExcel(HttpServletRequest request, HttpServletResponse response, ResourcesEntity resources) {

        return resourcesService.exportExcel(request, response, resources);
    }

    /**
     * 通过excel导入数据
     */
    @ApiOperation(value = "通过excel导入数据", notes = "通过excel导入数据", httpMethod = "POST")
    @PostMapping(value = "/importExcel")
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return resourcesService.importExcel(request, response, ResourcesEntityExport.class);
    }


    /**
     * 根据角色id 查询用户菜单
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @ApiOperation(value = "根据角色id 查询用户菜单", notes = "根据角色id 查询用户菜单", httpMethod = "POST")
    @PostMapping("/getMenuInfoByRoleIds")
    public BusinessResult<List<ResourcesEntity>> getMenuInfoByRoleIds(@ApiParam(name = "查询参数对象", value = "传入json格式",
            required = true) @RequestBody ResourcesQueryParams request) {

        List<ResourcesEntity> resourcesEntityList = resourcesService.getMenuInfoByRoleIds(request);

        return BusinessResult.success(resourcesEntityList);
    }

    /**
     * 禁用用户 资源
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.STATUS, extend = "模块")
    @ApiOperation(value = "禁用资源 启用资源", notes = "禁用资源 启用资源", httpMethod = "POST")
    @PostMapping("/changeResStatusByIds")
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<?> changeResStatusByIds(@ApiParam(name = "条件对象", value = "传入json格式",
            required = true) @RequestBody ResChangeStatusRequest params) {

        resourcesService.update(new UpdateWrapper<ResourcesEntity>()
                .set("delete_flag", params.getDeleteFlag())
                .in("id", params.getResourcesIdList()));

        return BusinessResult.success(true);
    }


    /**
     * 根据菜单主键id获取关联的角色列表
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @ApiOperation(value = "根据菜单主键id获取关联的角色列表", notes = "根据菜单主键id获取关联的角色列表", httpMethod = "POST")
    @PostMapping("/getRoleInfoByMenuIds")
    public BusinessResult<List<RoleEntity>> getRoleInfoByMenuIds(@ApiParam(name = "查询参数对象", value = "传入json格式",
            required = true) @RequestBody ResourcesQueryRoleParams request) {

        List<RoleEntity> roleEntityList = resourcesService.getRoleInfoByMenuIds(request);

        return BusinessResult.success(roleEntityList);
    }


    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.UPDATE,extend = "模块")
    @PostMapping("/setRoleToResources")
    public BusinessResult<Boolean> setResourcesToRole(@RequestBody RolesToResourceSaveRequest request) {
        RolesToResourceSaveParam param = new RolesToResourceSaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return resourcesService.setRolesToResource(param);
    }
}
