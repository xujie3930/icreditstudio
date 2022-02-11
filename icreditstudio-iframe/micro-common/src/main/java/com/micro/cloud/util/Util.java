package com.micro.cloud.util;

import com.micro.cloud.common.core.chain.Chain;
import com.micro.cloud.exception.ChainException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** 工具类 added by xulei 2021.10.13 */
public class Util {
  private static final Logger logger = LoggerFactory.getLogger(Util.class);

  public static final String YEAR_MON_DAY_HOUR_MIN_SEC_FORMAT = "yyyy-MM-dd HH:mm:ss";

  public static final String YEAR_MON_DAY_HOUR_MIN_SEC_CHN_FORMAT = "yyyy年MM月dd日HH时mm分ss秒";

  public static final String YEAR_MON_DAY_FORMAT = "yyyy-MM-dd";

  public static final String YEAR_MON_DAY_CHN_FORMAT = "yyyy年MM月dd日";

  public static final String YEAR_MON_FORMAT = "yyyy-MM";

  public static final String YEAR_MON_CHN_FORMAT = "yyyy年MM月";

  public static final String YEAR_FORMAT = "yyyy";

  public static final String YEAR_CHN_FORMAT = "yyyy年";

  /**
   * 将字符串当中的特殊字符 Added by xulei 2021.11.24
   *
   * @param parStr
   * @return
   */
  public static String escapeStr(String parStr) {
    String regEx = "[`_~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
    Pattern p = Pattern.compile(regEx);
    Matcher m1 = p.matcher(parStr);
    if (m1.find()) {
      CharSequence cs = parStr;
      int j = 0;
      for (int i = 0; i < cs.length(); i++) {
        String temp = String.valueOf(cs.charAt(i));
        Matcher m2 = p.matcher(temp);
        if (m2.find()) {
          StringBuilder sb = new StringBuilder(parStr);
          parStr = sb.insert(j, "\\").toString();
          j++;
        }
        j++; // 转义完成后str的长度增1
      }

      return parStr;
    }
    return parStr;
  }

  /** 字符合并 */
  public static String append(Object... arr) {
    StringBuilder sb = new StringBuilder();
    for (Object obj : arr) {
      sb.append(obj);
    }
    return sb.toString();
  }

  /** 将List元素拼装成逗号分隔的字符串 */
  public static String list2String(List<String> arr) {
    StringBuilder sb = new StringBuilder();
    for (Object obj : arr) {
      sb.append(obj).append(",");
    }
    return sb.deleteCharAt(sb.length() - 1).toString();
  }

  /**
   * 获取对象的字节个数.
   *
   * @param obj
   * @return
   * @author xulei
   * @date 2013-6-25 上午11:26:17
   */
  public static int getUtf8BytesLength(Object obj) {
    try {
      if (obj instanceof String) {
        return ((String) obj).getBytes("UTF-8").length;
      }

      return String.valueOf(obj).getBytes("UTF-8").length;
    } catch (Exception e) {
      if (obj instanceof String) {
        return ((String) obj).getBytes().length;
      }

      return String.valueOf(obj).getBytes().length;
    }
  }

  /**
   * 按UTF-8编码的字符串，截取指定长度的字符串
   *
   * @param src 按UTF-8编码的字符串
   * @param count 指定长度
   * @return 返回截取的字符串
   * @author xulei
   * @date 2013-7-16 上午10:06:21
   */
  public static String subUTF8String(String src, int count) {
    if (src == null || src.length() <= 0) {
      return src;
    }

    int len = getUtf8BytesLength(src);
    if (len <= count) {
      return src;
    }

    StringBuffer buff = new StringBuffer();
    char c;
    int idx = 0;
    int cLen = 0;
    for (int pos = count; pos > 0; ) {
      c = src.charAt(idx);
      cLen = getUtf8BytesLength(c);
      pos = pos - cLen;
      if (pos >= 0) {
        buff.append(c);
        idx++;
      }
    }

    return buff.toString();
  }

  public static boolean needPager(Long startRow, Long maxSize) {
    return !notNeedPager(startRow, maxSize);
  }

  public static boolean notNeedPager(Long startRow, Long maxSize) {
    return startRow == null
        || maxSize == null
        || maxSize.longValue() <= 0
        || startRow.longValue() < 0;
  }

  /**
   * 判断double类型数字是否为0
   *
   * @param d
   * @return
   */
  public static boolean isDoubleZero(Double d) {
    BigDecimal zero = new BigDecimal(0.0D);
    BigDecimal src = new BigDecimal(d);
    return src.compareTo(zero) == 0;
  }

  /**
   * 格式化双精度的数字
   *
   * @param position
   * @param formatStr
   * @return Added by xulei 2013.03.14
   */
  public static String formatPosition(double position, String formatStr) {
    DecimalFormat df = new DecimalFormat(formatStr);
    return df.format(position);
  }

  /**
   * 判断对象值是否为空： 若对象为字符串，判断对象值是否为null或空格; 若对象为数组，判断对象值是否为null，或数组个数是否为0;
   * 若对象为List。判断对象值是否为null，或List元素是否个数为0; 其他类型对象，只判断值是否为null.
   *
   * @param value
   * @return true
   */
  public static boolean isEmpty(Object value) {
    if (value == null) {
      return true;
    } else if ((value instanceof String) && (((String) value).trim().length() < 1)) {
      return true;
    } else if (value.getClass().isArray()) {
      if (0 == java.lang.reflect.Array.getLength(value)) {
        return true;
      }
    } else if (value instanceof List) {
      if (((List<?>) value).isEmpty()) {
        return true;
      }
    } else if (value instanceof Map) {
      if (((Map<?, ?>) value).isEmpty()) {
        return true;
      }
    } else if (value instanceof Set) {
      if (((Set<?>) value).isEmpty()) {
        return true;
      }
    }
    return false;
  }

  public static boolean isNotEmpty(Object value) {
    return !isEmpty(value);
  }

  /**
   * 将中文字符转换成utf-8格式
   *
   * @param xmlStr
   * @return Added by xulei 2012.02.15
   */
  public static String encodeUTF8(String xmlStr) {
    String str = "";
    try {
      str = URLEncoder.encode(xmlStr, "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return str;
  }

  public static Map<String, Object> convertMap(Map<String, Integer> map) {
    Map<String, Object> target = new HashMap<String, Object>(map.size());
    Set<Map.Entry<String, Integer>> set = map.entrySet();
    for (Map.Entry<String, Integer> e : set) {
      target.put(e.getKey(), e.getValue());
    }

    return target;
  }

  /**
   * 将utf-8格式字符转换成中文字符
   *
   * @param str
   * @return Added by xulei 2012.02.15
   */
  public static String decodeUTF8(String str) {
    String xmlStr = "";
    try {
      xmlStr = URLDecoder.decode(str, "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return xmlStr;
  }

  /**
   * 1.全角：指一个字符占用两个标准字符位置。汉字字符和规定了全角的英文字符及国标GB2312-80中的图形符号和特殊字符都是全角字符。
   * 一般的系统命令是不用全角字符的，只是在作文字处理时才会使用全角字符。 2.半角：指一字符占用一个标准的字符位置。通常的英文字母、数字键、符号键都是半角的
   * ，半角的显示内码都是一个字节。在系统内部，以上三种字符是作为基本代码处理的，所以用户输入命令和参数时一般都使用半角。
   * 3.全角与半角在计算机中的表示：据我所知，全角的第一个字节是163（我用-93），然后第二个字节与半角相差128。全角空格和半角空格也要考虑进去。
   *
   * <p>半角转全角
   *
   * @param input String.
   * @return 全角字符串. Added by xulei 2011.06.08
   */
  public static String ToSBC(String input) {
    char c[] = input.toCharArray();
    for (int i = 0; i < c.length; i++) {
      if (c[i] == ' ') {
        c[i] = '\u3000'; // 采用十六进制,相当于十进制的12288

      } else if (c[i] < '\177') { // 采用八进制,相当于十进制的127
        c[i] = (char) (c[i] + 65248);
      }
    }
    return new String(c);
  }

  /**
   * 全角转半角
   *
   * @param input String.
   * @return 半角字符串 Added by xulei 2011.06.08
   */
  public static String ToDBC(String input) {

    char c[] = input.toCharArray();
    for (int i = 0; i < c.length; i++) {
      if (c[i] == '\u3000') {
        c[i] = ' ';
      } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
        c[i] = (char) (c[i] - 65248);
      }
    }
    String returnString = new String(c);

    return returnString;
  }

  /**
   * Object转换为float，失败返回0
   *
   * @param o
   * @return
   */
  public static float toFloat(Object o) {
    try {
      return Float.parseFloat(String.valueOf(o));
    } catch (Exception e) {
      return 0;
    }
  }

  /**
   * Object转换为double，失败返回0
   *
   * @param o
   * @return
   */
  public static double toDouble(Object o) {
    try {
      return Double.parseDouble(String.valueOf(o));
    } catch (Exception e) {
      return 0;
    }
  }

  /**
   * Object转换为整形，失败返回0
   *
   * @param o
   * @return
   */
  public static int toInteger(Object o) {
    return toInteger(o, 0);
  }

  /**
   * Object转换为整形
   *
   * @param o
   * @param def 转换失败默认值
   * @return
   */
  public static int toInteger(Object o, int def) {
    try {
      if (o == null) {
        return def;
      }

      String typeName = o.getClass().getName();
      if ("int".equals(typeName) || Integer.class.getName().equals(typeName)) {
        return (Integer) o;
      }

      if (BigDecimal.class.getName().equals(typeName)) {
        return ((BigDecimal) o).intValue();
      }

      return new BigDecimal(o.toString()).intValue();
    } catch (Exception e) {
      return def;
    }
  }

  /**
   * Object转换为长整形，失败返回0
   *
   * @param o
   * @return
   */
  public static long toLong(Object o) {
    return toLong(o, 0L);
  }

  public static long toLong(Object o, Long defaultValue) {
    try {
      if (o == null) {
        return defaultValue;
      }

      String typeName = o.getClass().getName();
      if ("long".equals(typeName) || Long.class.getName().equals(typeName)) {
        return (Long) o;
      }

      if (BigDecimal.class.getName().equals(typeName)) {
        return ((BigDecimal) o).longValue();
      }

      return new BigDecimal(o.toString()).longValue();
    } catch (Exception e) {
      return 0L;
    }
  }

  /**
   * Object转换为短整形，失败返回0
   *
   * @param o
   * @return
   */
  public static Short toShort(Object o) {
    try {
      if (o == null) {
        return 0;
      }

      String typeName = o.getClass().getName();
      if ("short".equals(typeName) || Short.class.getName().equals(typeName)) {
        return (Short) o;
      }

      if (BigDecimal.class.getName().equals(typeName)) {
        return ((BigDecimal) o).shortValue();
      }

      return new BigDecimal(o.toString()).shortValue();
    } catch (Exception e) {
      return 0;
    }
  }

  /**
   * 调用当前linux系统中的杀进程重启TOMCAT脚本
   *
   * @param command
   * @return Added by xulei 2011.01.27
   */
  public static boolean exeShell(String command) {
    Runtime rt = Runtime.getRuntime();
    try {
      Process p = rt.exec(command);
      if (p.waitFor() != 0) return false;
    } catch (IOException e) {
      System.err.println("没有找到Tomcat重启killtomcatprocess.sh脚本");
      return false;
    } catch (InterruptedException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Html编码
   *
   * @param s
   * @return
   */
  public static String encodeHTML(String s) {
    StringBuffer out = new StringBuffer();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c > 127 || c == '"' || c == '<' || c == '>') {
        out.append("&#" + (int) c + ";");
      } else {
        out.append(c);
      }
    }
    return out.toString();
  }

  /**
   * Html解码
   *
   * @return
   */
  public static String decodeHtml(String str) {
    StringBuffer buf = new StringBuffer(str.length());
    int i;
    for (i = 0; i < str.length(); ++i) {
      char ch = str.charAt(i);
      if (ch == '&') {
        int semi = str.indexOf(';', i + 1);
        if (semi == -1) {
          buf.append(ch);
          continue;
        }
        String entityName = str.substring(i + 1, semi);
        int entityValue = -1;
        if (entityName.charAt(0) == '#') {
          entityValue = Integer.parseInt(entityName.substring(1));
        }
        if (entityValue == -1) {
          buf.append('&');
          buf.append(entityName);
          buf.append(';');
        } else {
          buf.append((char) (entityValue));
        }
        i = semi;
      } else {
        buf.append(ch);
      }
    }
    return buf.toString();
  }

  /**
   * 取得jar包里的包名
   *
   * @param f 要读取的jar包
   * @return 返回包名的Set集合
   */
  public static Set<String> readPackage(File f) {
    Set<String> dirs = new HashSet<String>();
    ZipFile zf = null;
    String name = null;
    String temp[] = null;
    try {
      zf = new ZipFile(f);
      Enumeration<? extends ZipEntry> e = zf.entries();
      while (e.hasMoreElements()) {
        ZipEntry ze = e.nextElement();
        name = ze.getName();
        temp = name.split("/");
        if (temp.length > 1 && !temp[0].trim().equalsIgnoreCase("META-INF"))
          dirs.add(name.substring(0, name.lastIndexOf("/")));
      }
    } catch (ZipException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return dirs;
  }

  /**
   * 取指定包名下面的所有类名
   *
   * @param f zip压缩包
   * @param packageName 包名
   * @return
   */
  public static Set<String> readClassNames(File f, String packageName) {
    Set<String> classes = new HashSet<String>();
    ZipFile zf = null;
    String name = null;
    String temp = null;
    try {
      zf = new ZipFile(f);
      Enumeration<? extends ZipEntry> e = zf.entries();
      while (e.hasMoreElements()) {
        ZipEntry ze = e.nextElement();
        name = ze.getName();
        if (name.split("/").length > 1
            && name.substring(0, name.lastIndexOf("/")).equals(packageName)
            && name.indexOf(".class") != -1) {
          temp = name.substring(name.lastIndexOf("/") + 1, name.lastIndexOf("."));
          classes.add(temp);
        }
      }
    } catch (ZipException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return classes;
  }

  /**
   * 判断两个字符串值是否相同，不区分大小写
   *
   * @param str1
   * @param str2
   * @return
   */
  public static boolean isStrsEqual(String str1, String str2) {
    if (isEmpty(str1) && isEmpty(str2)) {
      return true;
    }

    if (!isEmpty(str1) && !isEmpty(str2)) {
      return str1.equalsIgnoreCase(str2);
    }

    return false;
  }

  /**
   * 根据元素的自然顺序对指定Set中的元素按升序进行排序。Set中的所有元素都必须实现 Comparable 接口。 此外，列表中的所有元素都必须是可相互比较的（也就是说，对于列表中的任何 e1
   * 和 e2 元素，e1.compareTo(e2) 不得抛出 ClassCastException）。 此排序方法具有稳定性：不会因调用 sort 方法而对相等的元素进行重新排序。
   * 由于参数Set可能是不可更改的，因此将Set里的元素排序后，存放到List中，并将该List作为方法的返回值。
   *
   * @param set 要排序的Set
   * @return 排序后的元素列表
   */
  public static <T extends Comparable<? super T>> List<T> sort(Set<T> set) {
    Object[] a = set.toArray();
    Arrays.sort(a);
    List<T> list = new ArrayList<T>(set.size());
    for (int j = 0; j < a.length; j++) {
      list.add((T) a[j]);
    }

    return list;
  }

  /**
   * 身份证号码自动转换，15位转成18位，18位转成15位
   *
   * @param idNum
   * @return
   */
  public static String convertIdNum18or15(String idNum) {
    if (idNum != null && idNum.length() == 15) {
      String[] strJiaoYang = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2", "1"};
      int[] intQuan = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
      int intTemp = 0;
      String tempidNum = idNum.substring(0, 6) + "19" + idNum.substring(6);
      for (int i = 0; i < tempidNum.length(); i++) {
        intTemp = intTemp + Integer.parseInt(tempidNum.substring(i, i + 1)) * intQuan[i];
      }
      intTemp = intTemp % 11;
      idNum = tempidNum + strJiaoYang[intTemp];
    } else if (idNum != null && idNum.length() == 18) {
      idNum = idNum.substring(0, 17);
      idNum = idNum.substring(0, 6) + idNum.substring(8);
    }

    return idNum;
  }

  /**
   * 获取oracle的分页查询SQL. 当startRow小于0或者pageSize小于等于0时不做分页处理，返回原始SQL语句
   *
   * @param sql 未分页的原始查询SQL
   * @param startRow 起始行
   * @param pageSize 每页最大行数
   * @return
   */
  public static String getOraclePagingSql(String sql, int startRow, int pageSize) {
    if (startRow < 0 || pageSize <= 0) {
      return sql;
    }
    StringBuffer sqlStr = new StringBuffer(128);
    sqlStr.append("SELECT tab1.* from (");
    sqlStr.append("SELECT tab2.*, rownum numbers from (");
    sqlStr.append(sql);
    sqlStr.append(") tab2 where rownum <=");
    sqlStr.append(pageSize + startRow);
    sqlStr.append(") tab1  where tab1.numbers >");
    sqlStr.append(startRow);

    return sqlStr.toString();
  }

  /**
   * 对日期进行转换
   *
   * @param dateStr Added by xulei 2010.12.31
   * @return
   * @throws ParseException
   * @throws Exception
   */
  public static String dateFormatNew(String dateStr) throws ParseException {
    if (dateStr == null || dateStr.length() <= 0) {
      return "";
    }
    String srcFormat = "";
    String targetFormat = "";
    dateStr = dateStr.trim();
    if (dateStr.length() == 14) {
      srcFormat = "yyyyMMddHHmmss";
      targetFormat = "yyyy年MM月dd日HH时mm分ss秒";
    } else if (dateStr.length() == 12) {
      srcFormat = "yyyyMMddHHmm";
      targetFormat = "yyyy年MM月dd日HH时mm分";
    } else if (dateStr.length() == 10) {
      srcFormat = "yyyyMMddHH";
      targetFormat = "yyyy年MM月dd日HH时";
    } else if (dateStr.length() == 8) {
      srcFormat = "yyyyMMdd";
      targetFormat = "yyyy年MM月dd日";
    } else if (dateStr.length() == 6) {
      srcFormat = "yyyyMM";
      targetFormat = "yyyy年MM月";
    } else if (dateStr.length() == 4) {
      srcFormat = "yyyy";
      targetFormat = "yyyy年";
    } else {
      return "数据库该日期数据格式错误.";
    }

    SimpleDateFormat sdf = new SimpleDateFormat(srcFormat);
    SimpleDateFormat sdf2 = new SimpleDateFormat(targetFormat);
    Date date = sdf.parse(dateStr);
    return sdf2.format(date);
  }

  /**
   * 将DATE类型数据转换成指定格式的字符串数据
   *
   * @param srcDate
   * @param srcFormat
   * @return Added by xulei 2011.11.10
   */
  public static String dateToString(Date srcDate, String srcFormat) {
    DateFormat df = new SimpleDateFormat(srcFormat);
    return df.format(srcDate);
  }

  /**
   * 将String类型数据转换成指定格式的date数据
   *
   * @param dateStr
   * @param srcFormat
   * @return Added by xulei 2011.11.16
   * @throws ParseException
   */
  public static Date stringToDate(String dateStr, String srcFormat) throws ParseException {
    DateFormat df = new SimpleDateFormat(srcFormat);
    return df.parse(dateStr);
  }

  /**
   * 对日期进行转换
   *
   * @param srcFormat 源数据的日期格式
   * @param targetFormat 目标数据的日期格式
   * @param dateStr Added by xulei 2010.12.31
   * @return
   * @throws ParseException
   * @throws Exception
   */
  public static String dateFormat(String srcFormat, String targetFormat, String dateStr)
      throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(srcFormat);
    SimpleDateFormat sdf2 = new SimpleDateFormat(targetFormat);
    Date date = sdf.parse(dateStr);
    return sdf2.format(date);
  }

  /**
   * 对日期进行转换,去-补0到14位
   *
   * @author xiongwenhao 2010.12.25
   * @param date 日期格式 例：2008-08-08
   * @return
   */
  public static String dateFormat(String date) {
    if (date != null && !"".equals(date)) {
      String temp = date.replaceAll("-", "");
      if (temp.length() < 14) {
        int j = 14 - temp.length();
        for (int i = 0; i < j; i++) {
          temp = temp + "0";
        }
      }
      return temp;
    }
    return date;
  }

  /**
   * 对日期进行转换,去-补235959到14位
   *
   * @author xiongwenhao 2010.12.25
   * @param date 日期格式 例：2008-08-08
   * @return
   */
  public static String dateFormatEnd(String date) {
    if (date != null && !"".equals(date)) {
      String temp = date.replaceAll("-", "");
      if (temp.length() == 8) {
        temp = temp + "235959";
      }
      return temp;
    }
    return date;
  }

  /**
   * 向指定URL的servlet服务发送请求，并接受返回的结果
   *
   * @param url
   * @param param
   * @return
   * @throws Exception
   */
  public static String getServletResponse(String url, String param) throws Exception {
    OutputStream out = null;
    InputStream in = null;
    ByteArrayOutputStream bos = null;
    HttpURLConnection con = null;
    try {
      URL u = new URL(url);
      con = (HttpURLConnection) u.openConnection();
      con.setRequestMethod("POST");
      con.setDoInput(true);
      con.setDoOutput(true);
      if (param != null && param.length() > 0) {
        out = con.getOutputStream();
        out.write(param.getBytes());
        out.flush();
      }
      in = con.getInputStream();
      int length = 8192;
      byte[] data = new byte[length];
      bos = new ByteArrayOutputStream();
      while ((length = in.read(data, 0, length)) != -1) {
        bos.write(data, 0, length);
      }
      String p = bos.toString();
      return p;
    } catch (Exception e) {
      throw e;
    } finally {
      safeClose(bos);
      safeClose(in);
      safeClose(out);
    }
  }

  public static byte[] getServletResponseByte(String url, String param) throws Exception {
    OutputStream out = null;
    InputStream in = null;
    ByteArrayOutputStream bos = null;
    HttpURLConnection con = null;
    try {
      URL u = new URL(url);
      con = (HttpURLConnection) u.openConnection();
      con.setRequestMethod("POST");
      con.setDoInput(true);
      con.setDoOutput(true);
      if (param != null && param.length() > 0) {
        out = con.getOutputStream();
        out.write(param.getBytes());
        out.flush();
      }
      in = con.getInputStream();
      int length = 8192;
      byte[] data = new byte[length];
      bos = new ByteArrayOutputStream();
      while ((length = in.read(data, 0, length)) != -1) {
        bos.write(data, 0, length);
      }
      return bos.toByteArray();
    } catch (Exception e) {
      throw e;
    } finally {
      safeClose(bos);
      safeClose(in);
      safeClose(out);
    }
  }

  public static void safeClose(OutputStream os) {
    try {
      if (os != null) os.close();
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  public static void safeClose(InputStream in) {
    try {
      if (in != null) in.close();
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  public static void safeClose(ZipFile zf) {
    try {
      if (zf != null) zf.close();
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * 将yyyymmddhhmmss格式日期转成yyyy-mm-dd hh:mm:ss 或将yyyymmddhhmm格式日期转成yyyy-mm-dd hh:mm
   *
   * @param dateStr
   * @return
   * @throws Exception
   */
  public static String formateDateString(String dateStr) throws Exception {
    if (dateStr == null || dateStr.length() == 0) return "";
    try {
      String styleBefore = "yyyyMMddHHmmss";
      String styleAfter = "yyyy-MM-dd HH:mm:ss";
      if (dateStr.length() == 12) {
        styleBefore = "yyyyMMddHHmm";
        styleAfter = "yyyy-MM-dd HH:mm";
      }
      SimpleDateFormat sdf = new SimpleDateFormat(styleBefore);
      Date date = sdf.parse(dateStr);
      sdf.applyPattern(styleAfter);
      String d = sdf.format(date);
      return d;
    } catch (Exception e) {
      return dateStr;
    }
  }

  /**
   * 去除xml字符串中多余的空格
   *
   * @param xmlStr
   * @return
   */
  public static String formatStringFromService(String xmlStr) {
    if (xmlStr != null && xmlStr.length() > 0) {
      xmlStr = xmlStr.replaceAll("\n", "");
      xmlStr = xmlStr.replaceAll("\r", "");
      xmlStr = xmlStr.replaceAll("\t", "");
      if (xmlStr.indexOf("?>") != -1) {
        xmlStr = xmlStr.substring(xmlStr.indexOf("?>") + 2);
      }
      String leaveStr = xmlStr;
      StringBuffer sb = new StringBuffer();
      int index = 0;
      do {
        index = leaveStr.indexOf(">");
        if (index != -1 && index != leaveStr.length() - 1) {
          sb.append(leaveStr.substring(0, index + 1));
          leaveStr = leaveStr.substring(index + 1);
          if (leaveStr.indexOf("<") != -1) {
            String content = leaveStr.substring(0, leaveStr.indexOf("<"));
            if (content.trim().length() > 0) sb.append(content);
            leaveStr = leaveStr.substring(leaveStr.indexOf("<"));
          } else {
            break;
          }
        } else {
          if (index == leaveStr.length() - 1) sb.append(leaveStr);
          break;
        }
      } while (leaveStr.length() > 0);
      return sb.toString();
    } else {
      return xmlStr;
    }
  }

  /**
   * 向输出流中写入数据
   *
   * @param out
   * @param responseXml
   * @throws Exception
   */
  public static void sendResponseXml(OutputStream out, String responseXml) throws Exception {
    try {
      out.write(responseXml.getBytes());
    } catch (Exception e) {
      throw e;
    } finally {
      safeClose(out);
    }
  }

  public static void sendResponseXml(OutputStream out, byte[] datas) throws Exception {
    try {
      out.write(datas);
    } catch (Exception e) {
      throw e;
    } finally {
      safeClose(out);
    }
  }

  /**
   * 向输入流里写入照片
   *
   * @param out
   * @param image
   * @throws Exception Added by xulei 2011-1-14
   */
  public static void sendImage(OutputStream out, Image image, int newWidth) throws Exception {
    ByteArrayInputStream in = null;
    DataOutputStream dos = null;
    try {
      if (image != null) {
        int oldWidth = image.getWidth(null);
        int oldheight = image.getHeight(null);
        if (newWidth == 0) {
          newWidth = 320;
        }
        if (oldWidth < newWidth) {
          newWidth = oldWidth;
        }
        int newheight = newWidth * oldheight / oldWidth;
        BufferedImage bm =
            new BufferedImage(newWidth, newheight, BufferedImage.TYPE_USHORT_555_RGB);
        bm.getGraphics().drawImage(image, 0, 0, newWidth, newheight, null);
        dos = new DataOutputStream(out);
        ImageIO.write(bm, "PNG", dos);
      }
    } catch (Exception e) {
      throw e;
    } finally {
      safeClose(in);
      safeClose(out);
      safeClose(dos);
    }
  }

  /**
   * 从输入流中将文件写入指定目录
   *
   * @param in
   * @param filePath
   * @throws Exception
   */
  public static void writeFile(InputStream in, String filePath) throws Exception {
    FileOutputStream out = null;
    try {
      File f = new File(filePath);
      out = new FileOutputStream(f);
      int length = 8192;
      byte[] datas = new byte[length];
      while ((length = in.read(datas, 0, length)) != -1) {
        out.write(datas, 0, length);
      }
    } catch (Exception e) {
      throw e;
    } finally {
      safeClose(in);
      safeClose(out);
    }
  }

  /**
   * 续写文件
   *
   * @param path 文件全路径
   * @param in 输入流
   * @return
   */
  public static long writeFile(String path, InputStream in) throws Exception {
    long fileSize = 0l;
    RandomAccessFile file = new RandomAccessFile(path, "rwd");
    try {
      int length = 1024;
      byte[] datas = new byte[1024];
      while ((length = in.read(datas, 0, datas.length)) != -1) {
        file.seek(file.length());
        file.write(datas, 0, length);
      }
      fileSize = file.length();
      return fileSize;
    } catch (Exception e) {
      throw e;
    } finally {
      file.close();
    }
  }

  /**
   * 通过Date参数，获得当前日期的Web Services使用的格式
   *
   * @param date
   * @return XMLGregorianCalendar类型的日期
   */
  public static XMLGregorianCalendar getXMLGregorianCalendarDate(Date date) {
    try {
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTime(date);
      return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 获得文件MD5校验码
   *
   * @param path
   * @return
   */
  public static String getFileMD5(String path) {
    String md5Str = null;
    FileInputStream fis = null;
    ByteArrayOutputStream out = null;
    try {
      File f = new File(path);
      if (f.exists()) {
        fis = new FileInputStream(f);
        out = new ByteArrayOutputStream();
        int length = 8192;
        byte[] datas = new byte[length];
        while ((length = fis.read(datas, 0, length)) != -1) {
          out.write(datas, 0, length);
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bs = md.digest(out.toByteArray());
        int i;
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < bs.length; offset++) {
          i = bs[offset];
          if (i < 0) i += 256;
          if (i < 16) buf.append("0");
          buf.append(Integer.toHexString(i));
        }
        md5Str = buf.toString().toUpperCase();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      safeClose(fis);
      safeClose(out);
    }
    return md5Str;
  }

  /**
   * 获得字符串的MD5码
   *
   * @param str
   * @return
   */
  public static String getStringMd5(String str) {
    String md5Str = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] bs = md.digest(str.getBytes());
      int i;
      StringBuffer buf = new StringBuffer("");
      for (int offset = 0; offset < bs.length; offset++) {
        i = bs[offset];
        if (i < 0) i += 256;
        if (i < 16) buf.append("0");
        buf.append(Integer.toHexString(i));
      }
      md5Str = buf.toString().toUpperCase();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return md5Str;
  }

  public static String paserApkFile(String path) {
    ByteArrayOutputStream bos = null;
    InputStream in = null;
    ZipFile zipFile = null;
    try {
      zipFile = new ZipFile(path);
      ZipEntry entry = zipFile.getEntry("AndroidManifest.xml");
      if (entry != null) {
        in = zipFile.getInputStream(entry);
        int length = 8192;
        byte[] datas = new byte[length];
        bos = new ByteArrayOutputStream();
        while ((length = in.read(datas, 0, length)) != -1) {
          bos.write(datas, 0, length);
        }
        String dataStr = bos.toString();
        StringBuffer sb = new StringBuffer();
        char ch = "￥".charAt(0);
        // System.out.println(ch);
        for (int i = 0; i < dataStr.length(); i++) {
          char c = dataStr.charAt(i);
          if ((c >= 'a' && c <= 'z')
              || (c >= 'A' && c <= 'Z')
              || c == '.'
              || (c >= '0' && c <= '9')
              || c == '_'
              || c == '$'
              || c == '>'
              || c == '<'
              || c == '@'
              || c == ch) {
            sb.append(c);
          }
        }
        String resultStr = sb.toString();
        System.out.println(resultStr);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      safeClose(in);
      safeClose(bos);
      safeClose(zipFile);
    }
    return null;
  }

  /**
   * 对字符串进行MD5加密
   *
   * @param str
   * @return
   * @throws Exception
   */
  public static String encodeMD5(String str) throws Exception {
    if (isEmpty(str)) {
      return str;
    }

    try {
      MessageDigest mad5 = MessageDigest.getInstance("MD5");
      char[] charArray = str.toCharArray();
      byte[] byteArray = new byte[charArray.length];
      for (int i = 0; i < charArray.length; i++) byteArray[i] = (byte) charArray[i];
      byte[] md5Bytes = mad5.digest(byteArray);
      StringBuffer hexValue = new StringBuffer();
      for (int i = 0; i < md5Bytes.length; i++) {
        int val = ((int) md5Bytes[i]) & 0xff;
        // if (val < 16)
        // hexValue.append("0");
        hexValue.append(Integer.toHexString(val));
      }
      return hexValue.toString();
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 对字符串进行MD5加密
   *
   * @param str 加密字符
   * @param add16 是否补充到16位
   * @return
   * @throws Exception
   */
  public static String encodeMD5(String str, boolean add16) throws Exception {
    try {
      MessageDigest mad5 = MessageDigest.getInstance("MD5");
      char[] charArray = str.toCharArray();
      byte[] byteArray = new byte[charArray.length];
      for (int i = 0; i < charArray.length; i++) byteArray[i] = (byte) charArray[i];
      byte[] md5Bytes = mad5.digest(byteArray);
      StringBuffer hexValue = new StringBuffer();
      for (int i = 0; i < md5Bytes.length; i++) {
        int val = ((int) md5Bytes[i]) & 0xff;
        if (val < 16 && add16) hexValue.append("0");
        hexValue.append(Integer.toHexString(val));
      }
      return hexValue.toString();
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * 如果字符串为null返回空字符串，否则返回原字符串
   *
   * @param str
   * @return Added by Nijie 2011-3-29
   */
  public static String convertNullToEmpty(String str) {
    if (str == null) return "";
    return str;
  }

  /**
   * 判断该字符串是否由数字组成
   *
   * @param str
   * @return
   */
  public static boolean isNumber(String str) {
    if (!str.matches("^\\-?[0-9]+(\\.?[0-9]+)?$")) {
      return false;
    }
    return true;
  }

  /**
   * 通过本地文件完整路径获得文件名称
   *
   * @param fullFilePath
   * @return
   */
  public static String getFileName(String fullFilePath) {
    return fullFilePath.substring(fullFilePath.lastIndexOf("\\") + 1, fullFilePath.length());
  }

  /**
   * 判断字符串是否为整数
   *
   * @param str
   * @return
   */
  public static boolean isInteger(String str) {
    if (str == null || "".equals(str)) return false;
    return Pattern.matches("^[-+0-9]*$", str.trim());
  }

  /**
   * 判断是否为小数
   *
   * @param str
   * @return
   */
  public static boolean isFloat(String str) {
    if (str == null || "".equals(str)) return false;
    // + - digits and point is allowed
    return Pattern.matches("^[-+0-9.]*$", str.trim());
  }

  /**
   * 判断是否是英文字符串
   *
   * @param str
   * @return
   */
  public static boolean isEnglishChar(String str) {
    return Pattern.matches("^[[a-z]|[A-Z]]*$", str.trim());
  }

  /** 产生一个指定位数的随机码(随机码由数字或大小写字母构成) */
  public static String createRandomCode(int randomCodeLen) {
    final String str = "0123456789";
    final int maxNum = str.length();
    StringBuffer randomCode = new StringBuffer("");
    Random r = new Random();
    int count = 0;
    int ranDomNum = 0;
    while (count < randomCodeLen) {
      // 生成随机数，取绝对值，防止生成负数，
      ranDomNum = Math.abs(r.nextInt(maxNum)); // 生成的数最大为maxNum-1, 最小为0
      randomCode.append(str.charAt(ranDomNum));
      count++;
    }

    return randomCode.toString();
  }

  /**
   * 格式化数字为0.00格式
   *
   * @param num
   * @return
   */
  public static String formatNum(double num) {
    return formatNum(num, "0.00");
  }

  /**
   * 格式化数字为指定格式
   *
   * @param num
   * @param formatStr
   * @return
   */
  public static String formatNum(double num, String formatStr) {
    DecimalFormat df = new DecimalFormat(formatStr);
    return df.format(num);
  }

  /**
   * 是否为 ORA-02292: 违反完整约束条件
   *
   * @param t
   * @return true-是
   */
  public static boolean isFKException(Throwable t) {
    if (t == null) {
      return false;
    }

    String msg = t.getMessage();
    if (msg.toUpperCase().contains("ORA-02292")) {
      return true;
    } else {
      return isFKException(t.getCause());
    }
  }

  /**
   * 是否为 ORA-00001: 违反唯一性约束条件
   *
   * @param t
   * @return true-是
   */
  public static boolean isUniqueException(Throwable t) {
    if (t == null) {
      return false;
    }

    String msg = t.getMessage();

    if (msg.toUpperCase().contains("ORA-00001")) {
      return true;
    } else {
      return isFKException(t.getCause());
    }
  }

  /**
   * 将参数各个元素用下划线连接起来
   *
   * @param items
   * @return
   * @author xulei
   * @date 2013-8-2 下午5:40:04
   */
  public static String linkWithUnderline(Object... items) {
    return linkWithSymbol("_", items);
  }

  /**
   * 将参数各个元素用竖线连接起来
   *
   * @param items
   * @return
   * @author xulei
   * @date 2013-8-2 下午5:40:04
   */
  public static String linkWithVerticalLine(Object... items) {
    return linkWithSymbol("|", items);
  }

  /**
   * 将参数各个元素用指定的符号连接起来
   *
   * @param symbol
   * @param items
   * @return
   * @author xulei
   * @date 2013-8-2 下午5:40:04
   */
  public static String linkWithSymbol(String symbol, Object... items) {
    if (items == null || items.length == 0) {
      return null;
    }

    if (items.length == 1) {
      return items[0].toString();
    }

    StringBuilder sb = new StringBuilder();
    for (Object s : items) {
      sb.append(symbol).append(s);
    }

    return sb.toString().substring(1);
  }

  public static List<String> spiltByUnderline(String str) {
    return spiltBySymbol("_", str);
  }

  public static List<String> spiltByVerticalLine(String str) {
    return spiltBySymbol("\\|", str);
  }

  public static List<String> spiltBySymbol(String symbol, String str) {
    if (isEmpty(str)) {
      return null;
    }

    String[] arr = str.split(symbol);
    List<String> list = new ArrayList<String>(arr.length);
    for (String item : arr) {
      list.add(item);
    }
    return list;
  }

  /**
   * 格式化double类型的小数个数
   *
   * @param num 格式化数值
   * @param scale 保留小数位数
   * @return
   */
  public static double scaleDouble(double num, int scale) {
    int i = 1;
    for (int j = 0; j < scale; j++) {
      i *= 10;
    }
    return (double) Math.round(num * i) / i;
  }

  /**
   * 产生唯一的文件名
   *
   * @param filename
   * @return
   */
  public static synchronized String generateFileName(String filename) {
    int position = filename.lastIndexOf(".");
    String ext = filename.substring(position);
    return System.nanoTime() + ext;
  }

  /**
   * @return
   * @author xulei
   * @date 2013-12-11 上午11:57:07
   */
  public static boolean isVersionNo(String version) {
    Pattern p = Pattern.compile("[1-9]\\.[0-9]\\.[0-9]{2}");
    Matcher m = p.matcher(version);
    return m.matches();
  }

  /**
   * Integer类型的对象值是否大于指定值
   *
   * @param val
   * @param comparedVal
   * @return
   */
  public static boolean isIntGT(Integer val, int comparedVal) {
    return val != null && val > comparedVal;
  }

  /**
   * Integer类型的对象值是否小于指定值
   *
   * @param val
   * @param comparedVal
   * @return
   */
  public static boolean isIntLT(Integer val, int comparedVal) {
    return val != null && val < comparedVal;
  }

  /**
   * Integer类型的对象是否等于指定值
   *
   * @param val
   * @param comparedVal
   * @return
   */
  public static boolean isIntEq(Integer val, int comparedVal) {
    return val != null && val == comparedVal;
  }

  /**
   * Short类型的对象值是否大于指定值
   *
   * @param val
   * @param comparedVal
   * @return
   */
  public static boolean isShortGT(Short val, int comparedVal) {
    return val != null && val > comparedVal;
  }

  /**
   * Short类型的对象值是否小于指定值
   *
   * @param val
   * @param comparedVal
   * @return
   */
  public static boolean isShortLT(Short val, int comparedVal) {
    return val != null && val < comparedVal;
  }

  /**
   * Short类型的对象是否等于指定值
   *
   * @param val
   * @param comparedVal
   * @return
   */
  public static boolean isShortEq(Short val, int comparedVal) {
    return val != null && val == comparedVal;
  }

  public static void main(String[] args) {
    String end=null;
    try {
      end = (String) Chain.start()
          .must(x -> Util.isNotEmpty("1"))
          .operation(x -> "ceshi")
          .end(x -> x.get(Chain.OPERATION));
      System.out.println(isNotEmpty(end)?end:"");
    } catch (ChainException e) {
    }
    System.out.println("结束");
  }
}
