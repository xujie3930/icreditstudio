package com.jinninghui.datasphere.icreditstudio.modules.system.role.web.result;

import com.jinninghui.datasphere.icreditstudio.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserEntity;
import lombok.Data;

import java.util.List;

/**
 * Created by Pengpai on 2021/5/19 13:18
 */
@Data
public class RoleConfigUsersResult {
    /**
     * 组织列表
     */
    private List<OrganizationEntity> orgs;
    /**
     * 用户列表
     */
    private List<UserEntity> users;
}
