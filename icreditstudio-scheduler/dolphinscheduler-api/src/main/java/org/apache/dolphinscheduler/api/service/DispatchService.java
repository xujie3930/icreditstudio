package org.apache.dolphinscheduler.api.service;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.param.DispatchTaskPageParam;
import org.apache.dolphinscheduler.api.service.result.DispatchTaskPageResult;
import org.apache.dolphinscheduler.common.vo.DispatchLogVO;

import java.util.List;

public interface DispatchService {

    BusinessPageResult<DispatchTaskPageResult> dispatchPage(DispatchTaskPageParam param);

    BusinessResult<Boolean> startOrStop(String taskId, String execType);

    BusinessResult<List<DispatchLogVO>> logPage(String taskId);
}
