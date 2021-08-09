package com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.impl;

import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.common.dto.Interface;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.InterfaceLoadService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.InterfaceService;
import com.hashtech.businessframework.log.Logable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * interface服务接口
 *
 * @author Administrator
 */
@Service
public class InterfaceServiceImpl implements InterfaceService {

    @Autowired
    private InterfaceLoadService interfaceLoadService;
    /**
     * 获取数据库interface表中所有的interface实例
     *
     * @return
     */
    public static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
    public static List<Interface> cachedInterfaces = new ArrayList<>();
    /**
     * 按钮类型的url集合
     */
    private static List<String> buttonTypeUrlList = new ArrayList<>();

    @Logable
    @Override
    public List<Interface> loadInterface() {
        List<Interface> interfaces = interfaceLoadService.loadInterface();

        buttonTypeUrlList = interfaceLoadService.loadButtonMenuUrlList();
        return interfaces;
    }

    /**
     * 线程安全
     *
     * @param interfaceList
     */
    @Override
    public void setCachedInterfaceList(List<Interface> interfaceList) {
        LOCK.writeLock().lock();
        try {
            cachedInterfaces = interfaceList;
        } finally {
            // 释放写锁
            LOCK.writeLock().unlock();
        }
    }

    /**
     * 线程安全
     *
     * @return
     */
    @Override
    public List<Interface> getCachedInterfaceList() {
        LOCK.readLock().lock();
        try {
            return cachedInterfaces;
        } finally {
            LOCK.readLock().unlock();
        }

    }

    @Override
    public List<String> getCachedButtonUrlList() {
        LOCK.readLock().lock();
        try {
            return buttonTypeUrlList;
        } finally {
            LOCK.readLock().unlock();
        }
    }


}
