package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.web.result;


import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.entity.OrganizationEntity;
import lombok.Data;

import java.util.List;

/**
 *
 *
 * @author hzh
 */
@Data
public class OrgQueryChildrenResult extends OrganizationEntity {

    private List<OrganizationEntity> children;
}
