package com.jinninghui.datasphere.icreditstudio.modules.system.org.web.request;

import com.hashtech.businessframework.result.base.BusinessBasePageForm;
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
