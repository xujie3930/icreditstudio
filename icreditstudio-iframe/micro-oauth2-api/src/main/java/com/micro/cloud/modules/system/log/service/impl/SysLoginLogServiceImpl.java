package com.micro.cloud.modules.system.log.service.impl;

import cn.hutool.core.date.DateUtil;
import com.micro.cloud.enums.SysLoginLogTypeEnum;
import com.micro.cloud.modules.system.log.convert.SysLoginLogConvert;
import com.micro.cloud.modules.system.log.dataobject.SysLoginLog;
import com.micro.cloud.domian.dto.SysLoginLogCreateReqDTO;
import com.micro.cloud.modules.system.log.mapper.SysLoginLogMapper;
import com.micro.cloud.modules.system.log.service.SysLoginLogService;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.modules.system.user.mapper.SysUserMapper;
import com.micro.cloud.util.servlet.ServletUtils;
import com.micro.cloud.snowflake.sequence.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录日志 Service 实现
 *
 * @author roy
 */
@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {

  @Autowired private SequenceService sequenceService;

  @Autowired private SysUserMapper userMapper;

  @Autowired private SysLoginLogMapper loginLogMapper;

  @Override
  public void createLoginLog(SysLoginLogCreateReqDTO reqDTO) {
    SysLoginLog loginLog = SysLoginLogConvert.INSTANCE.convert(reqDTO);
    loginLog.setSysLoginLogId(String.valueOf(sequenceService.nextValue(null)));
    // 获得用户
    SysUser sysUser = userMapper.selectOne("user_name", reqDTO.getUsername());
    // 默认使用用户名登录
    loginLog.setLogType(SysLoginLogTypeEnum.LOGIN_USERNAME.getType());
    if (sysUser != null) {
      loginLog.setUserId(sysUser.getSysUserId());
    }
    String clientIP = ServletUtils.getClientIP();
    loginLog.setUsername(sysUser.getUserName());
    loginLog.setUserType(sysUser.getType());
    loginLog.setResult(reqDTO.getResult());
    loginLog.setUserIp(clientIP);
    loginLog.setCreateTime(DateUtil.date());
    // 插入
    loginLogMapper.insert(loginLog);
  }
}
