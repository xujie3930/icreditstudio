package com.jinninghui.datasphere.icreditstudio.modules.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jinninghui.datasphere.icreditstudio.modules.system.allinterface.result.SelectInfoResult;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.entity.RoleEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.web.request.LikeQueryUserInfoRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.web.request.OrgUserRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.web.request.UserEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.web.request.UserInfoRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.web.result.LikeQueryUserListResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *
 * @author hzh
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    IPage<UserEntity> queryPage(IPage<UserEntity> page, @Param("userQuery") UserEntityPageRequest pageRequest);

    List<SelectInfoResult> getAllUserInfo();


    List<OrganizationEntity> getOrgInfoByUsrId(UserInfoRequest params);


    List<UserEntity> getUserInfoByOrgId(OrgUserRequest params);

    List<UserEntity> getOrgChildUserInfoByOrgId(OrgUserRequest params);

    List<RoleEntity> getRoleInfoByUserId(UserInfoRequest params);

    /**
     * 模糊查询用户 姓名账号或者手机号
     * @param params name
     * @return
     */
    List<LikeQueryUserListResult> queryUserInfoByName(LikeQueryUserInfoRequest params);
}
