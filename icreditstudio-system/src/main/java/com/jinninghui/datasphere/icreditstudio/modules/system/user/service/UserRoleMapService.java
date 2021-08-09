package com.jinninghui.datasphere.icreditstudio.modules.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserRoleMapEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.web.request.UserRoleMapEntityPageRequest;

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

