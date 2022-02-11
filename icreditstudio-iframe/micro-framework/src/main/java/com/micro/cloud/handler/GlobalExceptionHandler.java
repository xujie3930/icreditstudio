package com.micro.cloud.handler;

import com.micro.cloud.api.CommonResult;
import com.micro.cloud.api.ResultCode;
import com.micro.cloud.exception.ApiException;
import com.micro.cloud.exception.FormDataException;
import com.micro.cloud.exception.GateWayException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/** @author roy */
@RestControllerAdvice
public class GlobalExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  /** 处理Api自定义异常 */
  @ExceptionHandler(ApiException.class)
  public CommonResult<?> handleRException(ApiException e) {
    logger.error(e.getMessage(), e);
    return new CommonResult(e.getErrorCode().getCode(), e.getMessage(), null);
  }

  @ExceptionHandler(GateWayException.class)
  public CommonResult<?> handleRException(GateWayException e) {
    logger.error(e.getMessage(), e);
    return new CommonResult(e.getErrorCode(), e.getMessage(), null);
  }

  /** 处理FormData自定义异常 */
  @ExceptionHandler(FormDataException.class)
  public CommonResult<?> handleRException(FormDataException e) {
    logger.error(e.getMessage(), e);
    return new CommonResult(e.getErrorCode().getCode(), e.getMessage(), null);
  }

  /** 方法参数校验 */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public CommonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    logger.error(e.getMessage(), e);
    return new CommonResult(
        ResultCode.VALIDATE_FAILED.getCode(),
        e.getBindingResult().getFieldError().getDefaultMessage(),
        null);
  }

  /** ValidationException */
  @ExceptionHandler(ValidationException.class)
  public CommonResult handleValidationException(ValidationException e) {
    logger.error(e.getMessage(), e);
    return new CommonResult(ResultCode.PARAM_FAIL_ERROR.getCode(), e.getCause().getMessage(), null);
  }

  /** ConstraintViolationException */
  @ExceptionHandler(ConstraintViolationException.class)
  public CommonResult handleConstraintViolationException(ConstraintViolationException e) {
    logger.error(e.getMessage(), e);
    return new CommonResult(ResultCode.VALIDATE_FAILED.getCode(), e.getMessage(), null);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public CommonResult handlerNoFoundException(Exception e) {
    logger.error(e.getMessage(), e);
    return new CommonResult(
        ResultCode.PATH_NOT_FUND.getCode(), ResultCode.PATH_NOT_FUND.getMessage(), null);
  }

  @ExceptionHandler(DuplicateKeyException.class)
  public CommonResult handleDuplicateKeyException(DuplicateKeyException e) {
    logger.error(e.getMessage(), e);
    return new CommonResult(
        ResultCode.DUPLICATE_KEY_ERROR.getCode(),
        ResultCode.DUPLICATE_KEY_ERROR.getMessage(),
        null);
  }

  @ExceptionHandler(Exception.class)
  public CommonResult handleException(ApiException e) {
    logger.error(e.getMessage(), e);
    return new CommonResult(e.getErrorCode().getCode(), "系统繁忙，请稍后再试", null);
  }
}
