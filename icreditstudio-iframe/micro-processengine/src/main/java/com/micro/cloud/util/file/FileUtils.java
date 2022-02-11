package com.micro.cloud.util.file;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;

import io.minio.MinioClient;
import org.overviewproject.mime_types.MimeTypeDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件通用类
 *
 * @author daiqingsong @Date 2020/6/21
 */
@Component
public class FileUtils {

  @Value("${minio.endpoint}")
  private String ENDPOINT;

  @Value("${minio.bucketName}")
  private String BUCKETNAME;

  @Value("${minio.accessKey}")
  private String ACCESSKEY;

  @Value("${minio.secretKey}")
  private String SECRETKEY;

  private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

  private static final MimeTypeDetector MIME_TYPE_DETECTOR = new MimeTypeDetector();

  /** 用户下载功能，下载Excel */
  public static void download(String filename, InputStream in, HttpServletResponse response)
      throws Exception {}

  /** 文件上传 */
  public static void uploadFile(String fileName, String path, MultipartFile file) {
    // Check for Unix-style path
    int unixSep = fileName.lastIndexOf('/');
    // Check for Windows-style path
    int winSep = fileName.lastIndexOf('\\');
    // Cut off at latest possible point
    int pos = (Math.max(winSep, unixSep));
    if (pos != -1) {
      fileName = fileName.substring(pos + 1);
    }
    File outFile = new File(path + File.separator);
    if (!outFile.exists() && !outFile.mkdirs()) {
      logger.error("创建文件夹【{}】失败，请检查目录权限！", path);
    }
    logger.info("上传文件：{}", outFile + fileName);
    try (InputStream in = file.getInputStream();
        OutputStream out = new FileOutputStream(outFile + "/" + fileName)) {
      StreamUtils.copy(in, out);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("文件上传失败", e);
    }
  }

  /**
   * miniox上传文件
   *
   * @param
   * @param
   */
  public String miniouploadFile(MultipartFile file) {
    String s = null;
    try {
      MinioClient minioClient = new MinioClient(ENDPOINT, ACCESSKEY, SECRETKEY);
      // 存入bucket不存在则创建，并设置为只读
      if (!minioClient.bucketExists(BUCKETNAME)) {
        minioClient.makeBucket(BUCKETNAME);
        minioClient.setBucketPolicy(BUCKETNAME, "*.*");
      }
      String filename = file.getOriginalFilename();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      // 文件存储的目录结构
      String objectName = sdf.format(new Date()) + "/" + filename;
      // 存储文件
      String contentType = file.getContentType();
      //            minioClient.putObject(BUCKETNAME,objectName,filename,null);
      minioClient.putObject(BUCKETNAME, objectName, file.getInputStream(), file.getContentType());
      logger.info("文件上传成功!");
      //            s=ENDPOINT + "/" + BUCKETNAME + "/" + objectName;
      s = objectName;
    } catch (Exception e) {
      e.printStackTrace();
      logger.info("上传发生错误: {}！", e.getMessage());
    }
    return s;
  }

  /**
   * miniox下载文件
   *
   * @param fileName
   * @param httpResponse
   */
  public void miniodownloadFile(
      String fileName, String uploadPath, HttpServletResponse httpResponse) {
    try {
      MinioClient minioClient = new MinioClient(ENDPOINT, ACCESSKEY, SECRETKEY);
      InputStream object = minioClient.getObject(BUCKETNAME, uploadPath);
      byte buf[] = new byte[1024];
      int length = 0;
      httpResponse.reset();

      httpResponse.setHeader(
          "Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
      httpResponse.setContentType("application/octet-stream");
      httpResponse.setCharacterEncoding("utf-8");
      OutputStream outputStream = httpResponse.getOutputStream();
      while ((length = object.read(buf)) > 0) {
        outputStream.write(buf, 0, length);
      }
      outputStream.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      logger.info("导出失败：", ex.getMessage());
    }
  }

  // 文件删除
  public boolean minioDeleteFile(String name) {
    try {
      MinioClient minioClient = new MinioClient(ENDPOINT, ACCESSKEY, SECRETKEY);
      minioClient.removeObject(BUCKETNAME, name);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return false;
    }
    return true;
  }

  /**
   * 文件后缀截取
   *
   * @param fileName 文件名称
   * @return 文件后缀名
   */
  public static String suffixFromFileName(String fileName) {
    return fileName.substring(fileName.lastIndexOf(".") + 1);
  }
}
