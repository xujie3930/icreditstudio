package com.micro.cloud.exception;

import com.micro.cloud.api.IErrorCode;

/**
 * 表单引擎异常类
 *
 * @author EDZ
 */
public class FormDataException extends RuntimeException {

  private IErrorCode errorCode;

  public FormDataException(IErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public FormDataException() {
    super();
  }

  public FormDataException(String message) {
    super(message);
  }

  public FormDataException(Throwable cause) {
    super(cause);
  }

  public FormDataException(String message, Throwable cause) {
    super(message, cause);
  }

  public IErrorCode getErrorCode() {
    return errorCode;
  }
}
