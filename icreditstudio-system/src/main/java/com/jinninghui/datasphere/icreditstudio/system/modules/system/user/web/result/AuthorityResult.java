package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.result;

import lombok.Data;

import java.util.List;

/**
 * @author xujie
 * @description:包含角色列表，功能权限，数据权限
 * @create 2021-12-21 15:47
 **/
@Data
public class AuthorityResult {
    private List<String> userRole;//用户角色

    private List<String> functionalAuthority;//功能权限

    private List<String> dataAuthority;//数据权限
}
