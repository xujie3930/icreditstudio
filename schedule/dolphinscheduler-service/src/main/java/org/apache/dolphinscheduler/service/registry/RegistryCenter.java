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

import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.common.IStoppable;
import org.apache.dolphinscheduler.common.utils.PropertyUtils;
import org.apache.dolphinscheduler.registry.Registry;
import org.apache.dolphinscheduler.registry.RegistryConnectListener;
import org.apache.dolphinscheduler.registry.RegistryException;
import org.apache.dolphinscheduler.registry.SubscribeListener;
import org.apache.dolphinscheduler.registry.zookeeper.ZookeeperRegistry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * All business parties use this class to access the registry
 */
public class RegistryCenter {

    private final AtomicBoolean isStarted = new AtomicBoolean(false);

    private Registry registry;

    private IStoppable stoppable;

    /**
     * nodes namespace
     */
    protected static String NODES;

    protected static final String EMPTY = "";

    private static final String REGISTRY_PREFIX = "registry";

    private static final String REGISTRY_CONFIG_FILE_PATH = "/registry.properties";

    /**
     * init node persist
     */
    public void init() {
        if (isStarted.compareAndSet(false, true)) {
            PropertyUtils.loadPropertyFile(REGISTRY_CONFIG_FILE_PATH);
            Map<String, String> registryConfig = PropertyUtils.getPropertiesByPrefix(REGISTRY_PREFIX);

            if (null == registryConfig || registryConfig.isEmpty()) {
                throw new RegistryException("registry config param is null");
            }
            registry = new ZookeeperRegistry();
            registry.init(registryConfig);
            initNodes();

        }
    }

    /**
     * init nodes
     */
    private void initNodes() {
        persist(Constants.REGISTRY_DOLPHINSCHEDULER_MASTERS, EMPTY);
        persist(Constants.REGISTRY_DOLPHINSCHEDULER_WORKERS, EMPTY);
        persist(Constants.REGISTRY_DOLPHINSCHEDULER_DEAD_SERVERS, EMPTY);
    }

    /**
     * close
     */
    public void close() {
        if (isStarted.compareAndSet(true, false) && registry != null) {
            registry.close();
        }
    }

    public void persist(String key, String value) {
        registry.persist(key, value);
    }

    public void persistEphemeral(String key, String value) {
        registry.persistEphemeral(key, value);
    }

    public void remove(String key) {
        registry.remove(key);
    }

    public void update(String key, String value) {
        registry.update(key, value);
    }

    public String get(String key) {
        return registry.get(key);
    }

    public void subscribe(String path, SubscribeListener subscribeListener) {
        registry.subscribe(path, subscribeListener);
    }

    public void addConnectionStateListener(RegistryConnectListener registryConnectListener) {
        registry.addConnectionStateListener(registryConnectListener);
    }

    public boolean isExisted(String key) {
        return registry.isExisted(key);
    }

    public boolean getLock(String key) {
        return registry.acquireLock(key);
    }

    public boolean releaseLock(String key) {
        return registry.releaseLock(key);
    }

    /**
     * @return get dead server node parent path
     */
    public String getDeadZNodeParentPath() {
        return Constants.REGISTRY_DOLPHINSCHEDULER_DEAD_SERVERS;
    }

    public void setStoppable(IStoppable stoppable) {
        this.stoppable = stoppable;
    }

    public IStoppable getStoppable() {
        return stoppable;
    }

    /**
     * whether master path
     *
     * @param path path
     * @return result
     */
    public boolean isMasterPath(String path) {
        return path != null && path.contains(Constants.REGISTRY_DOLPHINSCHEDULER_MASTERS);
    }

    /**
     * get worker group path
     *
     * @param workerGroup workerGroup
     * @return worker group path
     */
    public String getWorkerGroupPath(String workerGroup) {
        return Constants.REGISTRY_DOLPHINSCHEDULER_WORKERS + "/" + workerGroup;
    }

    /**
     * whether worker path
     *
     * @param path path
     * @return result
     */
    public boolean isWorkerPath(String path) {
        return path != null && path.contains(Constants.REGISTRY_DOLPHINSCHEDULER_WORKERS);
    }

    /**
     * get children nodes
     *
     * @param key key
     * @return children nodes
     */
    public List<String> getChildrenKeys(final String key) {
        return registry.getChildren(key);
    }

}
