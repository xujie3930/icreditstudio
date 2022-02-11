package com.micro.cloud.util.servlet;

import cn.hutool.core.io.IoUtil;
import cn.hutool.extra.servlet.ServletUtil;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 客户端工具类
 *
 * @author roy
 */
public class ServletUtils {

  /**
   * @param request 请求
   * @return ua
   */
  public static String getUserAgent(HttpServletRequest request) {
    String ua = request.getHeader("User-Agent");
    return ua != null ? ua : "";
  }

  /**
   * 获得请求
   *
   * @return HttpServletRequest
   */
  public static HttpServletRequest getRequest() {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (!(requestAttributes instanceof ServletRequestAttributes)) {
      return null;
    }
    return ((ServletRequestAttributes) requestAttributes).getRequest();
  }

  public static String getUserAgent() {
    HttpServletRequest request = getRequest();
    if (request == null) {
      return null;
    }
    return getUserAgent(request);
  }

  /**
   * 获取客户端IP
   *
   * @return 获取客户端IP
   */
  public static String getClientIP() {
    HttpServletRequest request = getRequest();
    if (request == null) {
      return null;
    }
    return ServletUtil.getClientIP(request);
  }
  /**
   * 返回附件
   *
   * @param response 响应
   * @param filename 文件名
   * @param content 附件内容
   * @throws IOException
   */
  public static void writeAttachment(HttpServletResponse response, String filename, byte[] content)
      throws IOException {
    // 设置 header 和 contentType
    response.setHeader(
        "Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
    response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
    // 输出附件
    IoUtil.write(response.getOutputStream(), false, content);
  }
}
