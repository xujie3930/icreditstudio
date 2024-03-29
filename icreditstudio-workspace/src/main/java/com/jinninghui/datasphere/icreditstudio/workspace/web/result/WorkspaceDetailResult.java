package com.jinninghui.datasphere.icreditstudio.workspace.web.result;

import com.jinninghui.datasphere.icreditstudio.workspace.web.request.WorkspaceMember;
import lombok.Data;

import java.util.List;

/**
 * @author xujie
 * @description 工作空间详情返回前端result
 * @create 2021-08-31 15:19
 **/
@Data
public class WorkspaceDetailResult {

    /**
     * 主键id
     */
    private String id;

    /**
     * 空间状态
     */
    private Integer status;

    /**
     * 删除标识
     */
    private String delFlag;

    /**
     * 空间描述
     */
    private String descriptor;

    /**
     * 负责人
     */
    private String director;

    /**
     * 空间名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 工作空间成员列表
     */
    private List<WorkspaceMember> memberList;
}
