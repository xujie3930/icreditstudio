package com.micro.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/** 资源与角色匹配关系管理业务类 Created by xulei on 2021/11/3 */
@Service
public class ResourceServiceImpl {

  private Map<String, List<String>> resourceRolesMap;
  @Autowired private RedisTemplate<String, Object> redisTemplate;

  // Constructor >> @Autowired >> @PostConstruct >> Init >> Service >> destroy >> @PreDestroy >>
  // 服务器卸载完毕---执行顺序说明
  /*@PostConstruct
  public void initData() {
    resourceRolesMap = new TreeMap<>();
    resourceRolesMap.put("/api/hello", CollUtil.toList("ADMIN"));
    resourceRolesMap.put("/api/user/currentUser", CollUtil.toList("ADMIN", "TEST"));
    redisTemplate.opsForHash().putAll(SysOauthConstant.RESOURCE_ROLES_MAP, resourceRolesMap);
  }*/
}
