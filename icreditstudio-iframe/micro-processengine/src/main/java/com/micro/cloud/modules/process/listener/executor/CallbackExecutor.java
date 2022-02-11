package com.micro.cloud.modules.process.listener.executor;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.micro.cloud.modules.file.convert.CommonFormDataConvert;
import com.micro.cloud.modules.file.vo.AttachmentVO;
import com.micro.cloud.modules.form.param.FormDataParam;
import com.micro.cloud.modules.form.service.WorkflowFormService;
import com.micro.cloud.modules.process.constant.ProcessConstant;
import com.micro.cloud.modules.process.convert.AttachmentConvert;
import com.micro.cloud.modules.process.convert.CallbackResultConvert;
import com.micro.cloud.modules.process.cache.FieldConvertCache;
import com.micro.cloud.modules.process.result.CallbackAttachmentResult;
import com.micro.cloud.modules.process.service.ProcessService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * 〈回调接口执行器〉
 *
 * @author roy
 * @create 2021/12/18
 * @since 1.0.0
 */
@Service
public class CallbackExecutor implements IExecutor {

  private final Logger logger = LoggerFactory.getLogger(CallbackExecutor.class);

  @Autowired private RestTemplate restTemplate;

  @Autowired private WorkflowFormService workflowFormService;

  @Autowired private RuntimeService runtimeService;

  @Autowired private RepositoryService repositoryService;

  @Autowired private ProcessService processService;

  @Value("${process.callback}")
  private String callBack;

  @Override
  public void execute(DelegateExecution delegateExecution, String approveStatus) {
    // 获取表单相关数据
    String processInstanceId = delegateExecution.getProcessInstanceId();
    ProcessInstance processInstance =
        runtimeService
            .createProcessInstanceQuery()
            .processInstanceId(processInstanceId)
            .singleResult();
    ProcessDefinition processDefinition =
        repositoryService
            .createProcessDefinitionQuery()
            .processDefinitionId(delegateExecution.getProcessDefinitionId())
            .singleResult();
    // 获取对应表单数据
    FormDataParam formDataParam = new FormDataParam();
    formDataParam.setProcessKey(processDefinition.getKey());
    Map<String, Object> formData = new HashMap<>();
    formData.put("business_id", processInstance.getBusinessKey());
    formDataParam.setFormData(formData);
    formData = workflowFormService.getFormData(formDataParam);
    logger.info("流程:{} 表单数据:{}", processDefinition.getName(), formData);
    // 合同类流程需要携带附件信息

    List<CallbackAttachmentResult> attachmentResult = null;
    if (FieldConvertCache.contactSet.contains(processDefinition.getKey())) {
      List<AttachmentVO> attachments = processService.attachments(processInstanceId);
      attachmentResult = AttachmentConvert.INSTANCE.convertCallbackList(attachments);
    }
    // 回调接口字段转换
    formData =
        CommonFormDataConvert.INSTANCE.convert(formData, processDefinition.getKey(), attachmentResult);
    String callbackResult =
        CallbackResultConvert.INSTANCE.convert(
            approveStatus, formData, processDefinition.getKey(), processInstance.getBusinessKey());
    logger.info(
        "流程:{} 实例:{} 审批结果:{}", processDefinition.getName(), processInstanceId, callbackResult);
    try {
      // 执行回调请求
      HttpHeaders headers = new HttpHeaders();
      MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
      headers.setContentType(type);
      headers.add("Accept", MediaType.APPLICATION_JSON.toString());
      HttpEntity<String> formEntity = new HttpEntity<>(callbackResult, headers);
      String result = restTemplate.postForObject(callBack, formEntity, String.class);
      logger.info("##### result:{}", result);
      JSONObject resultJson = JSONUtil.parseObj(result);
      if (!resultJson.getBool(ProcessConstant.CALLBACK_SUCCESS)) {
        // todo 回调成功与否需持久化,重试机制待添加
        logger.error(
            "流程:{} 实例:{} 审批结果回调失败:{}", processDefinition.getName(), processInstanceId, resultJson);
      }
      logger.info(
          "流程:{} 实例:{} 审批结果回调成功:{}", processDefinition.getName(), processInstanceId, resultJson);
    } catch (RestClientException e) {
      // e.printStackTrace();
      logger.error(
          "流程:{} 实例:{} 审批结果回调异常:{}",
          processDefinition.getName(),
          processInstanceId,
          e.getMessage());
    }
  }
}
