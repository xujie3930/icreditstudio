package com.micro.cloud.modules.process.component;

import cn.hutool.core.collection.CollUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CheckApprove {
  private final Logger LOGGER = LoggerFactory.getLogger(CheckApprove.class);

  public long checkHuiqian(List devapprove) {
    long res = 0;
    if(CollUtil.isNotEmpty(devapprove)) {
      LOGGER.info(devapprove.getClass().toString());
      LOGGER.info(devapprove.toString());

      for (Object devapp : devapprove) {
        LOGGER.info(devapp.getClass().toString()+devapp.toString());
      }

      if(devapprove.contains(0) || devapprove.contains("0")) {
        return res;
      }
      res = 1;
    }
    return res;
  }
}
