package com.jinninghui.datasphere.icreditstudio.modules.system.role.service.result;

import lombok.Data;

@Data
public class RoleEntityInfoResult {
    /**
     * id
     */
    private String id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 父角色id
     */
    private String parentId;
    /**
     * 排序字段
     */
    private Integer sortNumber;
    /**
     * 角色备注
     */
    private String roleRemark;

    /**
     * 删除标志 Y.已删除 N.未删除
     */
    private String deleteFlag;
    /**
     *
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
