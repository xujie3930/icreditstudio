package com.jinninghui.datasphere.icreditstudio.sparkx.executor.result;

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor.result
 * ClassName: LineRecord
 * Description:  LineRecord类
 * Date: 2021/9/24 4:46 下午
 *
 * @author liyanhui
 */
public class LineRecord  extends ResultRecord{

    public LineRecord(String line) {
        this.line = line;
    }

    String line;
    String getLine(){
        return line;
    }

    void setLine(String line){
        this.line = line;
    }

    @Override
    public Record cloneRecord() {
        return new LineRecord(line);
    }

    @Override
    public String toString() {
        return line;
    }
}
