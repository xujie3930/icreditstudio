package com.jinninghui.datasphere.icreditstudio.datasource.service.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Peng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
     * 选中状态
     */
    private boolean select;
    /**
     * 数据源ID
     */
    private String datasourceId;
    /**
     * 内容
     */
    private List<DatasourceCatalogue> content;
}
