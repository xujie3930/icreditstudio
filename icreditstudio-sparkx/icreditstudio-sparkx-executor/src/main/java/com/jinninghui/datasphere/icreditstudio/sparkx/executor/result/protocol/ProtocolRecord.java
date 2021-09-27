package com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.protocol;

import com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.kernal.Record;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.kernal.ResultRecord;

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor.result
 * ClassName: LineRecord
 * Description:  LineRecord类
 * Date: 2021/9/24 4:46 下午
 *
 * @author liyanhui
 */
public class ProtocolRecord extends ResultRecord {

    public ProtocolRecord(String line) {
        this.line = line;
    }

    String line;
    public String getLine(){
        return line;
    }

    void setLine(String line){
        this.line = line;
    }

    @Override
    public Record cloneRecord() {
        return new ProtocolRecord(line);
    }

    @Override
    public String toString() {
        return line;
    }
}
