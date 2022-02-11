package com.micro.cloud.modules.system.org.repository.impl;

import com.micro.cloud.enums.SysCommonStatusEnum;
import com.micro.cloud.modules.system.org.dataobject.SysOrg;
import com.micro.cloud.modules.system.org.mapper.SysOrgMapper;
import com.micro.cloud.modules.system.org.repository.SysOrgRepository;
import com.micro.cloud.modules.system.org.vo.SysOrgListReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgSimpleRespVO;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 〈〉
 *
 * @author roy
 * @create 2021/11/17
 * @since 1.0.0
 */
@Repository
public class SysOrgRepositoryImpl implements SysOrgRepository {

  @Autowired private SysOrgMapper orgMapper;
  /**
   * 提升部门等级(不为叶子结点)
   *
   * @param id 部门id
   */
  @Override
  public void upgradeDepartLevel(String id) {
    if (StringUtils.isNotBlank(id)) {
      SysOrg sysOrg = new SysOrg();
      sysOrg.setSysOrgId(id);
      sysOrg.setIsLeaf(SysCommonStatusEnum.DISABLE.getStatus());
      orgMapper.updateById(sysOrg);
    }
  }

  /**
   * 获取部门精简信息
   *
   * @param reqVO
   * @return
   */
  @Override
  public List<SysOrgSimpleRespVO> getSimpleOrg(SysOrgListReqVO reqVO) {
    // 默认选取状态为开启的组织机构
    return orgMapper.getSimpleOrg(reqVO);
  }
}
