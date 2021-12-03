package com.jinninghui.datasphere.icreditstudio.datasync.service.param;

import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedData;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.SyncCondition;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTableFieldRequest;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author Peng
 */
@Data
public class SyncStepTwoParam {
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 目标库名
     */
    private String targetSource;
    /**
     * 宽表名称
     */
    private String wideTableName;
    /**
     * 同步条件
     */
    private SyncCondition syncCondition;

    public SyncCondition getSyncCondition() {
        if (Objects.isNull(syncCondition)) {
            return new SyncCondition();
        }
        return syncCondition;
    }

    /**
     * 【0：外部数据库，1：本地文件，2：区块链数据】
     */
    private Integer sourceType;
    /**
     * 数据源方言
     */
    private String dialect;
    /**
     * 识别宽表的sql
     */
    private String sql;
    /**
     * 连接表集合
     */
    private List<TableInfo> sourceTables;

    public List<TableInfo> getSourceTables() {
        if (CollectionUtils.isEmpty(sourceTables)) {
            return Lists.newArrayList();
        }
        return sourceTables;
    }

    /**
     * 关联关系
     */
    private List<AssociatedData> view;

    public List<AssociatedData> getView() {
        if (CollectionUtils.isEmpty(view)) {
            return Lists.newArrayList();
        }
        return view;
    }

    /**
     * 宽表字段
     */
    private List<WideTableFieldRequest> fieldInfos;

    /**
     * 数据源ID
     */
    private String datasourceId;
}
