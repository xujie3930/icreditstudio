package com.micro.cloud.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.micro.cloud.api.CommonResult;
import com.micro.cloud.api.ResultCode;
import com.micro.cloud.constant.AuthConstant;
import com.micro.cloud.domian.dto.UserRoles;
import com.micro.cloud.info.UserInfo;
import com.micro.cloud.redis.service.RedisService;
import com.nimbusds.jose.JWSObject;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.yaml.snakeyaml.util.UriEncoder;
import reactor.core.publisher.Mono;

/** 将登录用户的JWT转化成用户信息的全局过滤器 Created by xulei on 2021/11/3 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

  private final Logger logger = LoggerFactory.getLogger(AuthGlobalFilter.class);

  @Autowired private RedisService redisService;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String token = exchange.getRequest().getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
    if (StrUtil.isEmpty(token)) {
      return chain.filter(exchange);
    }

    try {
      // redis 获取jti唯一标识判断token是否存在黑名单中
      token = token.replace(AuthConstant.JWT_TOKEN_PREFIX, Strings.EMPTY);
      JWSObject jwsObject = JWSObject.parse(token);
      String payload = String.valueOf(jwsObject.getPayload());
      // 黑名单token(登出、互踢)校验
      JSONObject jsonObject = JSONUtil.parseObj(payload);
      // JWT唯一标识
      String jti = jsonObject.getStr(AuthConstant.JWT_JTI);
      Boolean inBlack = redisService.hasKey(AuthConstant.TOKEN_BLACKLIST_PREFIX + jti);
      logger.info("######## inBlack:{}", inBlack);
      if (inBlack) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin", "*");
        response.getHeaders().set("Cache-Control", "no-cache");
        String body =
            JSONUtil.toJsonStr(CommonResult.unauthorized(ResultCode.UNAUTHORIZED.getMessage()));
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
      }
      // 从token中解析用户信息并设置到Header中去
      String userStr = jwsObject.getPayload().toString();
      exchange = wrapHeader(exchange, userStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return chain.filter(exchange);
  }
  /**
   * 方法实现说明:把我们从jwt解析出来的用户信息存储到请求中
   *
   * @param serverWebExchange
   * @param loginUserInfo
   * @author:smlz
   * @return: ServerWebExchange
   */
  private ServerWebExchange wrapHeader(ServerWebExchange serverWebExchange, String loginUserInfo) {
    JSONObject jsonObject = JSONUtil.parseObj(loginUserInfo);
    Map<String, Object> additionalInfo = jsonObject.get("additionalInfo", Map.class);
    logger.info("######### additionalInfo:{}", additionalInfo);
    String id = String.valueOf(additionalInfo.get("id"));
    String userName = String.valueOf(additionalInfo.get("userName"));
    String realName = String.valueOf(additionalInfo.get("realName"));
    String phone = String.valueOf(additionalInfo.get("phone"));
    String email = String.valueOf(additionalInfo.get("email"));
    Integer userType = Integer.getInteger(String.valueOf(additionalInfo.get("userType")));
    String departId = String.valueOf(additionalInfo.get("departId"));
    String departName = String.valueOf(additionalInfo.get("departName"));
    List<UserRoles> roles = (List<UserRoles>) additionalInfo.get("roles");
    UserInfo userInfo =
        new UserInfo(id, userName, phone, email, userType, departId, departName, roles);
    String userJson = JSONUtil.toJsonStr(userInfo);
    //    logger.info("######## userJson:{}", userJson);
    // 向headers中放用户相关信息，记得build
    ServerHttpRequest request =
        serverWebExchange
            .getRequest()
            .mutate()
            .header("userId", id)
            .header("realName", UriEncoder.encode(realName))
            .header("departId", departId)
            .header("userInfo", UriEncoder.encode(userJson))
            .build();

    // 将现在的request 变成 change对象
    return serverWebExchange.mutate().request(request).build();
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
