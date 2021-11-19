package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xujie
 * @since 2021-08-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DatasourceDetailResult implements Serializable {

    /**
     * 主键id
     */
    private String id;

    /**
     * 工作空间id
     */
    private String spaceId;
    /**
     * 类别
     */
    private Integer category;

    /**
     * 数据源类型
     */
    private Integer type;

    /**
     * 数据源自定义名称
     */
    private String name;

    /**
     * 连接信息
     */
    private String uri;

    /**
     * 是否启用：0-启用，1-非启用
     */
    private Integer status;

    /**
     * 是否删除:N-否，Y-删除
     */
    private String delFlag;

    /**
     * 最后一次同步时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastSyncTime;

    /**
     * 最后一次同步状态：0-成功，1-失败,2未执行
     */
    private Integer lastSyncStatus;

    /**
     * 描述信息
     */
    private String descriptor;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 是否展示密码：0-隐藏，1-展示，默认0
     */
    private Integer showPassword;

    /**
     * 备注
     */
    private String remark;
}
