package com.jinninghui.datasphere.icreditstudio.metadata.feign.result;

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
public class IcreditWorkspaceUserResult implements Serializable {

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
     * 用户部门
     */
    private String orgName;

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
     * 描述
     */
    private String remark;

    /**
     * 排序字段，用于展示成员列表的排序，空间的创建者为0
     */
    private Integer sort;
}
