package com.jinninghui.datasphere.icreditstudio.system.modules.system.role.service.result;

import lombok.Data;

@Data
public class RoleResourcesMapEntityResult {
    private String id;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 菜单_id
     */
    private String resourcesId;
    /**
     * 创建者id
     */
    private Long createTime;
    /**
     * 创建时间
     */
    private String createUserId;
    /**
     * 更新时间
     */
    private Long lastUpdateTime;
    /**
     * 更新者id
     */
    private String lastUpdateUserId;
}
