package com.micro.cloud.modules.system.user.service.impl;

import static com.micro.cloud.constant.SysErrorCodeConstants.ORG_NOT_ENABLE;

import cn.hutool.core.date.DateUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.cloud.api.CommonPage;
import com.micro.cloud.enums.SysCommonStatusEnum;
import com.micro.cloud.enums.UserTypeEnum;
import com.micro.cloud.exception.ApiException;
import com.micro.cloud.modules.system.app.dataobject.SysApplication;
import com.micro.cloud.modules.system.app.mapper.SysApplicationMapper;
import com.micro.cloud.modules.system.org.dataobject.SysOrg;
import com.micro.cloud.modules.system.org.mapper.SysOrgMapper;
import com.micro.cloud.modules.system.org.service.SysOrgService;
import com.micro.cloud.modules.system.user.convert.SysUserAccountConvert;
import com.micro.cloud.modules.system.user.convert.SysUserConvert;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.modules.system.user.dataobject.SysUserOrgRef;
import com.micro.cloud.modules.system.user.dataobject.SysUserRoleRef;
import com.micro.cloud.modules.system.user.dto.SysUserRoleRefDto;
import com.micro.cloud.modules.system.user.mapper.SysUserMapper;
import com.micro.cloud.modules.system.user.mapper.SysUserOrgRefMapper;
import com.micro.cloud.modules.system.user.mapper.SysUserRoleRefMapper;
import com.micro.cloud.modules.system.user.repository.ISysUserRepository;
import com.micro.cloud.modules.system.user.service.InternalUserService;
import com.micro.cloud.modules.system.user.service.SysUserAccountService;
import com.micro.cloud.modules.system.user.service.SysUserRoleRefService;
import com.micro.cloud.modules.system.user.validate.UserCommonOperateValidate;
import com.micro.cloud.modules.system.user.vo.SysUserAccountCreateReqVO;
import com.micro.cloud.modules.system.user.vo.SysUserPageReqVO;
import com.micro.cloud.modules.system.user.vo.SysUserUpdateReqVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserCreateReqVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserInfoVO;
import com.micro.cloud.modules.system.user.vo.org.OrgUserPageRepVO;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import com.micro.cloud.snowflake.sequence.SequenceService;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统用户管理
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Service("internalUserServiceImpl")
public class InternalUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements InternalUserService {

    private final Logger logger = LoggerFactory.getLogger(InternalUserServiceImpl.class);

    @Autowired
    private SysUserAccountService accountService;

    @Autowired
    private SysOrgService orgService;

    @Autowired
    private SysUserRoleRefService userRoleRefService;

    @Autowired
    private ISysUserRepository userRepository;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysOrgMapper orgMapper;

    @Autowired
    private SysUserRoleRefMapper userRoleRefMapper;

    @Autowired
    private SysUserOrgRefMapper userOrgRefMapper;

    @Autowired
    private UserCommonOperateValidate operateValidate;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private SysApplicationMapper applicationMapper;

    /**
     * 个人用户分页查询
     *
     * @param vo     分页请求参数
     * @param userId 当前用户id
     * @return 分页记录
     */
    @Override
    public CommonPage<InternalUserInfoVO> page(SysUserPageReqVO vo, String userId) {
        CommonPage<SysUser> page = userRepository.internalUserPage(vo, userId);
        CommonPage<InternalUserInfoVO> result = SysUserConvert.INSTANCE.covertPage(page);
        List<InternalUserInfoVO> interUserList = result.getList();
        // 获取内部用户组织机构信息
        if (CollectionUtils.isNotEmpty(interUserList)) {
            List<String> ids =
                    interUserList.stream()
                            .map(InternalUserInfoVO::getUserId)
                            .filter(Objects::nonNull)
                            .distinct()
                            .collect(Collectors.toList());
            // 获取用户组织机构信息
            Map<String, OrgUserPageRepVO> orgMap = orgMapper.getOrgByUserIds(ids);
            logger.info("######## orgMap:{}", orgMap);
            // 获取用户角色信息
            Map<String, SysUserRoleRefDto> roleMap = userRoleRefMapper.getRoleByUserId(ids, vo.getRoleId() != null ? vo.getRoleId() : null);
            interUserList.forEach(
                    internalUser -> {
                        if (ObjectUtils.isNotEmpty(orgMap)) {
                            internalUser.setTitle(
                                    Objects.nonNull(orgMap.get(internalUser.getUserId()))
                                            ? orgMap.get(internalUser.getUserId()).getTitle()
                                            : null);
                            internalUser.setOrgType(
                                    Objects.nonNull(orgMap.get(internalUser.getUserId()))
                                            ? orgMap.get(internalUser.getUserId()).getOrgType()
                                            : null);
                            internalUser.setOrgStatus(
                                    Objects.nonNull(orgMap.get(internalUser.getUserId()))
                                            ? orgMap.get(internalUser.getUserId()).getStatus()
                                            : null);
                        }
                        if (ObjectUtils.isNotEmpty(roleMap)) {
                            internalUser.setRole(
                                    Objects.nonNull(roleMap.get(internalUser.getUserId()))
                                            ? roleMap.get(internalUser.getUserId()).getRoleName()
                                            : null);
                            internalUser.setRoleId(
                                    Objects.nonNull(roleMap.get(internalUser.getUserId()))
                                            ? roleMap.get(internalUser.getUserId()).getRoleId()
                                            : null);

                        }
                    });
        }
        //去除空角色条件（前面根据角色条件查询已过滤，过滤空角色条件）
        if (StringUtils.isNotBlank(vo.getRoleId())) {
            interUserList = interUserList.stream()
                    .filter(i -> StringUtils.isNotBlank(i.getRoleId())).collect(Collectors.toList());
            result.setList(interUserList);
            return result;
        } else {
            result.setList(interUserList);
            return result;
        }
    }

    /**
     * 根据用户id获取用户详情
     *
     * @param id 用户id
     * @return 用户详情
     */
    @Override
    public InternalUserInfoVO info(String id, String orgId) {
        SysUser sysUser = userMapper.selectById(id);
        return generateMoreInfo(Collections.singletonList(id), sysUser);
    }

    private InternalUserInfoVO generateMoreInfo(List<String> ids, SysUser sysUser) {
        // 获取用户组织机构信息
        Map<String, OrgUserPageRepVO> orgMap = orgMapper.getOrgByUserIds(ids);
        // 获取用户角色信息
        Map<String, SysUserRoleRefDto> roleMap = userRoleRefMapper.getRoleByUserId(ids, null);
        InternalUserInfoVO internalUserInfoVO = SysUserConvert.INSTANCE.convertInternalVO(sysUser);
        if (ObjectUtils.isNotEmpty(orgMap)) {
            internalUserInfoVO.setOrgId(
                    Objects.nonNull(orgMap.get(internalUserInfoVO.getUserId()))
                            ? orgMap.get(internalUserInfoVO.getUserId()).getOrgId()
                            : null);
            internalUserInfoVO.setTitle(
                    Objects.nonNull(orgMap.get(internalUserInfoVO.getUserId()))
                            ? orgMap.get(internalUserInfoVO.getUserId()).getTitle()
                            : null);
            internalUserInfoVO.setOrgType(
                    Objects.nonNull(orgMap.get(internalUserInfoVO.getUserId()))
                            ? orgMap.get(internalUserInfoVO.getUserId()).getOrgType()
                            : null);
        }
        if (ObjectUtils.isNotEmpty(roleMap)) {
            internalUserInfoVO.setRole(
                    Objects.nonNull(roleMap.get(internalUserInfoVO.getUserId()))
                            ? roleMap.get(internalUserInfoVO.getUserId()).getRoleName()
                            : null);
            internalUserInfoVO.setRoleId(
                    Objects.nonNull(roleMap.get(internalUserInfoVO.getUserId()))
                            ? roleMap.get(internalUserInfoVO.getUserId()).getRoleId()
                            : null);
        }
        return internalUserInfoVO;
    }

    /**
     * 创建个人用户
     *
     * @param reqVO 新用户信息
     * @return 添加记录行数
     */
    @Override
    public String create(InternalUserCreateReqVO reqVO, String creatorId, String orgId) {
        // 校验用户名唯一性
        operateValidate.checkUsernameUnique(reqVO.getUsername());
        // 校验正确性
        operateValidate.checkCreateOrUpdate(
                null, reqVO.getUsername(), reqVO.getEmail(), reqVO.getMobile(), null);
        // 新增用户信息
        SysUser user = SysUserConvert.INSTANCE.convertDO(reqVO);
        String userId = String.valueOf(sequenceService.nextValue(null));
        user.setSysUserId(userId);
        // 设置创建者相关信息
        user.setCreatorId(creatorId);
        user.setCreateTime(DateUtil.date());
        user.setCreatorDepartId(orgId);
        user.setType(UserTypeEnum.INTERNAL.getValue());
        SysApplication sysApplication = applicationMapper.selectOne(new QueryWrapperX<>());
        user.setApplicationId(sysApplication.getSysApplicationId());
        super.save(user);
        // 维护用户与组织机构关联关系
        SysUserOrgRef sysUserOrgRef = new SysUserOrgRef();
        sysUserOrgRef.setSysUserOrgId(String.valueOf(sequenceService.nextValue(null)));
        sysUserOrgRef.setSysUserId(userId);
        sysUserOrgRef.setSysOrgId(reqVO.getOrgId());
        sysUserOrgRef.setCreatorId(creatorId);
        sysUserOrgRef.setCreateTime(DateUtil.date());
        userOrgRefMapper.insert(sysUserOrgRef);
        // 维护用户与所选角色关联关系
        SysUserRoleRef sysUserRoleRef = new SysUserRoleRef();
        sysUserRoleRef.setSysUserRoleRefId(sequenceService.nextStringValue(null));
        sysUserRoleRef.setSysUserId(userId);
        sysUserRoleRef.setSysRoleId(reqVO.getRoleId());
        sysUserRoleRef.setCreateTime(DateUtil.date());
        userRoleRefMapper.insert(sysUserRoleRef);
        // 创建用户账号
        SysUserAccountCreateReqVO accountCreateReqVO = SysUserAccountConvert.INSTANCE.convertVO(reqVO);
        accountService.createUserAccount(accountCreateReqVO, userId);
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
        super.updateById(sysUser);
        // 用户部门信息是否发生变化
        SysUserOrgRef orgRef = userOrgRefMapper.selectOne("sys_user_id", reqVO.getUserId());
        Optional.ofNullable(orgRef)
                .ifPresent(
                        ref -> {
                            if (!ref.getSysOrgId().equals(reqVO.getOrgId())) {
                                // 变更人员-部门机构关联关系
                                SysUserOrgRef newRef = new SysUserOrgRef();
                                newRef.setSysUserOrgId(ref.getSysUserOrgId());
                                newRef.setSysOrgId(reqVO.getOrgId());
                                newRef.setUpdateTime(DateUtil.date());
                                userOrgRefMapper.updateById(newRef);
                            }
                        });

        // 用户角色是否发生变化
        SysUserRoleRef roleRef = userRoleRefMapper.selectOne("sys_user_id", reqVO.getUserId());
        logger.info("######## roleRef:{}", roleRef);
        Optional.ofNullable(roleRef)
                .ifPresent(
                        ref -> {
                            logger.info("######## 角色关系维护 #########");
                            if (!ref.getSysRoleId().equals(reqVO.getRoleId())) {
                                // 变更人员-部门机构关联关系
                                SysUserRoleRef newRef = new SysUserRoleRef();
                                newRef.setSysUserRoleRefId(roleRef.getSysUserRoleRefId());
                                newRef.setSysRoleId(reqVO.getRoleId());
                                newRef.setUpdateTime(DateUtil.date());
                                userRoleRefMapper.updateById(newRef);
                            }
                        });
        // 用户角色关系不存在则维护新的关联关系
        if (Objects.isNull(roleRef)) {
            SysUserRoleRef sysUserRoleRef = new SysUserRoleRef();
            sysUserRoleRef.setSysUserRoleRefId(sequenceService.nextStringValue(null));
            sysUserRoleRef.setSysUserId(reqVO.getUserId());
            sysUserRoleRef.setSysRoleId(reqVO.getRoleId());
            userRoleRefMapper.insert(sysUserRoleRef);
        }
        return true;
    }

    /**
     * 批量更新用户状态
     *
     * @param ids    用户id集合
     * @param status 状态 参照SysCommonStatusEnum枚举类
     * @return
     */
    @Override
    public Boolean updateUserStatusBatch(List<String> ids, Boolean status) {
        // 获取用户所在部门
        List<SysOrg> userOrgs = userOrgRefMapper.getDepartByUserIds(ids);
        // 校验用户所在部门是否启用
        userOrgs.stream()
                .filter(Objects::nonNull)
                .forEach(
                        org -> {
                            if (SysCommonStatusEnum.DISABLE.getStatus().equals(org.getStatus())) {
                                throw new ApiException(ORG_NOT_ENABLE);
                            }
                        });
        // 更新状态
        userMapper.updateUserStatusBatch(ids, status);
        return true;
    }
}
