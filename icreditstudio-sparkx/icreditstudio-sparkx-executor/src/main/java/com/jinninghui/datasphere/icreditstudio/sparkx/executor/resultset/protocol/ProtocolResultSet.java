package com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset.protocol;

import com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset.ResultDeserializer;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset.ResultSerializer;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset.ResultSet;

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.protocol
 * ClassName: ProtocolResultSet
 * Description:  ProtocolResultSet类
 * Date: 2021/9/24 5:10 下午
 *
 * @author liyanhui
 */
public class ProtocolResultSet implements ResultSet<ProtocolMetaData, ProtocolRecord> {

    @Override
    public String resultSetType() {
        return null;
    }

    @Override
    public byte[] getResultSetHeader() {
        return new byte[0];
    }

    @Override
    public boolean belongToPath(String path) {
        return false;
    }

    @Override
    public boolean belongToResultSet(String content) {
        return false;
    }

    @Override
    public ResultSerializer createResultSerializer() {
        return null;
    }

    @Override
    public ResultDeserializer<ProtocolMetaData, ProtocolRecord> createResultDeserializer() {
        return null;
    }
}
