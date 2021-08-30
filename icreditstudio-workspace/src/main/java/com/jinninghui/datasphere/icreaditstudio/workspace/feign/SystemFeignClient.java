package com.jinninghui.datasphere.icreaditstudio.workspace.feign;

import com.jinninghui.datasphere.icreaditstudio.workspace.feign.request.FeignUserEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author xujie
 * @description iCreditBanner类
 * @create 2021-08-19 14:17
 **/
@FeignClient("uaa")
public interface SystemFeignClient {

    @PostMapping("/system/user/user/pageList")
    BusinessResult<BusinessPageResult> pageList(@RequestBody FeignUserEntityPageRequest pageRequest,
                                                @RequestHeader(value = "x-userid") String loginUserId);
}
