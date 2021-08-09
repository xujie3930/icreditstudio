package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.jinninghui.icreditdatasphere.icreditstudio.common.log.Log;
import com.jinninghui.icreditdatasphere.icreditstudio.common.log.LogHandler;
import com.jinninghui.icreditdatasphere.icreditstudio.common.log.LogHandlerUtil;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.log.entity.LoginLogEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.log.service.LoginLogService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.log.service.param.LoginLogEntitySaveParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.entity.UserAccountEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.entity.UserEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.UserAccountService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.UserService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.param.UserAccountEntityConditionParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.param.UserEntityConditionParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.result.OperateLoginResponse;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.web.request.OperateLoginRequest;
import com.hashtech.businessframework.result.BusinessResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by PPai on 2021/6/2 10:58
 */
@Component
@Slf4j
public class LoginLogHandler implements LogHandler<LoginLogEntity> {
    @Autowired
    private LoginLogService loginLogService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserAccountService accountService;

    @Override
    public LoginLogEntity pre(HttpServletRequest request, Method method, Object[] args) {
        LogHandlerUtil util = LogHandlerUtil.build(method);
        util.filter((type, operateType) -> Log.Type.LOGIN.equals(type) && Log.OperateType.LOGIN.equals(operateType));
        LoginLogEntity entity = new LoginLogEntity();
        Optional<LoginLogEntity> map = util.map((type, operateType) -> {
            LoginLogEntitySaveParam param = new LoginLogEntitySaveParam();
            for (Object arg : args) {
                if (arg instanceof OperateLoginRequest) {
                    OperateLoginRequest loginRequest = (OperateLoginRequest) arg;
                    String loginName = loginRequest.getLoginName();
                    param.setUserAccount(loginName);
                    List<UserAccountEntity> userAccounts = getUserAccounts(UserAccountEntityConditionParam.builder().userAccount(loginName).build());
                    Set<String> userIds = findUserIds(userAccounts);
                    List<UserEntity> users = getUsers(UserEntityConditionParam.builder().ids(userIds).build());
                    String userNameByAccount = getUserNameByAccount(loginRequest.getLoginName(), users, userAccounts);
                    param.setUserName(userNameByAccount);
                }
            }
            param.setUserIp(request.getRemoteAddr());
            param.setLoginTime(String.valueOf(System.currentTimeMillis()));
            return loginLogService.log(param);
        });
        if (map.isPresent()) {
            entity = map.get();
        }
        return entity;
    }

    @Override
    public void post(HttpServletRequest request, Method method, Object o, LoginLogEntity logEntity) {
        LogHandlerUtil util = LogHandlerUtil.build(method);
        util.filter((type, operateType) -> Log.Type.LOGIN.equals(type));
        util.filter((type, operateType) -> Log.OperateType.LOGIN.equals(operateType) && o instanceof BusinessResult);
        util.map((type, operateType) -> {
            boolean flag = true;
            BusinessResult result = (BusinessResult) o;
            if (result.isSuccess() && StringUtils.isNotBlank(logEntity.getId())) {
                OperateLoginResponse data = (OperateLoginResponse) result.getData();
                String userId = data.getUserId();
                UserEntity byId = userService.getById(userId);
                logEntity.setUserName(byId.getUserName());
//                logEntity.setUserToken(data.getToken());
                logEntity.setLoginStatus("S");
                flag = loginLogService.saveOrUpdate(logEntity);
            }
            return flag;
        });
        util.filter((type, operateType) -> Log.OperateType.LOGOUT.equals(operateType));
        util.consumer((type, operateType) -> {
            Cookie[] cookies = request.getCookies();
            String token = null;
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
            List<LoginLogEntity> logByToken = loginLogService.getLogByToken(token);
            if (CollectionUtils.isNotEmpty(logByToken)) {
                LoginLogEntity entity = logByToken.get(0);
                entity.setLogoutTime(String.valueOf(System.currentTimeMillis()));
                entity.setUserToken(token);
                loginLogService.saveOrUpdate(entity);
            }
        });
    }

    @Override
    public void ex(HttpServletRequest request, Method method, Throwable t, LoginLogEntity logEntity) {
        LogHandlerUtil util = LogHandlerUtil.build(method);
        util.filter((type, operateType) -> Log.Type.LOGIN.equals(type)
                && Log.OperateType.LOGIN.equals(operateType)
                && StringUtils.isNotBlank(logEntity.getId()));
        util.consumer((type, operateType) -> {
            logEntity.setLoginStatus("F");
            String appExceptionErrorMsg = LogHandlerUtil.getAppExceptionErrorMsg(t);
            logEntity.setErrorInfo(appExceptionErrorMsg);
            loginLogService.saveOrUpdate(logEntity);
        });
    }

    private Set<String> findUserIds(List<UserAccountEntity> userAccounts) {
        return userAccounts.parallelStream()
                .filter(Objects::nonNull)
                .map(UserAccountEntity::getUserId)
                .collect(Collectors.toSet());
    }

    private String getUserNameByAccount(String account, List<UserEntity> users, List<UserAccountEntity> userAccounts) {
        String u = "";
        List<String> userNameByAccounts = getUserNameByAccounts(account, users, userAccounts);
        if (CollectionUtils.isNotEmpty(userNameByAccounts)) {
            u = userNameByAccounts.get(0);
        }
        return u;
    }

    private List<String> getUserNameByAccounts(String account, List<UserEntity> users, List<UserAccountEntity> userAccounts) {
        List<String> results = Lists.newArrayList();
        if (StringUtils.isNotBlank(account) && CollectionUtils.isNotEmpty(users) && CollectionUtils.isNotEmpty(userAccounts)) {
            Set<String> userIds = userAccounts.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(userAccountEntity -> account.equals(userAccountEntity.getAccountIdentifier()))
                    .map(UserAccountEntity::getUserId)
                    .collect(Collectors.toSet());
            results = users.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(userEntity -> userIds.contains(userEntity.getId()))
                    .map(UserEntity::getUserName)
                    .collect(Collectors.toList());
        }
        return results;
    }

    private List<UserEntity> getUsers(UserEntityConditionParam param) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(UserEntity.ID, param.getIds());
        }
        return userService.list(wrapper);
    }

    private List<UserAccountEntity> getUserAccounts(UserAccountEntityConditionParam param) {
        QueryWrapper<UserAccountEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getUserAccount())) {
            wrapper.eq(UserAccountEntity.ACCOUNT_IDENTIFIER, param.getUserAccount());
        }
        return accountService.list(wrapper);
    }
}
