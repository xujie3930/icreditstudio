package com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.util.Collection;

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor.result
 * ClassName: SparkWriter
 * Description:  SparkWriter类
 * Date: 2021/9/24 4:09 下午
 *
 * @author liyanhui
 */
public interface ResultWriter<K extends MetaData, V extends Record> extends Closeable, Flushable {

    void addMetaData(K k) throws IOException;


    void addRecord(V v) throws IOException;

    Collection<V> getRecords();

}
