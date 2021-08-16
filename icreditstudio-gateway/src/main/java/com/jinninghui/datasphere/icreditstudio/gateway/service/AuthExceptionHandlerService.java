package com.jinninghui.datasphere.icreditstudio.gateway.service;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author liyanhui
 */
public interface AuthExceptionHandlerService {

    Mono<Void> handleException(ServerWebExchange exchange, String code, String message);


}
