package com.jinninghui.datasphere.icreditstudio.modules.uaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Sets;
import com.jinninghui.datasphere.icreditstudio.common.enums.DeleteFlagEnum;
import com.jinninghui.datasphere.icreditstudio.modules.system.allinterface.service.AllInterfacesService;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.service.OrganizationService;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.service.param.OrganizationEntityConditionParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.entity.RoleEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.service.RoleService;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserAccountEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserOrgMapEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserRoleMapEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.UserAccountService;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.UserOrgMapService;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.UserRoleMapService;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.UserService;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.BusinessUserAccountService;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.param.UserAccountGetsForm;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.param.UserRoleResRequest;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result.RoleEntityResult;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result.UserAccountEntityResult;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result.UserEntityResult;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result.UserRoleResResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wuwenbin
 */
@Service
public class ResourceAccountServiceImpl implements BusinessUserAccountService {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserRoleMapService userRoleMapService;
    @Autowired
    private UserOrgMapService userOrgMapService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AllInterfacesService allInterfacesService;
    @Autowired
    private OrganizationService organizationService;


    @Override
    public BusinessResult<UserAccountEntityResult> get(UserAccountGetsForm userAccountGetsForm) {

        UserAccountEntity accountIdentifier = userAccountService.getOne(new QueryWrapper<UserAccountEntity>().eq("account_identifier",
                userAccountGetsForm.getIdentifier()));

        UserAccountEntityResult accountResult = null;
        if (accountIdentifier != null) {
            accountResult = BeanCopyUtils.copyProperties(accountIdentifier, UserAccountEntityResult.class);
        }
        return BusinessResult.success(accountResult);
    }

    @Override
    public BusinessResult<UserRoleResResult> getRoleResAndUpdateLoginTime(UserRoleResRequest userRoleResRequest) {

        UserRoleResResult userRoleResResult = new UserRoleResResult();
        List<UserRoleMapEntity> userRoleMapEntityList =
                userRoleMapService.list(new QueryWrapper<UserRoleMapEntity>().eq("user_id", userRoleResRequest.getUserId()));
        if (CollectionUtils.isNotEmpty(userRoleMapEntityList)) {
            List<String> roleIdList =
                    userRoleMapEntityList.stream().map(UserRoleMapEntity::getRoleId).collect(Collectors.toList());
            Collection<RoleEntity> roleEntities = roleService.listByIds(roleIdList);
            List<RoleEntityResult> roleEntityResults = BeanCopyUtils.copy(roleEntities, RoleEntityResult.class);
            userRoleResResult.setRoleInfoResults(roleEntityResults);
        }
        List<UserEntity> userEntities = userService.list(new QueryWrapper<UserEntity>().eq("id",
                userRoleResRequest.getUserId()));
        List<UserEntityResult> userInfoResults = BeanCopyUtils.copy(userEntities, UserEntityResult.class);
        userRoleResResult.setUserInfoResults(userInfoResults);

        return BusinessResult.success(userRoleResResult);
    }

    /**
     * 校验用户角色是否有效
     *
     * @param userId 用户id
     * @return true 校验通过
     */
    @Override
    public boolean isVerifyRoleEffective(String userId) {
//        RoleEntityQueryRequest request = new RoleEntityQueryRequest();
//        request.setUserId(userId);
//        request.setDeleteFlag(CommonConstant.DELETE_FLAG_N);
//        List<RoleEntity> roleEntities = allInterfacesService.getRoleInfoByUserId(request);
//        return roleEntities.size() > 0;
        return true;
    }

    /**
     * 校验用户部门是否有效
     * 校验规则
     * 1 用户不存在部门 ，不做部门有效校验
     * 2 用户存在部门信息，判断部门是否可用
     *
     * @param userId 用户id
     * @return true 校验通过
     */
    @Override
    public boolean isVerifyOrgEffective(String userId) {
        List<UserOrgMapEntity> userOrgMaps = getUserOrgMaps(Sets.newHashSet(userId));
        Set<String> orgIdsByUserId = findOrgIdsByUserId(Sets.newHashSet(userId), userOrgMaps);
        List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().ids(orgIdsByUserId).build());
        long count = organizations.parallelStream()
                .filter(Objects::nonNull)
                .filter(organization -> DeleteFlagEnum.N.getCode().equals(organization.getDeleteFlag()))
                .count();
        if (count > 0) {
            return true;
        }
        return false;
        /*int userOrgCount = userOrgMapService.count(new QueryWrapper<UserOrgMapEntity>().eq("user_id", userId));

         *//**
         * 校验规则
         *1 用户不存在部门 ，不做部门有效校验
         * 2 用户存在部门信息，判断部门是否可用
         *//*
        if (userOrgCount == 0) {
            return true;
        }
        OrgEntityQueryRequest request = new OrgEntityQueryRequest();
        request.setUserId(userId);
        request.setDeleteFlag(CommonConstant.DELETE_FLAG_N);
        List<OrganizationEntity> organizationEntityList = allInterfacesService.getOrgInfoByUserId(request);

        return organizationEntityList.size() > 0;*/
    }

    private Set<String> findOrgIdsByUserId(Set<String> userIds, List<UserOrgMapEntity> userOrgMaps) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(userOrgMaps)) {
            results = userOrgMaps.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(userOrgMapEntity -> userIds.contains(userOrgMapEntity.getUserId()))
                    .map(UserOrgMapEntity::getOrgId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    private List<UserOrgMapEntity> getUserOrgMaps(Set<String> userIds) {
        QueryWrapper<UserOrgMapEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(userIds)) {
            wrapper.in(UserOrgMapEntity.USER_ID, userIds);
        }
        return userOrgMapService.list(wrapper);
    }

    private List<OrganizationEntity> getOrganizations(OrganizationEntityConditionParam param) {
        QueryWrapper<OrganizationEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(OrganizationEntity.ID, param.getIds());
        }
        return organizationService.list(wrapper);
    }
}
