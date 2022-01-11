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

package org.apache.dolphinscheduler.api.enums;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * status enum      // todo #4855 One category one interval
 */
public enum Status {

    SUCCESS(0, "success", "成功"),

    INTERNAL_SERVER_ERROR_ARGS(10000, "Internal Server Error: {0}", "服务端异常: {0}"),

    REQUEST_PARAMS_NOT_VALID_ERROR(10001, "request parameter {0} is not valid", "请求参数[{0}]无效"),
    TASK_TIMEOUT_PARAMS_ERROR(10002, "task timeout parameter is not valid", "任务超时参数无效"),
    ALERT_GROUP_NOT_EXIST(10011, "alarm group not found", "告警组不存在"),
    ALERT_GROUP_EXIST(10012, "alarm group already exists", "告警组名称已存在"),
    SCHEDULE_CRON_NOT_EXISTS(10022, "scheduler crontab {0} does not exist", "调度配置定时表达式[{0}]不存在"),
    DEFINITION_SCHEDULE_NOT_EXISTS(10022, "The task schedule associated with the workflow definition {0} does not exist", "工作流定义[{0}]关联的任务调度不存在"),
    SCHEDULE_CRON_ONLINE_FORBID_UPDATE(10023, "online status does not allow update operations", "调度配置上线状态不允许修改"),
    SCHEDULE_CRON_CHECK_FAILED(10024, "scheduler crontab expression validation failure: {0}", "调度配置定时表达式验证失败: {0}"),
    MASTER_NOT_EXISTS(10025, "master does not exist", "无可用master节点"),
    SCHEDULE_STATUS_UNKNOWN(10026, "unknown status: {0}", "未知状态: {0}"),
    CREATE_ALERT_GROUP_ERROR(10027, "create alert group error", "创建告警组错误"),
    QUERY_ALL_ALERTGROUP_ERROR(10028, "query all alertgroup error", "查询告警组错误"),
    LIST_PAGING_ALERT_GROUP_ERROR(10029, "list paging alert group error", "分页查询告警组错误"),
    UPDATE_ALERT_GROUP_ERROR(10030, "update alert group error", "更新告警组错误"),
    DELETE_ALERT_GROUP_ERROR(10031, "delete alert group error", "删除告警组错误"),
    LIST_WORKERS_ERROR(10044, "list workers error", "查询worker列表错误"),
    LIST_MASTERS_ERROR(10045, "list masters error", "查询master列表错误"),
    CREATE_SCHEDULE_ERROR(10076, "create schedule error", "创建调度配置错误"),
    UPDATE_SCHEDULE_ERROR(10077, "update schedule error", "更新调度配置错误"),
    QUERY_SCHEDULE_LIST_PAGING_ERROR(10080, "query schedule list paging error", "分页查询调度配置列表错误"),
    QUERY_SCHEDULE_LIST_ERROR(10081, "query schedule list error", "查询调度配置列表错误"),
    QUERY_TASK_LIST_PAGING_ERROR(10082, "query task list paging error", "分页查询任务列表错误"),
    VERIFY_PROCESS_DEFINITION_NAME_UNIQUE_ERROR(10106, "verify process definition name unique error", "工作流定义名称验证错误"),
    SCHEDULE_CRON_REALEASE_NEED_NOT_CHANGE(10126, "schedule release is already {0}", "调度配置上线错误[{0}]"),
    PREVIEW_SCHEDULE_ERROR(10139, "preview schedule error", "预览调度配置错误"),
    PARSE_TO_CRON_EXPRESSION_ERROR(10140, "parse cron to cron expression error", "解析调度表达式错误"),
    SCHEDULE_START_TIME_END_TIME_SAME(10141, "The start time must not be the same as the end", "开始时间不能和结束时间一样"),
    FORCE_TASK_SUCCESS_ERROR(10165, "force task success error", "强制成功任务实例错误"),
    TASK_INSTANCE_NOT_COMPLETED(10180, "Please wait until the task is completed", "请等待任务执行完成后再查看日志"),

    PROCESS_INSTANCE_NOT_EXIST(50001, "process instance {0} does not exist", "工作流实例[{0}]不存在"),
    PROCESS_DEFINE_NOT_EXIST(50003, "process definition {0} does not exist", "工作流定义[{0}]不存在"),
    PROCESS_DEFINE_NOT_RELEASE(50004, "process definition {0} not on line", "工作流定义[{0}]不是上线状态"),
    PROCESS_INSTANCE_ALREADY_CHANGED(50005, "the status of process instance {0} is already {1}", "工作流实例[{0}]的状态已经是[{1}]"),
    PROCESS_INSTANCE_STATE_OPERATION_ERROR(50006, "the status of process instance {0} is {1},Cannot perform {2} operation", "工作流实例[{0}]的状态是[{1}]，无法执行[{2}]操作"),
    PROCESS_DEFINE_NOT_ALLOWED_EDIT(50008, "process definition {0} does not allow edit", "工作流定义[{0}]不允许修改"),
    PROCESS_INSTANCE_EXECUTING_COMMAND(50009, "process instance {0} is executing the command, please wait ...", "工作流实例[{0}]正在执行命令，请稍等..."),
    TASK_INSTANCE_STATE_COUNT_ERROR(50011, "task instance state count error", "查询各状态任务实例数错误"),
    COUNT_PROCESS_INSTANCE_STATE_ERROR(50012, "count process instance state error", "查询各状态流程实例数错误"),
    COUNT_PROCESS_DEFINITION_USER_ERROR(50013, "count process definition user error", "查询各用户流程定义数错误"),
    START_PROCESS_INSTANCE_ERROR(50014, "start process instance error", "运行工作流实例错误"),
    EXECUTE_PROCESS_INSTANCE_ERROR(50015, "execute process instance error", "操作工作流实例错误"),
    PROCESS_DEFINE_STATE_ONLINE(50021, "process definition {0} is already on line", "工作流定义[{0}]已上线"),
    DELETE_PROCESS_DEFINE_BY_ID_ERROR(50022, "delete process definition by id error", "删除工作流定义错误"),
    SCHEDULE_CRON_STATE_ONLINE(50023, "the status of schedule {0} is already on line", "调度配置[{0}]已上线"),
    DELETE_SCHEDULE_CRON_BY_ID_ERROR(50024, "delete schedule by id error", "删除调度配置错误"),

    /**
     * for monitor
     */
    QUERY_DATABASE_STATE_ERROR(70001, "query database state error", "查询数据库状态错误"),
    QUERY_ZOOKEEPER_STATE_ERROR(70002, "query zookeeper state error", "查询zookeeper状态错误"),

    COMMAND_STATE_COUNT_ERROR(80001, "task instance state count error", "查询各状态任务实例数错误"),
    QUEUE_COUNT_ERROR(90001, "queue count error", "查询队列数据错误");

    private final int code;
    private final String enMsg;
    private final String zhMsg;

    Status(int code, String enMsg, String zhMsg) {
        this.code = code;
        this.enMsg = enMsg;
        this.zhMsg = zhMsg;
    }

    public int getCode() {
        return this.code;
    }

    public String getZhMsg() {
        return this.zhMsg;
    }

    public String getMsg() {
        if (Locale.SIMPLIFIED_CHINESE.getLanguage().equals(LocaleContextHolder.getLocale().getLanguage())) {
            return this.zhMsg;
        } else {
            return this.enMsg;
        }
    }
}
