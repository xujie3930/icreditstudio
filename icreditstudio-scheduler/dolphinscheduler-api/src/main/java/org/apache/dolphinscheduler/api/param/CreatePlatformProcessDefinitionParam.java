package org.apache.dolphinscheduler.api.param;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.dolphinscheduler.api.request.CreatePlatformProcessDefinitionRequest;
import org.apache.dolphinscheduler.common.enums.TaskType;

import java.util.List;

/**
 * @author Peng
 */
public class CreatePlatformProcessDefinitionParam extends CreatePlatformProcessDefinitionRequest {

    public ProcessDefinitionJson buildProcessDefinitionJson() {
        ProcessDefinitionJson definitionJson = new ProcessDefinitionJson();
        definitionJson.setTimeout(this.getOrdinaryParam().getTimeOut());
        definitionJson.setTenantCode(this.getAccessUser().getTenantCode());
        definitionJson.setGlobalParams(Lists.newArrayList());
        List<TaskNodeStruct> structs = Lists.newArrayList();
        TaskNodeStruct struct = new TaskNodeStruct();
        struct.setType(TaskType.DATAX.getDesc());
        struct.setId(StrUtil.concat(true, "tasks-", RandomUtil.randomNumbers(5)));
        struct.setName(this.getOrdinaryParam().getName());
        struct.setParams(new NodeParam(1, this.getOrdinaryParam().getTaskJson(), Lists.newArrayList()));
        struct.setDescription("");
        struct.setTimeout(new TimeOutParam("", null, false));
        struct.setRunFlag("NORMAL");
        struct.setConditionResult(new ConditionResult(Lists.newArrayList(), Lists.newArrayList()));
        struct.setDependence(Maps.newHashMap());
        struct.setMaxRetryTimes("0");
        struct.setRetryInterval("1");
        struct.setTaskInstancePriority("MEDIUM");
        struct.setWorkerGroup("default");
        struct.setPreTasks(Lists.newArrayList());

        structs.add(struct);
        definitionJson.setTasks(structs);
        return definitionJson;
    }
}
