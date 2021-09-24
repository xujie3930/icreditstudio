package com.jinninghui.datasphere.icreditstudio.sparkx.executor.result;


import com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.protocol.ProtocolResultSet;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.protocol.ProtocolResultSetWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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

    private Logger logger = LoggerFactory.getLogger(SparkOutputStream.class);
    private boolean isReady = false;

    private List<Integer> line = new ArrayList<>();
    private ResultSetWriter writer;

    @Override
    public void write(int b) throws IOException {

        if (isReady) {
            synchronized (this) {
                if (writer != null) {
                    if (b == '\n') {
                        flush0();
                        line.clear();
                    } else {
                        line.add(b);
                    }
                }
            }
        } else {
            logger.warn("writer is null");
        }
    }

    public void reset() throws IOException {
        writer = new ProtocolResultSetWriter(new ProtocolResultSet());
        writer.addMetaData(null);
    }

    public void reday(){
        this.isReady = true;
    }
    @Override
    public void flush() throws IOException {
        if (writer != null && !line.isEmpty()) {
            flush0();
            line.clear();
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
        for (Integer b : line) {
            String outStr = new String(intToByteArray(b), StandardCharsets.UTF_8);
            writer.addRecord(new LineRecord(outStr));
        }
    }

    byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }
}
