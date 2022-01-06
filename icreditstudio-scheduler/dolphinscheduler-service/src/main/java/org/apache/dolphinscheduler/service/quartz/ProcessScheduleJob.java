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
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.enums.CommandType;
import org.apache.dolphinscheduler.common.enums.ReleaseState;
import org.apache.dolphinscheduler.common.model.Configuration;
import org.apache.dolphinscheduler.dao.entity.Command;
import org.apache.dolphinscheduler.dao.entity.ProcessDefinition;
import org.apache.dolphinscheduler.dao.entity.Schedule;
import org.apache.dolphinscheduler.service.bean.SpringApplicationContext;
import org.apache.dolphinscheduler.service.handler.ProcessDefinitionJsonHandler;
import org.apache.dolphinscheduler.service.handler.ProcessDefinitionJsonHandlerContainer;
import org.apache.dolphinscheduler.service.increment.IncrementUtil;
import org.apache.dolphinscheduler.service.process.ProcessService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
            //增量字段不为空，则增量同步
            if (platformPartitionParam != null && StringUtils.isNotBlank(platformPartitionParam.getIncrementalField())) {
                String dialect = platformPartitionParam.getDialect();
                if (StringUtils.isBlank(dialect)) {
                    logger.warn("周期定时任务方言类型为空 dialect{}", dialect);
                    return;
                }
                String processDefinitionJson = processDefinition.getProcessDefinitionJson();
                //勾选了增量同步第一次全同步，并且 没有流程实例（是第一次同步），为 true；否则为 false
                platformPartitionParam.setFirstFull(platformPartitionParam.getFirstFull() && null == getProcessService().getLastInstanceByDefinitionId(processDefinition.getId()));
                ProcessDefinitionJsonHandler handler = ProcessDefinitionJsonHandlerContainer.get(dialect);
                String handStatement = handler.handler(platformPartitionParam, processDefinitionJson);
                log.info("处理后的json语句:{}", handStatement);
                handStatement = getProcessService().replaceDictInfo(handStatement);
                log.info("================>>>>>>>替换字典后的json" + handStatement);
                getProcessService().updateProcessDefinitionById(processDefinition.getId(), handStatement);
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
        /*String json = ("{\"globalParams\":[],\"tasks\":[{\"conditionResult\":{\"failedNode\":[],\"successNode\":[]},\"dependence\":{},\"description\":\"\",\"id\":\"tasks-94508\",\"maxRetryTimes\":\"0\",\"name\":\"bigdata\",\"params\":{\"customConfig\":1,\"json\":\"{\\\"content\\\":[{\\\"reader\\\":{\\\"parameter\\\":{\\\"password\\\":\\\"thgc@0305\\\",\\\"transferDict\\\":[],\\\"needTransferColumns\\\":{},\\\"connection\\\":[{\\\"querySql\\\":[\\\"select * from thgc_old.ge_user\\\"],\\\"jdbcUrl\\\":[\\\"jdbc:mysql://192.168.0.23:3306/thgc_old?useSSL=false&useUnicode=true&characterEncoding=utf8\\\"]}],\\\"username\\\":\\\"thgc\\\"},\\\"name\\\":\\\"mysqlreader\\\"},\\\"writer\\\":{\\\"parameter\\\":{\\\"fileName\\\":\\\"widthtable_20211109_bigdata\\\",\\\"compress\\\":\\\"NONE\\\",\\\"column\\\":[{\\\"name\\\":\\\"ID\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"USER_NAME\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"USER_CODE\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"USER_GENDER\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"USER_BIRTH\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"ID_CARD\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"ENTRY_TIME\\\",\\\"type\\\":\\\"DATE\\\"},{\\\"name\\\":\\\"DEPARTURE_TIME\\\",\\\"type\\\":\\\"DATE\\\"},{\\\"name\\\":\\\"E_MAIL\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"USER_POSITION\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"TEL_PHONE\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"SORT_NUMBER\\\",\\\"type\\\":\\\"INT\\\"},{\\\"name\\\":\\\"DELETE_FLAG\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"PICTURE_PATH\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"USER_REMARK\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"CREATE_TIME\\\",\\\"type\\\":\\\"BIGINT\\\"},{\\\"name\\\":\\\"CREATE_USER_ID\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"LAST_UPDATE_TIME\\\",\\\"type\\\":\\\"BIGINT\\\"},{\\\"name\\\":\\\"LAST_UPDATE_USER_ID\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"EXTEND_ONE\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"EXTEND_TWO\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"EXTEND_THREE\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"EXTEND_FOUR\\\",\\\"type\\\":\\\"STRING\\\"}],\\\"writeMode\\\":\\\"append\\\",\\\"fieldDelimiter\\\":\\\",\\\",\\\"path\\\":\\\"/user/hive/warehouse/hive_test.db/widthtable_20211109_bigdata/\\\",\\\"password\\\":\\\"bd@0414\\\",\\\"partition\\\":\\\"\\\",\\\"thriftUrl\\\":\\\"jdbc:hive2://192.168.0.174:10000/\\\",\\\"hadoopConfig\\\":{\\\"dfs.client.use.datanode.hostname\\\":true},\\\"defaultFS\\\":\\\"hdfs://192.168.0.174:8020\\\",\\\"user\\\":\\\"root\\\",\\\"fileType\\\":\\\"orc\\\"},\\\"name\\\":\\\"hdfswriter\\\"}}],\\\"setting\\\":{\\\"speed\\\":{\\\"channel\\\":1}}}\",\"localParams\":[]},\"preTasks\":[],\"retryInterval\":\"1\",\"runFlag\":\"NORMAL\",\"taskInstancePriority\":\"MEDIUM\",\"timeout\":{\"enable\":false},\"type\":\"DATAX\",\"workerGroup\":\"default\"}],\"tenantCode\":\"autotester\",\"timeout\":0}");
        System.out.println(getValue(json, PATH));*/
        String json = "{\"globalParams\":[],\"tenantCode\":\"hadoop\",\"tasks\":[{\"conditionResult\":{\"successNode\":[],\"failedNode\":[]},\"description\":\"\",\"runFlag\":\"NORMAL\",\"params\":{\"customConfig\":1,\"json\":\"{\\\"core\\\":{\\\"transport\\\":{\\\"channel\\\":{\\\"speed\\\":{\\\"record\\\":100000,\\\"channel\\\":2}}}},\\\"content\\\":[{\\\"reader\\\":{\\\"parameter\\\":{\\\"password\\\":\\\"MySQL2021\\\",\\\"transferDict\\\":[],\\\"needTransferColumns\\\":{\\\"sex\\\":\\\"1473538490168598529\\\"},\\\"connection\\\":[{\\\"querySql\\\":[\\\"select t02.student3.id,t02.student3.code,t02.student3.name,t02.student3.sex,t02.student3.age,t02.student3.class,t02.student3.create_time from  t02.student3 where t02.student3.create_time <= '2022-01-05 16:59:59'\\\"],\\\"jdbcUrl\\\":[\\\"jdbc:mysql://192.168.0.193:3306/t02?useSSL=false&useUnicode=true&characterEncoding=utf8\\\"]}],\\\"username\\\":\\\"root\\\"},\\\"name\\\":\\\"mysqlreader\\\"},\\\"writer\\\":{\\\"parameter\\\":{\\\"fileName\\\":\\\"widthtable_20220105_3999603\\\",\\\"compress\\\":\\\"NONE\\\",\\\"column\\\":[{\\\"name\\\":\\\"id\\\",\\\"type\\\":\\\"INT\\\"},{\\\"name\\\":\\\"code\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"name\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"sex\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"age\\\",\\\"type\\\":\\\"INT\\\"},{\\\"name\\\":\\\"class\\\",\\\"type\\\":\\\"STRING\\\"},{\\\"name\\\":\\\"create_time\\\",\\\"type\\\":\\\"DATE\\\"}],\\\"writeMode\\\":\\\"append\\\",\\\"fieldDelimiter\\\":\\\",\\\",\\\"path\\\":\\\"/user/hive/warehouse/hive_test2.db/widthtable_20220105_3999603/2022-01-05_16\\\",\\\"password\\\":\\\"bd@0414\\\",\\\"partition\\\":\\\"hour_\\\",\\\"thriftUrl\\\":\\\"jdbc:hive2://192.168.0.174:10000/\\\",\\\"hadoopConfig\\\":{\\\"dfs.client.use.datanode.hostname\\\":true},\\\"defaultFS\\\":\\\"hdfs://192.168.0.174:8020\\\",\\\"user\\\":\\\"root\\\",\\\"fileType\\\":\\\"orc\\\"},\\\"name\\\":\\\"hdfswriter\\\"}}],\\\"setting\\\":{\\\"speed\\\":{\\\"channel\\\":2}}}\",\"localParams\":[]},\"type\":\"DATAX\",\"timeout\":{\"enable\":false},\"maxRetryTimes\":\"0\",\"taskInstancePriority\":\"MEDIUM\",\"name\":\"fdfdsfsfdfd\",\"dependence\":{},\"preTasks\":[],\"retryInterval\":\"1\",\"id\":\"tasks-71210\",\"workerGroup\":\"default\"}],\"timeout\":0}";
        List<String> dictIds = getDictIds(json);
        System.out.println(JSONObject.toJSONString(dictIds));
        String s = replaceTransferDict(json, dictIds);
        System.out.println(s);
    }


    private static final String QUERY_SQL = "content[0].reader.parameter.connection[0].querySql[0]";

    private static final String PATH = "content[0].writer.parameter.path";

    /*public static Object getValue(String json, String path) {
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
    }*/

    private static List<String> getDictIds(String oldStatementJson) {
        List<String> results = Lists.newArrayList();
        Object value = getValue(oldStatementJson, NEED_TRANSFER_COLUMNS);
        Map map = JSONObject.parseObject(JSONObject.toJSONString(value)).toJavaObject(Map.class);
        Collection values = map.values();
        if (CollectionUtils.isNotEmpty(values)) {
            for (Object o : values) {
                results.add(o.toString());
            }
        }
        return results;
    }

    private final static String TRANSFER_DICT = "content[0].reader.parameter.transferDict";
    private final static String NEED_TRANSFER_COLUMNS = "content[0].reader.parameter.needTransferColumns";
    private final static String TASK_PARAM_JSON = "tasks[0].params.json";

    private static String replaceTransferDict(String oldStatementJson, List<String> dictIds) {
        if (CollectionUtils.isNotEmpty(dictIds)) {
            /*List<String> collect = dictIds.stream()
                    .filter(org.apache.dolphinscheduler.common.utils.StringUtils::isNotBlank)
                    .map(s -> REDIS_DICT_PREFIX + s)
                    .collect(Collectors.toList());
            List<String> strings = redisTemplate.opsForValue().multiGet(collect);*/
//            if (CollectionUtils.isNotEmpty(strings)) {
                /*List<DictInfo> dictInfos = Lists.newArrayList();
                for (String string : strings) {
                    RedisDictInfoResult redisDictInfo = JSONObject.parseObject(string).toJavaObject(RedisDictInfoResult.class);
                    DictInfo dictInfo = new DictInfo();
                    dictInfo.setKey(redisDictInfo.getDictId());
                    dictInfo.setValue(redisDictInfo.getColumnKey());
                    dictInfo.setName(redisDictInfo.getColumnValue());
                    dictInfos.add(dictInfo);
                }*/

            List<String> dictInfos = Lists.newArrayList();
            dictInfos.add("aaa");
            dictInfos.add("bbb");
            Configuration re = setValue(oldStatementJson, TRANSFER_DICT, JSONObject.toJSONString(dictInfos));
            return re.toJSON();
//            }

        }
        return oldStatementJson;
    }

    public static Object getValue(String json, String path) {
        Configuration from = Configuration.from(json);
        Object js = from.get(TASK_PARAM_JSON);
        Configuration content = Configuration.from(JSONUtil.toJsonStr(js));
        return content.get(path);
    }

    public static Configuration setValue(String json, String path, String newValue) {
        Configuration from = Configuration.from(json);
        Object js = from.get(TASK_PARAM_JSON);
        Configuration content = Configuration.from(JSONUtil.toJsonStr(js));
        content.set(path, newValue);

        from.set(TASK_PARAM_JSON, content.toJSON());
        return from;
    }
}
