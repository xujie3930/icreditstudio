package com.jinninghui.datasphere.icreditstudio.datasource.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xujie
 * @description iCreditBanner类
 * @create 2021-08-19 14:17
 **/
@Component
@FeignClient("datasync")
public interface DatasyncFeignClient {

    @GetMapping("/datasync/hasRunningTask")
    Boolean hasRunningTask(@RequestParam("datasourceId") String datasourceId);
}
