package com.micro.cloud.modules.file.service;

import com.micro.cloud.modules.file.vo.AttachmentVO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 〈文件通用服务接口〉
 *
 * @author roy
 * @create 2021/12/8
 * @since 1.0.0
 */
public interface FileCommonService {

  /**
   * 流程附件上传
   *
   * @param processInstanceId 流程实例id
   * @param taskId 流程节点id
   * @param type 文件类型
   * @param attachment 流程附件
   * @return 文件下载地址等信息
   * @exception IOException
   */
  AttachmentVO upload(
      String processInstanceId, String activityId, MultipartFile attachment, String type)
      throws IOException;

  /**
   * 流程附件下载
   * @param id
   * @return
   * @throws IOException
   */
  void download(String id, HttpServletResponse response) throws IOException;

  /**
   * 删除流程附件
   *
   * @param attachmentId 附件id
   * @return 是否成功
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean delete(String attachmentId);
}
