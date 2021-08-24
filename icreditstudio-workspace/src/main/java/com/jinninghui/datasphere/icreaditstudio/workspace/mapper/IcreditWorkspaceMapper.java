package com.jinninghui.datasphere.icreaditstudio.workspace.mapper;

import com.jinninghui.datasphere.icreaditstudio.workspace.entity.IcreditWorkspaceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.datasphere.icreaditstudio.workspace.web.request.WorkspaceHasExistRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2021-08-20
 */
@Mapper
public interface IcreditWorkspaceMapper extends BaseMapper<IcreditWorkspaceEntity> {

    void updateStatusById(@Param("id") String id);

    Boolean hasExit(WorkspaceHasExistRequest request);
}
