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
package org.apache.dolphinscheduler.common.task.datax;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.dolphinscheduler.common.enums.Flag;
import org.apache.dolphinscheduler.common.process.ResourceInfo;
import org.apache.dolphinscheduler.common.task.AbstractParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * DataX parameter
 */
@Data
public class DataxParameters extends AbstractParameters {

    /**
     * if custom json config，eg  0, 1
     */
    private int customConfig;

    /**
     * if customConfig eq 1 ,then json is usable
     */
    private String json;

    /**
     * data source type，eg  MYSQL, POSTGRES ...
     */
    private String dsType;

    /**
     * datasource id
     */
    private String dataSource;

    /**
     * data target type，eg  MYSQL, POSTGRES ...
     */
    private String dtType;

    /**
     * datatarget id
     */
    private String dataTarget;

    /**
     * sql
     */
    private String sql;

    /**
     * target table
     */
    private String targetTable;

    /**
     * Pre Statements
     */
    private List<String> preStatements;

    /**
     * Post Statements
     */
    private List<String> postStatements;

    /**
     * speed byte num
     */
    private int jobSpeedByte;

    /**
     * speed record count
     */
    private int jobSpeedRecord;


    @Override
    public boolean checkParameters() {
        if (customConfig == Flag.NO.ordinal()) {
            //TODO
//            return StringUtils.equals("0", dataSource)
//                    && !StringUtils.equals("0", dataTarget)
//                    && StringUtils.isNotEmpty(sql)
//                    && StringUtils.isNotEmpty(targetTable);
            return true;
        } else {
            return StringUtils.isNotEmpty(json);
        }
    }

    @Override
    public List<ResourceInfo> getResourceFilesList() {
        return new ArrayList<>();
    }
}
