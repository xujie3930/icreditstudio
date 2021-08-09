package com.jinninghui.datasphere.icreditstudio.gateway.service;

import com.netflix.zuul.context.RequestContext;

/**
 * @author liyanhui
 */
public interface AuthExceptionHandlerService {

    /**
     * 本地异常处理（业务异常）
     *
     * @param ctx
     * @param code
     * @param msg
     */
    void businessExceptionHandler(RequestContext ctx, String code, String msg);

    /**
     * 可同时处理本地异常以及远程异常 方法不会抛出异常
     */
    void systemExceptionHandler(Exception e, RequestContext ctx);

    /**
     * 可同时处理本地异常以及远程异常 方法不会抛出异常
     */
    void systemExceptionHandler(Exception e, RequestContext ctx, String code, String msg);
}
