package com.micro.cloud.modules.system.user.service.impl;

import static com.micro.cloud.util.servlet.ServletUtils.getClientIP;

import cn.hutool.core.date.DateUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.cloud.enums.SysCommonStatusEnum;
import com.micro.cloud.enums.UserTypeEnum;
import com.micro.cloud.modules.system.app.dataobject.SysApplication;
import com.micro.cloud.modules.system.app.mapper.SysApplicationMapper;
import com.micro.cloud.modules.system.common.service.SysCaptchaService;
import com.micro.cloud.modules.system.common.service.SysSmsCodeService;
import com.micro.cloud.modules.system.org.dataobject.SysOrg;
import com.micro.cloud.modules.system.org.mapper.SysOrgMapper;
import com.micro.cloud.modules.system.org.vo.SysOrgBaseVO;
import com.micro.cloud.modules.system.role.dataobject.SysRole;
import com.micro.cloud.modules.system.role.mapper.SysRoleMapper;
import com.micro.cloud.modules.system.role.service.SysRoleService;
import com.micro.cloud.modules.system.role.vo.RoleBindUsersReqVO;
import com.micro.cloud.modules.system.user.convert.SysUserAccountConvert;
import com.micro.cloud.modules.system.user.convert.SysUserConvert;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.modules.system.user.dataobject.SysUserOrgRef;
import com.micro.cloud.modules.system.user.mapper.SysUserMapper;
import com.micro.cloud.modules.system.user.mapper.SysUserOrgRefMapper;
import com.micro.cloud.modules.system.user.repository.ISysUserRepository;
import com.micro.cloud.modules.system.user.service.OrgUserService;
import com.micro.cloud.modules.system.user.service.SysUserAccountService;
import com.micro.cloud.modules.system.user.validate.UserCommonOperateValidate;
import com.micro.cloud.modules.system.user.vo.SysUserAccountCreateReqVO;
import com.micro.cloud.modules.system.user.vo.SysUserPageReqVO;
import com.micro.cloud.modules.system.user.vo.SysUserUpdateReqVO;
import com.micro.cloud.modules.system.user.vo.org.OrgUserCreateReqVO;
import com.micro.cloud.modules.system.user.vo.org.OrgUserInfoVO;
import com.micro.cloud.modules.system.user.vo.org.OrgUserPageRepVO;
import com.micro.cloud.modules.system.user.vo.org.OrgUserRegisterReqVO;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import com.micro.cloud.snowflake.sequence.SequenceService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Service("orgUserServiceImpl")
public class OrgUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements OrgUserService {

  private final Logger logger = LoggerFactory.getLogger(OrgUserServiceImpl.class);

  @Autowired private SysUserAccountService accountService;

  @Autowired private ISysUserRepository userRepository;

  @Autowired private SysUserMapper userMapper;

  @Autowired private SysOrgMapper orgMapper;

  @Autowired private SysUserOrgRefMapper orgRefMapper;

  @Autowired private SysRoleMapper roleMapper;

  @Autowired private UserCommonOperateValidate operateValidate;

  @Autowired private SysRoleService roleService;

  @Autowired private SysCaptchaService captchaService;

  @Autowired private SequenceService sequenceService;

  @Autowired private SysSmsCodeService smsCodeService;

  @Autowired private SysApplicationMapper applicationMapper;

  /**
   * 个人用户分页查询
   *
   * @param vo 分页请求参数
   * @param userId 当前用户id
   * @return 分页记录
   */
  @Override
  public List<OrgUserInfoVO> page(SysUserPageReqVO vo, String userId) {
    List<OrgUserInfoVO> orgUserInfos = userRepository.orgUserPage(vo, userId);
    if (CollectionUtils.isNotEmpty(orgUserInfos)) {
      List<String> userIds =
          orgUserInfos.stream()
              .map(OrgUserInfoVO::getUserId)
              .filter(Objects::nonNull)
              .distinct()
              .collect(Collectors.toList());
      Map<String, OrgUserPageRepVO> orgMap = orgMapper.getOrgByUserIds(userIds);
      orgUserInfos.stream()
          .forEach(
              orgUser -> {
                orgUser.setTitle(
                    Objects.nonNull(orgMap.get(orgUser.getUserId()))
                        ? orgMap.get(orgUser.getUserId()).getTitle()
                        : null);
                orgUser.setOrgType(
                    Objects.nonNull(orgMap.get(orgUser.getUserId()))
                        ? orgMap.get(orgUser.getUserId()).getOrgType()
                        : null);
                orgUser.setCreditCode(
                    Objects.nonNull(orgMap.get(orgUser.getUserId()))
                        ? orgMap.get(orgUser.getUserId()).getCreditCode()
                        : null);
              });
    }
    return orgUserInfos;
  }

  /**
   * 根据用户id获取用户详情
   *
   * @param id 用户id
   * @return 用户详情
   */
  @Override
  public OrgUserInfoVO info(String id, String orgId) {
    // 查看前校验
    // operateValidate.checkUserExists(id);
    SysUser sysUser = userMapper.selectById(id);
    OrgUserInfoVO result = SysUserConvert.INSTANCE.convertOrgVO(sysUser);
    Map<String, OrgUserPageRepVO> orgMap = orgMapper.getOrgByUserIds(Collections.singletonList(id));
    if (Objects.nonNull(orgMap)) {
      result.setTitle(Objects.nonNull(orgMap.get(id)) ? orgMap.get(id).getTitle() : null);
      result.setCreditCode(Objects.nonNull(orgMap.get(id)) ? orgMap.get(id).getCreditCode() : null);
      result.setOrgType(Objects.nonNull(orgMap.get(id)) ? orgMap.get(id).getOrgType() : null);
    }
    return result;
  }

  /**
   * 创建外部机构用户
   *
   * @param reqVO 新用户信息
   * @return 添加记录行数
   */
  @Override
  public String create(OrgUserCreateReqVO reqVO, String creatorId, String orgId) {
    // 校验用户名唯一性
    operateValidate.checkUsernameUnique(reqVO.getUsername());
    // 校验正确性
    operateValidate.checkCreateOrUpdate(
        null, reqVO.getUsername(), reqVO.getEmail(), reqVO.getMobile(), null);
    // 新建机构信息
    SysOrg sysOrg = new SysOrg();
    sysOrg.setSysOrgId(String.valueOf(sequenceService.nextValue(null)));
    sysOrg.setOrgName(reqVO.getOrgBaseVO().getTitle());
    sysOrg.setOrgCreditCode(reqVO.getOrgBaseVO().getCreditCode());
    sysOrg.setType(reqVO.getOrgBaseVO().getOrgType());
    sysOrg.setCreatorId(creatorId);
    sysOrg.setCreateTime(DateUtil.date());
    sysOrg.setCreatorDepartId(orgId);
    // 是否为叶子结点
    orgMapper.insert(sysOrg);
    // 新增用户信息
    SysUser user = SysUserConvert.INSTANCE.convertDO(reqVO);
    String userId = String.valueOf(sequenceService.nextValue(null));
    user.setSysUserId(userId);
    user.setUserName(reqVO.getUsername());
    user.setPhone(reqVO.getMobile());
    // 设置创建者相关信息
    user.setCreatorId(creatorId);
    user.setCreateTime(DateUtil.date());
    user.setCreatorDepartId(orgId);
    // 默认为机构用户
    user.setType(UserTypeEnum.ORGANIZATION.getValue());
    // 默认设置为子系统用户
    SysApplication sysApplication = applicationMapper.selectOne(new QueryWrapperX<>());
    user.setApplicationId(sysApplication.getSysApplicationId());
    super.save(user);
    // 维护用户与组织机构关联关系
    SysUserOrgRef sysUserOrgRef = new SysUserOrgRef();
    sysUserOrgRef.setSysUserOrgId(String.valueOf(sequenceService.nextValue(null)));
    sysUserOrgRef.setSysUserId(userId);
    sysUserOrgRef.setSysOrgId(sysOrg.getSysOrgId());
    sysUserOrgRef.setCreatorId(creatorId);
    sysUserOrgRef.setCreateTime(DateUtil.date());
    orgRefMapper.insert(sysUserOrgRef);
    // 创建用户账号
    SysUserAccountCreateReqVO accountCreateReqVO = SysUserAccountConvert.INSTANCE.convertVO(reqVO);
    accountService.createUserAccount(accountCreateReqVO, userId);
    // 设置用户默认角色
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
  public Boolean update(OrgUserInfoVO reqVO, String updaterId) {
    // 更新前校验
    SysUserUpdateReqVO updateReqVO = SysUserConvert.INSTANCE.convertUpdateVo(reqVO);
    operateValidate.checkForUpdateUserInfo(updateReqVO);
    // 对象数据转换
    SysUser sysUser = SysUserConvert.INSTANCE.convertDO(reqVO);
    sysUser.setUpdaterId(updaterId);
    sysUser.setUpdateTime(DateUtil.date());
    // 检测组织机构是否发生变化，如发生变化则进行更新
    SysOrg depart = orgRefMapper.getDepartByUserId(reqVO.getUserId());
    Optional.ofNullable(depart)
        .ifPresent(
            dep -> {
              dep.setOrgName(
                  StringUtils.isNotBlank(reqVO.getTitle()) ? reqVO.getTitle() : dep.getOrgName());
              dep.setOrgCreditCode(
                  StringUtils.isNotBlank(reqVO.getCreditCode())
                      ? reqVO.getCreditCode()
                      : dep.getOrgCreditCode());
              dep.setType(Objects.nonNull(reqVO.getOrgType()) ? reqVO.getOrgType() : dep.getType());
              orgMapper.updateById(dep);
            });

    return super.updateById(sysUser);
  }

  /**
   * 批量更新用户状态
   *
   * @param ids 用户id集合
   * @param status 状态 参照SysCommonStatusEnum
   */
  @Override
  public Boolean updateUserStatusBatch(List<String> ids, Boolean status) {
    // 更新状态
    userMapper.updateUserStatusBatch(ids, status);
    return true;
  }

  /**
   * 机构用户注册
   *
   * @param vo 注册信息
   * @return 是否成功
   */
  @Override
  public Boolean register(OrgUserRegisterReqVO vo) {
    // 校验外部机构是否已存在
    operateValidate.checkDeptExists(
        vo.getExternalOrgCreateVO().getTitle(), vo.getExternalOrgCreateVO().getCreditCode());
    // 校验图片验证码
    captchaService.verifyCaptcha(vo.getCaptchaCode());
    // todo 短信验证码校验
    // 短信码校验,场景目前固定为1， 表示注册验证
    smsCodeService.useSmsCode(vo.getMobile(), 1, vo.getMessageCode(), getClientIP());
    OrgUserCreateReqVO orgUserCreateReqVO = SysUserConvert.INSTANCE.convertCreatReqVO(vo);
    SysOrgBaseVO sysOrgBaseVO =
        SysUserConvert.INSTANCE.convertCreatReqVO(vo.getExternalOrgCreateVO());
    orgUserCreateReqVO.setOrgBaseVO(sysOrgBaseVO);
    // 用户状态默认为启用
    orgUserCreateReqVO.setStatus(SysCommonStatusEnum.ENABLE.getStatus());
    // 创建机构用户
    this.create(orgUserCreateReqVO, null, null);
    return true;
  }
}
