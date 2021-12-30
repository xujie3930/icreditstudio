package com.jinninghui.datasphere.icreditstudio.workspace.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.Query;
import com.jinninghui.datasphere.icreditstudio.workspace.entity.IcreditWorkspaceEntity;
import com.jinninghui.datasphere.icreditstudio.workspace.entity.IcreditWorkspaceUserEntity;
import com.jinninghui.datasphere.icreditstudio.workspace.mapper.IcreditWorkspaceUserMapper;
import com.jinninghui.datasphere.icreditstudio.workspace.service.IcreditWorkspaceUserService;
import com.jinninghui.datasphere.icreditstudio.workspace.web.request.IcreditWorkspaceUserEntityPageRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2021-08-23
 */
@Service
public class IcreditWorkspaceUserServiceImpl extends ServiceImpl<IcreditWorkspaceUserMapper, IcreditWorkspaceUserEntity> implements IcreditWorkspaceUserService {

    @Autowired
    private IcreditWorkspaceUserMapper workspaceUserMapper;

    @Override
    public BusinessPageResult queryPage(IcreditWorkspaceUserEntityPageRequest pageRequest) {
        QueryWrapper<IcreditWorkspaceUserEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(pageRequest.getSpaceId())) {
            wrapper.eq(IcreditWorkspaceUserEntity.SPACE_ID, pageRequest.getSpaceId());
        }
        wrapper.orderByDesc(IcreditWorkspaceEntity.CREATE_TIME);
        IPage<IcreditWorkspaceUserEntity> page = this.page(
                new Query<IcreditWorkspaceUserEntity>().getPage(pageRequest),
                wrapper
        );
        return BusinessPageResult.build(page, pageRequest);
    }

    @Override
    public List<Map<String, String>> getWorkspaceByUserId(String id) {
        List<Map<String, String>> list = workspaceUserMapper.getWorkspaceByUserId(id);
        //增加默认工作空间信息
        Map<String, String> defaultWorkspace = new HashMap<String, String>() {{
            put("id", "0");
            put("name", "默认工作空间");
        }};
        list.add(0, defaultWorkspace);
        return list;
    }

    public List<IcreditWorkspaceUserEntity> queryMemberListByWorkspaceId(String id) {
        return workspaceUserMapper.getUserListById(id);
    }

    @Override
    public List<String> getWorkSpaceIdsByUserId(String userId) {
        return workspaceUserMapper.getWorkSpaceIdsByUserId(userId);
    }

    @Override
    public BusinessResult<List<IcreditWorkspaceUserEntity>> getWorkspaceUserByWorkspaceId(String workspaceId) {
        QueryWrapper<IcreditWorkspaceUserEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(IcreditWorkspaceUserEntity.SPACE_ID, workspaceId);
        List<IcreditWorkspaceUserEntity> list = list(wrapper);
        return BusinessResult.success(Optional.ofNullable(list).orElse(Lists.newArrayList()));
    }
}
