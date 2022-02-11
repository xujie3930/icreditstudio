package com.micro.cloud.modules.system.role.repository.impl;

import com.github.pagehelper.PageHelper;
import com.micro.cloud.api.CommonPage;
import com.micro.cloud.enums.SysCommonStatusEnum;
import com.micro.cloud.modules.system.role.dataobject.SysRole;
import com.micro.cloud.modules.system.role.mapper.SysRoleMapper;
import com.micro.cloud.modules.system.role.param.RoleUserParam;
import com.micro.cloud.modules.system.role.repository.ISysRoleRepository;
import com.micro.cloud.modules.system.role.vo.RemainingUserPageRepVO;
import com.micro.cloud.modules.system.role.vo.RoleSimpleListReqVO;
import com.micro.cloud.modules.system.role.vo.SysRolePageReqVO;
import com.micro.cloud.modules.system.role.vo.SysRoleUserPageReqVO;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.modules.system.user.mapper.SysUserMapper;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserSimpleVO;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import com.micro.cloud.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 〈〉
 *
 * @author roy
 * @create 2021/11/11
 * @since 1.0.0
 */
@Repository
public class SysRoleRepositoryImpl implements ISysRoleRepository {

  private final Logger logger = LoggerFactory.getLogger(SysRoleRepositoryImpl.class);

  @Autowired private SysRoleMapper roleMapper;

  @Autowired private SysUserMapper userMapper;

  /**
   * 角色分页查询
   *
   * @param reqVO 分页请求参数
   * @return 分页记录
   */
  @Override
  public CommonPage<SysRole> page(SysRolePageReqVO reqVO) {
//    PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
    QueryWrapperX<SysRole> queryWrapperX = new QueryWrapperX<>();
    queryWrapperX.likeIfPresent("role_name", reqVO.getName()).orderByDesc("create_time");
//    Page<SysRole> rolePage=new Page<>(reqVO.getPageNo(),reqVO.getPageSize());
//    Page<SysRole> page = roleMapper.selectPage(rolePage, queryWrapperX);
    return roleMapper.selectPage(reqVO, queryWrapperX);
  }

  /**
   * 根据角色id获取对应用户列表
   *
   * @param reqVO
   * @return
   */
  @Override
  public List<SysUser> selectByRoleId(SysRoleUserPageReqVO reqVO) {
    reqVO.setAccount(Util.escapeStr(reqVO.getAccount()));
    reqVO.setName(Util.escapeStr(reqVO.getName()));
    PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
    return roleMapper.selectByRoleId(reqVO);
  }

  /**
   * 获取用户简要信息(列表)
   *
   * @param param 请求参数
   * @return 列表
   */
  @Override
  public List<InternalUserSimpleVO> selectUserSimpleInfoByRoleId(RoleUserParam param) {
    param.setUserName(Util.escapeStr(param.getUserName()));
    return roleMapper.selectUserSimpleInfoByRoleId(param);
  }

  /**
   * 获取系统角色精简信息(启用中)
   *
   * @return 系统角色信息
   */
  @Override
  public List<SysRole> selectRole(RoleSimpleListReqVO reqVO) {
    QueryWrapperX<SysRole> queryWrapperX = new QueryWrapperX<>();
    queryWrapperX
        .eq("status", SysCommonStatusEnum.ENABLE.getStatus())
        .likeIfPresent("role_name", reqVO.getName())
        .orderByDesc("create_time");
    //todo  limit待添加
//        .last(" limit 10");
    return roleMapper.selectList(queryWrapperX);
  }

  /**
   * 角色分配人员时分页查询
   *
   * @param containedUserIds 已包含的人员id
   * @return
   */
  @Override
  public List<RemainingUserPageRepVO> remainUserPage(
      SysRoleUserPageReqVO reqVO, List<String> containedUserIds) {
    reqVO.setAccount(Util.escapeStr(reqVO.getAccount()));
    reqVO.setName(Util.escapeStr(reqVO.getName()));
    PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
    return userMapper.remainingUser(reqVO, containedUserIds);
  }

  /**
   * 获取角色下所有人员id
   *
   * @param id 角色id
   * @return 人员id集合
   */
  @Override
  public List<String> selectAllUser(String id) {
    return roleMapper.selectAllUserByRoleId(id);
  }
}
