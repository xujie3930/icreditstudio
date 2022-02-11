package com.micro.cloud.modules.form.dal.mapper;

import com.micro.cloud.modules.form.dal.dataobject.WorkflowBillField;
import com.micro.cloud.mybatis.core.mapper.BaseMapperX;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/** @author roy */
@Mapper
public interface WorkFlowBillFieldMapper extends BaseMapperX<WorkflowBillField> {

  /**
   * 获取流程表单字段
   *
   * @param processKey 流程proessKey
   * @param fields 表单字段集合
   * @return 表单字段集合
   */
  List<WorkflowBillField> getFields(
      @Param(value = "processKey") String processKey,
      @Param(value = "fields") Collection<String> fields);
}
