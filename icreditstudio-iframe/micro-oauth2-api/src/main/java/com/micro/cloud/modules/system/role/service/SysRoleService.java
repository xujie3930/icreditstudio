package com.micro.cloud.modules.system.role.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.cloud.api.CommonPage;
import com.micro.cloud.modules.system.role.dataobject.SysRole;
import com.micro.cloud.modules.system.role.param.RoleUserParam;
import com.micro.cloud.modules.system.role.vo.RemainingUserPageRepVO;
import com.micro.cloud.modules.system.role.vo.RoleBindResourcesReqVO;
import com.micro.cloud.modules.system.role.vo.RoleBindUsersReqVO;
import com.micro.cloud.modules.system.role.vo.RoleSimpleListReqVO;
import com.micro.cloud.modules.system.role.vo.SysRoleCreateReqVO;
import com.micro.cloud.modules.system.role.vo.SysRolePageReqVO;
import com.micro.cloud.modules.system.role.vo.SysRoleRespVO;
import com.micro.cloud.modules.system.role.vo.SysRoleSimpleRespVO;
import com.micro.cloud.modules.system.role.vo.SysRoleUpdateReqVO;
import com.micro.cloud.modules.system.role.vo.SysRoleUpdateStatusReqVO;
import com.micro.cloud.modules.system.role.vo.SysRoleUserPageReqVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserInfoVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserSimpleVO;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务类
 *
 * @author EDZ
 * @since 2021-11-05
 */
public interface SysRoleService extends IService<SysRole> {
  /** 初始化角色的本地缓存 */
  void initLocalCache();

  /**
   * 创建角色
   *
   * @param reqVO 创建角色信息
   * @param creatorId 创建人员id
   * @return 角色编号
   */
  String create(SysRoleCreateReqVO reqVO, String creatorId);

  /**
   * 更新角色信息
   *
   * @param reqVO 角色信息更新请求
   * @param updater 更新人员id
   */
  Boolean updateRole(SysRoleUpdateReqVO reqVO, String updater);

  /**
   * 删除角色
   *
   * @param id 角色编号
   */
  Boolean delete(String id, String deleter);

  /**
   * 更新角色状态
   *
   * @param reqVO 更新状态请求参数
   * @param updater 更新人员id
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean updateStatus(SysRoleUpdateStatusReqVO reqVO, String updater);

  /**
   * 设置角色的数据权限
   *
   * @param id 角色编号
   * @param dataScope 数据范围
   * @param dataScopeDeptIds 部门编号数组
   */
  void updateDataScope(String id, Integer dataScope, Set<Long> dataScopeDeptIds);

  /**
   * 获得角色，从缓存中
   *
   * @param id 角色编号
   * @return 角色
   */
  SysRole getRoleFromCache(String id);

  /**
   * 获得角色列表
   *
   * @return 角色列表
   */
  List<SysRoleSimpleRespVO> getSimpleRoles(RoleSimpleListReqVO reqVO);

  /**
   * 获得角色数组，从缓存中
   *
   * @param ids 角色编号数组
   * @return 角色数组
   */
  List<SysRole> getRolesFromCache(Collection<String> ids);

  /**
   * 判断角色数组中，是否有管理员
   *
   * @param roleList 角色数组
   * @return 是否有管理员
   */
  boolean hasAnyAdmin(Collection<SysRole> roleList);

  /**
   * 判断角色编号数组中，是否有管理员
   *
   * @param ids 角色编号数组
   * @return 是否有管理员
   */
  default boolean hasAnyAdmin(Set<String> ids) {
    return hasAnyAdmin(getRolesFromCache(ids));
  }

  /**
   * 获得角色
   *
   * @param id 角色编号
   * @return 角色
   */
  SysRoleRespVO get(String id);

  /**
   * 获得角色分页
   *
   * @param reqVO 角色分页查询
   * @return 角色分页结果
   */
  CommonPage<SysRoleRespVO> page(SysRolePageReqVO reqVO);

  /**
   * 角色设置用户
   *
   * @param reqVO 角色设置用户请求
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean allocateUser(RoleBindUsersReqVO reqVO);

  /**
   * 获取指定角色下用户列表(分页)
   *
   * @param reqVO 角色相关信息
   * @return 用户列表
   */
  List<InternalUserInfoVO> getUserByRoleId(SysRoleUserPageReqVO reqVO);

  /**
   * 获取角色下用户简要信息
   *
   * @param param 请求参数
   * @return 用户简要信息列表
   */
  List<InternalUserSimpleVO> getSimpleUserInfo(RoleUserParam param);

  /**
   * 角色授权权限(资源)
   *
   * @param reqVO 授权请求参数
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean bindResource(RoleBindResourcesReqVO reqVO);

  /**
   * 角色移除人员
   *
   * @param reqVO 角色移除人员请求参数
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean removeUser(RoleBindUsersReqVO reqVO);

  /**
   * 角色分配人员时分页查询
   *
   * @param reqVO
   * @return
   */
  List<RemainingUserPageRepVO> remainingUser(SysRoleUserPageReqVO reqVO);

}
