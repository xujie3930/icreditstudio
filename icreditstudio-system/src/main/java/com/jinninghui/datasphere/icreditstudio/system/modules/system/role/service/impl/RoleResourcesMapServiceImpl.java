package com.jinninghui.datasphere.icreditstudio.system.modules.system.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.Query;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.entity.RoleResourcesMapEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.mapper.RoleResourcesMapDao;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.service.RoleResourcesMapService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.service.result.RoleResourcesMapEntityResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.web.request.RoleResourcesMapEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.web.request.RoleResourcesMapParam;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service("roleResourcesMapService")
public class RoleResourcesMapServiceImpl extends ServiceImpl<RoleResourcesMapDao, RoleResourcesMapEntity> implements RoleResourcesMapService {

    @Override
    public BusinessPageResult queryPage(RoleResourcesMapEntityPageRequest pageRequest) {
        IPage<RoleResourcesMapEntity> page = this.page(
                new Query<RoleResourcesMapEntity>().getPage(pageRequest),
                new QueryWrapper<RoleResourcesMapEntity>()
        );

        return BusinessPageResult.build(page, pageRequest);
    }

    @Override
    public BusinessResult<List<RoleResourcesMapEntityResult>> resource(RoleResourcesMapParam param) {
        QueryWrapper<RoleResourcesMapEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(RoleResourcesMapEntity.ROLE_ID, param.getRoleId());
        List<RoleResourcesMapEntity> resourcesMapEntities = list(wrapper);
        List<RoleResourcesMapEntityResult> results = Optional.ofNullable(resourcesMapEntities).orElse(Lists.newArrayList())
                .parallelStream()
                .map(roleResourcesMapEntity -> {
                    RoleResourcesMapEntityResult result = new RoleResourcesMapEntityResult();
                    BeanCopyUtils.copyProperties(roleResourcesMapEntity, result);
                    return result;
                }).collect(Collectors.toList());
        return BusinessResult.success(results);
    }

    @Override
    public List<RoleResourcesMapEntity> findByRoleIds(Set<String> roleIds) {
        List<RoleResourcesMapEntity> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(roleIds)) {
            QueryWrapper<RoleResourcesMapEntity> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc(RoleResourcesMapEntity.CREATE_TIME);
            wrapper.in(RoleResourcesMapEntity.ROLE_ID, roleIds);
            results = list(wrapper);
        }
        return results;
    }

    @Override
    public Set<String> findResourcesIdsByRoleIds(Set<String> roleIds) {
        Set<String> results = Sets.newHashSet();
        List<RoleResourcesMapEntity> byRoleIds = findByRoleIds(roleIds);
        results = byRoleIds.parallelStream()
                .filter(Objects::nonNull)
                .map(RoleResourcesMapEntity::getResourcesId)
                .collect(Collectors.toSet());
        return results;
    }

    @Override
    public List<RoleResourcesMapEntity> findByResourcesIds(Set<String> resourcesIds) {
        List<RoleResourcesMapEntity> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(resourcesIds)) {
            QueryWrapper<RoleResourcesMapEntity> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc(RoleResourcesMapEntity.CREATE_TIME);
            wrapper.in(RoleResourcesMapEntity.RESOURCES_ID, resourcesIds);
            results = list(wrapper);
        }
        return results;
    }

    @Override
    public Set<String> findRoleIdsByResourcesIds(Set<String> resourcesIds) {
        Set<String> results = Sets.newHashSet();
        List<RoleResourcesMapEntity> byResourcesIds = findByResourcesIds(resourcesIds);
        results = byResourcesIds.parallelStream()
                .filter(Objects::nonNull)
                .map(RoleResourcesMapEntity::getRoleId)
                .collect(Collectors.toSet());
        return results;
    }
}
