package com.jinninghui.datasphere.icreditstudio.datasync.web;

import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.Associated;
import com.jinninghui.datasphere.icreditstudio.datasync.service.SyncTaskService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.*;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.*;
import com.jinninghui.datasphere.icreditstudio.datasync.service.time.SyncTimeInterval;
import com.jinninghui.datasphere.icreditstudio.datasync.service.time.TimeInterval;
import com.jinninghui.datasphere.icreditstudio.datasync.web.request.*;
import com.jinninghui.datasphere.icreditstudio.framework.log.Logable;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

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
    @Logable
    @PostMapping("/stop")
    public BusinessResult<Boolean> stop(@RequestBody DataSyncExecRequest request) {
        DataSyncExecParam param = new DataSyncExecParam();
        BeanCopyUtils.copyProperties(request, param);
        return syncTaskService.stop(param);
    }

    /**
     * 任务删除
     *
     * @return
     */
    @Logable
    @PostMapping("/remove")
    public BusinessResult<Boolean> remove(@RequestBody DataSyncExecRequest request) {
        DataSyncExecParam param = new DataSyncExecParam();
        BeanCopyUtils.copyProperties(request, param);
        return syncTaskService.remove(param);
    }

    /**
     * 任务启用
     *
     * @return
     */
    @PostMapping("/enable")
    public BusinessResult<Boolean> enable(@RequestBody DataSyncExecRequest request) {
        DataSyncExecParam param = new DataSyncExecParam();
        BeanCopyUtils.copyProperties(request, param);
        return syncTaskService.enable(param);
    }

    @Logable
    @PostMapping("/run")
    public BusinessResult<Boolean> run(@RequestBody DataSyncExecRequest request) {
        DataSyncExecParam param = new DataSyncExecParam();
        BeanCopyUtils.copyProperties(request, param);
        return syncTaskService.run(param);
    }

    @PostMapping("/cease")
    public BusinessResult<Boolean> cease(@RequestBody DataSyncExecRequest request) {
        DataSyncExecParam param = new DataSyncExecParam();
        BeanCopyUtils.copyProperties(request, param);
        return syncTaskService.cease(param);
    }

    @PostMapping("/test")
    public BusinessResult<SyncTimeInterval> test(@RequestBody SyncCondition condition) {
        TimeInterval interval = new TimeInterval();
        SyncTimeInterval timeInterval = interval.getSyncTimeInterval(condition, n -> true);
        System.out.println(timeInterval);
        return BusinessResult.success(timeInterval);
    }

    @PostMapping("/dispatchPage")
    public BusinessResult<BusinessPageResult<DataSyncDispatchTaskPageResult>> dispatchPage(@RequestBody DataSyncDispatchTaskPageRequest dispatchPageRequest){
        DataSyncDispatchTaskPageParam param = new DataSyncDispatchTaskPageParam();
        BeanCopyUtils.copyProperties(dispatchPageRequest, param);
        if(null != dispatchPageRequest.getDispatchStartTime()){
            param.setDispatchStartTime(new Date(dispatchPageRequest.getDispatchStartTime()));
        }
        if(null != dispatchPageRequest.getDispatchEndTime()) {
            param.setDispatchEndTime(new Date(dispatchPageRequest.getDispatchEndTime()));
        }
        return syncTaskService.dispatchPage(param);
    }

    @GetMapping("/getProcessDefinitionId")
    public String getProcessDefinitionIdById(@RequestParam("taskId") String taskId){
        return syncTaskService.getProcessDefinitionIdById(taskId);
    }

}
