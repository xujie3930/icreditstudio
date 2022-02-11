package com.micro.cloud.modules.system.log.service;

import com.micro.cloud.domian.dto.SysLoginLogCreateReqDTO;

/** 登录日志 Core Service 接口
 * @author roy*/
public interface SysLoginLogService {

  /**
   * 创建登录日志
   *
   * @param reqDTO 日志信息
   */
  void createLoginLog(SysLoginLogCreateReqDTO reqDTO);
}
