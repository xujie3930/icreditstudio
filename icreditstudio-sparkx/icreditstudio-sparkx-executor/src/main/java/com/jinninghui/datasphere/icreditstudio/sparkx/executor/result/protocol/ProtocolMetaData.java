package com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.protocol;

import com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.kernal.MetaData;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.result.kernal.ResultMetaData;

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.sparkx.executor.result
 * ClassName: LineMetaData
 * Description:  LineMetaData类
 * Date: 2021/9/24 4:49 下午
 *
 * @author liyanhui
 */
public class ProtocolMetaData extends ResultMetaData {

    private String metaData;

    public ProtocolMetaData(String metaData) {
        this.metaData = metaData;
    }


    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    @Override
    public MetaData cloneMeta() {
        return new ProtocolMetaData(metaData);
    }
}
