package com.micro.cloud.modules.system.resource.service;

import com.micro.cloud.modules.system.org.vo.SysDepartTreeModel;
import com.micro.cloud.modules.system.org.vo.SysOrgTreeReqVO;
import com.micro.cloud.modules.system.resource.dataobject.SysResource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.cloud.modules.system.resource.vo.SysResourceCreateReqVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceListReqVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceNodeVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceRespVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceSimpleRespVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceTreeModel;
import com.micro.cloud.modules.system.resource.vo.SysResourceTreeReqVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceUpdateReqVO;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 服务类
 *
 * @author EDZ
 * @since 2021-11-05
 */
public interface SysResourceService extends IService<SysResource> {

  /** 初始化菜单的本地缓存 */
  void initLocalCache();

  void initData();

  /**
   * 创建菜单
   *
   * @param reqVO 菜单信息
   * @return 创建出来的菜单编号
   */
  String create(SysResourceCreateReqVO reqVO);

  /**
   * 更新菜单
   *
   * @param reqVO 菜单信息
   */
  Boolean updateResource(SysResourceUpdateReqVO reqVO);

  /**
   * 删除菜单
   *
   * @param id 菜单编号
   */
  Boolean delete(String id);

  /**
   * 获得所有菜单列表
   *
   * @return 菜单列表
   */
  List<SysResourceRespVO> getList(SysResourceListReqVO reqVO);

  /**
   * 筛选菜单列表
   *
   * @param reqVO 筛选条件请求 VO
   * @return 菜单列表
   */
  List<SysResourceSimpleRespVO> getSimpleList(SysResourceListReqVO reqVO);

  /**
   * 获得所有菜单，从缓存中
   *
   * <p>任一参数为空时，则返回为空
   *
   * @param menuTypes 菜单类型数组
   * @param menusStatuses 菜单状态数组
   * @return 菜单列表
   */
  List<SysResource> listMenusFromCache(
      Collection<Integer> menuTypes, Collection<Integer> menusStatuses);

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
  List<SysResource> listMenusFromCache(
      Collection<Long> menuIds, Collection<Integer> menuTypes, Collection<Integer> menusStatuses);

  /**
   * 获得权限对应的菜单数组
   *
   * @param permission 权限标识
   * @return 数组
   */
  List<SysResource> getMenuListByPermissionFromCache(String permission);

  /**
   * 获得菜单
   *
   * @param id 菜单编号
   * @return 菜单
   */
  SysResourceRespVO get(String id);

  /**
   * 操作权限树形结构查询(同步)
   *
   * @param reqVO 树形查询
   * @return
   */
  List<SysResourceNodeVO> queryTreeSync(SysResourceTreeReqVO reqVO);

  /**
   * 操作权限树形结构查询(异步)
   *
   * @param reqVO 树形查询
   * @return
   */
  List<SysResourceNodeVO> queryTreeAsync(SysResourceTreeReqVO reqVO);

  /**
   * 根据用户id获取相关资源权限
   *
   * @param userId 用户id
   * @return 用户资源权限
   */
  List<SysResourceRespVO> getByUserId(String userId);

  /**
   * 向下递归获取所有下级菜单权限
   *
   * @param key 菜单权限id
   * @param childrenResources 收集菜单资源权限
   */
  void downRecursionResourceTree(String key, Set<String> childrenResources);

  /**
   * 同步客户端服务接口数据
   *
   * @param uri 客户端服务地址
   * @return 客户端服务列表
   */
  Boolean generateClientsApiInfo(String uri);
}
