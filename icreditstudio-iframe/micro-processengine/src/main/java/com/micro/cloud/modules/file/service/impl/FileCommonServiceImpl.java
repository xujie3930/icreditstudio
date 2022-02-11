package com.micro.cloud.modules.file.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.micro.cloud.constant.SysErrorCodeConstants;
import com.micro.cloud.exception.ProcessAttachmentException;
import com.micro.cloud.modules.file.convert.FileCommonConvert;
import com.micro.cloud.modules.file.dto.FileInfoDTO;
import com.micro.cloud.modules.file.service.FileCommonService;
import com.micro.cloud.modules.file.service.FilePreviewService;
import com.micro.cloud.modules.file.vo.AttachmentVO;
import com.micro.cloud.modules.form.dal.mapper.FormMapper;
import com.micro.cloud.snowflake.sequence.SequenceService;
import com.micro.cloud.util.file.FileUtils;
import java.io.IOException;
import java.util.Optional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 〈〉
 *
 * @author roy
 * @create 2021/12/8
 * @since 1.0.0
 */
@Service("fileCommonService")
public class FileCommonServiceImpl implements FileCommonService {

  private final Logger logger = LoggerFactory.getLogger(FileCommonServiceImpl.class);

  private final SequenceService sequenceService;

  private final FilePreviewService filePreviewService;

  private final TaskService taskService;

  @Resource private FileUtils fileUtils;

  @Resource
  private FormMapper formMapper;

  public FileCommonServiceImpl(
      SequenceService sequenceService,
      FilePreviewService filePreviewService,
      TaskService taskService) {
    this.sequenceService = sequenceService;
    this.filePreviewService = filePreviewService;
    this.taskService = taskService;
  }

  /**
   * 流程附件上传
   *
   * @param processInstanceId 流程实例id
   * @param activityId 流程节点id
   * @param file 流程附件
   * @return 文件下载地址等信息
   * @exception IOException
   */
  @Transactional(rollbackFor = {Exception.class})
  @Override
  public AttachmentVO upload(
      String processInstanceId, String activityId, MultipartFile file, String type) {
    String fileName = file.getOriginalFilename();
    JSONObject attachmentUrl = new JSONObject();
    // 获取文件后缀
    String suffix = FileUtils.suffixFromFileName(fileName);
    // 文件名替换
    String replacedFileName = sequenceService.nextStringValue(null) + "." + suffix;
    logger.info("fileName:{}", replacedFileName);
    // 根据当前日期创建文件上传目录
    String uploadPath = fileUtils.miniouploadFile(file);
    FileInfoDTO fileInfo = new FileInfoDTO();
    fileInfo.setFilePath(uploadPath);
    fileInfo.setFileName(replacedFileName);
    // 文件转发到预览服务
    String filePreviewUrl = this.filePreviewService.forwardPreviewFile(fileInfo, file);
    attachmentUrl.put("uploadPath", uploadPath);
    attachmentUrl.put("previewUrl", filePreviewUrl);
    logger.info("####### attachmentUrl:{}", attachmentUrl);
    // 流程附件相关数据
    Attachment attachment =
        taskService.createAttachment(
            type, activityId, processInstanceId, fileName, null, attachmentUrl.toJSONString());
    return FileCommonConvert.INSTANCE.convertVO(attachment);
  }

  @Override
  public void download(String id, HttpServletResponse httpResponse) {
    // 获取流程附件
    Attachment attachment = taskService.getAttachment(id);
    String url = attachment.getUrl();
    JSONObject jsonObject = JSONObject.parseObject(url);
    String uploadPath = jsonObject.getString("uploadPath");
    String fileName = attachment.getName();
    // 下载文件
    fileUtils.miniodownloadFile(fileName, uploadPath, httpResponse);
  }

  /**
   * 删除流程附件
   *
   * @param attachmentId 附件id
   * @return 是否成功
   */
  @Override
  public Boolean delete(String attachmentId) {
    // 获取流程附件
    Attachment attachment = taskService.getAttachment(attachmentId);
    attachment =
        Optional.ofNullable(attachment)
            .orElseThrow(
                () ->
                    new ProcessAttachmentException(
                        SysErrorCodeConstants.PROCESS_ATTACHMENT_NOT_EXIST));
    // minio移除文件
    JSONObject attachUrl = JSONObject.parseObject(attachment.getUrl());
    logger.info("##### attachUrl:{}", attachUrl);
    String uploadPath = String.valueOf(attachUrl.get("uploadPath"));
    boolean flag = fileUtils.minioDeleteFile(uploadPath);
    logger.info("============minio文件删除{}================", flag);
    // 数据库附件记录删除
    int i = formMapper.delFileById(attachmentId);
    logger.info("======删除成功{}========",i>0);
//    taskService.deleteAttachment(attachmentId);
    return flag;
  }
}
