package com.micro.cloud.modules.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.cloud.domian.dto.UserDTO;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.modules.system.user.vo.CommonUserInfoVo;
import com.micro.cloud.modules.system.user.vo.GenerateTokenRepVo;
import com.micro.cloud.modules.system.user.vo.GenerateTokenReqVo;
import com.micro.cloud.modules.system.user.vo.PasswordResetReqVO;
import com.micro.cloud.modules.system.user.vo.PhoneValidateReqVo;
import com.micro.cloud.modules.system.user.vo.SysLoginRepVo;
import com.micro.cloud.modules.system.user.vo.SysLoginReqVo;
import com.micro.cloud.modules.system.user.vo.SysLoginWithoutCaptchaReqVo;
import com.micro.cloud.modules.system.user.vo.SysUserAuthReqVO;
import com.micro.cloud.modules.system.user.vo.UpdatePasswordReqVO;
import com.micro.cloud.modules.system.user.vo.UserValidateReqVo;
import com.micro.cloud.modules.system.user.vo.org.OrgUserPageRepVO;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户操作业务接口
 *
 * @author EDZ
 * @since 2021-11-05
 */
public interface CommonUserService extends IService<SysUser> {

  /**
   * 用户注册服务
   *
   * @param vo 用户注册请求
   * @return 注册是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean auth(SysUserAuthReqVO vo);

  /**
   * 根据用户名获取用户相关信息
   *
   * @param username 用户名
   * @return 用户相关信息
   */
  UserDTO getUserInfoByName(String username);

  /**
   * 登录功能 reqVo 请求登录参数
   *
   * @return 调用认证中心返回结果
   */
  SysLoginRepVo login(SysLoginReqVo reqVo);

  /**
   * 用户登录-无验证码
   *
   * @param reqVo 用户登录请求参数
   * @return 登录结果
   */
  SysLoginRepVo loginWithNoCaptcha(SysLoginWithoutCaptchaReqVo reqVo);

  /**
   * 用户重置密码(外部使用)
   *
   * @param reqVO 重置密码请求参数
   * @return 是否成功
   */
  Boolean passwordReset(PasswordResetReqVO reqVO);

  /**
   * 用户更改密码(内部使用)
   *
   * @param reqVO 更改密码请求参数
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean updatePassword(UpdatePasswordReqVO reqVO);

  /**
   * 用户登出
   *
   * @param request HttpServletRequest请求
   * @return true/false
   */
  Boolean logout(HttpServletRequest request);

  /**
   * 根据用户名密码获取token(外部系统对接使用)
   *
   * @param reqVo 请求token参数
   * @return token
   */
  GenerateTokenRepVo generateToken(GenerateTokenReqVo reqVo);

  CommonUserInfoVo getCurrentUserInfo(HttpServletRequest request);

  /**
   * 更新用户信息
   *
   * @param vo
   * @return
   */
  Boolean updateCommonInfo(CommonUserInfoVo vo);

  /**
   * 用户名密码校验
   *
   * @param reqVo 用户名密码校验请求参数
   * @return 是否成功
   */
  Boolean validate(UserValidateReqVo reqVo);

  /**
   * 用户更换手机号校验
   *
   * @param reqVo 更换手机号请求参数
   * @return 是否成功
   */
  Boolean validatePhone(PhoneValidateReqVo reqVo);

  /**
   * 根据用户id集合批量获取用户部门等信息
   *
   * @param ids 用户id集合
   * @return 用户部门等信息
   */
  Map<String, OrgUserPageRepVO> getOrgInfoByUserIds(List<String> ids);
}
