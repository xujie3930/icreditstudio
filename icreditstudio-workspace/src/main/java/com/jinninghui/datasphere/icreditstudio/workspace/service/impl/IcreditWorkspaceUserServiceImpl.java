package com.jinninghui.datasphere.icreditstudio.workspace.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.workspace.entity.IcreditWorkspaceEntity;
import com.jinninghui.datasphere.icreditstudio.workspace.entity.IcreditWorkspaceUserEntity;
import com.jinninghui.datasphere.icreditstudio.workspace.mapper.IcreditWorkspaceUserMapper;
import com.jinninghui.datasphere.icreditstudio.workspace.service.IcreditWorkspaceUserService;
import com.jinninghui.datasphere.icreditstudio.workspace.web.request.IcreditWorkspaceUserEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        return list;
    }

    public List<IcreditWorkspaceUserEntity> queryMemberListByWorkspaceId(String id) {
        return workspaceUserMapper.getUserListById(id);
    }
}
