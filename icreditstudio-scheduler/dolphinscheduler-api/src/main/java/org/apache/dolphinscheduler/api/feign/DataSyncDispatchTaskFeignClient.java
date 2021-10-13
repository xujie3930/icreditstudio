package org.apache.dolphinscheduler.api.feign;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import org.apache.dolphinscheduler.api.param.DispatchTaskPageParam;
import org.apache.dolphinscheduler.api.vo.DispatchTaskPageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author lmh
 * @create 2021-10-12 10:47
 **/
@FeignClient("datasync")
public interface DataSyncDispatchTaskFeignClient {

    /**
     * 获取周期任务列表
     */
    @PostMapping("/datasync/dispatchPage")
    BusinessPageResult<DispatchTaskPageVO> dispatchPage(@RequestBody DispatchTaskPageParam param) ;
}
