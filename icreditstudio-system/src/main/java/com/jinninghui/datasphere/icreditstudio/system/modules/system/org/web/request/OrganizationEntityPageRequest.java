package com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.request;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

/**
 *
 *
 * @author hzh
 */
@Data
public class OrganizationEntityPageRequest extends BusinessBasePageForm {

    private String parentId;

    private String orgName;

    private String linkManName;

    private String deleteFlag;
}
