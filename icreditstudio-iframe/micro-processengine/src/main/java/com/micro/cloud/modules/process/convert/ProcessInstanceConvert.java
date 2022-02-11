package com.micro.cloud.modules.process.convert;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.micro.cloud.modules.file.vo.AttachmentVO;
import com.micro.cloud.modules.process.dal.dataobject.ProcessAssociated;
import com.micro.cloud.modules.process.vo.HistoricNode;
import com.micro.cloud.modules.process.vo.ProcessCommentVO;
import com.micro.cloud.modules.process.vo.ProcessDataVO;
import com.micro.cloud.modules.process.vo.ProcessInstanceVO;
import com.micro.cloud.modules.process.vo.ReachableProcessVO;
import com.micro.cloud.modules.process.vo.TaskVO;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 〈流程实例对象转换〉
 *
 * @author roy
 * @create 2021/12/7
 * @since 1.0.0
 */
@Mapper
public interface ProcessInstanceConvert {

  ProcessInstanceConvert INSTANCE = Mappers.getMapper(ProcessInstanceConvert.class);

  /**
   * processInstance转换为视图类对象
   *
   * @param processInstance 流程实例接口
   * @param task 当前任务
   * @return 流程实例视图对象
   */
  default ProcessInstanceVO convertVO(ProcessInstance processInstance, Task task) {
    ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
    processInstanceVO.setBusinessId(processInstance.getBusinessKey());
    processInstanceVO.setProcessDefinitionId(processInstance.getProcessDefinitionId());
    processInstanceVO.setProcessInstanceId(processInstance.getProcessInstanceId());
    processInstanceVO.setActivityId(task.getTaskDefinitionKey());
    return processInstanceVO;
  }

  /**
   * 待办视图对象转换
   *
   * @param todoList 待办列表
   * @return 前端展示对象
   */
  default List<TaskVO> convertTodo(List<Task> todoList) {
    List<TaskVO> result = new LinkedList<>();
    Optional.ofNullable(todoList)
        .ifPresent(
            list -> {
              list.stream()
                  .forEach(
                      task -> {
                        TaskVO taskVo = new TaskVO();
                        taskVo.setActivityId(task.getTaskDefinitionKey());
                        taskVo.setActivityName(task.getName());
                        taskVo.setAssignee(task.getAssignee());
                        taskVo.setCreateTime(task.getCreateTime());
                        taskVo.setProcessDefinitionId(task.getProcessDefinitionId());
                        taskVo.setTaskDefinitionKey(task.getTaskDefinitionKey());
                        taskVo.setProcessInstanceId(task.getProcessInstanceId());
                        result.add(taskVo);
                      });
            });
    return result;
  }

  /**
   * 已办列表视图对象转换
   *
   * @param doneList 已办列表
   * @return 展示列表
   */
  default List<TaskVO> convertDone(
      List<HistoricTaskInstance> doneList,
      Map<String, Object> infoData,
      Map<String, HistoricProcessInstance> historicProcessInstanceMap,
      Map<String, Object> typeMap,
      Map<String, Map<String, Object>> variableMap) {
    List<TaskVO> result = new LinkedList<>();
    Optional.ofNullable(doneList)
        .ifPresent(
            list -> {
              list.stream()
                  .forEach(
                      taskInstance -> {
                        TaskVO taskVo = new TaskVO();
                        taskVo.setActivityId(taskInstance.getTaskDefinitionKey());
                        taskVo.setActivityName(taskInstance.getName());
                        taskVo.setAssignee(taskInstance.getAssignee());
                        if (ObjectUtils.isNotEmpty(infoData)) {
                          JSONObject jsonInfo =
                              JSONUtil.parseObj(infoData.get(taskInstance.getAssignee()));
                          taskVo.setAssigneeName(String.valueOf(jsonInfo.get("realName")));
                          taskVo.setOrgName(String.valueOf(jsonInfo.get("title")));
                        }
                        if (ObjectUtils.isNotEmpty(historicProcessInstanceMap)) {
                          taskVo.setProcessDefinitionName(
                              historicProcessInstanceMap
                                  .get(taskInstance.getProcessInstanceId())
                                  .getProcessDefinitionName());
                          taskVo.setBusinessId(
                              historicProcessInstanceMap
                                  .get(taskInstance.getProcessInstanceId())
                                  .getBusinessKey());
                          taskVo.setCompleted(
                              "COMPLETED"
                                  .equals(
                                      historicProcessInstanceMap
                                          .get(taskInstance.getProcessInstanceId())
                                          .getState()));
                          taskVo.setCreateTime(
                              historicProcessInstanceMap
                                  .get(taskInstance.getProcessInstanceId())
                                  .getStartTime());
                          taskVo.setEndTime(
                              historicProcessInstanceMap
                                  .get(taskInstance.getProcessInstanceId())
                                  .getEndTime());
                        }
                        taskVo.setProcessDefinitionId(taskInstance.getProcessDefinitionId());
                        taskVo.setProcessInstanceId(taskInstance.getProcessInstanceId());
                        taskVo.setProcessKey(taskInstance.getProcessDefinitionKey());
                        if (ObjectUtils.isNotEmpty(typeMap)) {
                          taskVo.setType(String.valueOf(typeMap.get(taskVo.getBusinessId())));
                        }
                        if (ObjectUtils.isNotEmpty(variableMap)
                            && variableMap.containsKey(taskInstance.getProcessInstanceId())) {
                          taskVo.setCreator(
                              String.valueOf(
                                  variableMap
                                      .get(taskInstance.getProcessInstanceId())
                                      .get("creatorUserName")));
                          taskVo.setProjectName(
                              String.valueOf(
                                  variableMap
                                      .get(taskInstance.getProcessInstanceId())
                                      .get("projectName")));
                        }
                        result.add(taskVo);
                      });
            });
    return result;
  }

  /**
   * 评论列表对象转换
   *
   * @param commentList 评论列表
   * @return 视图展示列表
   */
  default List<ProcessCommentVO> convertComments(
      List<Comment> commentList, Map<String, String> activityNameMap) {
    List<ProcessCommentVO> result = new LinkedList<>();
    Optional.ofNullable(commentList)
        .ifPresent(
            comments -> {
              comments.stream()
                  .forEach(
                      comment -> {
                        ProcessCommentVO processCommentVO = new ProcessCommentVO();
                        processCommentVO.setTaskId(comment.getTaskId());
                        processCommentVO.setActivityName(
                            ObjectUtils.isNotEmpty(activityNameMap)
                                ? activityNameMap.get(processCommentVO.getTaskId())
                                : "");
                        processCommentVO.setProcessInstanceId(comment.getProcessInstanceId());
                        processCommentVO.setUserId(comment.getUserId());
                        processCommentVO.setComment(comment.getFullMessage());
                        processCommentVO.setOperateTime(comment.getTime());
                        result.add(processCommentVO);
                      });
            });
    return result;
  }

  /**
   * 获取流程历史节点
   *
   * @param historicActivityInstances 历史流转记录
   * @return 历史节点
   */
  default List<HistoricNode> convertHistoricNodes(
      List<HistoricActivityInstance> historicActivityInstances, Map<String, Object> infoData) {
    List<HistoricNode> nodes = new LinkedList<>();
    AtomicInteger step = new AtomicInteger(1);
    Optional.ofNullable(historicActivityInstances)
        .ifPresent(
            instances -> {
              instances.stream()
                  .forEach(
                      instance -> {
                        HistoricNode historicNode = new HistoricNode();
                        historicNode.setStep(step.getAndIncrement());
                        historicNode.setProcessInstanceId(instance.getProcessInstanceId());
                        historicNode.setTaskId(instance.getTaskId());
                        historicNode.setActivityId(instance.getActivityId());
                        historicNode.setActivityName(instance.getActivityName());
                        historicNode.setAssignee(instance.getAssignee());
                        if (ObjectUtils.isNotEmpty(infoData)) {
                          JSONObject jsonInfo =
                              JSONUtil.parseObj(infoData.get(instance.getAssignee()));
                          historicNode.setAssigneeName(String.valueOf(jsonInfo.get("realName")));
                          historicNode.setAssigneeDept(String.valueOf(jsonInfo.get("title")));
                        }
                        historicNode.setOperateDate(instance.getEndTime());
                        nodes.add(historicNode);
                      });
            });
    return nodes;
  }

  /**
   * 流程可发起列表视图对象转换
   *
   * @param processDefinitionList 流程定义列表
   * @return 可发起列表视图列表
   */
  default List<ReachableProcessVO> convertReachable(List<ProcessDefinition> processDefinitionList) {
    List<ReachableProcessVO> result = new LinkedList<>();
    Optional.ofNullable(processDefinitionList)
        .ifPresent(
            processDefinitions -> {
              processDefinitions.stream()
                  .forEach(
                      processDefinition -> {
                        ReachableProcessVO reachableProcessVO = new ReachableProcessVO();
                        reachableProcessVO.setProcessKey(processDefinition.getKey());
                        reachableProcessVO.setProcessName(processDefinition.getName());
                        result.add(reachableProcessVO);
                      });
            });
    return result;
  }

  /**
   * 流程数据详情视图对象转换
   *
   * @param formDataTask 表单数据Task
   * @param attachmentTask 流程附件task
   * @param commentsTask 流程签字意见Task
   * @param task 流程节点任务信息
   * @return 流程详情视图对象
   * @throws ExecutionException
   * @throws InterruptedException
   */
  default ProcessDataVO convertDetails(
      FutureTask<Map<String, Object>> formDataTask,
      FutureTask<List<AttachmentVO>> attachmentTask,
      FutureTask<List<ProcessCommentVO>> commentsTask,
      FutureTask<List<ProcessAssociated>> associateProcessesTask,
      FutureTask<Integer> stepTask,
      FutureTask<HistoricTaskInstance> task)
      throws ExecutionException, InterruptedException {
    ProcessDataVO processDataVO = new ProcessDataVO();
    Optional.ofNullable(formDataTask.get()).ifPresent(processDataVO::setFormData);
    Optional.ofNullable(attachmentTask.get()).ifPresent(processDataVO::setAttachments);
    Optional.ofNullable(commentsTask.get()).ifPresent(processDataVO::setComments);
    Optional.ofNullable(stepTask.get()).ifPresent(processDataVO::setStep);
    Optional.ofNullable(task.get())
        .ifPresent(task1 -> processDataVO.setActivityId(task1.getTaskDefinitionKey()));
    Optional.ofNullable(task.get())
        .ifPresent(task1 -> processDataVO.setActivityName(task1.getName()));
    Optional.ofNullable(associateProcessesTask.get())
        .ifPresent(
            data -> {
              processDataVO.setAssociateProcesses(
                  data.stream().map(ProcessAssociated::getCondition).collect(Collectors.toList()));
            });
    return processDataVO;
  }
}
