package com.jinninghui.datasphere.icreditstudio.datasync.container.widetable;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.DatasourceFeign;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.FeignDataSourcesRequest;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DatasourceInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.PreSqlPositionDataSourceResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Peng
 */
@Slf4j
@Component
public class RelationQueryStatementParseHandler extends AbstractQueryStatementParseHandler {

    @Resource
    private DatasourceFeign datasourceFeign;

    @Override
    public String getDialect() {
        return "relation";
    }

    @Override
    public boolean isCurrentHandler(String queryStat) {
        if (StrUtil.containsIgnoreCase(queryStat, "select") && StrUtil.containsIgnoreCase(queryStat, "from")) {
            return true;
        }
        return false;
    }

    @Override
    public List<PreSqlPositionDataSourceResult.DatabaseInfo> getDataSourceInfo(Integer sourceType, String queryStat, String workspaceId) {
        String databaseName = parseDatabaseNameFromSql(queryStat);

        List<PreSqlPositionDataSourceResult.DatabaseInfo> result = null;
        if (StringUtils.isNotBlank(databaseName)) {
            FeignDataSourcesRequest request = new FeignDataSourcesRequest();
            request.setDatabaseName(databaseName);
            request.setWorkspaceId(workspaceId);
            request.setSourceType(sourceType);
            BusinessResult<List<DatasourceInfo>> dataSources = datasourceFeign.getDataSources(request);
            if (dataSources.isSuccess() && Objects.nonNull(dataSources.getData())) {
                Map<String, PreSqlPositionDataSourceResult.DatabaseInfo> distinctMap = Maps.newHashMap();
                dataSources.getData()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(entity -> {
                            PreSqlPositionDataSourceResult.DatabaseInfo info = new PreSqlPositionDataSourceResult.DatabaseInfo();
                            info.setDatabaseName(entity.getDatabaseName());
                            info.setDatasourceId(entity.getId());
                            info.setHost(entity.getHost());
                            info.setDialect(entity.getDialect());
                            return info;
                        }).forEach(entity -> {
                    distinctMap.put(new StringJoiner("").add(entity.getHost()).add(entity.getDatabaseName()).add(entity.getDialect()).toString(), entity);
                });

                result = distinctMap.values().stream().collect(Collectors.toList());
            }
        }
        return Optional.ofNullable(result).orElse(Lists.newArrayList());
    }

    private String parseDatabaseNameFromSql(String sql) {
        String from = StrUtil.subAfter(sql, "from", true);
        String databaseTable = StrUtil.subBefore(StrUtil.trim(from), " ", false);
        String database = StrUtil.subBefore(databaseTable, ".", false);
        return database;
    }
}
