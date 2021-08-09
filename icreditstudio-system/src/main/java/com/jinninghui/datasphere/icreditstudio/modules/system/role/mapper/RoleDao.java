package com.jinninghui.datasphere.icreditstudio.modules.system.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.datasphere.icreditstudio.modules.system.allinterface.param.UserRoleDataQueryParam;

import com.jinninghui.datasphere.icreditstudio.modules.system.allinterface.result.SelectInfoResult;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.entity.RoleEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.web.request.RoleEntityQueryParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.web.request.RoleUserQueryParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 *
 * @author hzh
 */
@Mapper
public interface RoleDao extends BaseMapper<RoleEntity> {

    List<RoleEntity> getRoleInfoByUserId(RoleEntityQueryParam request);


    List<SelectInfoResult> getAllRoleInfo();

    List<SelectInfoResult> getChildrenByParentId(RoleEntityQueryParam request);

    int isAdmin(UserRoleDataQueryParam param);


    List<UserEntity> getUserInfoByRoleId(RoleUserQueryParam request);

}
