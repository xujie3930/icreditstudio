package com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result;

import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.entity.RoleEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity.UserEntity;
import lombok.Data;

import java.util.List;

/**
 * @author hzh
 * @description
 * @date 2021/3/2 18:51
 */
@Data
public class UserEntityAuthResult extends UserEntity {

    List<RoleEntity> roleList;

    List<OrganizationEntity> orgList;

    /**
     * 是否是首次登陆
     */
    private boolean isFirstLogin;

    private String userRole;//用户角色

    private List<String> orgNames;//用户部门

    private String functionalAuthority;//功能权限

    private String dataAuthority;//数据权限

}
