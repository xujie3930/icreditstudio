package com.micro.cloud.modules.file.service.impl;

import cn.hutool.json.JSONObject;
import com.micro.cloud.modules.file.dto.FileInfoDTO;
import com.micro.cloud.modules.file.service.FilePreviewService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 * w文件预览实现类
 *
 * @author EDZ
 */
@Service
public class FilePreviewServiceImpl implements FilePreviewService {

  private static final Logger LOGGER = LoggerFactory.getLogger(FilePreviewServiceImpl.class);

  private final RestTemplate restTemplate;

  @Value("${file.view.domain}")
  private String filePreviewHost;

  public FilePreviewServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public String forwardPreviewFile(FileInfoDTO fileInfo, MultipartFile file) {
    try {
      LOGGER.info("Create preview from file: {}", fileInfo.getId());
      // 文件预览服务请求路径
      String fileUploadUrl = this.filePreviewHost + "/fileUpload";
      // 设置请求头
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);
      // 设置请求体，注意是LinkedMultiValueMap
      MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
      ByteArrayResource resource =
          new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
              return file.getOriginalFilename();
            }
          };
      form.add("file", resource);
      // 用HttpEntity封装整个请求报文
      HttpEntity<MultiValueMap<String, Object>> req = new HttpEntity<>(form, headers);
      JSONObject result = this.restTemplate.postForObject(fileUploadUrl, req, JSONObject.class);
      return this.filePreviewHost
          + "/onlinePreview?url="
          + Base64Utils.encodeToString(
              (this.filePreviewHost + result.get("content")).getBytes(StandardCharsets.UTF_8));
    } catch (RestClientException | IOException e) {
      LOGGER.error("Failed to generate preview.", e);
      return null;
    }
  }
}
