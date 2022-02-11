package com.micro.cloud.modules.system.user.mapper;

import com.micro.cloud.modules.system.role.vo.RemainingUserPageRepVO;
import com.micro.cloud.modules.system.role.vo.SysRoleUserPageReqVO;
import com.micro.cloud.domian.dto.UserRoles;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.modules.system.user.vo.CommonUserInfoRepVO;
import com.micro.cloud.mybatis.core.mapper.BaseMapperX;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper 接口
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Mapper
public interface SysUserMapper extends BaseMapperX<SysUser> {

  void updateUserStatusBatch(
      @Param(value = "ids") List<String> ids, @Param(value = "status") Boolean status);

  /**
   * 获取用户通用信息(登录时返回)
   *
   * @param userId 用户id
   * @return
   */
  CommonUserInfoRepVO getCommonInfo(@Param(value = "userId") String userId);

  /**
   * 获取用户角色信息
   *
   * @param userId
   * @return
   */
  List<UserRoles> getUserRoles(@Param(value = "userId") String userId);

  /**
   * 获取角色下未包含的人员列表
   *
   * @param reqVO
   * @param containedUserIds
   * @return
   */
  List<RemainingUserPageRepVO> remainingUser(
      @Param(value = "vo") SysRoleUserPageReqVO reqVO,
      @Param(value = "userIds") List<String> containedUserIds);
}
