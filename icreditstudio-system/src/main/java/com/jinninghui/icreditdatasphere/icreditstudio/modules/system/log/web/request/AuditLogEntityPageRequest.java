package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.log.web.request;

import com.hashtech.businessframework.result.base.BusinessBasePageForm;
import lombok.Data;
/**
 * 
 *
 * @author 1
 */
@Data
public class AuditLogEntityPageRequest extends BusinessBasePageForm {

    private String userId;

    private String oprateInfo;

    private Long startTime;

    private Long endTime;

    private String userName;
}
