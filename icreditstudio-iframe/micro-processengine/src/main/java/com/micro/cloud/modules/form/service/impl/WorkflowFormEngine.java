package com.micro.cloud.modules.form.service.impl;

import cn.hutool.extra.template.TemplateEngine;
import com.micro.cloud.modules.form.dal.dataobject.WorkflowBillField;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 〈工作流表单模版引擎〉
 *
 * @author roy
 * @create 2021/12/3
 * @since 1.0.0
 */
@Component
public class WorkflowFormEngine {

  private final Logger logger = LoggerFactory.getLogger(WorkflowFormEngine.class);
  /** 模板引擎，由 hutool 实现 */
  private final TemplateEngine templateEngine;

  public WorkflowFormEngine(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  /**
   * 根据sql模版生成sql
   *
   * @param tableName 表名称
   * @param fields 表字段结构
   * @return 建表语句
   */
  public String execute(String tableName, List<WorkflowBillField> fields) {
    HashMap<String, Object> bindingMap = new HashMap<>(16);
    bindingMap.put("tableName", tableName);
    bindingMap.put("fields", fields);
    // 执行生成
    return templateEngine.getTemplate(sqlTemplatePath()).render(bindingMap);
  }

  private static String sqlTemplatePath() {
    return "form/sql.vm";
  }
}
