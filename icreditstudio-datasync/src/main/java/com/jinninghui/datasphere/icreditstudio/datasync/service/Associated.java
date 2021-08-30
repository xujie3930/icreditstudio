package com.jinninghui.datasphere.icreditstudio.datasync.service;

import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedType;

import java.util.List;

/**
 * @author Peng
 */
public interface Associated {
    /**
     * 注册
     */
    void register();

    /**
     * 获取类型
     *
     * @return
     */
    List<AssociatedType> getAssocTypes();

    /**
     * 获取关联条件
     *
     * @return
     */
    List<String> getAssocConditions();

    /**
     * 获取方言
     *
     * @return
     */
    String getDialect();
}
