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

package org.apache.dolphinscheduler.api.service.impl;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.enums.Status;
import org.apache.dolphinscheduler.api.service.LoggerService;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.utils.StringUtils;
import org.apache.dolphinscheduler.dao.entity.TaskInstance;
import org.apache.dolphinscheduler.remote.utils.Host;
import org.apache.dolphinscheduler.service.log.LogClientService;
import org.apache.dolphinscheduler.service.process.ProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

/**
 * logger service impl
 */
@Service
public class LoggerServiceImpl implements LoggerService {


    private static final Logger logger = LoggerFactory.getLogger(LoggerService.class);

    @Autowired
    private ProcessService processService;

    private final LogClientService logClient;

    public LoggerServiceImpl() {
        logClient = new LogClientService();
    }

    @PreDestroy
    public void close() {
        logClient.close();
    }

    /**
     * view log
     *
     * @param taskInstId  task instance id
     * @return log string data
     */
    @Override
    public BusinessResult<String> queryLog(String taskInstId) {

        TaskInstance taskInstance = processService.findTaskInstanceById(taskInstId);

        if (taskInstance == null || StringUtils.isBlank(taskInstance.getHost())) {
            return BusinessResult.fail("", Status.TASK_INSTANCE_NOT_FOUND.getMsg());
        }

        String host = getHost(taskInstance.getHost());

        logger.info("log host : {} , logPath : {} , logServer port : {}", host, taskInstance.getLogPath(), Constants.RPC_PORT);

        String log = logClient.viewLog(host, Constants.RPC_PORT, taskInstance.getLogPath());
        return BusinessResult.success(log);
    }


    /**
     * get log size
     *
     * @param taskInstId task instance id
     * @return log byte array
     */
    @Override
    public byte[] getLogBytes(String taskInstId) {
        TaskInstance taskInstance = processService.findTaskInstanceById(taskInstId);
        if (taskInstance == null || StringUtils.isBlank(taskInstance.getHost())) {
            throw new RuntimeException("task instance is null or host is null");
        }
        String host = getHost(taskInstance.getHost());

        return logClient.getLogBytes(host, Constants.RPC_PORT, taskInstance.getLogPath());
    }


    /**
     * get host
     *
     * @param address address
     * @return old version return true ,otherwise return false
     */
    private String getHost(String address) {
        if (Host.isOldVersion(address)) {
            return address;
        }
        return Host.of(address).getIp();
    }
}
