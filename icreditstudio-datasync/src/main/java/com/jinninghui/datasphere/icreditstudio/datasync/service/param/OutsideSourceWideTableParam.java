package com.jinninghui.datasphere.icreditstudio.datasync.service.param;

import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedData;
import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class OutsideSourceWideTableParam {

    /**
     * 创建方式【0：可视化，1，SQL】
     */
    private Integer createMode;

    private String sql;

    /**
     * 数据源ID
     */
    private String datasourceId;
    /**
     * 数据源方言
     */
    private String dialect;
    /**
     * 连接表集合
     */
    private List<TableInfo> sourceTables;
    /**
     * 关联关系
     */
    private List<AssociatedData> view;
}
