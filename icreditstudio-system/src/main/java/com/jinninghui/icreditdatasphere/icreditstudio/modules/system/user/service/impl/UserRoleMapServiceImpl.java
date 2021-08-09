package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.entity.UserRoleMapEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.mapper.UserRoleMapDao;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.UserRoleMapService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.request.UserRoleMapEntityPageRequest;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.Query;
import org.springframework.stereotype.Service;


@Service("userRoleMapService")
public class UserRoleMapServiceImpl extends ServiceImpl<UserRoleMapDao, UserRoleMapEntity> implements UserRoleMapService {

    @Override
    public BusinessPageResult queryPage(UserRoleMapEntityPageRequest pageRequest) {
        IPage<UserRoleMapEntity> page = this.page(
                new Query<UserRoleMapEntity>().getPage(pageRequest),
                new QueryWrapper<UserRoleMapEntity>()
        );

        return BusinessPageResult.build(page, pageRequest);
    }

}
