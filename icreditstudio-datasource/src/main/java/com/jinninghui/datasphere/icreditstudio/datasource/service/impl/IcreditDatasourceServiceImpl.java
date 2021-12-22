package com.jinninghui.datasphere.icreditstudio.datasource.service.impl;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jinninghui.datasphere.icreditstudio.datasource.common.ResourceCodeBean;
import com.jinninghui.datasphere.icreditstudio.datasource.common.enums.*;
import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDatasourceEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDdlSyncEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.feign.DatasyncFeignClient;
import com.jinninghui.datasphere.icreditstudio.datasource.feign.SystemFeignClient;
import com.jinninghui.datasphere.icreditstudio.datasource.feign.UserWorkspaceFeignClient;
import com.jinninghui.datasphere.icreditstudio.datasource.mapper.IcreditDatasourceMapper;
import com.jinninghui.datasphere.icreditstudio.datasource.mapper.IcreditDdlSyncMapper;
import com.jinninghui.datasphere.icreditstudio.datasource.service.ConnectionSource;
import com.jinninghui.datasphere.icreditstudio.datasource.service.ConnectionSourceParser;
import com.jinninghui.datasphere.icreditstudio.datasource.service.IcreditDatasourceService;
import com.jinninghui.datasphere.icreditstudio.datasource.service.IcreditDdlSyncService;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.DatasourceFactory;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.DatasourceSync;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.pojo.TableSyncInfo;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.*;
import com.jinninghui.datasphere.icreditstudio.datasource.service.result.ConnectionInfo;
import com.jinninghui.datasphere.icreditstudio.datasource.service.result.DatasourceCatalogue;
import com.jinninghui.datasphere.icreditstudio.datasource.service.result.DatasourceResult;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.DataSourceHasExistRequest;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.IcreditDatasourceEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.IcreditDatasourceTestConnectRequest;
import com.jinninghui.datasphere.icreditstudio.datasource.web.result.DataSourceBaseInfo;
import com.jinninghui.datasphere.icreditstudio.datasource.web.result.DatasourceDetailResult;
import com.jinninghui.datasphere.icreditstudio.datasource.web.result.SourceTableInfo;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.Query;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.sequence.api.SequenceService;
import com.jinninghui.datasphere.icreditstudio.framework.utils.HDFSUtils;
import com.jinninghui.datasphere.icreditstudio.framework.validate.BusinessParamsValidate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2021-08-24
 */
@Slf4j
@Service
public class IcreditDatasourceServiceImpl extends ServiceImpl<IcreditDatasourceMapper, IcreditDatasourceEntity> implements IcreditDatasourceService {

    @Resource
    private IcreditDdlSyncMapper ddlSyncMapper;
    @Resource
    private IcreditDatasourceMapper datasourceMapper;
    @Resource
    private IcreditDdlSyncService icreditDdlSyncService;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private SystemFeignClient systemFeignClient;
    @Autowired
    private UserWorkspaceFeignClient userWorkspaceFeignClient;
    @Autowired
    private DatasyncFeignClient datasyncFeignClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> saveDef(String userId, IcreditDatasourceSaveParam param) {
        IcreditDatasourceTestConnectRequest testConnectRequest = BeanCopyUtils.copyProperties(param, IcreditDatasourceTestConnectRequest.class);
        checkDatabase(testConnectRequest);
        IcreditDatasourceEntity defEntity = new IcreditDatasourceEntity();
        BeanCopyUtils.copyProperties(param, defEntity);
        if (saveOrUpdate(userId, defEntity)) {
            return BusinessResult.success(true);
        } else {
            return BusinessResult.fail("", "保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> deleteById(IcreditDatasourceDelParam param) {
        if (DatasourceStatusEnum.ENABLE.getCode().equals(getById(param.getId()).getStatus())) {
            throw new AppException("70000009");
        }
        datasourceMapper.updateStatusById(param.getId());
        //1.删除同步记录，2:把hdfs上关联数据也删除
        List<IcreditDdlSyncEntity> delList = ddlSyncMapper.selectByDatasourceId(param.getId());
        for (IcreditDdlSyncEntity del : delList) {
            ddlSyncMapper.updateStatusById(del.getId());
            HDFSUtils.delFileFromHDFS(del.getColumnsInfo());
        }
        return BusinessResult.success(true);
    }

    @Override
    @BusinessParamsValidate
    public BusinessPageResult queryPage(String userId, IcreditDatasourceEntityPageRequest pageRequest) {
        QueryWrapper<IcreditDatasourceEntity> wrapper = new QueryWrapper<>();
        //不管是否管理员，都只能查询未删除的数据
        wrapper.eq(IcreditDatasourceEntity.DEL_FLAG, DatasourceDelFlagEnum.N);
        if (StringUtils.isNotBlank(pageRequest.getSpaceId())) {
            wrapper.eq(IcreditDatasourceEntity.SPACE_ID, pageRequest.getSpaceId());
        } else {
            //根据userId查询所有的空间id
            BusinessResult<Boolean> result = systemFeignClient.isAdmin();
            if (result.isSuccess() && result.getData()) {
                log.info("当前用户为管理员，拥有全部空间权限");
                userId = "";
            }
            BusinessResult<List<Map<String, String>>> workspaceList = userWorkspaceFeignClient.getWorkspaceListByUserId(userId);
            List<Map<String, String>> data = workspaceList.getData();
            List<String> list = new ArrayList<>();
            for (Map<String, String> map : data) {
                list.add(map.get("id"));
            }
            wrapper.in(IcreditDatasourceEntity.SPACE_ID, list);
        }
        if (StringUtils.isNotBlank(pageRequest.getName())) {
            wrapper.like(IcreditDatasourceEntity.NAME, pageRequest.getName());
        }
        if (Objects.nonNull(pageRequest.getType())) {
            wrapper.eq(IcreditDatasourceEntity.TYPE, pageRequest.getType());
        }
        if (Objects.nonNull(pageRequest.getStatus())) {
            wrapper.eq(IcreditDatasourceEntity.STATUS, pageRequest.getStatus());
        }
        wrapper.orderByAsc(IcreditDatasourceEntity.STATUS);
        wrapper.orderByDesc(IcreditDatasourceEntity.LAST_SYNC_TIME);
        IPage<IcreditDatasourceEntity> page = this.page(
                new Query<IcreditDatasourceEntity>().getPage(pageRequest),
                wrapper
        );
        return BusinessPageResult.build(page, pageRequest);
    }

    @Override
    public BusinessResult<String> testConn(IcreditDatasourceTestConnectRequest request) {
        DatasourceSync datasource = DatasourceFactory.getDatasource(request.getType());
        String resp = datasource.testConn(request.getType(), request.getUri());
        return BusinessResult.success(resp);
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<String> syncById(String id) {
        String result = String.format("同步成功");
        if (DatasourceStatusEnum.DISABLE.getCode().equals(getById(id).getStatus())) {
            throw new AppException("70000010");
        }
        IcreditDatasourceEntity dataEntity = datasourceMapper.selectById(id);
        if (dataEntity == null) {
            log.error("没有找到该数据源:{}", id);
            throw new AppException("70000003");
        }
        Date date = new Date();
        dataEntity.setLastSyncTime(date);

        //TODO:同步任务可能会耗时较久，看后期是否需要加redis锁
        try {
            //这里根据不同type类型，连接不同的数据库，同步其表
            DatasourceSync datasource = DatasourceFactory.getDatasource(dataEntity.getType());
            String key = sequenceService.nextValueString();
            Map<String, String> map = datasource.syncDDL(dataEntity.getType(), dataEntity.getUri());

            //icredit_ddl_sync表创建对象并且赋值
            IcreditDdlSyncEntity ddlEntity = new IcreditDdlSyncEntity();
            BeanCopyUtils.copyProperties(dataEntity, ddlEntity);
            ddlEntity.setId(sequenceService.nextValueString());
            ddlEntity.setUpdateTime(date);
            ddlEntity.setCreateTime(date);
            ddlEntity.setDatasourceId(dataEntity.getId());

            //TODO:这里加锁：先查询最大版本号，对其递增再插入，查询和插入两操作得保证原子性
            //获取旧数据,无旧数据则新增，旧数据与新数据不同，更新表结构字段且版本号+1
            IcreditDdlSyncEntity oldEntity = ddlSyncMapper.selectMaxVersionByDatasourceId(dataEntity.getId());
            if (oldEntity == null) {
                extracted(map, key, ddlEntity);
                result = getResult(null, map.get(DatasourceSync.DATASOURCEINFO));
            } else {
                String oldColumnsInfo = HDFSUtils.getStringFromHDFS(oldEntity.getColumnsInfo());
                if (!oldColumnsInfo.equals(map.get(DatasourceSync.DATASOURCEINFO))) {
                    ddlEntity.setVersion(oldEntity.getVersion() + 1);
                    extracted(map, key, ddlEntity);
                    result = getResult(oldColumnsInfo, map.get(DatasourceSync.DATASOURCEINFO));
                }
            }

            //更新datasource表
            dataEntity.setLastSyncStatus(DatasourceSyncStatusEnum.SUCCESS.getStatus());
            updateById(dataEntity);
        } catch (Exception e) {
            IcreditDatasourceServiceImpl icreditDatasourceService = (IcreditDatasourceServiceImpl) AopContext.currentProxy();
            dataEntity.setLastSyncStatus(DatasourceSyncStatusEnum.FAIL.getStatus());
            icreditDatasourceService.updateDatasourceById(dataEntity);
            log.error("数据源同步异常:{}", e.getMessage());
            throw new AppException("70000003");
        }
        return BusinessResult.success(result);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateDatasourceById(IcreditDatasourceEntity dataEntity) {
        datasourceMapper.updateById(dataEntity);
    }

    private String getResult(String oldColumnsInfo, String datasourceInfo) {
        StringBuilder builder = new StringBuilder("同步成功");
        List<TableSyncInfo> oldStructure;
        List<TableSyncInfo> newStructure;
        Integer add = 0;
        Integer addCloumns = 0;
        Integer del = 0;
        Integer delCloumns = 0;
        Integer update = 0;
        if (StringUtils.isBlank(oldColumnsInfo)) {
            newStructure = JSON.parseArray(datasourceInfo, TableSyncInfo.class);
            for (TableSyncInfo tableSyncInfo : newStructure) {
                add++;
                addCloumns += tableSyncInfo.getColumnList().size();
            }
            return String.format("同步成功，新增 %s 张表,共 %s 个字段 ", add, addCloumns);
        }
        //根据新旧json统计新增、删除、修改表的数量
        oldStructure = JSON.parseArray(oldColumnsInfo, TableSyncInfo.class);
        newStructure = JSON.parseArray(datasourceInfo, TableSyncInfo.class);
        for (TableSyncInfo tableSyncInfo : oldStructure) {
            if (newStructure.parallelStream().anyMatch(n -> n.getTableName().equals(tableSyncInfo.getTableName()) &&
                    !CollectionUtils.isEqualCollection(n.getColumnList(), tableSyncInfo.getColumnList()))) {
                update++;
            } else if (!newStructure.parallelStream().anyMatch(n -> n.getTableName().equals(tableSyncInfo.getTableName()))) {
                del++;
                delCloumns += tableSyncInfo.getColumnList().size();
            }
        }

        for (TableSyncInfo tableSyncInfo : newStructure) {
            if (!oldStructure.parallelStream().anyMatch(o -> o.getTableName().equals(tableSyncInfo.getTableName()))) {
                add++;
                addCloumns += tableSyncInfo.getColumnList().size();
            }
        }
        if (add > 0 && addCloumns > 0) {
            builder.append(String.format(",新增 %s 张表,共 %s 个字段", add, addCloumns));
        }
        if (del > 0 && delCloumns > 0) {
            builder.append(String.format(",删除 %s 张表,共 %s 个字段", del, delCloumns));
        }
        return builder.toString();
    }

    private void extracted(Map<String, String> map, String key, IcreditDdlSyncEntity ddlEntity) throws Exception {
        String hdfsPath = HDFSUtils.copyStringToHDFS(map.get(DatasourceSync.DATASOURCEINFO), key);
        ddlEntity.setColumnsInfo(hdfsPath);
        ddlSyncMapper.insert(ddlEntity);
    }

    @Override
    public BusinessResult<List<DataSourceBaseInfo>> datasourceSearch(DataSyncQueryDataSourceSearchParam param) {
        log.info("数据源搜索参数:" + JSONObject.toJSONString(param));
        List<DataSourceBaseInfo> results = Lists.newArrayList();
        IcreditDatasourceConditionParam build = IcreditDatasourceConditionParam.builder()
                .workspaceId(param.getWorkspaceId())
                .category(SourceTypeTransferEnum.getCatalogue((param.getSourceType())))
                .status(DatasourceStatusEnum.ENABLE.getCode())
                .build();
        List<IcreditDatasourceEntity> list = list(queryWrapper(build));
        log.info("数据源列表:" + JSONObject.toJSONString(list));
        if (CollectionUtils.isNotEmpty(list)) {
            Set<String> dataSourceIds = list.parallelStream()
                    .filter(Objects::nonNull)
                    .map(IcreditDatasourceEntity::getId).collect(Collectors.toSet());
            Map<String, Optional<IcreditDdlSyncEntity>> stringOptionalMap = icreditDdlSyncService.categoryLatelyDdlSyncs(dataSourceIds);
            log.info("数据源ddl信息:" + JSONObject.toJSONString(stringOptionalMap));
            if (MapUtils.isNotEmpty(stringOptionalMap)) {
                stringOptionalMap.forEach((k, v) -> {
                    Optional<IcreditDatasourceEntity> first = list.parallelStream().filter(Objects::nonNull).filter(entity -> StringUtils.equals(entity.getId(), k)).findFirst();
                    if (first.isPresent()) {
                        IcreditDatasourceEntity entity = first.get();
                        String name = entity.getName();
                        if (v.isPresent()) {
                            IcreditDdlSyncEntity ddlSyncEntity = v.get();
                            String columnsInfo = ddlSyncEntity.getColumnsInfo();
                            if (StringUtils.isNotBlank(columnsInfo)) {
                                List<String> tableName = IcreditDdlSyncService.parseColumnsTableName(columnsInfo);
                                List<DataSourceBaseInfo> collect = Optional.ofNullable(tableName).orElse(Lists.newArrayList())
                                        .parallelStream()
                                        .filter(StringUtils::isNotBlank)
                                        .map(n -> {
                                            DataSourceBaseInfo info = new DataSourceBaseInfo();
                                            info.setDatabaseName(name);
                                            info.setTableName(n);
                                            return info;
                                        })
                                        .filter(info -> StringUtils.isBlank(param.getTableName()) || info.getTableName().contains(param.getTableName())).collect(Collectors.toList());
                                results.addAll(collect);
                            }
                        }
                    }
                });
            }
        }
        return BusinessResult.success(results);
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<List<DatasourceCatalogue>> getDatasourceCatalogue(DataSyncQueryDatasourceCatalogueParam param) {
        datasourceCataloguePreValid(param);
        IcreditDatasourceConditionParam build = IcreditDatasourceConditionParam.builder()
                .workspaceId(param.getWorkspaceId())
                .category(SourceTypeTransferEnum.getCatalogue(param.getSourceType()))
                .status(DatasourceStatusEnum.ENABLE.getCode())
                .build();
        QueryWrapper<IcreditDatasourceEntity> wrapper = queryWrapper(build);
        List<IcreditDatasourceEntity> list = list(wrapper);
        List<DatasourceCatalogue> results = null;
        if (CollectionUtils.isNotEmpty(list)) {
            //数据源ID
            Set<String> sourceIds = list.parallelStream().filter(Objects::nonNull).map(IcreditDatasourceEntity::getId).collect(Collectors.toSet());
            //获取各个数据源下的表
            Map<String, Optional<IcreditDdlSyncEntity>> stringOptionalMap = icreditDdlSyncService.categoryLatelyDdlSyncs(sourceIds);

            results = assemblyDatasourceBaseInfo(list);
            if (MapUtils.isNotEmpty(stringOptionalMap)) {
                results = assemblyDatasourceTableInfo(results, stringOptionalMap);
            }
        }
        return BusinessResult.success(Optional.ofNullable(results).orElse(Lists.newArrayList()));
    }

    //数据源目录前置校验
    private void datasourceCataloguePreValid(DataSyncQueryDatasourceCatalogueParam param) {
        if (StringUtils.isBlank(param.getWorkspaceId())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_70000005.getCode());
        }
        if (Objects.isNull(param.getSourceType())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_70000006.getCode());
        }
    }

    //组装数据源基本信息
    private List<DatasourceCatalogue> assemblyDatasourceBaseInfo(List<IcreditDatasourceEntity> datasourceEntities) {
        List<DatasourceCatalogue> results = null;
        if (CollectionUtils.isNotEmpty(datasourceEntities)) {
            results = datasourceEntities.stream()
                    .filter(Objects::nonNull)
                    .map(datasourceEntity -> {
                        DatasourceCatalogue catalogue = new DatasourceCatalogue();
                        catalogue.setDatasourceId(datasourceEntity.getId());
                        catalogue.setName(datasourceEntity.getDatabaseName());
                        catalogue.setUrl(DatasourceSync.getConnUrl(datasourceEntity.getUri()));
                        catalogue.setHost(DatasourceSync.getHost(datasourceEntity.getUri()));
                        catalogue.setDialect(datasourceEntity.getDialect());
                        return catalogue;
                    }).collect(Collectors.toList());
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    //组装数据源下表信息
    private List<DatasourceCatalogue> assemblyDatasourceTableInfo(List<DatasourceCatalogue> catalogues, Map<String, Optional<IcreditDdlSyncEntity>> ddlSyncInfos) {
        List<DatasourceCatalogue> results = null;
        if (CollectionUtils.isNotEmpty(catalogues) && MapUtils.isNotEmpty(ddlSyncInfos)) {
            Map<String, List<String>> catalogueTablas = Maps.newHashMap();
            ddlSyncInfos.forEach((k, v) -> {
                v.ifPresent(ddlSyncEntity -> {
                    String columnsInfo = ddlSyncEntity.getColumnsInfo();
                    List<String> tableNames = IcreditDdlSyncService.parseColumnsTableName(columnsInfo);
                    catalogueTablas.put(k, tableNames);
                });
            });
            results = catalogues.stream()
                    .map(datasourceCatalogue -> {
                        DatasourceCatalogue result = new DatasourceCatalogue();
                        BeanCopyUtils.copyProperties(datasourceCatalogue, result);
                        String datasourceId = datasourceCatalogue.getDatasourceId();
                        List<String> tableNames = catalogueTablas.get(datasourceId);
                        List<DatasourceCatalogue> content = Optional.ofNullable(tableNames).orElse(Lists.newArrayList())
                                .parallelStream()
                                .map(s -> {
                                    DatasourceCatalogue catalogue = new DatasourceCatalogue();
                                    catalogue.setDatasourceId(datasourceId);
                                    catalogue.setUrl(datasourceCatalogue.getUrl());
                                    catalogue.setDialect(datasourceCatalogue.getDialect());
                                    catalogue.setName(s);
                                    return catalogue;
                                }).collect(Collectors.toList());
                        result.setContent(content);
                        return result;
                    }).collect(Collectors.toList());
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }


    @Override
    public BusinessResult<Boolean> hasExit(DataSourceHasExistRequest request) {
        boolean hasExit = BooleanUtils.isTrue(datasourceMapper.hasExit(request));
        return BusinessResult.success(hasExit);
    }

    @Override
    public DatasourceDetailResult getDetailById(String id) {
        IcreditDatasourceEntity entity = getById(id);
        DatasourceDetailResult result = BeanCopyUtils.copyProperties(entity, new DatasourceDetailResult());
        return result;
    }

    @Override
    public BusinessResult<ConnectionInfo> getConnectionInfo(ConnectionInfoParam param) {
        IcreditDatasourceEntity byId = getById(param.getDatasourceId());
        ConnectionInfo result = null;
        if (Objects.nonNull(byId)) {
            String dialect = DatasourceTypeEnum.findDatasourceTypeByType(byId.getType()).getDesc();
            String uri = byId.getUri();
            ConnectionSourceParser sourceParser = DataSourceUrlParseContainer.getInstance().find(dialect);
            if (Objects.isNull(sourceParser)) {
                throw new AppException("70000004");
            }
            ConnectionSource parse = sourceParser.parse(uri);
            result = new ConnectionInfo();
            result.setUsername(parse.getUsername());
            result.setPassword(parse.getPassword());
            result.setUrl(parse.getUrl());
            result.setDriverClass(parse.getDriverClass());
        }
        return BusinessResult.success(result);
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<List<SourceTableInfo>> getTableInfo(DataSourceTableInfoParam param) {
        List<SourceTableInfo> results = null;
        IcreditDatasourceEntity byId = getById(param.getDatasourceId());
        if (Objects.nonNull(byId)) {
            String dialect = DatasourceTypeEnum.findDatasourceTypeByType(byId.getType()).getDesc();
            String uri = byId.getUri();
            ConnectionSourceParser sourceParser = DataSourceUrlParseContainer.getInstance().find(dialect);
            if (Objects.isNull(sourceParser)) {
                throw new AppException("70000004");
            }
            ConnectionSource connectionSource = sourceParser.parse(uri);
            results = smartConnection(connectionSource, param, (conn, obj) -> {
                List<SourceTableInfo> result = Lists.newArrayList();
                try {
                    PreparedStatement stmt = conn.prepareStatement("select * from " + obj.getTableName());
                    ResultSetMetaData metaData = stmt.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    for (int i = 1; i <= columnCount; i++) {
                        SourceTableInfo info = new SourceTableInfo();
                        info.setName(metaData.getColumnName(i));
                        info.setFieldType(metaData.getColumnTypeName(i));
                        result.add(info);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                return result;
            });
        }
        return BusinessResult.success(Optional.ofNullable(results).orElse(Lists.newArrayList()));
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<List<IcreditDatasourceEntity>> getDataSources(DataSourcesQueryParam param) {
        IcreditDatasourceConditionParam build = IcreditDatasourceConditionParam.builder()
//                .uri(param.getDatabaseName())
                .databaseName(param.getDatabaseName())
                .datasourceId(param.getDatasourceId())
                .category(param.getCategory())
                .status(DatasourceStatusEnum.ENABLE.getCode())
                .build();
        QueryWrapper<IcreditDatasourceEntity> wrapper = queryWrapper(build);
        List<IcreditDatasourceEntity> list = list(wrapper);
        return BusinessResult.success(list);
    }

    @Override
    public BusinessResult<Boolean> updateDef(String userId, IcreditDatasourceUpdateParam param) {
        IcreditDatasourceEntity datasourceEntity = datasourceMapper.selectById(param.getId());
        //若数据源发生改动，则需要判断uri是否正确
        if (StringUtils.isNotBlank(param.getUri())) {
            IcreditDatasourceTestConnectRequest testConnectRequest = new IcreditDatasourceTestConnectRequest(datasourceEntity.getType(), param.getUri());
            checkDatabase(testConnectRequest);
        }
        IcreditDatasourceEntity entity = new IcreditDatasourceEntity();
        BeanCopyUtils.copyProperties(param, entity);
        if (saveOrUpdate(userId, entity)) {
            return BusinessResult.success(true);
        } else {
            return BusinessResult.fail("", "操作失败");
        }
    }

    private Boolean saveOrUpdate(String userId, IcreditDatasourceEntity entity) {
        DatasourceSync dataSource = DatasourceFactory.getDatasource(entity.getType());
        entity.setDatabaseName(dataSource.getDatabaseName(entity.getUri()));
        entity.setDialect(DatasourceTypeEnum.findDatasourceTypeByType(entity.getType()).getDesc());
        entity.setHost(DatasourceSync.getHost(entity.getUri()));
        entity.setUpdateBy(userId);
        entity.setUpdateTime(new Date());
        if (StringUtils.isEmpty(entity.getId())) {
            entity.setCreateTime(new Date());
            entity.setCreateBy(userId);
            entity.setId(sequenceService.nextValueString());
        }
        return saveOrUpdate(entity);
    }

    @Override
    public BusinessResult<Boolean> updateStatusById(IcreditDatasourceUpdateStatusParam param) {
        if (StringUtils.isEmpty(param.getId())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_70000002.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_70000002.message);
        }
        if (!DatasourceStatusEnum.ENABLE.getCode().equals(param.getDatasourceStatus()) && !DatasourceStatusEnum.DISABLE.getCode().equals(param.getDatasourceStatus())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_70000013.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_70000013.message);
        }
        //删除数据源时候需要判断该数据源下是否有工作流
        if (DatasourceStatusEnum.DISABLE.getCode().equals(param.getDatasourceStatus())) {
            Boolean hasRunningTask = datasyncFeignClient.hasRunningTask(param.getId());
            if (hasRunningTask) {
                throw new AppException("70000012");
            }
        }
        IcreditDatasourceEntity datasourceEntity = datasourceMapper.selectById(param.getId());
        datasourceEntity.setStatus(param.getDatasourceStatus());
        boolean isSuccessed = updateById(datasourceEntity);
        return isSuccessed ? BusinessResult.success(isSuccessed) : BusinessResult.fail("", "操作失败");
    }

    @Override
    public List<IcreditDatasourceEntity> findAllDatasoure() {
        return datasourceMapper.selectAll(null, DatasourceStatusEnum.ENABLE.getCode());
    }

    @Override
    public BusinessResult<DatasourceResult> getDatasourceJdbcInfo(String id) {
        IcreditDatasourceConditionParam build = IcreditDatasourceConditionParam.builder()
                .datasourceId(id)
                .build();
        QueryWrapper<IcreditDatasourceEntity> wrapper = queryWrapper(build);
        List<IcreditDatasourceEntity> list = list(wrapper);

        DatasourceResult result = null;
        if (CollectionUtils.isNotEmpty(list)) {
            IcreditDatasourceEntity entity = list.get(0);
            String uri = entity.getUri();
            result = new DatasourceResult();
            result.setJdbcUrl(DatasourceSync.getConnUrl(uri));
            result.setUsername(DatasourceSync.getUsername(uri));
            result.setPassword(DatasourceSync.getPassword(uri));
        }
        log.info("数据源信息:" + JSONObject.toJSONString(result));
        return BusinessResult.success(result);
    }

    @Override
    public BusinessResult<Boolean> delDatasourceFromWorkspace(String spaceId) {
        Boolean hasExit = BooleanUtils.isTrue(datasourceMapper.selectByWorkspaceIdHasExit(spaceId));
        if (hasExit) {
            throw new AppException("70000011");
        }

        List<IcreditDatasourceEntity> entities = datasourceMapper.selectAll(spaceId, DatasourceStatusEnum.DISABLE.getCode());
        for (IcreditDatasourceEntity entity : entities) {
            IcreditDatasourceDelParam param = new IcreditDatasourceDelParam();
            param.setId(entity.getId());
            deleteById(param);
        }
        return BusinessResult.success(true);
    }

    private void checkDatabase(IcreditDatasourceTestConnectRequest testConnectRequest) {
        BusinessResult<String> testConnResult = testConn(testConnectRequest);
        if (!testConnResult.isSuccess()) {
            throw new AppException("70000008");
        }
    }

    static <R, T> T smartConnection(ConnectionSource connectionSource, R r, BiFunction<Connection, R, T> function) {
        T apply = null;
        Connection connection = null;
        try {
            String url = connectionSource.getUrl();
            String username = connectionSource.getUsername();
            String password = connectionSource.getPassword();
            connection = DriverManager.getConnection(url, username, password);
            apply = function.apply(connection, r);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            IoUtil.close(connection);
        }
        return apply;
    }

    private QueryWrapper<IcreditDatasourceEntity> queryWrapper(IcreditDatasourceConditionParam param) {
        QueryWrapper<IcreditDatasourceEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getWorkspaceId())) {
            wrapper.eq(IcreditDatasourceEntity.SPACE_ID, param.getWorkspaceId());
        }
        if (CollectionUtils.isNotEmpty(param.getCategory())) {
            wrapper.in(IcreditDatasourceEntity.CATEGORY, param.getCategory());
        }
        if (StringUtils.isNotBlank(param.getUri())) {
            wrapper.like(IcreditDatasourceEntity.URI, param.getUri());
        }
        if (StringUtils.isNotBlank(param.getDatabaseName())) {
            wrapper.eq(IcreditDatasourceEntity.DATABASE_NAME, param.getDatabaseName());
        }
        if (StringUtils.isNotBlank(param.getDatasourceId())) {
            wrapper.eq(IcreditDatasourceEntity.ID, param.getDatasourceId());
        }
        if (Objects.nonNull(param.getStatus())) {
            wrapper.eq(IcreditDatasourceEntity.STATUS, param.getStatus());
        }
        wrapper.eq(IcreditDatasourceEntity.DEL_FLAG, "N");
        return wrapper;
    }
}
