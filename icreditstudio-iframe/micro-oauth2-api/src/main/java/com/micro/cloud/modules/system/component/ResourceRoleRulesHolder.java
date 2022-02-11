package com.micro.cloud.modules.system.component;

import com.micro.cloud.modules.system.resource.service.SysResourceService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** 资源与角色访问对应关系操作组件
 * @author roy*/
@Component
public class ResourceRoleRulesHolder {

  @Autowired private SysResourceService resourceService;

  @PostConstruct
  public void initResourceRolesMap() {
    resourceService.initData();
  }
}
