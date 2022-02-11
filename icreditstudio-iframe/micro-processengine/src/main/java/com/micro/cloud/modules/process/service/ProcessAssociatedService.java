package com.micro.cloud.modules.process.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.cloud.modules.process.dal.dataobject.ProcessAssociated;
import com.micro.cloud.modules.process.param.SaveAssociateProcessParam;

import java.util.List;

/**
 * 〈流程关联设置操作接口〉
 *
 * @author roy
 * @create 2021/12/10
 * @since 1.0.0
 */
public interface ProcessAssociatedService extends IService<ProcessAssociated> {

  /**
   * 保存关联数据
   *
   * @param param 关联设置请求参数
   * @return 主键id
   */
  Boolean saveData(SaveAssociateProcessParam param);

  /**
   * 删除关联数据
   * @param ids
   * @return
   */
  Boolean delData(List<String> ids);
}
