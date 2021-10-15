-- auto-generated definition
create table t_ds_process_instance
(
    id                        varchar(30)                           not null comment 'key'
        primary key,
    name                      varchar(255)                          null comment 'process instance name',
    process_definition_id     varchar(30)                           null comment 'process definition id',
    state                     tinyint                               null comment 'process instance Status: 0 commit succeeded, 1 running, 2 prepare to pause, 3 pause, 4 prepare to stop, 5 stop, 6 fail, 7 succeed, 8 need fault tolerance, 9 kill, 10 wait for thread, 11 wait for dependency to complete',
    recovery                  tinyint                               null comment 'process instance failover flag：0:normal,1:failover instance',
    start_time                datetime                              null comment 'process instance start time',
    end_time                  datetime                              null comment 'process instance end time',
    run_times                 int                                   null comment 'process instance run times',
    host                      varchar(135)                          null,
    command_type              tinyint                               null comment 'command type',
    command_param             text                                  null comment 'json command parameters',
    task_depend_type          tinyint                               null comment 'task depend type. 0: only current node,1:before the node,2:later nodes',
    max_try_times             tinyint     default 0                 null comment 'max try times',
    failure_strategy          tinyint     default 0                 null comment 'failure strategy. 0:end the process when node failed,1:continue running the other nodes when node failed',
    warning_type              tinyint     default 0                 null comment 'warning type. 0:no warning,1:warning if process success,2:warning if process failed,3:warning if success',
    warning_group_id          varchar(30)                           null comment 'warning group id',
    schedule_time             datetime                              null comment 'schedule time',
    command_start_time        datetime                              null comment 'command start time',
    global_params             text                                  null comment 'global parameters',
    process_instance_json     longtext                              null comment 'process instance json(copy的process definition 的json)',
    flag                      tinyint     default 1                 null comment 'flag',
    update_time               timestamp   default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    is_sub_process            int         default 0                 null comment 'flag, whether the process is sub process',
    executor_id               varchar(30)                           not null comment 'executor id',
    locations                 text                                  null comment 'Node location information',
    connects                  text                                  null comment 'Node connection information',
    history_cmd               text                                  null comment 'history commands of process instance operation',
    dependence_schedule_times text                                  null comment 'depend schedule fire time',
    process_instance_priority int                                   null comment 'process instance priority. 0 Highest,1 High,2 Medium,3 Low,4 Lowest',
    worker_group              varchar(64) default ''                null comment 'worker group',
    timeout                   int         default 0                 null comment 'time out',
    tenant_code               varchar(50) default '-1'              not null comment 'tenant id'
)
    charset = utf8;

create index process_instance_index
    on t_ds_process_instance (process_definition_id, id);

create index start_time_index
    on t_ds_process_instance (start_time);

