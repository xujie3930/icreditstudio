package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.common.code.ResourceCodeBean;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.entity.UserAccountEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.mapper.UserAccountDao;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.UserAccountService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.request.UserAccountEntityPageRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.request.UserAccountRequestParams;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.SessionService;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.Query;
import com.hashtech.businessframework.utils.sm4.SM4Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service("userAccountService")
public class UserAccountServiceImpl extends ServiceImpl<UserAccountDao, UserAccountEntity> implements UserAccountService {

    @Value("${account.defaultPassword}")
    private String defaultPassword;
    @Value("${sm4.secretKey}")
    private String secretKey;

    @Autowired
    private UserAccountDao userAccountDao;
    @Autowired
    private SessionService sessionService;

    @Override
    public BusinessPageResult queryPage(UserAccountEntityPageRequest pageRequest) {
        IPage<UserAccountEntity> page = this.page(
                new Query<UserAccountEntity>().getPage(pageRequest),
                new QueryWrapper<UserAccountEntity>()
        );

        return BusinessPageResult.build(page, pageRequest);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Set<String> userIdList, String token) {
        SM4Utils sm4 = new SM4Utils(secretKey);
        int resetNum = userAccountDao.update(null, new UpdateWrapper<UserAccountEntity>().set("account_credential",
                sm4.encryptData_ECB(defaultPassword)).in("user_id", userIdList));
        if (resetNum > 0) {
            sessionService.logout(token);
        }
    }

    @Override
    public BusinessResult<?> changePassword(UserAccountRequestParams userParams) {

        UserAccountEntity userAccountEntity1 =
                userAccountDao.selectOne(new QueryWrapper<UserAccountEntity>().eq("user_id", userParams.getUserId()));
        //判断原密码是否匹配
        if (!userAccountEntity1.getAccountCredential().equals(userParams.getOldPassWord())) {
            return BusinessResult.fail("50009314", ResourceCodeBean.ResourceCode.RESOURCE_CODE_50009314.getMessage());
        }

        UserAccountEntity userAccountEntity = new UserAccountEntity();
        userAccountEntity.setAccountCredential(userParams.getNewPassWord());
        userAccountDao.update(userAccountEntity, new UpdateWrapper<UserAccountEntity>().in("user_id", userParams.getUserId()));

        return BusinessResult.success(true);
    }

    @Override
    public boolean isFirstLogin(String userId) {

        UserAccountEntity userAccountEntity1 =
                userAccountDao.selectOne(new QueryWrapper<UserAccountEntity>().eq("user_id", userId));
        SM4Utils sm4 = new SM4Utils(secretKey);

        //判断密码是否是默认密码
        if (userAccountEntity1.getAccountCredential().equals(sm4.encryptData_ECB(defaultPassword))) {
            return true;
        }
        return false;
    }

}
