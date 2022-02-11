package com.micro.cloud.exception;

import com.micro.cloud.api.IErrorCode;

/**
 * 流程引擎异常类
 *
 * @author EDZ
 */
public class ProcessException extends RuntimeException {

  private IErrorCode errorCode;

  public ProcessException(IErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ProcessException() {
    super();
  }

  public ProcessException(String message) {
    super(message);
  }

  public ProcessException(Throwable cause) {
    super(cause);
  }

  public ProcessException(String message, Throwable cause) {
    super(message, cause);
  }

  public IErrorCode getErrorCode() {
    return errorCode;
  }
}
