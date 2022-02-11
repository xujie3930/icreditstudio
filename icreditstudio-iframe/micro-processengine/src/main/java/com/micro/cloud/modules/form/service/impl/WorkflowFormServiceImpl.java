package com.micro.cloud.modules.form.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.cloud.modules.form.convert.FormFieldConvert;
import com.micro.cloud.modules.form.dal.dataobject.WorkflowBill;
import com.micro.cloud.modules.form.dal.dataobject.WorkflowBillField;
import com.micro.cloud.modules.form.dal.mapper.FormMapper;
import com.micro.cloud.modules.form.dal.mapper.WorkFlowBillFieldMapper;
import com.micro.cloud.modules.form.dal.mapper.WorkFlowBillMapper;
import com.micro.cloud.modules.form.dto.FormFieldValidateDto;
import com.micro.cloud.modules.form.param.FormDataParam;
import com.micro.cloud.modules.form.param.SaveFormSchemaParam;
import com.micro.cloud.modules.form.result.TargetDataResult;
import com.micro.cloud.modules.form.service.WorkflowFormService;
import com.micro.cloud.modules.process.service.ProcessService;
import com.micro.cloud.modules.process.vo.ProcessInstanceVO;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import com.micro.cloud.snowflake.sequence.SequenceService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.*;
import java.util.stream.Collectors;

import com.micro.cloud.util.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.util.UriEncoder;

/**
 * 表单字段管理
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Service("workflowFormService")
public class WorkflowFormServiceImpl extends ServiceImpl<WorkFlowBillFieldMapper, WorkflowBillField>
    implements WorkflowFormService {

  private final Logger logger = LoggerFactory.getLogger(WorkflowFormServiceImpl.class);

  @Autowired private WorkflowFormEngine formEngine;

  @Autowired private SequenceService sequenceService;

  @Autowired private ProcessService processService;

  @Autowired private WorkFlowBillFieldMapper fieldMapper;

  @Autowired private FormMapper formMapper;

  @Autowired private WorkFlowBillMapper workFlowBillMapper;

  /**
   * 保存表单字段Schema
   *
   * @param param 表单Schema参数
   * @return 是否成功
   */
  @Transactional(rollbackFor = {Exception.class})
  @Override
  public Boolean saveFormSchema(SaveFormSchemaParam param) {
    // workflow-bill 数据保存
    WorkflowBill bill = new WorkflowBill();
    String workflowBillId = sequenceService.nextStringValue(null);
    bill.setWorkflowBillId(workflowBillId);
    bill.setProcessKey(param.getProcessKey());
    bill.setTableName(param.getFormSchema().getTableName());
    formMapper.insert(bill);
    //    logger.info("####### schema:{}", param.getFormSchema().getFormSchema());
    // 表单字段定义转换为对应数据库实体类
    List<WorkflowBillField> fields =
        FormFieldConvert.INSTANCE.convertDO(param.getFormSchema().getFormSchema());
    //    logger.info("######## fields:{}", fields);
    Optional.ofNullable(fields)
        .ifPresent(
            entries ->
                entries.stream()
                    .filter(Objects::nonNull)
                    .forEach(
                        entry -> {
                          entry.setWorkflowBillFieldId(sequenceService.nextStringValue(null));
                          entry.setWorkflowBillId(workflowBillId);
                        }));
    //     根据表单schema创建对应数据库表
    generateTableFromSchema(param.getFormSchema().getTableName(), fields);
    return saveBatch(fields);
  }

  private void generateTableFromSchema(String tableName, List<WorkflowBillField> fields) {
    String sql = formEngine.execute(tableName, fields);
    logger.info("######## sql:{}", sql);
    // formRepository.createTable(sql);
  }

  /**
   * 保存流程表单数据
   *
   * @param param 表单数据
   * @return 表单数据主键id
   */
  @Transactional(rollbackFor = {Exception.class})
  @Override
  public String saveFormData(FormDataParam param) {
    String businessId = sequenceService.nextStringValue(null);
    insertInToTable(param, businessId);
    return businessId;
  }

  private void insertInToTable(FormDataParam param, String businessId) {
    Map<String, Object> map = param.getFormData();
    map.put("business_id", businessId);
    // todo 校验表单参数
    StringBuilder columns = new StringBuilder();
    StringBuilder values = new StringBuilder();
    // 部分表单编号生成
    if (param.getProcessKey().equals("process_htspzb")) {
      String htbh = getHtbh(getWorkflowBill(param.getProcessKey()).getTableName(), "ZB");
      param.getFormData().put("htbh", htbh);
    } else if (param.getProcessKey().equals("process_htspfzb")) {
      String htbh = getHtbh(getWorkflowBill(param.getProcessKey()).getTableName(), "FZB");
      param.getFormData().put("htbh", htbh);
    } else if (param.getProcessKey().equals("process_gcbglx")) {
      String zdbh = getZdbh();
      param.getFormData().put("zdbh", zdbh);
    }
    for (Map.Entry<String, Object> entry : param.getFormData().entrySet()) {
      String k = entry.getKey();
      Object v = entry.getValue();
      columns.append(k).append(",");
      values.append('\'').append(v.toString()).append('\'').append(",");
    }
    columns = removePunctuation(columns);
    values = removePunctuation(values);

    StringBuilder finalColumns = columns;
    StringBuilder finalValues = values;

    formMapper.insertFormData(
        new HashMap(16) {
          {
            put("tableName", getWorkflowBill(param.getProcessKey()).getTableName());
            put("columns", finalColumns);
            put("values", finalValues);
          }
        });
  }

  /** 变更表生成重大编号 */
  private String getZdbh() {
    String header = "变-SG1-";
    String maxZdbh = formMapper.selMaxZdbh();
    String zdbh = null;
    if (StringUtils.isNotBlank(maxZdbh)) {
      Integer maxContractNum =
          Integer.valueOf(maxZdbh.substring(maxZdbh.length() - 3, maxZdbh.length())) + 1;
      zdbh = header + String.format("%03d", maxContractNum);
    } else {
      zdbh = header + "001";
    }
    return zdbh;
  }

  private String getHtbh(String tableName, String header) {
    // 公建中心合同审批表  合同编号生成
    Calendar cal = Calendar.getInstance();
    String year = String.valueOf(cal.get(Calendar.YEAR));
    String htbh = null;
    String maxHtbh = formMapper.selMaxHtbh(tableName);
    if (StringUtils.isNotBlank(maxHtbh)) {
      maxHtbh.replace("\n", "");
      Integer maxContractNum =
          Integer.valueOf(maxHtbh.substring(maxHtbh.length() - 3, maxHtbh.length())) + 1;
      htbh = header + "-" + year + "-" + String.format("%03d", maxContractNum);
    } else {
      htbh = header + "-" + year + "001";
    }
    return htbh;
  }

  /**
   * 保存流程表单数据
   *
   * @param param 表单数据
   * @param userId 用户信息
   * @return 表单数据主键id
   */
  @Transactional(rollbackFor = {Exception.class})
  @Override
  public ProcessInstanceVO ExternalSaveFormData(
      FormDataParam param, String userId, String username) {
    Map<String, Object> paramMap = new HashMap<>(16);
    paramMap.put("projectName", param.getFormData().get("xmmc"));
    // 开启流程
    ProcessInstanceVO processInstance =
        processService.start(param.getProcessKey(), paramMap, userId, UriEncoder.decode(username));
    // 保存表单数据
    insertInToTable(param, processInstance.getBusinessId());
    return processInstance;
  }

  /**
   * 更新流程表单数据
   *
   * @param param 表单数据
   * @return 表单数据主键id
   */
  @Transactional(rollbackFor = {Exception.class})
  @Override
  public int updateFormData(FormDataParam param) {
    // 获取对应表单字段
    WorkflowBill workflowBill = getWorkflowBill(param.getProcessKey());
    transformDateTypeFields(param.getFormData(), workflowBill);
    transformDecimalTypeFields(param.getFormData(), workflowBill);
    transformTextboxToJson(param.getFormData(), workflowBill);
    // todo 校验表单参数
    StringBuilder statement = new StringBuilder();
    StringBuilder stringBuilder = statement;
    Optional.ofNullable(param.getFormData())
        .ifPresent(
            formData -> {
              formData.entrySet().stream()
                  .filter(entry -> !StringUtils.equals("business_id", entry.getKey()))
                  .forEach(
                      entry -> {
                        String k = entry.getKey();
                        Object v = entry.getValue();
                        stringBuilder
                            .append(k)
                            .append('=')
                            .append('\'')
                            .append(v.toString())
                            .append('\'')
                            .append(",");
                      });
            });
    statement = removePunctuation(stringBuilder);
    //    logger.info("###### statement:{}", statement);
    StringBuilder finalStatement = statement;
    return formMapper.updateFormData(
        new HashMap(16) {
          {
            put("tableName", workflowBill.getTableName());
            put("statement", finalStatement);
            put("businessId", param.getFormData().get("business_id"));
          }
        });
  }

  private void transformDateTypeFields(Map<String, Object> formData, WorkflowBill workflowBill) {
    List<WorkflowBillField> workflowBillFields =
        fieldMapper.selectList("workflow_bill_id", workflowBill.getWorkflowBillId());
    Set<String> dateTypeFields =
        workflowBillFields.stream()
            .filter(Objects::nonNull)
            .filter(field -> "datetime".equals(field.getFieldDbType()))
            .map(WorkflowBillField::getFieldName)
            .collect(Collectors.toSet());
    //    logger.info("##### dateTypeFields:{}", dateTypeFields);
    // 对日期类型字段值进行格式转换
    Optional.ofNullable(formData)
        .ifPresent(
            data -> {
              data.entrySet().stream()
                  .filter(entry -> dateTypeFields.contains(entry.getKey()))
                  .forEach(
                      entry -> {
                        // 转换为日期格式
                        DateTime parse = DateUtil.parse(String.valueOf(entry.getValue()));
                        entry.setValue(String.valueOf(parse));
                      });
              //              logger.info("######## formData:{}", data);
            });
  }

  private void transformDecimalTypeFields(Map<String, Object> formData, WorkflowBill workflowBill) {
    List<WorkflowBillField> workflowBillFields =
        fieldMapper.selectList("workflow_bill_id", workflowBill.getWorkflowBillId());
    Set<String> decimalTypeFields =
        workflowBillFields.stream()
            .filter(Objects::nonNull)
            .filter(field -> "decimal".equals(field.getFieldDbType()))
            .map(WorkflowBillField::getFieldName)
            .collect(Collectors.toSet());
    //    logger.info("##### decimalTypeFields:{}", decimalTypeFields);
    // 对decimal类型字段值进行空字符串过滤
    Optional.ofNullable(formData)
        .ifPresent(
            data -> {
              data.entrySet().stream()
                  .filter(entry -> decimalTypeFields.contains(entry.getKey()))
                  .forEach(
                      entry -> {
                        Object value = entry.getValue();
                        entry.setValue(value.equals("") ? null : value);
                      });
              //              logger.info("######## formData:{}", formData);
            });
  }

  // 文本框数组转json入库
  private void transformTextboxToJson(Map<String, Object> formData, WorkflowBill workflowBill) {
    List<WorkflowBillField> workflowBillFields =
        fieldMapper.selectList("workflow_bill_id", workflowBill.getWorkflowBillId());
    Set<String> viewType =
        workflowBillFields.stream()
            .filter(Objects::nonNull)
            .filter(field -> 2 == field.getViewType())
            .map(WorkflowBillField::getFieldName)
            .collect(Collectors.toSet());
    //    logger.info("##### viewType:{}", viewType);
    // 对前端文本框传入数组转json字符串保存
    Optional.ofNullable(formData)
        .ifPresent(
            data -> {
              data.entrySet().stream()
                  .filter(entry -> viewType.contains(entry.getKey()))
                  .forEach(
                      entry -> {
                        List<String> value = (List<String>) entry.getValue();
                        String s = JSON.toJSONString(value);
                        entry.setValue(JsonUtils.toJsonString(value));
                      });
              //              logger.info("######## formData:{}", formData);
            });
  }

  // 过滤文本框字段json字符串转list
  private void transformTextboxToList(Map<String, Object> formData, WorkflowBill workflowBill) {
    List<WorkflowBillField> workflowBillFields =
        fieldMapper.selectList("workflow_bill_id", workflowBill.getWorkflowBillId());
    Set<String> viewType =
        workflowBillFields.stream()
            .filter(Objects::nonNull)
            .filter(field -> 2 == field.getViewType())
            .map(WorkflowBillField::getFieldName)
            .collect(Collectors.toSet());
    //    logger.info("##### viewType:{}", viewType);
    // 对前端文本框传入数组转json字符串保存
    Optional.ofNullable(formData)
        .ifPresent(
            data -> {
              data.entrySet().stream()
                  .filter(entry -> viewType.contains(entry.getKey()))
                  .forEach(
                      entry -> {
                        String value = (String) entry.getValue();
                        entry.setValue(JsonUtils.parseArray(value, String.class));
                      });
              //              logger.info("######## formData:{}", formData);
            });
  }

  /**
   * 删除流程表单数据
   *
   * @param param 表单数据
   * @return 表单数据主键id
   */
  @Override
  public int deleteFormData(FormDataParam param) {
    return formMapper.deleteFormData(
        new HashMap<String, Object>(16) {
          {
            put("tableName", getWorkflowBill(param.getProcessKey()).getTableName());
            put("businessId", param.getFormData().get("business_id"));
          }
        });
  }

  /**
   * 获取流程表单数据
   *
   * @param param 表单数据
   * @return 表单数据主键id
   */
  @Override
  public Map<String, Object> getFormData(FormDataParam param) {
    Map<String, Object> formData =
        formMapper.selectFormData(
            new HashMap<String, Object>(16) {
              {
                put("tableName", getWorkflowBill(param.getProcessKey()).getTableName());
                put("businessId", param.getFormData().get("business_id"));
              }
            });
    transformDateTypeFields(formData, getWorkflowBill(param.getProcessKey()));
    // 文本框数据格式转换
    transformTextboxToList(formData, getWorkflowBill(param.getProcessKey()));
    return formData;
  }

  /**
   * 获取指定字段
   *
   * @param processKey 流程定义
   * @param target 目标字段
   * @param businessIds 业务主键id
   * @return 查询结果
   */
  @Override
  public List<TargetDataResult> getTargetDataBatch(
      String processKey, String target, Collection<String> businessIds) {
    String tableName = getWorkflowBill(processKey).getTableName();
    return formMapper.getTargetDataBatch(tableName, target, businessIds);
  }

  /**
   * 批量获取表单数据
   *
   * @param processKey 流程processKey
   * @param businessIds 业务id集合
   * @return 业务数据集合
   */
  @Override
  public Map<String, Map<String, Object>> getFormDataBatch(
      String processKey, Collection<String> businessIds) {
    String tableName = getWorkflowBill(processKey).getTableName();
    return formMapper.getFormDataBatch(tableName, businessIds);
  }

  /**
   * 提交时校验表单参数
   *
   * @param processKey 流程processKey
   * @param formData 表单数据
   */
  @Override
  public void validateFormFields(String processKey, Map<String, Object> formData) {
    // 根据前端传递参数名称获取对应字段属性
    Set<String> keySet = new HashSet<>(formData.keySet());
    List<WorkflowBillField> fieldList = fieldMapper.getFields(processKey, keySet);
    List<FormFieldValidateDto> validateDtoList =
        FormFieldConvert.INSTANCE.convertDto(fieldList, formData);
    logger.info("##### validateDtoList:{}", validateDtoList);
  }

  /**
   * 根据processKey,获取workflow_bill表中的对应记录
   *
   * @param processKey 流程processKey
   * @return 字段映射关系
   */
  @Override
  public WorkflowBill getWorkflowBill(String processKey) {
    QueryWrapper<WorkflowBill> queryWrapper = new QueryWrapperX<>();
    queryWrapper.eq("process_key", processKey);
    return workFlowBillMapper.selectOne(queryWrapper);
  }

  /**
   * 去除标点符号
   *
   * @param stringBuilder
   * @return
   */
  private StringBuilder removePunctuation(StringBuilder stringBuilder) {
    if (stringBuilder.toString().contains(",")) {
      stringBuilder = stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
    }
    return stringBuilder;
  }
}
