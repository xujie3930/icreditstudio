package com.micro.cloud.modules.system.role.convert;

import com.micro.cloud.api.CommonPage;
import com.micro.cloud.modules.system.role.dataobject.SysRole;
import com.micro.cloud.modules.system.role.vo.SysRoleCreateReqVO;
import com.micro.cloud.modules.system.role.vo.SysRoleRespVO;
import com.micro.cloud.modules.system.role.vo.SysRoleSimpleRespVO;
import com.micro.cloud.modules.system.role.vo.SysRoleUpdateReqVO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SysRoleConvert {

  SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

  /**
   * 角色数据转换
   *
   * @param bean
   * @return
   */
  @Mappings({
    @Mapping(source = "id", target = "sysRoleId"),
    @Mapping(source = "name", target = "roleName")
  })
  SysRole convert(SysRoleUpdateReqVO bean);

  /**
   * 数据库实体类转换为角色展示信息
   *
   * @param bean
   * @return
   */
  @Mappings({
    @Mapping(source = "sysRoleId", target = "id"),
    @Mapping(source = "roleName", target = "name"),
  })
  SysRoleRespVO convert(SysRole bean);

  /**
   * 数据库实体类转换为角色展示信息
   *
   * @param list
   * @return
   */
  @Mappings({
    @Mapping(source = "sysRoleId", target = "id"),
    @Mapping(source = "roleName", target = "name"),
    @Mapping(source = "roleCode", target = "code")
  })
  List<SysRoleRespVO> convert(List<SysRole> list);

  /**
   * 创建信息转换为数据库实体类
   *
   * @param bean
   * @return
   */
  @Mappings({@Mapping(source = "name", target = "roleName")})
  SysRole convert(SysRoleCreateReqVO bean);

  /**
   * 列表数据转换
   *
   * @param bean
   * @return
   */
  @Mappings({
    @Mapping(source = "sysRoleId", target = "id"),
    @Mapping(source = "roleName", target = "name")
  })
  SysRoleSimpleRespVO convertSimpleVO(SysRole bean);

  /**
   * 列表数据转换
   *
   * @param list
   * @return
   */
  @Mappings({
    @Mapping(source = "sysRoleId", target = "id"),
    @Mapping(source = "roleName", target = "name")
  })
  List<SysRoleSimpleRespVO> convertSimpleList(List<SysRole> list);

  /**
   * 分页数据转换
   *
   * @param page 系统角色分页数据
   * @return 前端视图展示数据
   */
  CommonPage<SysRoleRespVO> covertPage(CommonPage<SysRole> page);

}
