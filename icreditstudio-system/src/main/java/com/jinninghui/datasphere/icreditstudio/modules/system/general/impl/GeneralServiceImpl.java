package com.jinninghui.datasphere.icreditstudio.modules.system.general.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jinninghui.datasphere.icreditstudio.common.enums.DeleteFlagEnum;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.entity.CodeInfoEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.service.CodeInfoService;
import com.jinninghui.datasphere.icreditstudio.modules.system.general.GeneralService;
import com.jinninghui.datasphere.icreditstudio.modules.system.general.param.GeneralOrgParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.general.result.UserLoginInfo;
import com.jinninghui.datasphere.icreditstudio.modules.system.log.entity.AuditLogEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.log.service.AuditLogService;
import com.jinninghui.datasphere.icreditstudio.modules.system.log.service.param.AuditLogEntitySaveParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.service.OrganizationService;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.service.param.OrganizationEntityConditionParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.entity.ResourcesEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.service.ResourcesService;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.service.param.ResourcesEntityConditionParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.entity.RoleEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.entity.RoleResourcesMapEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.service.RoleResourcesMapService;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.service.RoleService;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.service.param.RoleEntityConditionParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserAccountEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserOrgMapEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserRoleMapEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.UserAccountService;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.UserOrgMapService;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.UserRoleMapService;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.UserService;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.param.UserAccountEntityConditionParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.param.UserEntityConditionParam;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.SessionService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by PPai on 2021/6/9 17:41
 */
@Component
public class GeneralServiceImpl implements GeneralService {
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserOrgMapService userOrgMapService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleMapService userRoleMapService;
    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private RoleResourcesMapService roleResourcesMapService;
    @Autowired
    private CodeInfoService codeInfoService;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserAccountService userAccountService;

    @Override
    public OrganizationEntity getOrganizationById(String id) {
        OrganizationEntity entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = organizationService.getById(id);
        }
        return entity;
    }

    @Override
    public List<OrganizationEntity> getOrganizationByCode(String code) {
        List<OrganizationEntity> entity = Lists.newArrayList();
        if (StringUtils.isNotBlank(code)) {
            entity = getOrganizations(OrganizationEntityConditionParam.builder().orgCode(code).build());
        }
        return entity;
    }

    @Override
    public List<OrganizationEntity> getOrganizationLikeName(String name) {
        List<OrganizationEntity> results = Lists.newArrayList();
        if (StringUtils.isNotBlank(name)) {
            results = getOrganizations(OrganizationEntityConditionParam.builder().orgName(name).build());
        }
        return results;
    }

    @Override
    public List<OrganizationEntity> getDirectSubOrg(GeneralOrgParam param) {
        List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().build());
        List<OrganizationEntity> conditionOrgs = findOrganizations(param.getOrgCode(), param.getId(), organizations);
        //查询的部门id
        Set<String> orgIds = findOrgIds(conditionOrgs);
        return findDirectSonOrgs(orgIds, organizations);
    }

    @Override
    public List<OrganizationEntity> getCascadeSubOrg(GeneralOrgParam param) {
        List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().build());
        List<OrganizationEntity> conditionOrgs = findOrganizations(param.getOrgCode(), param.getId(), organizations);
        //查询的部门id
        Set<String> orgIds = findOrgIds(conditionOrgs);
        Set<String> sonOrgIds = loopFindSonOrgIds(orgIds, organizations);
        return findOrgsByOrgIds(sonOrgIds, organizations);
    }

    @Override
    public List<OrganizationEntity> getDirectSubDisableOrg(GeneralOrgParam param) {
        List<OrganizationEntity> directSubOrg = getDirectSubOrg(param);
        return directSubOrg.parallelStream()
                .filter(Objects::nonNull)
                .filter(organizationEntity -> DeleteFlagEnum.Y.getCode().equals(organizationEntity.getDeleteFlag()))
                .collect(Collectors.toList());
    }

    @Override
    public UserEntity getUserById(String id) {
        UserEntity entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = userService.getById(id);
        }
        return entity;
    }

    @Override
    public List<OrganizationEntity> getOrgInfoByUserId(String userId) {
        List<UserOrgMapEntity> userOrgMaps = getUserOrgMaps(Sets.newHashSet(userId), Sets.newHashSet());
        List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().build());
        Set<String> orgIdsByUserIds = findOrgIdsByUserIds(Sets.newHashSet(userId), userOrgMaps);
        return findOrgsByIds(orgIdsByUserIds, organizations);
    }

    @Override
    public List<UserEntity> getUsersByOrgParam(GeneralOrgParam param) {
        List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().build());
        List<OrganizationEntity> condition = findOrganizations(param.getOrgCode(), param.getId(), organizations);
        Set<String> orgIds = findOrgIds(condition);
        List<UserOrgMapEntity> userOrgMaps = getUserOrgMaps(Sets.newHashSet(), Sets.newHashSet());
        Set<String> userIdsByOrgIds = findUserIdsByOrgIds(orgIds, userOrgMaps);
        List<UserEntity> users = getUsers(UserEntityConditionParam.builder().build());
        return findUsersByIds(userIdsByOrgIds, users);
    }

    @Override
    public List<UserEntity> getCascadeSubOrgUsersByOrgParam(GeneralOrgParam param) {
        List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().build());
        List<OrganizationEntity> condition = findOrganizations(param.getOrgCode(), param.getId(), organizations);
        Set<String> orgIds = findOrgIds(condition);
        Set<String> sonOrgIds = loopFindSonOrgIds(orgIds, organizations);
        Set<String> currAndSonOrgIds = Sets.newHashSet(orgIds);
        currAndSonOrgIds.addAll(sonOrgIds);
        List<UserOrgMapEntity> userOrgMaps = getUserOrgMaps(Sets.newHashSet(), Sets.newHashSet());
        Set<String> userIdsByOrgIds = findUserIdsByOrgIds(currAndSonOrgIds, userOrgMaps);
        List<UserEntity> users = getUsers(UserEntityConditionParam.builder().build());
        return findUsersByIds(userIdsByOrgIds, users);
    }

    @Override
    public List<RoleEntity> getRolesByUserId(String userId) {
        List<UserRoleMapEntity> userRoleMaps = getUserRoleMaps(Sets.newHashSet(userId), Sets.newHashSet());
        Set<String> roleIdsByUserIds = findRoleIdsByUserIds(Sets.newHashSet(userId), userRoleMaps);
        return getRoles(RoleEntityConditionParam.builder().ids(roleIdsByUserIds).build());
    }

    @Override
    public List<ResourcesEntity> getResourcesByUserId(String userId) {
        List<UserRoleMapEntity> userRoleMaps = getUserRoleMaps(Sets.newHashSet(userId), Sets.newHashSet());
        Set<String> roleIdsByUserIds = findRoleIdsByUserIds(Sets.newHashSet(userId), userRoleMaps);
        List<RoleResourcesMapEntity> roleResourcesMaps = getRoleResourcesMaps(roleIdsByUserIds);
        Set<String> resourcesIds = findResourcesIds(roleIdsByUserIds, roleResourcesMaps);
        return getResources(ResourcesEntityConditionParam.builder().ids(resourcesIds).build());
    }

    @Override
    public List<UserEntity> getDisabledUsersByOrgParam(GeneralOrgParam param) {
        List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().build());
        List<OrganizationEntity> condition = findOrganizations(param.getOrgCode(), param.getId(), organizations);
        Set<String> orgIds = findOrgIds(condition);
        List<UserOrgMapEntity> userOrgMaps = getUserOrgMaps(Sets.newHashSet(), Sets.newHashSet(orgIds));
        Set<String> userIdsByOrgIds = findUserIdsByOrgIds(orgIds, userOrgMaps);

        List<UserEntity> users = getUsers(UserEntityConditionParam.builder().build());
        List<UserEntity> usersByIds = findUsersByIds(userIdsByOrgIds, users);
        return usersByIds.parallelStream()
                .filter(Objects::nonNull)
                .filter(userEntity -> DeleteFlagEnum.Y.getCode().equals(userEntity.getDeleteFlag()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserEntity> getDirectSubOrgDisabledUsersByOrgParam(GeneralOrgParam param) {
        List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().build());
        List<OrganizationEntity> condition = findOrganizations(param.getOrgCode(), param.getId(), organizations);
        Set<String> orgIds = findOrgIds(condition);
        List<OrganizationEntity> directSonOrgs = findDirectSonOrgs(orgIds, organizations);
        Set<String> sonOrgIds = findOrgIds(directSonOrgs);

        List<UserOrgMapEntity> userOrgMaps = getUserOrgMaps(Sets.newHashSet(), Sets.newHashSet());
        Set<String> userIdsByOrgIds = findUserIdsByOrgIds(sonOrgIds, userOrgMaps);

        List<UserEntity> users = getUsers(UserEntityConditionParam.builder().build());
        return findUsersByIds(userIdsByOrgIds, users);
    }

    @Override
    public RoleEntity getRoleById(String id) {
        RoleEntity entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = roleService.getById(id);
        }
        return entity;
    }

    @Override
    public List<UserEntity> getUsersByRoleId(String roleId) {
        List<UserRoleMapEntity> userRoleMaps = getUserRoleMaps(Sets.newHashSet(), Sets.newHashSet(roleId));
        Set<String> userIdsByRoleIds = findUserIdsByRoleIds(Sets.newHashSet(roleId), userRoleMaps);

        List<UserEntity> users = getUsers(UserEntityConditionParam.builder().build());
        return findUsersByIds(userIdsByRoleIds, users);
    }

    @Override
    public List<ResourcesEntity> getCascadeSubResourcesByRoleId(String role) {
        List<RoleResourcesMapEntity> roleResourcesMaps = getRoleResourcesMaps(Sets.newHashSet(role));
//        Set<String> resourcesIds = findResourcesIds(Sets.newHashSet(role), roleResourcesMaps);
        Set<String> resourcesIdsByRoleIds = findResourcesIdsByRoleIds(Sets.newHashSet(role), roleResourcesMaps);
        List<ResourcesEntity> resources = getResources(ResourcesEntityConditionParam.builder().build());
        Set<String> sonResourcesIds = loopFindSonResourcesId(resourcesIdsByRoleIds, resources);

        return resources.parallelStream()
                .filter(Objects::nonNull)
                .filter(resourcesEntity -> sonResourcesIds.contains(resourcesEntity.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleEntity> getDisabledRolesByParentRoleId(String parentRoleId) {
        return getRoles(RoleEntityConditionParam.builder()
                .parentIds(Sets.newHashSet(parentRoleId))
                .deleteFlag(DeleteFlagEnum.Y.getCode()).build());
    }

    @Override
    public ResourcesEntity getResourcesById(String id) {
        ResourcesEntity entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = resourcesService.getById(id);
        }
        return entity;
    }

    @Override
    public List<RoleEntity> getRolesByResourcesId(String resourcesId) {
        List<RoleResourcesMapEntity> roleResourcesMaps = getRoleResourcesMaps(Sets.newHashSet());
        Set<String> roleIdsByResourcesIds = findRoleIdsByResourcesIds(Sets.newHashSet(resourcesId), roleResourcesMaps);

        List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder().build());
        return findRolesByIds(roleIdsByResourcesIds, roles);
    }

    @Override
    public List<ResourcesEntity> getDisabledResourcesByParentResourcesId(String parentId) {
        return getResources(ResourcesEntityConditionParam.builder()
                .parentIds(Sets.newHashSet(parentId))
                .deleteFlag(DeleteFlagEnum.Y.getCode()).build());
    }

    @Override
    public List<CodeInfoEntity> getCodeInfoByCodeName(String name) {
        List<CodeInfoEntity> results = Lists.newArrayList();
        if (StringUtils.isNotBlank(name)) {
            results = getCodeInfos(name);
        }
        return results;
    }

    @Override
    public AuditLogEntity saveAuditLog(AuditLogEntitySaveParam param) {
        return auditLogService.log(param);
    }

    @Override
    public UserLoginInfo getCurrUserLoginInfo() {
        UserLoginInfo info = new UserLoginInfo();
        String userId = sessionService.getUserId();
        String token = sessionService.getToken();
        List<UserAccountEntity> userAccounts = getUserAccounts(UserAccountEntityConditionParam.builder()
                .userIds(Sets.newHashSet(userId)).build());
        if (CollectionUtils.isNotEmpty(userAccounts)) {
            UserAccountEntity accountEntity = userAccounts.get(0);
            info.setAccountIdentifier(accountEntity.getAccountIdentifier());
        }
        info.setUserId(userId);
        info.setToken(token);
        return info;
    }

    private List<RoleEntity> findRolesByIds(Set<String> ids, List<RoleEntity> roles) {
        List<RoleEntity> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(ids) && CollectionUtils.isNotEmpty(roles)) {
            results = roles.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(roleEntity -> ids.contains(roleEntity.getId()))
                    .collect(Collectors.toList());
        }
        return results;
    }

    private Set<String> findRoleIdsByResourcesIds(Set<String> resourcesIds, List<RoleResourcesMapEntity> roleResourcesMaps) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(resourcesIds) && CollectionUtils.isNotEmpty(roleResourcesMaps)) {
            results = roleResourcesMaps.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(entity -> resourcesIds.contains(entity.getResourcesId()))
                    .map(RoleResourcesMapEntity::getRoleId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    private Set<String> findResourcesIdsByRoleIds(Set<String> roleIds, List<RoleResourcesMapEntity> roleResourcesMaps) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(roleIds) && CollectionUtils.isNotEmpty(roleResourcesMaps)) {
            results = roleResourcesMaps.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(entity -> roleIds.contains(entity.getRoleId()))
                    .map(RoleResourcesMapEntity::getResourcesId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    private Set<String> loopFindSonResourcesId(Set<String> currResourcesId, List<ResourcesEntity> resources) {
        Set<String> result = Sets.newHashSet();
        Set<String> temp = currResourcesId;
        while (CollectionUtils.isNotEmpty(temp)) {
            Set<String> tempChildResourcesIds = Sets.newHashSet();
            for (String resourcesId : temp) {
                Set<String> childResourcesIds = Optional.ofNullable(resources).orElse(Lists.newArrayList())
                        .parallelStream()
                        .filter(resourcesEntity -> resourcesId.equals(resourcesEntity.getParentId()))
                        .map(ResourcesEntity::getId)
                        .collect(Collectors.toSet());
                result.addAll(childResourcesIds);
                tempChildResourcesIds.addAll(childResourcesIds);
            }
            temp = tempChildResourcesIds;
        }
        return result;
    }

    private Set<String> findUserIdsByRoleIds(Set<String> roleIds, List<UserRoleMapEntity> userRoleMaps) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(roleIds) && CollectionUtils.isNotEmpty(userRoleMaps)) {
            results = userRoleMaps.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(userRoleMapEntity -> roleIds.contains(userRoleMapEntity.getRoleId()))
                    .map(UserRoleMapEntity::getUserId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    private Set<String> findResourcesIds(Set<String> roleIds, List<RoleResourcesMapEntity> roleResourcesMaps) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(roleIds) && CollectionUtils.isNotEmpty(roleResourcesMaps)) {
            results = roleResourcesMaps.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(s -> roleIds.contains(s.getRoleId()))
                    .map(RoleResourcesMapEntity::getResourcesId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    private Set<String> findRoleIdsByUserIds(Set<String> userIds, List<UserRoleMapEntity> userRoleMaps) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(userIds) && CollectionUtils.isNotEmpty(userRoleMaps)) {
            results = userRoleMaps.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(userRoleMapEntity -> userIds.contains(userRoleMapEntity.getUserId()))
                    .map(UserRoleMapEntity::getRoleId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    private List<UserEntity> findUsersByIds(Set<String> ids, List<UserEntity> users) {
        List<UserEntity> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(ids) && CollectionUtils.isNotEmpty(users)) {
            results = users.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(userEntity -> ids.contains(userEntity.getId()))
                    .collect(Collectors.toList());
        }
        return results;
    }

    private Set<String> findOrgIdsByUserIds(Set<String> userIds, List<UserOrgMapEntity> userOrgMaps) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(userIds) && CollectionUtils.isNotEmpty(userOrgMaps)) {
            results = userOrgMaps.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(userOrgMapEntity -> userIds.contains(userOrgMapEntity.getUserId()))
                    .map(UserOrgMapEntity::getOrgId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    private Set<String> findUserIdsByOrgIds(Set<String> orgIds, List<UserOrgMapEntity> userOrgMaps) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(orgIds) && CollectionUtils.isNotEmpty(userOrgMaps)) {
            results = userOrgMaps.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(userOrgMapEntity -> orgIds.contains(userOrgMapEntity.getOrgId()))
                    .map(UserOrgMapEntity::getUserId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    private List<OrganizationEntity> findOrgsByIds(Set<String> orgIds, List<OrganizationEntity> organizations) {
        List<OrganizationEntity> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(orgIds) && CollectionUtils.isNotEmpty(organizations)) {
            results = organizations.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(organizationEntity -> orgIds.contains(organizationEntity.getId()))
                    .collect(Collectors.toList());
        }
        return results;
    }

    private List<OrganizationEntity> findDirectSonOrgs(Set<String> currOrgs, List<OrganizationEntity> organizations) {
        List<OrganizationEntity> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(currOrgs) && CollectionUtils.isNotEmpty(organizations)) {
            results = organizations.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(organizationEntity -> currOrgs.contains(organizationEntity.getParentId()))
                    .collect(Collectors.toList());
        }
        return results;
    }

    private Set<String> loopFindSonOrgIds(Set<String> currOrgIds, List<OrganizationEntity> orgs) {
        Set<String> result = Sets.newHashSet();
        Set<String> temp = currOrgIds;
        while (CollectionUtils.isNotEmpty(temp)) {
            Set<String> tempChildOrgIds = Sets.newHashSet();
            for (String orgId : temp) {
                Set<String> childOrgIds = Optional.ofNullable(orgs).orElse(Lists.newArrayList())
                        .parallelStream()
                        .filter(org -> orgId.equals(org.getParentId()))
                        .map(OrganizationEntity::getId)
                        .collect(Collectors.toSet());
                result.addAll(childOrgIds);
                tempChildOrgIds.addAll(childOrgIds);
            }
            temp = tempChildOrgIds;
        }
        return result;
    }

    private List<OrganizationEntity> findOrgsByOrgIds(Set<String> orgIds, List<OrganizationEntity> organizations) {
        List<OrganizationEntity> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(orgIds) && CollectionUtils.isNotEmpty(organizations)) {
            results = organizations.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(organizationEntity -> orgIds.contains(organizationEntity.getId()))
                    .collect(Collectors.toList());
        }
        return results;
    }

    private Set<String> findOrgIds(List<OrganizationEntity> organizations) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(organizations)) {
            results = organizations.parallelStream()
                    .filter(Objects::nonNull)
                    .map(OrganizationEntity::getId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    private List<UserOrgMapEntity> getUserOrgMaps(Set<String> userIds, Set<String> orgIds) {
        QueryWrapper<UserOrgMapEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(userIds)) {
            wrapper.in(UserOrgMapEntity.USER_ID, userIds);
        }
        if (CollectionUtils.isNotEmpty(orgIds)) {
            wrapper.in(UserOrgMapEntity.ORG_ID, orgIds);
        }
        return userOrgMapService.list(wrapper);
    }

    private List<OrganizationEntity> findOrganizations(String code, String id, List<OrganizationEntity> organizations) {
        List<OrganizationEntity> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(organizations)) {
            results = organizations.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(organizationEntity -> {
                        boolean flag = false;
                        if (StringUtils.isNotBlank(code)) {
                            flag = code.equals(organizationEntity.getOrgCode());
                        }
                        if (StringUtils.isNotBlank(id)) {
                            flag = id.equals(organizationEntity.getId());
                        }
                        return flag;
                    }).collect(Collectors.toList());
        }
        return results;
    }

    private List<OrganizationEntity> getAccurateOrganization(String code, String id) {
        QueryWrapper<OrganizationEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(code)) {
            wrapper.eq(OrganizationEntity.ORG_CODE, code);
        }
        if (StringUtils.isNotBlank(id)) {
            wrapper.eq(OrganizationEntity.ID, id);
        }
        return organizationService.list(wrapper);
    }


    private List<OrganizationEntity> getOrganizations(OrganizationEntityConditionParam param) {
        QueryWrapper<OrganizationEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getDeleteFlag())) {
            wrapper.eq(OrganizationEntity.DELETE_FLAG, param.getDeleteFlag());
        }
        if (StringUtils.isNotBlank(param.getOrgCode())) {
            wrapper.like(OrganizationEntity.ORG_CODE, param.getOrgCode());
        }
        if (StringUtils.isNotBlank(param.getOrgName())) {
            wrapper.like(OrganizationEntity.ORG_NAME, param.getOrgName());
        }
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(OrganizationEntity.ID, param.getIds());
        }
        return organizationService.list(wrapper);
    }

    private List<UserEntity> getUsers(UserEntityConditionParam param) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(UserEntity.ID, param.getIds());
        }
        return userService.list(wrapper);
    }

    private List<RoleEntity> getRoles(RoleEntityConditionParam param) {
        QueryWrapper<RoleEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(RoleEntity.ID, param.getIds());
        }
        if (CollectionUtils.isNotEmpty(param.getParentIds())) {
            wrapper.in(RoleEntity.PARENT_ID, param.getParentIds());
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(param.getDeleteFlag())) {
            wrapper.eq(RoleEntity.DELETE_FLAG, param.getDeleteFlag());
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(param.getRoleName())) {
            wrapper.eq(RoleEntity.ROLE_NAME, param.getRoleName());
        }
        return roleService.list(wrapper);
    }

    private List<UserRoleMapEntity> getUserRoleMaps(Set<String> userIds, Set<String> roleIds) {
        QueryWrapper<UserRoleMapEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(userIds)) {
            wrapper.in(UserRoleMapEntity.USER_ID, userIds);
        }
        if (CollectionUtils.isNotEmpty(roleIds)) {
            wrapper.in(UserRoleMapEntity.ROLE_ID, roleIds);
        }
        return userRoleMapService.list(wrapper);
    }

    private List<ResourcesEntity> getResources(ResourcesEntityConditionParam param) {
        QueryWrapper<ResourcesEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getParentIds())) {
            wrapper.in(ResourcesEntity.PARENT_ID, param.getParentIds());
        }
        if (StringUtils.isNotBlank(param.getName())) {
            wrapper.like(ResourcesEntity.NAME, param.getName());
        }
        if (StringUtils.isNotBlank(param.getDeleteFlag())) {
            wrapper.eq(ResourcesEntity.DELETE_FLAG, param.getDeleteFlag());
        }
        return resourcesService.list(wrapper);
    }

    private List<RoleResourcesMapEntity> getRoleResourcesMaps(Set<String> roleIds) {
        QueryWrapper<RoleResourcesMapEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(roleIds)) {
            wrapper.in(RoleResourcesMapEntity.ROLE_ID, roleIds);
        }
        return roleResourcesMapService.list(wrapper);
    }

    private List<CodeInfoEntity> getCodeInfos(String name) {
        List<CodeInfoEntity> results = Lists.newArrayList();
        if (StringUtils.isNotBlank(name)) {
            QueryWrapper<CodeInfoEntity> wrapper = new QueryWrapper<>();
            wrapper.like(CodeInfoEntity.CODE_NAME, name);
            results = codeInfoService.list(wrapper);
        }
        return results;
    }

    private List<UserAccountEntity> getUserAccounts(UserAccountEntityConditionParam param) {
        QueryWrapper<UserAccountEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getUserIds())) {
            wrapper.in(UserAccountEntity.USER_ID, param.getUserIds());
        }
        return userAccountService.list(wrapper);
    }
}

