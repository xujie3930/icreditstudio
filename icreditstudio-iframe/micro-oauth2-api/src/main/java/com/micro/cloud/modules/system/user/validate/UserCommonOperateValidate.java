package com.micro.cloud.modules.system.user.validate;

import static com.micro.cloud.constant.SysErrorCodeConstants.ORG_EXITS_CHILDREN;
import static com.micro.cloud.constant.SysErrorCodeConstants.ORG_NAME_DUPLICATE;
import static com.micro.cloud.constant.SysErrorCodeConstants.ORG_NOT_ENABLE;
import static com.micro.cloud.constant.SysErrorCodeConstants.ORG_NOT_FOUND;
import static com.micro.cloud.constant.SysErrorCodeConstants.USER_ACCOUNT_LOCKED;
import static com.micro.cloud.constant.SysErrorCodeConstants.USER_MOBILE_EXISTS;
import static com.micro.cloud.constant.SysErrorCodeConstants.USER_NOT_EXISTS;
import static com.micro.cloud.constant.SysErrorCodeConstants.USER_USERNAME_EXISTS;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.annotations.VisibleForTesting;
import com.micro.cloud.constant.SysErrorCodeConstants;
import com.micro.cloud.modules.system.org.dataobject.SysOrg;
import com.micro.cloud.modules.system.org.mapper.SysOrgMapper;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.enums.SysCommonStatusEnum;
import com.micro.cloud.exception.ApiException;
import com.micro.cloud.modules.system.user.dataobject.SysUserAccount;
import com.micro.cloud.modules.system.user.mapper.SysUserAccountMapper;
import com.micro.cloud.modules.system.user.mapper.SysUserMapper;
import com.micro.cloud.modules.system.user.vo.SysLoginReqVo;
import com.micro.cloud.modules.system.user.vo.SysUserUpdateReqVO;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 〈用户操作管理通用校验〉
 *
 * @author roy
 * @create 2021/11/6
 * @since 1.0.0
 */
@Component
public class UserCommonOperateValidate {

  private Logger logger = LoggerFactory.getLogger(UserCommonOperateValidate.class);

//  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private SysUserMapper userMapper;

  @Autowired private SysUserAccountMapper userAccountMapper;

  @Autowired private SysOrgMapper orgMapper;

  public void checkCreateOrUpdate(
      String id, String username, String email, String mobile, String deptId) {
    // 校验用户存在
    this.checkUserExists(id);
    // 校验用户名是否唯一
    this.checkUsernameUnique(username);
    // 校验手机号唯一
    this.checkMobileUnique(id, mobile);
    // 校验email是否唯一
    this.checkEmailUnique(id, email);
    // 校验部门处于开启状态
    this.checkDeptEnable(deptId);
  }

  @VisibleForTesting
  public void checkUserExists(String id) {
    if (StrUtil.isBlank(id)) {
      return;
    }
    SysUser user = userMapper.selectById(id);
    if (user == null) {
      throw new ApiException(USER_NOT_EXISTS);
    }
  }

  @VisibleForTesting
  public void checkMobileUnique(String id, String mobile) {
    if (StrUtil.isBlank(mobile)) {
      return;
    }
    QueryWrapper<SysUser> userPhoneWrapper = new QueryWrapper<>();
    userPhoneWrapper.eq("phone", mobile);
    SysUser user = userMapper.selectOne(userPhoneWrapper);
    if (user == null) {
      return;
    }
    // 如果 id 为空，说明不用比较是否为相同 id 的用户
    if (id == null) {
      throw new ApiException(USER_MOBILE_EXISTS);
    }
    if (!user.getSysUserId().equals(id)) {
      throw new ApiException(USER_MOBILE_EXISTS);
    }
  }

  public void checkEmailUnique(String id, String email) {
    if (StrUtil.isBlank(email)) {
      return;
    }
    QueryWrapper<SysUser> userPhoneWrapper = new QueryWrapper<>();
    userPhoneWrapper.eq("email", email);
    SysUser user = userMapper.selectOne(userPhoneWrapper);
    if (user == null) {
      return;
    }
    // 如果 id 为空，说明不用比较是否为相同 id 的用户
    if (id == null) {
      throw new ApiException(SysErrorCodeConstants.USER_EMAIL_EXISTS);
    }
    if (!user.getSysUserId().equals(id)) {
      throw new ApiException(SysErrorCodeConstants.USER_EMAIL_EXISTS);
    }
  }

  public void checkDeptEnable(String orgId) {
    // 允许外部用户(无部门/组织架构)
    if (StrUtil.isBlank(orgId)) {
      return;
    }
    QueryWrapper<SysOrg> orgQueryWrapper = new QueryWrapper<>();
    orgQueryWrapper.eq("sys_org_id", orgId);
    SysOrg org = orgMapper.selectOne(orgQueryWrapper);
    if (Objects.isNull(org)) {
      throw new ApiException(ORG_NOT_FOUND);
    }
    if (!SysCommonStatusEnum.ENABLE.getStatus().equals(org.getStatus())) {
      throw new ApiException(ORG_NOT_ENABLE);
    }
  }

  public void checkUsernameUnique(String username) {
    if (StrUtil.isBlank(username)) {
      return;
    }
    QueryWrapper<SysUser> usernameWrapper = new QueryWrapper<>();
    usernameWrapper.eq("user_name", username);
    SysUser user = userMapper.selectOne(usernameWrapper);
    if (user == null) {
      return;
    }
    throw new ApiException(USER_USERNAME_EXISTS);
  }

  public void checkDeptExists(String name, String creditCode) {
    QueryWrapper<SysOrg> orgQueryWrapper = new QueryWrapper<>();
    orgQueryWrapper.eq("org_name", name).eq("org_credit_code", creditCode);
    SysOrg sysOrg = orgMapper.selectOne(orgQueryWrapper);
    if (Objects.nonNull(sysOrg)) {
      throw new ApiException(ORG_NAME_DUPLICATE);
    }
  }

  public void checkUserLegality(SysUser sysUser, String password) {
    if (Objects.isNull(sysUser)) {
      throw new ApiException(SysErrorCodeConstants.USER_NOT_EXISTS);
    }
    SysUserAccount userAccount = userAccountMapper.selectOne("sys_user_id", sysUser.getSysUserId());
    // 校验用户状态
    if (SysCommonStatusEnum.DISABLE.getStatus().booleanValue()
        == sysUser.getStatus().booleanValue()) {
      throw new ApiException(SysErrorCodeConstants.USER_DISABLE);
    }
    // 校验用户账号是否过期
    if (SysCommonStatusEnum.ENABLE.getStatus().equals(userAccount.getAccountExpired())) {
      throw new ApiException(SysErrorCodeConstants.USER_ACCOUNT_DISABLE);
    }
    // 校验用户账号是否被锁定
    if (SysCommonStatusEnum.ENABLE.getStatus().equals(userAccount.getAccountLocked())) {
      throw new ApiException(SysErrorCodeConstants.USER_ACCOUNT_LOCKED);
    }
    // 校验用户密码
    if (StringUtils.isBlank(password)) {
      return;
    }
    if (!password.equals(userAccount.getCredential())){
      throw new ApiException(SysErrorCodeConstants.USER_PASSWORD_FAILED);
    }
//    if (!passwordEncoder.matches(password, userAccount.getCredential())) {
//      throw new ApiException(SysErrorCodeConstants.USER_PASSWORD_FAILED);
//    }
  }

  public void checkUserPassword(
      String userId, String newPassword, String confirmPassword, String originalPassword) {
    if (ObjectUtil.notEqual(newPassword, confirmPassword)) {
      throw new ApiException(SysErrorCodeConstants.PASSWORD_NOT_CONFIRM);
    }
    if (newPassword.equals(originalPassword)){
      throw new ApiException(SysErrorCodeConstants.USER_PASSWORD_FAILED);
    }
//    if (passwordEncoder.matches(newPassword, originalPassword)) {
//      throw new ApiException(SysErrorCodeConstants.PASSWORD_EQUAL_ORIGIN);
//    }
  }

  /**
   * 用户更新时校验唯一性
   *
   * @param reqVO 更新请求数据
   */
  public void checkForUpdateUserInfo(SysUserUpdateReqVO reqVO) {
    // 校验唯一性
    SysUser original = userMapper.selectById(reqVO.getUserId());
    if (ObjectUtil.notEqual(reqVO.getUsername(), original.getUserName())) {
      this.checkUsernameUnique(reqVO.getUsername());
    }
    if (ObjectUtil.notEqual(reqVO.getMobile(), original.getPhone())) {
      this.checkMobileUnique(reqVO.getUserId(), reqVO.getMobile());
    }
    if (ObjectUtil.notEqual(reqVO.getEmail(), original.getEmail())) {
      this.checkEmailUnique(reqVO.getUserId(), reqVO.getEmail());
    }
  }
}
