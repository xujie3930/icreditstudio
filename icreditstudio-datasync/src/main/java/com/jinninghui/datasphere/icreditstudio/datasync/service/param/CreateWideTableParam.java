package com.jinninghui.datasphere.icreditstudio.datasync.service.param;

import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTableFieldRequest;
import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class CreateWideTableParam {

    /**
     * 宽表名称
     */
    private String wideTableName;
    /**
     * 分区字段
     */
    private String partition;
    /**
     * 宽表字段列表
     */
    private List<WideTableFieldRequest> fieldInfos;
    /**
     * 目标数据库
     */
    private String targetSource;
}
