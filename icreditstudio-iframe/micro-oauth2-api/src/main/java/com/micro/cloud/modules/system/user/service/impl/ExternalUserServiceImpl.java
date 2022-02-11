package com.micro.cloud.modules.system.user.service.impl;

import static com.micro.cloud.util.servlet.ServletUtils.getClientIP;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.cloud.enums.SysCommonStatusEnum;
import com.micro.cloud.enums.UserTypeEnum;
import com.micro.cloud.modules.system.app.dataobject.SysApplication;
import com.micro.cloud.modules.system.app.mapper.SysApplicationMapper;
import com.micro.cloud.modules.system.common.service.SysCaptchaService;
import com.micro.cloud.modules.system.common.service.SysSmsCodeService;
import com.micro.cloud.modules.system.role.dataobject.SysRole;
import com.micro.cloud.modules.system.role.mapper.SysRoleMapper;
import com.micro.cloud.modules.system.role.service.SysRoleService;
import com.micro.cloud.modules.system.role.vo.RoleBindUsersReqVO;
import com.micro.cloud.modules.system.user.convert.SysUserAccountConvert;
import com.micro.cloud.modules.system.user.convert.SysUserConvert;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.modules.system.user.mapper.SysUserMapper;
import com.micro.cloud.modules.system.user.repository.ISysUserRepository;
import com.micro.cloud.modules.system.user.service.ExternalUserService;
import com.micro.cloud.modules.system.user.service.SysUserAccountService;
import com.micro.cloud.modules.system.user.validate.UserCommonOperateValidate;
import com.micro.cloud.modules.system.user.vo.SysUserAccountCreateReqVO;
import com.micro.cloud.modules.system.user.vo.SysUserPageReqVO;
import com.micro.cloud.modules.system.user.vo.SysUserUpdateReqVO;
import com.micro.cloud.modules.system.user.vo.external.ExternalUserCreateReqVO;
import com.micro.cloud.modules.system.user.vo.external.ExternalUserInfoVO;
import com.micro.cloud.modules.system.user.vo.external.ExternalUserRegisterReqVO;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import com.micro.cloud.snowflake.sequence.SequenceService;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 外部用户管理
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Service("externalUserServiceImpl")
public class ExternalUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements ExternalUserService {

  private final Logger LOGGER = LoggerFactory.getLogger(ExternalUserServiceImpl.class);

  @Autowired private SysUserAccountService accountService;

  @Autowired private SysCaptchaService captchaService;

  @Autowired private ISysUserRepository userRepository;

  @Autowired private SysUserMapper userMapper;

  @Autowired private SysRoleMapper roleMapper;

  @Autowired private SysRoleService roleService;

  @Autowired private UserCommonOperateValidate operateValidate;

  @Autowired private SysSmsCodeService smsCodeService;

  @Autowired private SequenceService sequenceService;

  @Autowired private SysApplicationMapper applicationMapper;

  /**
   * 个人用户注册服务
   *
   * @param vo 个人用户注册请求
   * @return 注册是否成功
   */
  @Override
  public Boolean register(ExternalUserRegisterReqVO vo) {
    // 校验用户名唯一性
    operateValidate.checkUsernameUnique(vo.getUsername());
    // 校验手机唯一性
    operateValidate.checkMobileUnique(null, vo.getMobile());
    // 图片验证码校验
    captchaService.verifyCaptcha(vo.getCaptchaCode());
    // 短信码校验,场景目前固定为1， 表示注册验证
    smsCodeService.useSmsCode(vo.getMobile(), 1, vo.getMessageCode(), getClientIP());
    // 创建个人用户
    ExternalUserCreateReqVO creatReqVO = SysUserConvert.INSTANCE.convertCreatReqVO(vo);
    // 用户类型设为个人用户
    creatReqVO.setType(UserTypeEnum.EXTERNAL.getValue());
    // 默认设为启用
    creatReqVO.setStatus(SysCommonStatusEnum.ENABLE.getStatus());
    this.create(creatReqVO, null, null);
    return true;
  }

  /**
   * 个人用户分页查询
   *
   * @param vo 分页请求参数
   * @param userId 当前用户id
   * @return 分页记录
   */
  @Override
  public List<ExternalUserInfoVO> page(SysUserPageReqVO vo, String userId) {
    return userRepository.externalUserPage(vo, userId);
  }

  /**
   * 根据用户id获取用户详情
   *
   * @param id 用户id
   * @return 用户详情
   */
  @Override
  public ExternalUserInfoVO info(String id, String orgId) {
    // 查看前校验
    // operateValidate.checkUserExists(id);

    SysUser sysUser = userMapper.selectById(id);
    return SysUserConvert.INSTANCE.convertVO(sysUser);
  }

  /**
   * 创建个人用户
   *
   * @param reqVO 新用户信息
   * @return 添加记录行数
   */
  @Override
  public String create(ExternalUserCreateReqVO reqVO, String creatorId, String orgId) {
    // 校验正确性
    operateValidate.checkCreateOrUpdate(
        null, reqVO.getUsername(), reqVO.getEmail(), reqVO.getMobile(), null);
    // 新增用户信息
    SysUser user = SysUserConvert.INSTANCE.convertDO(reqVO);
    String userId = String.valueOf(sequenceService.nextValue(null));
    user.setSysUserId(userId);
    // 设置创建者相关信息
    user.setCreatorId(creatorId);
    user.setCreatorDepartId(orgId);
    user.setType(UserTypeEnum.EXTERNAL.getValue());
    // 设置用户所属系统
    SysApplication sysApplication = applicationMapper.selectOne(new QueryWrapperX<>());
    user.setApplicationId(sysApplication.getSysApplicationId());
    super.save(user);
    // 创建用户账号
    SysUserAccountCreateReqVO accountCreateReqVO = SysUserAccountConvert.INSTANCE.convertVO(reqVO);
    accountService.createUserAccount(accountCreateReqVO, userId);
    // 个人用户默认设置个人用户角色
    SysRole sysRole = roleMapper.selectOne("role_name", "EX_USER_ROLE");
    RoleBindUsersReqVO bindUsersReqVO = new RoleBindUsersReqVO();
    bindUsersReqVO.setRoleId(sysRole.getSysRoleId());
    bindUsersReqVO.setUserIds(Collections.singletonList(userId));
    roleService.allocateUser(bindUsersReqVO);
    return userId;
  }

  /**
   * 更新用户信息
   *
   * @param reqVO 用户更新信息
   * @return 更新记录行数
   */
  @Override
  public Boolean updateUser(SysUserUpdateReqVO reqVO, String updaterId) {
    // 更新前校验
    operateValidate.checkForUpdateUserInfo(reqVO);
    // 对象数据转换
    SysUser sysUser = SysUserConvert.INSTANCE.convertDO(reqVO);
    sysUser.setUpdaterId(updaterId);
    sysUser.setUpdateTime(DateUtil.date());
    return super.updateById(sysUser);
  }

  /**
   * 批量更新用户状态
   *
   * @param ids 用户id集合
   * @param status 状态 参照SysCommonStatusEnum枚举类
   * @return
   */
  @Override
  public Boolean updateUserStatusBatch(List<String> ids, Boolean status) {
    // 更新状态
    userMapper.updateUserStatusBatch(ids, status);
    return true;
  }
}
