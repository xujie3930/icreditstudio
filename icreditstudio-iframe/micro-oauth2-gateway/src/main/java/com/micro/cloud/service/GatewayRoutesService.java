package com.micro.cloud.service;

import java.util.List;

/**
 * 〈网关路由服务接口〉
 *
 * @author roy
 * @create 2021/12/27
 * @since 1.0.0
 */
public interface GatewayRoutesService {

  /**
   * 获取网关路由列表
   *
   * @return 路由列表
   */
  List<?> activeRoutes();
}
