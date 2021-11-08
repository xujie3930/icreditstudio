package com.jinninghui.datasphere.icreditstudio.datasync.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "workspace")
public interface WorkSpaceFeign {

    @GetMapping("/workspace/user/getWorkSpaceIdsByUserId")
    List<String> getWorkSpaceIdsByUserId(@RequestParam("userId") String userId);

}
