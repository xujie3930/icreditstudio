package com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.protocol;

import com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor.result
 * ClassName: ProtocolResultSetWriter
 * Description:  ProtocolResultSetWriter类
 * Date: 2021/9/24 5:08 下午
 *
 * @author liyanhui
 */
public class ProtocolResultSetWriter extends ResultSetWriter {

    private Logger logger = LoggerFactory.getLogger(ProtocolResultSetWriter.class);
    public ProtocolResultSetWriter(ResultSet resultSet, Long maxCacheSize) {
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
    public void addMetaData(MetaData metaData) throws IOException {

    }

    @Override
    public void addRecord(Record record) throws IOException {

        logger.info(((LineRecord)record).getLine());
    }
}
