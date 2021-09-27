package com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.kernal;

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor.result
 * ClassName: ResultSet
 * Description:  ResultSet类
 * Date: 2021/9/24 4:15 下午
 *
 * @author liyanhui
 */
public interface ResultSet <K extends MetaData, V extends Record>{

    String chartset = null;

    String resultSetType();

    byte[ ] getResultSetHeader();

    boolean belongToPath(String path);

    boolean belongToResultSet(String content);

    ResultSerializer createResultSerializer();

    ResultDeserializer<K, V> createResultDeserializer();
}
