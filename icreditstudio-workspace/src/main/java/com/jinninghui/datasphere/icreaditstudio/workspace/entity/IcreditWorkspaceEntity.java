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
 * @since 2021-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("icredit_workspace")
public class IcreditWorkspaceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String NAME = "NAME";
    public static final String CREATE_USER = "CREATE_USER";
    public static final String CREATE_TIME  = "CREATE_TIME";

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUser;
}
