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
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.dolphinscheduler.common.enums.Flag;
import org.apache.dolphinscheduler.common.enums.ReleaseState;
import org.apache.dolphinscheduler.common.process.Property;
import org.apache.dolphinscheduler.common.utils.JSONUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * process definition
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_ds_process_definition")
public class ProcessDefinition {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String workspaceId;//新增工作空间id

    private Integer scheduleType;//0-周期实例，1-手动实例

//    /**
//     * code
//     */
//    private Long code;

    /**
     * 工作流名称
     */
    private String name;

    /**
     * version
     */
    private int version;

    /**
     * release state : online/offline
     */
    private ReleaseState releaseState;

//    /**
//     * project id
//     * TODO: delete
//     */
//    @TableField(exist = false)
//    private String projectId;

    /**
     * project code
     */
    private String projectCode;

    /**
     * definition json string
     */
    private String processDefinitionJson;

    /**
     * description
     */
    private String description;

    /**
     * user defined parameters
     */
    private String globalParams;

    /**
     * user defined parameter list
     */
    @TableField(exist = false)
    private List<Property> globalParamList;

    /**
     * user define parameter map
     */
    @TableField(exist = false)
    private Map<String, String> globalParamMap;

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
     * process is valid: yes/no
     */
    private Flag flag;

    /**
     * process user id
     */
    private String userId;

    /**
     * 用户码 //TODO ADD
     */
    private String userCode;

    /**
     * user name
     */
    @TableField(exist = false)
    private String userName;

    /**
     * project name
     */
    @TableField(exist = false)
    private String projectName;

    /**
     * locations array for web
     */
    private String locations;

    /**
     * connects array for web
     */
    private String connects;

    /**
     * receivers
     */
    private String receivers;

    /**
     * receivers cc
     */
    private String receiversCc;

    /**
     * schedule release state : online/offline
     */
    @TableField(exist = false)
    private ReleaseState scheduleReleaseState;

    /**
     * process warning time out. unit: minute
     */
    private int timeout;

    /**
     * tenant id
     */
    private String tenantCode;

    /**
     * modify user name
     */
    private String modifyBy;

    /**
     * resource ids
     */
    private String resourceIds;

    /**
     * warningGroupId
     */
    @TableField(exist = false)
    private String warningGroupId;

    public void setGlobalParams(String globalParams) {
        if (globalParams == null) {
            this.globalParamList = new ArrayList<>();
        } else {
            this.globalParamList = JSONUtils.toList(globalParams, Property.class);
        }
        this.globalParams = globalParams;
    }

    public void setGlobalParamList(List<Property> globalParamList) {
        this.globalParams = JSONUtils.toJsonString(globalParamList);
        this.globalParamList = globalParamList;
    }

    public Map<String, String> getGlobalParamMap() {
        if (globalParamMap == null && StringUtils.isNotEmpty(globalParams)) {
            List<Property> propList = JSONUtils.toList(globalParams, Property.class);
            globalParamMap = propList.stream().collect(Collectors.toMap(Property::getProp, Property::getValue));
        }

        return globalParamMap;
    }
}
