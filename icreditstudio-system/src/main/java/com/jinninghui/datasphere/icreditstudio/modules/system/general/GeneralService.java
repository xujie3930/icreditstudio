package com.jinninghui.datasphere.icreditstudio.modules.system.general;

import com.jinninghui.datasphere.icreditstudio.modules.system.dict.entity.CodeInfoEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.general.param.GeneralOrgParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.general.result.UserLoginInfo;
import com.jinninghui.datasphere.icreditstudio.modules.system.log.entity.AuditLogEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.log.service.param.AuditLogEntitySaveParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.entity.ResourcesEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.entity.RoleEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserEntity;

import java.util.List;

/**
 * Created by PPai on 2021/6/10 11:47
 */
public interface GeneralService {
    /*
    组织机构接口
     */

    /**
     * 根据主键id获取部门信息
     *
     * @param id
     * @return
     */
    OrganizationEntity getOrganizationById(String id);

    /**
     * 根据部门编码获取部门信息
     *
     * @param code
     * @return
     */
    List<OrganizationEntity> getOrganizationByCode(String code);

    /**
     * 根据部门名称获取部门信息
     *
     * @param name
     * @return
     */
    List<OrganizationEntity> getOrganizationLikeName(String name);

    /**
     * 根据当前部门编码或主键id获取下一级部门列表
     *
     * @return
     */
    List<OrganizationEntity> getDirectSubOrg(GeneralOrgParam param);

    /**
     * 根据当前部门编码或主键id获取所有子部门列表
     *
     * @return
     */
    List<OrganizationEntity> getCascadeSubOrg(GeneralOrgParam param);

    /**
     * 根据当前部门编码或主键id获取下一级被禁用部门列表
     *
     * @return
     */
    List<OrganizationEntity> getDirectSubDisableOrg(GeneralOrgParam param);
    /*
    用户接口
     */


    UserLoginInfo getCurrUserLoginInfo();
    /**
     * 根据用户主键id获取用户信息
     *
     * @return
     */
    UserEntity getUserById(String id);

    /**
     * 根据用户主键id获取用户所在部门信息
     *
     * @return
     */
    List<OrganizationEntity> getOrgInfoByUserId(String userId);

    /**
     * 根据部门编码或主键id获取本部门人员列表
     *
     * @return
     */
    List<UserEntity> getUsersByOrgParam(GeneralOrgParam param);

    /**
     * 根据部门编码或主键id获取所有下级部门人员列表
     *
     * @return
     */
    List<UserEntity> getCascadeSubOrgUsersByOrgParam(GeneralOrgParam param);

    /**
     * 根据用户主键id获取该用户的角色列表
     *
     * @param userId
     * @return
     */
    List<RoleEntity> getRolesByUserId(String userId);

    /**
     * 根据用户主键id获取该用户所有能访问的菜单列表，以及该菜单中可访问的按钮
     *
     * @param userId
     * @return
     */
    List<ResourcesEntity> getResourcesByUserId(String userId);

    /**
     * 获取部门编码或主键id获取本部门被禁用人员
     *
     * @return
     */
    List<UserEntity> getDisabledUsersByOrgParam(GeneralOrgParam param);

    /**
     * 获取部门编码或主键id获取下级部门所有被禁用人员
     *
     * @return
     */
    List<UserEntity> getDirectSubOrgDisabledUsersByOrgParam(GeneralOrgParam param);


    /*
    角色接口
     */

    /**
     * 根据角色主键id获取角色信息
     *
     * @param id
     * @return
     */
    RoleEntity getRoleById(String id);

    /**
     * 根据角色id获取用户列表
     *
     * @param roleId
     * @return
     */
    List<UserEntity> getUsersByRoleId(String roleId);

    /**
     * 根据角色id获取菜单列表，以及该菜单所有的按钮
     *
     * @param roleId
     * @return
     */
    List<ResourcesEntity> getCascadeSubResourcesByRoleId(String roleId);

    /**
     * 根据父角色id获取所有被禁用角色
     *
     * @param parentRoleId
     * @return
     */
    List<RoleEntity> getDisabledRolesByParentRoleId(String parentRoleId);

    /*
    菜单接口
     */

    /**
     * 根据菜单主键id获取菜单信息
     *
     * @param id
     * @return
     */
    ResourcesEntity getResourcesById(String id);

    /**
     * 根据菜单主键id获取关联的角色列表
     *
     * @param resourcesId
     * @return
     */
    List<RoleEntity> getRolesByResourcesId(String resourcesId);

    /**
     * 根据父菜单主键id获取所有被禁用菜单
     *
     * @param parentId
     * @return
     */
    List<ResourcesEntity> getDisabledResourcesByParentResourcesId(String parentId);

    /**
     * 根据字典名称取得值
     * @param name
     * @return
     */
    List<CodeInfoEntity> getCodeInfoByCodeName(String name);

    /**
     * 保持审计日志
     * @param param
     * @return
     */
    AuditLogEntity saveAuditLog(AuditLogEntitySaveParam param);
}
