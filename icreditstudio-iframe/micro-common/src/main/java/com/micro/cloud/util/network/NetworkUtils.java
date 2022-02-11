//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.micro.cloud.util.network;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

public class NetworkUtils {
  private static byte[][] invalidMacs = new byte[][]{{0, 5, 105}, {0, 28, 20}, {0, 12, 41}, {0, 80, 86}, {8, 0, 39}, {10, 0, 39}, {0, 3, -1}, {0, 21, 80}};

  public NetworkUtils() {
  }

  private static boolean isVmMac(byte[] mac) {
    if (null == mac) {
      return false;
    } else {
      byte[][] var1 = invalidMacs;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
        byte[] invalid = var1[var3];
        if (invalid[0] == mac[0] && invalid[1] == mac[1] && invalid[2] == mac[2]) {
          return true;
        }
      }

      return false;
    }
  }

  public static Set<NetworkInterface> getNICs(NetworkUtils.Filter... filters) {
    if (null == filters) {
      filters = new NetworkUtils.Filter[]{NetworkUtils.Filter.ALL};
    }

    HashSet ret = new HashSet();

    Enumeration networkInterfaceEnumeration;
    try {
      networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
    } catch (SocketException var9) {
      throw new IllegalStateException(var9);
    }

    while(networkInterfaceEnumeration.hasMoreElements()) {
      boolean match = false;
      NetworkInterface next = (NetworkInterface)networkInterfaceEnumeration.nextElement();
      NetworkUtils.Filter[] var5 = filters;
      int var6 = filters.length;

      for(int var7 = 0; var7 < var6; ++var7) {
        NetworkUtils.Filter filter = var5[var7];
        if (filter.apply(next)) {
          match = true;
        }
      }

      if (match) {
        ret.add(next);
      }
    }

    return ret;
  }

  public static String getMacAddress(NetworkInterface networkInterface, String separator) {
    try {
      return format(networkInterface.getHardwareAddress(), separator, NetworkUtils.Radix.HEX);
    } catch (SocketException var3) {
      throw new IllegalStateException(var3);
    }
  }

  public static String format(byte[] source, String separator, NetworkUtils.Radix radix) {
    if (null == source) {
      return "";
    } else {
      if (null == separator) {
        separator = "";
      }

      StringBuilder sb = new StringBuilder();
      byte[] var4 = source;
      int var5 = source.length;

      for(int var6 = 0; var6 < var5; ++var6) {
        byte b = var4[var6];
        sb.append(separator).append(apply(b, radix));
      }

      return sb.length() > 0 ? sb.substring(separator.length()) : "";
    }
  }

  public static final String getIpAddress(HttpServletRequest request) throws IOException {
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Real-IP");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }

    if (ip.length() > 15) {
      String[] ips = ip.split(",");

      for(int index = 0; index < ips.length; ++index) {
        String strIp = ips[index];
        if (!"unknown".equalsIgnoreCase(strIp)) {
          ip = strIp;
          break;
        }
      }
    }

    return ip;
  }

  private static String apply(Byte input, NetworkUtils.Radix radix) {
    return String.copyValueOf(new char[]{Character.forDigit((input & 240) >> 4, radix.value), Character.forDigit(input & 15, radix.value)});
  }

  public static enum Radix {
    BIN(2),
    DEC(10),
    HEX(16);

    final int value;

    private Radix(int radix) {
      this.value = radix;
    }
  }

  public static enum Filter {
    ALL,
    UP,
    VIRTUAL,
    LOOPBACK,
    PHYSICAL_ONLY;

    private Filter() {
    }

    public boolean apply(NetworkInterface input) {
      if (null == input) {
        return false;
      } else {
        try {
          switch(this) {
          case UP:
            return input.isUp();
          case VIRTUAL:
            return input.isVirtual();
          case LOOPBACK:
            return input.isLoopback();
          case PHYSICAL_ONLY:
            byte[] hardwareAddress = input.getHardwareAddress();
            return null != hardwareAddress && hardwareAddress.length > 0 && !input.isVirtual() && !NetworkUtils.isVmMac(hardwareAddress);
          case ALL:
          default:
            return true;
          }
        } catch (SocketException var3) {
          throw new IllegalStateException(var3);
        }
      }
    }
  }
}
