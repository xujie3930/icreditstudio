package com.jinninghui.datasphere.icreditstudio.datasync.web;


import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.Associated;
import com.jinninghui.datasphere.icreditstudio.datasync.service.SyncTaskService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.*;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.*;
import com.jinninghui.datasphere.icreditstudio.datasync.web.request.*;
import com.jinninghui.datasphere.icreditstudio.framework.log.Logable;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Peng
 */
@Slf4j
@RestController
@RequestMapping("/datasync")
public class DataSyncController {
    @Resource
    private SyncTaskService syncTaskService;

    /**
     * 同步任务定义、同步任务构建、同步任务调度保存
     *
     * @param request
     * @return
     */
    @Logable
    @PostMapping("/save")
    public BusinessResult<ImmutablePair<String, String>> save(@RequestBody DataSyncSaveRequest request) {
        DataSyncSaveParam param = new DataSyncSaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return syncTaskService.save(param);
    }

    /**
     * 数据源支持的关联类型
     *
     * @param request
     * @return
     */
    @Logable
    @PostMapping("/dialectAssociatedSupport")
    public BusinessResult<Associated> dialectAssociatedSupport(@RequestBody DataSyncDialectSupportRequest request) {
        DataSyncDialectSupportParam param = new DataSyncDialectSupportParam();
        BeanCopyUtils.copyProperties(request, param);
        return syncTaskService.dialectAssociatedSupport(param);
    }

    /**
     * 数据源目录
     *
     * @return
     */
    @Logable
    @PostMapping("/getDatasourceCatalogue")
    public BusinessResult<List<DatasourceCatalogue>> getDatasourceCatalogue(@RequestBody DataSyncQueryDatasourceCatalogueRequest request) {
        List<DatasourceCatalogue> results = Lists.newArrayList();
        DatasourceCatalogue dc = new DatasourceCatalogue();
        dc.setName("mysql");
        dc.setDialect("mysql");
        dc.setUrl("jdbc:mysql://localhost:3306/datasync?allowMultiQueries=true&useSSL=false&useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true");
        dc.setDatasourceId("1000000");

        DatasourceCatalogue dc1 = new DatasourceCatalogue();
        dc1.setName("hive");
        dc1.setDialect("hive");
        dc1.setUrl("jdbc:mysql://localhost:3306/datasync");
        dc.setContent(Lists.newArrayList(dc1));
        results.add(dc);
        return BusinessResult.success(results);
    }

    /**
     * 生成宽表
     *
     * @return
     */
    @Logable
    @PostMapping("/generateWideTable")
    public BusinessResult<WideTable> generateWideTable(@RequestBody DataSyncGenerateWideTableRequest request) {
        DataSyncGenerateWideTableParam param = new DataSyncGenerateWideTableParam();
        BeanCopyUtils.copyProperties(request, param);
        return syncTaskService.generateWideTable(param);
    }

    /**
     * 目标源列表
     *
     * @return
     */
    @Logable
    @PostMapping("/targetSources")
    public BusinessResult<List<DataSource>> targetSources(@RequestBody DataSyncQueryTargetSourceRequest request) {
        List<DataSource> results = Lists.newArrayList();
        DataSource ds = new DataSource();
        ds.setName("data");
        ds.setUrl("jdbc://");
        ds.setId("");
        results.add(ds);
        return BusinessResult.success(results);
    }

    /**
     * 关联字典
     *
     * @return
     */
    @Logable
    @PostMapping("/associatedDict")
    public BusinessResult<List<Dict>> associatedDict(@RequestBody DataSyncQueryDictRequest request) {
        List<Dict> results = Lists.newArrayList();
        Dict dict = new Dict();
        dict.setName("性别");
        dict.setKey("grand");
        dict.setType("nan");
        results.add(dict);
        return BusinessResult.success(results);
    }

    /**
     * 同步任务列表
     *
     * @return
     */
    @Logable
    @PostMapping("/syncTasks")
    public BusinessResult<BusinessPageResult> syncTasks(@RequestBody DataSyncQueryRequest request) {
        DataSyncQueryParam param = new DataSyncQueryParam();
        BeanCopyUtils.copyProperties(request, param);
        return syncTaskService.syncTasks(param);
    }

    /**
     * 同步任务定义信息
     *
     * @return
     */
    @Logable
    @PostMapping("/taskDefineInfo")
    public BusinessResult<TaskDefineInfo> taskDefineInfo(@RequestBody DataSyncDetailRequest request) {
        DataSyncDetailParam param = new DataSyncDetailParam();
        BeanCopyUtils.copyProperties(request, param);
        return syncTaskService.taskDefineInfo(param);
    }

    /**
     * 同步任务构建信息
     *
     * @return
     */
    @Logable
    @PostMapping("/taskBuildInfo")
    public BusinessResult<TaskBuildInfo> taskBuildInfo(@RequestBody DataSyncDetailRequest request) {
        DataSyncDetailParam param = new DataSyncDetailParam();
        BeanCopyUtils.copyProperties(request, param);
        return syncTaskService.taskBuildInfo(param);
    }

    /**
     * 同步任务调度信息
     *
     * @return
     */
    @Logable
    @PostMapping("/taskScheduleInfo")
    public BusinessResult<TaskScheduleInfo> taskScheduleInfo(@RequestBody DataSyncDetailRequest request) {
        DataSyncDetailParam param = new DataSyncDetailParam();
        BeanCopyUtils.copyProperties(request, param);
        return syncTaskService.taskScheduleInfo(param);
    }

    /**
     * 任务停用
     *
     * @return
     */
    @PostMapping("/stop")
    public BusinessResult<Boolean> stop(@RequestBody DataSyncExecRequest request) {
        return null;
    }

    /**
     * 任务删除
     *
     * @return
     */
    @PostMapping("/remove")
    public BusinessResult<Boolean> remove(@RequestBody DataSyncExecRequest request) {
        return null;
    }

    /**
     * 任务启用
     *
     * @return
     */
    @PostMapping("/enable")
    public BusinessResult<Boolean> enable(@RequestBody DataSyncExecRequest request) {
        return null;
    }
}
