package com.micro.cloud.modules.process.dal.mapper;

import com.micro.cloud.modules.process.dal.dataobject.ProcessAssociateSetting;
import com.micro.cloud.modules.process.vo.TaskVO;
import com.micro.cloud.mybatis.core.mapper.BaseMapperX;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper 接口
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Mapper
public interface ProcessAssociateSettingMapper extends BaseMapperX<ProcessAssociateSetting> {

  /**
   * 流程实例搜索结果
   *
   * @param processKey 流程识别码
   * @param userId 用户id
   * @return 搜索结果
   */
  List<TaskVO> getAssociatedProcessList(String processKey, String userId);
}
