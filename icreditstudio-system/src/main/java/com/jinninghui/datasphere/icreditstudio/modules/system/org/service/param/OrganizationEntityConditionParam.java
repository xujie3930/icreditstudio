package com.jinninghui.datasphere.icreditstudio.modules.system.org.service.param;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class OrganizationEntityConditionParam {
    private Set<String> ids;

    private String orgName;

    private String linkManName;

    private String deleteFlag;

    private String orgCode;
}
