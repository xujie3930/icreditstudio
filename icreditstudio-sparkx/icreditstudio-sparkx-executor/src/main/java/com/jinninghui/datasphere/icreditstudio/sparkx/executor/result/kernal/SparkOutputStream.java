package com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.kernal;


import com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.protocol.ProtocolMetaData;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.protocol.ProtocolRecord;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.protocol.ProtocolResultSet;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.protocol.ProtocolResultSetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 拿到Spark执行的结果进行输出，
 * <p>
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor.result
 * ClassName: SparkOutputStream
 * Description:  SparkOutputStream类
 * Date: 2021/9/24 4:04 下午
 *
 * @author liyanhui
 */
public class SparkOutputStream extends OutputStream {

    private final Logger logger = LoggerFactory.getLogger(SparkOutputStream.class);
    private boolean isReady = false;

    //    private List<Integer> line = new ArrayList<>();
    private final ByteBuffer bb = ByteBuffer.allocate(4096);
    private ResultSetWriter<ProtocolMetaData, ProtocolRecord> writer;

    @Override
    public void write(int b) throws IOException {

        if (isReady) {
            synchronized (this) {
                if (writer != null) {
                    if (b == '\n') {
                        flush0();
                        bb.clear();
                    } else {
                        bb.put(intToByteArray(b));
                    }
                } else {
                    logger.warn("writer is null");
                }
            }
        }
        {
        }
    }

    public void reset() throws IOException {
        writer = new ProtocolResultSetWriter(new ProtocolResultSet());
        writer.addMetaData(null);
    }

    public void reday() {
        this.isReady = true;
    }

    @Override
    public void flush() throws IOException {
        if (writer != null && !bb.hasRemaining()) {
            flush0();
            bb.clear();
        }
    }

    @Override
    public String toString() {
        if (writer != null) {
            return writer.toString();
        } else {
            return null;
        }
    }

    @Override
    public void close() throws IOException {
        if (writer != null) {
            flush();
            writer.close();
            writer = null;
        }
    }

    void flush0() throws IOException {
        String outStr = new String(bb.array(), StandardCharsets.UTF_8);
        writer.addRecord(new ProtocolRecord(outStr));

    }

    byte[] intToByteArray(int i) {
        byte[] result = new byte[1];
        result[0] = (byte) (i & 0xFF);
        return result;
    }
}
