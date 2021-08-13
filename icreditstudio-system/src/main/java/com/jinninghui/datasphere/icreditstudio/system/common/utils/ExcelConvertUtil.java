package com.jinninghui.datasphere.icreditstudio.system.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * excel字段转换工具类
 *
 * @author EDZ
 */
public class ExcelConvertUtil {

  public static final String BIRTH_PATTERN = "^\\d{4}-\\d{1,2}-\\d{1,2}";


  public static boolean userBirthExamine(String birth) {
    if (StringUtils.isNotBlank(birth)) {
      Pattern regexp = Pattern.compile(BIRTH_PATTERN);
      if (regexp.matcher(birth).matches()) {
        return true;
      }
    }
    return false;
  }
}
