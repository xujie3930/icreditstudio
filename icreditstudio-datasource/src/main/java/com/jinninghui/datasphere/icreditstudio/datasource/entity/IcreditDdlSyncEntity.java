package com.jinninghui.datasphere.icreditstudio.datasource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author xujie
 * @since 2021-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("icredit_ddl_sync")
public class IcreditDdlSyncEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String DATASOURCE_ID = "datasource_id";
    /**
     * 主键id
     */
    private String id;

    /**
     * 工作空间id
     */
    private String spaceId;

    /**
     * datasource表的id
     */
    private String datasourceId;

    /**
     * 连接信息
     */
    private String uri;

    /**
     * 表所有信息
     */
    private String columnsInfo;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 是否删除:0-否，1-删除
     */
    private String delFlag;

    /**
     * 备注
     */
    private String remark;


}
