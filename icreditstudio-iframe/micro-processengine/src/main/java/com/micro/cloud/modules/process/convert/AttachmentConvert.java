package com.micro.cloud.modules.process.convert;

import com.micro.cloud.modules.file.vo.AttachmentVO;
import com.micro.cloud.modules.process.result.CallbackAttachmentResult;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 〈附件数据转换〉
 *
 * @author roy
 * @create 2021/12/20
 * @since 1.0.0
 */
@Mapper
public interface AttachmentConvert {

  AttachmentConvert INSTANCE = Mappers.getMapper(AttachmentConvert.class);

  /**
   * 附件回调结果转换
   *
   * @param attachmentVO 附件信息
   * @return 回调结果
   */
  @Mappings({
    @Mapping(source = "id", target = "bizId"),
    @Mapping(source = "name", target = "fileName"),
    @Mapping(source = "previewUrl", target = "filePreviewUrl")
  })
  CallbackAttachmentResult convertCallback(AttachmentVO attachmentVO);

  /**
   * 批量转换
   *
   * @param attachmentList 附件列表
   * @return 回调附件列表
   */
  List<CallbackAttachmentResult> convertCallbackList(List<AttachmentVO> attachmentList);
}
