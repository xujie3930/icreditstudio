package com.jinninghui.datasphere.icreditstudio.system.modules.system.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.log.entity.AuditLogEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.log.mapper.AuditLogMapper;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.log.service.AuditLogService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.log.service.param.AuditLogEntitySaveParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.log.web.request.AuditLogEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.Query;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.validate.BusinessParamsValidate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service("auditLogService")
public class AuditLogServiceImpl extends ServiceImpl<AuditLogMapper, AuditLogEntity> implements AuditLogService {

    @Override
    @BusinessParamsValidate
    public BusinessPageResult queryPage(AuditLogEntityPageRequest pageRequest) {
        QueryWrapper<AuditLogEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(pageRequest.getUserName())) {
            wrapper.eq(AuditLogEntity.USER_NAME, pageRequest.getUserName());
        }
        if (StringUtils.isNotBlank(pageRequest.getOprateInfo())) {
            wrapper.like(AuditLogEntity.OPRATE_INFO, pageRequest.getOprateInfo());
        }
        if (Objects.nonNull(pageRequest.getStartTime())) {
            wrapper.ge(AuditLogEntity.OPRATE_TIME, pageRequest.getStartTime());
        }
        if (Objects.nonNull(pageRequest.getEndTime())) {
            wrapper.le(AuditLogEntity.OPRATE_TIME, pageRequest.getEndTime());
        }
        wrapper.orderByDesc(AuditLogEntity.CREATE_TIME);
        IPage<AuditLogEntity> page = this.page(
                new Query<AuditLogEntity>().getPage(pageRequest),
                wrapper
        );
        return BusinessPageResult.build(page, pageRequest);
    }

    @Override
    public AuditLogEntity log(AuditLogEntitySaveParam param) {

        AuditLogEntity entity = BeanCopyUtils.copyProperties(param, AuditLogEntity.class);
        entity.setOprateTime(param.getOprateTime() + "");
        save(entity);
        return entity;
    }
}
