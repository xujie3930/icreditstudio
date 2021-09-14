package org.apache.dolphinscheduler.feign;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import org.apache.dolphinscheduler.feign.result.UserEntity;
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
     * 获取当前登录用户信息
     */
    @RequestMapping(value = {"/system/user/user/getLoginUserInfo"}, method = {RequestMethod.POST})
    BusinessResult<UserEntity> getLoginUserInfo();
}
