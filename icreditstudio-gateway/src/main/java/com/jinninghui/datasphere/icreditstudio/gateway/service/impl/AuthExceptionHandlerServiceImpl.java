package com.jinninghui.datasphere.icreditstudio.gateway.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jinninghui.datasphere.icreditstudio.framework.utils.StringUtils;
import com.jinninghui.datasphere.icreditstudio.gateway.common.Constants;
import com.jinninghui.datasphere.icreditstudio.gateway.service.AuthExceptionHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthExceptionHandlerServiceImpl implements AuthExceptionHandlerService {

    private static final Logger log = LoggerFactory.getLogger(AuthExceptionHandlerServiceImpl.class);

    @Override
    public Mono<Void> handleException(ServerWebExchange exchange, String code, String message) {
        // 自定义返回格式
        Map<String, Object> resultMap = new HashMap<>(8);
        resultMap.put("returnCode", StringUtils.isBlank(code) ? Constants.ErrorCode.SYSTEM_FAIL.code : code);
        resultMap.put("returnMsg", StringUtils.isBlank(message) ? Constants.ErrorCode.SYSTEM_FAIL.comment : message);
        byte[] bytes = null;
        try {
            bytes = new ObjectMapper().writeValueAsBytes(resultMap);
        } catch (IOException e) {
            log.error("处理异常", e);
        }
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBuffer dataBuffer = response.bufferFactory().allocateBuffer().write(bytes);
        return response.writeWith(Mono.just(dataBuffer));

    }
}
