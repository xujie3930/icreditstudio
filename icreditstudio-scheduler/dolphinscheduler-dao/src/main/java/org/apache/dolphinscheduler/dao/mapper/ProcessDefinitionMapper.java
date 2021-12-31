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
package org.apache.dolphinscheduler.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.dolphinscheduler.dao.entity.ProcessDefinition;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * process definition mapper interface
 */
public interface ProcessDefinitionMapper extends BaseMapper<ProcessDefinition> {

    /**
     * verify process definition by name
     *
     * @param projectCode projectCode
     * @param name        name
     * @return process definition
     */
    ProcessDefinition verifyByDefineName(@Param("projectCode") String projectCode,
                                         @Param("name") String name);

    /**
     * query process definition by id
     *
     * @param processDefineId processDefineId
     * @return process definition
     */
    ProcessDefinition queryByDefineId(@Param("processDefineId") String processDefineId);

    void updateStatusById(@Param("processDefinitionId") String processDefinitionId, @Param("state") int state);

    int updateTimeById(@Param("updateTime")Date date, @Param("processDefinitionId")String processDefinitionId);

    void updateDefinitionVersionById(@Param("version") Integer version, @Param("cron") String cron, @Param("processDefinitionId") String processDefinitionId);
}
