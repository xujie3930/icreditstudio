-- auto-generated definition
drop table  if exists t_ds_process_definition;
create table t_ds_process_definition
(
    id                      varchar(30)              not null comment 'key'
        primary key,
    name                    varchar(255)             null comment 'process definition name',
    version                 int                      null comment 'process definition version',
    release_state           tinyint                  null comment 'process definition release state：0:offline,1:online',
    project_code            varchar(50)              null comment '项目编码',
    user_id                 varchar(30)              null comment 'process definition creator id',
    process_definition_json longtext                 null comment 'process definition json content',
    description             text                     null,
    global_params           text                     null comment 'global parameters',
    flag                    tinyint                  null comment '0 not available, 1 available',
    locations               text                     null comment 'Node location information',
    connects                text                     null comment 'Node connection information',
    receivers               text                     null comment 'receivers',
    receivers_cc            text                     null comment 'cc',
    create_time             datetime                 null comment 'create time',
    timeout                 int         default 0    null comment 'time out',
    tenant_code             varchar(50) default '-1' not null comment 'tenant id',
    update_time             datetime                 null comment 'update time',
    modify_by               varchar(36) default ''   null comment 'modify user',
    resource_ids            varchar(255)             null comment 'resource ids',
    workspace_id            varchar(32)              NOT NULL DEFAULT '0' COMMENT '工作空间id',
    schedule_type           tinyint(4)               NOT NULL DEFAULT '1' COMMENT '0-周期实例，1-手动实例'
)
    charset = utf8;

create index process_definition_index
    on t_ds_process_definition (id);

