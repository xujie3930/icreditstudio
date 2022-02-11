package com.micro.cloud.modules.system.user.convert;

import com.micro.cloud.modules.system.user.vo.SysLoginReqVo;
import com.micro.cloud.modules.system.user.vo.SysLoginWithoutCaptchaReqVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 〈登录参数类型转换〉
 *
 * @author roy
 * @create 2021/12/22
 * @since 1.0.0
 */
@Mapper
public interface LoginParamConvert {

  LoginParamConvert INSTANCE = Mappers.getMapper(LoginParamConvert.class);

  /**
   * 用户登录参数
   *
   * @param reqVo 请求参数
   * @return 登录结果
   */
  SysLoginReqVo convertVo(SysLoginWithoutCaptchaReqVo reqVo);
}
