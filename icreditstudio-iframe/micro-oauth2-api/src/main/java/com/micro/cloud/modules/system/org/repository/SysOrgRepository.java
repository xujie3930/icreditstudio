package com.micro.cloud.modules.system.org.repository;

import com.micro.cloud.modules.system.org.dataobject.SysOrg;
import com.micro.cloud.modules.system.org.vo.SysOrgListReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgSimpleRespVO;
import java.util.List;

/**
 * 〈组织机构数据库操作防腐层接口〉
 *
 * @author roy
 * @create 2021/11/17
 * @since 1.0.0
 */
public interface SysOrgRepository {

  /**
   * 更新部门信息
   *
   * @param id 部门id
   */
  void upgradeDepartLevel(String id);

  /**
   * 获取部门精简信息
   * @param reqVO
   * @return
   */
  List<SysOrgSimpleRespVO> getSimpleOrg(SysOrgListReqVO reqVO);
}
