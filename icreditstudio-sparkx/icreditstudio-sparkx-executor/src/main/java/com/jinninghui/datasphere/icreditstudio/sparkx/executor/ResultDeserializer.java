package com.jinninghui.datasphere.icreditstudio.sparkx.executor;

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor.result
 * ClassName: ResultDeserizlizer
 * Description:  ResultDeserizlizer类
 * Date: 2021/9/24 4:23 下午
 *
 * @author liyanhui
 */
public interface ResultDeserializer<K extends MetaData, V extends Record>{

    K createMetaData(byte[] bytes);
    V createRecord(byte[] bytes);
}
