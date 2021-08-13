package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity.UserRoleMapEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.request.UserRoleMapEntityPageRequest;

/**
 * 
 *
 * @author hzh
 */
public interface UserRoleMapService extends IService<UserRoleMapEntity> {

    /**
    *  分页查询
    * @param pageRequest
    * @return
    */
    BusinessPageResult queryPage(UserRoleMapEntityPageRequest pageRequest);
}

