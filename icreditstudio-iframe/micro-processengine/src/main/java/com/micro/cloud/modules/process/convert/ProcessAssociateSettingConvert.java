package com.micro.cloud.modules.process.convert;

import com.micro.cloud.modules.process.dal.dataobject.ProcessAssociateSetting;
import com.micro.cloud.modules.process.param.ProcessAssociateSettingParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 〈流程关联实体类转换〉
 *
 * @author roy
 * @create 2021/12/18
 * @since 1.0.0
 */
@Mapper
public interface ProcessAssociateSettingConvert {

  ProcessAssociateSettingConvert INSTANCE = Mappers.getMapper(ProcessAssociateSettingConvert.class);

  /**
   * 请求参数转换为数据库实体类
   *
   * @param bean 请求参数
   * @return 数据库实体类
   */
  ProcessAssociateSetting convertDO(ProcessAssociateSettingParam bean);
}
