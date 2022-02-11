package com.micro.cloud.modules.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.micro.cloud.modules.system.org.vo.SysUserRoleRefPageReqVO;
import com.micro.cloud.modules.system.role.vo.SysRoleRespVO;
import com.micro.cloud.modules.system.user.dataobject.SysUserRoleRef;
import com.micro.cloud.modules.system.user.dto.SysUserRoleRefDto;
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
public interface SysUserRoleRefMapper extends BaseMapperX<SysUserRoleRef> {

  /**
   * 根据用户id集合获取用户角色信息
   * @param ids 用户id集合
   * @return 用关乎角色信息
   */
  @MapKey(value = "userId")
  Map<String, SysUserRoleRefDto> getRoleByUserId(@Param(value = "ids") List<String> ids,@Param("roleId") String roleId);


  List<SysUserRoleRef> selectSysUserRoleRefPageList(SysUserRoleRefPageReqVO reqVO);
}
