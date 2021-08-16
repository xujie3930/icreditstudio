package com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service;

import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.param.UserAccountGetsForm;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.param.UserRoleResRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.result.UserAccountEntityResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.result.UserRoleResResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;

/**
 * @Author: jidonglin
 * @Date: 2019/8/5 11:23
 */
public interface BusinessUserAccountService {

    BusinessResult<UserAccountEntityResult> get(UserAccountGetsForm form);

    BusinessResult<UserRoleResResult> getRoleResAndUpdateLoginTime(UserRoleResRequest userRoleResRequest);

    /**
     * 校验用户角色是否有效
     * @param userId 用户id
     * @return
     */
    boolean isVerifyRoleEffective(String userId);

    /**
     * 校验用户部门是否有效
     * @param userId 用户id
     * @return
     */
    boolean isVerifyOrgEffective(String userId);


}
