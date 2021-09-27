package com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset.protocol;

import com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset.ResultSet;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset.ResultSetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor.result
 * ClassName: ProtocolResultSetWriter
 * Description:  ProtocolResultSetWriter类
 * Date: 2021/9/24 5:08 下午
 *
 * @author liyanhui
 */
public class ProtocolResultSetWriter extends ResultSetWriter<ProtocolMetaData, ProtocolRecord> {

    private final Logger logger = LoggerFactory.getLogger(ProtocolResultSetWriter.class);

    private final Collection<ProtocolRecord> records = new ArrayList<>();
    public ProtocolResultSetWriter(ResultSet<ProtocolMetaData, ProtocolRecord> resultSet, Long maxCacheSize) {
        super(resultSet, maxCacheSize);
    }

    public ProtocolResultSetWriter(ProtocolResultSet resultSet) {
        super(resultSet, 4096L);
    }

    @Override
    public String toString() {
        return null;
    }


    @Override
    public void addMetaDataAndRecordString(String content) {

    }

    @Override
    public void addRecordString(String content) {

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void addMetaData(ProtocolMetaData protocolMetaData) throws IOException {

    }

    @Override
    public void addRecord(ProtocolRecord protocolRecord) throws IOException {
        records.add(protocolRecord);
    }

    @Override
    public Collection<ProtocolRecord> getRecords() {
        return records;
    }
}
