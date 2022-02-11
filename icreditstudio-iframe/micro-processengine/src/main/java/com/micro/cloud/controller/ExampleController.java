package com.micro.cloud.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.XmlUtil;

import com.micro.cloud.modules.process.component.CheckApprove;
import com.micro.cloud.modules.process.constant.ProcessConstant;
import com.micro.cloud.modules.process.vo.ActivityHighLineVO;
import com.micro.cloud.modules.process.vo.LabelValueVO;
import com.micro.cloud.modules.process.vo.TaskVO;
import com.micro.cloud.util.Util;
import com.micro.cloud.util.json.JsonUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.Query;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/** 流程引擎测试接口 Created by xulei on 2021/11/9 */
@RestController
public class ExampleController {

  private final Logger logger = LoggerFactory.getLogger(ExampleController.class);

  public static final String HUIQIAN_LOCAL_VAR_KEY = "devApproves";

  @Autowired RepositoryService repositoryService;

  @Autowired RuntimeService runtimeService;

  @Autowired TaskService taskService;

  @Autowired HistoryService historyService;

  @Autowired CheckApprove checkApprove;

  @Autowired IdentityService identityService;

  /**
   * 流程定义部署 部署完成后可以去观察数据库表的变化：act_re_deployment 中会新增本次的部署信息、
   * act_re_procdef流程定义的一些信息、act_ge_bytearray 部署的资源文件。。 added by xulei 2021.11.09
   *
   * @return
   */
  @GetMapping("/deploy/{bpmnFile}")
  public String deployProcess(@PathVariable("bpmnFile") String bpmnFile) {
    Deployment deploy =
        repositoryService
            .createDeployment()
            .addClasspathResource("BPMN/" + bpmnFile) // firstApprove.bpmn
            .deploy();
    logger.info(deploy.getName());
    return deploy.getName()
        + "|"
        + deploy.getId()
        + "|"
        + deploy.getSource()
        + "|"
        + deploy.getTenantId()
        + "|"
        + deploy.getDeploymentTime();
  }

  /**
   * 开启一条流程，并给用户任务的 assignee 赋值 一个流程实例启动后，可以去观察 act_hi_actinst 表中记录了每个任务的相关信息(审批人、开始时间、结束时间等等)、
   * act_hi_identitylink 表中记录了流程实例的参与者、act_hi_procinst 表中是流程实例的信息、 act_hi_tastinst
   * 记录了流程实例中每个任务实例的信息、act_ru_identitylink 表中记录了运行时的流程实例当前的参与者、 act_ru_tast
   * 表记录了当前运行中的任务、act_ru_variable 表中记录了设置的参数、等等 added by xulei 2021.11.09
   */
  @GetMapping("/start/{processKey}/{businessKey}")
  public String startProcessInstanceWithAssignee(
      @PathVariable("processKey") String processKey,
      @PathVariable("businessKey") String businessKey) {

    Map<String, Object> map = new HashMap<>();
    map.put("employee", "xulei");
    //        map.put("devApprove","zhangmingchen");
    map.put("hrApprove", "juzuo");
    map.put("days", 2);
    /**
     * Camunda 支持的变量类型不限于：String、int、short、long、double、date、binary、 serializable(Java
     * 对象需要去实现序列化接口，并生成 serialVersionUID)
     *
     * <p>流程变量的作用域默认是一个流程实例(从流程启动到流程结束)，一个流程实例是一个流程变量最大范围的作用域，所以被称为 global
     * 变量；作用域也可以是一个任务(Task)或者一个执行实例(execution)，所以被称为 local 变量。 global
     * 变量中变量名不允许重复，设置相同的变量名，后设置的值会覆盖先设置的值。local 变量和 global 变量可以变量名相同，没有影响。
     */
    // Process_1m8om1k
    ProcessInstance holiday =
        runtimeService.startProcessInstanceByKey(processKey, businessKey, map);
    logger.info("BusinessKey:" + holiday.getBusinessKey());
    /**
     * 1.启动流程时设置变量： //键值对形式的变量，值也可以是实现了 serializable 接口的对象 Map<String,Object> params = new
     * HashMap<>(); params.put("xxx","xxx"); params.put("xxx","xxx"); ... ProcessInstance holiday =
     * runtimeService.startProcessInstanceByKey("holiday",params);
     *
     * <p>2.完成任务时设置变量： //依然是键值对形式的 params taskService.complete(task.getId(),params);
     * 3.设置global/local变量： Map params = new HashMap(); params.put("days",3);
     * params.put("type","休假"); ... //设置global变量
     * runtimeService.setVariable("excutionId","key","value");
     * runtimeService.setVariables("excutionId",params); //设置local变量
     * runtimeService.setVariableLocal("excutionId","key","value");
     * runtimeService.setVariablesLocal("excutionId",params);
     */
    // 流程实例Id
    logger.info(holiday.getProcessInstanceId());
    logger.info(holiday.getId());
    logger.info(holiday.getProcessDefinitionId());
    return holiday.getProcessInstanceId();
  }

  /**
   * 继续观察启动流程时介绍的表，act_ru_task 中之前 xxx 的用户任务已经被删除, 在 act_hi_taskinst 中已经变为 completed 状态；但是由于我们设置的是
   * global 变量， 在 act_ru_variable 中还可以找到开启流程时设置的参数；其他的表中也会有记录，细细观察会有更多收获
   *
   * @param user
   */
  @GetMapping("/complete/{processInstanceId}/{user}/{lineValue}/{paraValue}/{comment}")
  public String taskComplete(
      @PathVariable("processInstanceId") String processInstanceId,
      @PathVariable("user") String user,
      @PathVariable("lineValue") String lineValue,
      @PathVariable("paraValue") String paraValue,
      @PathVariable("comment") String comment) {

    /**
     * 会签审批时需要使用以下全局变量: nrOfInstance 实例的数目 nrOfCompletedInstances 完成实例的数目 nrOfActiveInstance
     * 未完成实例的数目 loopCounter 循环计数器，办理人在列表中的索引。
     */
    List<Task> taskList =
        taskService
            .createTaskQuery()
            .taskAssignee(user)
            .processInstanceId(processInstanceId)
            .list();
    Task task = CollUtil.isNotEmpty(taskList) ? taskList.get(0) : null;

    /** 此处逻辑为获取下一节点处理人流程变量名及出线变量--begin */
    // 下一节点为单实例普通节点时的处理人变量名称
    AtomicReference<String> assigneeVarExtractMulti = new AtomicReference<>("");
    // 下一节点为多实例会签节点时的处理人集合变量名称
    AtomicReference<String> utCollectionVar = new AtomicReference<>("");
    // 当前节点出线表达式变量名称
    AtomicReference<String> conditionExpressionVar = new AtomicReference<>("");
    // 获取流程定义的bpmn模型
    BpmnModelInstance bpmn = repositoryService.getBpmnModelInstance(task.getProcessDefinitionId());
    ModelElementInstance domElement = bpmn.getModelElementById(task.getTaskDefinitionKey());
    // 转换成 flowNode流程节点 才能获取到前后元素
    FlowNode act = (FlowNode) domElement;
    FlowNode next = null;
    SequenceFlow outGoing = null;
    if (Util.isNotEmpty(act)) {
      Query<FlowNode> succeedings = act.getSucceedingNodes(); // 获取当前节点后续节点
      Collection<SequenceFlow> outGoingLines = act.getOutgoing(); // 获取当前节点出线
      if (!outGoingLines.isEmpty() && outGoingLines.size() > 1) {
        // 如果当前节点出线大于1条，则会有1个线变量来决定出线走哪根，因此获取该线变量名称时取第一条即可
        outGoing = outGoingLines.stream().findFirst().get();
      }
      if (Util.isNotEmpty(succeedings) && succeedings.count() > 0) {
        // 此处需要约定：当节点outgoing线大于1条时，后续节点表达式变量名称需要设置相同值。
        next = succeedings.list().get(0);
      }
    }

    String bpmnStr =
        new BufferedReader(
                new InputStreamReader(
                    repositoryService.getProcessModel(task.getProcessDefinitionId())))
            .lines()
            .collect(Collectors.joining(System.lineSeparator()));
    Document bpmnDocument = XmlUtil.parseXml(bpmnStr);
    Element goalElement =
        XmlUtil.getElementByXPath(ProcessConstant.BPMN_GOALELEMENT_XPATH, bpmnDocument);

    // 获取当前节点出线大于1条时的出线表达式变量名
    String outGoingLineKey = "";
    if (Util.isNotEmpty(outGoing)) {
      outGoingLineKey = outGoing.getAttributeValue(ProcessConstant.CURNODE_ATTRI_ID_LABEL);
      if (Util.isNotEmpty(outGoingLineKey)) {
        List<Element> sequenceFlowElements =
            XmlUtil.getElements(goalElement, ProcessConstant.BPMN_SEQUENCEFLOW_TAGNAME);
        if (Util.isNotEmpty(sequenceFlowElements)) {
          String finalOutGoingLineKey = outGoingLineKey;
          sequenceFlowElements.stream()
              .forEach(
                  sequenceFlowE -> {
                    String sfeID =
                        sequenceFlowE.getAttribute(ProcessConstant.CURNODE_ATTRI_ID_LABEL);
                    if (finalOutGoingLineKey.equals(sfeID)) {
                      String sequenceFlowSourceRef =
                          sequenceFlowE.getAttribute(ProcessConstant.BPMN_SEQUENCEFLOW_SOURCEREF);
                      List<Element> conditionExpressions =
                          XmlUtil.getElements(
                              sequenceFlowE, ProcessConstant.BPMN_CONDITIONEXPRESSION_TAGNAME);
                      // 每个bpmn:sequenceFlow节点如果有变量判断表达式则只会有1个bpmn:conditionExpression
                      if (Util.isNotEmpty(conditionExpressions)) {
                        // 此处需要判断该线的源头是否为gateway网关节点，因为如果源头为gateway则判断表达式的格式会有所不同
                        if (sequenceFlowSourceRef.startsWith(
                            ProcessConstant.GATEWAY_LOCAL_VAR_KEY)) { // 源头为网关的线
                          conditionExpressionVar.set(
                              ReUtil.extractMulti(
                                  ProcessConstant.GATEWAY_OUTGOING_REGEX,
                                  conditionExpressions.get(0).getTextContent(),
                                  "$1"));
                          logger.info(
                              "next-conditionExpressionVar:" + conditionExpressionVar.get());
                        } else { // 源头非网关的线
                          conditionExpressionVar.set(
                              ReUtil.extractMulti(
                                  ProcessConstant.OUTGOING_REGEX,
                                  conditionExpressions.get(0).getTextContent(),
                                  "$1"));
                          logger.info(
                              "next-conditionExpressionVar:" + conditionExpressionVar.get());
                        }
                      }
                    }
                  });
        }
      }
    }

    // 获取下一节点代理人表达式变量名
    String nextTaskDefinitionKey = "";
    if (Util.isNotEmpty(next)) {
      nextTaskDefinitionKey = next.getAttributeValue(ProcessConstant.CURNODE_ATTRI_ID_LABEL);
      if (Util.isNotEmpty(nextTaskDefinitionKey)) {
        List<Element> userTaskElements =
            XmlUtil.getElements(goalElement, ProcessConstant.BPMN_USERTASK_TAGNAME);
        if (Util.isNotEmpty(userTaskElements)) {
          String finalNextTaskDefinitionKey = nextTaskDefinitionKey;
          userTaskElements.stream()
              .forEach(
                  userTe -> {
                    String uteID = userTe.getAttribute(ProcessConstant.CURNODE_ATTRI_ID_LABEL);
                    if (finalNextTaskDefinitionKey.equals(uteID)) {
                      String utAssigneeVar =
                          userTe.getAttribute(ProcessConstant.BPMN_CAMUNDA_ASSIGNEE_TAGNAME);
                      assigneeVarExtractMulti.set(
                          ReUtil.extractMulti(
                              ProcessConstant.VAREXTRACTMULTI_REGEX, utAssigneeVar, "$1"));

                      logger.info("next-assignee:" + utAssigneeVar);
                      logger.info("next-assigneeVarExtractMulti:" + assigneeVarExtractMulti.get());
                      List<Element> multiInstanceLoopCharacteristics =
                          XmlUtil.getElements(
                              userTe,
                              ProcessConstant.BPMN_MULTIINSTANCELOOPCHARACTERISTICS_TAGNAME);
                      // 每个userTask节点如果是多实例会签节点则只会有1个bpmn:multiInstanceLoopCharacteristics
                      if (Util.isNotEmpty(multiInstanceLoopCharacteristics)) {
                        utCollectionVar.set(
                            multiInstanceLoopCharacteristics
                                .get(0)
                                .getAttribute(ProcessConstant.BPMN_CAMUNDA_COLLECTION_TAGNAME));
                        logger.info("next-camunda:collection:" + utCollectionVar.get());
                      }
                    }
                  });
        }
      }
    }
    /** 此处逻辑为获取下一节点处理人流程变量名及出线变量--end */

    // 这设置的是审批人及查询意见时的userId。
    identityService.setAuthenticatedUserId(user);
    Comment approveComment = null;
    if (Util.isNotEmpty(comment)) {
      approveComment = taskService.createComment(task.getId(), processInstanceId, comment);
    }
    // comment意思是完成时的审批意见，可在Act_Hi_Comment里的massge查询到。

    Map<String, Object> params = new HashMap<>();
    // 当outGoing出线大于1条时需要设置出线表达式变量的值
    if (Util.isNotEmpty(lineValue) && Util.isNotEmpty(conditionExpressionVar.get())) {
      params.put(conditionExpressionVar.get(), lineValue);
    }

    // 此处设置target目标节点流程变量
    if (Util.isNotEmpty(paraValue)) {
      // 下一个节点为会签多实例审批时
      if (Util.isNotEmpty(nextTaskDefinitionKey)
          && nextTaskDefinitionKey.startsWith("hq")
          && Util.isNotEmpty(utCollectionVar.get())) {
        params.put(utCollectionVar.get(), Arrays.asList(paraValue.split(",")));
      } else { // 普通流转
        // 当前节点为多实例会签节点审批
        params.put(assigneeVarExtractMulti.get(), paraValue);
      }
      // 完成任务，交由下一节点审批
      taskService.complete(task.getId(), params);
    } else {
      // 完成任务，交由下一节点审批
      taskService.complete(task.getId());
    }
    return "流程【"
        + processInstanceId
        + "】,当前执行人【"
        + user
        + "】任务处理流转成功！审批意见【"
        + approveComment.getFullMessage()
        + "】";
  }

  /**
   * 加签实现
   *
   * @param processInstanceId
   * @param activityId
   * @param paraName
   * @param paraValue
   * @return
   */
  @GetMapping("/addInstance/{processInstanceId}/{activityId}/{paraName}/{paraValue}")
  public String addInstance(
      @PathVariable("processInstanceId") String processInstanceId,
      @PathVariable("activityId") String activityId,
      @PathVariable("paraName") String paraName,
      @PathVariable("paraValue") String paraValue) {

    runtimeService
        .createProcessInstanceModification(processInstanceId)
        .startBeforeActivity(activityId) // 会签节点的activityId
        .setVariable(paraName, paraValue)
        .execute();

    logger.info("流程【" + processInstanceId + "】会签加签【" + paraValue + "】成功");
    return "流程【" + processInstanceId + "】会签加签【" + paraValue + "】成功";
  }

  /**
   * 减签实现
   *
   * @param processInstanceId
   * @param activityId
   * @param paraName
   * @param paraValue
   * @return
   */
  @GetMapping("/delInstance/{processInstanceId}/{activityId}/{paraName}/{paraValue}")
  public String delInstance(
      @PathVariable("processInstanceId") String processInstanceId,
      @PathVariable("activityId") String activityId,
      @PathVariable("paraName") String paraName,
      @PathVariable("paraValue") String paraValue) {

    Task task =
        taskService
            .createTaskQuery()
            .taskAssignee(paraValue)
            .processInstanceId(processInstanceId)
            .singleResult();

    ActivityInstance[] activityInstances =
        runtimeService.getActivityInstance(processInstanceId).getActivityInstances(activityId);
    for (ActivityInstance activityInstance : activityInstances) {
      if (task.getExecutionId().equals(activityInstance.getExecutionIds()[0])) {
        logger.info(
            activityInstance.getId()
                + "|"
                + activityInstance.getActivityId()
                + "|"
                + activityInstance.getActivityName());

        runtimeService.removeVariable(task.getExecutionId(), paraName);

        runtimeService
            .createProcessInstanceModification(processInstanceId)
            .cancelActivityInstance(activityInstance.getId()) // 会签执行流实例的activityInstance
            .execute();
      }
    }

    // taskService.deleteTask(task.getId());

    logger.info("流程【" + processInstanceId + "】会签减签【" + paraValue + "】成功");
    return "流程【" + processInstanceId + "】会签减签【" + paraValue + "】成功";
  }

  /**
   * 获取某个节点的处理人的待办列表
   *
   * @param processInstanceId
   * @param assignee
   * @param pageNum
   * @param pageSize
   * @return
   */
  @GetMapping("/remainTaskList/{processInstanceId}/{assignee}/{pageNum}/{pageSize}")
  public String remainTaskList(
      @PathVariable("processInstanceId") String processInstanceId,
      @PathVariable("assignee") String assignee,
      @PathVariable("pageNum") int pageNum,
      @PathVariable("pageSize") int pageSize) {

    int i = (pageNum - 1) * pageSize;
    int i1 = pageSize;
    List<Task> list =
        taskService
            .createTaskQuery()
            .endOr()
            .processInstanceId(processInstanceId)
            .taskAssignee(assignee)
            .listPage(i, i1);

    List<TaskVO> taskLikeList = new ArrayList<TaskVO>();
    long count = taskService.createTaskQuery().taskAssignee(assignee).count();
    // count是表示有多少条一共。
    // page的分页就很清楚，两参一个其实页一个，页最大容量

    // 备注：
    // 最后这个任务的循环值不能直接用List<Task>集合返回，这样会报一个错com.boot.model.RestfulResponse[\"data\"]->java.util.ArrayList[0]->org.camund之类的错误
    //    百度之后说的是自己引用了自己， Task 属性里面有一些属性会导致循环引用，所以还得自己造个对象，把想输出的变成属性再次赋值输出即可。
    for (Task task : list) {
      TaskVO taskLike = new TaskVO();
      taskLike.setAssignee(task.getAssignee());
      taskLike.setCreateTime(task.getCreateTime());
      taskLike.setDescription((String) runtimeService.getVariable(task.getExecutionId(), "title"));
      //      taskLike.setExecutionId(task.getExecutionId());
      taskLike.setProcessDefinitionId(task.getProcessDefinitionId());
      taskLike.setTaskDefinitionKey(task.getTaskDefinitionKey());
      taskLike.setProcessInstanceId(task.getProcessInstanceId());
      taskLikeList.add(taskLike);
    }

    logger.info("流程【" + processInstanceId + "】获取待办列表【" + taskLikeList + "】成功");
    return "流程【" + processInstanceId + "】获取待办列表【" + taskLikeList + "】成功";
  }

  /**
   * 获取某个节点的处理人的已办列表
   *
   * @param processInstanceId
   * @param assignee
   * @param pageNum
   * @param pageSize
   * @return
   */
  @GetMapping("/alreadyDoneTaskList/{processInstanceId}/{assignee}/{pageNum}/{pageSize}")
  public String alreadyDoneTaskList(
      @PathVariable("processInstanceId") String processInstanceId,
      @PathVariable("assignee") String assignee,
      @PathVariable("pageNum") int pageNum,
      @PathVariable("pageSize") int pageSize) {

    int i = (pageNum - 1) * pageSize;
    int i1 = pageSize;

    List<HistoricTaskInstance> list1 =
        historyService // 历史任务Service
            .createHistoricTaskInstanceQuery() // 创建历史任务实例查询
            .taskAssignee(assignee) // 指定办理人
            .finished() // 查询已经完成的任务
            .listPage(i, i1);
    long count =
        historyService.createHistoricTaskInstanceQuery().taskAssignee(assignee).finished().count();
    // count是表示有多少条一共。
    List<TaskVO> taskLikeList = new ArrayList<TaskVO>();

    for (HistoricTaskInstance hti : list1) {
      TaskVO taskLike = new TaskVO(); // 自己创建的任务对象，属性都在下边set里的就不单独摆出来了
      taskLike.setAssignee(hti.getAssignee());
      taskLike.setCreateTime(hti.getStartTime());
      //
      // taskLike.setDescription((String)runtimeService.getVariable(hti.getExecutionId(),""));
      // 这个传入参数就是启动时设置map的
      //      taskLike.setExecutionId(hti.getExecutionId());
      taskLike.setProcessDefinitionId(hti.getProcessDefinitionId());
      //      taskLike.setTaskId(hti.getId());
      //      taskLike.setTaskName(hti.getName());
      taskLike.setTaskDefinitionKey(hti.getTaskDefinitionKey());
      taskLike.setProcessInstanceId(hti.getProcessInstanceId());
      taskLikeList.add(taskLike);
    }

    logger.info("流程【" + processInstanceId + "】获取已办列表【" + taskLikeList + "】成功");
    return "流程【" + processInstanceId + "】获取已办列表【" + taskLikeList + "】成功";
  }

  /**
   * 查询这个流程的审批意见,用未完成任务id查整个流程已完成任务的审批意见内容
   *
   * @param taskId
   * @return
   */
  @GetMapping("/getAlreadyDoneTaskCommentList/{taskId}")
  public String getAlreadyDoneTaskCommentList(@PathVariable("taskId") String taskId) {

    //        ProcessEngine 	processEngine=ProcessEngines.getDefaultProcessEngine();
    //        HistoryService historyService=processEngine.getHistoryService();
    //        TaskService taskService=processEngine.getTaskService();
    // 查询这个流程的审批意见,用未完成任务id查整个流程已完成任务的审批意见内容
    List<Comment> commentlist = new ArrayList<Comment>();
    Task task =
        taskService
            .createTaskQuery() //
            .taskId(taskId) // 使用当前任务ID查询
            .singleResult();

    // 获取流程实例ID
    String processInstanceId = task.getProcessInstanceId();
    // 使用流程实例ID，查询历史任务，获取历史任务对应的每个任务ID
    List<HistoricTaskInstance> htiList =
        historyService
            .createHistoricTaskInstanceQuery() // 历史任务表查询
            .processInstanceId(processInstanceId) // 使用流程实例ID查询
            .list();
    // 遍历集合，获取每个任务ID
    htiList.forEach(
        hti -> {
          String htaskId = hti.getId(); // 获取历史任务批注的任务ID
          List<Comment> taskCommentList = taskService.getTaskComments(htaskId); // 基于历史已完成任务ID查询
          commentlist.addAll(taskCommentList);
        });

    StringBuffer commentStr = new StringBuffer();
    commentlist.forEach(
        comment -> {
          commentStr
              .append("{ 流程【" + comment.getProcessInstanceId() + "】")
              .append("根流程【" + comment.getRootProcessInstanceId() + "】")
              .append("用户【" + comment.getUserId() + "】")
              .append("任务【" + comment.getTaskId() + "】")
              .append("审批意见【" + comment.getFullMessage() + "】")
              .append(
                  "处理时间【"
                      + DateUtil.format(
                          comment.getTime(), Util.YEAR_MON_DAY_HOUR_MIN_SEC_CHN_FORMAT)
                      + "】}");
        });
    logger.info("获取已办任务审批意见列表【" + commentStr.toString() + "】成功");
    return "获取已办任务审批意见列表【" + commentStr.toString() + "】成功";
  }

  /**
   * 从当前节点驳回到第一个任务节点
   *
   * @param processInstanceId
   * @param assigneeLogin
   * @param comment
   * @return
   */
  @GetMapping("/rejectToFirst/{processInstanceId}/{assignee}/{comment}")
  public String rejectToFirst(
      @PathVariable("processInstanceId") String processInstanceId,
      @PathVariable("assignee") String assigneeLogin,
      @PathVariable("comment") String comment) {

    String message = comment;
    Task task =
        taskService
            .createTaskQuery()
            .taskAssignee(assigneeLogin) // 当前登录用户的id
            .processInstanceId(processInstanceId)
            .singleResult();
    ActivityInstance tree = runtimeService.getActivityInstance(processInstanceId);
    List<HistoricActivityInstance> resultList =
        historyService
            .createHistoricActivityInstanceQuery()
            .processInstanceId(processInstanceId)
            .activityType("userTask")
            .finished()
            .orderByHistoricActivityInstanceEndTime()
            .asc()
            .list();
    // 得到第一个任务节点的id
    HistoricActivityInstance historicActivityInstance = resultList.get(0);
    String toActId = historicActivityInstance.getActivityId();
    String assignee = historicActivityInstance.getAssignee();
    // 设置流程中的可变参数
    Map<String, Object> taskVariable = new HashMap<>(2);
    taskVariable.put("user", assignee);
    taskVariable.put("formName", "项目建设");
    // 这设置的是审批人及查询意见时的userId。
    identityService.setAuthenticatedUserId(assigneeLogin);
    taskService.createComment(task.getId(), processInstanceId, "驳回原因:" + message);
    runtimeService
        .createProcessInstanceModification(processInstanceId)
        //        .cancelActivityInstance(getInstanceIdForActivity(tree,
        // task.getTaskDefinitionKey()))//关闭相关任务
        .cancelAllForActivity(task.getTaskDefinitionKey()) // 关闭改节点全部相关任务
        .setAnnotation("进行了驳回到第一个任务节点操作")
        .startBeforeActivity(toActId) // 启动目标活动节点
        .setVariables(taskVariable) // 流程的可变参数赋值
        .execute();

    logger.info("驳回到第一个任务节点操作成功");
    return "驳回到第一个任务节点操作成功";
  }

  /**
   * 获取任意流程实例流程流转过程活动实例数据列表
   *
   * @param processInstanceId
   * @return
   */
  @GetMapping("/getHistoricActivityInstances/{processInstanceId}")
  public String getHistoricActivityInstances(
      @PathVariable("processInstanceId") String processInstanceId) {
    List<HistoricActivityInstance> historicActivityInstanceList =
        historyService
            .createHistoricActivityInstanceQuery()
            .processInstanceId(processInstanceId)
            .activityType("userTask")
            .finished()
            .orderByHistoricActivityInstanceEndTime()
            .asc()
            .list();

    Map<String, List<HistoricActivityInstance>> hisaiMap =
        new HashMap<String, List<HistoricActivityInstance>>();
    hisaiMap.put("data", historicActivityInstanceList);
    logger.info("获取任意流程实例流程流转过程活动实例数据列表【" + JsonUtils.toJsonString(hisaiMap) + "】成功");
    return JsonUtils.toJsonString(hisaiMap);
  }

  /**
   * 获取bpmn模型中当前activity任务节点的目标节点列表
   *
   * @param activityId
   * @return
   */
  @GetMapping("/getTargetActivityList/{processInstanceId}/{activityId}/{processDefinitionId}")
  public List<LabelValueVO> getTargetActivityList(
      @PathVariable("processInstanceId") String processInstanceId,
      @PathVariable("activityId") String activityId,
      @PathVariable("processDefinitionId") String processDefinitionId) {
    List<LabelValueVO> waitSelectNodes = new ArrayList<LabelValueVO>();

    // 获取流程定义的bpmn模型
    BpmnModelInstance bpmn = repositoryService.getBpmnModelInstance(processDefinitionId);
    ModelElementInstance domElement = bpmn.getModelElementById(activityId);
    // 转换成 flowNode流程节点 才能获取到前后元素
    FlowNode act = (FlowNode) domElement;
    List<FlowNode> succeedingList = null;
    Iterator<SequenceFlow> flowIt = null;

    if (Util.isNotEmpty(processInstanceId)) {
      long curTasksCount =
          taskService.createTaskQuery().processInstanceId(processInstanceId).count();
      if (curTasksCount > 1) {
        return waitSelectNodes;
      }
    }

    if (Util.isNotEmpty(act)) {
      Query<FlowNode> succeedings = act.getSucceedingNodes(); // 获取当前节点后续节点
      Collection<SequenceFlow> outGoingLines = act.getOutgoing(); // 获取当前节点出线
      if (!outGoingLines.isEmpty() && outGoingLines.size() > 0) {
        // 如果当前节点出线大于0条，则会有1个线变量来决定出线走哪根
        flowIt = outGoingLines.stream().iterator();
      }
      if (Util.isNotEmpty(succeedings) && succeedings.count() > 0) {
        // 此处需要约定：当节点outgoing线大于0条时，后续节点表达式变量名称需要设置相同值。
        succeedingList = succeedings.list();
      }
    }

    if (Util.isNotEmpty(succeedingList) && Util.isNotEmpty(flowIt)) {
      while (flowIt.hasNext()) {
        SequenceFlow sequenceFlow = flowIt.next();
        logger.info(sequenceFlow.getId() + "-" + sequenceFlow.getName());
        logger.info("sequenceFlow.getTextContent():" + sequenceFlow.getTextContent());
        logger.info(
            "sequenceFlow.Target:"
                + sequenceFlow.getTarget().getId()
                + "-"
                + sequenceFlow.getTarget().getName());
        logger.info(
            "sequenceFlow.Source:"
                + sequenceFlow.getSource().getId()
                + "-"
                + sequenceFlow.getSource().getName());
        succeedingList.stream()
            .forEach(
                succeeding -> {
                  if (sequenceFlow.getTarget().getId().equals(succeeding.getId())) {
                    LabelValueVO labelValueVo = new LabelValueVO();
                    labelValueVo.setLabel(succeeding.getName());
                    labelValueVo.setValue(
                        ReUtil.extractMulti(
                            ProcessConstant.NEXT_OUTGOING_REGEX,
                            sequenceFlow.getTextContent(),
                            "$2"));
                    waitSelectNodes.add(labelValueVo);
                  }
                });
      }
    }

    return waitSelectNodes;
  }

  /**
   * 获取当前流程实例运转状态流程图,含已经过的流程节点，已经过的流程线，当前流程节点，我办理的流程节点高亮标记数据
   *
   * @param processInstanceId
   * @param user
   * @return
   */
  @GetMapping("/getHighlightNode/{processInstanceId}/{processKey}/{user}")
  public ActivityHighLineVO getHighlightNode(
      @PathVariable("processInstanceId") String processInstanceId,
      @PathVariable("processKey") String processKey,
      @PathVariable("user") String user) {
    ActivityHighLineVO activityHighLineVO = new ActivityHighLineVO();
    if (Util.isNotEmpty(processInstanceId)) {
      HistoricProcessInstance hisProIns =
          historyService
              .createHistoricProcessInstanceQuery()
              .processInstanceId(processInstanceId)
              .singleResult();
      // logger.info(hisProIns.getProcessDefinitionName()+" "+hisProIns.getProcessDefinitionKey());
      // ===================已完成节点
      List<HistoricActivityInstance> finished =
          historyService
              .createHistoricActivityInstanceQuery()
              .processInstanceId(processInstanceId)
              .finished()
              .orderByHistoricActivityInstanceStartTime()
              .asc()
              .list();
      Set<String> highPoint = new HashSet<>();
      finished.forEach(t -> highPoint.add(t.getActivityId()));
      // =================待完成节点
      List<HistoricActivityInstance> unfinished =
          historyService
              .createHistoricActivityInstanceQuery()
              .processInstanceId(processInstanceId)
              .unfinished()
              .list();
      Set<String> waitingToDo = new HashSet<>();
      unfinished.forEach(t -> waitingToDo.add(t.getActivityId()));

      // =================iDo 我执行过的
      Set<String> iDo = new HashSet<>();
      // 存放 高亮 我的办理节点
      List<HistoricTaskInstance> taskInstanceList =
          historyService
              .createHistoricTaskInstanceQuery()
              .taskAssignee(user)
              .finished()
              .processInstanceId(processInstanceId)
              .list();
      taskInstanceList.forEach(a -> iDo.add(a.getTaskDefinitionKey()));

      // ===========高亮线
      Set<String> highLine2 = new HashSet<>();
      // 获取流程定义的bpmn模型
      BpmnModelInstance bpmn =
          repositoryService.getBpmnModelInstance(hisProIns.getProcessDefinitionId());
      // 已完成任务列表 可直接使用上面写过的
      List<HistoricActivityInstance> finishedList =
          historyService
              .createHistoricActivityInstanceQuery()
              .processInstanceId(processInstanceId)
              .finished()
              .orderByHistoricActivityInstanceStartTime()
              .asc()
              .list();
      int finishedNum = finishedList.size();
      // 循环 已完成的节点
      for (int i = 0; i < finishedNum; i++) {
        HistoricActivityInstance finItem = finishedList.get(i);
        // 根据 任务key 获取 bpmn元素
        ModelElementInstance domElement = bpmn.getModelElementById(finItem.getActivityId());
        // 转换成 flowNode流程节点 才能获取到 输出线 和输入线
        FlowNode act = (FlowNode) domElement;

        if (Util.isNotEmpty(act)) {
          Collection<SequenceFlow> outgoing = act.getOutgoing();
          // 循环当前节点的 向下分支
          outgoing.forEach(
              v -> {
                String tarId = v.getTarget().getId();
                // 已完成
                for (int j = 0; j < finishedNum; j++) {
                  // 循环历史完成节点 和当前完成节点的向下分支比对
                  // 如果当前完成任务 的结束时间 等于 下个任务的开始时间
                  HistoricActivityInstance setpFinish = finishedList.get(j);
                  String finxId = setpFinish.getActivityId();
                  if (tarId.equals(finxId)) {
                    if (finItem.getEndTime().equals(setpFinish.getStartTime())) {
                      highLine2.add(v.getId());
                    }
                  }
                }
                // 待完成
                for (int j = 0; j < unfinished.size(); j++) {
                  // 循环待节点 和当前完成节点的向下分支比对
                  HistoricActivityInstance setpUnFinish = unfinished.get(j);
                  String finxId = setpUnFinish.getActivityId();
                  if (tarId.equals(finxId)) {
                    if (finItem.getEndTime().equals(setpUnFinish.getStartTime())) {
                      highLine2.add(v.getId());
                    }
                  }
                }
              });
        }
      }

      String result =
          new BufferedReader(
                  new InputStreamReader(
                      repositoryService.getProcessModel(hisProIns.getProcessDefinitionId())))
              .lines()
              .collect(Collectors.joining(System.lineSeparator()));

      // 返回结果
      activityHighLineVO.setHighPoint(highPoint);
      activityHighLineVO.setHighLine(highLine2);
      activityHighLineVO.setWaitingToDo(waitingToDo);
      activityHighLineVO.setiDo(iDo);
      activityHighLineVO.setBpmn(result);
    } else {
      String result =
          new BufferedReader(
                  new InputStreamReader(
                      repositoryService.getProcessModel(
                          repositoryService
                              .createProcessDefinitionQuery()
                              .processDefinitionKey(processKey)
                              .latestVersion()
                              .singleResult()
                              .getId())))
              .lines()
              .collect(Collectors.joining(System.lineSeparator()));

      // 返回结果
      activityHighLineVO.setBpmn(result);
    }
    return activityHighLineVO;
  }

  private String getInstanceIdForActivity(ActivityInstance activityInstance, String activityId) {
    ActivityInstance instance = getChildInstanceForActivity(activityInstance, activityId);
    if (instance != null) {
      return instance.getId();
    }
    return null;
  }

  private ActivityInstance getChildInstanceForActivity(
      ActivityInstance activityInstance, String activityId) {
    if (activityId.equals(activityInstance.getActivityId())) {
      return activityInstance;
    }
    for (ActivityInstance childInstance : activityInstance.getChildActivityInstances()) {
      ActivityInstance instance = getChildInstanceForActivity(childInstance, activityId);
      if (instance != null) {
        return instance;
      }
    }
    return null;
  }

  /** 设置global/loacal变量 */
  public void setVariables() {
    Map params = new HashMap();
    params.put("days", 3);
    params.put("type", "请假");
    // 设置global变量
    runtimeService.setVariable("excutionId", "key", "value");
    runtimeService.setVariables("excutionId", params);
    // 设置local变量
    runtimeService.setVariableLocal("excutionId", "key", "value");
    runtimeService.setVariablesLocal("excutionId", params);
  }
}
