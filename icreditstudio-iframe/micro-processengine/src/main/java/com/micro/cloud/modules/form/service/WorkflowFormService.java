package com.micro.cloud.modules.form.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.cloud.modules.form.dal.dataobject.WorkflowBill;
import com.micro.cloud.modules.form.dal.dataobject.WorkflowBillField;
import com.micro.cloud.modules.form.param.CommitFormParam;
import com.micro.cloud.modules.form.param.FormDataParam;
import com.micro.cloud.modules.form.param.SaveFormSchemaParam;

import com.micro.cloud.modules.form.result.TargetDataResult;
import com.micro.cloud.modules.process.vo.ProcessInstanceVO;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 表单字段管理
 *
 * @author EDZ
 * @since 2021-11-05
 */
public interface WorkflowFormService extends IService<WorkflowBillField> {

  /**
   * 保存表单字段Schema
   *
   * @param param 表单Schema参数
   * @return 是否成功
   */
  Boolean saveFormSchema(SaveFormSchemaParam param);

  /**
   * 保存流程表单数据
   *
   * @param param 表单数据
   * @return 表单数据主键id
   */
  String saveFormData(FormDataParam param);

  /**
   * 开启流程实例并执行保单数据预保存
   *
   * @param param 表单数据
   * @param userId 用户id
   * @return 流程实例信息
   */
  ProcessInstanceVO ExternalSaveFormData(FormDataParam param, String userId, String username);

  /**
   * 更新流程表单数据
   *
   * @param param 表单数据
   * @return 表单数据主键id
   */
  int updateFormData(FormDataParam param);

  /**
   * 删除流程表单数据
   *
   * @param param 表单数据
   * @return 表单数据主键id
   */
  int deleteFormData(FormDataParam param);

  /**
   * 获取流程表单数据
   *
   * @param param 表单数据
   * @return 表单数据主键id
   */
  Map<String, Object> getFormData(FormDataParam param);

  /**
   * 获取指定字段
   *
   * @param processKey 流程定义
   * @param target 目标字段
   * @param businessIds 业务主键id
   * @return 查询结果
   */
  List<TargetDataResult> getTargetDataBatch(
      String processKey, String target, Collection<String> businessIds);

  /**
   * 提交时校验表单参数
   *
   * @param param 表单参数
   */
  void validateFormFields(String processKey, Map<String, Object> formData);

  /**
   * 获取对应表单字段
   *
   * @param processKey 流程processKey
   * @return 表单对应字段
   */
  WorkflowBill getWorkflowBill(String processKey);

  /**
   * 批量获取表单数据
   *
   * @param processKey 流程processKey
   * @param businessIds 业务id集合
   * @return 业务数据集合
   */
  Map<String, Map<String, Object>> getFormDataBatch(
      String processKey, Collection<String> businessIds);
}
