package com.jinninghui.datasphere.icreditstudio.modules.system.role.web.request;

import com.hashtech.businessframework.result.base.BusinessBasePageForm;
import lombok.Data;

/**
 *
 *
 * @author hzh
 */
@Data
public class RoleEntityPageRequest extends BusinessBasePageForm {

    private String roleName;

    private String deleteFlag;
}
