package com.jinninghui.datasphere.icreditstudio.datasync.listener;

import com.alibaba.fastjson.JSONObject;
import com.jinninghui.datasphere.icreditstudio.datasync.model.TaskCallBackModel;
import com.jinninghui.datasphere.icreditstudio.datasync.service.SyncTaskService;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class ZkListener {

    private static final Logger logger = LoggerFactory.getLogger(ZkListener.class);

    @Value("${zookeeper.baseSleepTimeMs:1000}")
    private int baseSleepTimeMs;
    @Value("${zookeeper.maxRetries:3}")
    private int maxRetries;
    @Value("${zookeeper.connectionTimeoutMs:1000}")
    private int connectionTimeoutMs;
    @Value("${zookeeper.sessionTimeoutMs:1000}")
    private int sessionTimeoutMs;
    @Value("${zookeeper.connectString}")
    private String connectString;
    @Value("${task.writeBackPath:/icredit_studio/task/status}")
    private String writeBackPath;

    @Resource
    private SyncTaskService syncTaskService;

    private CuratorFramework getClient(){
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
        return CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .build();
    }

    @PostConstruct
    public void listenNodeCache() {

        CuratorFramework client = getClient();
        client.start();
        try {
            if(null == client.checkExists().forPath(writeBackPath)) {
                client.create().creatingParentsIfNeeded().forPath(writeBackPath);
            }

            NodeCache nodeCache =
                    new NodeCache(client, writeBackPath, false);
            NodeCacheListener listener = new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    ChildData childData = nodeCache.getCurrentData();
                    String taskCallBackStr = new String(childData.getData(), "Utf-8");
                    TaskCallBackModel model = JSONObject.parseObject(taskCallBackStr, TaskCallBackModel.class);
                    if(null != model && !StringUtils.isEmpty(model.getProcessDefinitionId()) && null != model.getExecTime()){
                        syncTaskService.taskWriteBack(model);
                    }
                }
            };
            nodeCache.getListenable().addListener(listener);
            nodeCache.start();
        } catch (Exception e) {
            logger.error("创建NodeCache监听失败, path={}", writeBackPath);
            e.printStackTrace();
        }
    }


}
