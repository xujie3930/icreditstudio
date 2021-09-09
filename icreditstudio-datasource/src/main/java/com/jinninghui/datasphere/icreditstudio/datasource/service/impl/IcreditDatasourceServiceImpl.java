package com.jinninghui.datasphere.icreditstudio.datasource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jinninghui.datasphere.icreditstudio.datasource.common.enums.DatasourceDelFlagEnum;
import com.jinninghui.datasphere.icreditstudio.datasource.common.enums.DatasourceTypeEnum;
import com.jinninghui.datasphere.icreditstudio.datasource.common.enums.SourceTypeTransferEnum;
import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDatasourceEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDdlSyncEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.feign.SystemFeignClient;
import com.jinninghui.datasphere.icreditstudio.datasource.mapper.IcreditDatasourceMapper;
import com.jinninghui.datasphere.icreditstudio.datasource.mapper.IcreditDdlSyncMapper;
import com.jinninghui.datasphere.icreditstudio.datasource.service.IcreditDatasourceService;
import com.jinninghui.datasphere.icreditstudio.datasource.service.IcreditDdlSyncService;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.DatasourceFactory;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.DatasourceSync;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.*;
import com.jinninghui.datasphere.icreditstudio.datasource.service.result.ConnectionInfo;
import com.jinninghui.datasphere.icreditstudio.datasource.service.result.DatasourceCatalogue;
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
import com.jinninghui.datasphere.icreditstudio.framework.validate.BusinessParamsValidate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.*;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> saveDef(IcreditDatasourceSaveParam param) {
        IcreditDatasourceEntity defEntity = new IcreditDatasourceEntity();
        BeanCopyUtils.copyProperties(param, defEntity);
        defEntity.setId(sequenceService.nextValueString());
        defEntity.setCreateTime(new Date());
        return BusinessResult.success(save(defEntity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> deleteById(IcreditDatasourceDelParam param) {
        datasourceMapper.updateStatusById(param.getId());
        return BusinessResult.success(true);
    }

    @Override
    public BusinessPageResult queryPage(IcreditDatasourceEntityPageRequest pageRequest) {
        QueryWrapper<IcreditDatasourceEntity> wrapper = new QueryWrapper<>();
        //不管是否管理员，都只能查询未删除的数据
        wrapper.eq(IcreditDatasourceEntity.DEL_FLAG, DatasourceDelFlagEnum.N);
        if (StringUtils.isNotBlank(pageRequest.getSpaceId())) {
            wrapper.eq(IcreditDatasourceEntity.SPACE_ID, pageRequest.getSpaceId());
        }
        if (StringUtils.isNotBlank(pageRequest.getName())) {
            wrapper.like(IcreditDatasourceEntity.NAME, pageRequest.getName());
        }
        if (Objects.nonNull(pageRequest.getType())) {
            wrapper.eq(IcreditDatasourceEntity.TYPE, pageRequest.getType());
        }
        if (Objects.nonNull(pageRequest.getStatus())) {
            wrapper.le(IcreditDatasourceEntity.STATUS, pageRequest.getStatus());
        }
        wrapper.orderByDesc(IcreditDatasourceEntity.CREATE_TIME);
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
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<String> syncById(String id) {
        //TODO:同步任务可能会耗时较久，看后期是否需要加redis锁，防止重复点击
        IcreditDatasourceEntity dataEntity = datasourceMapper.selectById(id);
        if (dataEntity == null) {
            return BusinessResult.success("");
        }
        //开始同步的时间，更新到表中
        dataEntity.setLastSyncTime(new Date());
        datasourceMapper.updateById(dataEntity);
        //这里根据不同type类型，连接不同的数据库，同步其表
        DatasourceSync datasource = DatasourceFactory.getDatasource(dataEntity.getType());
        String ddlInfo = null;
        String key = sequenceService.nextValueString();
        String hdfsPath;
        Map<String, String> map;
        try {
            map = datasource.syncDDL(dataEntity.getType(), dataEntity.getUri());
            //hdfsPath = HDFSUtils.copyStringToHDFS(key, ddlInfo);
        } catch (Exception e) {
            return BusinessResult.success(e.getMessage());
        }
        IcreditDdlSyncEntity ddlEntity = new IcreditDdlSyncEntity();
        BeanCopyUtils.copyProperties(dataEntity, ddlEntity);
        ddlEntity.setId(sequenceService.nextValueString());
        //建立外键关联
        ddlEntity.setDatasourceId(dataEntity.getId());
        //这里先存存储hdfs的路径
        ddlEntity.setColumnsInfo(map.get("datasourceInfo"));
        ddlEntity.setCreateTime(new Date());
        //TODO：这里加锁：先查询最大版本号，对其递增再插入，查询和插入两操作得保证原子性
        IcreditDdlSyncEntity oldEntity = ddlSyncMapper.selectMaxVersionByDatasourceId(dataEntity.getId());
        if (oldEntity == null) {
            ddlSyncMapper.insert(ddlEntity);
        } else {
            if (!oldEntity.getColumnsInfo().equals(ddlEntity.getColumnsInfo())) {
                ddlEntity.setVersion(oldEntity.getVersion() + 1);
                ddlSyncMapper.insert(ddlEntity);
            }
        }
        return BusinessResult.success(map.get("tablesCount").toString());
    }

    @Override
    public BusinessResult<List<DataSourceBaseInfo>> datasourceSearch(DataSyncQueryDataSourceSearchParam param) {
        List<DataSourceBaseInfo> results = Lists.newArrayList();
        IcreditDatasourceConditionParam build = IcreditDatasourceConditionParam.builder()
                .category(Sets.newHashSet(param.getSourceType()))
                .build();
        List<IcreditDatasourceEntity> list = list(queryWrapper(build));
        if (CollectionUtils.isNotEmpty(list)) {
            Set<String> dataSourceIds = list.parallelStream()
                    .filter(Objects::nonNull)
                    .map(IcreditDatasourceEntity::getId).collect(Collectors.toSet());
            Map<String, Optional<IcreditDdlSyncEntity>> stringOptionalMap = icreditDdlSyncService.categoryLatelyDdlSyncs(dataSourceIds);
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
                                results.addAll(Optional.ofNullable(tableName).orElse(Lists.newArrayList())
                                        .parallelStream()
                                        .filter(StringUtils::isNotBlank)
                                        .map(n -> {
                                            DataSourceBaseInfo info = new DataSourceBaseInfo();
                                            info.setDatabaseName(name);
                                            info.setTableName(n);
                                            return info;
                                        })
                                        .filter(info -> StringUtils.isBlank(param.getTableName()) || info.getTableName().contains(param.getTableName())).collect(Collectors.toList()));
                            }
                        }
                    }
                });
            }
        }
        return BusinessResult.success(results);
    }

    @Override
    public BusinessResult<List<DatasourceCatalogue>> getDatasourceCatalogue(DataSyncQueryDatasourceCatalogueParam param) {
        IcreditDatasourceConditionParam build = IcreditDatasourceConditionParam.builder()
                .workspaceId(param.getWorkspaceId())
                .category(SourceTypeTransferEnum.getCatalogue(param.getSourceType()))
                .build();
        QueryWrapper<IcreditDatasourceEntity> wrapper = queryWrapper(build);
        List<IcreditDatasourceEntity> list = list(wrapper);
        List<DatasourceCatalogue> results = null;
        if (CollectionUtils.isNotEmpty(list)) {
            //数据源ID
            Set<String> sourceIds = list.parallelStream().filter(Objects::nonNull).map(IcreditDatasourceEntity::getId).collect(Collectors.toSet());
            //数据源最新同步表
            Map<String, Optional<IcreditDdlSyncEntity>> stringOptionalMap = icreditDdlSyncService.categoryLatelyDdlSyncs(sourceIds);
            //数据源信息
            results = list.stream()
                    .filter(Objects::nonNull)
                    .map(icreditDatasourceEntity -> {
                        DatasourceCatalogue catalogue = new DatasourceCatalogue();
                        catalogue.setDatasourceId(icreditDatasourceEntity.getId());
                        catalogue.setName(icreditDatasourceEntity.getName());
                        if (StringUtils.isNotBlank(icreditDatasourceEntity.getName())) {
                            catalogue.setSelect(icreditDatasourceEntity.getName().equals(param.getTableName()));
                        }
                        catalogue.setUrl(icreditDatasourceEntity.getUri());
                        catalogue.setDialect(DatasourceTypeEnum.findDatasourceTypeByType(icreditDatasourceEntity.getType()).getDesc());
                        return catalogue;
                    }).collect(Collectors.toList());
            if (MapUtils.isNotEmpty(stringOptionalMap)) {
                Map<String, List<String>> catalogueTablas = Maps.newHashMap();
                stringOptionalMap.forEach((k, v) -> {
                    v.ifPresent(icreditDdlSyncEntity -> {
                        String columnsInfo = icreditDdlSyncEntity.getColumnsInfo();
                        List<String> tableNames = IcreditDdlSyncService.parseColumnsTableName(columnsInfo);
                        catalogueTablas.put(k, tableNames);
                    });
                });
                results.stream()
                        .forEach(datasourceCatalogue -> {
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
                                        catalogue.setSelect(s.equals(param.getTableName()));
                                        return catalogue;
                                    }).collect(Collectors.toList());
                            datasourceCatalogue.setContent(content);
                        });
            }
        }
        return BusinessResult.success(Optional.ofNullable(results).orElse(Lists.newArrayList()));
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
        ConnectionInfo info = null;
        if (Objects.nonNull(byId)) {
            info = new ConnectionInfo();
            info.setDriverClass(DatasourceTypeEnum.findDatasourceTypeByType(byId.getType()).getDriver());
            info.setUsername(DatasourceSync.getUsername(byId.getUri()));
            info.setPassword(DatasourceSync.getPassword(byId.getUri()));
            info.setUrl(DatasourceSync.getConnUrl(byId.getUri()));
        }
        return BusinessResult.success(info);
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<List<SourceTableInfo>> getTableInfo(DataSourceTableInfoParam param) {
        List<SourceTableInfo> results = null;
        IcreditDatasourceEntity byId = getById(param.getDatasourceId());
        if (Objects.nonNull(byId)) {
            String uri = byId.getUri();
            String connUrl = DatasourceSync.getConnUrl(uri);
            String username = DatasourceSync.getUsername(uri);
            String password = DatasourceSync.getPassword(uri);
            Connection conn = DatasourceSync.getConn(byId.getType(), connUrl, username, password);
            if (Objects.isNull(conn)) {
                throw new AppException("70000000");
            }
            try {
                PreparedStatement stemt = conn.prepareStatement("select * from " + param.getTableName());
                ResultSetMetaData metaData = stemt.getMetaData();
                int columnCount = metaData.getColumnCount();
                if (columnCount > 0) {
                    results = Lists.newArrayList();
                    for (int i = 1; i <= columnCount; i++) {
                        SourceTableInfo info = new SourceTableInfo();
                        info.setName(metaData.getColumnName(i));
                        info.setFieldType(metaData.getColumnTypeName(i));
                        results.add(info);
                    }
                }
            } catch (Exception e) {
                log.error("获取数据库源信息", e);
            }
        }
        return BusinessResult.success(results);
    }

    private QueryWrapper<IcreditDatasourceEntity> queryWrapper(IcreditDatasourceConditionParam param) {
        QueryWrapper<IcreditDatasourceEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getWorkspaceId())) {
            wrapper.eq(IcreditDatasourceEntity.SPACE_ID, param.getWorkspaceId());
        }
        if (CollectionUtils.isNotEmpty(param.getCategory())) {
            wrapper.in(IcreditDatasourceEntity.CATEGORY, param.getCategory());
        }
        if (StringUtils.isNotBlank(param.getDatasourceId())) {
            wrapper.eq(IcreditDatasourceEntity.ID, param.getDatasourceId());
        }
        wrapper.eq(IcreditDatasourceEntity.DEL_FLAG, "N");
        return wrapper;
    }
}
