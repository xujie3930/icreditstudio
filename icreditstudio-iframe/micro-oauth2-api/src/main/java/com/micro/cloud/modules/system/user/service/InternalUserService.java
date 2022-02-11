package com.micro.cloud.modules.system.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.cloud.api.CommonPage;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.modules.system.user.vo.SysUserPageReqVO;
import com.micro.cloud.modules.system.user.vo.SysUserUpdateReqVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserCreateReqVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserInfoVO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户操作业务接口
 *
 * @author EDZ
 * @since 2021-11-05
 */
public interface InternalUserService extends IService<SysUser> {

  /**
   * 个人用户信息分页查询
   *
   * @param vo 分页请求参数
   * @param userId 当前用户id
   * @return 分页记录
   */
  CommonPage<InternalUserInfoVO> page(SysUserPageReqVO vo, String userId);

  /**
   * 根据用户id获取用户详情
   *
   * @param id 用户id
   * @return 用户详情
   */
  InternalUserInfoVO info(String id, String orgID);

  /**
   * 创建用户
   *
   * @param vo 新用户信息
   * @return 添加记录行数
   */
  @Transactional(rollbackFor = Exception.class)
  String create(InternalUserCreateReqVO vo, String creatorId, String orgId);

  /**
   * 更新用户信息
   *
   * @param reqVO 用户信息
   * @param updaterId 更新者id
   * @return 更新记录行数
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean updateUser(SysUserUpdateReqVO reqVO, String updaterId);

  /**
   * 批量更新用户状态
   *
   * @param ids 用户id集合
   * @param status 状态 参照SysCommonStatusEnum枚举类
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean updateUserStatusBatch(List<String> ids, Boolean status);
}
