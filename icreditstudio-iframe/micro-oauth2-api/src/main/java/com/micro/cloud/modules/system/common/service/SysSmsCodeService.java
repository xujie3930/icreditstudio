package com.micro.cloud.modules.system.common.service;

import com.micro.cloud.validation.Mobile;
import org.springframework.transaction.annotation.Transactional;

/**
 * 短信验证码 Service 接口
 *
 * @author roy
 */
public interface SysSmsCodeService {

  /**
   * 创建短信验证码，并进行发送
   *
   * @param mobile 手机号
   * @param scene 发送场景
   * @param createIp 发送 IP
   */
  @Transactional(rollbackFor = Exception.class)
  void sendSmsCode(@Mobile String mobile, Integer scene, String createIp);

  /**
   * 验证短信验证码，并进行使用 如果正确，则将验证码标记成已使用 如果错误，则抛出
   *
   * @param mobile 手机号
   * @param scene 发送场景
   * @param code 验证码
   * @param usedIp 使用 IP
   */
  void useSmsCode(@Mobile String mobile, Integer scene, String code, String usedIp);
}
