package com.micro.cloud.modules.process.controller;

import com.micro.cloud.api.CommonPage;
import com.micro.cloud.api.CommonResult;
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
import com.micro.cloud.modules.process.service.ProcessService;
import com.micro.cloud.modules.process.vo.ActivityHighLineVO;
import com.micro.cloud.modules.process.vo.HistoricNode;
import com.micro.cloud.modules.process.vo.LabelValueVO;
import com.micro.cloud.modules.process.vo.ProcessCommentVO;
import com.micro.cloud.modules.process.vo.ProcessDataVO;
import com.micro.cloud.modules.process.vo.ProcessInstanceVO;
import com.micro.cloud.modules.process.vo.ReachableProcessVO;
import com.micro.cloud.modules.process.vo.TaskVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈流程引擎接口〉
 *
 * @author roy
 * @create 2021/12/7
 * @since 1.0.0
 */
@Api(tags = "流程引擎接口")
@RestController
@RequestMapping("/process")
public class ProcessController {

  private final Logger logger = LoggerFactory.getLogger(ProcessController.class);

  private final ProcessService processService;

  public ProcessController(ProcessService processService) {
    this.processService = processService;
  }

  /**
   * 流程定义部署 部署完成后可以去观察数据库表的变化：act_re_deployment 中会新增本次的部署信息、
   * act_re_procdef流程定义的一些信息、act_ge_bytearray 部署的资源文件。。 added by xulei 2021.11.09
   *
   * @return 流程实例发布id
   */
  @ApiOperation(value = "发布流程")
  @GetMapping("/deploy/{bpmnFile}")
  public CommonResult<String> deploy(@PathVariable("bpmnFile") String bpmnFile) {
    try {
      String result = processService.deploy(bpmnFile);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation(value = "流程发起列表")
  @PostMapping("/board")
  public CommonResult<List<ReachableProcessVO>> board(
      @Validated @RequestBody ReachableProcessParam param) {
    List<ReachableProcessVO> result = processService.board(param);
    return CommonResult.success(result);
  }

  /**
   * 开启一条流程，并给用户任务的 assignee 赋值 一个流程实例启动后，可以去观察 act_hi_actinst 表中记录了每个任务的相关信息(审批人、开始时间、结束时间等等)、
   * act_hi_identitylink 表中记录了流程实例的参与者、act_hi_procinst 表中是流程实例的信息、 act_hi_tastinst
   * 记录了流程实例中每个任务实例的信息、act_ru_identitylink 表中记录了运行时的流程实例当前的参与者、 act_ru_tast
   * 表记录了当前运行中的任务、act_ru_variable 表中记录了设置的参数、等等 added by xulei 2021.11.09
   */
  @ApiOperation(value = "开启流程实例")
  @GetMapping("/start/{processKey}")
  public CommonResult<ProcessInstanceVO> start(
      @PathVariable("processKey") String processKey,
      @RequestHeader(value = "userId") String userId,
      @RequestHeader(value = "realName") String userName) {
    try {
      ProcessInstanceVO result = processService.start(processKey, null, userId, userName);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  /**
   * 提交流程 将待办、已办等关键信息带入到流程变量，方便后续分页查询
   *
   * @param params 流程提交参数
   */
  @ApiOperation(value = "提交流程")
  @PostMapping("/commit")
  public CommonResult<Boolean> commit(
      @Validated @RequestBody CommitTaskParam params,
      @RequestHeader(value = "userId") String userId) {
    try {
      Boolean result = processService.commit(params, userId);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation(value = "开启并提交流程")
  @PostMapping("/commit2")
  public CommonResult<Boolean> commit2(
      @RequestBody CommitTaskParam param, @RequestHeader(value = "userInfo") String userInfo) {
    Boolean result = processService.commit2(param, userInfo);
    return CommonResult.success(result);
  }

  /**
   * @param param 获取当前节点表单详情请求参数
   * @return 表单详情数据
   */
  @ApiOperation(value = "查看流程详情")
  @PostMapping("/detail")
  public CommonResult<ProcessDataVO> detail(
      @RequestBody ProcessDetailParam param, @RequestHeader(value = "userId") String userId) {
    try {
      ProcessDataVO detail = processService.detail(param, userId);
      return CommonResult.success(detail);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  /**
   * 加签
   *
   * @param param 节点加签请求参数
   * @return 是否成功
   */
  @ApiOperation(value = "流程节点加签")
  @PostMapping("/append")
  public CommonResult<Boolean> append(
      @RequestBody EditNodeOperatorParam param, @RequestHeader(value = "userId") String userId) {
    try {
      param.setAssignee(userId);
      Boolean result = processService.appendSign(param);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }
  /**
   * 减签
   *
   * @param param 请求参数
   * @return 是否成功
   */
  @ApiOperation(value = "流程节点减签")
  @PostMapping("/subtract")
  public CommonResult<Boolean> subtract(
      @Validated @RequestBody EditNodeOperatorParam param,
      @RequestHeader(value = "userId") String userId) {
    try {
      Boolean result = processService.subtractSign(param);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  /**
   * 获取某个节点的处理人的待办列表
   *
   * @param param 待办请求参数
   * @return 待办列表
   */
  @ApiOperation(value = "待办列表")
  @PostMapping("/todo")
  public CommonResult<CommonPage<TaskVO>> todoTask(
      @Validated @RequestBody ProcessPageParam param,
      @RequestHeader(value = "userId") String assignee) {
    param.setAssignee(assignee);
    CommonPage<TaskVO> result = processService.todoTask(param);
    return CommonResult.success(result);
  }

  /**
   * 获取某个节点的处理人的已办列表
   *
   * @param param 已办请求参数
   * @return 已办列表
   */
  @ApiOperation(value = "已办列表")
  @PostMapping("/done")
  public CommonResult<CommonPage<TaskVO>> doneTask(
      @Validated @RequestBody ProcessPageParam param,
      @RequestHeader(value = "userId") String assignee) {
    param.setAssignee(assignee);
    CommonPage<TaskVO> result = processService.doneTask(param);
    return CommonResult.success(result);
  }

  /**
   * 查询这个流程的审批意见,用未完成任务id查整个流程已完成任务的审批意见内容
   *
   * @param param 请求参数
   * @return 审批意见列表
   */
  @ApiOperation(value = "评论列表")
  @PostMapping("/comments")
  public CommonResult<List<ProcessCommentVO>> comments(@RequestBody CommentParam param) {
    List<ProcessCommentVO> result = processService.comments(param);
    return CommonResult.success(result);
  }

  /**
   * 获取退回节点列表
   *
   * @param processInstanceId 流程实例id
   * @return 退回节点列表
   */
  @ApiOperation(value = "获取流程回退节点")
  @GetMapping("/fallback/nodes/{processInstanceId}")
  public CommonResult<List<HistoricNode>> historicNodes(
      @PathVariable("processInstanceId") String processInstanceId) {
    List<HistoricNode> nodes = processService.historicNodes(processInstanceId);
    return CommonResult.success(nodes);
  }

  /**
   * 从当前节点驳回到第一个任务节点
   *
   * @param param 退回至第一节点请求参数
   * @return
   */
  @ApiOperation(value = "流程退回")
  @PostMapping("/fallback")
  public CommonResult<Boolean> fallback(
      @Validated @RequestBody RejectToFirstNodeRequestParam param,
      @RequestHeader("userId") String userId) {
    try {
      Boolean result = processService.fallback(param, userId);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  /**
   * 获取bpmn模型中当前activity任务节点的目标节点列表
   *
   * @param param 提交时获取下一节点
   * @return 后续节点信息
   */
  @PostMapping("/submit/targets")
  public CommonResult<List<LabelValueVO>> targets(@Validated @RequestBody QueryTargetParam param) {
    List<LabelValueVO> result = processService.targets(param);
    return CommonResult.success(result);
  }

  /**
   * 获取当前流程实例运转状态流程图,含已经过的流程节点，已经过的流程线，当前流程节点，我办理的流程节点高亮标记数据
   *
   * @param processInstanceId 流程实例id
   * @param processKey 流程KEY
   * @return 流程图
   */
  @ApiOperation(value = "流程图(高亮)")
  @GetMapping("/high_light_node/{processInstanceId}/{processKey}")
  public CommonResult<ActivityHighLineVO> getHighlightNode(
      @PathVariable("processInstanceId") String processInstanceId,
      @PathVariable("processKey") String processKey,
      @RequestHeader("userId") String userId) {
    ActivityHighLineVO result =
        processService.getHighLightNode(processInstanceId, userId, processKey);
    return CommonResult.success(result);
  }

  @ApiOperation(value = "流程关联搜索")
  @PostMapping("/search")
  public CommonResult<List<TaskVO>> searchProcess(
      @RequestBody SearchProcessParam param, @RequestHeader("userId") String userId) {
    List<TaskVO> result = processService.searchProcess(param, userId);
    return CommonResult.success(result);
  }

  @ApiOperation(value = "流程附件")
  @GetMapping("/attachments/{processInstanceId}")
  public CommonResult<List<AttachmentVO>> attachments(@PathVariable String processInstanceId) {
    List<AttachmentVO> attachments = processService.attachments(processInstanceId);
    return CommonResult.success(attachments);
  }
}
