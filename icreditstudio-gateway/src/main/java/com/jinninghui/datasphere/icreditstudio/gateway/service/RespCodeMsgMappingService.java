package com.jinninghui.datasphere.icreditstudio.gateway.service;

/**
 * @author liyanhui
 */
public interface RespCodeMsgMappingService {

    void loadMappings();

    String getMsgByCode(String code);
}
