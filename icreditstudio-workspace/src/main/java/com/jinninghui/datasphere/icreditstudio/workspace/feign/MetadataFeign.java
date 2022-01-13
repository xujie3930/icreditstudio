package com.jinninghui.datasphere.icreditstudio.workspace.feign;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.workspace.feign.request.FeignUserAuthRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Peng
 */

@FeignClient("metadata")
public interface MetadataFeign {

    /**
     * 用户授予表权限
     *
     * @param request
     * @return
     */
    @PostMapping("/metadata/auth")
    BusinessResult<Boolean> auth(@RequestBody FeignUserAuthRequest request);

    /**
     * 用户移除表权限
     *
     * @param request
     * @return
     */
    @PostMapping("/metadata/unAuth")
    BusinessResult<Boolean> unAuth(@RequestBody FeignUserAuthRequest request);
}
