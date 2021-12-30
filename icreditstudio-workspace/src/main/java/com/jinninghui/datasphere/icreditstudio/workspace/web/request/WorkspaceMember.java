package com.jinninghui.datasphere.icreditstudio.workspace.web.request;

import lombok.Data;

import java.util.List;

/**
 * @author xujie
 * @description 工作空间成员入参
 * @create 2021-08-31 14:35
 **/
@Data
public class WorkspaceMember {
    private String userId;//用户id
    private String username;//用户名称
    private List<String> userRole;//用户角色
    private List<String> orgNames;//用户部门
    private List<String> functionalAuthority;//功能权限
    private List<String> dataAuthority;//数据权限
    private String tenantCode;//用户账号
    private Long createTime;//创建时间
}
