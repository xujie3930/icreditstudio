package com.jinninghui.datasphere.icreditstudio.system.modules.system.role.web.request;

import lombok.Data;

@Data
public class RoleResourcesMapRequest {

    private String roleId;

    private String[] resourceIds;
}
