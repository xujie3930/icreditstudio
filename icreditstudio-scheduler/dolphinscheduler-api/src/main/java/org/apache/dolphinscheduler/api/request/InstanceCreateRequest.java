package org.apache.dolphinscheduler.api.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xujie
 * @description 创建工作流定义及实例
 * @create 2021-09-24 13:56
 **/
@Data
public class InstanceCreateRequest {

    /**
     * 工作空间id
     */
    private String workspaceId;
    /**
     * 租户
     */
    private String tenantCode;

    /**
     * 目标库
     */
    private String targetDatabase;

    /**
     * 目标表
     */
    private String targetTable;

    /**
     * 目标库类型
     */
    private String dtType;

    /**
     * 源库类型
     */
    private String dsType;

    /**
     * 源库用户名
     */
    private String username;

    /**
     * 源库密码
     */
    private String password;

    /**
     * 源库密码
     */
    private List<Object> fields = new ArrayList<>();

    /**
     *源库uri
     */
    private String sourceUri;

    /**
     * 源库sql
     */
    private String sql;

    /**
     * 定时cron表达式
     */
    private String scheduler;

    /**
     * 定时开始时间
     */
    private String startTime;

    /**
     * 定时结束时间
     */
    private String endTime;
}
