package com.jinninghui.datasphere.icreditstudio.modules.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserAccountEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.web.request.UserAccountEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.web.request.UserAccountRequestParams;

import java.util.Set;

/**
 *
 *
 * @author hzh
 */
public interface UserAccountService extends IService<UserAccountEntity> {

    /**
    *  分页查询
    * @param pageRequest
    * @return
    */
    BusinessPageResult queryPage(UserAccountEntityPageRequest pageRequest);

    void resetPassword(Set<String> asList, String token);


    BusinessResult<?> changePassword(UserAccountRequestParams userParams);

    boolean isFirstLogin(String userId);

}

