package com.micro.cloud.exception;

import com.micro.cloud.api.IErrorCode;

/**
 * 流程引擎附件异常类
 *
 * @author EDZ
 */
public class ProcessAttachmentException extends RuntimeException {

  private IErrorCode errorCode;

  public ProcessAttachmentException(IErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ProcessAttachmentException() {
    super();
  }

  public ProcessAttachmentException(String message) {
    super(message);
  }

  public ProcessAttachmentException(Throwable cause) {
    super(cause);
  }

  public ProcessAttachmentException(String message, Throwable cause) {
    super(message, cause);
  }

  public IErrorCode getErrorCode() {
    return errorCode;
  }
}
