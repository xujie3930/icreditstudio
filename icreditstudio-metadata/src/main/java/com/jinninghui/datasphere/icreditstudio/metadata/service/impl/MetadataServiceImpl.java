package com.jinninghui.datasphere.icreditstudio.metadata.service.impl;

import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.metadata.common.DataSourceInfo;
import com.jinninghui.datasphere.icreditstudio.metadata.common.Database;
import com.jinninghui.datasphere.icreditstudio.metadata.common.WarehouseAddressProperties;
import com.jinninghui.datasphere.icreditstudio.metadata.common.WarehouseDataSource;
import com.jinninghui.datasphere.icreditstudio.metadata.service.MetadataService;
import com.jinninghui.datasphere.icreditstudio.metadata.service.param.MetadataGenerateWideTableParam;
import com.jinninghui.datasphere.icreditstudio.metadata.service.param.MetadataQueryTargetSourceParam;
import com.jinninghui.datasphere.icreditstudio.metadata.service.param.StatementField;
import com.jinninghui.datasphere.icreditstudio.metadata.service.result.TargetSourceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author Peng
 */
@Slf4j
@Service
@EnableConfigurationProperties(WarehouseAddressProperties.class)
public class MetadataServiceImpl implements MetadataService {
    @Resource
    private WarehouseAddressProperties addressProperties;

    @Override
    public List<Database> getDatabases() {
        List<Database> results;
        List<WarehouseDataSource> warehouseDataSources = getWarehouseDataSources();
        results = warehouseDataSources.parallelStream()
                .filter(Objects::nonNull)
                .map(dataSource -> {
                    List<Database> databases = Lists.newArrayList();
                    String address = dataSource.getDataSourceInfo().getAddress();

                    Statement stmt = dataSource.getStmt();
                    String sql = "show databases";
                    try {
                        ResultSet resultSet = stmt.executeQuery(sql);
                        while (resultSet.next()) {
                            Database db = new Database();
                            String database = resultSet.getString(1);
                            db.setDatabaseName(database);
                            db.setHost(address);
                            databases.add(db);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    return databases;
                }).flatMap(databases -> databases.stream()).collect(Collectors.toList());
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    private Optional<WarehouseDataSource> getWarehouseDataSource(String address) {
        List<WarehouseDataSource> warehouseDataSources = getWarehouseDataSources();
        return warehouseDataSources.parallelStream()
                .filter(Objects::nonNull)
                .filter(dataSource -> {
                    DataSourceInfo dataSourceInfo = dataSource.getDataSourceInfo();
                    String address1 = dataSourceInfo.getAddress();
                    return StringUtils.equals(address1, address);
                }).findFirst();
    }

    @Override
    public List<WarehouseDataSource> getWarehouseDataSources() {
        List<WarehouseDataSource> results = null;
        List<DataSourceInfo> address = addressProperties.getAddress();
        results = Optional.ofNullable(address).orElse(Lists.newArrayList())
                .parallelStream()
                .map(dataSourceInfo -> {
                    WarehouseDataSource dataSource = new WarehouseDataSource();
                    dataSource.setDataSourceInfo(dataSourceInfo);
                    return dataSource;
                }).collect(Collectors.toList());
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    @Override
    public BusinessResult<List<TargetSourceInfo>> targetSources(MetadataQueryTargetSourceParam param) {
        List<Database> databases = getDatabases();
        List<TargetSourceInfo> results = databases.parallelStream()
                .filter(Objects::nonNull)
                .map(database -> {
                    TargetSourceInfo info = new TargetSourceInfo();
                    info.setName(database.getDatabaseName());
                    info.setUrl(database.getHost());
                    return info;
                }).filter(info -> StringUtils.isBlank(param.getName()) || info.getName().contains(param.getName()))
                .collect(Collectors.toList());
        return BusinessResult.success(results);
    }

    @Override
    public BusinessResult<Boolean> generateWideTable(MetadataGenerateWideTableParam param) {
        String statementSql = generateWideTableStatement(param);
        List<WarehouseDataSource> warehouseDataSources = getWarehouseDataSources();
        if (CollectionUtils.isNotEmpty(warehouseDataSources)) {
            WarehouseDataSource dataSource = warehouseDataSources.get(0);
            String useSql = "use " + param.getDatabaseName();
            Statement stmt = dataSource.getStmt();
            try {
                stmt.execute(useSql);
                stmt.execute(statementSql);
            } catch (Exception ex) {
                log.error("执行hive命令失败", ex);
                throw new AppException("80000000", ex.getMessage());
            }
        } else {
            throw new AppException("80000001");
        }
        return BusinessResult.success(true);
    }

    private String generateWideTableStatement(MetadataGenerateWideTableParam param) {
        //语句前缀
        String prefix = "create table ";

        List<StatementField> fieldList = param.getFieldList();
        StringJoiner sj = null;
        if (CollectionUtils.isNotEmpty(fieldList)) {
            sj = new StringJoiner(",", "(", ")");
            for (StatementField field : fieldList) {
                StringJoiner sj1 = new StringJoiner(" ");
                sj1.add(field.getFieldName());
                sj1.add(field.getFieldType());
                String filed = sj1.toString();
                sj.add(filed);
            }
        }
        //分区
        String partitionCondition = new StringJoiner(" ").add("partitioned by (").add(param.getPartition()).add(" string)").toString();
        //分隔符
        String delimiterCondition = new StringJoiner(" ").add("row format delimited fields terminated by ").add("\'" + param.getDelimiter() + "\'").toString();

        String wideTableName = param.getWideTableName();
        StringJoiner statement = null;
        if (StringUtils.isNotBlank(param.getDatabaseName()) && StringUtils.isNotBlank(wideTableName) && Objects.nonNull(sj)) {
            statement = new StringJoiner(" ");
            statement.add(prefix);
            statement.add(new StringJoiner(".").add(param.getDatabaseName()).add(wideTableName).toString());
            statement.add(sj.toString());
            if (StringUtils.isNotBlank(param.getPartition())) {
                statement.add(partitionCondition);
            }
            if (StringUtils.isNotEmpty(param.getDelimiter())) {
                statement.add(delimiterCondition);
            }
        }
        return statement.toString();
    }
}
