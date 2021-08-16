package com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.service.param.ResEntityDelParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.service.param.ResourcesEntityQueryParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.service.param.ResourcesEntitySaveParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.service.param.RolesToResourceSaveParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.service.result.ResourcesEntityResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.entity.ResourcesEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.request.ResourcesEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.request.ResourcesQueryParams;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.request.ResourcesQueryRoleParams;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.result.ResourcesEntityExport;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.entity.RoleEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author hzh
 */
public interface ResourcesService extends IService<ResourcesEntity> {

    /**
     * 分页查询
     *
     * @param pageRequest
     * @return
     */
    BusinessPageResult queryPage(ResourcesEntityPageRequest pageRequest);

    List<ResourcesEntity> getMenuInfoByRoleIds(ResourcesQueryParams request);

    BusinessResult<?> exportExcel(HttpServletRequest request, HttpServletResponse response, ResourcesEntity resources);


    BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response, Class<ResourcesEntityExport> resourcesEntityExportClass);


    List<RoleEntity> getRoleInfoByMenuIds(ResourcesQueryRoleParams request);

    BusinessResult<List<ResourcesEntityResult>> queryList(ResourcesEntityQueryParam param);

    BusinessResult<Boolean> setRolesToResource(RolesToResourceSaveParam param);

    BusinessResult<Boolean> save(ResourcesEntitySaveParam param);

    BusinessResult<ResourcesEntityResult> edit(ResourcesEntitySaveParam param);

    BusinessResult<Boolean> delete(ResEntityDelParam param);

    Optional<List<ResourcesEntity>> findResourcesByRoleIds(Set<String> roleIds);
}

