package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hashtech.businessframework.result.BaseController;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.sequence.api.SequenceService;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import com.jinninghui.icreditdatasphere.icreditstudio.common.log.Log;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.result.SelectInfoResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.resources.service.result.ResourcesEntityResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.entity.RoleEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.entity.RoleResourcesMapEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.service.RoleResourcesMapService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.service.RoleService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.service.param.*;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.service.result.RoleEntityInfoResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.service.result.RoleResourcesMapEntityResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.web.request.*;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.result.UserEntityInfoResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.result.RoleEntityResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * @author hzh
 */
@RestController("res_roleController")
@RequestMapping("role/role")
@RequiredArgsConstructor
public class RoleController extends BaseController<RoleEntity, RoleService> {

    private final RoleService roleService;
    private final RoleResourcesMapService roleResourcesMapService;
    private final SequenceService generalSequence;

    //    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT, extend = "角色")
    @PostMapping("/getChildrenRoles")
    public BusinessResult<List<SelectInfoResult>> getChildrenRoles(
            @RequestBody RoleEntityQueryRequest request,
            @RequestHeader(value = "x-userid") String userId) {
        RoleEntityQueryParam params = new RoleEntityQueryParam();
        BeanCopyUtils.copyProperties(request, params);
        params.setUserId(userId);
        return roleService.getRoleTree(params);
    }

    /**
     * 分页查询列表
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", httpMethod = "POST")
    @PostMapping("/pageList")
    public BusinessResult<BusinessPageResult> pageList(@ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody RoleEntityPageRequest pageRequest) {

        BusinessPageResult page = roleService.queryPage(pageRequest);

        return BusinessResult.success(page);
    }

    /**
     * 查询列表
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @ApiOperation(value = "查询列表", notes = "查询列表", httpMethod = "POST")
    @PostMapping("/queryList")
    public BusinessResult<List> queryList(@ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody RoleEntityQueryParam queryParams) {

        QueryWrapper<RoleEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNoneEmpty(queryParams.getRoleName())) {
            wrapper = wrapper.like("ROLE_NAME", queryParams.getRoleName());
        }

        List<RoleEntity> list = roleService.list(wrapper);

        return BusinessResult.success(list);
    }


    /**
     * 信息
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @ApiOperation(value = "信息", notes = "信息", httpMethod = "GET")
    @GetMapping("/info/{id}")
    public BusinessResult<RoleEntity> info(@ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") String id) {
        RoleEntity role = roleService.getById(id);
        return BusinessResult.success(role);
    }

    /**
     * 保存
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.ADD, extend = "角色")
    @PostMapping("/save")
    @BusinessParamsValidate
    public BusinessResult<RoleEntity> save(@RequestBody RoleEntitySaveRequest request,
                                           @RequestHeader("x-userid") String userId) {
        RoleEntitySaveParam param = new RoleEntitySaveParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setAccessUserId(userId);
        return roleService.save(param);
    }

    /**
     * 修改
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.UPDATE, extend = "角色")
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @PostMapping("/update")
    @BusinessParamsValidate
    public BusinessResult<Boolean> update(@RequestBody RoleEntitySaveRequest request,
                                          @RequestHeader("x-userid") String userId) {
        RoleEntitySaveParam param = new RoleEntitySaveParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setAccessUserId(userId);
        return roleService.updateRole(param);
    }

    /**
     * 删除
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.DEL, extend = "角色")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping("/delete")
    public BusinessResult<Boolean> delete(@RequestBody RoleEntityDelRequest request,
                                          @RequestHeader("x-userid") String userId) {
        RoleEntityDelParam param = new RoleEntityDelParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setAccessUserId(userId);
        return roleService.deleteRole(param);
    }

    /**
     * 导出excel
     */
    @ApiOperation(value = "导出excel", notes = "导出excel", httpMethod = "GET")
    @GetMapping(value = "/exportExcel")
    public BusinessResult<?> exportExcel(HttpServletRequest request, HttpServletResponse response, RoleEntity role) {

        return super.exportExcel(request, response, role, RoleEntity.class, "role");
    }

    /**
     * 通过excel导入数据
     */
    @ApiOperation(value = "通过excel导入数据", notes = "通过excel导入数据", httpMethod = "POST")
    @PostMapping(value = "/importExcel")
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, RoleEntity.class);
    }


    /**
     * 根据userId查询角色信息
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @PostMapping("/getRoleInfoByUserId")
    public BusinessResult<List<RoleEntityInfoResult>> getRoleInfoByUserId(@RequestBody RoleEntityQueryRequest request) {

        RoleEntityQueryParam param = new RoleEntityQueryParam();
        BeanCopyUtils.copyProperties(request, param);
        return BusinessResult.success(roleService.getRoleInfoByUserId(param));
    }

    /**
     * 启/禁用
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.STATUS, extend = "角色")
    @PostMapping("/status")
    @BusinessParamsValidate
    public BusinessResult<Boolean> status(@RequestBody RoleEntityStatusRequest request,
                                          @RequestHeader("x-userid") String accessUserId) {

        RoleEntityStatusParams params = new RoleEntityStatusParams();
        BeanCopyUtils.copyProperties(request, params);
        params.setAccessUserId(accessUserId);
        return roleService.status(params);
    }

    /**
     * 查询角色权限
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @PostMapping("/resource")
    public BusinessResult<List<RoleResourcesMapEntityResult>> resource(@RequestBody RoleResourcesMapRequest request) {

        RoleResourcesMapParam param = new RoleResourcesMapParam();
        BeanCopyUtils.copyProperties(request, param);
        return roleResourcesMapService.resource(param);
    }

    /**
     * 设置菜单与按钮
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.UPDATE)
    @PostMapping("/resource/set")
    public BusinessResult<Boolean> setResource(@RequestBody RoleResourcesMapParam params) {

        roleResourcesMapService.remove(new QueryWrapper<RoleResourcesMapEntity>().eq("ROLE_ID", params.getRoleId()));

        List<RoleResourcesMapEntity> saveList = new ArrayList<>();
        for (String x : params.getResourceIds()) {
            RoleResourcesMapEntity roleResources = new RoleResourcesMapEntity();
            roleResources.setRoleId(params.getRoleId());
            roleResources.setResourcesId(x);
            roleResources.setId(generalSequence.nextValueString());
            saveList.add(roleResources);
        }
        roleResourcesMapService.saveBatch(saveList);

        return BusinessResult.success(true);
    }

    /**
     * 根据角色id获取用户列表
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @PostMapping("/getUserInfoByRoleId")
    public BusinessResult<List<UserEntityInfoResult>> getUserInfoByRoleId(@RequestBody RoleUserQueryParam request) {

        List<UserEntityInfoResult> roleEntityList = roleService.getUserInfoByRoleId(request);

        return BusinessResult.success(roleEntityList);
    }

    /**
     * 设置角色下的用户，参数中包含数据库不包含则新增，参数中不包含数据库包含则删除
     *
     * @param request
     * @return
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.UPDATE, extend = "角色")
    @PostMapping("/setRoleToUsers")
    public BusinessResult setRoleToUsers(@RequestBody RoleToUsersSaveRequest request) {
        RoleToUsersSaveParam params = new RoleToUsersSaveParam();
        BeanCopyUtils.copyProperties(request, params);
        return roleService.setRoleToUsers(params);
    }

    //    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @PostMapping("/getResourcesFromRole")
    public BusinessResult<List<ResourcesEntityResult>> getResourcesFromRole(@RequestBody ResourcesFromRoleQueryRequest request) {

        ResourcesFromRoleQueryParam param = new ResourcesFromRoleQueryParam();
        BeanCopyUtils.copyProperties(request, param);
        return roleService.getResourcesFromRole(param);
    }

    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.UPDATE,extend = "角色")
    @PostMapping("/setResourcesToRole")
    public BusinessResult<Boolean> setResourcesToRole(@RequestBody ResourcesToRoleSaveRequest request) {

        ResourcesToRoleSaveParam param = new ResourcesToRoleSaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return roleService.setResourcesToRole(param);
    }

    //    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @PostMapping("/getCurrAndSonRoles")
    public BusinessResult<List<RoleEntityResult>> getCurrAndSonRoles(@RequestBody CurrAndSonRoleQueryRequest request) {
        CurrAndSonRoleQueryParam param = new CurrAndSonRoleQueryParam();
        BeanCopyUtils.copyProperties(request, param);
        return roleService.getCurrAndSonRoles(param);
    }
}
