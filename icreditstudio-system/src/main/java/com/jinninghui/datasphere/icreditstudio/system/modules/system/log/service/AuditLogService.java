package com.jinninghui.datasphere.icreditstudio.system.modules.system.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.log.entity.AuditLogEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.log.service.param.AuditLogEntitySaveParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.log.web.request.AuditLogEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;

/**
 * @author 1
 */
public interface AuditLogService extends IService<AuditLogEntity> {

    /**
     * 分页查询
     *
     * @param pageRequest
     * @return
     */
    BusinessPageResult queryPage(AuditLogEntityPageRequest pageRequest);

    AuditLogEntity log(AuditLogEntitySaveParam param);
}

