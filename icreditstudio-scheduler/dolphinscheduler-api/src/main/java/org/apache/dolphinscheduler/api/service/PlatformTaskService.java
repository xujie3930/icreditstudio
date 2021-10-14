package org.apache.dolphinscheduler.api.service;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.param.CreatePlatformTaskParam;
import org.apache.dolphinscheduler.api.service.result.CreatePlatformTaskResult;

/**
 * @author Peng
 */
public interface PlatformTaskService {

    /**
     * icredit平台一个任务对应一个工作流定义
     *
     * @param param
     * @return
     */
    BusinessResult<CreatePlatformTaskResult> create(CreatePlatformTaskParam param);
}
