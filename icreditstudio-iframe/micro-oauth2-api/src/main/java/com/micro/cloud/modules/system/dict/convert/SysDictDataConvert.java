package com.micro.cloud.modules.system.dict.convert;

import com.micro.cloud.modules.system.dict.dataobject.SysDictData;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataCreateReqVO;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataRespVO;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataSimpleRespVO;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataUpdateReqVO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SysDictDataConvert {

  SysDictDataConvert INSTANCE = Mappers.getMapper(SysDictDataConvert.class);

  /**
   * 数据库实体类转换为前端展示数据
   *
   * @param list 数据库实体类列表
   * @return
   */
  List<SysDictDataSimpleRespVO> convertList(List<SysDictData> list);

  SysDictDataRespVO convert(SysDictData bean);

  List<SysDictDataRespVO> convert(List<SysDictData> list);

  /**
   * 字典数据更新请求转换为数据库实体类
   *
   * @param bean 更新请求数据
   * @return 数据库实体类
   */
  @Mappings({@Mapping(source = "id", target = "sysDictDataId")})
  SysDictData convert(SysDictDataUpdateReqVO bean);

  /**
   * 字典数据创建请求数据转换为数据库实体类
   *
   * @param bean 请求数据
   * @return 数据库实体类
   */
  SysDictData convert(SysDictDataCreateReqVO bean);
}
