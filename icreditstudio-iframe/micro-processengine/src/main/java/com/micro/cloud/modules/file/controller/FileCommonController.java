package com.micro.cloud.modules.file.controller;

import com.micro.cloud.api.CommonResult;
import com.micro.cloud.modules.file.service.FileCommonService;
import com.micro.cloud.modules.file.vo.AttachmentVO;
import com.micro.cloud.util.file.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 〈文件上传通用接口〉
 *
 * @author roy
 * @create 2021/12/7
 * @since 1.0.0
 */
@Api(tags = {"文件通用接口"})
@RestController
@RequestMapping("/process/attachment")
public class FileCommonController {

  private final FileCommonService fileCommonService;

  public FileCommonController(FileCommonService fileCommonService) {
    this.fileCommonService = fileCommonService;
  }

  @Resource private FileUtils fileUtils;

  @ApiOperation("上传流程附件")
  @PostMapping(value = "/upload")
  public CommonResult<AttachmentVO> upload(
      @RequestParam(value = "processInstanceId") String processInstanceId,
      @RequestParam(value = "activityId") String activiteId,
      @RequestParam(value = "type") String type,
      @RequestParam(value = "file") MultipartFile file) {
    try {
      AttachmentVO result = fileCommonService.upload(processInstanceId, activiteId, file, type);
      return CommonResult.success(result);
    } catch (IOException e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }

  @ApiOperation("流程附件下载")
  @GetMapping(value = "/download/{id}")
  public void download(@PathVariable("id") String id, HttpServletResponse response) {
    try {
      fileCommonService.download(id, response);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @ApiOperation("删除流程附件")
  @GetMapping(value = "/delete/{attachmentId}")
  public CommonResult<Boolean> delete(@PathVariable String attachmentId) {
    try {
      Boolean result = fileCommonService.delete(attachmentId);
      return CommonResult.success(result);
    } catch (Exception e) {
      e.printStackTrace();
      return CommonResult.failed(e.getMessage());
    }
  }
}
