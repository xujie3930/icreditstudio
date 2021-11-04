package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.Query;
import com.jinninghui.datasphere.icreditstudio.framework.utils.sm4.SM4Utils;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.common.code.ResourceCodeBean;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity.UserAccountEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.mapper.UserAccountDao;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.UserAccountService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.param.UserAccountQueryConditionParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.request.UserAccountEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.request.UserAccountRequestParams;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.SessionService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public BusinessResult<Map<String, String>> getUserExecCode(String userId) {
        UserAccountQueryConditionParam build = UserAccountQueryConditionParam.builder()
                .userId(userId)
                .build();
        QueryWrapper<UserAccountEntity> wrapper = queryWrapper(build);
        List<UserAccountEntity> userAccountEntities = userAccountDao.selectList(wrapper);

        Map<String, String> results = new HashMap<>(2);
        results.put("id", userId);
        if (CollectionUtils.isNotEmpty(userAccountEntities)) {
            UserAccountEntity userAccountEntity = userAccountEntities.get(0);
            results.put("tenantCode", userAccountEntity.getAccountIdentifier());
        } else {
            throw new AppException("50009384");
        }
        return BusinessResult.success(results);
    }

    private QueryWrapper<UserAccountEntity> queryWrapper(UserAccountQueryConditionParam param) {
        QueryWrapper<UserAccountEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getUserId())) {
            wrapper.eq(UserAccountEntity.USER_ID, param.getUserId());
        }
        return wrapper;
    }
}
