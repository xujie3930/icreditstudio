package com.micro.cloud.modules.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.cloud.enums.SysCommonStatusEnum;
import com.micro.cloud.enums.Identifier;
import com.micro.cloud.modules.system.user.convert.SysUserAccountConvert;
import com.micro.cloud.modules.system.user.mapper.SysUserAccountMapper;
import com.micro.cloud.modules.system.user.service.SysUserAccountService;
import com.micro.cloud.modules.system.user.dataobject.SysUserAccount;
import com.micro.cloud.modules.system.user.vo.SysUserAccountCreateReqVO;
import com.micro.cloud.snowflake.sequence.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Service
public class SysUserAccountServiceImpl extends ServiceImpl<SysUserAccountMapper, SysUserAccount>
    implements SysUserAccountService {

//  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private SequenceService sequenceService;

  /**
   * 创建用户账号
   *
   * @param reqVO 用户个人/账号信息
   * @return 新增用户账号id
   */
  @Override
  public String createUserAccount(SysUserAccountCreateReqVO reqVO, String userId) {
    // 构建用户账号信息
    SysUserAccount sysUserAccount = SysUserAccountConvert.INSTANCE.convertDO(reqVO);
    String accountId = String.valueOf(sequenceService.nextValue(null));
    sysUserAccount.setSysUserAccountId(accountId);
    // 状态正常
    sysUserAccount.setStatus(SysCommonStatusEnum.ENABLE.getStatus());
    // 默认账号未过期
    sysUserAccount.setAccountExpired(SysCommonStatusEnum.DISABLE.getStatus());
    // 默认账号未锁定
    sysUserAccount.setAccountLocked(SysCommonStatusEnum.DISABLE.getStatus());
    // 账号密码需加密
    sysUserAccount.setCredential(reqVO.getPassword());
    // 默认账号凭证未过期
    sysUserAccount.setCredentialExpired(SysCommonStatusEnum.DISABLE.getStatus());
    // 设置用户id
    sysUserAccount.setSysUserId(String.valueOf(userId));
    // 个人/机构用户默认使用用户名登录
    sysUserAccount.setIdentifier(Identifier.USERNAME.getValue());
    // todo 账号登录模式待定
    // 新增用户账号
    super.save(sysUserAccount);
    return accountId;
  }
}
