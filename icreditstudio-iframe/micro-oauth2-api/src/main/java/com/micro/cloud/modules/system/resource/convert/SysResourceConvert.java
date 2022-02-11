package com.micro.cloud.modules.system.resource.convert;

import com.micro.cloud.modules.system.resource.dataobject.SysResource;
import com.micro.cloud.modules.system.resource.vo.SysResourceCreateReqVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceNodeVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceRespVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceSimpleRespVO;
import com.micro.cloud.modules.system.resource.vo.SysResourceTreeModel;
import com.micro.cloud.modules.system.resource.vo.SysResourceUpdateReqVO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/** @author roy */
@Mapper
public interface SysResourceConvert {

  SysResourceConvert INSTANCE = Mappers.getMapper(SysResourceConvert.class);

  /**
   * 数据库实体类转为前段展示信息
   *
   * @param list
   * @return
   */
  List<SysResourceRespVO> convertList(List<SysResource> list);

  /**
   * 数据库实体类转换为前端展示字段
   *
   * @param list
   * @return
   */
  List<SysResourceSimpleRespVO> convertSimple(List<SysResource> list);

  /**
   * 创建数据转换
   *
   * @param bean
   * @return
   */
  SysResource convert(SysResourceCreateReqVO bean);

  /**
   * 更新数据转换
   *
   * @param bean
   * @return
   */
  SysResource convert(SysResourceUpdateReqVO bean);

  /**
   * 数据库实体类转换为前端信息展示
   *
   * @param bean
   * @return
   */
  SysResourceRespVO convert(SysResource bean);

  /**
   * 数据库实体类转为操作权限树形结构
   *
   * @param sysResource
   * @return
   */
  @Mappings({
    @Mapping(source = "sysResourceId", target = "key"),
    @Mapping(source = "name", target = "title"),
  })
  SysResourceNodeVO convertTree(SysResource sysResource);

  /**
   * 数据库实体类转为操作权限树形结构
   *
   * @param sysResource
   * @return
   */
  @Mappings({@Mapping(source = "sysResourceId", target = "id")})
  List<SysResourceNodeVO> convertTree(List<SysResource> sysResource);
}
