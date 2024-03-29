package com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.entity.ResourcesEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.request.ResourcesQueryParams;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.request.ResourcesQueryRoleParams;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.result.ResourcesEntityExport;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author hzh
 */
@Mapper
public interface ResourcesDao extends BaseMapper<ResourcesEntity> {

    List<ResourcesEntity> getMenuInfoByRoleIds(ResourcesQueryParams request);


    List<ResourcesEntityExport> queryInfoByName(ResourcesEntity resources);


    List<RoleEntity> getRoleInfoByMenuIds(ResourcesQueryRoleParams request);

}
