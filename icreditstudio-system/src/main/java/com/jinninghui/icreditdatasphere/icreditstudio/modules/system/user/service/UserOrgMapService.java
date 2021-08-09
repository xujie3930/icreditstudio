package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.entity.UserOrgMapEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.request.UserOrgMapEntityPageRequest;
import com.hashtech.businessframework.result.BusinessPageResult;

import java.util.List;
import java.util.Set;

/**
 * 
 *
 * @author hzh
 */
public interface UserOrgMapService extends IService<UserOrgMapEntity> {

    /**
    *  分页查询
    * @param pageRequest
    * @return
    */
    BusinessPageResult queryPage(UserOrgMapEntityPageRequest pageRequest);

    /**
     * 获取用户组织列表，通过组织id列表
     * @param orgIds    组织id列表
     * @return  用户组织关系列表
     */
    List<UserOrgMapEntity> getUserOrgByOrgIds(Set<String> orgIds);

    /**
     * 从用户组织列表中获取用户id集合，用户组织id列表
     * @param orgIds    组织id列表
     * @return  用户id集合
     */
    Set<String> getUserIdsByOrgIds(Set<String> orgIds);

    /**
     * 获取组织列表，通过userId集合
     * @param userIds
     * @return
     */
    List<UserOrgMapEntity> getOrgByUserIds(Set<String> userIds);
}

