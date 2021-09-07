package com.jinninghui.datasphere.icreditstudio.metadata.service.impl;

import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.metadata.common.DataSourceInfo;
import com.jinninghui.datasphere.icreditstudio.metadata.common.Database;
import com.jinninghui.datasphere.icreditstudio.metadata.common.WarehouseAddressProperties;
import com.jinninghui.datasphere.icreditstudio.metadata.common.WarehouseDataSource;
import com.jinninghui.datasphere.icreditstudio.metadata.service.MetadataService;
import com.jinninghui.datasphere.icreditstudio.metadata.service.param.MetadataQueryTargetSourceParam;
import com.jinninghui.datasphere.icreditstudio.metadata.service.result.TargetSourceInfo;
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
import java.util.stream.Collectors;

/**
 * @author Peng
 */
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
                }).collect(Collectors.toList());
        return BusinessResult.success(results);
    }
}
