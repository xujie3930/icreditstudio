package com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.protocol;

import com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.MetaData;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.Record;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.ResultDeserializer;

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.protocol
 * ClassName: ProtocolResultDeserializer
 * Description:  ProtocolResultDeserializer类
 * Date: 2021/9/24 5:12 下午
 *
 * @author liyanhui
 */
public class ProtocolResultDeserializer implements ResultDeserializer {
    @Override
    public MetaData createMetaData(byte[] bytes) {
        return null;
    }

    @Override
    public Record createRecord(byte[] bytes) {
        return null;
    }
}
