package com.jinninghui.datasphere.icreditstudio.sparkx.executor;

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor.result
 * ClassName: ResultSerializer
 * Description:  ResultSerializer类
 * Date: 2021/9/24 4:22 下午
 *
 * @author liyanhui
 */
public interface ResultSerializer {

    byte[] metaDataToBytes(MetaData metaData);

    byte[] recordToBytes(Record record);


}
