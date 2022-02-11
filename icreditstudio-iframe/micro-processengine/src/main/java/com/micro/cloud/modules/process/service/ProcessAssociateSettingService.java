package com.micro.cloud.modules.process.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.cloud.modules.process.dal.dataobject.ProcessAssociateSetting;
import com.micro.cloud.modules.process.param.ProcessAssociateSettingParam;

/**
 * 〈流程关联设置操作接口〉
 *
 * @author roy
 * @create 2021/12/10
 * @since 1.0.0
 */
public interface ProcessAssociateSettingService extends IService<ProcessAssociateSetting> {

  /**
   * 保存关联设置
   *
   * @param param 关联设置请求参数
   * @return 主键id
   */
  String saveSetting(ProcessAssociateSettingParam param);
}
