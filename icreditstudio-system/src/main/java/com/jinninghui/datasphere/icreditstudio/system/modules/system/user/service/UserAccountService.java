package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity.UserAccountEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.request.UserAccountEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.request.UserAccountRequestParams;

import java.util.Map;
import java.util.Set;

/**
 * @author hzh
 */
public interface UserAccountService extends IService<UserAccountEntity> {

    /**
     * 分页查询
     *
     * @param pageRequest
     * @return
     */
    BusinessPageResult queryPage(UserAccountEntityPageRequest pageRequest);

    void resetPassword(Set<String> asList, String token);


    BusinessResult<?> changePassword(UserAccountRequestParams userParams);

    boolean isFirstLogin(String userId);

    BusinessResult<Map<String,String>> getUserExecCode(String userId);
}

