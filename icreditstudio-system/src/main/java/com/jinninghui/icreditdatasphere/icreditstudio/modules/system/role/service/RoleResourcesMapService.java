package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.service.result.RoleResourcesMapEntityResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.web.request.RoleResourcesMapParam;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.entity.RoleResourcesMapEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.web.request.RoleResourcesMapEntityPageRequest;
import com.hashtech.businessframework.result.BusinessResult;

import java.util.List;
import java.util.Set;

/**
 * 
 *
 * @author hzh
 */
public interface RoleResourcesMapService extends IService<RoleResourcesMapEntity> {

    /**
    *  分页查询
    * @param pageRequest
    * @return
    */
    BusinessPageResult queryPage(RoleResourcesMapEntityPageRequest pageRequest);

    BusinessResult<List<RoleResourcesMapEntityResult>> resource(RoleResourcesMapParam param);

    List<RoleResourcesMapEntity> findByRoleIds(Set<String> roleIds);

    Set<String> findResourcesIdsByRoleIds(Set<String> roleIds);

    List<RoleResourcesMapEntity> findByResourcesIds(Set<String> resourcesIds);

    Set<String> findRoleIdsByResourcesIds(Set<String> resourcesIds);
}

