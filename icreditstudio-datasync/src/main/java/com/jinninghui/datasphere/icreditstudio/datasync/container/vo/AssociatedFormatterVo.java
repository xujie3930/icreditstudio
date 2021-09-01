package com.jinninghui.datasphere.icreditstudio.datasync.container.vo;

import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedData;
import lombok.Data;

import java.util.List;

/**
 * @author peng
 */
@Data
public class AssociatedFormatterVo {
    private String dialect;
    private List<TableInfo> sourceTables;
    private List<AssociatedData> assoc;
}
