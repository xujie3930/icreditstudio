package com.micro.cloud.modules.system.role.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.annotations.VisibleForTesting;
import com.micro.cloud.api.CommonPage;
import com.micro.cloud.constant.SysErrorCodeConstants;
import com.micro.cloud.enums.SysCommonStatusEnum;
import com.micro.cloud.exception.ApiException;
import com.micro.cloud.modules.system.org.mapper.SysOrgMapper;
import com.micro.cloud.modules.system.resource.service.SysResourceService;
import com.micro.cloud.modules.system.role.convert.SysRoleConvert;
import com.micro.cloud.modules.system.role.dataobject.SysRole;
import com.micro.cloud.modules.system.role.dataobject.SysRoleResourceRef;
import com.micro.cloud.modules.system.role.mapper.SysRoleMapper;
import com.micro.cloud.modules.system.role.mapper.SysRoleResourceRefMapper;
import com.micro.cloud.modules.system.role.param.RoleUserParam;
import com.micro.cloud.modules.system.role.repository.ISysRoleRepository;
import com.micro.cloud.modules.system.role.service.SysRoleResourceRefService;
import com.micro.cloud.modules.system.role.service.SysRoleService;
import com.micro.cloud.modules.system.role.vo.*;
import com.micro.cloud.modules.system.user.convert.SysUserConvert;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.modules.system.user.dataobject.SysUserRoleRef;
import com.micro.cloud.modules.system.user.mapper.SysUserRoleRefMapper;
import com.micro.cloud.modules.system.user.service.SysUserRoleRefService;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserInfoVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserSimpleVO;
import com.micro.cloud.modules.system.user.vo.org.OrgUserPageRepVO;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import com.micro.cloud.snowflake.sequence.SequenceService;
import com.micro.cloud.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService {

  private final Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

  @Autowired private SequenceService sequenceService;

  @Autowired private SysResourceService resourceService;

  @Autowired private SysUserRoleRefService userRoleRefService;

  @Autowired private SysRoleResourceRefService roleResourceRefService;

  @Autowired private SysUserRoleRefMapper userRoleRefMapper;

  @Autowired private SysRoleResourceRefMapper roleResourceRefMapper;

  @Autowired private ISysRoleRepository roleRepository;

  @Autowired private SysRoleMapper roleMapper;

  @Autowired private SysOrgMapper orgMapper;

  /** 初始化角色的本地缓存 */
  @Override
  public void initLocalCache() {}

  /**
   * 创建角色
   *
   * @param reqVO 创建角色信息
   * @return 角色编号
   */
  @Override
  public String create(SysRoleCreateReqVO reqVO, String creatorId) {
    // 校验角色名称是否唯一
    // checkDuplicateRole(reqVO.getCode(), null);
    // 插入到数据库
    SysRole role = SysRoleConvert.INSTANCE.convert(reqVO);
    role.setSysRoleId(sequenceService.nextStringValue(null));
    // 类型设为自定义
    //    role.setType(SysRoleTypeEnum.CUSTOM.getType());
    role.setCreatorId(creatorId);
    roleMapper.insert(role);
    // 返回
    return role.getSysRoleId();
  }

  /**
   * 更新角色
   *
   * @param reqVO 更新角色信息
   */
  @Override
  public Boolean updateRole(SysRoleUpdateReqVO reqVO, String updaterId) {
    // 校验是否可以更新
    this.checkUpdateRole(reqVO.getId());
    // 校验角色的唯一字段是否重复
    // checkDuplicateRole(reqVO.getCode(), reqVO.getId());
    // 获取角色信息
    SysRole originalRole = roleMapper.selectById(reqVO.getId());
    // 更新到数据库
    SysRole updateInfo = SysRoleConvert.INSTANCE.convert(reqVO);
    updateInfo.setUpdaterId(updaterId);
    updateInfo.setUpdateTime(DateUtil.date());
    roleMapper.updateById(updateInfo);
    // 判断角色状态是否发生变更，如果发生变更需要重新刷新redis权限列表
    if (ObjectUtil.notEqual(originalRole.getStatus(), reqVO.getStatus())) {
      resourceService.initData();
    }
    return true;
  }

  /**
   * 删除角色
   *
   * @param id 角色编号
   */
  @Override
  public Boolean delete(String id, String deleter) {
    // 校验是否可以更新
    this.checkUpdateRole(id);
    // todo 删除功能权限相关数据
    // permissionService.processRoleDeleted(id);
    removeById(id);
    return true;
  }

  /**
   * 更新角色状态
   *
   * @param reqVO 更新状态请求参数
   * @param updater 更新者id
   * @return 是是否成功
   */
  @Override
  public Boolean updateStatus(SysRoleUpdateStatusReqVO reqVO, String updater) {
    // 校验是否可以更新
    this.checkUpdateRole(reqVO.getId());
    // 更新状态
    SysRole updateObject = new SysRole();
    updateObject.setSysRoleId(reqVO.getId());
    updateObject.setStatus(reqVO.getStatus());
    roleMapper.updateById(updateObject);
    // 禁用调用角色下所有相关权限
    resourceService.initData();
    return true;
  }

  /**
   * 设置角色的功能权限
   *
   * @param id 角色编号
   * @param dataScope 数据范围
   * @param dataScopeDeptIds 部门编号数组
   */
  @Override
  public void updateDataScope(String id, Integer dataScope, Set<Long> dataScopeDeptIds) {}

  /**
   * 获得角色，从缓存中
   *
   * @param id 角色编号
   * @return 角色
   */
  @Override
  public SysRole getRoleFromCache(String id) {
    return null;
  }

  /**
   * 获得角色列表
   *
   * @return 角色列表
   */
  @Override
  public List<SysRoleSimpleRespVO> getSimpleRoles(RoleSimpleListReqVO reqVO) {
    List<SysRole> sysRoles = roleRepository.selectRole(reqVO);
    return SysRoleConvert.INSTANCE.convertSimpleList(sysRoles);
  }

  /**
   * 获得角色数组，从缓存中
   *
   * @param ids 角色编号数组
   * @return 角色数组
   */
  @Override
  public List<SysRole> getRolesFromCache(Collection<String> ids) {
    return null;
  }

  /**
   * 判断角色数组中，是否有管理员
   *
   * @param roleList 角色数组
   * @return 是否有管理员
   */
  @Override
  public boolean hasAnyAdmin(Collection<SysRole> roleList) {
    return false;
  }

  /**
   * 获得角色
   *
   * @param id 角色编号
   * @return 角色
   */
  @Override
  public SysRoleRespVO get(String id) {
    SysRole sysRole = roleMapper.selectById(id);
    return SysRoleConvert.INSTANCE.convert(sysRole);
  }

  /**
   * 获得角色分页
   *
   * @param reqVO 角色分页查询
   * @return 角色分页结果
   */
  @Override
  public CommonPage<SysRoleRespVO> page(SysRolePageReqVO reqVO) {
    CommonPage<SysRole> page = roleRepository.page(reqVO);
    return SysRoleConvert.INSTANCE.covertPage(page);
  }

  /**
   * 角色设置用户
   *
   * @param reqVO 角色设置用户请求
   * @return 是否成功
   */
  @Override
  public Boolean allocateUser(RoleBindUsersReqVO reqVO) {
    checkEnable(reqVO.getRoleId());
    // 关联关系是否已存在
    // 维护角色-用户关联关系
    List<SysUserRoleRef> userRoleRefs = new ArrayList<>();
    List<String> userIds = reqVO.getUserIds();
    userIds.stream()
        .filter(Objects::nonNull)
        .forEach(
            userId -> {
              SysUserRoleRef userRoleRef = new SysUserRoleRef();
              userRoleRef.setSysUserRoleRefId(sequenceService.nextStringValue(null));
              userRoleRef.setSysUserId(userId);
              userRoleRef.setSysRoleId(reqVO.getRoleId());
              userRoleRefs.add(userRoleRef);
            });
    // 批量保存
    userRoleRefService.saveBatch(userRoleRefs);
    return true;
  }

  /**
   * 获取指定角色下用户列表(分页)
   *
   * @param reqVO 角色相关信息
   * @return 用户列表
   */
  @Override
  public List<InternalUserInfoVO> getUserByRoleId(SysRoleUserPageReqVO reqVO) {
    List<SysUser> sysUsers = roleRepository.selectByRoleId(reqVO);
    // 转换为视图对象
    List<InternalUserInfoVO> result = SysUserConvert.INSTANCE.convertInternalVO(sysUsers);
    // 获取人员对应部门信息
    if (CollectionUtils.isNotEmpty(result)) {
      List<String> userIds =
          result.stream()
              .filter(Objects::nonNull)
              .map(InternalUserInfoVO::getUserId)
              .distinct()
              .filter(StringUtils::isNoneBlank)
              .collect(Collectors.toList());
      Map<String, OrgUserPageRepVO> orgMap = orgMapper.getOrgByUserIds(userIds);
      // 设置用户部门信息
      result.forEach(
          user -> {
            user.setTitle(
                Objects.nonNull(orgMap.get(user.getUserId()))
                    ? orgMap.get(user.getUserId()).getTitle()
                    : null);
            user.setOrgType(
                Objects.nonNull(orgMap.get(user.getUserId()))
                    ? orgMap.get(user.getUserId()).getOrgType()
                    : null);
          });
    }
    return result;
  }

  /**
   * 获取角色下用户简要信息
   *
   * @param param 请求参数
   * @return 用户简要信息列表
   */
  @Override
  public List<InternalUserSimpleVO> getSimpleUserInfo(RoleUserParam param) {
    // 转换为视图对象
    return roleRepository.selectUserSimpleInfoByRoleId(param);
  }

  /**
   * 角色授权权限(资源)
   *
   * @param reqVO 授权请求参数
   */
  @Override
  public Boolean bindResource(RoleBindResourcesReqVO reqVO) {
    Set<String> summaryResourceIds = new HashSet<>(reqVO.getHalfCheckedKeys());
    if (CollectionUtils.isNotEmpty(reqVO.getResourceIds())) {
      // 先获取parentId为空的元素，作为根节点
      Set<ResourceBindInfoVo> roots =
          reqVO.getResourceIds().stream()
              .filter(Objects::nonNull)
              .filter(resources -> StringUtils.isBlank(resources.getParentId()))
              .collect(Collectors.toSet());
      //      logger.info("####### roots:{}", roots);
      // 过滤parentId为空的元素后，再按照parentId分组
      Map<String, List<ResourceBindInfoVo>> groupMap =
          reqVO.getResourceIds().stream()
              .filter(Objects::nonNull)
              .filter(resources -> StringUtils.isNotBlank(resources.getParentId()))
              .collect(Collectors.groupingBy(ResourceBindInfoVo::getParentId));
      //      logger.info("########## groupMap:{}", groupMap);
      // 过滤出所有的叶子节点
      Map<String, List<ResourceBindInfoVo>> leafNodes =
          reqVO.getResourceIds().stream()
              .filter(Objects::nonNull)
              .filter(resources -> StringUtils.isNotBlank(resources.getParentId()))
              .filter(ResourceBindInfoVo::getLeaf)
              .collect(Collectors.groupingBy(ResourceBindInfoVo::getParentId));
      //      logger.info("########## leafNodes:{}", leafNodes);
      // 移除叶子节点后剩余节点视为选中的中间节点，需递归其子节点
      if (CollectionUtil.isNotEmpty(leafNodes)) {
        Set<String> leafNodeParentIds = leafNodes.keySet();
        leafNodeParentIds.stream()
            .filter(Objects::nonNull)
            .forEach(
                leafNodeParentId -> {
                  summaryResourceIds.add(leafNodeParentId);
                  groupMap.get(leafNodeParentId).removeAll(leafNodes.get(leafNodeParentId));
                });
      }
      groupMap.keySet().stream()
          .filter(key -> CollectionUtil.isNotEmpty(groupMap.get(key)))
          .forEach(summaryResourceIds::add);
      Set<String> middleNodeIds = new HashSet<>();
      groupMap.values().stream()
          .filter(CollectionUtil::isNotEmpty)
          .forEach(
              middleNode -> {
                middleNodeIds.addAll(
                    middleNode.stream()
                        .map(ResourceBindInfoVo::getKey)
                        .collect(Collectors.toSet()));
              });
      summaryResourceIds.addAll(middleNodeIds);
      // 获取所有中间节点下的所有子节点
      if (CollectionUtil.isNotEmpty(middleNodeIds)) {
        middleNodeIds.stream()
            .filter(StringUtils::isNotBlank)
            .forEach(
                key -> {
                  resourceService.downRecursionResourceTree(key, summaryResourceIds);
                });
      }
      //      logger.info("######### rest groupMap:{}", groupMap);
      if (CollectionUtil.isNotEmpty(leafNodes)) {
        leafNodes.values().stream()
            .forEach(
                resources -> {
                  Set<String> leafKey =
                      resources.stream()
                          .map(ResourceBindInfoVo::getKey)
                          .collect(Collectors.toSet());
                  summaryResourceIds.addAll(leafKey);
                });
      }
      // 过滤出只有根节点的元素
      Set<String> hasChildrenRoot = new HashSet<>(groupMap.keySet());
      Set<ResourceBindInfoVo> singleRoots =
          roots.stream()
              .filter(Objects::nonNull)
              .filter(root -> !hasChildrenRoot.contains(root.getKey()))
              .collect(Collectors.toSet());
      //      logger.info("########## singleRoots:{}", singleRoots);
      singleRoots.stream()
          .filter(Objects::nonNull)
          .forEach(
              singleRoot -> {
                summaryResourceIds.add(singleRoot.getKey());
                resourceService.downRecursionResourceTree(singleRoot.getKey(), summaryResourceIds);
              });
      logger.info(
          "########### summaryResourceIds:{}, size{}",
          summaryResourceIds,
          summaryResourceIds.size());
    }
    // 批量维护角色-资源关联关系
    return batchAuth(summaryResourceIds, reqVO.getRoleId());
  }

  /**
   * 批量维护角色与权限关联关系
   *
   * @param resourceIds 资源集合
   * @param roleId 角色id
   * @return true/false
   */
  private Boolean batchAuth(Set<String> resourceIds, String roleId) {
    // 获取角色原有权限列表进行比对
    List<String> alreadyHasResources = roleResourceRefMapper.getResourceByRoleId(roleId);
    logger.info(
        "########## alreadyHasResources:{}, size{}",
        alreadyHasResources,
        alreadyHasResources.size());
    // 待移除
    Set<String> forDel =
        alreadyHasResources.stream()
            .filter(item -> !resourceIds.contains(item))
            .collect(Collectors.toSet());
    logger.info("########## forDel:{}", forDel);
    // 待新增
    Set<String> forAdd =
        resourceIds.stream()
            .filter(item -> !alreadyHasResources.contains(item))
            .collect(Collectors.toSet());
    logger.info("########## forAdd:{}", forAdd);
    // 移除
    if (CollectionUtil.isNotEmpty(forDel)) {
      QueryWrapperX<SysRoleResourceRef> queryWrapperX = new QueryWrapperX<>();
      queryWrapperX.eq("sys_role_id", roleId).in("sys_resource_id", forDel);
      roleResourceRefService.remove(queryWrapperX);
    }
    List<SysRoleResourceRef> refList = new ArrayList<>();
    forAdd.stream()
        .filter(StringUtils::isNoneBlank)
        .forEach(
            item -> {
              SysRoleResourceRef ref = new SysRoleResourceRef();
              ref.setSysRoleResourceRefId(sequenceService.nextStringValue(null));
              ref.setSysRoleId(roleId);
              ref.setSysResourceId(item);
              refList.add(ref);
            });
    // 批量执行
    boolean result = roleResourceRefService.saveBatch(refList);
    // 初始化redis权限菜单
    resourceService.initData();
    return result;
  }

  /**
   * 角色移除人员
   *
   * @param reqVO 移除人员请求
   * @return 是否成功
   */
  @Override
  public Boolean removeUser(RoleBindUsersReqVO reqVO) {
    // 校验角色是否正常
    checkEnable(reqVO.getRoleId());
    // 根据请求参数获取对应映射关系id
    QueryWrapperX<SysUserRoleRef> queryWrapperX = new QueryWrapperX<>();
    queryWrapperX
        .select("sys_user_role_ref_id")
        .eq("sys_role_id", reqVO.getRoleId())
        .in("sys_user_id", reqVO.getUserIds());
    List<SysUserRoleRef> refs = userRoleRefMapper.selectList(queryWrapperX);
    if (CollectionUtil.isNotEmpty(refs)) {
      List<String> refIds =
          refs.stream()
              .filter(Objects::nonNull)
              .map(SysUserRoleRef::getSysUserRoleRefId)
              .filter(StringUtils::isNoneBlank)
              .collect(Collectors.toList());
      // 移除角色-用户关联关系
      userRoleRefMapper.deleteBatchIds(refIds);
    }
    return true;
  }

  /**
   * 角色分配人员时分页查询
   *
   * @param reqVO 请求参数
   * @return 用户列表
   */
  @Override
  public List<RemainingUserPageRepVO> remainingUser(SysRoleUserPageReqVO reqVO) {
    // 获取当前角色已有人员
    List<String> sysUserIds = roleRepository.selectAllUser(reqVO.getId());
    return roleRepository.remainUserPage(reqVO, sysUserIds);
  }

  /**
   * 向上递归,找出集合中的顶级父节点
   *
   * @param bindInfoVo 角色绑定资源
   * @param resourceBindInfoVoList 资源集合
   * @return 顶级父节点
   */
  private ResourceBindInfoVo upRecursionResourceTree(
      ResourceBindInfoVo bindInfoVo, List<ResourceBindInfoVo> resourceBindInfoVoList) {
    AtomicReference<ResourceBindInfoVo> res = new AtomicReference<>();
    resourceBindInfoVoList.stream()
        .forEach(
            inBindInfoVo -> {
              if (bindInfoVo.getParentId().equals(inBindInfoVo.getKey())) {
                res.set(upRecursionResourceTree(inBindInfoVo, resourceBindInfoVoList));
                if (Util.isEmpty(res.get())) {
                  res.set(inBindInfoVo);
                }
              }
            });
    return res.get();
  }

  /**
   * 校验角色是否处于启用状态
   *
   * @param roleId 角色id
   */
  private void checkEnable(String roleId) {
    SysRole sysRole = roleMapper.selectOne("sys_role_id", roleId);
    Optional.ofNullable(sysRole)
        .ifPresent(
            role -> {
              if (SysCommonStatusEnum.DISABLE.getStatus().booleanValue()
                  == sysRole.getStatus().booleanValue()) {
                throw new ApiException(SysErrorCodeConstants.ROLE_IS_DISABLE);
              }
            });
  }

  /**
   * 校验角色的唯一字段是否重复
   *
   * <p>是否存在相同编码的角色
   *
   * @param code 角色编码
   * @param id 角色id
   */
  public void checkDuplicateRole(String code, String id) {
    // 是否存在相同编码的角色
    if (StringUtils.isBlank(code)) {
      return;
    }
    // 该 code 编码被其它角色所使用
    SysRole role = roleMapper.selectOne("role_code", code);
    if (role != null && !role.getSysRoleId().equals(id)) {
      throw new ApiException(SysErrorCodeConstants.ROLE_CODE_DUPLICATE);
    }
  }

  /**
   * 校验角色是否可以被更新
   *
   * @param id 角色编号
   */
  @VisibleForTesting
  public void checkUpdateRole(String id) {
    SysRole role = roleMapper.selectById(id);
    if (role == null) {
      throw new ApiException(SysErrorCodeConstants.ROLE_NOT_EXISTS);
    }
    // 内置角色，不允许删除
    /* if (SysRoleTypeEnum.SYSTEM.getType().equals(role.getType())) {
      throw new ApiException(SysErrorCodeConstants.ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE);
    }*/
  }
}
