package com.jinninghui.datasphere.icreditstudio.workspace.feign;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xujie
 * @description iCreditBanner类
 * @create 2021-08-19 14:17
 **/
@FeignClient("uaa")
public interface SystemFeignClient {

    /**
     * 判断是否是超级管理员
     */
    @RequestMapping(value = {"/system/resources/isAdmin"}, method = {RequestMethod.POST})
    @ApiOperation(value = "判断是否是超级管理员", notes = "判断是否是超级管理员", httpMethod = "POST")
    BusinessResult<Boolean> isAdmin();
}
