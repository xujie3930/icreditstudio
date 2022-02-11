package com.micro.cloud.modules.process.convert;

import cn.hutool.json.JSONUtil;
import com.micro.cloud.modules.process.result.ProcessCallbackResult;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 〈回传结果转换〉
 *
 * @author roy
 * @create 2021/12/18
 * @since 1.0.0
 */
@Mapper
public interface CallbackResultConvert {

  CallbackResultConvert INSTANCE = Mappers.getMapper(CallbackResultConvert.class);

  /**
   * 回调结果转换
   *
   * @param approveStatus 审批状态
   * @param formData 表单数据
   * @param businessId 主键id
   * @return 转换结果
   */
  default String convert(
      String approveStatus, Map<String, Object> formData, String processKey, String businessId) {
    ProcessCallbackResult callbackResult = new ProcessCallbackResult();
    callbackResult.setApproveStatus(approveStatus);
    callbackResult.setProcessKey(processKey);
    callbackResult.setBizId(businessId);
    callbackResult.setProcessData(formData);
    return JSONUtil.toJsonStr(callbackResult);
  }
}
