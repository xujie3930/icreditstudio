package com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.result;

import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.entity.OrganizationEntity;
import lombok.Data;

@Data
public class OrganizationEntityResult extends OrganizationEntity {

    private String operateFlag;

    private boolean currOrg;
}
