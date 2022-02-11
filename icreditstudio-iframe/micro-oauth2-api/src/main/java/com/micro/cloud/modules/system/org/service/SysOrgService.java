package com.micro.cloud.modules.system.org.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.cloud.modules.system.org.dataobject.SysOrg;
import com.micro.cloud.modules.system.org.param.OrgUserParam;
import com.micro.cloud.modules.system.org.vo.OrgUpdateStatusReqVO;
import com.micro.cloud.modules.system.org.vo.SysDepartTreeModel;
import com.micro.cloud.modules.system.org.vo.SysOrgCreateReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgInfoRespVO;
import com.micro.cloud.modules.system.org.vo.SysOrgListReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgNodeVO;
import com.micro.cloud.modules.system.org.vo.SysOrgPageReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgRespVO;
import com.micro.cloud.modules.system.org.vo.SysOrgSimpleRespVO;
import com.micro.cloud.modules.system.org.vo.SysOrgTreeReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgUpdateReqVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserSimpleVO;
import com.micro.cloud.util.collection.CollectionUtils;
import java.beans.Transient;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务类
 *
 * @author EDZ
 * @since 2021-11-05
 */
public interface SysOrgService extends IService<SysOrg> {

  /** 初始化部门信息缓存 */
  void initCache();

  /**
   * 部门树形结构查询
   *
   * @param id 部门id
   * @return 树形结构
   */
  List<SysOrgNodeVO> treeList(String id);

  /**
   * 部门树形结构查询(异步)
   *
   * @param reqVO 树形查询
   * @return
   */
  List<SysOrgNodeVO> queryTreeAsync(SysOrgTreeReqVO reqVO);

  /**
   * 根据关键字搜索相关的部门数据
   *
   * @param reqVO 树形查询参数
   * @return
   */
  List<SysDepartTreeModel> searchBy(SysOrgTreeReqVO reqVO);

  /**
   * 创建部门
   *
   * @param reqVO 部门信息
   * @return 部门编号
   */
  String createDept(SysOrgCreateReqVO reqVO);

  /**
   * 更新部门
   *
   * @param reqVO 部门信息
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean updateDept(SysOrgUpdateReqVO reqVO);

  /**
   * 删除部门
   *
   * @param id 部门编号
   */
  @Transactional(rollbackFor = Exception.class)
  void deleteDept(String id);

  /**
   * 批量删除部门
   * @param ids
   */
  @Transactional(rollbackFor = Exception.class)
  void deleteOrgBatch(List<String> ids);


  /**
   * 筛选部门列表
   *
   * @param reqVO 筛选条件请求 VO
   * @return 部门列表
   */
  IPage<SysOrgRespVO> page(SysOrgPageReqVO reqVO);


  /**
   * 查询所有部门
   * @param reqVO
   * @return
   */
  IPage<SysOrgRespVO> selAllOrg(SysOrgPageReqVO reqVO);


  /**
   * 获得部门信息
   *
   * @param id 部门编号
   * @return 部门信息
   */
  SysOrgInfoRespVO getDept(String id);

  /**
   * 获得所有子部门，从缓存中
   *
   * @param parentId 部门编号
   * @param recursive 是否递归获取所有
   * @return 子部门列表
   */
  List<SysOrg> getOrgFromCache(String parentId, boolean recursive);

  /**
   * 批量禁用部门(包含子部门及其用户)
   *
   * @param reqVO 部门信息
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean changeStatusBatch(OrgUpdateStatusReqVO reqVO);

  /**
   * 获取组织机构精简信息(前端新增部门时联想值)
   *
   * @param reqVO 获取组织机构简要信息列表
   * @return 组织机构简要信息列表
   */
  List<SysOrgSimpleRespVO> getSimpleOrg(SysOrgListReqVO reqVO);

  /**
   * 获取对应组织机构用户
   *
   * @param param 组织机构用户请求参数
   * @return 组织机构用户列表
   */
  List<InternalUserSimpleVO> getUserByDeptId(OrgUserParam param);
}
