package com.micro.cloud.modules.system.role.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.micro.cloud.api.CommonPage;
import com.micro.cloud.modules.system.role.dataobject.SysRole;
import com.micro.cloud.modules.system.role.param.RoleUserParam;
import com.micro.cloud.modules.system.role.vo.*;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserSimpleVO;

import java.util.List;

/**
 * 〈系统角色DB业务〉
 *
 * @author roy
 * @create 2021/11/11
 * @since 1.0.0
 */
public interface ISysRoleRepository {

  /**
   * 角色分页查询
   *
   * @param reqVO 分页请求参数
   * @return 分页记录
   */
  CommonPage<SysRole> page(SysRolePageReqVO reqVO);

  /**
   * 根据角色id获取对应用户列表(分页)
   *
   * @param reqVO 请求参数
   * @return 分页
   */
  List<SysUser> selectByRoleId(SysRoleUserPageReqVO reqVO);

  /**
   * 获取用户简要信息(列表)
   *
   * @param param 请求参数
   * @return 列表
   */
  List<InternalUserSimpleVO> selectUserSimpleInfoByRoleId(RoleUserParam param);

  /**
   * 获取系统角色精简信息
   *
   * @return
   */
  List<SysRole> selectRole(RoleSimpleListReqVO reqVO);

  /**
   * 角色分配人员时分页查询
   *
   * @param containedUserIds
   * @return
   */
  List<RemainingUserPageRepVO> remainUserPage(
      SysRoleUserPageReqVO reqVO, List<String> containedUserIds);

  /**
   * 获取角色下所有人员id
   *
   * @param id 角色id
   * @return 人员id集合
   */
  List<String> selectAllUser(String id);
}
