package com.micro.cloud.sms.core.client;

import com.micro.cloud.exception.ErrorCode;
import java.util.function.Function;

/**
 * 将 API 的错误码，转换为通用的错误码
 *
 * @see SmsCommonResult
 * @author roy
 */
public interface SmsCodeMapping extends Function<String, ErrorCode> {}
