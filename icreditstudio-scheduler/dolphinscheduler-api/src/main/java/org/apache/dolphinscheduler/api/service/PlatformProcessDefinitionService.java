package org.apache.dolphinscheduler.api.service;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.param.CreatePlatformProcessDefinitionParam;
import org.apache.dolphinscheduler.api.param.ReleasePlatformProcessDefinitionParam;
import org.apache.dolphinscheduler.api.service.result.CreatePlatformTaskResult;

/**
 * @author Peng
 */
public interface PlatformProcessDefinitionService {

    /**
     * icredit平台一个任务对应一个工作流定义
     *
     * @param param
     * @return
     */
    BusinessResult<CreatePlatformTaskResult> create(CreatePlatformProcessDefinitionParam param);

    /**
     * 上线工作流定义
     *
     * @param param
     * @return
     */
    BusinessResult<Boolean> release(ReleasePlatformProcessDefinitionParam param);
}
