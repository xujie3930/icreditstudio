package com.jinninghui.datasphere.icreditstudio.modules.system.role.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Created by Pengpai on 2021/5/24 10:09
 */
@Data
public class RoleEntityQueryRequest {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 父角色id
     */
    private String parentId;
    /**
     * 角色名称
     */
    @Length(max = 20, message = "50009002")
    private String roleName;
    /**
     * 启用标识
     */
    private String deleteFlag;
    /**
     * 是否展开
     */
    private boolean lazy;
}
