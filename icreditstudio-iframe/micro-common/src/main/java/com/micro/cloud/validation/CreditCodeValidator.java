package com.micro.cloud.validation;

import cn.hutool.core.lang.Validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class CreditCodeValidator implements ConstraintValidator<CreditCode, String> {

  @Override
  public void initialize(CreditCode annotation) {}

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (StringUtils.isBlank(value)) {
      return true;
    }
    // 校验社会统一信用代码
    return Validator.isCreditCode(value);
  }
}
