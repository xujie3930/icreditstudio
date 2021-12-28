package com.jinninghui.datasphere.icreditstudio.datasync.container.widetable.outside;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jinninghui.datasphere.icreditstudio.datasync.common.ResourceCodeBean;
import com.jinninghui.datasphere.icreditstudio.datasync.container.ConnectionSource;
import com.jinninghui.datasphere.icreditstudio.datasync.container.utils.AssociatedUtil;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedFormatterVo;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.ConnectionInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.CreateModeEnum;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.HiveMapJdbcTypeEnum;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.PartitionTypeEnum;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.DatasourceFeign;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.FeignConnectionInfoRequest;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.OutsideSourceWideTableParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTable;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTableFieldInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.WideTableFieldResult;
import com.jinninghui.datasphere.icreditstudio.framework.common.enums.DialectEnum;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author Peng
 */
@Slf4j
@Component
public class OracleWideTableHandler extends AbstractOutsideWideTableHandler {

    @Resource
    private DatasourceFeign datasourceFeign;

    @Override
    public String getDialect() {
        return DialectEnum.ORACLE.getDialect();
    }

    @Override
    public boolean isCurrentTypeHandler(OutsideSourceWideTableParam param) {
        return getDialect().equals(param.getDialect());
    }

    @Override
    public String getWideTableSql(OutsideSourceWideTableParam param) {
        String sql = null;
        if (CreateModeEnum.VISUAL == CreateModeEnum.find(param.getCreateMode())) {
            AssociatedFormatterVo vo = new AssociatedFormatterVo();
            vo.setDialect(param.getDialect());
            vo.setSourceTables(BeanCopyUtils.copy(param.getSourceTables(), TableInfo.class));
            vo.setAssoc(param.getView());
            sql = AssociatedUtil.wideTableSql(vo);
        } else {
            if (StringUtils.isNotBlank(param.getSql())) {
                sql = param.getSql();
            }
        }
        return sql;
    }

    @Override
    public String getDataSourceId(String sql, OutsideSourceWideTableParam param) {
        return param.getDatasourceId();
    }

    @Override
    public WideTable generate(String statement, String datasourceId) {
        ConnectionSource connectionSource = getConnectionSource(datasourceFeign, datasourceId);
        return smartConnection(connectionSource, statement, (connection, sql) -> {
            //宽表字段信息列表
            List<WideTableFieldInfo> wideTableFieldInfos = Lists.newArrayList();
            try {
                List<FieldInfo> fieldInfos = analysisRestructuring(sql, connection);
                if (CollectionUtils.isNotEmpty(fieldInfos)) {
                    for (int i = 0; i < fieldInfos.size(); i++) {
                        FieldInfo fieldInfo = fieldInfos.get(i);
                        WideTableFieldResult info = new WideTableFieldResult();
                        info.setSort(i);
                        info.setDatabaseName(fieldInfo.getDatabase());
                        info.setSourceTable(fieldInfo.getTableName());
                        info.setFieldName(fieldInfo.getFieldName());
                        info.setFieldChineseName(fieldInfo.getRemarks());
                        info.setRemark(fieldInfo.getRemarks());

                        HiveMapJdbcTypeEnum typeEnum = HiveMapJdbcTypeEnum.find(fieldInfo.getType().trim());
                        info.setFieldType(Lists.newArrayList(typeEnum.getCategoryEnum().getCode(), typeEnum.getHiveType()));
                        wideTableFieldInfos.add(info);
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                String message = e.getMessage();
                if ((message.contains("table") || message.contains("Table")) && message.contains("doesn't") && message.contains("exist")) {
                    throw new AppException("60000050", ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000050.getMessage());
                }
                throw new AppException("60000051", ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000051.getMessage());
            }
            WideTable wideTable = new WideTable();
            wideTable.setSql(sql);
            wideTable.setIncrementalFields(wideTableFieldInfos.stream().filter(Objects::nonNull).map(e -> new WideTable.Select(e.getFieldName(), e.getFieldName())).collect(Collectors.toList()));
            wideTable.setFields(wideTableFieldInfos);
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
            Properties properties = new Properties();
            properties.put("user", username);
            properties.put("password", password);
            properties.put("remarksReporting", "true");
            connection = DriverManager.getConnection(url, properties);
            apply = conn.apply(connection, s);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException("60000006", e.getMessage());
        } finally {
            IoUtil.close(connection);
        }
        return apply;
    }


    private List<FieldInfo> analysisRestructuring(String analysisSql, Connection connection) {
        List<FieldInfo> results = Lists.newArrayList();
        try {
            log.info("查询语句：" + analysisSql);
            List<String> dbTables = analysisContainsTable(analysisSql);
            log.info("语句中包含的库表：" + dbTables);
            Map<String, List<String>> dbMapTables = separationDBAndTable(dbTables);
            log.info("归集数据库和所属表：" + JSONObject.toJSONString(dbMapTables));
            List<FieldInfo> tableColumns = getTableColumns(connection, dbMapTables);
            log.info("表和字段映射：" + tableColumns);

            results = assembleStatement(tableColumns, analysisSql);
            log.info("最终生成的查询语句字段信息：" + JSONObject.toJSONString(results));
        } catch (Exception e) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000089.getCode());
        }
        return results;
    }

    /**
     * 分析sql中的数据库和下面的表
     *
     * @param analysisSql
     * @return 语句中包含的表[库.表名]
     */
    private List<String> analysisContainsTable(String analysisSql) {
        List<String> results = Lists.newArrayList();
        //解析数据库.表名称
        String from = StrUtil.subAfter(analysisSql, "from", true);
        List<String> join = StrUtil.split(from, "join");
        results = Optional.ofNullable(join).orElse(Lists.newArrayList())
                .stream()
                .filter(StringUtils::isNotBlank)
                .map(s -> {
                    String on = StrUtil.subBefore(s, "on", false);
                    List<String> split = StrUtil.split(on.trim(), " ");
                    String result = "";
                    if (org.apache.commons.collections.CollectionUtils.isNotEmpty(split)) {
                        String s1 = split.get(0);
                        result = s1;
                    }
                    return result;
                })
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    /**
     * 归集数据库和所属表
     *
     * @param dbTables [库.表名]
     * @return 数据库和所属表{库:[表，表]}
     */
    private Map<String, List<String>> separationDBAndTable(List<String> dbTables) {
        Map<String, List<String>> baseMapTable = Maps.newHashMap();
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(dbTables)) {
            for (String data : dbTables) {
                String[] split = data.split("\\.");
                if (split.length != 2) {
                    throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000087.getCode());
                }
                if (baseMapTable.containsKey(split[0])) {
                    baseMapTable.get(split[0]).add(split[1]);
                } else {
                    baseMapTable.put(split[0], Lists.newArrayList(split[1]));
                }
            }
        }
        return baseMapTable;
    }

    /**
     * @param connection 数据库连接
     * @param dbTables   {库：[表1，表2]}
     * @return 表和所属字段 {库.表:[字段1,字段2]}
     */
    private List<FieldInfo> getTableColumns(Connection connection, Map<String, List<String>> dbTables) {
        List<FieldInfo> results = Lists.newArrayList();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            String userName = metaData.getUserName();
            dbTables.forEach((k, v) -> {
                v.stream().forEach(tableName -> {
                    ResultSet columnResultSet = null;
                    try {
                        columnResultSet = metaData.getColumns(null, userName, tableName, "%");
                        while (columnResultSet.next()) {
                            // 字段名称
                            String columnName = columnResultSet.getString("COLUMN_NAME");
                            String columnTypeName = columnResultSet.getString("TYPE_NAME");
                            String remarks = columnResultSet.getString("REMARKS");

                            String wideField = new StringJoiner(".").add(k).add(tableName).add(columnName).toString();
                            FieldInfo info = new FieldInfo(k, tableName, columnName, columnTypeName, remarks, wideField);
                            results.add(info);
                        }
                    } catch (Exception e) {
                        throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000088.getCode());
                    } finally {
                        IoUtil.close(columnResultSet);
                    }
                });
            });
        } catch (Exception e) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000088.getCode());
        }
        return results;
    }

    /**
     * 拼装查询sql
     *
     * @param tableColumns 表字段集合 {表：[{字段，类型}]}
     * @param oldStatement 原查询语句
     * @return 拼装后的查询语句
     */
    private List<FieldInfo> assembleStatement(List<FieldInfo> tableColumns, String oldStatement) {
        List<FieldInfo> results = Lists.newArrayList();
        results = tableColumns;

        if (!StringUtils.contains(oldStatement, "*")) {
            String from = StrUtil.subBefore(oldStatement, "from", true);
            String select = StrUtil.subAfter(from, "select", true);
            List<String> split = StrUtil.split(select, ",");

            List<FieldInfo> temp = Lists.newArrayList();
            for (FieldInfo tableColumn : tableColumns) {
                for (String s : split) {
                    if (tableColumn.getWideField().trim().equalsIgnoreCase(s.trim())) {
                        temp.add(tableColumn);
                    }
                }
            }
            results = temp;
        }
        return results;
    }

    @Data
    @AllArgsConstructor
    static class FieldInfo {
        private String database;
        private String tableName;
        private String fieldName;
        private String type;
        private String remarks;
        //库.表.字段
        private String wideField;
    }
}
