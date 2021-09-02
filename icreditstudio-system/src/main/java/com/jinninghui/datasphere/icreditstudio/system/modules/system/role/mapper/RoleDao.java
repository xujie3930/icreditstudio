package com.jinninghui.datasphere.icreditstudio.system.modules.system.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.param.UserRoleDataQueryParam;

import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result.SelectInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.entity.RoleEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.web.request.RoleEntityQueryParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.web.request.RoleUserQueryParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author hzh
 */
@Mapper
public interface RoleDao extends BaseMapper<RoleEntity> {

    List<RoleEntity> getRoleInfoByUserId(RoleEntityQueryParam request);


    List<SelectInfoResult> getAllRoleInfo();

    List<SelectInfoResult> getChildrenByParentId(RoleEntityQueryParam request);

    int isAdmin(UserRoleDataQueryParam param);


    List<UserEntity> getUserInfoByRoleId(RoleUserQueryParam request);

    List<Map<String, String>> getRoleNameByRoleIds(@Param("ids") Set<String> roleIds);
}
