package com.micro.cloud.sms.core.enums;

import cn.hutool.core.util.ArrayUtil;

/**
 * 短信渠道枚举
 *
 * @author zzf
 * @date 2021/1/25 10:56
 */
public enum SmsChannelEnum {
  /** 阿里云 */
  ALIYUN("ALIYUN", "阿里云");

  /** 编码 */
  private final String code;
  /** 名字 */
  private final String name;

  SmsChannelEnum(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public static SmsChannelEnum getByCode(String code) {
    return ArrayUtil.firstMatch(o -> o.getCode().equals(code), values());
  }
}
