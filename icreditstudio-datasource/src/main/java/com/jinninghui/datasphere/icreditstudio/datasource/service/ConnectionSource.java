package com.jinninghui.datasphere.icreditstudio.datasource.service;

/**
 * @author Peng
 */
public interface ConnectionSource {

    /**
     * 驱动
     *
     * @return
     */
    String getDriverClass();

    /**
     * 数据库连接
     *
     * @return
     */
    String getUrl();

    /**
     * 连接用户名
     *
     * @return
     */
    String getUsername();

    /**
     * 连接密码
     *
     * @return
     */
    String getPassword();
}
