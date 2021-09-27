package com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset.protocol;

import com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset.MetaData;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset.Record;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset.ResultSerializer;

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.protocol
 * ClassName: ProtocolResultSerializer
 * Description:  ProtocolResultSerializer类
 * Date: 2021/9/24 5:12 下午
 *
 * @author liyanhui
 */
public class ProtocolResultSerializer implements ResultSerializer {
    @Override
    public byte[] metaDataToBytes(MetaData metaData) {
        return new byte[0];
    }

    @Override
    public byte[] recordToBytes(Record record) {
        return new byte[0];
    }
}
