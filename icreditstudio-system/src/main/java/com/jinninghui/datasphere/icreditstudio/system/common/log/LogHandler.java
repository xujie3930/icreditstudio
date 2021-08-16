package com.jinninghui.datasphere.icreditstudio.system.common.log;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public interface LogHandler<T> {
    /**
     * 方法调用前
     *
     * @param request
     * @param method
     */
    T pre(HttpServletRequest request, Method method, Object[] args);

    /**
     * 方法调用后
     *
     * @param method
     * @param o
     */
    void post(HttpServletRequest request,Method method, Object o, T t);

    /**
     * 方法调用异常
     *
     * @param method
     * @param t
     */
    void ex(HttpServletRequest request,Method method, Throwable th, T t);
}
