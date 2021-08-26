package com.jinninghui.datasphere.icreaditstudio.workspace.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreaditstudio.workspace.entity.IcreditWorkspaceEntity;
import com.jinninghui.datasphere.icreaditstudio.workspace.entity.IcreditWorkspaceUserEntity;
import com.jinninghui.datasphere.icreaditstudio.workspace.mapper.IcreditWorkspaceUserMapper;
import com.jinninghui.datasphere.icreaditstudio.workspace.service.IcreditWorkspaceUserService;
import com.jinninghui.datasphere.icreaditstudio.workspace.web.request.IcreditWorkspaceUserEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xujie
 * @since 2021-08-23
 */
@Service
public class IcreditWorkspaceUserServiceImpl extends ServiceImpl<IcreditWorkspaceUserMapper, IcreditWorkspaceUserEntity> implements IcreditWorkspaceUserService {

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
}
