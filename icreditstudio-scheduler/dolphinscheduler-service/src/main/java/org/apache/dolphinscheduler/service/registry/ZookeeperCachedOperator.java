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
package org.apache.dolphinscheduler.service.registry;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.dolphinscheduler.service.model.ProcessInstanceWriteBackModel;
import org.apache.dolphinscheduler.service.model.TaskInstanceWriteBackModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class ZookeeperCachedOperator extends ZookeeperOperator {

    private final Logger logger = LoggerFactory.getLogger(ZookeeperCachedOperator.class);


    private TreeCache treeCache;
    private NodeCache taskNodeCache;
    private NodeCache processNodeCache;

    /**
     * register a unified listener of /${dsRoot},
     */
    @Override
    protected void registerListener() {
        NodeCacheListener processListener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                ChildData childData = processNodeCache.getCurrentData();
                String processInstanceWriteBackStr = new String(childData.getData(), "Utf-8");
                ProcessInstanceWriteBackModel processModel = JSONObject.parseObject(processInstanceWriteBackStr, ProcessInstanceWriteBackModel.class);
                if(null != processModel && !StringUtils.isEmpty(processModel.getProcessInstanceId()) && !StringUtils.isEmpty(processModel.getFileName())){
                    handleProcessModel(processModel);
                }
            }
        };
        processNodeCache.getListenable().addListener(processListener);

        NodeCacheListener taskListener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                ChildData childData = taskNodeCache.getCurrentData();
                String taskInstanceWriteBackStr = new String(childData.getData(), "Utf-8");
                TaskInstanceWriteBackModel taskModel = JSONObject.parseObject(taskInstanceWriteBackStr, TaskInstanceWriteBackModel.class);
                if(null != taskModel && !StringUtils.isEmpty(taskModel.getTaskInstanceId())){
                    handleTaskModel(taskModel);
                }
            }
        };
        taskNodeCache.getListenable().addListener(taskListener);

        treeCache.getListenable().addListener((client, event) -> {
            String path = null == event.getData() ? "" : event.getData().getPath();
            if (path.isEmpty()) {
                return;
            }
            dataChanged(client, event, path);
        });
    }

    @Override
    protected void treeCacheStart() {
        treeCache = new TreeCache(zkClient, getZookeeperConfig().getDsRoot() + "/nodes");
        logger.info("add listener to zk path: {}", getZookeeperConfig().getDsRoot());
        try {
            treeCache.start();
        } catch (Exception e) {
            logger.error("add listener to zk path: {} failed", getZookeeperConfig().getDsRoot());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void nodeCacheStart() {
        processNodeCache = new NodeCache(zkClient, getZookeeperConfig().getProcessInstanceWriteBackPath());
        logger.info("add listener to zk path: {}", getZookeeperConfig().getProcessInstanceWriteBackPath());
        taskNodeCache = new NodeCache(zkClient, getZookeeperConfig().getTaskInstanceWriteBackPath());
        logger.info("add listener to zk path: {}", getZookeeperConfig().getTaskInstanceWriteBackPath());
        try {
            processNodeCache.start();
            taskNodeCache.start();
        } catch (Exception e) {
            logger.error("add listener to zk path: {} failed", getZookeeperConfig().getProcessInstanceWriteBackPath() + "," + getZookeeperConfig().getTaskInstanceWriteBackPath());
            throw new RuntimeException(e);
        }
    }

    //for sub class
    protected void dataChanged(final CuratorFramework client, final TreeCacheEvent event, final String path) {}
    protected void handleProcessModel(ProcessInstanceWriteBackModel processModel) {}
    protected void handleTaskModel(TaskInstanceWriteBackModel taskModel) {}

    public String getFromCache(final String cachePath, final String key) {
        ChildData resultInCache = treeCache.getCurrentData(key);
        if (null != resultInCache) {
            return null == resultInCache.getData() ? null : new String(resultInCache.getData(), StandardCharsets.UTF_8);
        }
        return null;
    }

    public TreeCache getTreeCache(final String cachePath) {
        return treeCache;
    }

    public void addListener(TreeCacheListener listener) {
        this.treeCache.getListenable().addListener(listener);
    }

    @Override
    public void close() {
        treeCache.close();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignore) {
        }
        super.close();
    }
}
