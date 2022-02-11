package com.micro.cloud.service.impl;

import com.micro.cloud.service.GatewayRoutesService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * 〈〉
 *
 * @author roy
 * @create 2021/12/27
 * @since 1.0.0
 */
@Service("gatewayService")
public class GatewayRoutesServiceImpl implements GatewayRoutesService {

  private Logger logger = LoggerFactory.getLogger(GatewayRoutesServiceImpl.class);

  @Autowired
  private RouteLocator routeLocator;

  /**
   * 获取网关路由列表
   *
   * @return 路由列表
   */
  @Override
  public List<?> activeRoutes() {
//    routeLocator.getRoutes().
    return null;
  }
}
