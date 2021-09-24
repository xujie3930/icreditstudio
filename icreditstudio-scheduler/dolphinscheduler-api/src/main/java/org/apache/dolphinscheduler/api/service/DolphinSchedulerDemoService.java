package org.apache.dolphinscheduler.api.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.dolphinscheduler.api.enums.Status;
import org.apache.dolphinscheduler.api.request.InstanceCreateRequest;
import org.apache.dolphinscheduler.api.utils.Result;
import org.apache.dolphinscheduler.common.enums.*;
import org.apache.dolphinscheduler.dao.entity.Tenant;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.dao.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.dolphinscheduler.api.enums.Status.RESOURCE_FILE_IS_EMPTY;

@Service
public class DolphinSchedulerDemoService {

    private static final Logger logger = LoggerFactory.getLogger(DolphinSchedulerDemoService.class);

    @Autowired
    private TenantService tenantService;
    @Autowired
    private ResourcesService resourceService;
    @Autowired
    private ProcessDefinitionService processDefinitionService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ExecutorService execService;
    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public void test(User loginUser, JSONObject jsonObject) throws Exception {
        Map<String,Object> contentMap = (Map<String, Object>) jsonObject.get("content");
        JSONObject oo = (JSONObject) JSONObject.toJSON(contentMap);
        String content = oo.toJSONString();//datax执行的JSON文件内容

        String tenantCode = "root";//租户编码
        Tenant tenant = tenantService.findByTenantCode(tenantCode);
        int tenantId;
        if(tenant == null){//租户不存在则创建租户
            Map<String, Object> tenantResult = tenantService.createTenant(loginUser,  "root", 1, "");
            tenantId = (int) tenantResult.get("tenantId");
            loginUser.setTenantId(tenantId);
            userMapper.updateById(loginUser);//将用户与租户绑定
        }else {
            tenantId = tenant.getId();
        }

        long currTime = System.currentTimeMillis();
        ResourceType type = ResourceType.FILE;//资源文件类型
        String fileName = "file" + currTime;//生成 资源文件名
        String fileSuffix = "json";//资源文件后缀
        String sourceDescription = "";//资源文件描述  可为空
        int pid = -1;
        String currentDir = "/";
        int userId = 1;//登录用户ID

        String projectName = "项目名称" + currTime;//生成 自定义 项目名称， 可替换成工作空间
        String projectDescription = "";//项目描述  可为空

        String processDefName = "processDefinition" + currTime;//工作流定义名称
        String processDefDesc = "";//工作流定义描述  可为空
        String taskId = "tasks-" + currTime;
        String taskName = "node" + currTime;
        String processDefLocations = appendProcessDefLocations(taskId, taskName);//拼接工作流定义 任务节点 JSON
        String processDefConnects = "[]";//JSON格式，可为空

        String scheduleTime = "";
        FailureStrategy failureStrategy = FailureStrategy.CONTINUE;
        String startNodeList = "";
        TaskDependType taskDependType = TaskDependType.TASK_POST;
        CommandType execType = null;
        WarningType warningType = WarningType.NONE;
        int warningGroupId = 0;
        String receivers = "";
        String receiversCc = "";
        RunMode runMode = RunMode.RUN_MODE_SERIAL;
        Priority processInstancePriority = Priority.MEDIUM;//紧急程度
        String workerGroup = "default";
        Integer timeout = 86400;

//        User loginUser = getUserById(userId);

        //创建datax执行文件
        Result result = onlineCreateResource(loginUser, type, fileName, fileSuffix, sourceDescription, content, pid, currentDir, tenantCode);
        Map<String,Object> map = (Map<String, Object>) result.getData();
        int sourceId = (int) map.get("resourceId");

        //创建项目  -- 这边可以改成 workspace
        Map<String, Object> projectResult = projectService.createProject(loginUser , projectName, projectDescription);

        //创建工作流定义，默认上线
        String processDefJson = appendProcessDefJson(taskId, taskName, sourceId, tenantId, fileName, fileSuffix);//拼接工作流定义JSON
        Map<String, Object> processDefinitionResult = processDefinitionService.createProcessDefinition(loginUser, projectName, processDefName, processDefJson, processDefDesc, processDefLocations, processDefConnects);

        //启动工作流定义
        int processDefinitionId = (int) processDefinitionResult.get("processDefinitionId");
        Map<String, Object> execProcessInstanceResult = execService.execProcessInstance(loginUser, projectName, processDefinitionId, scheduleTime, execType, failureStrategy,startNodeList,
                taskDependType, warningType,warningGroupId, runMode, processInstancePriority, workerGroup, timeout, null);

    }

    private String appendProcessDefJson(String taskId, String taskName, int sourceId, int tenantId, String fileName, String fileSuffix) {
        Map<String,Object> outMap = new HashMap<>();
        List<Map<String,Object>> taskList = new ArrayList<>();
        Map<String,Object> taskFirstMap = new HashMap<>();
        Map<String,Object> paramsMap = new HashMap<>();
        List<Map<String,Object>> resourceList = new ArrayList<>();
        Map<String,Object> resourceMap = new HashMap<>();
        resourceMap.put("id", sourceId);
        resourceMap.put("name", fileName + "." + fileSuffix);
        resourceMap.put("res", fileName + "." + fileSuffix);
        paramsMap.put("resourceList", resourceList);
        paramsMap.put("localParams", new ArrayList<>());
        paramsMap.put("rawScript", "#!/bin/bash\n" +
//                "hive -e \"truncate table dfstest.dfs_mysql\"\n" +
                "python /usr/local/software/idea/workspace/datax/bin/datax.py " + fileName + "." + fileSuffix);

        taskFirstMap.put("type","SHELL");
        taskFirstMap.put("id",taskId);
        taskFirstMap.put("name",taskName);
        taskFirstMap.put("params",paramsMap);
        taskFirstMap.put("description","");

        Map<String,Object> timeoutMap = new HashMap<>();
        timeoutMap.put("strategy","");
        timeoutMap.put("interval",null);
        timeoutMap.put("enable",false);
        taskFirstMap.put("timeout",timeoutMap);
        taskFirstMap.put("runFlag", "NORMAL");

        Map<String,Object> conditionResultMap = new HashMap<>();
        List<String> conditionResultList = new ArrayList<>();
        conditionResultList.add("");
        conditionResultMap.put("successNode", conditionResultList);
        conditionResultMap.put("failedNode", conditionResultList);
        taskFirstMap.put("conditionResult", conditionResultMap);
        taskFirstMap.put("dependence", new HashMap<String,Object>());
        taskFirstMap.put("maxRetryTimes", "0");
        taskFirstMap.put("retryInterval", "1");
        taskFirstMap.put("taskInstancePriority", "MEDIUM");
        taskFirstMap.put("workerGroup", "default");
        taskFirstMap.put("preTasks", new ArrayList<>());

        resourceList.add(resourceMap);
        taskList.add(taskFirstMap);

        outMap.put("globalParams",new ArrayList<>());
        outMap.put("tasks",taskList);
        outMap.put("tenantId",tenantId);
        outMap.put("timeout",0);
        return ((JSONObject) JSONObject.toJSON(outMap)).toJSONString();
    }

    private String appendProcessDefLocations(String taskId, String taskName) {
        Map<String,Object> outMap = new HashMap<>();
        Map<String,Object> innerMap = new HashMap<>();
        innerMap.put("name", taskName);
        innerMap.put("targetarr","");
        innerMap.put("nodenumber","0");
        innerMap.put("x",121);
        innerMap.put("y",146);
        outMap.put(taskId, innerMap);
        return ((JSONObject) JSONObject.toJSON(outMap)).toJSONString();
    }

    public User getUserById(int userId){
        return userMapper.selectById(userId);
    }

    public Result onlineCreateResource(User loginUser, ResourceType type, String fileName,  String fileSuffix, String sourceDescription, String content, int pid, String currentDir, String tenantCode){
        if (StringUtils.isEmpty(content)) {
            logger.error("resource file contents are not allowed to be empty");
            return error(Status.RESOURCE_FILE_IS_EMPTY.getCode(), RESOURCE_FILE_IS_EMPTY.getMsg());
        }
        return resourceService.myOnlineCreateResource(loginUser, type, fileName, fileSuffix, sourceDescription, content, pid, currentDir, tenantCode);
    }

    public Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }


    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> dataxTest(User loginUser, InstanceCreateRequest request) throws Exception {
        String tenantCode = request.getTenantCode();//租户编码
        String schedule = request.getScheduler();//cron表达式
        String startTime = request.getStartTime();//scheduler开始时间
        String endTime = request.getEndTime();//scheduler结束时间

        long currTime = System.currentTimeMillis();
        String projectName = "项目" + currTime;//生成 自定义 项目名称， 可替换成工作空间
        String projectDescription = "";//项目描述  可为空

        String processDefName = "processDefinition" + currTime;//工作流定义名称
        String processDefDesc = "";//工作流定义描述  可为空
        String taskId = "tasks-" + currTime;
        String taskName = "node" + currTime;
        String processDefLocations = appendProcessDefLocations(taskId, taskName);//拼接工作流定义 任务节点 JSON
        String processDefConnects = "[]";//JSON格式，可为空

        String scheduleTime = "";
        FailureStrategy failureStrategy = FailureStrategy.CONTINUE;
        String startNodeList = "";
        TaskDependType taskDependType = TaskDependType.TASK_POST;
        CommandType execType = null;
        WarningType warningType = WarningType.NONE;
        int warningGroupId = 0;
        String receivers = "";
        String receiversCc = "";
        RunMode runMode = RunMode.RUN_MODE_SERIAL;
        Priority processInstancePriority = Priority.MEDIUM;//紧急程度
        String workerGroup = "default";
        Integer timeout = 86400;

        Map<String, Object> projectResult = projectService.createProject(loginUser , projectName, projectDescription);

        Tenant tenant = tenantService.findByTenantCode(tenantCode);

        //创建工作流定义，默认上线
        String processDefJson = appendDataxProcessDefJson(tenant.getId(), taskId, taskName, request);//拼接工作流定义JSON
        Map<String, Object> processDefinitionResult = processDefinitionService.createProcessDefinition(loginUser, projectName, processDefName, processDefJson, processDefDesc, processDefLocations, processDefConnects);

        //启动工作流定义
        int processDefinitionId = (int) processDefinitionResult.get("processDefinitionId");
        Map<String, Object> execProcessInstanceResult = execService.execProcessInstance(loginUser, projectName, processDefinitionId, scheduleTime, execType, failureStrategy,startNodeList,
                taskDependType, warningType,warningGroupId, runMode, processInstancePriority, workerGroup, timeout, null);

        if (StringUtils.isNotBlank(schedule) && StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            Map<String, Object> scheduleResult = schedulerService.myInsertSchedule(startTime, endTime, loginUser, projectName, processDefinitionId, schedule,
                    warningType, warningGroupId, failureStrategy, receivers, receiversCc, processInstancePriority, workerGroup);
            schedulerService.setScheduleState(loginUser, projectName, (int) scheduleResult.get("scheduleId"), ReleaseState.ONLINE);
        }

        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("processDefinitionId", processDefinitionId+"");
        resultMap.put("","");
        return resultMap;
    }

    private String appendDataxProcessDefJson(int tenantId, String taskId, String taskName, InstanceCreateRequest request) {
        Map<String,Object> outMap = new HashMap<>();
        List<String> emptyList = new ArrayList<>();
        List<Map<String,Object>> taskList = new ArrayList<>();
        Map<String,Object> taskFirstMap = new HashMap<>();
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("customConfig", 0);
        //这里写入所有自定义参数
        Field[] fields = request.getClass().getDeclaredFields();
        for(Field field : fields){
            Object value = getFieldValueByName(field.getName(), request);
            paramsMap.put(field.getName(), value);
        }
        paramsMap.put("jobSpeedByte", 0);
        paramsMap.put("jobSpeedRecord", 1000);
        paramsMap.put("preStatements", emptyList);
        paramsMap.put("postStatements", emptyList);

        taskFirstMap.put("type","DATAX");
        taskFirstMap.put("id",taskId);
        taskFirstMap.put("name",taskName);
        taskFirstMap.put("params",paramsMap);
        taskFirstMap.put("description","");

        Map<String,Object> timeoutMap = new HashMap<>();
        timeoutMap.put("strategy","");
        timeoutMap.put("interval",null);
        timeoutMap.put("enable",false);
        taskFirstMap.put("timeout",timeoutMap);
        taskFirstMap.put("runFlag", "NORMAL");

        Map<String,Object> conditionResultMap = new HashMap<>();
        List<String> conditionResultList = new ArrayList<>();
        conditionResultList.add("");
        conditionResultMap.put("successNode", conditionResultList);
        conditionResultMap.put("failedNode", conditionResultList);
        taskFirstMap.put("conditionResult", conditionResultMap);
        taskFirstMap.put("dependence", new HashMap<String,Object>());
        taskFirstMap.put("maxRetryTimes", "0");
        taskFirstMap.put("retryInterval", "1");
        taskFirstMap.put("taskInstancePriority", "MEDIUM");
        taskFirstMap.put("workerGroup", "default");
        taskFirstMap.put("preTasks", emptyList);

        taskList.add(taskFirstMap);

        outMap.put("globalParams",emptyList);
        outMap.put("tasks",taskList);
        outMap.put("tenantId",tenantId);
        outMap.put("timeout",0);
        return ((JSONObject) JSONObject.toJSON(outMap)).toJSONString();
    }

    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            logger.error("获取属性值失败！" + e, e);
        }
        return null;
    }

}
