package com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset;

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor.result
 * ClassName: ResultSetWriter
 * Description:  ResultSetWriter类
 * Date: 2021/9/24 4:09 下午
 *
 * @author liyanhui
 */
public abstract class ResultSetWriter<K extends MetaData, V extends Record> implements ResultWriter<K, V> {


    ResultSet<K, V> resultSet;
    Long maxCacheSize;

    public ResultSetWriter(ResultSet<K, V> resultSet, Long maxCacheSize) {
        this.resultSet = resultSet;
        this.maxCacheSize = maxCacheSize;
    }

    public abstract String toString();

    protected abstract void addMetaDataAndRecordString(String content);
    protected abstract void addRecordString(String content);



}
