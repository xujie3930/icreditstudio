package com.jinninghui.datasphere.icreditstudio.modules.system.allinterface.result;

import com.jinninghui.datasphere.icreditstudio.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.entity.RoleEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserEntity;
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

}
