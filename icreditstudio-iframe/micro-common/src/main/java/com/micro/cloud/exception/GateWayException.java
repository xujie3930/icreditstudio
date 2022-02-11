package com.micro.cloud.exception;

import com.micro.cloud.api.IErrorCode;

/**
 * 网关异常
 *
 * @author EDZ
 */
public class GateWayException extends RuntimeException {

  private long errorCode;

  private String message;

  public GateWayException(IErrorCode errorCode) {
    this.errorCode = errorCode.getCode();
    this.message = errorCode.getMessage();
  }

  public GateWayException() {
    super();
  }

  public GateWayException(String message) {
    super(message);
  }

  public GateWayException(Throwable cause) {
    super(cause);
  }

  public GateWayException(String message, Throwable cause) {
    super(message, cause);
  }

  public long getErrorCode() {
    return errorCode;
  }
}
