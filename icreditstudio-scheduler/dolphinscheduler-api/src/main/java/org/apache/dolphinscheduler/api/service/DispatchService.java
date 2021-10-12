package org.apache.dolphinscheduler.api.service;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import org.apache.dolphinscheduler.api.param.DispatchTaskPageParam;
import org.apache.dolphinscheduler.api.vo.DispatchTaskPageVO;

public interface DispatchService {

    BusinessPageResult<DispatchTaskPageVO> dispatchPage(DispatchTaskPageParam param);
}
