package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedData;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Peng
 */
@Data
public class DataSyncGenerateWideTableRequest {

    /**
     * 数据源ID
     */
    @NotNull(message = "60000003")
    private String datasourceId;
    /**
     * 数据源方言
     */
    @NotBlank(message = "60000004")
    private String dialect;
    /**
     * 创建方式【0：可视化，1，SQL】
     */
    private Integer createMode;

    private String sql;
    /**
     * 连接表集合
     */
    @NotNull(message = "60000005")
    private List<TableInfo> sourceTables;
    /**
     * 关联关系
     */
    private List<AssociatedData> view;
}
