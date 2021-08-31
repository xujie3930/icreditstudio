package com.jinninghui.datasphere.icreditstudio.datasource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDatasourceEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDdlSyncEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.mapper.IcreditDatasourceMapper;
import com.jinninghui.datasphere.icreditstudio.datasource.mapper.IcreditDdlSyncMapper;
import com.jinninghui.datasphere.icreditstudio.datasource.service.IcreditDatasourceService;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.DatasourceFactory;
import com.jinninghui.datasphere.icreditstudio.datasource.service.factory.DatasourceSync;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.IcreditDatasourceDelParam;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.IcreditDatasourceSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.IcreditDatasourceEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.IcreditDatasourceTestConnectRequest;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.Query;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.sequence.api.SequenceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xujie
 * @since 2021-08-24
 */
@Service
public class IcreditDatasourceServiceImpl extends ServiceImpl<IcreditDatasourceMapper, IcreditDatasourceEntity> implements IcreditDatasourceService {

    @Autowired
    private IcreditDdlSyncMapper ddlSyncMapper;
    @Autowired
    private IcreditDatasourceMapper datasourceMapper;

    @Autowired
    private SequenceService sequenceService;

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
        if (StringUtils.isNotBlank(pageRequest.getWorkspaceId())){
            wrapper.eq(IcreditDatasourceEntity.SPACE_ID, pageRequest.getWorkspaceId());
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
    public BusinessResult<String> syncById(String id){
        //TODO:同步任务可能会耗时较久，看后期是否需要加redis锁，防止重复点击
        IcreditDatasourceEntity dataEntity = datasourceMapper.selectById(id);
        if (dataEntity == null){
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
        ddlEntity.setColumnsInfo(ddlInfo);
        ddlEntity.setCreateTime(new Date());
        ddlSyncMapper.insert(ddlEntity);
        return BusinessResult.success(map.get("tablesCount").toString());
    }
}
