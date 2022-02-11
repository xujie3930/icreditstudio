package com.micro.cloud.exception;

import com.micro.cloud.api.IErrorCode;

/**
 * 断言处理,用于抛出各类型API异常
 *
 * @author EDZ
 */
public class Asserts {

  public static void fail(String message) {
    throw new ApiException(message);
  }

  public static void fail(String message, Throwable cause) {
    throw new ApiException(message, cause);
  }

  public static void fail(IErrorCode errorCode) {
    throw new ApiException(errorCode);
  }
}
