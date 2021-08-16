package com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.mapper;


import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result.SelectInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result.UserGroupMapResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.web.request.OrgEntityQueryRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.web.request.RoleEntityQueryRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hzh
 */
@Mapper
public interface AllInterfacesDao {

    /**
     * 根据角色id或者组织id 查询信息
     *
     * @param ids
     * @return
     */
    List<SelectInfoResult> getFlowableApproveGroupInfoByGroupIds(@Param("ids") List<String> ids);

    /**
     * 根据角色id或者组织id 查询用户信息
     *
     * @param ids
     * @return
     */
    List<SelectInfoResult> getFlowableApproveUserInfoByGroupIds(@Param("ids") List<String> ids);

    /**
     * 查询角色或者组织信息
     *
     * @param ids 用户ids
     * @return
     */
    List<SelectInfoResult> getFlowableApproveGroupInfoByUserIds(@Param("ids") List<String> ids);


    List<UserGroupMapResult> getFlowableUserGroupMapByUserIds(@Param("ids") List<String> ids);

    List<UserGroupMapResult> getFlowableUserGroupMapByGroupIds(@Param("ids") List<String> ids);


    List<RoleEntity> getRoleInfoByUserId(RoleEntityQueryRequest request);


    List<OrganizationEntity> getOrgInfoByUserId(OrgEntityQueryRequest orgEffectiveQueryParams);


}
