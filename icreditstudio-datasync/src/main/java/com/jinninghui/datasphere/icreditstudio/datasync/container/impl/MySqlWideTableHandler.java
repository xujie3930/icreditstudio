package com.jinninghui.datasphere.icreditstudio.datasync.container.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractWideTableHandler;
import com.jinninghui.datasphere.icreditstudio.datasync.container.ConnectionSource;
import com.jinninghui.datasphere.icreditstudio.datasync.container.utils.AssociatedUtil;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedFormatterVo;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.ConnectionInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.CreateModeEnum;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.DatasourceTypeEnum;
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
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.*;
import java.util.*;
import java.util.function.BiFunction;
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
    public boolean isCurrentWideTable(DataSyncGenerateWideTableParam param) {
        //可视化创建方式直接判断dialect
        if (CreateModeEnum.VISUAL == CreateModeEnum.find(param.getCreateMode())) {
            return getDialect().equals(param.getDialect());
        } else {//sql创建方式
            //不同主机相同数据库情况，选择了host
            if (Objects.nonNull(param.getSqlInfo()) && CollectionUtils.isNotEmpty(param.getSqlInfo().getDatabaseHost())) {
                String datasourceId = param.getSqlInfo().getDatabaseHost().get(0).getDatasourceId();

                FeignDataSourcesRequest request = new FeignDataSourcesRequest();
                request.setDatasourceId(datasourceId);
                return isSameDialect(request, getDialect());
            } else {//没选择host,根据数据库名称定位数据源类型
                String sql = param.getSqlInfo().getSql();
                String database = parseDatabaseNameFromSql(sql);
                FeignDataSourcesRequest request = new FeignDataSourcesRequest();
                request.setDatabaseName(database);
                return isSameDialect(request, getDialect());
            }
        }
    }

    private boolean isSameDialect(FeignDataSourcesRequest request, String dialect) {
        BusinessResult<List<DatasourceInfo>> dataSources = datasourceFeign.getDataSources(request);
        if (dataSources.isSuccess() && CollectionUtils.isNotEmpty(dataSources.getData())) {
            DatasourceInfo datasourceInfo = dataSources.getData().get(0);
            return DatasourceTypeEnum.findDatasourceTypeByType(datasourceInfo.getType()).getDesc().equals(dialect);
        }
        return false;
    }

    @Override
    public String getDialect() {
        return "mysql";
    }

    @Override
    public String getWideTableSql(DataSyncGenerateWideTableParam param) {
        String sql = null;
        if (CreateModeEnum.VISUAL == CreateModeEnum.find(param.getCreateMode())) {
            AssociatedFormatterVo vo = new AssociatedFormatterVo();
            vo.setDialect(param.getDialect());
            vo.setSourceTables(BeanCopyUtils.copy(param.getSourceTables(), TableInfo.class));
            vo.setAssoc(param.getView());
            sql = AssociatedUtil.wideTableSql(vo);
        } else {
            if (Objects.nonNull(param.getSqlInfo())) {
                sql = param.getSqlInfo().getSql();
            }
        }
        return sql;
    }

    @Override
    public boolean verifySql(String sql, DataSyncGenerateWideTableParam param) {
        if (StringUtils.isBlank(sql)) {
            throw new AppException("60000024");
        }
        return true;
    }

    @Override
    public List<DataSyncGenerateWideTableRequest.DatabaseInfo> checkDatabaseFromSql(String sql) {
        List<DataSyncGenerateWideTableRequest.DatabaseInfo> results = Lists.newArrayList();
        String database = parseDatabaseNameFromSql(sql);
        FeignDataSourcesRequest request = new FeignDataSourcesRequest();
        request.setDatabaseName(database);
        BusinessResult<List<DatasourceInfo>> dataSources = datasourceFeign.getDataSources(request);
        if (dataSources.isSuccess() && CollectionUtils.isNotEmpty(dataSources.getData())) {
            List<DatasourceInfo> data = dataSources.getData();
            Map<String, List<DataSyncGenerateWideTableRequest.DatabaseInfo>> collect = data.stream()
                    .filter(Objects::nonNull)
                    .map(info -> {
                        DataSyncGenerateWideTableRequest.DatabaseInfo databaseInfo = new DataSyncGenerateWideTableRequest.DatabaseInfo();
                        databaseInfo.setDatasourceId(info.getId());
                        databaseInfo.setDatabaseName(database);
                        String uri = info.getUri();
                        String prefix = StrUtil.subBefore(uri, "?", false);
                        String temp = StrUtil.subBefore(prefix, ":", true);
                        String host = StrUtil.subAfter(temp, "//", true);
                        databaseInfo.setHost(host);
                        return databaseInfo;
                    }).collect(Collectors.groupingBy(DataSyncGenerateWideTableRequest.DatabaseInfo::getHost));
            if (collect.size() > 1) {
                collect.forEach((k, v) -> {
                    results.add(v.get(0));
                });
            }
        }
        return results;
    }

    @Override
    public String getDataSourceId(String sql, DataSyncGenerateWideTableParam param) {
        String dataSourceId = null;
        if (CreateModeEnum.VISUAL == CreateModeEnum.find(param.getCreateMode())) {
            dataSourceId = param.getDatasourceId();
        } else {
            if (Objects.nonNull(param.getSqlInfo()) && CollectionUtils.isNotEmpty(param.getSqlInfo().getDatabaseHost())) {
                dataSourceId = param.getSqlInfo().getDatabaseHost().get(0).getDatasourceId();
            } else {
                //从查询sql中解析数据库名称
                String database = parseDatabaseNameFromSql(sql);
                FeignDataSourcesRequest feignRequest = new FeignDataSourcesRequest();
                feignRequest.setDatabaseName(database);
                BusinessResult<List<DatasourceInfo>> dataSources = datasourceFeign.getDataSources(feignRequest);
                if (dataSources.isSuccess() && CollectionUtils.isNotEmpty(dataSources.getData())) {
                    dataSourceId = dataSources.getData().get(0).getId();
                }
            }
        }
        if (StringUtils.isBlank(dataSourceId)) {
            throw new AppException("60000025");
        }
        return dataSourceId;
    }

    private String parseDatabaseNameFromSql(String sql) {
        String from = StrUtil.subAfter(sql, "from", true);
        String databaseTable = StrUtil.subBefore(StrUtil.trim(from), " ", false);
        String database = StrUtil.subBefore(databaseTable, ".", false);
        return database;
    }

    @Override
    public WideTable generate(String statement, String datasourceId) {
        ConnectionSource connectionSource = getConnectionSource(datasourceFeign, datasourceId);
        return smartConnection(connectionSource, statement, (connection, sql) -> {
            //宽表字段信息列表
            List<WideTableFieldInfo> fieldInfos = Lists.newArrayList();
            try {
                DatabaseMetaData metaData = connection.getMetaData();
                PreparedStatement state = connection.prepareStatement(sql);
                ResultSetMetaData rsMetadata = state.getMetaData();
                //数据库和表映射
                Map<String, List<String>> databaseTables = Maps.newHashMap();

                for (int i = 1; i <= rsMetadata.getColumnCount(); i++) {
                    String catalogName = rsMetadata.getCatalogName(i);
                    String tableName = rsMetadata.getTableName(i);
                    //宽表字段信息
                    WideTableFieldResult fieldInfo = new WideTableFieldResult();
                    fieldInfo.setSort(i);
                    fieldInfo.setFieldName(rsMetadata.getColumnName(i));
                    HiveMapJdbcTypeEnum typeEnum = HiveMapJdbcTypeEnum.find(rsMetadata.getColumnTypeName(i));
                    fieldInfo.setFieldType(Lists.newArrayList(typeEnum.getCategoryEnum().getCode(), typeEnum.getHiveType()));
                    fieldInfo.setSourceTable(tableName);
                    fieldInfo.setDatabaseName(catalogName);

                    fieldInfos.add(fieldInfo);
                    //添加数据库和表的映射
                    addDatabaseTables(databaseTables, catalogName, tableName);
                }
                //数据库字段注释内容
                Map<String, String> comments = getComments(metaData, databaseTables);
                //将数据库字段注释设置为字段中文名
                for (WideTableFieldInfo fieldInfo : fieldInfos) {
                    String databaseName = fieldInfo.getDatabaseName();
                    String sourceTable = fieldInfo.getSourceTable();
                    String fieldName = fieldInfo.getFieldName();
                    String key = new StringJoiner(".").add(databaseName).add(sourceTable).add(fieldName).toString();
                    String comment = comments.get(key);
//                    fieldInfo.setFieldChineseName(comment);
                    fieldInfo.setRemark(comment);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new AppException("60000006", e.getMessage());
            }
            WideTable wideTable = new WideTable();
            wideTable.setTableName(null);
            wideTable.setSql(sql);
            wideTable.setIncrementalFields(fieldInfos.stream().filter(Objects::nonNull).map(e -> new WideTable.Select(e.getFieldName(), e.getFieldName())).collect(Collectors.toList()));
            wideTable.setFields(fieldInfos);
            wideTable.setPartitions(Arrays.stream(PartitionTypeEnum.values()).map(e -> new WideTable.Select(e.getName(), new StringJoiner("_").add(e.getName()).toString())).collect(Collectors.toList()));
            return wideTable;
        });
    }

    /**
     * 根据数据源ID取得连接信息
     *
     * @param datasourceFeign datasource服务调用对象
     * @param datasourceId    数据源ID
     * @return 数据库连接对象
     */
    static ConnectionSource getConnectionSource(DatasourceFeign datasourceFeign, String datasourceId) {
        FeignConnectionInfoRequest build = FeignConnectionInfoRequest.builder()
                .datasourceId(datasourceId)
                .build();
        BusinessResult<ConnectionInfo> connectionInfo = datasourceFeign.getConnectionInfo(build);
        log.info("获取的连接信息", JSONObject.toJSONString(connectionInfo));
        if (connectionInfo.isSuccess() && Objects.nonNull(connectionInfo.getData())) {
            return connectionInfo.getData();
        }
        throw new AppException("60000006", connectionInfo.getReturnMsg());
    }

    /**
     * 数据库和表映射
     *
     * @param map   数据库和表映射
     * @param key   数据库名称
     * @param value 表名称
     */
    private void addDatabaseTables(Map<String, List<String>> map, String key, String value) {
        List<String> tables = map.get(key);
        if (CollectionUtils.isNotEmpty(tables)) {
            tables.add(value);
        } else {
            map.put(key, Lists.newArrayList(value));
        }
    }

    /**
     * 取得表注释map
     * 连接关闭前操作
     *
     * @param metaData       数据库元信息
     * @param databaseTables 数据库和表映射
     * @return 表字段注释
     */
    private Map<String, String> getComments(DatabaseMetaData metaData, Map<String, List<String>> databaseTables) {
        Map<String, String> results = Maps.newHashMap();
        try {
            for (Map.Entry<String, List<String>> entry : databaseTables.entrySet()) {
                String catalog = entry.getKey();
                for (String table : entry.getValue()) {
                    ResultSet columns = metaData.getColumns(catalog, "%", table, "%");
                    while (columns.next()) {
                        String remarks = columns.getString("REMARKS");
                        String columnName = columns.getString("COLUMN_NAME");
                        results.put(new StringJoiner(".").add(catalog).add(table).add(columnName).toString(), remarks);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return results;
    }

    /**
     * 取得jdbc连接
     *
     * @param source 数据库连接对象
     * @param conn   数据库连接
     * @param <S>    做为function第二个参数
     * @param <T>    返回值类型
     * @return 返回值对象
     */
    static <S, T> T smartConnection(ConnectionSource source, S s, BiFunction<Connection, S, T> conn) {
        T apply;
        Connection connection = null;
        try {
            String username = source.getUsername();
            String password = source.getPassword();
            String url = source.getUrl();
            connection = DriverManager.getConnection(url, username, password);
            apply = conn.apply(connection, s);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException("60000006", e.getMessage());
        } finally {
            IoUtil.close(connection);
        }
        return apply;
    }
}
