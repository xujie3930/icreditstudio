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

package org.apache.dolphinscheduler.service.queue;

import lombok.Data;

import java.util.Map;

/**
 * task priority info
 */
@Data
public class TaskPriority implements Comparable<TaskPriority> {

    /**
     * processInstancePriority
     */
    private int processInstancePriority;

    /**
     * processInstanceId
     */
    private String processInstanceId;

    /**
     * taskInstancePriority
     */
    private int taskInstancePriority;

    /**
     * taskId
     */
    private String taskId;

    /**
     * groupName
     */
    private String groupName;

    /**
     * context
     */
    private Map<String, String> context;

    public TaskPriority(int processInstancePriority,
                        String processInstanceId,
                        int taskInstancePriority,
                        String taskId, String groupName) {
        this.processInstancePriority = processInstancePriority;
        this.processInstanceId = processInstanceId;
        this.taskInstancePriority = taskInstancePriority;
        this.taskId = taskId;
        this.groupName = groupName;
    }

    @Override
    public int compareTo(TaskPriority other) {
        if (this.getProcessInstancePriority() > other.getProcessInstancePriority()) {
            return 1;
        }
        if (this.getProcessInstancePriority() < other.getProcessInstancePriority()) {
            return -1;
        }

        if (this.getProcessInstanceId().compareTo(other.getProcessInstanceId()) > 0) {
            return 1;
        }
        if (this.getProcessInstanceId().compareTo(other.getProcessInstanceId()) < 0) {
            return -1;
        }

        if (this.getTaskInstancePriority() > other.getTaskInstancePriority()) {
            return 1;
        }
        if (this.getTaskInstancePriority() < other.getTaskInstancePriority()) {
            return -1;
        }

        if (this.getTaskId().compareTo(other.getTaskId()) > 0) {
            return 1;
        }
        if (this.getTaskId().compareTo(other.getTaskId()) < 0) {
            return -1;
        }

        return this.getGroupName().compareTo(other.getGroupName());
    }
}
