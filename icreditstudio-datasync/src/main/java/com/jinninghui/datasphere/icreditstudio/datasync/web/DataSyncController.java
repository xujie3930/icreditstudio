package com.jinninghui.datasphere.icreditstudio.datasync.web;


import com.jinninghui.datasphere.icreditstudio.datasync.service.result.*;
import com.jinninghui.datasphere.icreditstudio.datasync.web.request.*;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Peng
 */
@RestController
@RequestMapping("/data/sync")
public class DataSyncController {

    /**
     * 同步任务定义、同步任务构建、同步任务调度保存
     *
     * @param request
     * @return
     */
    @PostMapping("/save")
    public BusinessResult<ImmutablePair<String, String>> save(@RequestBody DataSyncSaveRequest request) {
        return null;
    }

    /**
     * 数据源目录
     *
     * @return
     */
    @PostMapping("/getDatasourceCatalogue")
    public BusinessResult<List<DatasourceCatalogue>> getDatasourceCatalogue(@RequestBody DataSyncQueryDatasourceCatalogueRequest request) {
        return null;
    }

    /**
     * 生成宽表
     *
     * @return
     */
    @PostMapping("/generateWideTable")
    public BusinessResult<WideTable> generateWideTable(@RequestBody DataSyncGenerateWideTableRequest request) {
        return null;
    }

    /**
     * 目标源列表
     *
     * @return
     */
    @PostMapping("/targetSources")
    public BusinessResult<List<DataSource>> targetSources(@RequestBody DataSyncQueryTargetSourceRequest request) {
        return null;
    }

    /**
     * 关联字典
     *
     * @return
     */
    @PostMapping("/associatedDict")
    public BusinessResult<List<Dict>> associatedDict(@RequestBody DataSyncQueryDictRequest request) {
        return null;
    }

    /**
     * 同步任务列表
     *
     * @return
     */
    @PostMapping("/syncTasks")
    public BusinessResult<List<SyncTaskInfo>> syncTasks(@RequestBody DataSyncQueryRequest request) {
        return null;
    }

    /**
     * 任务详情
     *
     * @return
     */
    /*@PostMapping("/detailTask")
    public BusinessResult<> detailTask() {

    }*/

    /**
     * 同步任务定义信息
     *
     * @return
     */
    @PostMapping("/taskDefineInfo")
    public BusinessResult<TaskDefineInfo> taskDefineInfo() {
        return null;
    }

    /**
     * 同步任务构建信息
     *
     * @return
     */
    @PostMapping("/taskBuildInfo")
    public BusinessResult<TaskBuildInfo> taskBuildInfo() {
        return null;
    }

    /**
     * 同步任务调度信息
     *
     * @return
     */
    @PostMapping("/taskScheduleInfo")
    public BusinessResult<TaskScheduleInfo> taskScheduleInfo() {
        return null;
    }

    /**
     * 任务停用
     *
     * @return
     */
    @PostMapping("/stop")
    public BusinessResult<Boolean> stop() {
        return null;
    }

    /**
     * 任务删除
     *
     * @return
     */
    @PostMapping("/remove")
    public BusinessResult<Boolean> remove() {
        return null;
    }

    /**
     * 任务启用
     *
     * @return
     */
    @PostMapping("/enable")
    public BusinessResult<Boolean> enable() {
        return null;
    }
}
