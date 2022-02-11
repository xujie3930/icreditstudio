package com.micro.cloud.modules.file.convert;

import com.alibaba.fastjson.JSONObject;
import com.micro.cloud.modules.file.vo.AttachmentVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.camunda.bpm.engine.task.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 〈文件通用转换〉
 *
 * @author roy
 * @create 2021/12/8
 * @since 1.0.0
 */
@Mapper
public interface FileCommonConvert {

  FileCommonConvert INSTANCE = Mappers.getMapper(FileCommonConvert.class);

  /**
   * 流程附件视图对象转换
   *
   * @param attachment 流程附件
   * @return 视图对象
   */
  default AttachmentVO convertVO(Attachment attachment) {
    AttachmentVO attachmentVO = new AttachmentVO();
    Optional.ofNullable(attachment)
        .ifPresent(
            attach -> {
              attachmentVO.setId(attach.getId());
              attachmentVO.setName(attach.getName());
              attachmentVO.setType(attach.getType());
              JSONObject attachUrl = JSONObject.parseObject(attach.getUrl());
              attachmentVO.setPreviewUrl(String.valueOf(attachUrl.get("previewUrl")));
            });
    return attachmentVO;
  }

  /**
   * 附件列表转换
   *
   * @param attachments 附件列表
   * @return 附件视图对象列表
   */
  default List<AttachmentVO> convertAttachments(List<Attachment> attachments) {
    List<AttachmentVO> result = new ArrayList<>();
    Optional.ofNullable(attachments)
        .ifPresent(
            files -> {
              files.stream()
                  .forEach(
                      file -> {
                        AttachmentVO attachmentVO = convertVO(file);
                        result.add(attachmentVO);
                      });
            });
    return result;
  }
}
