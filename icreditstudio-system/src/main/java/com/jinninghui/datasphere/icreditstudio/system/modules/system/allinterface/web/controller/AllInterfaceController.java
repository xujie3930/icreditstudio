package com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.web.controller;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.param.InterfaceAuthParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.param.UserAuthParams;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result.AuthResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result.InterfaceAuthResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result.SelectInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result.SelectTreeInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.service.AllInterfacesService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.web.request.OrgEntityQueryRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.web.request.RoleEntityQueryRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.entity.RoleEntity;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author hzh
 * @description
 * @date 2021/2/24 15:22
 */
@RestController()
@RequestMapping("resources")
@RequiredArgsConstructor
public class AllInterfaceController {

    private final AllInterfacesService allInterfacesService;

    /**
     * 获取用户菜单权限，用户信息
     */
    @RequestMapping(value = {"/initAuth"}, method = {RequestMethod.POST})
    @ApiOperation(value = "获取用户菜单权限，用户信息", notes = "获取用户菜单权限，用户信息", httpMethod = "POST")
    public BusinessResult<AuthResult> initAuth(@RequestHeader(value = "x-userid") String userId) throws IOException {
        UserAuthParams userAuthParams = new UserAuthParams();
        userAuthParams.setUserId(userId);

        return allInterfacesService.getAuth(userAuthParams);
    }


    /**
     * 根据用户id 获取用户接口权限
     */
    @RequestMapping(value = {"/getUserAuthInterfaceIdList"}, method = {RequestMethod.POST})
    @ApiOperation(value = "根据用户id 获取用户接口权限", notes = "根据用户id 获取用户接口权限", httpMethod = "POST")
    public BusinessResult<List<InterfaceAuthResult>> getUserAuthInterfaceIdList(@RequestHeader(value = "x-userid") String userId) {
        InterfaceAuthParam param = new InterfaceAuthParam();
        param.setUserId(userId);
        List<InterfaceAuthResult> s =  allInterfacesService.getUserAuthInterfaceIdList(param);

        return BusinessResult.success(s);
    }


    /**
     * 判断是否是超级管理员
     */
    @RequestMapping(value = {"/isAdmin"}, method = {RequestMethod.POST})
    @ApiOperation(value = "判断是否是超级管理员", notes = "判断是否是超级管理员", httpMethod = "POST")
    public BusinessResult<Boolean> isAdmin(@RequestHeader(value = "x-userid") String userId) {


        return BusinessResult.success(allInterfacesService.isAdmin(userId));
    }


    /**
     * 获取所有有效用户信息
     */
    @RequestMapping(value = {"/getAllUserInfo"}, method = {RequestMethod.POST})
    @ApiOperation(value = "获取所有有效用户信息", notes = "获取所有有效用户信息", httpMethod = "POST")
    public BusinessResult<List<SelectInfoResult>> getAllUserInfo() {

        List<SelectInfoResult> selectInfoResult = allInterfacesService.getAllUserInfo();
        return BusinessResult.success(selectInfoResult);
    }

    /**
     * 获取所有有效角色信息
     */
    @RequestMapping(value = {"/getAllRoleInfo"}, method = {RequestMethod.POST})
    @ApiOperation(value = "获取所有有效角色信息", notes = "获取所有有效角色信息", httpMethod = "POST")
    public BusinessResult<List<SelectInfoResult>> getAllRoleInfo() {

        List<SelectInfoResult> selectInfoResult = allInterfacesService.getAllRoleInfo();
        return BusinessResult.success(selectInfoResult);
    }

    /**
     * 获取所有有效组织机构树信息
     */
    @RequestMapping(value = {"/getAllOrgTreeInfo"}, method = {RequestMethod.POST})
    @ApiOperation(value = "获取所有有效组织机构树信息", notes = "获取所有有效组织机构树信息", httpMethod = "POST")
    public BusinessResult<List<SelectTreeInfoResult>> getAllOrgTreeInfo() {

        List<SelectTreeInfoResult> selectInfoResult = allInterfacesService.getAllOrgTreeInfo();
        return BusinessResult.success(selectInfoResult);
    }

    /**
     * 获取所有有效组织机构树信息
     */
    @RequestMapping(value = {"/getAllOrgInfo"}, method = {RequestMethod.POST})
    @ApiOperation(value = "获取所有有效组织机构树信息", notes = "获取所有有效组织机构树信息", httpMethod = "POST")
    public BusinessResult<List<SelectInfoResult>> getAllOrgInfo() {

        List<SelectInfoResult> selectInfoResult = allInterfacesService.getAllOrgInfo();
        return BusinessResult.success(selectInfoResult);
    }


    /**
     * 根据userId 获取有效的角色信息
     */
    @RequestMapping(value = {"/getRoleInfoByUserId"}, method = {RequestMethod.POST})
    @ApiOperation(value = "根据userId 获取有效的角色信息", notes = "根据userId 获取有效的角色信息", httpMethod = "POST")
    public BusinessResult<List<RoleEntity>> getRoleInfoByUserId(RoleEntityQueryRequest request) {

        List<RoleEntity> selectInfoResult = allInterfacesService.getRoleInfoByUserId(request);

        return BusinessResult.success(selectInfoResult);
    }

    /**
     * 根据userId 获取有效的部门信息
     */
    @RequestMapping(value = {"/getOrgInfoByUserId"}, method = {RequestMethod.POST})
    @ApiOperation(value = "根据userId 获取有效的部门信息", notes = "根据userId 获取有效的部门信息", httpMethod = "POST")
    public BusinessResult<List<OrganizationEntity>> getOrgInfoByUserId(OrgEntityQueryRequest request) {

        List<OrganizationEntity> selectInfoResult = allInterfacesService.getOrgInfoByUserId(request);

        return BusinessResult.success(selectInfoResult);
    }



}
