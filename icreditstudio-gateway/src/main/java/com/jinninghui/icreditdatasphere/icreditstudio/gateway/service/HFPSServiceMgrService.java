package com.jinninghui.icreditdatasphere.icreditstudio.gateway.service;

/**
 * @author liyanhui
 */
public interface HFPSServiceMgrService {

    /**
     * 调用UAA接口查询出所有Service加载至进程内缓存 会抛出异常 线程安全
     */
    void loadInterfaces();

    /**
     * 校验当前接口(uri+method唯一确定一个接口)是否需要执行authType类型的鉴权 线程安全
     * 不会抛出异常，如果内部出现异常，则返回true(宁可错杀，不可放过)
     *
     * @param uri
     *            当前请求地址
     * @param method
     *            当前请求方式
     * @param authType
     *            鉴权方式
     * @return 是否需要鉴权标识
     */
    boolean interfaceNeedAuth(String uri, String method, String authType);

    /**
     * 是否匹配某个精确Service且该Service不需要鉴权
     *
     * @param requestURI
     * @param method
     * @return
     */
    boolean matchOneExactlyInterface(String requestURI, String method);

    /**
     * 是否匹配某个通配符Service且该Service不需要鉴权
     *
     * @param requestURI
     * @param method
     * @return
     */
    boolean matchOneWildInterface(String requestURI, String method);
}
