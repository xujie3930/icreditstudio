package com.jinninghui.datasphere.icreditstudio.datasync.feign.request;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Peng
 */
@Data
@AllArgsConstructor
public class PlatformTaskOrdinaryParam {

    private Integer version;
    private Integer taskType;
    private String cron;
    private String targetTable;
    private String sourceTableStr;
    private String workspaceId;
    /**
     * 启用/停用
     */
    private Integer enable;

    private String name;
    /**
     * 所属项目码
     */
    private String projectCode;
    /**
     * 流程定义名称
     */
    private String platformTaskId;
    /**
     * 任务json
     */
    private String taskJson;
    /**
     * 超时时间
     */
    private int timeOut;
}
