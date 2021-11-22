/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.service.quartz;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.enums.CommandType;
import org.apache.dolphinscheduler.common.enums.ReleaseState;
import org.apache.dolphinscheduler.common.model.Configuration;
import org.apache.dolphinscheduler.dao.entity.Command;
import org.apache.dolphinscheduler.dao.entity.ProcessDefinition;
import org.apache.dolphinscheduler.dao.entity.Schedule;
import org.apache.dolphinscheduler.service.bean.SpringApplicationContext;
import org.apache.dolphinscheduler.service.increment.IncrementUtil;
import org.apache.dolphinscheduler.service.process.ProcessService;
import org.apache.dolphinscheduler.service.time.SyncTimeInterval;
import org.apache.dolphinscheduler.service.time.TimeInterval;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Objects;

/**
 * process schedule job
 */
@Slf4j
public class ProcessScheduleJob implements Job {

    /**
     * logger of ProcessScheduleJob
     */
    private static final Logger logger = LoggerFactory.getLogger(ProcessScheduleJob.class);

    public ProcessService getProcessService() {
        return SpringApplicationContext.getBean(ProcessService.class);
    }

    /**
     * Called by the Scheduler when a Trigger fires that is associated with the Job
     *
     * @param context JobExecutionContext
     * @throws JobExecutionException if there is an exception while executing the job.
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Assert.notNull(getProcessService(), "please call init() method first");

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String projectId = String.valueOf(dataMap.get(Constants.PROJECT_ID));
        String scheduleId = String.valueOf(dataMap.get(Constants.SCHEDULE_ID));

        Date scheduledFireTime = context.getScheduledFireTime();

        Date fireTime = context.getFireTime();

        logger.info("scheduled fire time :{}, fire time :{}, process id :{}", scheduledFireTime, fireTime, scheduleId);

        // query schedule
        Schedule schedule = getProcessService().querySchedule(scheduleId);
        if (schedule == null) {
            logger.warn("process schedule does not exist in db，delete schedule job in quartz, projectId:{}, scheduleId:{}", projectId, scheduleId);
            deleteJob(projectId, scheduleId);
            return;
        }

        ProcessDefinition processDefinition = getProcessService().findProcessDefineById(schedule.getProcessDefinitionId());
        // release state : online/offline
        ReleaseState releaseState = processDefinition.getReleaseState();
        if (releaseState == ReleaseState.OFFLINE) {
            logger.warn("process definition does not exist in db or offline，need not to create command, projectId:{}, processId:{}", projectId, scheduleId);
            return;
        }

        releaseState = schedule.getReleaseState();
        if (releaseState == ReleaseState.OFFLINE) {
            logger.warn("定时任务是下线状态，不能创建command, projectId:{}, processId:{}", projectId, scheduleId);
            return;
        }

        //根据分区条件确定是否创建分区路径和添加同步过滤条件
        String partitionParam = processDefinition.getPartitionParam();
        if (StringUtils.isNotBlank(partitionParam)) {
            PlatformPartitionParam platformPartitionParam = IncrementUtil.parseSyncConditionJson(partitionParam);
//            PlatformPartitionParam platformPartitionParam = JSONObject.parseObject(partitionParam).toJavaObject(PlatformPartitionParam.class);
            //增量字段不为空，则增量同步
            if (platformPartitionParam != null && StringUtils.isNotBlank(platformPartitionParam.getIncrementalField())) {
                String processDefinitionJson = processDefinition.getProcessDefinitionJson();
                TimeInterval interval = new TimeInterval();
                SyncTimeInterval syncTimeInterval = interval.getSyncTimeInterval(platformPartitionParam, n -> true);
                //增量存储开启则创建分区
                String partitionDir = null;
                if (platformPartitionParam.getInc()) {
                    Object value = getValue(processDefinitionJson, PATH);
                    log.info("路径:" + JSONObject.toJSONString(value));
                    if (Objects.nonNull(value)) {
                        String timeFormat = syncTimeInterval.getTimeFormat();
                        String dataxHdfsPath = IncrementUtil.getDataxHdfsPath(value.toString(), timeFormat);
                        log.info("处理后路径:" + dataxHdfsPath);
                        partitionDir = dataxHdfsPath;
                    }
                }
                Object value = getValue(processDefinitionJson, QUERY_SQL);
                log.info("查询SQL:" + JSONObject.toJSONString(value));
                if (Objects.nonNull(value)) {
                    String startTime = syncTimeInterval.formatStartTime();
                    String endTime = syncTimeInterval.formatEndTime();
                    //TODO 只处理mysql类型，后续增加
                    String mysql = IncrementUtil.getTimeIncQueryStatement(value.toString(), "mysql", platformPartitionParam.getIncrementalField(), startTime, endTime);
                    //替换查询语句
                    Configuration configuration = setValue(processDefinitionJson, QUERY_SQL, mysql);
                    //分区不为空给,替换path
                    if (StringUtils.isNotBlank(partitionDir)) {
                        configuration = setValue(configuration.toJSON(), PATH, partitionDir);
                    }
                    log.info("处理后的json语句:" + configuration.toJSON());
                    getProcessService().updateProcessDefinitionById(processDefinition.getId(), configuration.toJSON());
                }
            }
        }
        Command command = new Command();
        command.setCommandType(CommandType.SCHEDULER);
        command.setExecutorId(schedule.getUserId());
        command.setFailureStrategy(schedule.getFailureStrategy());
        command.setProcessDefinitionId(schedule.getProcessDefinitionId());
        command.setScheduleTime(scheduledFireTime);
        command.setStartTime(fireTime);
        command.setWarningGroupId(schedule.getWarningGroupId());
        String workerGroup = StringUtils.isEmpty(schedule.getWorkerGroup()) ? Constants.DEFAULT_WORKER_GROUP : schedule.getWorkerGroup();
        command.setWorkerGroup(workerGroup);
        command.setWarningType(schedule.getWarningType());
        command.setProcessInstancePriority(schedule.getProcessInstancePriority());

        getProcessService().createCommand(command);
    }

    /**
     * delete job
     */
    private void deleteJob(String projectId, String scheduleId) {
        String jobName = QuartzExecutors.buildJobName(scheduleId);
        String jobGroupName = QuartzExecutors.buildJobGroupName(projectId);
        QuartzExecutors.getInstance().deleteJob(jobName, jobGroupName);
    }

    public static void main(String[] args) {
        String json = ("{\"globalParams\":[],\"tasks\":[{\"conditionResult\":{\"failedNode\":[],\"successNode\":[]},\"dependence\":{},\"description\":\"\",\"id\":\"tasks-94508\",\"maxRetryTimes\":\"0\",\"name\":\"bigdata\",\"params\":{\"customConfig\":1,\"json\":\"{\\\"content\\\":[{\\\"reader\\\":{\\\"parameter\\\":{\\\"password\\\":\\\"thgc@0305\\\",\\\"transferDict\\\":[],\\\"needTransferColumns\\\":{},\\\"connection\\\":[{\\\"querySql\\\":[\\\"select * from thgc_old.ge_user\\\"],\\\"jdbcUrl\\\":[\\\"jdbc:mysql://192.168.0.23:3306/thgc_old?useSSL=false&useUnicode=true&characterEncoding=utf8\\\"]}],\\\"username\\\":\\\"thgc\\\"},\\\"name\\\":\\\"mysqlreader\\\"},\\\"writer\\\":{\\\"parameter\\\":{\\\"fileName\\\":\\\"widthtable_20211109_bigdata\\\",\\\"compress\\\":\\\"NONE\\\",\\\"column\\\":[{\\\"name\\\":\\\"ID\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"USER_NAME\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"USER_CODE\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"USER_GENDER\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"USER_BIRTH\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"ID_CARD\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"ENTRY_TIME\\\",\\\"type\\\":\\\"DATE\\\"},{\\\"name\\\":\\\"DEPARTURE_TIME\\\",\\\"type\\\":\\\"DATE\\\"},{\\\"name\\\":\\\"E_MAIL\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"USER_POSITION\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"TEL_PHONE\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"SORT_NUMBER\\\",\\\"type\\\":\\\"INT\\\"},{\\\"name\\\":\\\"DELETE_FLAG\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"PICTURE_PATH\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"USER_REMARK\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"CREATE_TIME\\\",\\\"type\\\":\\\"BIGINT\\\"},{\\\"name\\\":\\\"CREATE_USER_ID\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"LAST_UPDATE_TIME\\\",\\\"type\\\":\\\"BIGINT\\\"},{\\\"name\\\":\\\"LAST_UPDATE_USER_ID\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"EXTEND_ONE\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"EXTEND_TWO\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"EXTEND_THREE\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"EXTEND_FOUR\\\",\\\"type\\\":\\\"STRING\\\"}],\\\"writeMode\\\":\\\"append\\\",\\\"fieldDelimiter\\\":\\\",\\\",\\\"path\\\":\\\"/user/hive/warehouse/hive_test.db/widthtable_20211109_bigdata/\\\",\\\"password\\\":\\\"bd@0414\\\",\\\"partition\\\":\\\"\\\",\\\"thriftUrl\\\":\\\"jdbc:hive2://192.168.0.174:10000/\\\",\\\"hadoopConfig\\\":{\\\"dfs.client.use.datanode.hostname\\\":true},\\\"defaultFS\\\":\\\"hdfs://192.168.0.174:8020\\\",\\\"user\\\":\\\"root\\\",\\\"fileType\\\":\\\"orc\\\"},\\\"name\\\":\\\"hdfswriter\\\"}}],\\\"setting\\\":{\\\"speed\\\":{\\\"channel\\\":1}}}\",\"localParams\":[]},\"preTasks\":[],\"retryInterval\":\"1\",\"runFlag\":\"NORMAL\",\"taskInstancePriority\":\"MEDIUM\",\"timeout\":{\"enable\":false},\"type\":\"DATAX\",\"workerGroup\":\"default\"}],\"tenantCode\":\"autotester\",\"timeout\":0}");
        System.out.println(getValue(json, PATH));
    }


    private static final String QUERY_SQL = "content[0].reader.parameter.connection[0].querySql[0]";

    private static final String PATH = "content[0].writer.parameter.path";

    public static Object getValue(String json, String path) {
        Configuration from = Configuration.from(json);
        Object js = from.get("tasks[0].params.json");
        Configuration content = Configuration.from(JSONUtil.toJsonStr(js));
        return content.get(path);
    }

    public static Configuration setValue(String json, String path, String newValue) {
        Configuration from = Configuration.from(json);
        Object js = from.get("tasks[0].params.json");
        Configuration content = Configuration.from(JSONUtil.toJsonStr(js));
        content.set(path, newValue);

        from.set("tasks[0].params.json", content.toJSON());
        return from;
    }
}
