package com.micro.cloud.modules.system.resource.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.healthcheck.impl.Http;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.cloud.constant.SysOauthConstant;
import com.micro.cloud.enums.SysCommonStatusEnum;
import com.micro.cloud.modules.system.resource.constant.ResourceConstant;
import com.micro.cloud.modules.system.resource.convert.SysResourceConvert;
import com.micro.cloud.modules.system.resource.dataobject.SysResource;
import com.micro.cloud.modules.system.resource.mapper.SysResourceMapper;
import com.micro.cloud.modules.system.resource.service.SysResourceService;
import com.micro.cloud.modules.system.resource.vo.SysResourceCreateReqVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceListReqVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceNodeVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceRespVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceSimpleRespVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceTreeReqVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceUpdateReqVO;
import com.micro.cloud.modules.system.role.dto.RoleResourceRefMapDto;
import com.micro.cloud.modules.system.role.mapper.SysRoleResourceRefMapper;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import com.micro.cloud.snowflake.sequence.SequenceService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * 服务实现类
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource>
    implements SysResourceService {

  private final Logger logger = LoggerFactory.getLogger(SysResourceServiceImpl.class);

  @Autowired private SequenceService sequenceService;
  @Autowired private SysResourceMapper resourceMapper;
  @Autowired private SysRoleResourceRefMapper roleResourceRefMapper;
  @Autowired private RedisTemplate<String, Object> redisTemplate;
  @Autowired private RestTemplate restTemplate;

  @Override
  public void initData() {
    logger.info("########## 初始化菜单 ###########");
    // 删除原有权限列表
    redisTemplate.delete(SysOauthConstant.RESOURCE_ROLES_MAP);
    // 获取用户权限列表,格式 <url,{角色001,角色002,角色003,角色004.....}>
    Map<String, RoleResourceRefMapDto> resourceRolesMap =
        roleResourceRefMapper.getResourceRolesMap();
    List<RoleResourceRefMapDto> refList = new ArrayList<>(resourceRolesMap.values());
    Map<String, List<String>> refMap =
        refList.stream()
            .collect(
                Collectors.toMap(RoleResourceRefMapDto::getUrl, RoleResourceRefMapDto::getRoleIds));
    Map<String, String> treemap = new TreeMap<>();
    refMap.entrySet().stream()
        .filter(Objects::nonNull)
        .forEach(
            entry -> {
              String value = String.valueOf(entry.getValue());
              treemap.put(entry.getKey(), value);
            });
    redisTemplate.opsForHash().putAll(SysOauthConstant.RESOURCE_ROLES_MAP, treemap);
  }

  /** 初始化菜单的本地缓存 */
  @Override
  public void initLocalCache() {}

  /**
   * 创建菜单
   *
   * @param reqVO 菜单信息
   * @return 创建出来的菜单编号
   */
  @Override
  public String create(SysResourceCreateReqVO reqVO) {
    // 校验菜单（自己）
    checkResource(reqVO.getName(), null);
    // 插入数据库
    SysResource resource = SysResourceConvert.INSTANCE.convert(reqVO);
    resource.setSysResourceId(sequenceService.nextStringValue(null));
    resourceMapper.insert(resource);
    // 返回
    return resource.getSysResourceId();
  }

  /**
   * 更新菜单
   *
   * @param reqVO 菜单信息
   */
  @Override
  public Boolean updateResource(SysResourceUpdateReqVO reqVO) {
    // 校验菜单（自己）
    checkResource(reqVO.getName(), reqVO.getId());
    // 更新到数据库
    SysResource updateObject = SysResourceConvert.INSTANCE.convert(reqVO);
    resourceMapper.updateById(updateObject);
    return true;
  }

  /**
   * 删除菜单
   *
   * @param id 菜单编号
   */
  @Override
  public Boolean delete(String id) {
    // 标记删除
    resourceMapper.deleteById(id);
    // 删除授予给角色的权限
    // permissionService.processMenuDeleted(menuId);
    return true;
  }

  /**
   * 获得所有菜单列表
   *
   * @return 菜单列表
   */
  @Override
  public List<SysResourceRespVO> getList(SysResourceListReqVO reqVO) {
    List<SysResource> result = this.resourceMapper.selectList(reqVO);
    return SysResourceConvert.INSTANCE.convertList(result);
  }

  /**
   * 筛选菜单列表
   *
   * @param reqVO 筛选条件请求 VO
   * @return 菜单简洁列表
   */
  @Override
  public List<SysResourceSimpleRespVO> getSimpleList(SysResourceListReqVO reqVO) {

    List<SysResource> result = this.resourceMapper.selectList(reqVO);
    return SysResourceConvert.INSTANCE.convertSimple(result);
  }

  /**
   * 获得所有菜单，从缓存中
   *
   * <p>任一参数为空时，则返回为空
   *
   * @param menuTypes 菜单类型数组
   * @param menusStatuses 菜单状态数组
   * @return 菜单列表
   */
  @Override
  public List<SysResource> listMenusFromCache(
      Collection<Integer> menuTypes, Collection<Integer> menusStatuses) {
    return null;
  }

  /**
   * 获得指定编号的菜单数组，从缓存中
   *
   * <p>任一参数为空时，则返回为空
   *
   * @param menuIds 菜单编号数组
   * @param menuTypes 菜单类型数组
   * @param menusStatuses 菜单状态数组
   * @return 菜单数组
   */
  @Override
  public List<SysResource> listMenusFromCache(
      Collection<Long> menuIds, Collection<Integer> menuTypes, Collection<Integer> menusStatuses) {
    return null;
  }

  /**
   * 获得权限对应的菜单数组
   *
   * @param permission 权限标识
   * @return 数组
   */
  @Override
  public List<SysResource> getMenuListByPermissionFromCache(String permission) {
    return null;
  }

  /**
   * 获得菜单
   *
   * @param id 菜单编号
   * @return 菜单
   */
  @Override
  public SysResourceRespVO get(String id) {
    /* Map<String, RoleResourceRefMapDto> resourceRolesMap =
    roleResourceRefMapper.getResourceRolesMap();*/
    SysResource result = resourceMapper.selectById(id);
    return SysResourceConvert.INSTANCE.convert(result);
  }

  /**
   * 操作权限树形结构查询(同步)
   *
   * @param reqVO 树形查询
   * @return 操作权限树形结构
   */
  @Override
  public List<SysResourceNodeVO> queryTreeSync(SysResourceTreeReqVO reqVO) {
    List<SysResource> sysResources = resourceMapper.selectList();
    // 获取当前角色所有权限
    List<String> permissions = roleResourceRefMapper.getResourceByRoleId(reqVO.getRoleId());
    logger.info("######## permissions:{}", permissions);
    return sysResources.stream()
        .filter(resource -> StringUtils.isBlank(resource.getParentId()))
        .map(resource -> covertResourceNode(resource, sysResources, permissions))
        .collect(Collectors.toList());
  }

  /**
   * 操作权限树形结构查询(异步)
   *
   * @param reqVO 树形查询
   * @return 操作权限树形结构
   */
  @Override
  public List<SysResourceNodeVO> queryTreeAsync(SysResourceTreeReqVO reqVO) {
    // 根据id加载该菜单资源下所有下级资源，如果id为空，则默认查询所有一级菜单资源
    if (StringUtils.isBlank(reqVO.getParentId())) {
      QueryWrapperX<SysResource> orgQueryWrapperX = new QueryWrapperX<>();
      orgQueryWrapperX.isNull("parent_id");
      List<SysResource> resources = resourceMapper.selectList(orgQueryWrapperX);
      List<SysResourceNodeVO> tree = SysResourceConvert.INSTANCE.convertTree(resources);
      markTree(reqVO, tree);
      return tree;
    }
    QueryWrapperX<SysResource> queryWrapperX = new QueryWrapperX<>();
    queryWrapperX
        .eq(StringUtils.isNotBlank(reqVO.getParentId()), "parent_id", reqVO.getParentId())
        .likeIfPresent("name", reqVO.getName())
        .orderByAsc("order_by");
    List<SysResource> resourceList = resourceMapper.selectList(queryWrapperX);
    List<SysResourceNodeVO> tree = SysResourceConvert.INSTANCE.convertTree(resourceList);
    markTree(reqVO, tree);
    return tree;
  }

  /**
   * 递归下级菜单资源
   *
   * @param resource 菜单资源
   * @param resourceList 下级菜单资源列表
   * @return 树形结构菜单
   */
  private SysResourceNodeVO covertResourceNode(
      SysResource resource, List<SysResource> resourceList, List<String> permissions) {
    SysResourceNodeVO node = SysResourceConvert.INSTANCE.convertTree(resource);
    // 叶子节点进行标识
    if (permissions.contains(resource.getSysResourceId()) && node.getLeaf()) {
      node.setChecked(SysCommonStatusEnum.ENABLE.getStatus());
    }
    // 非叶子节点，判断该角色是否拥有此节点下所有权限
    if (permissions.contains(resource.getSysResourceId()) && !node.getLeaf()) {
      Set<String> childrenIds = new HashSet<>();
      downRecursionResourceTree(node.getKey(), childrenIds);
      if (permissions.containsAll(childrenIds)) {
        node.setChecked(SysCommonStatusEnum.ENABLE.getStatus());
      }
    }
    List<SysResourceNodeVO> children =
        resourceList.stream()
            .filter(subResource -> StringUtils.isNotBlank(subResource.getParentId()))
            .filter(subResource -> subResource.getParentId().equals(resource.getSysResourceId()))
            .map(subResource -> covertResourceNode(subResource, resourceList, permissions))
            .collect(Collectors.toList());
    node.setChildren(children);
    return node;
  }

  /**
   * 根据角色id获取该角色现有资源，对树形进行checked标记
   *
   * @param reqVO 权限树形结构请求参数
   * @param tree 树形结构
   */
  private void markTree(SysResourceTreeReqVO reqVO, List<SysResourceNodeVO> tree) {
    // 加入缓存
    List<String> permissions = roleResourceRefMapper.getResourceByRoleId(reqVO.getRoleId());
    logger.info("########## permissions:{}, size{}", permissions, permissions.size());
    Optional.of(permissions)
        .ifPresent(
            resources ->
                tree.stream()
                    .filter(Objects::nonNull)
                    .forEach(
                        node -> {
                          // 当前角色是否已包含此菜单权限(为叶子节点时设置选中标记)
                          if (permissions.contains(node.getKey()) && node.getLeaf()) {
                            node.setChecked(SysCommonStatusEnum.ENABLE.getStatus());
                          }
                          if (!node.getLeaf() && permissions.contains(node.getKey())) {

                            // 非叶子节点时，获取该节点下所有叶子节点与角色已有权限菜单进行匹配,如果是则为全选
                            Set<String> children = new HashSet<>();
                            downRecursionResourceTree(node.getKey(), children);
                            if (permissions.containsAll(children)) {
                              node.setChecked(SysCommonStatusEnum.ENABLE.getStatus());
                            }
                          }
                        }));
  }

  /**
   * 向下递归获取所有下级菜单权限
   *
   * @param key 菜单权限id
   * @param childrenResources 收集菜单资源权限
   */
  @Override
  public void downRecursionResourceTree(String key, Set<String> childrenResources) {
    // 获取下级子节点
    QueryWrapperX<SysResource> queryWrapperX = new QueryWrapperX<>();
    queryWrapperX.eq("parent_id", key);
    List<SysResource> sysResources = resourceMapper.selectList(queryWrapperX);
    if (CollectionUtil.isNotEmpty(sysResources)) {
      sysResources.stream()
          .filter(Objects::nonNull)
          .forEach(
              resource -> {
                childrenResources.add(resource.getSysResourceId());
                if (!resource.getLeaf()) {
                  downRecursionResourceTree(resource.getSysResourceId(), childrenResources);
                }
              });
    }
  }

  /**
   * 根据用户id获取相关资源权限
   *
   * @param userId 用户id
   * @return 用户资源权限
   */
  @Override
  public List<SysResourceRespVO> getByUserId(String userId) {
    return roleResourceRefMapper.getUserResourceById(userId);
  }

  /**
   * 同步指定客户端接口数据
   *
   * @return 客户端服务列表
   */
  @Override
  public Boolean generateClientsApiInfo(String uri) {
    // http://127.0.0.1:9527/v2/api-docs
    uri = ResourceConstant.HTTP_PREFIX + uri + ResourceConstant.API_DOCS;
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
    Optional.ofNullable(responseEntity)
        .ifPresent(
            response -> {
              JSONObject parseObj = JSONUtil.parseObj(response.getBody());
              Map<String, Map<String, String>> pathMap =
                  (Map<String, Map<String, String>>) parseObj.get("paths");
              pathMap.entrySet().stream()
                  .forEach(
                      entry -> {
                        logger.info(
                            "##### path:{} http method:{}",
                            entry.getKey(),
                            entry.getValue().keySet().stream().findAny().get());
                      });
            });
    return null;
  }

  /**
   * 校验系统资源是否合法
   *
   * @param name 操作菜单名字
   * @param id 操作菜单id
   */
  public void checkResource(String name, String id) {}
}
