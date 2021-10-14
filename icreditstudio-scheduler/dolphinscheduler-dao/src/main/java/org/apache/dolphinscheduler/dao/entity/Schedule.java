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

package org.apache.dolphinscheduler.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.dolphinscheduler.common.enums.FailureStrategy;
import org.apache.dolphinscheduler.common.enums.Priority;
import org.apache.dolphinscheduler.common.enums.ReleaseState;
import org.apache.dolphinscheduler.common.enums.WarningType;

import java.util.Date;

/**
 * schedule
 */
@Data
@TableName("t_ds_schedules")
public class Schedule {

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String workspaceId;
    /**
     * process definition id
     */
    private String processDefinitionId;

    /**
     * process definition name
     */
    @TableField(exist = false)
    private String processDefinitionName;

    /**
     * project name
     */
    @TableField(exist = false)
    private String projectCode;

    /**
     * schedule description
     */
    @TableField(exist = false)
    private String definitionDescription;

    /**
     * schedule start time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * schedule end time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * timezoneId
     * <p>see {@link java.util.TimeZone#getTimeZone(String)}
     */
    private String timezoneId;

    /**
     * crontab expression
     */
    private String crontab;

    /**
     * failure strategy
     */
    private FailureStrategy failureStrategy;

    /**
     * warning type
     */
    private WarningType warningType;

    /**
     * create time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * update time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * created user id
     */
    private String userId;

    /**
     * created user name
     */
    @TableField(exist = false)
    private String userName;

    /**
     * release state
     */
    private ReleaseState releaseState;

    /**
     * warning group id
     */
    private String warningGroupId;


    /**
     * process instance priority
     */
    private Priority processInstancePriority;

    /**
     * worker group
     */
    private String workerGroup;
}
