package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.result;

import lombok.Data;

import java.util.List;

/**
 * @author xujie
 * @description
 * @date 2021/8/31 16:23
 */
@Data
public class LikeQueryUserRoleListResult {

    private String id;

    private String name;

    private String roleId;

    private String userRole;//用户角色

    private List<String> orgNames;//用户部门

    private String functionalAuthority;//功能权限

    private String dataAuthority;//数据权限
}
