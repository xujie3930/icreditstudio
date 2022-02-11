package com.micro.cloud.modules.file.service;

import com.micro.cloud.modules.file.dto.FileInfoDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件预览服务
 * @author EDZ
 */
public interface FilePreviewService {
    /**
     * 文件转发至预览服务
     * @param fileInfo 文件信息
     * @return
     */
    String forwardPreviewFile(FileInfoDTO fileInfo, MultipartFile file);


}
