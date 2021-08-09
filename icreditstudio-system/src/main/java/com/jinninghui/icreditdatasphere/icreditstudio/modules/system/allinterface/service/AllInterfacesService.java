package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.service;

import com.hashtech.businessframework.result.BusinessResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.param.InterfaceAuthParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.param.UserAuthParams;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.result.*;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.web.request.OrgEntityQueryRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.web.request.RoleEntityQueryRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.entity.RoleEntity;

import java.io.IOException;
import java.util.List;

/**
 * @author hzh
 */
public interface AllInterfacesService {

    /**
     * 获取用户菜单权限，用户信息
     */
    BusinessResult<AuthResult> getAuth(UserAuthParams userAuthParams) throws IOException;

    /**
     * 根据用户id 获取用户接口权限
     */
    List<InterfaceAuthResult> getUserAuthInterfaceIdList(InterfaceAuthParam param);
    /**
     * 判断是否是超级管理员
     */
    boolean isAdmin(String userId);
    /**
     * 获取所有有效角色信息
     */
    List<SelectInfoResult> getAllRoleInfo();

    /**
     * 获取所有有效组织机构树信息
     */
    List<SelectTreeInfoResult> getAllOrgTreeInfo();

    /**
     * 获取所有有效用户信息
     */
    List<SelectInfoResult> getAllUserInfo();

    /**
     * 获取所有有效组织机构树信息
     */
    List<SelectInfoResult> getAllOrgInfo();

    /**
     * 根据角色id或者组织id 查询信息
     *
     * @param ids 角色id或者组织id
     * @return
     */
    List<SelectInfoResult> getFlowableApproveGroupInfoByGroupIds(List<String> ids);

    /**
     * 根据角色id或者组织id 查询用户信息
     *
     * @param ids 角色id或者组织id
     * @return
     */
    List<SelectInfoResult> getFlowableApproveUserInfoByGroupIds(List<String> ids);

    /**
     * 查询角色或者组织信息
     *
     * @param ids 用户id
     * @return
     */
    List<SelectInfoResult> getFlowableApproveGroupInfoByUserIds(List<String> ids);

    /**
     * 查询角色或者组织信息与用户的关系
     *
     * @param ids 用户id
     * @return
     */
    List<UserGroupMapResult> getFlowableUserGroupMapByUserIds(List<String> ids);

    /**
     * 查询角色或者组织信息与用户的关系
     *
     * @param ids 角色或者组织id
     * @return
     */
    List<UserGroupMapResult> getFlowableUserGroupMapByGroupIds(List<String> ids);


    /**
     * 根据userId 获取有效的角色信息
     *
     * @param request
     * @return
     */
    List<RoleEntity> getRoleInfoByUserId(RoleEntityQueryRequest request);


    /**
     * 根据userId 获取有效的部门信息
     *
     * @param request
     * @return
     */
    List<OrganizationEntity> getOrgInfoByUserId(OrgEntityQueryRequest request);

}

