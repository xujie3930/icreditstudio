package com.micro.cloud.modules.system.log.convert;

import com.micro.cloud.modules.system.log.dataobject.SysLoginLog;
import com.micro.cloud.domian.dto.SysLoginLogCreateReqDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 日志实体类转换
 *
 * @author roy
 */
@Mapper
public interface SysLoginLogConvert {

  SysLoginLogConvert INSTANCE = Mappers.getMapper(SysLoginLogConvert.class);

  /**
   * 日志创建信息转换为数据库实体类
   *
   * @param bean 日志创建信息
   * @return 数据库实体类
   */
  SysLoginLog convert(SysLoginLogCreateReqDTO bean);
}
