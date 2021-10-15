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
import lombok.Data;
import org.apache.dolphinscheduler.common.enums.AlertStatus;
import org.apache.dolphinscheduler.common.enums.AlertType;
import org.apache.dolphinscheduler.common.enums.ShowType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@TableName("t_ds_alert")
public class Alert {
    /**
     * primary key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * title
     */
    @TableField(value = "title")
    private String title;
    /**
     * show_type
     */
    @TableField(value = "show_type")
    private ShowType showType;
    /**
     * content
     */
    @TableField(value = "content")
    private String content;
    /**
     * alert_type
     */
    @TableField(value = "alert_type")
    private AlertType alertType;
    /**
     * alert_status
     */
    @TableField(value = "alert_status")
    private AlertStatus alertStatus;
    /**
     * log
     */
    @TableField(value = "log")
    private String log;
    /**
     * alertgroup_id
     */
    @TableField("alertgroup_id")
    private String alertGroupId;
    /**
     * receivers
     */
    @TableField("receivers")
    private String receivers;
    /**
     * receivers_cc
     */
    @TableField("receivers_cc")
    private String receiversCc;
    /**
     * create_time
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * update_time
     */
    @TableField("update_time")
    private Date updateTime;
    @TableField(exist = false)
    private Map<String, Object> info = new HashMap<>();

}
