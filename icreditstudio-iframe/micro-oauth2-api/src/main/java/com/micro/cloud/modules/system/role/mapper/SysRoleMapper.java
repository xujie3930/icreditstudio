package com.micro.cloud.modules.system.role.mapper;

import com.micro.cloud.modules.system.role.dataobject.SysRole;
import com.micro.cloud.modules.system.role.param.RoleUserParam;
import com.micro.cloud.modules.system.role.vo.SysRoleUserPageReqVO;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserSimpleVO;
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
public interface SysRoleMapper extends BaseMapperX<SysRole> {

  /**
   * 根据角色id等条件获取用户列表
   *
   * @param reqVO
   * @return 该角色下用户列表
   */
  List<SysUser> selectByRoleId(SysRoleUserPageReqVO reqVO);

  /**
   * 根据角色id获取所有人员
   *
   * @param id
   * @return
   */
  List<String> selectAllUserByRoleId(String id);

  /**
   * 获取角色下用户简要信息
   *
   * @param param
   * @return
   */
  List<InternalUserSimpleVO> selectUserSimpleInfoByRoleId(RoleUserParam param);
}
