package com.jinninghui.datasphere.icreditstudio.datasync.container.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractWideTableHandler;
import com.jinninghui.datasphere.icreditstudio.datasync.container.utils.AssociatedUtil;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedFormatterVo;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.ConnectionInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.CreateModeEnum;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.HiveMapJdbcTypeEnum;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.PartitionTypeEnum;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.DatasourceFeign;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.FeignConnectionInfoRequest;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.FeignDataSourcesRequest;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DataSyncGenerateWideTableParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DatasourceInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTable;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTableFieldInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTableFieldResult;
import com.jinninghui.datasphere.icreditstudio.datasync.web.request.DataSyncGenerateWideTableRequest;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Peng
 */
@Slf4j
@Component
public class MySqlWideTableHandler extends AbstractWideTableHandler {
    @Resource
    private DatasourceFeign datasourceFeign;

    @Override
    public String getDialect() {
        return "mysql";
    }

    @Override
    public String getWideTableSql(DataSyncGenerateWideTableParam param) {
        String sql;
        if (CreateModeEnum.VISUAL == CreateModeEnum.find(param.getCreateMode())) {
            AssociatedFormatterVo vo = new AssociatedFormatterVo();
            vo.setDialect(param.getDialect());
            vo.setSourceTables(BeanCopyUtils.copy(param.getSourceTables(), TableInfo.class));
            vo.setAssoc(param.getView());
            sql = AssociatedUtil.wideTableSql(vo);
        } else {
            DataSyncGenerateWideTableRequest.SqlInfo sqlInfo = param.getSqlInfo();
            sql = sqlInfo.getSql();
        }
        return sql;
    }

    @Override
    public boolean verifySql(DataSyncGenerateWideTableParam param) {
        return true;
    }

    @Override
    public String getDataSourceId(DataSyncGenerateWideTableParam param) {
        String dataSourceId = null;
        if (CreateModeEnum.VISUAL == CreateModeEnum.find(param.getCreateMode())) {
            dataSourceId = param.getDatasourceId();
        } else {
            if (CollectionUtils.isNotEmpty(param.getSqlInfo().getDatabaseHost())) {
                dataSourceId = param.getSqlInfo().getDatabaseHost().get(0).getDatasourceId();
            } else {
                String wideTableSql = getWideTableSql(param);
                String from = StrUtil.subAfter(wideTableSql, "from", true);
                String database = StrUtil.subBefore(StrUtil.trim(from), " ", true);
                FeignDataSourcesRequest feignRequest = new FeignDataSourcesRequest();
                feignRequest.setDatabaseName(database);
                BusinessResult<List<DatasourceInfo>> dataSources = datasourceFeign.getDataSources(feignRequest);
                if (dataSources.isSuccess()) {
                    List<DatasourceInfo> data = dataSources.getData();
                    if (CollectionUtils.isNotEmpty(data)) {
                        DatasourceInfo datasourceInfo = data.get(0);
                        dataSourceId = datasourceInfo.getId();
                    }
                }
            }
        }
        return dataSourceId;
    }

    @Override
    public List<TableInfo> getTableInfos(DataSyncGenerateWideTableParam param) {
        List<TableInfo> results = Lists.newArrayList();
        if (CreateModeEnum.VISUAL == CreateModeEnum.find(param.getCreateMode())) {
            results.addAll(param.getSourceTables());
        } else {

        }
        return results;
    }

    @Override
    public WideTable generate(String sql, String datasourceId, List<TableInfo> tableInfos) {
        ConnectionInfo info = getConnectionInfo(datasourceId);
        Connection connection = null;
        WideTable wideTable = null;
        try {
            wideTable = new WideTable();
            connection = AssociatedUtil.getConnection(info);
            ResultSetMetaData metaData = AssociatedUtil.getResultSetMetaData(connection, sql);

//            List<TableInfo> sourceTables = param.getSourceTables();
            Map<String, String> tableFieldRemark = getDatabaseTableFieldRemark(tableInfos, info);

            int columnCount = metaData.getColumnCount();
            List<WideTableFieldInfo> fieldInfos = Lists.newArrayList();
            for (int i = 1; i <= columnCount; i++) {
                WideTableFieldResult fieldInfo = new WideTableFieldResult();
                fieldInfo.setSort(i);
                fieldInfo.setFieldName(metaData.getColumnName(i));
                HiveMapJdbcTypeEnum typeEnum = HiveMapJdbcTypeEnum.find(metaData.getColumnTypeName(i));
                fieldInfo.setFieldType(Lists.newArrayList(typeEnum.getCategoryEnum().getCode(), typeEnum.getHiveType()));
                fieldInfo.setSourceTable(metaData.getTableName(i));
                String key = new StringJoiner(".").add(metaData.getCatalogName(i)).add(metaData.getTableName(i)).add(metaData.getColumnName(i)).toString();
                fieldInfo.setFieldChineseName(tableFieldRemark.get(key));
                fieldInfos.add(fieldInfo);
            }
            wideTable.setFields(fieldInfos);
            wideTable.setSql(sql);
            //分区字段
            wideTable.setPartitions(Arrays.stream(PartitionTypeEnum.values()).map(e -> new WideTable.Select(e.getName(), e.getName())).collect(Collectors.toList()));
            //增量字段
            wideTable.setIncrementalFields(fieldInfos.stream().filter(Objects::nonNull).map(e -> new WideTable.Select(e.getFieldName(), e.getFieldName())).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("识别宽表失败", e);
            throw new AppException("60000020");
        } finally {
            IoUtil.close(connection);
        }
        return wideTable;
    }

    private ConnectionInfo getConnectionInfo(String datasourceId) {
        FeignConnectionInfoRequest build = FeignConnectionInfoRequest.builder()
                .datasourceId(datasourceId)
                .build();
        BusinessResult<ConnectionInfo> connectionInfo = datasourceFeign.getConnectionInfo(build);
        if (connectionInfo.isSuccess() && Objects.nonNull(connectionInfo.getData())) {
            return connectionInfo.getData();
        }
        throw new AppException("60000006");
    }

    private Map<String, String> getDatabaseTableFieldRemark(List<TableInfo> tableInfos, ConnectionInfo connectionInfo) {
        Map<String, String> results = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(tableInfos) && Objects.nonNull(connectionInfo)) {
            ConnectionInfo connectionInfoCopy = BeanCopyUtils.copyProperties(connectionInfo, ConnectionInfo.class);
            String url = connectionInfoCopy.getUrl();
            //拆分数据库前后字符串
            String s = StrUtil.subBefore(url, "?", true);
            String suffix = StrUtil.subAfter(url, "?", true);
            String prefix = StrUtil.subBefore(s, "/", true);
            //url模板
            Function<String, String> urlModel = database -> new StringJoiner("").add(prefix).add("/").add(database).add("?").add(suffix).toString();
            tableInfos.parallelStream()
                    .filter(Objects::nonNull)
                    .forEach(tableInfo -> {
                        Connection connection = null;
                        Map<String, String> result = null;
                        try {
                            String database = tableInfo.getDatabase();
                            String applyUrl = urlModel.apply(database);
                            ConnectionInfo info = new ConnectionInfo();
                            BeanCopyUtils.copyProperties(connectionInfoCopy, info);
                            info.setUrl(applyUrl);
                            connection = AssociatedUtil.getConnection(info);
                            DatabaseMetaData metaData = connection.getMetaData();
                            result = getTableFieldRemark(metaData, Lists.newArrayList(new ImmutablePair<>(tableInfo.getDatabase(), tableInfo.getTableName())));
                            results.putAll(result);
                        } catch (Exception e) {
                            log.error("取得数据库表字段信息失败", e);
                        } finally {
                            IoUtil.close(connection);
                        }
                    });
        }
        return results;
    }

    private Map<String, String> getTableFieldRemark(DatabaseMetaData metaData, List<ImmutablePair<String, String>> pairs) throws Exception {
        Map<String, String> results = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(pairs)) {
            for (ImmutablePair<String, String> pair : pairs) {
                ResultSet columnResultSet = metaData.getColumns(null, "%", pair.getRight(), "%");
                while (columnResultSet.next()) {
                    // 字段名称
                    String columnName = columnResultSet.getString("COLUMN_NAME");
                    if (StringUtils.isNotBlank(columnName)) {
                        String key = new StringJoiner(".").add(pair.getLeft()).add(pair.getRight()).add(columnName).toString();
                        // 描述
                        String remarks = columnResultSet.getString("REMARKS");
                        results.put(key, remarks);
                    }
                }
            }
        }
        return results;
    }
}
