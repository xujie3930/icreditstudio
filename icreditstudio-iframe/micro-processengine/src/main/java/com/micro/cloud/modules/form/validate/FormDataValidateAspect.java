package com.micro.cloud.modules.form.validate;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.micro.cloud.modules.form.dal.mapper.WorkFlowBillMapper;
import com.micro.cloud.modules.form.param.FormDataParam;
import com.micro.cloud.modules.form.service.WorkflowFormService;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 表单参数校验，切面处理
 *
 * @author roy
 */
@Aspect
@Component
public class FormDataValidateAspect {

  private final Logger logger = LoggerFactory.getLogger(FormDataValidateAspect.class);

  @Autowired private WorkflowFormService workflowFormService;

  @Pointcut("@annotation(com.micro.cloud.modules.form.validate.FormDataValidate)")
  public void pointCut() {}

  @Around("pointCut()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
    Object[] args = joinPoint.getArgs();
    Map<String, Object> parameters = generateParameters(method, args);
    FormDataParam formDataParam = (FormDataParam) parameters.get("body");
    workflowFormService.validateFormFields(
        formDataParam.getProcessKey(), formDataParam.getFormData());
    return joinPoint.proceed();
  }

  /** 根据方法和传入的参数获取请求参数 */
  private Map<String, Object> generateParameters(Method method, Object[] args) {
    Map<String, Object> argMap = new HashMap<>(8);
    Parameter[] parameters = method.getParameters();
    for (int i = 0; i < parameters.length; i++) {
      // 将RequestBody注解修饰的参数作为请求参数
      RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
      if (Objects.nonNull(requestBody)) {
        argMap.put("body", args[i]);
      }
      // 将pathVaribale注解修饰参数作为入参
      PathVariable pathVariable = parameters[i].getAnnotation(PathVariable.class);
      if (Objects.nonNull(pathVariable)) {
        String key = parameters[i].getName();
        if (!StringUtils.isEmpty(pathVariable.value())) {
          key = pathVariable.value();
        }
        argMap.put(key, args[i]);
      }
      // 将RequestParam注解修饰的参数作为请求参数
      RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
      if (Objects.nonNull(requestParam)) {
        String key = parameters[i].getName();
        if (!StringUtils.isEmpty(requestParam.value())) {
          key = requestParam.value();
        }
        argMap.put(key, args[i]);
      }
      // 将RequestParam注解修饰的参数作为请求参数
      RequestHeader requestHeader = parameters[i].getAnnotation(RequestHeader.class);
      if (Objects.nonNull(requestHeader)) {
        String key = parameters[i].getName();
        if (!StringUtils.isEmpty(requestHeader.value())) {
          key = requestHeader.value();
        }
        argMap.put(key, args[i]);
      }
    }
    return argMap;
  }
}
