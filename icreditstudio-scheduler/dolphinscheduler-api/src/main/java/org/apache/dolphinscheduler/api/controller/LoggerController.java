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

package org.apache.dolphinscheduler.api.controller;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.service.LoggerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * logger controller
 */
@RestController
@RequestMapping("/log")
public class LoggerController extends BaseController {

    @Autowired
    private LoggerService loggerService;

    /**
     * query task log
     *
     * @param taskInstanceId task instance id
     * @return task log content
     */
    @GetMapping(value = "/detail")
    public BusinessResult<String> queryLog(@RequestParam(value = "taskInstanceId") String taskInstanceId) {
        return loggerService.queryLog(taskInstanceId);
    }


    /**
     * download log file
     *
     * @param taskInstanceId task instance id
     * @return log file content
     */
    @GetMapping(value = "/download-log")
    public ResponseEntity downloadTaskLog(@RequestParam(value = "taskInstanceId") String taskInstanceId) {
        byte[] logBytes = loggerService.getLogBytes(taskInstanceId);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + System.currentTimeMillis() + ".log" + "\"")
                .body(logBytes);
    }

}
