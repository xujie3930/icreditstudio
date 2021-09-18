package com.jinninghui.datasphere.icreditstudio.metadata.service;

/**
 * @author peng
 */
public interface ConnectionSource {

    /**
     * 驱动类名称
     *
     * @return
     */
    String getDriverClass();

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

    /**
     * 连接url
     *
     * @return
     */
    String getUrl();
}
