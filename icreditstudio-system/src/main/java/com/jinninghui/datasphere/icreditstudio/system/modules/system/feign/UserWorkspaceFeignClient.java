package com.jinninghui.datasphere.icreditstudio.system.modules.system.feign;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * @author xujie
 * @description iCreditBanner类
 * @create 2021-08-19 14:17
 **/
@Component
@FeignClient("workspace")
public interface UserWorkspaceFeignClient {

    @GetMapping(value = {"/workspace/user/getWorkspaceByUserId/{id}", "/workspace/user/getWorkspaceByUserId"})
    BusinessResult<List<Map<String, String>>> getWorkspaceListByUserId(@PathVariable(value = "id", required = false) String id);
}
