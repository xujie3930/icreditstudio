package com.micro.cloud.modules.system.dict.convert;

import com.micro.cloud.api.CommonPage;
import com.micro.cloud.modules.system.dict.dataobject.SysDictType;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeCreateReqVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeRespVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeSimpleRespVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeUpdateReqVO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SysDictTypeConvert {

  SysDictTypeConvert INSTANCE = Mappers.getMapper(SysDictTypeConvert.class);

  /**
   * 字典类型转换视图对象
   *
   * @param bean
   * @return
   */
  @Mappings({@Mapping(source = "sysDictTypeId", target = "id")})
  SysDictTypeRespVO convert(SysDictType bean);

  /**
   * 数据库实体类转换为前端展示数据列表
   *
   * @param bean 分页数据
   * @return
   */
  List<SysDictTypeRespVO> convert(List<SysDictType> bean);

  /**
   * 创建请求数据转为数据库实体类
   *
   * @param bean 创建请求参数
   * @return 数据库实体类
   */
  SysDictType convert(SysDictTypeCreateReqVO bean);

  /**
   * 更新请求数据转换为数据库实体类
   *
   * @param bean 更新请求数据
   * @return 数据库实体类
   */
  @Mappings({@Mapping(source = "id", target = "sysDictTypeId")})
  SysDictType convert(SysDictTypeUpdateReqVO bean);

  /**
   * 数据库实体类转换为精简信息列表
   *
   * @param dictTypeList 数据库实体类列表
   * @return
   */
  @Mappings({@Mapping(source = "sysDictTypeId", target = "id")})
  List<SysDictTypeSimpleRespVO> convertSimpleList(List<SysDictType> dictTypeList);
}
