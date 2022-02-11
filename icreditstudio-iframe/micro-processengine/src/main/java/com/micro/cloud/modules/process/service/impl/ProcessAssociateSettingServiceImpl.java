package com.micro.cloud.modules.process.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.cloud.modules.process.convert.ProcessAssociateSettingConvert;
import com.micro.cloud.modules.process.dal.dataobject.ProcessAssociateSetting;
import com.micro.cloud.modules.process.dal.mapper.ProcessAssociateSettingMapper;
import com.micro.cloud.modules.process.param.ProcessAssociateSettingParam;
import com.micro.cloud.modules.process.service.ProcessAssociateSettingService;
import com.micro.cloud.snowflake.sequence.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 〈〉
 *
 * @author roy
 * @create 2021/12/18
 * @since 1.0.0
 */
@Service
public class ProcessAssociateSettingServiceImpl
    extends ServiceImpl<ProcessAssociateSettingMapper, ProcessAssociateSetting>
    implements ProcessAssociateSettingService {

  @Autowired private SequenceService sequenceService;

  @Autowired private ProcessAssociateSettingMapper processAssociateSettingMapper;

  /**
   * 保存关联设置
   *
   * @param param 关联设置请求参数
   * @return 主键id
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String saveSetting(ProcessAssociateSettingParam param) {
    ProcessAssociateSetting processAssociateSetting =
        ProcessAssociateSettingConvert.INSTANCE.convertDO(param);
    String settingId = sequenceService.nextStringValue(null);
    processAssociateSetting.setProcessAssociateSettingId(settingId);
    save(processAssociateSetting);
    return settingId;
  }
}
