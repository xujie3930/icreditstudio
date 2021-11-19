package com.jinninghui.datasphere.icreditstudio.workspace.web.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xujie
 * @description 工作空间工作台
 * @create 2021-11-17 14:36
 **/
@Data
public class WorkBenchResult implements Serializable {

    /**
     * 未运行任务
     */
    private Long notRun;

    /**
     * 正在执行任务
     */
    private Long running;

    /**
     * 运行失败
     */
    private Long failure;

    /**
     * 运行成功
     */
    private Long success;
}