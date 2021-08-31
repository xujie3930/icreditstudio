package com.jinninghui.datasphere.icreaditstudio.workspace.service;

import com.jinninghui.datasphere.icreaditstudio.workspace.entity.IcreditWorkspaceEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreaditstudio.workspace.service.param.IcreditWorkspaceDelParam;
import com.jinninghui.datasphere.icreaditstudio.workspace.service.param.IcreditWorkspaceSaveParam;
import com.jinninghui.datasphere.icreaditstudio.workspace.web.request.IcreditWorkspaceEntityPageRequest;
import com.jinninghui.datasphere.icreaditstudio.workspace.web.request.WorkspaceHasExistRequest;
import com.jinninghui.datasphere.icreaditstudio.workspace.web.result.WorkspaceDetailResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xujie
 * @since 2021-08-20
 */
public interface IcreditWorkspaceService extends IService<IcreditWorkspaceEntity> {

    BusinessResult<Boolean> saveDef(IcreditWorkspaceSaveParam param);

    BusinessResult<Boolean> deleteById(IcreditWorkspaceDelParam param);

    BusinessPageResult queryPage(IcreditWorkspaceEntityPageRequest pageRequest);

    BusinessResult<Boolean> hasExit(WorkspaceHasExistRequest request);

    WorkspaceDetailResult getDetailById(String id);
}
