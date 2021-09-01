package com.jinninghui.datasphere.icreaditstudio.workspace.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinninghui.datasphere.icreaditstudio.workspace.entity.IcreditWorkspaceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.datasphere.icreaditstudio.workspace.web.request.IcreditWorkspaceEntityPageRequest;
import com.jinninghui.datasphere.icreaditstudio.workspace.web.request.WorkspaceHasExistRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2021-08-20
 */
@Mapper
public interface IcreditWorkspaceMapper extends BaseMapper<IcreditWorkspaceEntity> {

    void updateStatusById(@Param("id") String id);

    Boolean hasExit(WorkspaceHasExistRequest request);

    List<IcreditWorkspaceEntity> queryPage(Page<IcreditWorkspaceEntity> page, @Param(value = "request") IcreditWorkspaceEntityPageRequest request);
}
