package com.jinninghui.datasphere.icreditstudio.system.modules.system.information.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.information.entity.InfoReceiverEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.information.enums.ReadStatusEnum;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.information.mapper.InfoReceiverMapper;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.information.service.InfoReceiverService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.information.service.param.InfoReceiverDataParam;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service("infoReceiverService")
public class InfoReceiverServiceImpl extends ServiceImpl<InfoReceiverMapper, InfoReceiverEntity> implements InfoReceiverService {

    @Override
    public List<InfoReceiverEntity> getInfoReceiverFromDatabase(InfoReceiverDataParam param) {
        QueryWrapper<InfoReceiverEntity> wrapper = queryWrapper(param);
        return list(wrapper);
    }

    private QueryWrapper<InfoReceiverEntity> queryWrapper(InfoReceiverDataParam param) {
        QueryWrapper<InfoReceiverEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getInfoId())) {
            wrapper.eq(InfoReceiverEntity.INFO_ID, param.getInfoId());
        }
        if (CollectionUtils.isNotEmpty(param.getInfoIds())) {
            wrapper.in(InfoReceiverEntity.INFO_ID, param.getInfoIds());
        }
        if (StringUtils.isNotBlank(param.getReceiverId())) {
            wrapper.eq(InfoReceiverEntity.RECEIVER_ID, param.getReceiverId());
        }
        if (CollectionUtils.isNotEmpty(param.getReceiverIds())) {
            wrapper.in(InfoReceiverEntity.RECEIVER_ID, param.getReceiverIds());
        }
        if (Objects.nonNull(param.getReadStatus()) && !ReadStatusEnum.ALL.equals(param.getReadStatus())) {
            wrapper.eq(InfoReceiverEntity.READ_STATUS, param.getReadStatus().getCode());
        }
        if (Objects.nonNull(param.getDeleteFlag())) {
            wrapper.eq(InfoReceiverEntity.DELETE_FLAG, param.getDeleteFlag().getCode());
        }
        wrapper.orderByDesc(InfoReceiverEntity.CREATE_TIME);
        return wrapper;
    }
}
