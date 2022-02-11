package com.micro.cloud.modules.process.controller;

import cn.hutool.json.JSONObject;
import com.micro.cloud.api.CommonResult;
import com.micro.cloud.util.file.DateUtils;
import io.swagger.annotations.ApiOperation;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/history")
public class HistoryController {

  @Autowired private HistoryService historyService;

  @RequestMapping("/getAvgTime")
  @ApiOperation("获取流程统计时间")
  public CommonResult avgTime(@RequestHeader("userId") String userId) {
    // 计算本周处理流程平均时间
    //        System.out.println(new SimpleDateFormat("YYYY-MM-dd
    // HH:mm:ss").format(DateUtils.getBeginDayOfWeek(new Date())));
    //        System.out.println(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
    //        System.out.println(new SimpleDateFormat("YYYY-MM-dd
    // HH:mm:ss").format(DateUtils.getEndDayOfWeek(new Date())));
    List<HistoricProcessInstance> historicProcessInstanceList =
        historyService
            .createHistoricProcessInstanceQuery()
            .startedAfter(DateUtils.getBeginDayOfWeek(new Date()))
            .finishedBefore(DateUtils.getEndDayOfWeek(new Date()))
            .list();
    List<Long> times = new ArrayList<>();
    historicProcessInstanceList.forEach(
        h -> {
          times.add(h.getDurationInMillis());
        });
    Double avgTime = new Double(0.0);
    avgTime = times.stream().collect(Collectors.averagingLong(Long::longValue)) / 60000; // 分
    // 本周处理流程个数
    Integer weekTasks = new Integer(0);
    List<HistoricTaskInstance> historicTaskInstanceList =
        historyService
            .createHistoricTaskInstanceQuery()
            .finishedBefore(DateUtils.getEndDayOfWeek(new Date()))
            .finishedAfter(DateUtils.getBeginDayOfWeek(new Date()))
            .list();
    Map<String, String> smap = new HashMap<String, String>();
    historicTaskInstanceList.stream()
        .forEach(
            h -> {
              smap.put(h.getProcessInstanceId(), h.getProcessInstanceId());
            });
    weekTasks = smap.size();
    JSONObject result = new JSONObject();
    //       result.putOnce("avgProTime",avgProTime);
    result.putOnce("avgTaskTime", avgTime);
    result.putOnce("weekTasks", weekTasks);

    return CommonResult.success(result);
  }
}
