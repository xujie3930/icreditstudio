package com.jinninghui.datasphere.icreditstudio.metadata.service.impl;

import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.metadata.common.DataSourceInfo;
import com.jinninghui.datasphere.icreditstudio.metadata.common.Database;
import com.jinninghui.datasphere.icreditstudio.metadata.common.WarehouseAddressProperties;
import com.jinninghui.datasphere.icreditstudio.metadata.common.WarehouseDataSource;
import com.jinninghui.datasphere.icreditstudio.metadata.service.MetadataService;
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
        List<WarehouseDataSource> warehouseDataSources = getWarehouseDataSources();
        List<List<String>> collect = warehouseDataSources.parallelStream()
                .filter(Objects::nonNull)
                .map(dataSource -> {
                    List<String> databases = Lists.newArrayList();
                    Statement stmt = dataSource.getStmt();
                    String sql = "show databases";
                    try {
                        ResultSet resultSet = stmt.executeQuery(sql);
                        while (resultSet.next()) {
                            String database = resultSet.getString(1);
                            databases.add(database);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    return databases;
                }).collect(Collectors.toList());
        return null;
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
}
