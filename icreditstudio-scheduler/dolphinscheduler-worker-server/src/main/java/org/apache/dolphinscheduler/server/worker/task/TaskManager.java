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
package org.apache.dolphinscheduler.server.worker.task;


import org.apache.dolphinscheduler.common.enums.TaskType;
import org.apache.dolphinscheduler.common.utils.EnumUtils;
import org.apache.dolphinscheduler.server.entity.TaskExecutionContext;
import org.apache.dolphinscheduler.server.worker.task.datax.DataxTask;
import org.apache.dolphinscheduler.server.worker.task.sql.SqlTask;
import org.slf4j.Logger;

/**
 * task manaster
 */
public class TaskManager {

    /**
     * create new task
     *
     * @param taskExecutionContext taskExecutionContext
     * @param logger               logger
     * @return AbstractTask
     * @throws IllegalArgumentException illegal argument exception
     */
    public static AbstractTask newTask(TaskExecutionContext taskExecutionContext,
                                       Logger logger)
            throws IllegalArgumentException {
        switch (EnumUtils.getEnum(TaskType.class, taskExecutionContext.getTaskType())) {
            case SQL:
                return new SqlTask(taskExecutionContext, logger);
            case DATAX:
                return new DataxTask(taskExecutionContext, logger);

            default:
                logger.error("unsupport task type: {}", taskExecutionContext.getTaskType());
                throw new IllegalArgumentException("not support task type");
        }
    }
}
