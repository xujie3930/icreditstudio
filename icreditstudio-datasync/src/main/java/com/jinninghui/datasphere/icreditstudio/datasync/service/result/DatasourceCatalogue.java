package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class DatasourceCatalogue {
    /**
     * 名称
     */
    private String name;
    /**
     * 方言
     */
    private String dialect;
    /**
     * 链接地址
     */
    private String url;
    /**
     * 数据源ID
     */
    private String datasourceId;
    /**
     * 内容
     */
    private List<DatasourceCatalogue> content;
}
