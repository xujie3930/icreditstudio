package com.jinninghui.datasphere.icreditstudio.datasync.feign;

import com.jinninghui.datasphere.icreditstudio.datasync.feign.request.User;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictInfo;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * @author Peng
 */
@FeignClient(value = "uaa")
public interface SystemFeign {

    @GetMapping("/system/code/code/getDictInfos")
    BusinessResult<List<DictInfo>> getDictInfoByTypes(@RequestParam("types") Collection<String> types);

    @GetMapping("/system/user/useraccount/getUserExecCode")
    BusinessResult<User> getUserAccountInfo(@RequestParam("userId") String userId);
}
