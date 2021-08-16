package com.jinninghui.datasphere.icreditstudio.system.common.log;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BaseComponent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

@Aspect
@Component
public class LogAspect extends BaseComponent {

    @Autowired
    private ThreadPoolExecutor executor;

    @Pointcut("@annotation(com.jinninghui.datasphere.icreditstudio.system.common.log.Log)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) throws Throwable {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] args = joinPoint.getArgs();

        Map<String, LogHandler> logHandlers = getApplicationContext().getBeansOfType(LogHandler.class);

        LogWrapperRequest wrapperRequest = new LogWrapperRequest(request);
        CompletableFuture<Map<String, LogHandler>> future = CompletableFuture.supplyAsync(() -> logHandlers, executor);

        ConcurrentHashMap<String, Object> cm = new ConcurrentHashMap<>();
        try {
            future = future.thenApplyAsync(map -> {
                map.forEach((k, v) -> {
                    Object result = v.pre(wrapperRequest, method, args);
                    if (Objects.nonNull(result)) {
                        cm.put(k, result);
                    }
                });
                return map;
            });
            CompletableFuture.allOf(future).join();
            Object proceed = joinPoint.proceed();
            future.thenAcceptAsync(map -> {
                map.forEach((k, v) -> {
                    Object o = cm.get(k);
                    v.post(wrapperRequest, method, proceed, o);
                });
            });
            return proceed;
        } catch (Throwable t) {
            future.thenAcceptAsync(map -> {
                map.forEach((k, v) -> {
                    Object o = cm.get(k);
                    v.ex(wrapperRequest, method, t, o);
                });
            });
            if (t instanceof RuntimeException) {
                throw  (RuntimeException) t;
            }
            return t;
        }
    }
}
