package com.micro.cloud.modules.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.cloud.modules.system.user.dataobject.SysUserAccount;
import com.micro.cloud.modules.system.user.vo.SysUserAccountCreateReqVO;
import com.micro.cloud.modules.system.user.vo.external.ExternalUserCreateReqVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务类
 *
 * @author EDZ
 * @since 2021-11-05
 */
public interface SysUserAccountService extends IService<SysUserAccount> {

  /**
   * 创建用户账号
   * @param reqVO 用户个人/账号信息
   * @param userId 创建者id
   * @return 新增用户账号id
   */
  @Transactional(rollbackFor = Exception.class)
  String createUserAccount(SysUserAccountCreateReqVO reqVO, String userId);
}
