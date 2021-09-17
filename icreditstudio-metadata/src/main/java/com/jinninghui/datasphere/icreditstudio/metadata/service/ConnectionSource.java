package com.jinninghui.datasphere.icreditstudio.metadata.service;

/**
 * @author peng
 */
public interface ConnectionSource {

    String getDriverClass();

    String getUsername();

    String getPassword();

    String getUrl();
}
