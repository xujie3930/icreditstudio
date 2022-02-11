package com.micro.cloud.modules.process.convert;

import com.micro.cloud.modules.process.dal.dataobject.ProcessAssociateSetting;
import com.micro.cloud.modules.process.dal.dataobject.ProcessAssociated;
import com.micro.cloud.modules.process.param.ProcessAssociateSettingParam;
import com.micro.cloud.modules.process.param.SaveAssociateProcessParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
public interface ProcessAssociateDataConvert {

  ProcessAssociateDataConvert INSTANCE = Mappers.getMapper(ProcessAssociateDataConvert.class);
}
