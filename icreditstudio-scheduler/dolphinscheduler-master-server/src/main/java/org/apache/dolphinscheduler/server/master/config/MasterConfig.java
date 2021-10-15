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

package org.apache.dolphinscheduler.server.master.config;

import lombok.Data;
import org.apache.dolphinscheduler.common.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource(value = "master.properties")
public class MasterConfig {

    @Value("${master.listen.port:5678}")
    private int listenPort;

    @Value("${master.exec.threads:100}")
    private int masterExecThreads;

    @Value("${master.exec.task.num:20}")
    private int masterExecTaskNum;

    @Value("${master.dispatch.task.num:3}")
    private int masterDispatchTaskNumber;

    @Value("${master.host.selector:LowerWeight}")
    private String hostSelector;

    @Value("${master.heartbeat.interval:10}")
    private int masterHeartbeatInterval;

    @Value("${master.task.commit.retryTimes:5}")
    private int masterTaskCommitRetryTimes;

    @Value("${master.task.commit.interval:1000}")
    private int masterTaskCommitInterval;

    @Value("${master.max.cpuload.avg:-1}")
    private double masterMaxCpuloadAvg;

    @Value("${master.reserved.memory:0.3}")
    private double masterReservedMemory;

    public double getMasterMaxCpuloadAvg() {
        if (masterMaxCpuloadAvg == -1) {
            return Constants.DEFAULT_MASTER_CPU_LOAD;
        }
        return masterMaxCpuloadAvg;
    }
}