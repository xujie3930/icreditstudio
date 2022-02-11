package com.micro.cloud.modules.system.common.service.impl;

import static cn.hutool.core.util.RandomUtil.randomInt;
import static com.micro.cloud.constant.SysErrorCodeConstants.USER_SMS_CODE_EXCEED_SEND_MAXIMUM_QUANTITY_PER_DAY;
import static com.micro.cloud.constant.SysErrorCodeConstants.USER_SMS_CODE_EXPIRED;
import static com.micro.cloud.constant.SysErrorCodeConstants.USER_SMS_CODE_NOT_CORRECT;
import static com.micro.cloud.constant.SysErrorCodeConstants.USER_SMS_CODE_NOT_FOUND;
import static com.micro.cloud.constant.SysErrorCodeConstants.USER_SMS_CODE_SEND_TOO_FAST;
import static com.micro.cloud.constant.SysErrorCodeConstants.USER_SMS_CODE_USED;

import cn.hutool.core.date.DateUtil;
import com.micro.cloud.enums.SysCommonStatusEnum;
import com.micro.cloud.exception.ApiException;
import com.micro.cloud.modules.system.common.dataobject.SysSmsCode;
import com.micro.cloud.modules.system.common.dto.SysSmsSendMessage;
import com.micro.cloud.modules.system.common.mapper.SysSmsCodeMapper;
import com.micro.cloud.modules.system.common.service.SysSmsCodeService;
import com.micro.cloud.config.sms.SmsCodeProperties;
import com.micro.cloud.sms.core.client.SmsClient;
import com.micro.cloud.sms.core.client.SmsClientFactory;
import com.micro.cloud.sms.core.client.SmsCommonResult;
import com.micro.cloud.sms.core.client.dto.SmsSendRespDTO;
import com.micro.cloud.snowflake.sequence.SequenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 短信 Service Core 实现
 *
 * @author roy
 */
@Service
public class SysSmsCodeServiceImpl implements SysSmsCodeService {

  private final Logger logger = LoggerFactory.getLogger(SysSmsCodeServiceImpl.class);

  @Autowired private SmsClientFactory smsClientFactory;
  @Autowired protected SequenceService sequenceService;

  @Autowired private SmsCodeProperties smsCodeProperties;

  @Autowired private SysSmsCodeMapper smsCodeMapper;

  /**
   * 创建短信验证码，并进行发送
   *
   * @param mobile 手机号
   * @param scene 发送场景
   * @param createIp 发送 IP
   */
  @Override
  public void sendSmsCode(String mobile, Integer scene, String createIp) {
    // 创建验证码
    String code = this.createSmsCode(mobile, scene, createIp);
    logger.info("code:{}", code);
    // 发送验证码
    // doSendSms();
  }

  private String createSmsCode(String mobile, Integer scene, String ip) {
    // 校验是否可以发送验证码，不用筛选场景
    SysSmsCode lastSmsCode = smsCodeMapper.selectLastByMobile(mobile, scene);
    if (lastSmsCode != null) {
      if (lastSmsCode.getTodayIndex()
          >= smsCodeProperties.getSendMaximumQuantityPerDay()) { // 超过当天发送的上限。
        throw new ApiException(USER_SMS_CODE_EXCEED_SEND_MAXIMUM_QUANTITY_PER_DAY);
      }
      if (System.currentTimeMillis() - lastSmsCode.getCreateTime().getTime()
          < smsCodeProperties.getSendFrequency().toMillis()) { // 发送过于频繁
        throw new ApiException(USER_SMS_CODE_SEND_TOO_FAST);
      }
    }

    // 创建验证码记录
    String code =
        String.valueOf(
            randomInt(smsCodeProperties.getBeginCode(), smsCodeProperties.getEndCode() + 1));
    SysSmsCode smsCodeRecord = new SysSmsCode();
    smsCodeRecord.setMobile(mobile);
    smsCodeRecord.setCode(code);
    smsCodeRecord.setId(String.valueOf(sequenceService.nextValue(null)));
    smsCodeRecord.setCreateIp(ip);
    smsCodeRecord.setScene(scene);
    smsCodeRecord.setTodayIndex(lastSmsCode != null ? lastSmsCode.getTodayIndex() + 1 : 1);
    smsCodeRecord.setUsed(SysCommonStatusEnum.DISABLE.getStatus());
    smsCodeMapper.insert(smsCodeRecord);

    return code;
  }

  public void doSendSms(SysSmsSendMessage message) {
    // 获得渠道对应的 SmsClient 客户端
    SmsClient smsClient = this.smsClientFactory.getSmsClient(message.getChannelId());
    Assert.notNull(smsClient, String.format("短信客户端(%d) 不存在", message.getChannelId()));
    // 发送短信
    SmsCommonResult<SmsSendRespDTO> sendResult =
        smsClient.sendSms(
            message.getLogId(),
            message.getMobile(),
            message.getApiTemplateId(),
            message.getTemplateParams());
    /* this.smsLogCoreService.updateSmsSendResult(message.getLogId(), sendResult.getCode(), sendResult.getMsg(),
    sendResult.getApiCode(), sendResult.getApiMsg(), sendResult.getApiRequestId(),
    sendResult.getData() != null ? sendResult.getData().getSerialNo() : null);*/
  }
  /**
   * 验证短信验证码，并进行使用 如果正确，则将验证码标记成已使用 如果错误，则抛出
   *
   * @param mobile 手机号
   * @param scene 发送场景
   * @param code 验证码
   * @param usedIp 使用 IP
   */
  @Override
  public void useSmsCode(String mobile, Integer scene, String code, String usedIp) {
    // 校验验证码
    SysSmsCode lastSmsCode = smsCodeMapper.selectLastByMobile(mobile, scene);
    // 若验证码不存在，抛出异常
    if (lastSmsCode == null) {
      throw new ApiException(USER_SMS_CODE_NOT_FOUND);
    }
    // 验证码已过期
    if (System.currentTimeMillis() - lastSmsCode.getCreateTime().getTime()
        >= smsCodeProperties.getExpireTimes().toMillis()) {
      throw new ApiException(USER_SMS_CODE_EXPIRED);
    }
    // 验证码已使用
    if (SysCommonStatusEnum.ENABLE.getStatus().equals(lastSmsCode.getUsed())) {
      throw new ApiException(USER_SMS_CODE_USED);
    }
    if (!lastSmsCode.getCode().equals(code)) {
      throw new ApiException(USER_SMS_CODE_NOT_CORRECT);
    }

    // 使用验证码
    SysSmsCode smsCodeRecord = new SysSmsCode();
    smsCodeRecord.setId(lastSmsCode.getId());
    // 已使用
    smsCodeRecord.setUsed(SysCommonStatusEnum.ENABLE.getStatus());
    smsCodeRecord.setUsedTime(DateUtil.date());
    smsCodeRecord.setUsedIp(usedIp);
    smsCodeMapper.updateById(smsCodeRecord);
  }
}
