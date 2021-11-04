package com.jinninghui.datasphere.icreditstudio.workspace.feign;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xujie
 * @description DatasourceFeign
 * @create 2021-11-03 14:57
 **/
@FeignClient("datasource")
public interface DatasourceFeignClient {

    @GetMapping("/datasource/delDatasourceFromWorkspace")
    BusinessResult<Boolean> delDatasourceFromWorkspace(@RequestParam("spaceId") String spaceId);
}
