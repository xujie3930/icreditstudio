package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity.UserRoleMapEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author hzh
 */
@Mapper
public interface UserRoleMapDao extends BaseMapper<UserRoleMapEntity> {

    List<Map<String, String>> getRoleIds(@Param("ids") Set<String> ids);
}
