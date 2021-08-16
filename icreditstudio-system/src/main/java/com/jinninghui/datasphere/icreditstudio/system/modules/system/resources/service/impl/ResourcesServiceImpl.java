package com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.Query;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.sequence.api.SequenceService;
import com.jinninghui.datasphere.icreditstudio.framework.utils.excel.EasyExcelUtil;
import com.jinninghui.datasphere.icreditstudio.framework.utils.excel.ExcelUtil;
import com.jinninghui.datasphere.icreditstudio.framework.validate.BusinessParamsValidate;
import com.jinninghui.datasphere.icreditstudio.system.common.enums.DeleteFlagEnum;
import com.jinninghui.datasphere.icreditstudio.system.common.enums.NumberEnum;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.common.code.CommonConstant;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.result.ExpertInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.entity.ResourcesEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.mapper.ResourcesDao;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.service.ResourcesService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.service.result.ResourcesEntityResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.request.ResourcesEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.request.ResourcesQueryParams;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.request.ResourcesQueryRoleParams;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.result.ResourcesEntityExport;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.entity.RoleEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.entity.RoleResourcesMapEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.service.RoleResourcesMapService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.service.RoleService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity.UserRoleMapEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.UserRoleMapService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.service.param.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service("resourcesService")
@Slf4j
public class ResourcesServiceImpl extends ServiceImpl<ResourcesDao, ResourcesEntity> implements ResourcesService {

    @Autowired
    private ResourcesDao resourcesDao;
    @Autowired
    private SequenceService generalSequence;
    @Autowired
    private RoleResourcesMapService roleResourcesMapService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleMapService userRoleMapService;

    @Override
    public BusinessPageResult queryPage(ResourcesEntityPageRequest pageRequest) {
        IPage<ResourcesEntity> page = this.page(
                new Query<ResourcesEntity>().getPage(pageRequest),
                new QueryWrapper<ResourcesEntity>()
        );

        return BusinessPageResult.build(page, pageRequest);
    }

    @Override
    public List<ResourcesEntity> getMenuInfoByRoleIds(ResourcesQueryParams request) {

        return resourcesDao.getMenuInfoByRoleIds(request);
    }

    @Override
    public BusinessResult<?> exportExcel(HttpServletRequest request, HttpServletResponse response, ResourcesEntity resources) {

        try {
            List<ResourcesEntityExport> resourcesEntityList = resourcesDao.queryInfoByName(resources);
            ExcelUtil.exportExcel(response, "资源列表导出", resourcesEntityList, ResourcesEntityExport.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return BusinessResult.fail("", "资源列表导出失败");
        }
        return BusinessResult.success("资源列表导出成功");

    }

    @Override
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response, Class<ResourcesEntityExport> resourcesEntityExportClass) {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        Map<String, String> resourcesNameAndId = new HashMap<>();
        ExpertInfoResult expertInfoResult = new ExpertInfoResult();
        List<ResourcesEntity> passList = new ArrayList<>();
        List<Object> noPassList = new ArrayList<>();

        // 获取所有的资源信息
        List<ResourcesEntity> resourcesEntities = this.list();
        if (CollectionUtils.isNotEmpty(resourcesEntities)) {
            resourcesNameAndId = resourcesEntities.stream()
                    .collect(Collectors.toMap(ResourcesEntity::getName, ResourcesEntity::getId, (k1, k2) -> k1));
        }

        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 获取上传文件对象
            MultipartFile file = entity.getValue();
            try {
                List<T> importExcelList = EasyExcelUtil.syncReadModel(file.getInputStream(), resourcesEntityExportClass, 0, 1);
                List<ResourcesEntityExport> infoExperts = BeanCopyUtils.copy(importExcelList, ResourcesEntityExport.class);

                // 去除导入时 模块名称的重复
                Map<String, List<ResourcesEntityExport>> resourcesNameGroupBy = infoExperts
                        .stream().filter(f -> f.getName() != null)
                        .collect(Collectors.groupingBy(ResourcesEntityExport::getName));
                resourcesNameGroupBy.forEach((key, value) -> {
                    if (value.size() > 1) {
                        value.forEach(f -> {
                            String format = String.format("导入模块名称[%S]重复!", f.getName());
                            f.setErrorMsg(format);
                            noPassList.add(f);
                        });
                        infoExperts.removeAll(value);
                    }
                });


                for (ResourcesEntityExport resourcesEntityExport : infoExperts) {
                    //校验
                    if (!importResourcesCheck(resourcesNameAndId, noPassList, resourcesEntityExport)) {
                        continue;
                    }
                    // 通过保存
                    ResourcesEntity resourcesEntity = BeanCopyUtils.copyProperties(resourcesEntityExport, ResourcesEntity.class);
                    resourcesEntity.setId(generalSequence.nextValueString());
                    // 父部门 赋值
                    resourcesEntity.setParentId(resourcesNameAndId.get(resourcesEntityExport.getParentName()));
                    resourcesEntity.setDeleteFlag("N");
                    resourcesEntity.setKeepAlive("N");
                    // 数据库字典转换
                    resourcesEntity.setType(CommonConstant.RESOURCES_TYPE_MAP.get(resourcesEntityExport.getType()));
                    resourcesEntity.setIsCache(CommonConstant.RESOURCES_STATUS_MAP.get(resourcesEntityExport.getIsCache()));
                    resourcesEntity.setIsShow(CommonConstant.RESOURCES_STATUS_MAP.get(resourcesEntityExport.getIsShow()));
                    passList.add(resourcesEntity);
                }
                expertInfoResult.setNoPassList(noPassList);
                expertInfoResult.setErrorCount(noPassList.size());
                expertInfoResult.setSuccessCount(passList.size());

                this.saveBatch(passList);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                //手动开启事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return BusinessResult.fail("00", "文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return BusinessResult.success(expertInfoResult);
    }

    @BusinessParamsValidate
    @Override
    public List<RoleEntity> getRoleInfoByMenuIds(ResourcesQueryRoleParams request) {

        return resourcesDao.getRoleInfoByMenuIds(request);
    }

    private boolean importResourcesCheck(Map<String, String> resourcesNameAndId, List<Object> noPassList,
                                         ResourcesEntityExport resourcesEntityExport) {


        //校验名称不能为空
        if (StringUtils.isEmpty(resourcesEntityExport.getName())) {
            resourcesEntityExport.setErrorMsg("模块名称不能为空");
            noPassList.add(resourcesEntityExport);
            return false;
        }
        //校验名称是否已存在
        if (resourcesNameAndId.get(resourcesEntityExport.getName()) != null) {
            String format = String.format("模块名称[%S]已存在!", resourcesEntityExport.getName());
            resourcesEntityExport.setErrorMsg(format);
            noPassList.add(resourcesEntityExport);
            return false;
        }


        return true;
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> save(ResourcesEntitySaveParam param) {
        ResourcesEntity entity = BeanCopyUtils.copyProperties(param, ResourcesEntity.class);
        String needAuth = param.getNeedAuth();
        int needA = StringUtils.isNotBlank(needAuth) ? Integer.valueOf(needAuth) : 1;
        entity.setNeedAuth(needA);
        if (StringUtils.isBlank(entity.getParentId())) {
            entity.setParentId(NumberEnum.ZERO.getNum());
        }
        save(entity);
        return BusinessResult.success(true);
    }

    @Override
    public BusinessResult<ResourcesEntityResult> edit(ResourcesEntitySaveParam param) {
        if (StringUtils.isBlank(param.getId())) {
            throw new AppException("50009338");
        }
        ResourcesEntity entity = BeanCopyUtils.copyProperties(param, ResourcesEntity.class);

        String needAuth = param.getNeedAuth();
        int needA = StringUtils.isNotBlank(needAuth) ? Integer.valueOf(needAuth) : 1;
        entity.setNeedAuth(needA);
        if (StringUtils.isBlank(entity.getParentId())) {
            entity.setParentId(NumberEnum.ZERO.getNum());
        }
        List<ResourcesEntity> resources = getResources(ResourcesEntityConditionParam.builder().build());
        //父级模块校验
        validityOfParentId(entity.getId(), entity.getParentId(), resources);
        updateById(entity);
        ResourcesEntityResult result = BeanCopyUtils.copyProperties(entity, ResourcesEntityResult.class);
        result.setNeedAuth(param.getNeedAuth());
        return BusinessResult.success(result);
    }

    private void validityOfParentId(String currId, String parentId, List<ResourcesEntity> resources) {
        if (CollectionUtils.isNotEmpty(resources)) {
            List<ResourcesEntityResult> copy = BeanCopyUtils.copy(resources, ResourcesEntityResult.class);
            List<ResourcesEntityResult> currAndSonResources = findCurrAndSonResources(Sets.newHashSet(currId), copy);
            Set<String> resourcesIds = findResourcesIds(currAndSonResources);
            if (resourcesIds.contains(parentId)) {
                throw new AppException("50009353");
            }
        }
    }

    private Set<String> findResourcesIds(List<ResourcesEntityResult> resourcesEntityResults) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(resourcesEntityResults)) {
            results = resourcesEntityResults.parallelStream()
                    .filter(Objects::nonNull)
                    .map(ResourcesEntityResult::getId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    @Override
    public BusinessResult<Boolean> delete(ResEntityDelParam param) {
        List<ResourcesEntity> resources = getResources(ResourcesEntityConditionParam.builder().build());
        //有子菜单不能删除
        checkSubResources(param.getIds(), resources);
        List<ResourcesEntity> resourcesByIds = findResourcesByIds(param.getIds(), resources);
        //启用模块不能删除
        long count = resourcesByIds.parallelStream()
                .filter(entity -> DeleteFlagEnum.N.getCode().equals(entity.getDeleteFlag()))
                .count();
        if (count > 0) {
            throw new AppException("50009349");
        }
        removeByIds(param.getIds());
        return BusinessResult.success(true);
    }

    private void checkSubResources(Set<String> currResIds, List<ResourcesEntity> resources) {
        List<ResourcesEntityResult> copy = BeanCopyUtils.copy(resources, ResourcesEntityResult.class);
        if (CollectionUtils.isNotEmpty(loopFindSonResourcesId(currResIds, copy))) {
            throw new AppException("50009348");
        }
    }

    @Override
    public BusinessResult<List<ResourcesEntityResult>> queryList(ResourcesEntityQueryParam param) {
        List<ResourcesEntityResult> results = Lists.newArrayList();

//        QueryWrapper<ResourcesEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            List<ResourcesEntity> idsResources = getResources(ResourcesEntityConditionParam.builder().build());
            List<ResourcesEntityResult> resourcesEntityResults = markOperateFlagToAllByRole(param.getUserId(), idsResources);
            results = findCurrAndSonResources(param.getIds(), resourcesEntityResults);
        } else if (StringUtils.isNotBlank(param.getName())) {
//            wrapper.like(ResourcesEntity.NAME, param.getName());
//            List<ResourcesEntity> nameResources = list(wrapper);
            List<ResourcesEntity> nameResources = getResources(ResourcesEntityConditionParam.builder().name(param.getName()).build());
            List<ResourcesEntityResult> resourcesEntityResults = markOperateFlagToAllByRole(param.getUserId(), nameResources);
            Set<String> currIds = resourcesEntityResults.parallelStream()
                    .map(ResourcesEntityResult::getId).collect(Collectors.toSet());

            List<ResourcesEntity> resources = getResources(ResourcesEntityConditionParam.builder().build());
            List<ResourcesEntityResult> resourcesEntity = markOperateFlagToAllByRole(param.getUserId(), resources);
            results = findCurrAndParentResources(currIds, resourcesEntity);
        } else {
//            List<ResourcesEntity> resources = list(wrapper);
            List<ResourcesEntity> resources = getResources(ResourcesEntityConditionParam.builder().build());
            results = markOperateFlagToAllByRole(param.getAccessUserId(), resources);
        }
        results = sort(results);
        return BusinessResult.success(results);
    }

    private List<ResourcesEntityResult> sort(List<ResourcesEntityResult> results) {
        return results.stream()
                .map(result -> {
                    if (Objects.isNull(result.getSortNumber())) {
                        result.setSortNumber(0);
                    }
                    return result;
                })
                .sorted(Comparator.comparing(ResourcesEntityResult::getSortNumber)
                        .thenComparing(ResourcesEntityResult::getCreateTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("all")
    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> setRolesToResource(RolesToResourceSaveParam param) {
        Set<String> roleIds = param.getRoleIds();
        String resourcesId = param.getResourcesId();
        List<RoleResourcesMapEntity> currResourcesMapEntities = roleResourcesMapService.findByResourcesIds(Sets.newHashSet(resourcesId));
        Set<String> currRoleIds = roleResourcesMapService.findRoleIdsByResourcesIds(Sets.newHashSet(resourcesId));
        //移除的角色权限id列表
        List<String> removeRoleIds = currRoleIds
                .parallelStream()
                .filter(r -> !roleIds.contains(r))
                .collect(Collectors.toList());
        //删除的RoleResourcesMapEntity记录id集合
        Set<String> removeIds = Optional.ofNullable(currResourcesMapEntities).orElse(Lists.newArrayList())
                .parallelStream()
                .filter(entity -> removeRoleIds.contains(entity.getRoleId()))
                .map(RoleResourcesMapEntity::getId)
                .collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(removeIds)) {
            roleResourcesMapService.removeByIds(removeIds);
        }
        //角色新增的权限
        Set<String> newRoleIds = roleIds
                .parallelStream()
                .filter(roleId -> !currRoleIds.contains(roleId))
                .collect(Collectors.toSet());
        //组装新增角色权限列表
        Set<RoleResourcesMapEntity> roleResourcesMapEntities = transferToRoleResourcesMapEntity(newRoleIds, resourcesId);
        //批量存入
        roleResourcesMapService.saveBatch(roleResourcesMapEntities, 5);
        return BusinessResult.success(true);
    }

    @Override
    public Optional<List<ResourcesEntity>> findResourcesByRoleIds(Set<String> roleIds) {
        Optional<List<ResourcesEntity>> results = Optional.empty();
        if (CollectionUtils.isNotEmpty(roleIds)) {
            List<RoleResourcesMapEntity> roleResourcesMaps = getRoleResourcesMaps(roleIds);
            Set<String> resourcesIdsByRoleIds = findResourcesIdsByRoleIds(roleIds, roleResourcesMaps);
            List<ResourcesEntity> resources = getResources(ResourcesEntityConditionParam.builder().ids(resourcesIdsByRoleIds).build());
            results = Optional.ofNullable(resources);
        }
        return results;
    }

    private Set<RoleResourcesMapEntity> transferToRoleResourcesMapEntity(Set<String> roleIds, String resourcesId) {
        return roleIds.parallelStream()
                .map(s -> {
                    RoleResourcesMapEntity entity = new RoleResourcesMapEntity();
                    entity.setResourcesId(resourcesId);
                    entity.setRoleId(s);
                    return entity;
                }).collect(Collectors.toSet());
    }

    /**
     * 标记用户角色下的权限
     *
     * @param userId
     * @return
     */
    private List<ResourcesEntityResult> markOperateFlagToAllByRole(String userId, List<ResourcesEntity> allResources) {
        List<ResourcesEntityResult> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(allResources)) {
            //当前
            //当前角色及子角色ID
            Set<String> currRoleAndSon = roleService.findRoleAndSonByUserId(userId);
            //有权限的模块Id
            Set<String> resourcesId = roleResourcesMapService.findResourcesIdsByRoleIds(currRoleAndSon);
            results = allResources.parallelStream()
                    .filter(Objects::nonNull)
                    .map(resource -> {
                        ResourcesEntityResult result = new ResourcesEntityResult();
                        BeanCopyUtils.copyProperties(resource, result);
                        if (resourcesId.contains(resource.getId())) {
                            result.setOperateFlag("1");
                        }
                        String parentId = result.getParentId();
                        if (StringUtils.isNotBlank(parentId)) {
                            allResources.stream().forEach(entity -> {
                                if (parentId.equals(entity.getId())) {
                                    result.setParentName(entity.getName());
                                }
                            });
                        }
                        return result;
                    }).collect(Collectors.toList());
        }
        return results;
    }

    private List<ResourcesEntity> findResourcesByIds(Set<String> ids, List<ResourcesEntity> resources) {
        List<ResourcesEntity> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(ids) && CollectionUtils.isNotEmpty(resources)) {
            results = resources.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(entity -> ids.contains(entity.getId()))
                    .collect(Collectors.toList());
        }
        return results;
    }

    //根据模块ID找到当前模块及子模块
    private List<ResourcesEntityResult> findCurrAndSonResources(Set<String> currResourcesIds, List<ResourcesEntityResult> resources) {
        List<ResourcesEntityResult> results = Lists.newArrayList();
        Set<String> resourcesIds = Sets.newHashSet();
        resourcesIds.addAll(currResourcesIds);
        Set<String> sonIds = loopFindSonResourcesId(currResourcesIds, resources);
        resourcesIds.addAll(sonIds);
        results = resources.parallelStream()
                .filter(resourcesEntityResult -> resourcesIds.contains(resourcesEntityResult.getId()))
                .collect(Collectors.toList());
        return results;
    }

    //根据模块ID找到当前模块及父模块
    private List<ResourcesEntityResult> findCurrAndParentResources(Set<String> currResourcesIds, List<ResourcesEntityResult> resources) {
        List<ResourcesEntityResult> results = Lists.newArrayList();
        Set<String> resourcesIds = Sets.newHashSet();
        resourcesIds.addAll(currResourcesIds);
        Set<String> parentIds = loopFindParentResourcesId(currResourcesIds, resources);
        resourcesIds.addAll(parentIds);
        results = resources.parallelStream()
                .filter(resourcesEntityResult -> resourcesIds.contains(resourcesEntityResult.getId()))
                .collect(Collectors.toList());
        return results;
    }

    /**
     * 查找子模块
     *
     * @param currResourcesId
     * @param resources
     * @return
     */
    @SuppressWarnings("all")
    private Set<String> loopFindSonResourcesId(Set<String> currResourcesId, List<ResourcesEntityResult> resources) {
        Set<String> result = Sets.newHashSet();
        Set<String> temp = currResourcesId;
        while (CollectionUtils.isNotEmpty(temp)) {
            Set<String> tempChildResourcesIds = Sets.newHashSet();
            for (String resourcesId : temp) {
                Set<String> childResourcesIds = Optional.ofNullable(resources).orElse(Lists.newArrayList())
                        .parallelStream()
                        .filter(resourcesEntity -> resourcesId.equals(resourcesEntity.getParentId()))
                        .map(ResourcesEntityResult::getId)
                        .collect(Collectors.toSet());
                result.addAll(childResourcesIds);
                tempChildResourcesIds.addAll(childResourcesIds);
            }
            temp = tempChildResourcesIds;
        }
        return result;
    }

    @SuppressWarnings("all")
    private Set<String> loopFindParentResourcesId(Set<String> currResourcesId, List<ResourcesEntityResult> resources) {
        Set<String> result = Sets.newHashSet();
        Set<String> temp = currResourcesId;
        while (CollectionUtils.isNotEmpty(temp)) {
            Set<String> tempParentResourcesIds = Sets.newHashSet();
            for (String resourcesId : temp) {
                Set<String> parentResourcesIds = Optional.ofNullable(resources).orElse(Lists.newArrayList())
                        .parallelStream()
                        .filter(resourcesEntity -> resourcesId.equals(resourcesEntity.getId()))
                        .map(ResourcesEntityResult::getParentId)
                        .collect(Collectors.toSet());
                result.addAll(parentResourcesIds);
                tempParentResourcesIds.addAll(parentResourcesIds);
            }
            temp = tempParentResourcesIds;
        }
        return result;
    }

    private Set<String> findCurrResourcesIdsByUserId(String userId, List<UserRoleMapEntity> userRoleMaps, List<RoleResourcesMapEntity> roleResourcesMaps) {
        Set<String> results = Sets.newHashSet();
        if (StringUtils.isNotBlank(userId) && CollectionUtils.isNotEmpty(userRoleMaps) && CollectionUtils.isNotEmpty(roleResourcesMaps)) {
            Set<String> roleIdsByUserIds = findRoleIdsByUserIds(Sets.newHashSet(userId), userRoleMaps);
            results = findResourcesIdsByRoleIds(roleIdsByUserIds, roleResourcesMaps);
        }
        return results;
    }

    private Set<String> findResourcesIdsByRoleIds(Set<String> roleIds, List<RoleResourcesMapEntity> roleResourcesMaps) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(roleIds) && CollectionUtils.isNotEmpty(roleResourcesMaps)) {
            results = roleResourcesMaps.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(map -> roleIds.contains(map.getRoleId()))
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
                    .filter(map -> userIds.contains(map.getUserId()))
                    .map(UserRoleMapEntity::getRoleId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    private List<ResourcesEntity> getResources(ResourcesEntityConditionParam param) {
        QueryWrapper<ResourcesEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(ResourcesEntity.ID, param.getIds());
        }
        if (CollectionUtils.isNotEmpty(param.getParentIds())) {
            wrapper.in(ResourcesEntity.PARENT_ID, param.getParentIds());
        }
        if (StringUtils.isNotBlank(param.getName())) {
            wrapper.like(ResourcesEntity.NAME, param.getName());
        }
        return list(wrapper);
    }

    private List<RoleResourcesMapEntity> getRoleResourcesMaps(Set<String> roleIds) {
        QueryWrapper<RoleResourcesMapEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(roleIds)) {
            wrapper.in(RoleResourcesMapEntity.ROLE_ID, roleIds);
        }
        return roleResourcesMapService.list(wrapper);
    }

    private List<UserRoleMapEntity> getUserRoleMaps(Set<String> userIds) {
        QueryWrapper<UserRoleMapEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(userIds)) {
            wrapper.in(UserRoleMapEntity.USER_ID, userIds);
        }
        return userRoleMapService.list(wrapper);
    }
}
