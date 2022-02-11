package com.micro.cloud.modules.system.common.service.impl;

import static com.micro.cloud.constant.SysErrorCodeConstants.AUTH_LOGIN_CAPTCHA_CODE_ERROR;
import static com.micro.cloud.constant.SysErrorCodeConstants.AUTH_LOGIN_CAPTCHA_NOT_FOUND;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.codec.Base64;
import com.micro.cloud.config.captcha.CaptchaProperties;
import com.micro.cloud.exception.ApiException;
import com.micro.cloud.modules.system.common.service.SysCaptchaService;
import com.micro.cloud.modules.system.util.VerifyCode;
import com.micro.cloud.redis.service.RedisService;
import com.micro.cloud.util.secure.MD5Util;
import com.micro.cloud.util.servlet.ServletUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 验证码 Service 实现类
 *
 * @author roy
 */
@Service
public class SysCaptchaServiceImpl implements SysCaptchaService {

  private Logger logger = LoggerFactory.getLogger(SysCaptchaServiceImpl.class);

  @Autowired private CaptchaProperties captchaProperties;

  @Autowired private RedisService redisService;

  @Value("${captcha.timeout}")
  private Long timeout;

  @Override
  public String getCaptchaImage() {
    String clientIP = ServletUtils.getClientIP();
    logger.info("####### clientIP:{}", clientIP);
    // 生成验证码
    CircleCaptcha captcha =
        CaptchaUtil.createCircleCaptcha(
            captchaProperties.getWidth(), captchaProperties.getHeight());
    String lowerCaseCode = captcha.getCode().toLowerCase();
    String realKey = MD5Util.MD5Encode(clientIP, "utf-8");
    // todo 防刷机制 统计一段时间间隔内同一IP请求次数，请求次数过多则返回提示
    // 放入redis,持续时间60s
    redisService.set(realKey, lowerCaseCode, timeout);
    return captcha.getImageBase64();
  }

  @Override
  public String getCaptchaImageForNum() throws IOException {
    String clientIP = ServletUtils.getClientIP();
    logger.info("####### clientIP:{}", clientIP);
    // 生成验证码
    int width=200;

    int height=69;
    BufferedImage verifyImg=new BufferedImage(captchaProperties.getWidth(),captchaProperties.getHeight(),BufferedImage.TYPE_INT_RGB);
//生成对应宽高的初始图片
    String randomText = VerifyCode.drawRandomText(captchaProperties.getWidth(),captchaProperties.getHeight(),verifyImg);
    ByteArrayOutputStream os =new ByteArrayOutputStream();
    ImageIO.write(verifyImg,"png",os);

    String realKey = MD5Util.MD5Encode( clientIP, "utf-8");
    // todo 防刷机制 统计一段时间间隔内同一IP请求次数，请求次数过多则返回提示
    // 放入redis,持续时间60s
    redisService.set(realKey, randomText, timeout);
    return Base64.encode(os.toByteArray());
  }

  @Override
  public String getCaptchaCode(String uuid) {
    return (String) redisService.get(uuid);
  }

  @Override
  public void deleteCaptchaCode(String uuid) {
    redisService.del(uuid);
  }

  /**
   * 图片验证码校验
   *
   * @param captchaCode 图片验证码
   */
  @Override
  public void verifyCaptcha(String captchaCode) {
    String clientIP = ServletUtils.getClientIP();
    String lowerCaseCode = captchaCode.toLowerCase();
    String realKey = MD5Util.MD5Encode( clientIP, "utf-8");
    String code = this.getCaptchaCode(realKey);
    // 验证码不存在
    if (StringUtils.isBlank(code)) {
      // 创建登录失败日志（验证码不存在）
      throw new ApiException(AUTH_LOGIN_CAPTCHA_NOT_FOUND);
    }
    // 验证码不正确
    if (ObjectUtils.notEqual(captchaCode, code)) {
      // 创建登录失败日志（验证码不正确)
      throw new ApiException(AUTH_LOGIN_CAPTCHA_CODE_ERROR);
    }
    // 校验通过后，删除验证码
    this.deleteCaptchaCode(realKey);
  }
}
