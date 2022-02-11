package com.micro.cloud.modules.system.role.mapper;

import com.micro.cloud.modules.system.resource.vo.SysResourceRespVO;
import com.micro.cloud.modules.system.role.dataobject.SysRoleResourceRef;
import com.micro.cloud.modules.system.role.dto.RoleResourceRefMapDto;
import com.micro.cloud.modules.system.role.vo.SysRoleResourceExternalVo;
import com.micro.cloud.mybatis.core.mapper.BaseMapperX;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public interface SysRoleResourceRefMapper extends BaseMapperX<SysRoleResourceRef> {

  /**
   * 根据用户id获取该用户资源权限
   *
   * @param userId
   * @return
   */
  List<SysResourceRespVO> getUserResourceById(@Param("userId") String userId);

  /**
   * 获取url对应角色列表
   *
   * @return
   */
  @MapKey("url")
  Map<String, RoleResourceRefMapDto> getResourceRolesMap();

  /**
   * 根据角色id获取对应权限id
   *
   * @param roleId
   * @return
   */
  List<String> getResourceByRoleId(@Param(value = "roleId") String roleId);

  /**
   * 同步role菜单关系给外包项目管理系统
   *
   * @param
   * @return
   */
  List<SysRoleResourceExternalVo> getAllRoleResourceRefs();
}
