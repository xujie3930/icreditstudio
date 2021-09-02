package com.jinninghui.datasphere.icreditstudio.system.modules.system.log.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

/**
 * @author 1
 */
@Data
public class LoginLogEntityPageRequest extends BusinessBasePageForm {

    private String userAccount;

    private String userName;

    private Long startTime;

    private Long endTime;

}
