package com.micro.cloud.modules.process.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.micro.cloud.api.CommonPage;
import com.micro.cloud.api.CommonResult;
import com.micro.cloud.common.core.chain.Chain;
import com.micro.cloud.constant.SysErrorCodeConstants;
import com.micro.cloud.exception.ChainException;
import com.micro.cloud.exception.ProcessException;
import com.micro.cloud.modules.file.convert.FileCommonConvert;
import com.micro.cloud.modules.file.vo.AttachmentVO;
import com.micro.cloud.modules.form.param.FormDataParam;
import com.micro.cloud.modules.form.result.TargetDataResult;
import com.micro.cloud.modules.form.service.WorkflowFormService;
import com.micro.cloud.modules.process.cache.ProcessRepositoryCache;
import com.micro.cloud.modules.process.constant.ProcessConstant;
import com.micro.cloud.modules.process.convert.ProcessInstanceConvert;
import com.micro.cloud.modules.process.dal.dataobject.ProcessAssociateSetting;
import com.micro.cloud.modules.process.dal.dataobject.ProcessAssociated;
import com.micro.cloud.modules.process.dal.mapper.ProcessAssociateSettingMapper;
import com.micro.cloud.modules.process.dal.mapper.ProcessAssociatedMapper;
import com.micro.cloud.modules.process.param.CommentParam;
import com.micro.cloud.modules.process.param.CommitTaskParam;
import com.micro.cloud.modules.process.param.EditNodeOperatorParam;
import com.micro.cloud.modules.process.param.ProcessDetailParam;
import com.micro.cloud.modules.process.param.ProcessPageParam;
import com.micro.cloud.modules.process.param.QueryTargetParam;
import com.micro.cloud.modules.process.param.ReachableProcessParam;
import com.micro.cloud.modules.process.param.RejectToFirstNodeRequestParam;
import com.micro.cloud.modules.process.param.SearchProcessParam;
import com.micro.cloud.modules.process.service.ProcessService;
import com.micro.cloud.modules.process.service.UserService;
import com.micro.cloud.modules.process.vo.ActivityHighLineVO;
import com.micro.cloud.modules.process.vo.HistoricNode;
import com.micro.cloud.modules.process.vo.LabelValueVO;
import com.micro.cloud.modules.process.vo.NodeInfoVo;
import com.micro.cloud.modules.process.vo.ProcessCommentVO;
import com.micro.cloud.modules.process.vo.ProcessDataVO;
import com.micro.cloud.modules.process.vo.ProcessInstanceVO;
import com.micro.cloud.modules.process.vo.ReachableProcessVO;
import com.micro.cloud.modules.process.vo.TaskVO;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import com.micro.cloud.snowflake.sequence.SequenceService;
import com.micro.cloud.util.Util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.task.Attachment;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.Query;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.LoopCharacteristics;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 〈流程引擎服务实现〉
 *
 * @author roy
 * @create 2021/12/2
 * @since 1.0.0
 */
@Service
public class ProcessServiceImpl implements ProcessService {

  private final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);

  @Autowired private RepositoryService repositoryService;
  @Autowired private RuntimeService runtimeService;
  @Autowired private TaskService taskService;
  @Autowired private HistoryService historyService;
  @Autowired private IdentityService identityService;
  @Autowired private SequenceService sequenceService;
  @Autowired private WorkflowFormService workflowFormService;
  @Autowired private UserService userService;
  @Autowired private ProcessAssociateSettingMapper associateSettingMapper;
  @Autowired private ProcessAssociatedMapper associatedMapper;

  private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
  /** 创建线程池 调整队列数 拒绝服务 */
  private static final ThreadFactory NAMED_THREAD_FACTORY =
      new ThreadFactoryBuilder().setNameFormat("process-pool-%d").build();

  private static final ExecutorService EXECUTOR =
      new ThreadPoolExecutor(
          CORE_POOL_SIZE,
          CORE_POOL_SIZE * 2,
          10L,
          TimeUnit.SECONDS,
          new LinkedBlockingQueue<Runnable>(20),
          NAMED_THREAD_FACTORY,
          new ThreadPoolExecutor.AbortPolicy());

  /**
   * 流程发布
   *
   * @param bpmnFile bpmn流程配置文件
   * @return 流程发布id
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String deploy(String bpmnFile) {
    // 部署bpmn文件
    Deployment deploy =
        repositoryService.createDeployment().addClasspathResource("BPMN/" + bpmnFile).deploy();
    logger.info("######### deploy name:{}", deploy.getName());
    // 流程发布后需重新解析bpmn模型，将新的节点顺序结构维护至节点缓存
    String processKey =
        repositoryService
            .createProcessDefinitionQuery()
            .deploymentId(deploy.getId())
            .singleResult()
            .getKey();
    FutureTask<Void> analyseProcessStepTask =
        new FutureTask<>(() -> analyseProcessStep(processKey));
    EXECUTOR.execute(analyseProcessStepTask);
    return deploy.getId();
  }

  /**
   * 解析流程节点信息
   *
   * @param processKey 流程processKey
   */
  private Void analyseProcessStep(String processKey) {
    // 移除原有流程节点步骤信息
    if (ProcessRepositoryCache.PROCESS_STEP_CACHE.containsKey(processKey)) {
      ProcessRepositoryCache.PROCESS_STEP_CACHE.remove(processKey);
    }
    List<Element> userTaskElements = this.getUserTaskElements(processKey);
    AtomicInteger atomicStep = new AtomicInteger(1);
    Map<String, Integer> stepMap = MapUtil.newHashMap(16);
    userTaskElements.stream()
        .filter(Objects::nonNull)
        .forEach(
            element -> {
              stepMap.put(
                  element.getAttribute(ProcessConstant.CURNODE_ATTRI_ID_LABEL),
                  atomicStep.getAndIncrement());
            });
    ProcessRepositoryCache.PROCESS_STEP_CACHE.put(processKey, stepMap);
    return null;
  }

  /**
   * 流程展示列表
   *
   * @param param 可发起流程请求参数
   * @return 可发起流程列表
   */
  @Override
  public List<ReachableProcessVO> board(ReachableProcessParam param) {
    List<ProcessDefinition> processDefinitionList =
        repositoryService
            .createProcessDefinitionQuery()
            // .processDefinitionNameLike(param.getProcessName())
            .latestVersion()
            .list();
    //    logger.info("####### processDefinitionList:{}", processDefinitionList);
    return ProcessInstanceConvert.INSTANCE.convertReachable(processDefinitionList);
  }

  /**
   * 启动流程实例
   *
   * @param processKey 流程实例名称
   * @return 流程实例id
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public ProcessInstanceVO start(
      String processKey, Map<String, Object> paramMap, String userId, String userName) {
    // 目前流程实例启动与表单提交划分为两个节点进行操作，为保证流程实例与表单数据关联关系，所以在流程实例启动时为业务表单数据生成主键id
    String businessKey = sequenceService.nextStringValue(null);
    // 根据processKey获取流程相关资源
    ProcessDefinition processDefinition =
        repositoryService
            .createProcessDefinitionQuery()
            .processDefinitionKey(processKey)
            .latestVersion()
            .singleResult();
    // 解析创建节点,设置节点操作者
    Map<String, Object> param = new HashMap<>();
    NodeInfoVo nodeInfoVo = isMultiSignNode(null, processDefinition.getId(), null, false);
    logger.info("###### nodeInfoVo:{}", nodeInfoVo);
    // 流程创建人 -> 当前登录人员
    if (nodeInfoVo.isMultiIns()) {
      // 多实例节点
      param.put(nodeInfoVo.getParamKey(), Collections.singletonList(userId));
    } else {
      // 单实例节点
      param.put(nodeInfoVo.getParamKey(), userId);
    }
    logger.info("###### param:{}", param);
    ProcessInstance processInstance =
        runtimeService.startProcessInstanceByKey(processKey, businessKey, param);
    logger.info(
        "instanceId:{} | definitionId:{} | businessKey:{}",
        processInstance.getProcessInstanceId(),
        processInstance.getProcessDefinitionId(),
        processInstance.getBusinessKey());
    // 获取流程task
    Task task =
        taskService
            .createTaskQuery()
            .processInstanceId(processInstance.getProcessInstanceId())
            .singleResult();
    // 设置流程变量 processDefinitionKey、项目名称、创建人
    String proDefName =
        repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId()).getName();
    paramMap.put(ProcessConstant.PROCESS_DEFINITION_KEY, proDefName);
    paramMap.put(ProcessConstant.CREATOR, userName);
    //    logger.info("##### paramMap:{}", paramMap);
    runtimeService.setVariables(task.getExecutionId(), paramMap);
    return ProcessInstanceConvert.INSTANCE.convertVO(processInstance, task);
  }

  /**
   * 判断某节点是否为多实例会签节点，并动态返回多实例节点或者普通任务节点的处理人变量名称和是否多实例标记
   *
   * @param processDefinitionId 流程定义id
   * @param activityId 流程节点id
   * @param isFallBack 是否退回
   * @return 动态返回多实例节点或者普通任务节点的处理人变量名称和是否多实例标记
   */
  private NodeInfoVo isMultiSignNode(
      Task task, String processDefinitionId, String activityId, Boolean isFallBack) {
    // 获取流程定义的bpmn模型
    BpmnModelInstance bpmn = repositoryService.getBpmnModelInstance(processDefinitionId);
    String bpmnStr =
        new BufferedReader(
                new InputStreamReader(repositoryService.getProcessModel(processDefinitionId)))
            .lines()
            .collect(Collectors.joining(System.lineSeparator()));
    Document bpmnDocument = XmlUtil.parseXml(bpmnStr);
    Element elements =
        XmlUtil.getElementByXPath(ProcessConstant.BPMN_GOALELEMENT_XPATH, bpmnDocument);
    // 会签节点情况处理
    Element multiElement;
    // 普通节点情况处理
    Element simpleElement = null;
    NodeInfoVo nodeInfoVo = new NodeInfoVo();
    AtomicReference<String> atomicActivityId = new AtomicReference<>(activityId);
    // activityId为空则默认获取第一节点activityId
    if (Util.isEmpty(activityId)) {
      // 获取开始节点所在元素
      List<Element> startEventElements =
          XmlUtil.getElements(elements, ProcessConstant.BPMN_START_TAGNAME);
      if (CollectionUtil.isNotEmpty(startEventElements)) {
        String startEventId =
            startEventElements.get(0).getAttribute(ProcessConstant.CURNODE_ATTRI_ID_LABEL);
        logger.info("####### startEventId:{}", startEventId);
        // 转换成 flowNode流程节点后获取前后元素
        FlowNode flowNode = bpmn.getModelElementById(startEventId);
        SequenceFlow sequenceFlow = flowNode.getOutgoing().stream().findFirst().get();
        // 获取第一节点id
        String startTaskId = sequenceFlow.getTarget().getId();
        atomicActivityId.set(startTaskId);
      }
    }
    logger.info("##### targetId:{} ", atomicActivityId.get());
    // 根据第一节点是否包含 bpmn:multiInstanceLoopCharacteristics元素判断是否为会签节点
    List<Element> userTaskElements =
        XmlUtil.getElements(elements, ProcessConstant.BPMN_USERTASK_TAGNAME);
    multiElement =
        userTaskElements.stream()
            .filter(Objects::nonNull)
            .filter(
                element ->
                    element
                        .getAttribute(ProcessConstant.CURNODE_ATTRI_ID_LABEL)
                        .equals(atomicActivityId.get()))
            .filter(
                element ->
                    element
                            .getElementsByTagName(
                                ProcessConstant.BPMN_MULTIINSTANCELOOPCHARACTERISTICS_TAGNAME)
                            .getLength()
                        > 0)
            .findAny()
            .orElse(null);
    // 多实例为空则按照单实例节点解析
    if (Util.isEmpty(multiElement)) {
      simpleElement =
          userTaskElements.stream()
              .filter(Objects::nonNull)
              .filter(
                  element ->
                      element
                          .getAttribute(ProcessConstant.CURNODE_ATTRI_ID_LABEL)
                          .equals(atomicActivityId.get()))
              .findAny()
              .orElse(null);
    }
    // 多实例节点退回场景
    if (isFallBack && ObjectUtils.isNotEmpty(multiElement)) {
      logger.info("###### 多实例节点退回场景 ######");
      nodeInfoVo.setMultiIns(true);
      nodeInfoVo.setParamKey(
          ReUtil.extractMulti(
              ProcessConstant.VAREXTRACTMULTI_REGEX,
              multiElement.getAttribute(ProcessConstant.BPMN_CAMUNDA_ASSIGNEE_TAGNAME),
              "$1"));
    }
    // 多实例节点非退回场景
    if (!isFallBack && ObjectUtils.isNotEmpty(multiElement)) {
      logger.info("###### 多实例节点非退回场景 ######");
      nodeInfoVo.setMultiIns(true);
      NodeList nodeList =
          multiElement.getElementsByTagName(
              ProcessConstant.BPMN_MULTIINSTANCELOOPCHARACTERISTICS_TAGNAME);
      Node item =
          nodeList
              .item(0)
              .getAttributes()
              .getNamedItem(ProcessConstant.BPMN_CAMUNDA_COLLECTION_TAGNAME);
      //      logger.info("###### node value:{}", item.getNodeValue());
      nodeInfoVo.setParamKey(item.getNodeValue());
    }
    // 单实例情况下无需区分是否为退回场景
    if (ObjectUtils.isNotEmpty(simpleElement)) {
      logger.info("###### 单实例场景 ######");
      nodeInfoVo.setMultiIns(false);
      nodeInfoVo.setParamKey(
          ReUtil.extractMulti(
              ProcessConstant.VAREXTRACTMULTI_REGEX,
              simpleElement.getAttribute(ProcessConstant.BPMN_CAMUNDA_ASSIGNEE_TAGNAME),
              "$1"));
    }
    if (ObjectUtils.isEmpty(simpleElement) && ObjectUtils.isEmpty(multiElement)) {
      logger.info("####### 结束节点 ######");
      // 视为结束节点 根据当前activityId
      String taskDefinitionKey = task.getTaskDefinitionKey();
      // 获取endEvent所有incoming
      List<Element> endEventElements =
          XmlUtil.getElements(elements, ProcessConstant.BPMN_END_TAGNAME);
      // 获取当前节点outgoing
      Element currentElement =
          userTaskElements.stream()
              .filter(Objects::nonNull)
              .filter(
                  element ->
                      element
                          .getAttribute(ProcessConstant.CURNODE_ATTRI_ID_LABEL)
                          .equals(taskDefinitionKey))
              .findAny()
              .orElse(null);
      NodeList endChildren = endEventElements.get(0).getChildNodes();
      Set<String> endIncomingSet = new HashSet<>();
      String sequenceFlowName = "";
      for (int i = 0; i < endChildren.getLength(); i++) {
        String endIncoming = endChildren.item(i).getTextContent();
        endIncomingSet.add(endIncoming);
      }
      NodeList currentChildren =
          currentElement.getElementsByTagName(ProcessConstant.BPMN_OUT_GOING_TAGNAME);
      for (int i = 0; i < currentChildren.getLength(); i++) {
        if (endIncomingSet.contains(currentChildren.item(i).getTextContent())) {
          sequenceFlowName = currentChildren.item(i).getTextContent();
        }
      }
      logger.info("####### sequenceFlowName:{}", sequenceFlowName);
      // 获取所有sequenceFlow
      String finalSequenceFlowName = sequenceFlowName;
      Element targetElement =
          XmlUtil.getElements(elements, ProcessConstant.BPMN_SEQUENCEFLOW_TAGNAME).stream()
              .filter(
                  element ->
                      StringUtils.equals(
                          element.getAttribute(ProcessConstant.CURNODE_ATTRI_ID_LABEL),
                          finalSequenceFlowName))
              .findFirst()
              .orElse(null);
      logger.info("##### targetElement:{}", targetElement);
      NodeList conditionList =
          targetElement.getElementsByTagName(ProcessConstant.BPMN_CONDITIONEXPRESSION_TAGNAME);
      if (conditionList.getLength() > 0) {
        String expression =
            targetElement
                .getElementsByTagName(ProcessConstant.BPMN_CONDITIONEXPRESSION_TAGNAME)
                .item(0)
                .getTextContent();
        String expressionValue =
            ReUtil.extractMulti(ProcessConstant.VAREXTRACTMULTI_REGEX, expression, "$1");
        logger.info("##### expression:{}", expressionValue);
        nodeInfoVo.setMultiIns(false);
        nodeInfoVo.setParamKey(expressionValue);
      }
    }
    logger.info("##### nodeInfoVo:{}", nodeInfoVo);
    return nodeInfoVo;
  }

  /**
   * 开启流程实例并提交至下一步(包含表单参数)
   *
   * @param param 开启流程实例并提交至下一步请求参数
   * @param creator 创建人
   * @return 流程实例信息
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public Boolean commit2(CommitTaskParam param, String creator) {
    // 更新表单
    if (ObjectUtils.isNotEmpty(param.getFormDataParam().getFormData())) {
      workflowFormService.updateFormData(param.getFormDataParam());
    }
    // 判断processInstanceId是否存在
    if (StringUtils.isBlank(param.getProcessInstanceId())) {
      Map<String, Object> processParam = new HashMap<>(16);
      // 流程创建人 -> 当前登录人员
      processParam.put("creator", creator);
      ProcessInstance processInstance =
          runtimeService.startProcessInstanceByKey(
              param.getFormDataParam().getProcessKey(),
              String.valueOf(param.getFormDataParam().getFormData().get("business_id")),
              processParam);
      logger.info(
          "instanceId:{} | definitionId:{} | businessKey:{}",
          processInstance.getProcessInstanceId(),
          processInstance.getProcessDefinitionId(),
          processInstance.getBusinessKey());
      // 设置processInstanceId
      param.setProcessInstanceId(processInstance.getProcessInstanceId());
    }
    // 提交流程
    return commit(param, creator);
  }

  /**
   * 查看流程详情
   *
   * @param param 流程详情请求参数
   * @param userId 用户id
   * @return 表单详情数据
   */
  @Override
  public ProcessDataVO detail(ProcessDetailParam param, String userId)
      throws ExecutionException, InterruptedException {
    // 获取表单数据详情
    FutureTask<Map<String, Object>> formDataTask =
        new FutureTask<>(
            () -> {
              FormDataParam formDataParam = new FormDataParam();
              formDataParam.setProcessKey(param.getProcessKey());
              HashMap<String, Object> formData = MapUtil.newHashMap(16);
              formData.put("business_id", param.getBusinessId());
              formDataParam.setFormData(formData);
              return workflowFormService.getFormData(formDataParam);
            });
    EXECUTOR.execute(formDataTask);
    // 获取流程签字意见
    FutureTask<List<ProcessCommentVO>> commentsTask =
        new FutureTask<>(
            () -> {
              CommentParam commentParam = new CommentParam();
              commentParam.setProcessInstanceId(param.getProcessInstanceId());
              return comments(commentParam);
            });
    EXECUTOR.execute(commentsTask);
    // 获取流程附件
    FutureTask<List<AttachmentVO>> attachmentTask =
        new FutureTask<>(() -> attachments(param.getProcessInstanceId()));
    EXECUTOR.execute(attachmentTask);
    // 获取被关联流程数据
    FutureTask<List<ProcessAssociated>> associateProcessesTask =
        new FutureTask<>(() -> associateProcesses(param.getProcessInstanceId()));
    EXECUTOR.execute(associateProcessesTask);
    // 获取当前节点节点信息
    FutureTask<HistoricTaskInstance> task =
        new FutureTask<HistoricTaskInstance>(() -> getTaskByActivityId(param, userId));
    EXECUTOR.submit(task);
    // 获取当前节点所处步骤
    FutureTask<Integer> stepTask =
        new FutureTask<>(() -> getActivityStep(param.getProcessKey(), param.getActivityId()));
    EXECUTOR.execute(stepTask);
    return ProcessInstanceConvert.INSTANCE.convertDetails(
        formDataTask, attachmentTask, commentsTask, associateProcessesTask, stepTask, task);
  }

  /**
   * 获取流程任务节点信息
   *
   * @param param 请求参数
   * @param userId 用户id
   * @return 流程节点任务
   */
  private HistoricTaskInstance getTaskByActivityId(ProcessDetailParam param, String userId) {
    List<HistoricTaskInstance> historicTaskInstanceList =
        historyService
            .createHistoricTaskInstanceQuery()
            .processInstanceId(param.getProcessInstanceId())
            .taskAssignee(userId)
            .taskDefinitionKey(param.getActivityId())
            .list();
    logger.info("###### 节点任务信息:{}", historicTaskInstanceList);
    return CollectionUtil.isEmpty(historicTaskInstanceList)
        ? null
        : historicTaskInstanceList.get(0);
  }

  /**
   * 获取被关联流程数据
   *
   * @param processInstanceId 流程实例id
   * @return 被关联数据
   */
  private List<ProcessAssociated> associateProcesses(String processInstanceId) {
    return associatedMapper.selectList("process_instance_id", processInstanceId);
  }

  @Override
  public Integer getActivityStep(String processKey, String activityId) {
    // 考虑获取当前节点步骤查询频次过高，启动服务时预先将流程节点对应步骤缓存至进程中，如果缓存未命中则进行解析后再放入缓存
    Integer step = ProcessRepositoryCache.PROCESS_STEP_CACHE.get(processKey).get(activityId);
    if (Objects.nonNull(step)) {
      return step;
    }
    AtomicInteger atomicStep = new AtomicInteger(0);
    List<Element> userTaskElements = getUserTaskElements(processKey);
    // 过滤后根据节点所在位置确定该节点所属步骤
    userTaskElements.stream()
        .filter(
            element -> {
              atomicStep.getAndIncrement();
              return StringUtils.equals(
                  activityId, element.getAttribute(ProcessConstant.CURNODE_ATTRI_ID_LABEL));
            })
        .findFirst();
    logger.info("##### step:{}", atomicStep.get());
    return atomicStep.get();
  }

  @Override
  public List<Element> getUserTaskElements(String processKey) {
    // 根据processKey获取流程相关资源
    ProcessDefinition processDefinition =
        repositoryService
            .createProcessDefinitionQuery()
            .processDefinitionKey(processKey)
            .latestVersion()
            .singleResult();
    // 获取流程定义的bpmn模型
    String bpmnStr =
        new BufferedReader(
                new InputStreamReader(repositoryService.getProcessModel(processDefinition.getId())))
            .lines()
            .collect(Collectors.joining(System.lineSeparator()));
    Document document = XmlUtil.parseXml(bpmnStr);
    // 获取所有任务节点
    Element elements = XmlUtil.getElementByXPath(ProcessConstant.BPMN_GOALELEMENT_XPATH, document);
    return XmlUtil.getElements(elements, ProcessConstant.BPMN_USERTASK_TAGNAME);
  }

  /**
   * 提交流程
   *
   * @param params 提交流程
   * @param userId 用户id
   * @return taskId
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public Boolean commit(CommitTaskParam params, String userId) {
    // 更新表单数据
    if (ObjectUtils.isNotEmpty(params.getFormDataParam().getFormData())) {
      workflowFormService.updateFormData(params.getFormDataParam());
    }
    /**
     * 会签审批时需要使用以下全局变量: nrOfInstance 实例的数目 nrOfCompletedInstances 完成实例的数目 nrOfActiveInstance
     * 未完成实例的数目 loopCounter 循环计数器，办理人在列表中的索引。
     */
    List<Task> taskList =
        taskService
            .createTaskQuery()
            .taskAssignee(userId)
            .processInstanceId(params.getProcessInstanceId())
            .list();
    Task task = CollUtil.isNotEmpty(taskList) ? taskList.get(0) : null;
    /** 此处逻辑为获取下一节点处理人流程变量名及出线变量--begin */
    // 当前节点出线表达式变量名称
    AtomicReference<String> conditionExpressionVar = new AtomicReference<>("");
    // 获取流程定义的bpmn模型
    BpmnModelInstance bpmn = repositoryService.getBpmnModelInstance(task.getProcessDefinitionId());
    ModelElementInstance domElement = bpmn.getModelElementById(task.getTaskDefinitionKey());
    // 转换成 flowNode流程节点 才能获取到前后元素
    FlowNode act = (FlowNode) domElement;
    SequenceFlow outGoing = null;
    if (Util.isNotEmpty(act)) {
      // 获取当前节点出线
      Collection<SequenceFlow> outGoingLines = act.getOutgoing();
      if (!outGoingLines.isEmpty() && outGoingLines.size() > 1) {
        // 如果当前节点出线大于1条，则会有1个线变量来决定出线走哪根，因此获取该线变量名称时取第一条即可
        outGoing = outGoingLines.stream().findFirst().get();
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
    NodeInfoVo nodeInfoVo =
        isMultiSignNode(task, task.getProcessDefinitionId(), params.getNextActivityId(), false);
    /** 此处逻辑为获取下一节点处理人流程变量名及出线变量--end */
    // 设置签字意见
    identityService.setAuthenticatedUserId(userId);
    if (Util.isNotEmpty(params.getComment())) {
      taskService.createComment(task.getId(), params.getProcessInstanceId(), params.getComment());
    }
    // comment意思是完成时的审批意见，可在Act_Hi_Comment里的massge查询到。
    Map<String, Object> param = new HashMap<>();
    // 当outGoing出线大于1条时需要设置出线表达式变量的值
    if (Util.isNotEmpty(params.getLineValue()) && Util.isNotEmpty(conditionExpressionVar.get())) {
      param.put(conditionExpressionVar.get(), params.getLineValue());
    }
    logger.info("###### param:{}", param);
    // 此处设置target目标节点流程变量
    if (CollectionUtil.isNotEmpty(params.getNextNodeOperators()) && Objects.nonNull(param)) {
      // 下一个节点为会签多实例审批时
      if (Util.isNotEmpty(nodeInfoVo) && nodeInfoVo.isMultiIns()) {
        param.put(nodeInfoVo.getParamKey(), params.getNextNodeOperators());
      } else { // 普通流转
        // 当前节点为多实例会签节点审批
        param.put(nodeInfoVo.getParamKey(), params.getNextNodeOperators().get(0));
      }
      // 完成任务，交由下一节点审批
      logger.info("###### param:{}", param);
      taskService.complete(task.getId(), param);
    }
    if (CollectionUtil.isEmpty(params.getNextNodeOperators()) && Objects.isNull(param)) {
      // 完成任务，交由下一节点审批
      taskService.complete(task.getId());
    }
    if (CollectionUtil.isEmpty(params.getNextNodeOperators()) && Objects.nonNull(param)) {
      taskService.complete(task.getId(), param);
    }
    return true;
  }

  /**
   * 节点加签
   *
   * @param param 加签所需参数
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public Boolean appendSign(EditNodeOperatorParam param) {
    // 获取当前流程实例下所有任务的待办人员
    List<Task> taskList =
        taskService.createTaskQuery().processInstanceId(param.getProcessInstanceId()).list();
    if (CollectionUtil.isNotEmpty(taskList)) {}
    Optional.ofNullable(taskList)
        .ifPresent(
            tasks -> {
              Set<String> assigneeSet =
                  tasks.stream().map(Task::getAssignee).collect(Collectors.toSet());
              param.getOperator().stream()
                  .forEach(
                      operator -> {
                        if (assigneeSet.contains(operator)) {
                          throw new ProcessException(
                              SysErrorCodeConstants.PROCESS_APPEND_OPERATOR_HAS_TASK.getMessage()
                                  + operator);
                        }
                      });
            });
    Task task =
        taskService
            .createTaskQuery()
            // 当前登录用户的id
            .taskAssignee(param.getAssignee())
            .processInstanceId(param.getProcessInstanceId())
            .singleResult();
    String processDefinitionId = task.getProcessDefinitionId();
    // 获取当前节点activityId,根据activityId获取bpmn节点变量属性名称，进行赋值
    NodeInfoVo nodeInfo = isMultiSignNode(task, processDefinitionId, param.getActivityId(), true);
    // 加签
    Optional.ofNullable(param.getOperator())
        .ifPresent(
            operators -> {
              operators.stream()
                  .filter(Objects::nonNull)
                  .forEach(
                      operator -> {
                        logger.info("###### operator:{}", operator);
                        runtimeService
                            .createProcessInstanceModification(param.getProcessInstanceId())
                            // 会签节点的activityId
                            .startBeforeActivity(param.getActivityId())
                            .setVariable(nodeInfo.getParamKey(), operator)
                            .execute();
                      });
            });
    return true;
  }

  /**
   * 节点减签
   *
   * @param param 减签所需参数
   * @return 是否成功
   */
  @Override
  public Boolean subtractSign(EditNodeOperatorParam param) {
    Task task =
        taskService
            .createTaskQuery()
            .taskAssignee(param.getAssignee())
            .processInstanceId(param.getProcessInstanceId())
            .singleResult();
    String processDefinitionId = task.getProcessDefinitionId();
    NodeInfoVo nodeInfo = isMultiSignNode(task, processDefinitionId, param.getActivityId(), false);
    ActivityInstance[] activityInstances =
        runtimeService
            .getActivityInstance(param.getProcessInstanceId())
            .getActivityInstances(param.getActivityId());
    for (ActivityInstance activityInstance : activityInstances) {
      if (task.getExecutionId().equals(activityInstance.getExecutionIds()[0])) {
        runtimeService.removeVariable(task.getExecutionId(), nodeInfo.getParamKey());
        // 会签执行流实例的activityInstance
        runtimeService
            .createProcessInstanceModification(param.getProcessInstanceId())
            .cancelActivityInstance(activityInstance.getId())
            .execute();
      }
    }
    taskService.deleteTask(task.getId());
    logger.info("流程【" + param.getProcessInstanceId() + "】会签减签【" + param.getActivityId() + "】成功");
    return true;
  }

  /**
   * 代办列表
   *
   * @param param 待办列表请求参数
   * @return 待办前端展示列表
   */
  @Override
  public CommonPage<TaskVO> todoTask(ProcessPageParam param) {
    List<Task> list = null;
    Object listRes = null;
    /** 开启查询条件链式调用---begin */
    try {
      listRes =
          Chain.start()
              .must(x -> Util.isNotEmpty(param))
              .must(x -> Util.isNotEmpty(param.getProcessDefinitionName()))
              .operation(
                  x ->
                      taskService
                          .createTaskQuery()
                          .taskAssignee(param.getAssignee())
                          .orderByTaskCreateTime()
                          .desc()
                          .active()
                          .processVariableValueLike(
                              ProcessConstant.PROCESS_DEFINITION_KEY,
                              "%" + Util.escapeStr(param.getProcessDefinitionName()) + "%")
                          .listPage(param.getOffSet(), param.getPageSize()))
              .end(x -> x.get(Chain.OPERATION));
    } catch (ChainException e) {
    }

    try {
      Object res1 = listRes;
      listRes =
          Chain.start()
              .must(x -> Util.isEmpty(res1))
              .must(x -> Util.isNotEmpty(param))
              .must(x -> Util.isEmpty(param.getProcessDefinitionName()))
              .operation(
                  x ->
                      taskService
                          .createTaskQuery()
                          .taskAssignee(param.getAssignee())
                          .orderByTaskCreateTime()
                          .desc()
                          .active()
                          .listPage(param.getOffSet(), param.getPageSize()))
              .end(x -> x.get(Chain.OPERATION));
    } catch (ChainException e) {
    }

    try {
      Object res2 = listRes;
      list =
          (List<Task>)
              Chain.start()
                  .must(x -> Util.isNotEmpty(res2))
                  .operation(x -> res2)
                  .end(x -> x.get(Chain.OPERATION));
    } catch (ChainException e) {
    }

    long count = 0L;
    try {
      count =
          (long)
              Chain.start()
                  .must(x -> Util.isNotEmpty(param))
                  .must(x -> Util.isNotEmpty(param.getProcessDefinitionName()))
                  .operation(
                      x ->
                          taskService
                              .createTaskQuery()
                              .taskAssignee(param.getAssignee())
                              .active()
                              .processVariableValueLike(
                                  ProcessConstant.PROCESS_DEFINITION_KEY,
                                  "%" + Util.escapeStr(param.getProcessDefinitionName()) + "%")
                              .count())
                  .end(x -> x.get(Chain.OPERATION));
    } catch (ChainException e) {
    }

    try {
      long resCount = count;
      count =
          (long)
              Chain.start()
                  .must(x -> resCount == 0)
                  .must(x -> Util.isNotEmpty(param))
                  .must(x -> Util.isEmpty(param.getProcessDefinitionName()))
                  .operation(
                      x ->
                          taskService
                              .createTaskQuery()
                              .taskAssignee(param.getAssignee())
                              .active()
                              .count())
                  .end(x -> x.get(Chain.OPERATION));
    } catch (ChainException e) {
    }
    /** 查询条件链式调用结束---end */
    List<TaskVO> result = ProcessInstanceConvert.INSTANCE.convertTodo(list);
    // 获取processKey, activityId, businessId
    if (CollectionUtil.isNotEmpty(result)) {
      String[] processDefinitionIds =
          result.stream()
              .map(TaskVO::getProcessDefinitionId)
              .filter(Objects::nonNull)
              .collect(Collectors.toSet())
              .stream()
              .toArray(String[]::new);
      List<ProcessDefinition> processDefinitions =
          repositoryService
              .createProcessDefinitionQuery()
              .processDefinitionIdIn(processDefinitionIds)
              .list();
      Map<String, String> processKeyMap =
          processDefinitions.stream()
              .collect(Collectors.toMap(ProcessDefinition::getId, ProcessDefinition::getKey));
      Map<String, String> processNameMap =
          processDefinitions.stream()
              .collect(Collectors.toMap(ProcessDefinition::getId, ProcessDefinition::getName));
      Set<String> processInstanceIds =
          list.stream().map(Task::getProcessInstanceId).collect(Collectors.toSet());
      Map<String, String> businessKeyMap =
          runtimeService
              .createProcessInstanceQuery()
              .processInstanceIds(processInstanceIds)
              .list()
              .stream()
              .collect(
                  Collectors.toMap(
                      ProcessInstance::getProcessInstanceId, ProcessInstance::getBusinessKey));
      List<VariableInstance> variableList =
          runtimeService
              .createVariableInstanceQuery()
              .processInstanceIdIn(processInstanceIds.toArray(new String[0]))
              .variableNameIn("projectName", "creatorUserName")
              .list();
      Map<String, List<VariableInstance>> variables =
          variableList.stream()
              .collect(Collectors.groupingBy(VariableInstance::getProcessInstanceId));
      //      logger.info("#### variables:{}", variables);
      Map<String, Map<String, Object>> variableMap = new HashMap<>(16);
      Optional.ofNullable(variables)
          .ifPresent(
              variable -> {
                variable.entrySet().stream()
                    .filter(Objects::nonNull)
                    .forEach(
                        entry -> {
                          Map<String, Object> keyValue =
                              entry.getValue().stream()
                                  .filter(
                                      variableInstance ->
                                          Objects.nonNull(variableInstance.getValue()))
                                  .collect(
                                      Collectors.toMap(
                                          VariableInstance::getName, VariableInstance::getValue));
                          variableMap.put(entry.getKey(), keyValue);
                        });
              });
      logger.info("###### variableMap:{}", variableMap);
      // 获取用户相关信息
      CommonResult<?> userInfo =
          userService.getOrgInfoByUserIds(Collections.singletonList(param.getAssignee()));
      //      logger.info("###### userInfo:{}", userInfo);
      result.stream()
          .filter(Objects::nonNull)
          .forEach(
              task -> {
                task.setBusinessId(businessKeyMap.get(task.getProcessInstanceId()));
                task.setProcessKey(processKeyMap.get(task.getProcessDefinitionId()));
                task.setProcessDefinitionName(processNameMap.get(task.getProcessDefinitionId()));
                Map<String, Object> infoData = (Map<String, Object>) userInfo.getData();
                JSONObject jsonInfo = JSONUtil.parseObj(infoData.get(param.getAssignee()));
                task.setAssigneeName(String.valueOf(jsonInfo.get("realName")));
                task.setOrgName(String.valueOf(jsonInfo.get("title")));
                task.setCreator(
                    String.valueOf(
                        variableMap.get(task.getProcessInstanceId()).get("creatorUserName")));
                task.setProjectName(
                    String.valueOf(
                        variableMap.get(task.getProcessInstanceId()).get("projectName")));
              });
      // 根据processKey过滤后查询对应表单中type后返回给前端进行页面路由跳转
      Set<String> targetBusinessIds =
          result.stream()
              .filter(task -> StringUtils.equals("process_gcbglx", task.getProcessKey()))
              .map(TaskVO::getBusinessId)
              .collect(Collectors.toSet());
      logger.info("##### targetBusinessIds:{}", targetBusinessIds);
      List<TargetDataResult> targetDataBatch =
          workflowFormService.getTargetDataBatch("process_gcbglx", "type", targetBusinessIds);
      logger.info("##### targetDataBatch:{}", targetDataBatch);
      Optional.ofNullable(targetDataBatch)
          .ifPresent(
              data -> {
                Map<String, Object> dataMap =
                    data.stream()
                        .collect(
                            Collectors.toMap(
                                TargetDataResult::getBusinessId, TargetDataResult::getValue));
                result.stream()
                    .filter(Objects::nonNull)
                    .forEach(
                        task -> {
                          task.setType(String.valueOf(dataMap.get(task.getBusinessId())));
                        });
              });
    }
    CommonPage<TaskVO> page = CommonPage.restPage(param, count, result);
    logger.info("获取待办列表{}", result);
    return page;
  }

  /**
   * 已办列表
   *
   * @param param 已办请求参数
   * @return 已办前端展示列表
   */
  @Override
  public CommonPage<TaskVO> doneTask(ProcessPageParam param) {
    List<HistoricTaskInstance> list = null;
    Object listRes = null;
    logger.info("已办列表查询参数");
    // 获取历史流转任务
    /** 开启查询条件链式调用---begin */
    try {
      listRes =
          Chain.start()
              .must(x -> Util.isNotEmpty(param))
              .must(x -> Util.isNotEmpty(param.getProcessDefinitionName()))
              .operation(
                  x ->
                      historyService
                          .createHistoricTaskInstanceQuery()
                          // 指定办理人
                          .taskAssignee(param.getAssignee())
                          .finished()
                          .processVariableValueLike(
                              ProcessConstant.PROCESS_DEFINITION_KEY,
                              "%" + Util.escapeStr(param.getProcessDefinitionName()) + "%")
                          // 查询已经完成的任务
                          .listPage(param.getOffSet(), param.getPageSize()))
              .end(x -> x.get(Chain.OPERATION));
    } catch (ChainException e) {
    }

    try {
      Object res1 = listRes;
      listRes =
          Chain.start()
              .must(x -> Util.isEmpty(res1))
              .must(x -> Util.isNotEmpty(param))
              .must(x -> Util.isEmpty(param.getProcessDefinitionName()))
              .operation(
                  x ->
                      historyService
                          .createHistoricTaskInstanceQuery()
                          // 指定办理人
                          .taskAssignee(param.getAssignee())
                          .finished()
                          // 查询已经完成的任务
                          .listPage(param.getOffSet(), param.getPageSize()))
              .end(x -> x.get(Chain.OPERATION));
    } catch (ChainException e) {
    }

    try {
      Object res2 = listRes;
      list =
          (List<HistoricTaskInstance>)
              Chain.start()
                  .must(x -> Util.isNotEmpty(res2))
                  .operation(x -> res2)
                  .end(x -> x.get(Chain.OPERATION));
    } catch (ChainException e) {
    }

    long count = 0L;
    try {
      count =
          (long)
              Chain.start()
                  .must(x -> Util.isNotEmpty(param))
                  .must(x -> Util.isNotEmpty(param.getProcessDefinitionName()))
                  .operation(
                      x ->
                          historyService
                              .createHistoricTaskInstanceQuery()
                              // 指定办理人
                              .taskAssignee(param.getAssignee())
                              .finished()
                              .processVariableValueLike(
                                  ProcessConstant.PROCESS_DEFINITION_KEY,
                                  "%" + Util.escapeStr(param.getProcessDefinitionName()) + "%")
                              .count())
                  .end(x -> x.get(Chain.OPERATION));
    } catch (ChainException e) {
    }

    try {
      long resCount = count;
      count =
          (long)
              Chain.start()
                  .must(x -> resCount == 0)
                  .must(x -> Util.isNotEmpty(param))
                  .must(x -> Util.isEmpty(param.getProcessDefinitionName()))
                  .operation(
                      x ->
                          historyService
                              .createHistoricTaskInstanceQuery()
                              // 指定办理人
                              .taskAssignee(param.getAssignee())
                              .finished()
                              .count())
                  .end(x -> x.get(Chain.OPERATION));
    } catch (ChainException e) {
    }
    /** 查询条件链式调用结束---end */
    //    logger.info("#### list:{}", list);
    // 获取历史流程实例x
    Map<String, HistoricProcessInstance> historicProcessInstanceMap = null;
    Map<String, Object> infoData = null;
    Map<String, Object> typeMap = null;
    Map<String, Map<String, Object>> variableMap = new HashMap<>(16);
    if (CollectionUtil.isNotEmpty(list)) {
      Set<String> historicProcessInstanceIds =
          list.stream().map(HistoricTaskInstance::getProcessInstanceId).collect(Collectors.toSet());
      List<HistoricProcessInstance> historicProcessInstanceList =
          historyService
              .createHistoricProcessInstanceQuery()
              .processInstanceIds(historicProcessInstanceIds)
              .list();
      historicProcessInstanceMap =
          historicProcessInstanceList.stream()
              .collect(
                  Collectors.toMap(
                      HistoricProcessInstance::getId,
                      historicProcessInstance -> historicProcessInstance));
      List<HistoricVariableInstance> variableList = new ArrayList<>();
      List<HistoricVariableInstance> projectNames =
          historyService
              .createHistoricVariableInstanceQuery()
              .processInstanceIdIn(historicProcessInstanceIds.toArray(new String[0]))
              .variableName("projectName")
              .list();
      variableList.addAll(projectNames);
      List<HistoricVariableInstance> creatorUserNames =
          historyService
              .createHistoricVariableInstanceQuery()
              .processInstanceIdIn(historicProcessInstanceIds.toArray(new String[0]))
              .variableName("creatorUserName")
              .list();
      variableList.addAll(creatorUserNames);
      Map<String, List<HistoricVariableInstance>> variables =
          variableList.stream()
              .collect(Collectors.groupingBy(HistoricVariableInstance::getProcessInstanceId));
      variables.entrySet().stream()
          .forEach(
              entry -> {
                Map<String, Object> keyValue =
                    entry.getValue().stream()
                        .filter(variableInstance -> Objects.nonNull(variableInstance.getValue()))
                        .collect(
                            Collectors.toMap(
                                HistoricVariableInstance::getName,
                                HistoricVariableInstance::getValue));
                variableMap.put(entry.getKey(), keyValue);
              });
      logger.info("###### variableMap:{}", variableMap);
      // 获取用户相关信息
      List<String> userIds =
          list.stream()
              .map(HistoricTaskInstance::getAssignee)
              .filter(Objects::nonNull)
              .collect(Collectors.toList());
      if (CollectionUtil.isNotEmpty(userIds)) {
        //        logger.info("##### userIds:{}", userIds);
        CommonResult<?> userInfo = userService.getOrgInfoByUserIds(userIds);
        infoData = (Map<String, Object>) userInfo.getData();
        //        logger.info("##### infoData:{}", infoData);
      }
      // 根据processKey过滤后查询对应表单中type后返回给前端进行页面路由跳转
      Set<String> targetBusinessIds =
          historicProcessInstanceList.stream()
              .filter(
                  processInstance ->
                      StringUtils.equals(
                          "process_gcbglx", processInstance.getProcessDefinitionKey()))
              .map(HistoricProcessInstance::getBusinessKey)
              .collect(Collectors.toSet());
      List<TargetDataResult> targetDataBatch =
          workflowFormService.getTargetDataBatch("process_gcbglx", "type", targetBusinessIds);
      logger.info("##### targetDataBatch:{}", targetDataBatch);
      if (CollectionUtil.isNotEmpty(targetDataBatch)) {
        typeMap =
            targetDataBatch.stream()
                .filter(Objects::nonNull)
                .collect(
                    Collectors.toMap(TargetDataResult::getBusinessId, TargetDataResult::getValue));
      }
    }
    List<TaskVO> result =
        ProcessInstanceConvert.INSTANCE.convertDone(
            list, infoData, historicProcessInstanceMap, typeMap, variableMap);
    CommonPage<TaskVO> page = CommonPage.restPage(param, count, result);
    logger.info("获取已办列表{}", result);
    return page;
  }

  /**
   * 获取流程评论列表
   *
   * @param param 流程评论列表请求参数
   * @return 评论列表
   */
  @Override
  public List<ProcessCommentVO> comments(CommentParam param) {
    // 查询这个流程的审批意见,用未完成任务id查整个流程已完成任务的审批意见内容
    List<Comment> commentList = new ArrayList<Comment>();
    // 使用流程实例ID，查询历史任务，获取历史任务对应的每个任务ID
    List<HistoricTaskInstance> historicTaskList =
        historyService
            // 历史任务表查询
            .createHistoricTaskInstanceQuery()
            // 使用流程实例ID查询
            .processInstanceId(param.getProcessInstanceId())
            .orderByHistoricTaskInstanceEndTime()
            .asc()
            .list();
    // 遍历集合，获取每个任务ID
    historicTaskList.stream()
        .forEach(
            historicTask -> {
              // 获取历史任务批注的任务ID
              //              historicTask.get
              String historicTaskId = historicTask.getId();
              // 基于历史已完成任务ID查询
              List<Comment> taskCommentList = taskService.getTaskComments(historicTaskId);
              commentList.addAll(taskCommentList);
            });
    // 获取节点名称
    Map<String, String> activityNameMap =
        historicTaskList.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(HistoricTaskInstance::getId, HistoricTaskInstance::getName));
    return ProcessInstanceConvert.INSTANCE.convertComments(commentList, activityNameMap);
  }

  /**
   * 退回至发起节点,或者指定的任务节点，同时增加了动态获取变量名称的底层支持
   *
   * @param param 请求参数
   * @return 退回后taskId
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public Boolean fallback(RejectToFirstNodeRequestParam param, String userId) {
    // todo 获取当前登录用户指定流程实例id任务
    Task task =
        taskService
            .createTaskQuery()
            // 当前登录用户的id
            .taskAssignee(userId)
            .processInstanceId(param.getProcessInstanceId())
            .active()
            .singleResult();
    String processDefinitionId = task.getProcessDefinitionId();
    List<HistoricActivityInstance> resultList = new ArrayList<>();
    // 不指定activityId则默认为退回至创建节点
    if (StringUtils.isBlank(param.getActivityId())) {
      List<HistoricActivityInstance> historicActivityInstanceList =
          historyService
              .createHistoricActivityInstanceQuery()
              .processInstanceId(param.getProcessInstanceId())
              .activityType("userTask")
              .finished()
              .orderByHistoricActivityInstanceEndTime()
              .asc()
              .list();
      // 获取第一节点信息
      HistoricActivityInstance historicActivityInstance = historicActivityInstanceList.get(0);
      resultList.add(historicActivityInstance);
    } else {
      // 指定activityId则获取此activityId历史流转记录
      List<HistoricActivityInstance> historicActivityInstanceList =
          historyService
              .createHistoricActivityInstanceQuery()
              .processInstanceId(param.getProcessInstanceId())
              .activityId(param.getActivityId())
              .activityType("userTask")
              .finished()
              .orderByHistoricActivityInstanceEndTime()
              .asc()
              .list();
      if (CollectionUtil.isNotEmpty(historicActivityInstanceList)) {
        resultList.addAll(historicActivityInstanceList);
      }
    }
    // 这设置的是审批人及查询意见时的userId
    identityService.setAuthenticatedUserId(param.getAssignee());
    taskService.createComment(task.getId(), param.getProcessInstanceId(), param.getComment());
    NodeInfoVo nodeInfoVo = isMultiSignNode(task, processDefinitionId, param.getActivityId(), true);
    if (CollectionUtil.isNotEmpty(resultList)) {
      resultList.stream()
          .forEach(
              result -> {
                // 设置流程中的可变参数
                Map<String, Object> taskVariable = new HashMap<>(16);
                taskVariable.put(nodeInfoVo.getParamKey(), result.getAssignee());
                logger.info("###### taskVariable:{}", taskVariable);
                // 流程的可变参数赋值
                runtimeService
                    .createProcessInstanceModification(param.getProcessInstanceId())
                    // 关闭该节点全部相关任务
                    .cancelAllForActivity(task.getTaskDefinitionKey())
                    .setAnnotation("进行退回操作")
                    // 启动目标活动节点
                    .startBeforeActivity(param.getActivityId())
                    // 流程的可变参数赋值
                    .setVariables(taskVariable)
                    .execute();
                logger.info("退回至:{}成功", param.getActivityId());
              });
    }
    return true;
  }

  /**
   * 流程历史节点
   *
   * @param processInstanceId 流程实例id
   * @return 历史节点列表
   */
  @Override
  public List<HistoricNode> historicNodes(String processInstanceId) {
    List<HistoricActivityInstance> historicActivityInstanceList =
        historyService
            .createHistoricActivityInstanceQuery()
            .processInstanceId(processInstanceId)
            .activityType("userTask")
            .finished()
            .orderByHistoricActivityInstanceEndTime()
            .asc()
            .list();
    // 获取用户相关信息
    Map<String, Object> infoData = null;
    if (CollectionUtil.isNotEmpty(historicActivityInstanceList)) {
      CommonResult<?> userInfo =
          userService.getOrgInfoByUserIds(
              historicActivityInstanceList.stream()
                  .map(HistoricActivityInstance::getAssignee)
                  .collect(Collectors.toList()));
      infoData = (Map<String, Object>) userInfo.getData();
    }
    List<HistoricNode> historicNodes =
        ProcessInstanceConvert.INSTANCE.convertHistoricNodes(
            historicActivityInstanceList, infoData);
    logger.info("退回节点列表:{}", historicNodes);
    return historicNodes;
  }

  /**
   * 提交前获取后续节点
   *
   * @param param 请求参数
   * @return 后续节点列表
   */
  @Override
  public List<LabelValueVO> targets(QueryTargetParam param) {
    List<LabelValueVO> waitSelectNodes = new ArrayList<>();
    // 获取流程定义的bpmn模型
    BpmnModelInstance bpmn = repositoryService.getBpmnModelInstance(param.getProcessDefinitionId());
    ModelElementInstance domElement = bpmn.getModelElementById(param.getActivityId());
    // 转换成 flowNode流程节点 才能获取到前后元素
    FlowNode act = (FlowNode) domElement;
    List<FlowNode> succeedingList = null;
    Iterator<SequenceFlow> flowIt = null;
    if (Util.isNotEmpty(param.getProcessInstanceId())) {
      long curTasksCount =
          taskService.createTaskQuery().processInstanceId(param.getProcessInstanceId()).count();
      if (curTasksCount > 1) {
        return waitSelectNodes;
      }
    }
    if (Util.isNotEmpty(act)) {
      // 获取当前节点后续节点
      Query<FlowNode> succeedings = act.getSucceedingNodes();
      // 获取当前节点出线
      Collection<SequenceFlow> outGoingLines = act.getOutgoing();
      if (CollectionUtil.isNotEmpty(outGoingLines)) {
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
        logger.info("##### nextNodeId:{}", sequenceFlow.getTarget().getId());
        succeedingList.forEach(
            succeeding -> {
              if (succeeding.getId().equals(sequenceFlow.getTarget().getId())) {
                LabelValueVO labelValueVo = new LabelValueVO();
                labelValueVo.setLabel(succeeding.getName());
                labelValueVo.setValue(
                    ReUtil.extractMulti(
                        ProcessConstant.NEXT_OUTGOING_REGEX, sequenceFlow.getTextContent(), "$2"));
                labelValueVo.setActivityId(
                    succeeding.getAttributeValue(ProcessConstant.CURNODE_ATTRI_ID_LABEL));
                labelValueVo.setMultiNode(
                    succeeding.getChildElementsByType(LoopCharacteristics.class).size() > 0);
                waitSelectNodes.add(labelValueVo);
              }
            });
      }
    }
    logger.info("###### waitSelectNodes:{}", waitSelectNodes);
    return waitSelectNodes;
  }

  /**
   * 获取流程图数据
   *
   * @param processInstanceId 流程实例id
   * @param user 当前登录用户id
   * @return 流程图数据
   */
  @Override
  public ActivityHighLineVO getHighLightNode(
      String processInstanceId, String user, String processKey) {
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

  /**
   * 流程关联搜索
   *
   * @param param 请求参数
   * @param userId 用户id
   * @return 搜索结果
   */
  @Override
  public List<TaskVO> searchProcess(SearchProcessParam param, String userId) {
    // 根据processKey获取对应设置
    QueryWrapperX<ProcessAssociateSetting> queryWrapperX = new QueryWrapperX<>();
    queryWrapperX.select("associated_process_key").eq("process_key", param.getProcessKey());
    ProcessAssociateSetting processAssociateSetting =
        associateSettingMapper.selectOne(queryWrapperX);
    List<TaskVO> taskList = new ArrayList<>();
    Optional.ofNullable(processAssociateSetting)
        .ifPresent(
            setting -> {
              // 根据被associatedProcessKey获取已完成流转的流程实例数据
              List<HistoricProcessInstance> historicProcessInstanceList =
                  historyService
                      // 历史任务表查询
                      .createHistoricProcessInstanceQuery()
                      // 使用processKey查询
                      .processDefinitionKey(processAssociateSetting.getAssociatedProcessKey())
                      .finished()
                      .orderByProcessInstanceEndTime()
                      .asc()
                      .list();
              // 获取已关联数据
              List<ProcessAssociated> associatedList =
                  associatedMapper.selectList("process_instance_id", param.getProcessInstanceId());
              if (CollectionUtil.isNotEmpty(associatedList)) {
                Set<String> associatedIds =
                    associatedList.stream()
                        .map(ProcessAssociated::getProcessInstanceId)
                        .collect(Collectors.toSet());
                historicProcessInstanceList =
                    historicProcessInstanceList.stream()
                        .filter(Objects::nonNull)
                        .filter(instance -> associatedIds.contains(instance.getId()))
                        .collect(Collectors.toList());
              }
              historicProcessInstanceList.stream()
                  .filter(Objects::nonNull)
                  .forEach(
                      historicProcessInstance -> {
                        TaskVO taskVO = new TaskVO();
                        taskVO.setProcessKey(historicProcessInstance.getProcessDefinitionKey());
                        taskVO.setCreateTime(historicProcessInstance.getStartTime());
                        taskVO.setBusinessId(historicProcessInstance.getBusinessKey());
                        taskVO.setEndTime(historicProcessInstance.getEndTime());
                        taskVO.setProcessInstanceId(historicProcessInstance.getId());
                        taskVO.setAssignee(historicProcessInstance.getStartUserId());
                        taskVO.setProcessDefinitionId(
                            historicProcessInstance.getProcessDefinitionId());
                        taskVO.setProcessDefinitionName(
                            historicProcessInstance.getProcessDefinitionName());
                        taskList.add(taskVO);
                      });
              // 获取对应业务表数据
              if (CollectionUtil.isNotEmpty(taskList)) {
                // 获取businessId
                Set<String> businessIds =
                    taskList.stream()
                        .map(TaskVO::getBusinessId)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
                // 根据processKey、businessId获取对应表单数据
                Map<String, Map<String, Object>> formData =
                    workflowFormService.getFormDataBatch(
                        processAssociateSetting.getAssociatedProcessKey(), businessIds);
                logger.info("##### formData:{}", formData);
                if (Objects.nonNull(formData)) {
                  taskList.stream()
                      .filter(Objects::nonNull)
                      .forEach(
                          task -> {
                            task.setBdhf(
                                String.valueOf(formData.get(task.getBusinessId()).get("bdhf")));
                            task.setBdmc(
                                String.valueOf(formData.get(task.getBusinessId()).get("bdmc")));
                          });
                }
              }
            });
    return taskList;
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

  /**
   * 流程附件
   *
   * @param processInstanceId 流程实例id
   * @return 流程附件列表
   */
  @Override
  public List<AttachmentVO> attachments(String processInstanceId) {
    List<Attachment> attachments = taskService.getProcessInstanceAttachments(processInstanceId);
    return FileCommonConvert.INSTANCE.convertAttachments(attachments);
  }

  /**
   * 设置流程全局变量(Global)
   *
   * @param executionId 任务id
   */
  public void setGlobalVariables(
      String executionId, String processKey, Map<String, Object> formData) {
    if ("process_gcbglx".equals(processKey)) {
      // 提交流程时设置流程变量
      Map<String, java.io.Serializable> params = new HashMap<>();
      params.put("type", (Serializable) formData.get("type"));
      // 设置global变量
      runtimeService.setVariables(executionId, params);
      logger.info("###### 设置流程全局变量成功 ######");
    }
  }

  /**
   * 设置流程局部变量(Local)
   *
   * @param executionId 当前任务id
   * @param param 局部变量
   */
  public void setLocalVariables(String executionId, Map<String, Boolean> param) {
    // 设置local变量
    runtimeService.setVariablesLocal(executionId, param);
  }
}
