package com.micro.cloud.exception;


import com.micro.cloud.api.IErrorCode;

/**
 * 调用链异常
 *
 * @author xulei
 */
public class ChainException extends RuntimeException {

  private IErrorCode errorCode;

  public ChainException(IErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ChainException() {
    super();
  }

  public ChainException(String message) {
    super(message);
  }

  public ChainException(Throwable cause) {
    super(cause);
  }

  public ChainException(String message, Throwable cause) {
    super(message, cause);
  }

  public IErrorCode getErrorCode() {
    return errorCode;
  }
}
