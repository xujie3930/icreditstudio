package com.micro.cloud.controller;

import com.micro.cloud.api.CommonResult;
import com.micro.cloud.service.GatewayRoutesService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈网关路由控制器〉
 *
 * @author roy
 * @create 2021/12/27
 * @since 1.0.0
 */
@RestController
@RequestMapping("/routes")
public class GatewayRoutesController {

  @Autowired private GatewayRoutesService gatewayRoutesService;

  @ApiOperation(value = "获取路由列表")
  @GetMapping("/list")
  public CommonResult<List<?>> routes() {
    List<?> routes = gatewayRoutesService.activeRoutes();
    return CommonResult.success(routes);
  }
}
