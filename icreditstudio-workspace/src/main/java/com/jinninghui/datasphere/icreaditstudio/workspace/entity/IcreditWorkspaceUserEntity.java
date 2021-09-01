package com.jinninghui.datasphere.icreaditstudio.workspace.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author xujie
 * @since 2021-08-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("icredit_workspace_user")
public class IcreditWorkspaceUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SPACE_ID = "SPACE_ID";

    /**
     * 主键id
     */
    private String id;

    /**
     * 工作空间id
     */
    private String spaceId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户角色
     */
    private String userRole;

    /**
     * 功能权限
     */
    private String functionalAuthority;

    /**
     * 数据权限
     */
    private String dataAuthority;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 创建人
     */
    private String updateUser;

    /**
     * 创建人
     */
    private String remark;


}
