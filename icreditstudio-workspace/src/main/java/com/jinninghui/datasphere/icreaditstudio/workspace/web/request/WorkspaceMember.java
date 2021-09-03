package com.jinninghui.datasphere.icreaditstudio.workspace.web.request;

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
    private String userRole;//用户角色
    private List<String> orgNames;//用户部门
    private String functionalAuthority;//功能权限
    private String dataAuthority;//数据权限
    private Long createTime;//创建时间
}
