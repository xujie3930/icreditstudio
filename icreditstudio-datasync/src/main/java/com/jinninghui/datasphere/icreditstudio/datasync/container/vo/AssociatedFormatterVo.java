package com.jinninghui.datasphere.icreditstudio.datasync.container.vo;

import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedData;
import lombok.Data;

import java.util.List;

/**
 * @author peng
 */
@Data
public class AssociatedFormatterVo {
    /**
     * 数据库方言
     */
    private String dialect;
    /**
     * 可视化表信息
     */
    private List<TableInfo> sourceTables;
    /**
     * 表关联信息
     */
    private List<AssociatedData> assoc;
}
