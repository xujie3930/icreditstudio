package com.jinninghui.datasphere.icreditstudio.workspace.mapper;

import com.jinninghui.datasphere.icreditstudio.workspace.entity.IcreditWorkspaceUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2021-08-23
 */
public interface IcreditWorkspaceUserMapper extends BaseMapper<IcreditWorkspaceUserEntity> {

    List<Map<String, String>> getWorkspaceByUserId(@Param("id") String id);

    List<IcreditWorkspaceUserEntity> getUserListById(String id);

    List<String> getWorkSpaceIdsByUserId(@Param("userId") String userId);
}
