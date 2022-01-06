package org.apache.dolphinscheduler.api.feign;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.api.feign.result.DictInfo;
import org.apache.dolphinscheduler.api.param.DispatchTaskPageParam;
import org.apache.dolphinscheduler.api.service.result.DispatchTaskPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

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
    BusinessResult<BusinessPageResult<DispatchTaskPageResult>> dispatchPage(@RequestBody DispatchTaskPageParam param);

    /**
     * 获取任务中的 流程定义ID
     */
    @GetMapping("/datasync/getProcessDefinitionId")
    String getProcessDefinitionIdByTaskId(@RequestParam("taskId") String taskId);

    /**
     * 通过scheduleId来更新执行状态
     */
    @GetMapping("/datasync/updateExecStatusByScheduleId")
    boolean updateExecStatusByScheduleId(@RequestParam("scheduleId") String scheduleId);

    /**
     * 根据字典ID获取字典的列值
     *
     * @param dictIds
     * @return
     */
    @GetMapping("/datasync/getDictColumnsByDictIds")
    BusinessResult<DictInfo> getDictColumnsByDictIds(@RequestParam("dictIds") Set<String> dictIds);
}
