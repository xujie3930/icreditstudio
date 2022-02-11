package com.micro.cloud.modules.file.convert;

import com.micro.cloud.modules.process.cache.FieldConvertCache;
import com.micro.cloud.modules.process.result.CallbackAttachmentResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈文件通用转换〉
 *
 * @author roy
 * @create 2021/12/8
 * @since 1.0.0
 */
@Mapper
public interface CommonFormDataConvert {
  final Logger logger = LoggerFactory.getLogger(CommonFormDataConvert.class);

  CommonFormDataConvert INSTANCE = Mappers.getMapper(CommonFormDataConvert.class);

  /**
   * 流程附件视图对象转换
   *
   * @param dataMap 表单数据
   * @param processKey 流程processKey
   * @return 视图对象
   */
  default Map<String, Object> convert(
      Map<String, Object> dataMap, String processKey,  List<CallbackAttachmentResult> attachments) {
    Map<String, Object> resultMap = new HashMap<>(64);
    if (FieldConvertCache.fieldCacheMap.containsKey(processKey)) {
      Map<String, String> stringObjectMap = FieldConvertCache.fieldCacheMap.get(processKey);
      //      logger.info("##### processKey:{}, stringObjectMap:{}", processKey, stringObjectMap);
      dataMap.entrySet().stream()
          .filter(entry -> stringObjectMap.containsKey(entry.getKey()))
          .forEach(
              entry -> {
                resultMap.put(stringObjectMap.get(entry.getKey()), entry.getValue());
                if (FieldConvertCache.contactSet.contains(processKey)) {
                  resultMap.put("fileList", attachments);
                }
              });
    }
    return resultMap;
  }
}
