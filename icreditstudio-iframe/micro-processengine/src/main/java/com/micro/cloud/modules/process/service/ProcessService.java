package com.micro.cloud.modules.process.service;

import com.micro.cloud.api.CommonPage;
import com.micro.cloud.modules.file.vo.AttachmentVO;
import com.micro.cloud.modules.process.param.CommentParam;
import com.micro.cloud.modules.process.param.CommitTaskParam;
import com.micro.cloud.modules.process.param.EditNodeOperatorParam;
import com.micro.cloud.modules.process.param.ProcessDetailParam;
import com.micro.cloud.modules.process.param.ProcessPageParam;
import com.micro.cloud.modules.process.param.QueryTargetParam;
import com.micro.cloud.modules.process.param.ReachableProcessParam;
import com.micro.cloud.modules.process.param.RejectToFirstNodeRequestParam;
import com.micro.cloud.modules.process.param.SearchProcessParam;
import com.micro.cloud.modules.process.vo.ActivityHighLineVO;
import com.micro.cloud.modules.process.vo.HistoricNode;
import com.micro.cloud.modules.process.vo.LabelValueVO;
import com.micro.cloud.modules.process.vo.ProcessCommentVO;
import com.micro.cloud.modules.process.vo.ProcessDataVO;
import com.micro.cloud.modules.process.vo.ProcessInstanceVO;
import com.micro.cloud.modules.process.vo.ReachableProcessVO;
import com.micro.cloud.modules.process.vo.TaskVO;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Element;

/**
 * 〈流程引擎服务接口〉
 *
 * @author roy
 * @create 2021/12/1
 * @since 1.0.0
 */
public interface ProcessService {

  /**
   * 流程发布
   *
   * @param bpmnFile bpmn流程配置文件
   * @return 流程发布id
   */
  @Transactional(rollbackFor = Exception.class)
  String deploy(String bpmnFile);

  /**
   * 启动流程实例
   *
   * @param processKey 流程实例名称
   * @param userInfo 创建人相关信息
   * @return 流程实例id
   */
  @Transactional(rollbackFor = Exception.class)
  ProcessInstanceVO start(
      String processKey, Map<String, Object> paramMap, String userInfo, String userName);

  /**
   * 获取流程详情
   *
   * @param param 详情请求参数
   * @param userId 用户id
   * @return 表单详情数据
   */
  ProcessDataVO detail(ProcessDetailParam param, String userId)
      throws ExecutionException, InterruptedException;

  /**
   * 提交流程
   *
   * @param params 提交流程所需参数
   * @param userInfo 用户信息
   * @return taskId
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean commit(CommitTaskParam params, String userInfo);

  /**
   * 开启流程实例(包含表单参数)
   *
   * @param param 开启流程实例请求参数
   * @param userInfo 用户信息
   * @return 流程实例信息
   */
  Boolean commit2(CommitTaskParam param, String userInfo);

  /**
   * 节点加签
   *
   * @param param 加签所需参数
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean appendSign(EditNodeOperatorParam param);

  /**
   * 节点减签
   *
   * @param param 减签所需参数
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean subtractSign(EditNodeOperatorParam param);

  /**
   * 待办列表
   *
   * @param param 待办列表请求参数
   * @return 待办列表
   */
  CommonPage<TaskVO> todoTask(ProcessPageParam param);

  /**
   * 已办列表
   *
   * @param param 已办请求参数
   * @return 已办列表
   */
  CommonPage<TaskVO> doneTask(ProcessPageParam param);

  /**
   * 获取流程评论列表
   *
   * @param param 流程评论列表请求参数
   * @return 评论列表
   */
  List<ProcessCommentVO> comments(CommentParam param);

  /**
   * 退回至发起节点
   *
   * @param param 请求参数
   * @return 是否成功
   */
  Boolean fallback(RejectToFirstNodeRequestParam param, String userId);

  /**
   * 流程历史节点
   *
   * @param processInstanceId 流程实例id
   * @return 历史节点列表
   */
  List<HistoricNode> historicNodes(String processInstanceId);

  /**
   * 提交前获取后续节点
   *
   * @param param 请求参数
   * @return 后续节点列表
   */
  List<LabelValueVO> targets(QueryTargetParam param);

  /**
   * 获取流程图数据
   *
   * @param processInstanceId 流程实例id
   * @param user 当前登录用户id
   * @param processKey 流程定义KEY
   * @return 流程图数据
   */
  ActivityHighLineVO getHighLightNode(String processInstanceId, String user, String processKey);

  /**
   * 流程展示列表
   *
   * @return
   */
  List<ReachableProcessVO> board(ReachableProcessParam param);

  /**
   * 流程附件
   *
   * @param processInstanceId 流程实例id
   * @return 流程附件列表
   */
  List<AttachmentVO> attachments(String processInstanceId);

  /**
   * 搜素关联流程
   *
   * @param param 参数
   * @param userId 用户id
   * @return 搜索结果
   */
  List<TaskVO> searchProcess(SearchProcessParam param, String userId);

  /**
   * 获取指定流程节点所属步骤
   *
   * @param processKey 流程processKey
   * @param activityId 流程节点id
   * @return 节点所属步骤: 1、2、3...
   */
  Integer getActivityStep(String processKey, String activityId);

  /**
   * 获取指定流程任务节点元素
   *
   * @param processKey 流程processKey
   * @return bpmn:userTask 元素列表
   */
  List<Element> getUserTaskElements(String processKey);
}
