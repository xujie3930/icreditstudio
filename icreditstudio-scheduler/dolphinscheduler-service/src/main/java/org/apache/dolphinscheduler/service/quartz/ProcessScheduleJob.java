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
import lombok.extern.slf4j.Slf4j;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.enums.CommandType;
import org.apache.dolphinscheduler.common.enums.ReleaseState;
import org.apache.dolphinscheduler.common.model.Configuration;
import org.apache.dolphinscheduler.dao.entity.Command;
import org.apache.dolphinscheduler.dao.entity.ProcessDefinition;
import org.apache.dolphinscheduler.dao.entity.Schedule;
import org.apache.dolphinscheduler.service.bean.SpringApplicationContext;
import org.apache.dolphinscheduler.service.process.ProcessService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;

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

        String processDefinitionJson = processDefinition.getProcessDefinitionJson();
        log.info("processDefinitionJson" +processDefinitionJson);
        /*String processDefinitionJson = processDefinition.getProcessDefinitionJson();
        ProcessData processData = JSONUtils.parseObject(JSONObject.toJSONString(processDefinitionJson), ProcessData.class);
        List<TaskNode> tasks = processData.getTasks();
        for (TaskNode task : tasks) {
            String params = task.getParams();
            log.info("datax执行脚本:" + params);
        }*/

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
