package com.jinninghui.datasphere.icreditstudio.datasync.service.task.reader;

import com.jinninghui.datasphere.icreditstudio.datasync.container.DialectKeyRegister;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictInfo;

import java.util.List;
import java.util.Map;

/**
 * @author Peng
 */
public interface DataxReader  extends DialectKeyRegister {

    /**
     * datax reader插件参数
     *
     * @return
     */
    Map<String, Object> getReaderEntity();

    /**
     * 前置配置参数设置
     *
     * @param widetableEntity
     */
    void preSetConfigParam(SyncWidetableEntity widetableEntity);

    /**
     * 需要做字典转换的列
     *
     * @param columns
     */
    void setNeedTransferColumns(Map<String, String> columns);

    /**
     * 字典列表
     *
     * @param dictInfos
     */
    void setTransferDict(List<DictInfo> dictInfos);
}
