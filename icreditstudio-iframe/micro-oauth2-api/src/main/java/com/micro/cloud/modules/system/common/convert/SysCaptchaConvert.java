package com.micro.cloud.modules.system.common.convert;

import cn.hutool.captcha.AbstractCaptcha;
import com.micro.cloud.modules.system.common.vo.SysCaptchaImageRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author roy
 */
@Mapper
public interface SysCaptchaConvert {

  SysCaptchaConvert INSTANCE = Mappers.getMapper(SysCaptchaConvert.class);

  /**
   * 验证码信息转换
   * @param uuid 随机id
   * @param captcha 验证码信息
   * @return 验证码信息实体类
   */
  default SysCaptchaImageRespVO convert(String uuid, AbstractCaptcha captcha) {
    SysCaptchaImageRespVO sysCaptchaImageRespVO = new SysCaptchaImageRespVO();
    sysCaptchaImageRespVO.setUuid(uuid);
    sysCaptchaImageRespVO.setImg(captcha.getImageBase64());
    return sysCaptchaImageRespVO;
  }
}
