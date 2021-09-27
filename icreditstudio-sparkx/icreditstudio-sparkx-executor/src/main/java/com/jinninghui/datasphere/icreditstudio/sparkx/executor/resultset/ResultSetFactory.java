package com.jinninghui.datasphere.icreditstudio.sparkx.executor.resultset;

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor.result
 * ClassName: ResultSetFactory
 * Description:  ResultSetFactory类
 * Date: 2021/9/24 4:25 下午
 *
 * @author liyanhui
 */
public interface ResultSetFactory {

    <K extends MetaData, V extends Record> ResultSet<K, V> getResultSetByType(String resultSetType);
    <K extends MetaData, V extends Record> ResultSet<K, V> getResultSetByContent(String content);

    boolean exists(String resultSetType);
    boolean isResultSetPath(String path);
    boolean isResultSet(String content);
}
