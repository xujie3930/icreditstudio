-- auto-generated definition
create table t_ds_task_instance
(
    id                     varchar(30)            not null comment 'key'
        primary key,
    name                   varchar(255)           null comment 'task name',
    task_type              varchar(64)            null comment 'task type',
    process_definition_id  varchar(30)            null comment 'process definition id',
    process_instance_id    int                    null comment 'process instance id',
    task_json              longtext               null comment 'task content json',
    state                  tinyint                null comment 'Status: 0 commit succeeded, 1 running, 2 prepare to pause, 3 pause, 4 prepare to stop, 5 stop, 6 fail, 7 succeed, 8 need fault tolerance, 9 kill, 10 wait for thread, 11 wait for dependency to complete',
    submit_time            datetime               null comment 'task submit time',
    start_time             datetime               null comment 'task start time',
    end_time               datetime               null comment 'task end time',
    host                   varchar(135)           null,
    execute_path           varchar(200)           null comment 'task execute path in the host',
    log_path               varchar(200)           null comment 'task log path',
    alert_flag             tinyint                null comment 'whether alert',
    retry_times            int(4)      default 0  null comment 'task retry times',
    pid                    int(4)                 null comment 'pid of task',
    app_link               text                   null comment 'yarn app id',
    flag                   tinyint     default 1  null comment '0 not available, 1 available',
    retry_interval         int(4)                 null comment 'retry interval when task failed ',
    max_retry_times        int(2)                 null comment 'max retry times',
    task_instance_priority int                    null comment 'task instance priority:0 Highest,1 High,2 Medium,3 Low,4 Lowest',
    worker_group           varchar(64) default '' null comment 'worker group',
    executor_id            int                    null comment 'executor id'
)
    charset = utf8;

create index process_instance_id
    on t_ds_task_instance (process_instance_id);

create index task_instance_index
    on t_ds_task_instance (process_definition_id, process_instance_id);

