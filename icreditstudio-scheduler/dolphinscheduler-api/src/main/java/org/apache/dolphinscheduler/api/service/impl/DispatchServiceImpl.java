package org.apache.dolphinscheduler.api.service.impl;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import org.apache.dolphinscheduler.api.feign.DataSyncDispatchTaskFeignClient;
import org.apache.dolphinscheduler.api.param.DispatchTaskPageParam;
import org.apache.dolphinscheduler.api.service.DispatchService;
import org.apache.dolphinscheduler.api.vo.DispatchTaskPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DispatchServiceImpl implements DispatchService {

    @Autowired
    private DataSyncDispatchTaskFeignClient dataSyncDispatchTaskFeignClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessPageResult<DispatchTaskPageVO> dispatchPage(DispatchTaskPageParam param) {
        return dataSyncDispatchTaskFeignClient.dispatchPage(param);
    }
}
