package com.jinninghui.datasphere.icreditstudio.system.modules.system.role.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
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
