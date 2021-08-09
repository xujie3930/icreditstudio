package com.jinninghui.datasphere.icreditstudio.modules.system.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hashtech.businessframework.exception.interval.AppException;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.Query;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.utils.Base64Utils;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import com.jinninghui.datasphere.icreditstudio.common.enums.DeleteFlagEnum;
import com.jinninghui.datasphere.icreditstudio.common.enums.NumberEnum;
import com.jinninghui.datasphere.icreditstudio.modules.system.allinterface.param.UserRoleDataQueryParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.allinterface.result.SelectInfoResult;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.service.OrganizationService;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.entity.ResourcesEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.service.ResourcesService;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.service.result.ResourcesEntityResult;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.entity.RoleEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.entity.RoleResourcesMapEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.mapper.RoleDao;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.service.RoleResourcesMapService;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.service.RoleService;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.service.param.*;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.service.result.RoleEntityInfoResult;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.web.request.RoleEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.web.request.RoleEntityQueryParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.web.request.RoleEntityStatusParams;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.web.request.RoleUserQueryParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserOrgMapEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserRoleMapEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.UserOrgMapService;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.UserRoleMapService;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.web.result.UserEntityInfoResult;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result.RoleEntityResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserRoleMapService userRoleMapService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private RoleResourcesMapService roleResourcesMapService;
    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private UserOrgMapService userOrgMapService;

    @Override
    public BusinessPageResult queryPage(RoleEntityPageRequest pageRequest) {

        IPage<RoleEntity> page = this.page(
                new Query<RoleEntity>().getPage(pageRequest),
                new QueryWrapper<RoleEntity>().like(StringUtils.isNotEmpty(pageRequest.getRoleName()), "ROLE_NAME", pageRequest.getRoleName()).eq(StringUtils.isNotEmpty(pageRequest.getDeleteFlag()), "DELETE_FLAG", pageRequest.getDeleteFlag())
        );

        return BusinessPageResult.build(page, pageRequest);
    }

    @Override
    public List<RoleEntityInfoResult> getRoleInfoByUserId(RoleEntityQueryParam param) {
        List<RoleEntity> roleInfoByUserId = roleDao.getRoleInfoByUserId(param);
        return Optional.ofNullable(roleInfoByUserId).orElse(Lists.newArrayList())
                .parallelStream()
                .map(roleEntity -> {
                    RoleEntityInfoResult result = new RoleEntityInfoResult();
                    BeanCopyUtils.copyProperties(roleEntity, result);
                    return result;
                }).collect(Collectors.toList());
    }

    @Override
    public List<SelectInfoResult> getAllRoleInfo() {

        return roleDao.getAllRoleInfo();
    }

    @Override
    public List<SelectInfoResult> getChildrenByParentId(RoleEntityQueryParam param) {
        return roleDao.getChildrenByParentId(param);
    }

    @Override
    public boolean isAdmin(UserRoleDataQueryParam param) {
        int count = roleDao.isAdmin(param);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<UserEntityInfoResult> getUserInfoByRoleId(RoleUserQueryParam param) {
        List<UserEntityInfoResult> results = Lists.newArrayList();

        List<UserEntity> userEntities = roleDao.getUserInfoByRoleId(param);
        List<UserOrgMapEntity> allUserOrgMap = userOrgMapService.list(new QueryWrapper<>());
        List<OrganizationEntity> organizationEntities = organizationService.list(new QueryWrapper<>());
        results = Optional.ofNullable(userEntities).orElse(Lists.newArrayList())
                .parallelStream()
                .filter(Objects::nonNull)
                .map(userEntity -> {
                    UserEntityInfoResult result = new UserEntityInfoResult();
                    BeanCopyUtils.copyProperties(userEntity, result);

                    InputStream picturePath = userEntity.getPicturePath();
                    String encode = null;
                    if (picturePath != null) {
                        try {
                            encode = Base64Utils.encode(IOUtils.toByteArray(picturePath));
                        } catch (IOException e) {
                            log.error(e.getMessage(), e);
                        }
                    }
                    Set<String> orgIds = allUserOrgMap.parallelStream()
                            .filter(Objects::nonNull)
                            .filter(userOrgMapEntity -> userEntity.getId().equals(userOrgMapEntity.getUserId()))
                            .map(UserOrgMapEntity::getOrgId)
                            .collect(Collectors.toSet());

                    result.setOrgIds(orgIds);

                    Set<String> orgNames = organizationEntities.parallelStream()
                            .filter(Objects::nonNull)
                            .filter(o -> orgIds.contains(o.getId()))
                            .map(OrganizationEntity::getOrgName)
                            .collect(Collectors.toSet());
                    result.setOrgNames(orgNames);
                    result.setPhoto(encode);
                    return result;
                }).collect(Collectors.toList());
        return results;
    }

    @SuppressWarnings("all")
    @Override
    @Transactional(rollbackFor = Exception.class)
    @BusinessParamsValidate
    public BusinessResult<Boolean> status(RoleEntityStatusParams param) {
        List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder().build());
        List<UserRoleMapEntity> userRoleMaps = getUserRoleMaps(Sets.newHashSet(param.getAccessUserId()), Sets.newHashSet());
        //操作合法性检查
        operationalLegalityCheck(param.getAccessUserId(), Sets.newHashSet(param.getId()), roles, userRoleMaps);
        Set<String> currAndSonRoleIds = findCurrAndSonRoleIds(Sets.newHashSet(param.getId()), roles);
        List<RoleEntity> update = currAndSonRoleIds.parallelStream()
                .filter(StringUtils::isNoneBlank)
                .map(s -> {
                    RoleEntity entity = new RoleEntity();
                    entity.setId(s);
                    entity.setDeleteFlag(param.getDeleteFlag());
                    return entity;
                }).collect(Collectors.toList());
        return BusinessResult.success(this.updateBatchById(update));
    }

    @SuppressWarnings("all")
    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<RoleEntity> save(RoleEntitySaveParam param) {
        List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder().build());
        List<UserRoleMapEntity> userRoleMaps = getUserRoleMaps(Sets.newHashSet(param.getAccessUserId()), Sets.newHashSet());
        //操作合法性检查
        operationalLegalityCheck(param.getAccessUserId(), Sets.newHashSet(param.getParentId()), roles, userRoleMaps);
        //角色名不能重复
        long count = roles.parallelStream()
                .filter(Objects::nonNull)
                .filter(roleEntity -> param.getRoleName().equals(StringUtils.trim(roleEntity.getRoleName())))
                .count();
        if (count > 0) {
            throw new AppException("50009004");
        }
        RoleEntity entity = new RoleEntity();
        BeanCopyUtils.copyProperties(param, entity);
        save(entity);
        return BusinessResult.success(entity);
    }

    @SuppressWarnings("all")
    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> updateRole(RoleEntitySaveParam param) {
        List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder().build());
        List<UserRoleMapEntity> userRoleMaps = getUserRoleMaps(Sets.newHashSet(param.getAccessUserId()), Sets.newHashSet());
        //操作合法性检查
        operationalLegalityCheck(param.getAccessUserId(), Sets.newHashSet(param.getId()), roles, userRoleMaps);
        if (StringUtils.isBlank(param.getId())) {
            throw new AppException("50009332");
        }
        if (param.getId().equals(param.getParentId())) {
            throw new AppException("50009114");
        }
        //上级不能是自己及子角色
        Set<String> currAndSonRoleIds = findCurrAndSonRoleIds(Sets.newHashSet(param.getId()), roles);
        if (currAndSonRoleIds.contains(param.getParentId())) {
            throw new AppException("50009112");
        }
        //角色名不能重复
        long count = roles.parallelStream()
                .filter(Objects::nonNull)
                .filter(roleEntity -> !param.getId().equals(roleEntity.getId()))
                .filter(roleEntity -> param.getRoleName().equals(StringUtils.trim(roleEntity.getRoleName())))
                .count();
        if (count > 0) {
            throw new AppException("50009004");
        }
        RoleEntity entity = new RoleEntity();
        BeanCopyUtils.copyProperties(param, entity);
        updateById(entity);
//        RoleEntityResult roleEntityResult = BeanCopyUtils.copyProperties(entity, RoleEntityResult.class);
        return BusinessResult.success(true);
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> deleteRole(RoleEntityDelParam param) {
        List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder().build());
        List<UserRoleMapEntity> accessUserRoleMaps = getUserRoleMaps(Sets.newHashSet(param.getAccessUserId()), Sets.newHashSet());
        //操作合法性检查
        operationalLegalityCheck(param.getAccessUserId(), param.getIds(), roles, accessUserRoleMaps);
        //启用状态不能删除
        long openCount = roles.parallelStream()
                .filter(Objects::nonNull)
                .filter(roleEntity -> param.getIds().contains(roleEntity.getId()))
                .filter(roleEntity -> DeleteFlagEnum.N.getCode().equals(roleEntity.getDeleteFlag()))
                .count();
        if (openCount > 0) {
            throw new AppException("50009113");
        }
        //还有子角色不能删除
        long sonCount = roles.parallelStream()
                .filter(Objects::nonNull)
                .filter(roleEntity -> param.getIds().contains(roleEntity.getParentId()))
                .count();
        if (sonCount > 0) {
            throw new AppException("50009333");
        }
        //配置了用户不能删除
        List<UserRoleMapEntity> userRoleMaps = getUserRoleMaps(Sets.newHashSet(), param.getIds());
        if (CollectionUtils.isNotEmpty(userRoleMaps)) {
            throw new AppException("50009334");
        }
        return BusinessResult.success(removeByIds(param.getIds()));
    }

    private void operationalLegalityCheck(String accessUserId, Set<String> operateRoleId, List<RoleEntity> roles, List<UserRoleMapEntity> userRoleMaps) {
        Set<String> currAndSonRoleIdsByUserId = findCurrAndSonRoleIdsByUserId(accessUserId, roles, userRoleMaps);
        if (!currAndSonRoleIdsByUserId.containsAll(Sets.newHashSet(operateRoleId))) {
            throw new AppException("50009115");
        }
    }

    @Override
    @SuppressWarnings("all")
    @Transactional(rollbackFor = Exception.class)
    @BusinessParamsValidate
    public BusinessResult<Boolean> setRoleToUsers(RoleToUsersSaveParam params) {
        List<String> userIds = params.getUserIds();
        String roleId = params.getRoleId();

        //该角色下已存在的userId
        List<UserRoleMapEntity> userRoleMaps = getUserRoleMaps(Sets.newHashSet(), Sets.newHashSet(roleId));
        List<String> existUserIds = Optional.ofNullable(userRoleMaps).orElse(Lists.newArrayList())
                .parallelStream()
                .map(UserRoleMapEntity::getUserId)
                .collect(Collectors.toList());
        //移除的用户列表
        List<String> removeUserIds = existUserIds
                .parallelStream()
                .filter(userId -> !userIds.contains(userId))
                .collect(Collectors.toList());
        //删除的UserRoleMap记录id集合
        Set<String> removeIds = Optional.ofNullable(userRoleMaps).orElse(Lists.newArrayList())
                .parallelStream()
                .filter(userRoleMapEntity -> removeUserIds.contains(userRoleMapEntity.getUserId()))
                .map(UserRoleMapEntity::getId)
                .collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(removeIds)) {
            userRoleMapService.removeByIds(removeIds);
        }
        //新增的用户列表
        List<String> newUserIds = userIds
                .parallelStream()
                .filter(userId -> !existUserIds.contains(userId))
                .collect(Collectors.toList());
        //组装新增用户角色列表
        Set<UserRoleMapEntity> newUserRoleMap = transferTo(newUserIds, roleId);
        //批量存入
        userRoleMapService.saveBatch(newUserRoleMap, 5);
        return BusinessResult.success(true);
    }

    /**
     * 组装UserRoleMapEntity
     *
     * @param userIds 用户id集合
     * @param roleId  角色id
     * @return UserRoleMapEntity集合
     */
    @SuppressWarnings("all")
    private Set<UserRoleMapEntity> transferTo(final List<String> userIds, final String roleId) {
        return Optional.ofNullable(userIds).orElse(Lists.newArrayList())
                .parallelStream()
                .filter(StringUtils::isNoneBlank)
                .map(userId -> {
                    UserRoleMapEntity entity = new UserRoleMapEntity();
                    entity.setUserId(userId);
                    entity.setRoleId(roleId);
                    return entity;
                }).collect(Collectors.toSet());
    }

    @Override
    public BusinessResult<List<SelectInfoResult>> getRoleTree(RoleEntityQueryParam param) {
        List<SelectInfoResult> results = Lists.newArrayList();
        String userId = param.getUserId();
        /*RoleEntityConditionParam roleEntityConditionParam = RoleEntityConditionParam.builder().build();
        BeanCopyUtils.copyProperties(param, roleEntityConditionParam);*/
//        List<RoleEntity> roles = getRoles(roleEntityConditionParam);
        List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder().build());
        List<UserRoleMapEntity> userRoleMaps = getUserRoleMaps(Sets.newHashSet(userId), Sets.newHashSet());
        List<SelectInfoResult> roleLink = getRoleLink(userId, roles, userRoleMaps);
        if (param.isLazy()) {
            String parentId = StringUtils.isBlank(param.getParentId()) ? NumberEnum.ZERO.getNum() : param.getParentId();
            results = roleLink.parallelStream()
                    .filter(selectInfoResult -> parentId.equals(selectInfoResult.getParentId()))
                    .collect(Collectors.toList());
        } else if (StringUtils.isNotBlank(param.getRoleName()) || StringUtils.isNotBlank(param.getDeleteFlag())) {
            String roleName = StringUtils.trim(param.getRoleName());
            Set<String> byRoleName = roles.parallelStream().filter(Objects::nonNull)
                    .filter(roleEntity -> {
                        boolean flag1 = true;
                        boolean flag2 = true;
                        if (StringUtils.isNotBlank(roleName)) {
//                            flag1 = roleName.equals(roleEntity.getRoleName());
                            flag1 = roleEntity.getRoleName().contains(roleName);
                        }
                        if (StringUtils.isNotBlank(param.getDeleteFlag())) {
                            flag2 = param.getDeleteFlag().equals(roleEntity.getDeleteFlag());
                        }
                        return flag1 && flag2;
                    })
                    .map(RoleEntity::getId)
                    .collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(byRoleName)) {
                Set<String> currAndParentRoleIds = findCurrAndParentRoleIds(byRoleName, roles);
                results = roleLink.stream()
                        .filter(Objects::nonNull)
                        .filter(result -> currAndParentRoleIds.contains(result.getId()))
                        .sorted(Comparator.comparing(SelectInfoResult::getCreateTime).reversed())
                        .collect(Collectors.toList());
            }
        } else {
            results = roleLink;
        }
        results = sort(results);
        return BusinessResult.success(results);
    }

    private List<SelectInfoResult> sort(List<SelectInfoResult> results) {
        return results.stream()
                .sorted(Comparator.comparing(SelectInfoResult::getCreateTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<List<ResourcesEntityResult>> getResourcesFromRole(ResourcesFromRoleQueryParam param) {
        List<ResourcesEntityResult> results = Lists.newArrayList();
        String roleId = param.getRoleId();
        Set<String> roleIds = Sets.newHashSet(roleId);
        List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder().deleteFlag(DeleteFlagEnum.N.getCode()).build());
        Set<String> currAndSonRoleIds = findCurrAndSonRoleIds(roleIds, roles);
        Set<String> resourcesIds = roleResourcesMapService.findResourcesIdsByRoleIds(currAndSonRoleIds);
        if (CollectionUtils.isNotEmpty(resourcesIds)) {
            List<ResourcesEntity> resourcesEntities = (List<ResourcesEntity>) resourcesService.listByIds(resourcesIds);
            results = resourcesEntities.parallelStream()
                    .filter(Objects::nonNull)
                    .map(entity -> {
                        ResourcesEntityResult result = new ResourcesEntityResult();
                        BeanCopyUtils.copyProperties(entity, result);
                        String parentId = result.getParentId();
                        if (StringUtils.isNotBlank(parentId)) {
                            resourcesEntities.stream().forEach(resourcesEntity -> {
                                if (parentId.equals(resourcesEntity.getId())) {
                                    result.setParentName(resourcesEntity.getName());
                                }
                            });
                        }
                        return result;
                    }).collect(Collectors.toList());
        }
        return BusinessResult.success(results);
    }

    @SuppressWarnings("all")
    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> setResourcesToRole(ResourcesToRoleSaveParam param) {
        String roleId = param.getRoleId();
        Set<String> resourcesIds = param.getResourcesIds();
        List<RoleResourcesMapEntity> resourcesMapEntities = roleResourcesMapService.findByRoleIds(Sets.newHashSet(roleId));
//        Set<String> currResourcesIds = roleResourcesMapService.findResourcesIdsByRoleIds(Sets.newHashSet(roleId));
        Set<String> roleResourcesIds = findRoleResourcesIds(resourcesMapEntities);
        if (CollectionUtils.isNotEmpty(roleResourcesIds)) {
            roleResourcesMapService.removeByIds(roleResourcesIds);
        }
        Set<RoleResourcesMapEntity> newRoleResources = transferToRoleResourcesMapEntity(resourcesIds, roleId);
        roleResourcesMapService.saveBatch(newRoleResources);
        return BusinessResult.success(true);
        /*//移除的权限列表
        List<String> removeResourcesIds = currResourcesIds
                .parallelStream()
                .filter(resourcesId -> !resourcesIds.contains(resourcesId))
                .collect(Collectors.toList());
        //删除的RoleResourcesMapEntity记录id集合
        Set<String> removeIds = Optional.ofNullable(resourcesMapEntities).orElse(Lists.newArrayList())
                .parallelStream()
                .filter(entity -> removeResourcesIds.contains(entity.getResourcesId()))
                .map(RoleResourcesMapEntity::getId)
                .collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(removeIds)) {
            roleResourcesMapService.removeByIds(removeIds);
        }
        //角色新增的权限
        Set<String> newResourcesIds = resourcesIds
                .parallelStream()
                .filter(userId -> !currResourcesIds.contains(userId))
                .collect(Collectors.toSet());
        //组装新增角色权限列表
        Set<RoleResourcesMapEntity> roleResourcesMapEntities = transferToRoleResourcesMapEntity(newResourcesIds, roleId);
        //批量存入
        roleResourcesMapService.saveBatch(roleResourcesMapEntities, 5);
        return BusinessResult.success(true);*/
    }

    private Set<String> findRoleResourcesIds(List<RoleResourcesMapEntity> roleResourcesMaps) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(roleResourcesMaps)) {
            results = roleResourcesMaps.parallelStream()
                    .filter(Objects::nonNull)
                    .map(RoleResourcesMapEntity::getId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    private Set<RoleResourcesMapEntity> transferToRoleResourcesMapEntity(Set<String> resourcesIds, String roleId) {
        return resourcesIds.parallelStream()
                .map(s -> {
                    RoleResourcesMapEntity entity = new RoleResourcesMapEntity();
                    entity.setResourcesId(s);
                    entity.setRoleId(roleId);
                    return entity;
                }).collect(Collectors.toSet());
    }

    @Override
    public List<ResourcesEntity> findResourcesByUserId(String userId) {
        List<ResourcesEntity> results = Lists.newArrayList();
        Set<String> roleAndSonByUserId = findRoleAndSonByUserId(userId);
        List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder()
                .ids(roleAndSonByUserId)
                .deleteFlag(DeleteFlagEnum.N.getCode()).build());
        Set<String> authRoleIds = Optional.ofNullable(roles).orElse(Lists.newArrayList())
                .parallelStream()
                .filter(Objects::nonNull)
                .map(RoleEntity::getId)
                .collect(Collectors.toSet());
        Optional<List<ResourcesEntity>> resourcesByRoleIds = resourcesService.findResourcesByRoleIds(authRoleIds);
        if (resourcesByRoleIds.isPresent()) {
            results = resourcesByRoleIds.get();
        }
        return results;
    }

    @Override
    public Set<String> findRoleAndSonByUserId(String userId) {
        Set<String> results = Sets.newHashSet();
        List<UserRoleMapEntity> userRoleMaps = getUserRoleMaps(Sets.newHashSet(userId), Sets.newHashSet());
        if (CollectionUtils.isNotEmpty(userRoleMaps)) {
            Set<String> roleIds = userRoleMaps.parallelStream()
                    .filter(Objects::nonNull)
                    .map(UserRoleMapEntity::getRoleId)
                    .collect(Collectors.toSet());
            List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder().build());
            //当前用户的角色id集合
            results = findCurrAndSonRoleIdsByUserId(userId, roles, userRoleMaps);
        }
        return results;
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<List<RoleEntityResult>> getCurrAndSonRoles(CurrAndSonRoleQueryParam param) {
        List<RoleEntityResult> results = Lists.newArrayList();
        Set<String> roleIds = param.getRoleIds();
        List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder().build());
//        Set<String> currAndSonRoleIds = loopFindSonRoleId(roleIds, roles);
        Set<String> currAndSonRoleIds = findCurrAndSonRoleIds(roleIds, roles);
        results = roles.parallelStream()
                .filter(Objects::nonNull)
                .filter(roleEntity -> currAndSonRoleIds.contains(roleEntity.getId()))
                .filter(roleEntity -> {
                    boolean flag = true;
                    if (StringUtils.isNotBlank(param.getDeleteFlag())) {
                        flag = param.getDeleteFlag().equals(roleEntity.getDeleteFlag());
                    }
                    return flag;
                })
                .map(roleEntity -> {
                    RoleEntityResult result = new RoleEntityResult();
                    BeanCopyUtils.copyProperties(roleEntity, result);
                    return result;
                }).collect(Collectors.toList());
        return BusinessResult.success(results);
    }

    /**
     * 取得所有角色的上下级链
     *
     * @param userId 标记userId用户拥有的角色及子角色,为null不标记
     * @return 角色链列表
     */
    private List<SelectInfoResult> getRoleLink(String userId, final List<RoleEntity> roles, final List<UserRoleMapEntity> userRoleMaps) {
        List<SelectInfoResult> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(roles)) {
            //当前用户的角色id集合
            Set<String> currRoleIds = findCurrRoleIdsByUserId(userId, userRoleMaps);
            //根据父角色找子角色
            Set<String> selfAndSonRoleIds = findCurrAndSonRoleIds(currRoleIds, roles);
            results = BeanCopyUtils.copy(roles, SelectInfoResult.class);
            //父子角色链表，设置有权查看的角色和角色下子角色数量
            for (SelectInfoResult result1 : results) {
                if (StringUtils.isBlank(result1.getSonNum())) {
                    result1.setSonNum(NumberEnum.ZERO.getNum());
                }
                if (selfAndSonRoleIds.contains(result1.getId())) {
                    result1.setOperateFlag(NumberEnum.ONE.getNum());
                }
                if (currRoleIds.contains(result1.getId())) {
                    result1.setCurrOrg(true);
                }
                String parentId = result1.getParentId();
                if (NumberEnum.ZERO.getNum().equals(parentId)) {
                    continue;
                }
                for (SelectInfoResult result2 : results) {
                    if (result2.getId().equals(parentId)) {
                        result1.setParent(result2);
                        String sonNum = result2.getSonNum();
                        if (StringUtils.isBlank(sonNum)) {
                            sonNum = NumberEnum.ZERO.getNum();
                        }
                        result2.setSonNum(String.valueOf(Integer.valueOf(sonNum) + 1));
                    }
                }
            }
        }
        return results;
    }

    /**
     * 查找子角色(递归方式)
     *
     * @param roleIds           当前角色ids
     * @param allRoles          查找对象
     * @param selfAndSonRoleIds 当前角色id及子子id集合
     */
    private void recursionFindSonRoleId(Set<String> roleIds, List<RoleEntity> allRoles, Set<String> selfAndSonRoleIds) {
        selfAndSonRoleIds.addAll(roleIds);
        Set<String> sonRoles = Sets.newHashSet();
        for (String roleId : roleIds) {
            for (RoleEntity allRoleId : allRoles) {
                if (roleId.equals(allRoleId.getParentId())) {
                    sonRoles.add(allRoleId.getId());
                }
            }
        }
        if (CollectionUtils.isNotEmpty(sonRoles)) {
            recursionFindSonRoleId(sonRoles, allRoles, selfAndSonRoleIds);
        }
    }

    /**
     * 通过用户id取得当前角色及子角色id集合
     *
     * @param userId
     * @return
     */
    private Set<String> findCurrAndSonRoleIdsByUserId(String userId, final List<RoleEntity> roles, final List<UserRoleMapEntity> userRoleMaps) {
        Set<String> currRoleIds = findCurrRoleIdsByUserId(userId, userRoleMaps);
        Set<String> results = findCurrAndSonRoleIds(currRoleIds, roles);
        return results;
    }

    /**
     * 取得当前角色及子角色id集合
     *
     * @param currRoleIds
     * @param allRoles
     * @return
     */
    private Set<String> findCurrAndSonRoleIds(Set<String> currRoleIds, final List<RoleEntity> allRoles) {
        Set<String> results = loopFindSonRoleId(currRoleIds, allRoles);
        results.addAll(currRoleIds);
        return results;
    }

    /**
     * 查找子角色(循环方式) 从allRoles中查找currRoleIds中的子角色
     *
     * @param currRoleIds
     * @param allRoles
     * @return
     */
    @SuppressWarnings("all")
    private Set<String> loopFindSonRoleId(Set<String> currRoleIds, final List<RoleEntity> allRoles) {
        Set<String> result = Sets.newHashSet();
        Set<String> temp = currRoleIds;
        while (CollectionUtils.isNotEmpty(temp)) {
            Set<String> tempChildRoleIds = Sets.newHashSet();
            for (String roleId : temp) {
                Set<String> childRoleIds = Optional.ofNullable(allRoles).orElse(Lists.newArrayList())
                        .parallelStream()
                        .filter(roleEntity -> roleId.equals(roleEntity.getParentId()))
                        .map(RoleEntity::getId)
                        .collect(Collectors.toSet());
                result.addAll(childRoleIds);
                tempChildRoleIds.addAll(childRoleIds);
            }
            temp = tempChildRoleIds;
        }
        return result;
    }

    private Set<String> findCurrAndParentRoleIds(Set<String> currRoleIds, final List<RoleEntity> allRoles) {
        Set<String> parentRoleIds = loopFindParentRoleId(currRoleIds, allRoles);
        Set<String> results = Sets.newHashSet(parentRoleIds);
        results.addAll(currRoleIds);
        return results;
    }

    @SuppressWarnings("all")
    private Set<String> loopFindParentRoleId(Set<String> currRoleIds, final List<RoleEntity> allRoles) {
        Set<String> results = Sets.newHashSet();
        Set<String> temp = currRoleIds;
        while (CollectionUtils.isNotEmpty(temp)) {
            Set<String> tempParentRoleIds = Sets.newHashSet();
            for (String roleId : temp) {
                Set<String> parentRoleIds = Optional.ofNullable(allRoles).orElse(Lists.newArrayList())
                        .parallelStream()
                        .filter(roleEntity -> roleId.equals(roleEntity.getId()))
                        .map(RoleEntity::getParentId)
                        .collect(Collectors.toSet());
                results.addAll(parentRoleIds);
                tempParentRoleIds.addAll(parentRoleIds);
            }
            temp = tempParentRoleIds;
        }
        return results;
    }

    /**
     * 根据用户id查找当前用户的角色id集合
     *
     * @param userId
     * @return
     */
    private Set<String> findCurrRoleIdsByUserId(String userId, final List<UserRoleMapEntity> userRoleMaps) {
        Set<String> result = Sets.newHashSet();
        if (StringUtils.isNotBlank(userId)) {
            //提取当前用户的角色id集合
            result = Optional.ofNullable(userRoleMaps).orElse(Lists.newArrayList())
                    .parallelStream()
                    .filter(Objects::nonNull)
                    .filter(userRole -> userId.equals(userRole.getUserId()))
                    .map(UserRoleMapEntity::getRoleId)
                    .collect(Collectors.toSet());
        }
        return result;
    }

    /**
     * 查角色数据
     *
     * @return
     */
    private List<RoleEntity> getRoles(RoleEntityConditionParam param) {
        QueryWrapper<RoleEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(RoleEntity.ID, param.getIds());
        }
        if (CollectionUtils.isNotEmpty(param.getParentIds())) {
            wrapper.in(RoleEntity.PARENT_ID, param.getParentIds());
        }
        if (StringUtils.isNotBlank(param.getDeleteFlag())) {
            wrapper.eq(RoleEntity.DELETE_FLAG, param.getDeleteFlag());
        }
        if (StringUtils.isNotBlank(param.getRoleName())) {
            wrapper.like(RoleEntity.ROLE_NAME, param.getRoleName());
        }
        return list(wrapper);
    }

    /**
     * 查询用户角色映射关系
     *
     * @return
     */
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
}
