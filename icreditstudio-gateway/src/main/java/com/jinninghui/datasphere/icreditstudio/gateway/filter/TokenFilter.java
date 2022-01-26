package com.jinninghui.datasphere.icreditstudio.gateway.filter;

import com.jinninghui.datasphere.icreditstudio.gateway.common.Constants;
import com.jinninghui.datasphere.icreditstudio.gateway.service.AuthExceptionHandlerService;
import com.jinninghui.datasphere.icreditstudio.gateway.service.HFPSServiceMgrService;
import com.jinninghui.datasphere.icreditstudio.gateway.service.UaaService;
import com.jinninghui.datasphere.icreditstudio.gateway.service.result.BusinessToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

/**
 * 描述 ：拦截所有的访问地址，截取token，根据访问的uri，判断是否需要token校验
 */
@Component
public class TokenFilter implements GlobalFilter, Ordered {

    static Logger log = LoggerFactory.getLogger(TokenFilter.class);

    @Autowired
    private UaaService uaaService;

    @Autowired
    private AuthExceptionHandlerService authExceptionHandler;

    @Autowired
    private HFPSServiceMgrService hfpsServiceMgrService;


    protected String getToken(MultiValueMap<String, HttpCookie> cookies) {
        String token = null;
        if (cookies.containsKey(Constants.TOKEN_AUTH_TYPE)) {
            HttpCookie cookie = cookies.getFirst(Constants.TOKEN_AUTH_TYPE);
            token = cookie.getValue();
        }
        return token;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 如访问的是http://localhost:8002/os/city/query-hot-city，uri=/os/city/query-hot-city
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        // 获取请求的uri
        String uri = serverHttpRequest.getURI().getPath();
        String requestURI = FinalAuthFilter.getUri(uri);
        // 获取请求的方法,POST，GET等
        String method = serverHttpRequest.getMethod().name();
        if (RequestMethod.OPTIONS.name().equalsIgnoreCase(method)) {
            chain.filter(exchange);
        }
        // 获取cookie集合
        MultiValueMap<String, HttpCookie> cookies = serverHttpRequest.getCookies();
        /*List<String> authorization = serverHttpRequest.getHeaders().get("Authorization");
        String token = authorization.get(0);*/
        // 根据uri和方法，判断请求是否需要鉴权
        if (serverHttpRequest.getHeaders().containsKey(Constants.AUTH_PASS_KEY)) {
            log.info("不需要执行token鉴权,因为其他鉴权Filter已经鉴权通过, uri=" + requestURI + ",method=" + method);
            return chain.filter(exchange);
        }
        if (!hfpsServiceMgrService.interfaceNeedAuth(requestURI, method, Constants.TOKEN_AUTH_TYPE)) {
            log.info("不需要执行token鉴权,因为该接口未配置需要执行token鉴权, uri=" + requestURI + ",method=" + method);
            return chain.filter(exchange);
        }
        if (StringUtils.isBlank(getToken(cookies)) && !serverHttpRequest.getHeaders().containsKey(Constants.TOKEN_AUTH_TYPE_OUT)) {
            log.info("不需要执行token鉴权,因为请求的cookies中没有包含token, uri=" + requestURI + ",method=" + method);
            return chain.filter(exchange);
        }

        // 1 内部管理系统  2  外部登录用户
        String requestType = "2";
        // 外部登录用户  Access-Token 获取token鉴权
        String token = serverHttpRequest.getHeaders().getFirst(Constants.TOKEN_AUTH_TYPE_OUT);
        // 内部管理系统 从Cookie 中获取 token
        if (token == null) {
            requestType = "1";
            token = getToken(cookies);
        }
        try {
            // token鉴权
            BusinessToken businessToken = uaaService.tokenAuth(requestURI, method, token, requestType);
            if (businessToken.isSuccess()) {

                Consumer<HttpHeaders> httpHeaders = httpHeaders1 -> {
                    httpHeaders1.set(Constants.AUTH_PASS_KEY, Constants.SUCCESS);
                    httpHeaders1.set("x-token", businessToken.getToken());
                    httpHeaders1.set("x-Access-Token", businessToken.getToken());
                    httpHeaders1.set("x-userid", businessToken.getUserId().toString());
                    httpHeaders1.set("x-customer-code", businessToken.getCustomerCode());
                    httpHeaders1.set("x-roleId", businessToken.getRoleId() != null ? businessToken.getRoleId().toString() : null);
                    httpHeaders1.set("x-customer-type", businessToken.getCustomerTypeCode());
                    httpHeaders1.set("x-business-type", businessToken.getBusinessType());
                    httpHeaders1.set("x-extra", businessToken.getExtra());
                    httpHeaders1.set("Authorization", businessToken.getToken());
                    httpHeaders1.set("userId", String.valueOf(businessToken.getUserId()));
                };
                ServerHttpRequest build = serverHttpRequest.mutate().headers(httpHeaders).build();

                ServerWebExchange buildRequest = exchange.mutate().request(build).build();
                log.info("token鉴权成功, uri=" + requestURI + ",method=" + method);
                return chain.filter(buildRequest);
            } else {
                // 报错直接返回，不在调用过滤器链
                return authExceptionHandler.handleException(exchange, businessToken.getReturnCode(), businessToken.getReturnMsg());
            }
        } catch (Exception e) {
            //token鉴权失败，直接返回，不在调用过滤器链
            return authExceptionHandler.handleException(exchange, null, null);
        }

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
