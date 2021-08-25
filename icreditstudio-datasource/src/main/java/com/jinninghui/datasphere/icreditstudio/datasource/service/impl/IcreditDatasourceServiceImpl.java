package com.jinninghui.datasphere.icreditstudio.datasource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDatasourceEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.mapper.IcreditDatasourceMapper;
import com.jinninghui.datasphere.icreditstudio.datasource.service.IcreditDatasourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.IcreditDatasourceDelParam;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.IcreditDatasourceSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.IcreditDatasourceEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.Query;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.sequence.api.SequenceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Action;
import java.util.Date;
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
}
