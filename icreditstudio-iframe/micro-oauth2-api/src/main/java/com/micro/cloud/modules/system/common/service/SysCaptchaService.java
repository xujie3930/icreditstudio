package com.micro.cloud.modules.system.common.service;

import com.micro.cloud.modules.system.common.vo.SysCaptchaImageRespVO;

import java.io.IOException;

/**
 * 验证码 Service 接口
 *
 * @author roy
 */
public interface SysCaptchaService {

  /**
   * 获得验证码图片
   *
   * @return 验证码图片
   */
  String getCaptchaImage();


  String getCaptchaImageForNum() throws IOException;

  /**
   * 获得 uuid 对应的验证码
   *
   * @param uuid 验证码编号
   * @return 验证码
   */
  String getCaptchaCode(String uuid);

  /**
   * 删除 uuid 对应的验证码
   *
   * @param uuid 验证码编号
   */
  void deleteCaptchaCode(String uuid);

  /**
   * 图片验证码校验
   *
   * @param captchaCode 图片验证码
   */
  void verifyCaptcha(String captchaCode);
}
