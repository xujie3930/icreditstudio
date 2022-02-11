package com.micro.cloud.modules.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.micro.cloud.modules.system.org.dataobject.SysOrg;
import com.micro.cloud.modules.system.org.vo.SysUserOrgRefPageReqVO;
import com.micro.cloud.modules.system.org.vo.SysUserOrgRefRespVO;
import com.micro.cloud.modules.system.user.dataobject.SysUserOrgRef;
import com.micro.cloud.mybatis.core.mapper.BaseMapperX;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper 接口
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Mapper
public interface SysUserOrgRefMapper extends BaseMapperX<SysUserOrgRef> {

  /**
   * 判断部门下是否存在用户
   *
   * @param orgIds
   * @return
   */
  Integer existRefByOrgIds(@Param("ids") List<String> orgIds);

  @MapKey("sys_org_id")
  Map<String, Integer> getMembersCount(@Param("ids") List<String> orgIds);

  /**
   * 根据用户id获取部门信息
   *
   * @param userId 用户id
   * @return 部门名称
   */
  String getUserDepartbyId(@Param("userId") String userId);

  /**
   * 根据用户id获取部门信息
   *
   * @param userId 用户id
   * @return 部门名称
   */
  SysOrg getDepartByUserId(@Param("userId") String userId);

  List<SysUserOrgRef> selectSysUserRefList(SysUserOrgRefPageReqVO refPageReqVO);

  /**
   * 根据用户id列表获取用户所在部门信息
   *
   * @param ids 用户id列表
   * @return
   */
  List<SysOrg> getDepartByUserIds(@Param("ids") List<String> ids);
}
