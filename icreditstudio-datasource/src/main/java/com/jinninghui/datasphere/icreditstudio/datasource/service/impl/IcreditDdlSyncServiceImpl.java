package com.jinninghui.datasphere.icreditstudio.datasource.service.impl;

import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDdlSyncEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.mapper.IcreditDdlSyncMapper;
import com.jinninghui.datasphere.icreditstudio.datasource.service.IcreditDdlSyncService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xujie
 * @since 2021-08-25
 */
@Service
public class IcreditDdlSyncServiceImpl extends ServiceImpl<IcreditDdlSyncMapper, IcreditDdlSyncEntity> implements IcreditDdlSyncService {
    @Override
    public Map<String, List<IcreditDdlSyncEntity>> categoryLatelyDdlSyncs(String datasourceId) {
        return null;
    }
}
