package com.micro.cloud.util.validate;

import cn.hutool.core.util.StrUtil;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

/**
 * 校验工具类
 *
 * @author roy
 */
public class ValidationUtils {

  private static Pattern PATTERN_URL =
      Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

  private static Pattern PHONE =
      Pattern.compile("/^1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$/");

  public static boolean isMobile(String mobile) {
    if (StrUtil.length(mobile) != 11) {
      return false;
    }
    //
    return true;
  }

  public static boolean isURL(String url) {
    return StringUtils.hasText(url) && PATTERN_URL.matcher(url).matches();
  }
}
