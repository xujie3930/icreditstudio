package com.jinninghui.datasphere.icreditstudio.gateway.common;

import com.jinninghui.datasphere.icreditstudio.gateway.service.result.BusinessToken;
import com.jinninghui.datasphere.icreditstudio.gateway.service.result.Interface;
import com.jinninghui.datasphere.icreditstudio.gateway.service.result.SignMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project：iCreditStudio
 * Package：com.jinninghui.datasphere.icreditstudio.gateway.common
 * ClassName: UaaFeignClient
 * Description:  UaaFeignClient类
 * Date: 2021/8/12 6:40 下午
 *
 * @author liyanhui
 */
@FeignClient("uaa")
public interface UaaFeignClient {
    @GetMapping({"/interfaces"})
    List<Interface> getAllInterface();

    @PostMapping(value = "/tokenAuth")
    BusinessToken tokenAuth(@RequestParam(value = "interfaceUrl") String interfaceUrl, @RequestParam(value = "interfaceMethod") String interfaceMethod,
                            @RequestParam(value = "token") String token,
                            @RequestParam(value = "requestType") String requestType);

    @RequestMapping(value ="/sign", method = RequestMethod.POST)
    SignMsg sign(@RequestParam(value = "input") String input);

}
