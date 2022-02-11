package com.micro.cloud.modules.form.convert;

import com.micro.cloud.modules.form.dal.dataobject.WorkflowBillField;
import com.micro.cloud.modules.form.dto.FormFieldValidateDto;
import com.micro.cloud.modules.form.param.FormFieldSchema;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈流程表单字段转换〉
 *
 * @author roy
 * @create 2021/12/3
 * @since 1.0.0
 */
@Mapper
public interface FormFieldConvert {

  Logger logger = LoggerFactory.getLogger(FormFieldConvert.class);

  FormFieldConvert INSTANCE = Mappers.getMapper(FormFieldConvert.class);

  /**
   * bean转换DB
   *
   * @param bean
   * @return
   */
  WorkflowBillField convertDO(FormFieldSchema bean);

  /**
   * beans转换为List
   *
   * @param beans
   * @return
   */
  List<WorkflowBillField> convertDO(Collection<FormFieldSchema> beans);

  /**
   * 实体类转换为字段校验信息
   *
   * @param field 字段
   * @return 字段校验信息
   */
  FormFieldValidateDto convertDto(WorkflowBillField field);

  /**
   * 实体类转换为字段校验信息
   *
   * @param fields 字段列表
   * @return 字段校验信息
   */
  List<FormFieldValidateDto> convertDto(List<WorkflowBillField> fields);

  /**
   * 根据前端传递的参数列表，过滤空值后转换为校验信息
   *
   * @param fieldList 字段列表
   * @param formData 前端数据
   * @return 校验信息
   */
  default List<FormFieldValidateDto> convertDto(
      List<WorkflowBillField> fieldList, Map<String, Object> formData) {
    List<FormFieldValidateDto> validateDtoList = convertDto(fieldList);
    Optional.ofNullable(validateDtoList)
        .ifPresent(
            fields -> {
              fields.stream()
                  .filter(
                      field ->
                          formData.containsKey(field.getFieldName())
                              && Objects.nonNull(formData.get(field.getFieldName())))
                  .forEach(
                      field -> {
                        field.setFileValue(formData.get(field.getFieldName()));
                      });
            });
    return validateDtoList;
  }
}
