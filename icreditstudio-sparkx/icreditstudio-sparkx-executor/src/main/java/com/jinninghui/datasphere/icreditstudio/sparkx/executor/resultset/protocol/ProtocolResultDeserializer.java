package com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset.protocol;

import com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset.ResultDeserializer;

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.protocol
 * ClassName: ProtocolResultDeserializer
 * Description:  ProtocolResultDeserializer类
 * Date: 2021/9/24 5:12 下午
 *
 * @author liyanhui
 */
public class ProtocolResultDeserializer implements ResultDeserializer<ProtocolMetaData, ProtocolRecord> {
    @Override
    public ProtocolMetaData createMetaData(byte[] bytes) {
        return null;
    }

    @Override
    public ProtocolRecord createRecord(byte[] bytes) {
        return null;
    }
}
