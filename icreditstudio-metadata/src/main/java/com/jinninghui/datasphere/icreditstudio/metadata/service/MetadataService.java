package com.jinninghui.datasphere.icreditstudio.metadata.service;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.metadata.common.Database;
import com.jinninghui.datasphere.icreditstudio.metadata.common.WarehouseDataSource;
import com.jinninghui.datasphere.icreditstudio.metadata.service.param.MetadataQueryTargetSourceParam;
import com.jinninghui.datasphere.icreditstudio.metadata.service.result.TargetSourceInfo;
import com.jinninghui.datasphere.icreditstudio.metadata.web.request.MetadataQueryTargetSourceRequest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author Peng
 */
public interface MetadataService {
    /**
     * 获取数据库列表
     *
     * @return
     */
    List<Database> getDatabases();

    /**
     * 获取仓库源列表
     *
     * @return
     */
    List<WarehouseDataSource> getWarehouseDataSources();

    /**
     * 获取目标库列表
     * @param param
     * @return
     */
    BusinessResult<List<TargetSourceInfo>> targetSources(MetadataQueryTargetSourceParam param);
}
