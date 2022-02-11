package com.micro.cloud.authorization;

import cn.hutool.core.convert.Convert;
import com.micro.cloud.config.IgnoreUrlsConfig;
import com.micro.cloud.constant.AuthConstant;
import com.micro.cloud.constant.SysOauthConstant;
import com.micro.cloud.redis.service.RedisService;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

/** 鉴权管理器，用于判断是否有资源的访问权限 Created by xulei on 2021/11/3 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

  private final Logger logger = LoggerFactory.getLogger(AuthorizationManager.class);

  @Autowired private RedisService redisService;

  @Autowired private IgnoreUrlsConfig ignoreUrlsConfig;

  @Override
  public Mono<AuthorizationDecision> check(
      Mono<Authentication> mono, AuthorizationContext authorizationContext) {
    ServerHttpRequest request = authorizationContext.getExchange().getRequest();
    // 从Redis中获取当前路径可访问角色列表
    URI uri = request.getURI();
    logger.info("######### uri:{}", uri.getPath());
    PathMatcher pathMatcher = new AntPathMatcher();

    // 管理端路径需校验权限
    Map<Object, Object> resourceRolesMap =
        redisService.hGetAll(SysOauthConstant.RESOURCE_ROLES_MAP);
    Iterator<Object> iterator = resourceRolesMap.keySet().iterator();
    List<String> authorities = new ArrayList<>();
    while (iterator.hasNext()) {
      String pattern = (String) iterator.next();
      if (pathMatcher.match(pattern.replace(" ", ""), uri.getPath())) {
        authorities.addAll(Convert.toList(String.class, resourceRolesMap.get(pattern)));
      }
    }
    authorities =
        authorities.stream()
            .map(i -> i = AuthConstant.AUTHORITY_PREFIX + i)
            .collect(Collectors.toList());
    logger.info("########## authorities:{}", authorities);
    // 认证通过且角色匹配的用户可访问当前路径

    return mono.filter(Authentication::isAuthenticated)
        .flatMapIterable(Authentication::getAuthorities)
        .map(GrantedAuthority::getAuthority)
        .any(authorities::contains)
        .map(AuthorizationDecision::new)
        .defaultIfEmpty(new AuthorizationDecision(false));
  }
}
