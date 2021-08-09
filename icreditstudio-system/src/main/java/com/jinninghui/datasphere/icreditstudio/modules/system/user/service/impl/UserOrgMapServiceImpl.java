package com.jinninghui.datasphere.icreditstudio.modules.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserOrgMapEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.mapper.UserOrgMapDao;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.UserOrgMapService;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.web.request.UserOrgMapEntityPageRequest;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.Query;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service("userOrgMapService")
public class UserOrgMapServiceImpl extends ServiceImpl<UserOrgMapDao, UserOrgMapEntity> implements UserOrgMapService {

    @Override
    public BusinessPageResult queryPage(UserOrgMapEntityPageRequest pageRequest) {
        IPage<UserOrgMapEntity> page = this.page(
                new Query<UserOrgMapEntity>().getPage(pageRequest),
                new QueryWrapper<UserOrgMapEntity>()
        );

        return BusinessPageResult.build(page, pageRequest);
    }

    @Override
    public List<UserOrgMapEntity> getUserOrgByOrgIds(Set<String> orgIds) {
        List<UserOrgMapEntity> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(orgIds)) {
            QueryWrapper<UserOrgMapEntity> wrapper = new QueryWrapper<>();
            wrapper.in(UserOrgMapEntity.ORG_ID, orgIds);
            result = list(wrapper);
        }
        return result;
    }

    @Override
    public Set<String> getUserIdsByOrgIds(Set<String> orgIds) {
        Set<String> result = Sets.newHashSet();
        List<UserOrgMapEntity> userOrgs = getUserOrgByOrgIds(orgIds);
        if (CollectionUtils.isNotEmpty(userOrgs)) {
            result = userOrgs.stream()
                    .filter(Objects::nonNull)
                    .map(UserOrgMapEntity::getUserId)
                    .collect(Collectors.toSet());
        }
        return result;
    }

    @Override
    public List<UserOrgMapEntity> getOrgByUserIds(Set<String> userIds) {
        List<UserOrgMapEntity> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(userIds)) {
            QueryWrapper<UserOrgMapEntity> wrapper = new QueryWrapper<>();
            wrapper.in(UserOrgMapEntity.USER_ID,userIds);
            result = list(wrapper);
        }
        return result;
    }
}
