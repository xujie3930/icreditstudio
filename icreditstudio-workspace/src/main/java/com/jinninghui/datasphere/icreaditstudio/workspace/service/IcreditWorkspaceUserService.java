package com.jinninghui.datasphere.icreaditstudio.workspace.service;

import com.jinninghui.datasphere.icreaditstudio.workspace.entity.IcreditWorkspaceUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreaditstudio.workspace.web.request.IcreditWorkspaceUserEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xujie
 * @since 2021-08-23
 */
public interface IcreditWorkspaceUserService extends IService<IcreditWorkspaceUserEntity> {

    BusinessPageResult queryPage(IcreditWorkspaceUserEntityPageRequest pageRequest);
}
