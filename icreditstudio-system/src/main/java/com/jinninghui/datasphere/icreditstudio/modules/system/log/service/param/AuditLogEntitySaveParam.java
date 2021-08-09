package com.jinninghui.datasphere.icreditstudio.modules.system.log.service.param;

import lombok.Data;

/**
 * Created by PPai on 2021/6/10 11:33
 */
@Data
public class AuditLogEntitySaveParam {

    private String oprateType;

    private String userId;

    private String userName;

    private Long oprateTime;

    private String oprateResult;

    private String oprateInfo;
}
