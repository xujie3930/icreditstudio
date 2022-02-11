package com.micro.cloud.modules.form.dal.mapper;

import com.google.api.client.util.Key;
import com.micro.cloud.modules.form.dal.dataobject.WorkflowBill;
import com.micro.cloud.modules.form.dal.dataobject.WorkflowBillField;
import com.micro.cloud.modules.form.result.TargetDataResult;
import com.micro.cloud.mybatis.core.mapper.BaseMapperX;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 〈表单数据库操作〉
 *
 * @author roy
 * @create 2021/12/4
 * @since 1.0.0
 */
@Mapper
public interface FormMapper extends BaseMapperX<WorkflowBill> {

  /**
   * 保存表单数据
   *
   * @return 影响行数
   */
  int insertFormData(Map formData);

  /**
   * 更新表单数据
   *
   * @return 影响行数
   */
  int updateFormData(Map formData);

  /**
   * 删除表单数据
   *
   * @return 影响行数
   */
  int deleteFormData(Map formData);

  /**
   * 获取表单数据
   *
   * @return 影响行数
   */
  Map<String, Object> selectFormData(Map formData);

  /**
   * 批量获取指定字段值
   *
   * @param tableName 表名称
   * @param target 目标字段
   * @param businessIds 业务id集合
   * @return 查询结果
   */
  List<TargetDataResult> getTargetDataBatch(
      @Param(value = "tableName") String tableName,
      @Param(value = "target") String target,
      @Param(value = "ids") Collection<String> businessIds);

  /**
   * 查询合同编号最大值
   *
   * @return
   */
  String selMaxHtbh(String tableName);

  /**
   * 查询变更表重大编号最大值
   *
   * @return
   */
  String selMaxZdbh();

  /**
   * 根据id删除入库文件记录
   *
   * @param id
   * @return
   */
  int delFileById(String id);

  /**
   * 批量获取表单数据
   *
   * @param tableName 业务表名称
   * @param businessIds 业务id集合
   * @return 查询结果
   */
  @MapKey("business_id")
  Map<String, Map<String, Object>> getFormDataBatch(
      @Param(value = "tableName") String tableName,
      @Param(value = "ids") Collection<String> businessIds);

}
