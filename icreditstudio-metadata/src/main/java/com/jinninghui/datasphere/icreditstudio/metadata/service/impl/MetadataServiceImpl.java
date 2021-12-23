package com.jinninghui.datasphere.icreditstudio.metadata.service.impl;

import cn.hutool.core.io.IoUtil;
import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.metadata.common.Database;
import com.jinninghui.datasphere.icreditstudio.metadata.common.ResourceCodeBean;
import com.jinninghui.datasphere.icreditstudio.metadata.entity.WorkspaceTableEntity;
import com.jinninghui.datasphere.icreditstudio.metadata.service.AbstractClusterHiveConnectionSource;
import com.jinninghui.datasphere.icreditstudio.metadata.service.MetadataConnection;
import com.jinninghui.datasphere.icreditstudio.metadata.service.MetadataService;
import com.jinninghui.datasphere.icreditstudio.metadata.service.WorkspaceTableService;
import com.jinninghui.datasphere.icreditstudio.metadata.service.param.MetadataGenerateWideTableParam;
import com.jinninghui.datasphere.icreditstudio.metadata.service.param.MetadataQueryTargetSourceParam;
import com.jinninghui.datasphere.icreditstudio.metadata.service.param.StatementField;
import com.jinninghui.datasphere.icreditstudio.metadata.service.result.TargetSourceInfo;
import com.jinninghui.datasphere.icreditstudio.metadata.service.result.WarehouseInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author Peng
 */
@Slf4j
@Service
public class MetadataServiceImpl implements MetadataService {
    @Resource
    private MetadataConnection connection;
    @Autowired
    private AbstractClusterHiveConnectionSource connectionSource;
    @Resource
    private WorkspaceTableService workspaceTableService;

    @Override
    public List<Database> getDatabases() {
        List<Database> results = Lists.newArrayList();
        Connection connection = this.connection.getConnection();
        String sql = "show databases";
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                Database db = new Database();
                String database = resultSet.getString(1);
                db.setDatabaseName(database);
                db.setHost("*");
                results.add(db);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AppException("80000000", e.getMessage());
        } finally {
            IoUtil.close(resultSet);
            IoUtil.close(stmt);
            IoUtil.close(connection);
        }
        return results;
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
                    info.setId("*");
                    return info;
                }).filter(info -> StringUtils.isBlank(param.getName()) || info.getName().contains(param.getName()))
                .collect(Collectors.toList());
        return BusinessResult.success(results);
    }

    @Override
    public BusinessResult<Boolean> generateWideTable(MetadataGenerateWideTableParam param) {
        String statementSql = generateWideTableStatement(param);
        log.info("生成的hive建表语句:" + statementSql);
        Connection connection = this.connection.getConnection();
        Boolean aBoolean = smartCloseConn(connection, statementSql, (conn, sql) -> {
            String useSql = "use " + param.getDatabaseName();
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.execute(useSql);
                stmt.execute(sql);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                String message = e.getMessage();
                if (message.contains("AlreadyExistsException")) {
                    throw new AppException("80000003");
                }
            } finally {
                IoUtil.close(stmt);
            }
            return true;
        });
        if (aBoolean) {
            addWorkspaceTable(param.getWorkspaceId(), param.getDatabaseName(), param.getWideTableName());
        }
        return BusinessResult.success(aBoolean);
    }

    /**
     * 添加hive表和工作空间映射
     *
     * @param workspaceId
     * @param databaseName
     * @param tableName
     */
    private void addWorkspaceTable(String workspaceId, String databaseName, String tableName) {
        //参数前置校验
        addWorkspaceTablePreValid(workspaceId, databaseName, tableName);
        WorkspaceTableEntity workspaceTableEntity = new WorkspaceTableEntity();
        workspaceTableEntity.setWorkspaceId(workspaceId);
        workspaceTableEntity.setTableName(tableName);
        workspaceTableEntity.setDatabaseName(databaseName);
        workspaceTableService.save(workspaceTableEntity);
    }

    private void addWorkspaceTablePreValid(String workspaceId, String databaseName, String tableName) {
        //工作空间为空
        if (StringUtils.isBlank(workspaceId)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_80000004.getCode());
        }
        //hive库名称为空
        if (StringUtils.isBlank(databaseName)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_80000005.getCode());
        }
        //hive表名称为空
        if (StringUtils.isBlank(tableName)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_80000006.getCode());
        }
    }

    @Override
    public BusinessResult<WarehouseInfo> getWarehouseInfo() {
        WarehouseInfo info = new WarehouseInfo();

        String defaultFS = connectionSource.getDefaultFS();
        String password = connectionSource.getPassword();
        String username = connectionSource.getUsername();
        String wareHouse = connectionSource.getWareHouse();
        String url = connection.getUrl();

        info.setDefaultFS(defaultFS);
        info.setUser(username);
        info.setPassWord(password);
        info.setThriftUrl(url);
        info.setWarehouse(wareHouse);

        return BusinessResult.success(info);
    }

    /**
     * 关闭连接
     *
     * @param connection 连接
     * @param r          做为function的r参数
     * @param function   处理函数
     * @param <R>
     * @param <T>
     * @return
     */
    private <R, T> T smartCloseConn(Connection connection, R r, BiFunction<Connection, R, T> function) {
        T apply;
        try {
            apply = function.apply(connection, r);
        } finally {
            IoUtil.close(connection);
        }
        return apply;
    }

    /**
     * 拼接hive创建表语句
     *
     * @param param 建表参数
     * @return
     */
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
        String wideTableName = param.getWideTableName();
        StringJoiner statement = null;
        if (StringUtils.isNotBlank(param.getDatabaseName()) && StringUtils.isNotBlank(wideTableName) && Objects.nonNull(sj)) {
            statement = new StringJoiner(" ");
            statement.add(prefix);
            statement.add(new StringJoiner(".").add(param.getDatabaseName()).add(wideTableName).toString());
            statement.add(sj.toString());
            if (StringUtils.isNotBlank(param.getPartition())) {
                //分区
                String partitionCondition = new StringJoiner(" ").add("partitioned by (").add(param.getPartition()).add(" string)").toString();
                statement.add(partitionCondition);
            }
            if (StringUtils.isNotEmpty(param.getDelimiter())) {
                //分隔符
                String delimiterCondition = new StringJoiner(" ").add("row format delimited fields terminated by ").add("\'" + param.getDelimiter() + "\'").toString();
                statement.add(delimiterCondition);
            }
            statement.add("stored as  orc");
        }
        return statement.toString();
    }
}
