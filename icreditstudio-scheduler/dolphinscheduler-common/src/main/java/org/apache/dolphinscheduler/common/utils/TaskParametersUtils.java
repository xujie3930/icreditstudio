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
package org.apache.dolphinscheduler.common.utils;

import org.apache.dolphinscheduler.common.enums.TaskType;
import org.apache.dolphinscheduler.common.task.AbstractParameters;
import org.apache.dolphinscheduler.common.task.datax.DataxParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * task parameters utils
 */
public class TaskParametersUtils {

    private static Logger logger = LoggerFactory.getLogger(TaskParametersUtils.class);

    /**
     * get task parameters
     *
     * @param taskType  task type
     * @param parameter parameter
     * @return task parameters
     */
    public static AbstractParameters getParameters(String taskType, String parameter) {
        try {
            switch (EnumUtils.getEnum(TaskType.class, taskType)) {
                case DATAX:
                    return JSONUtils.parseObject(parameter, DataxParameters.class);
                default:
                    return null;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
