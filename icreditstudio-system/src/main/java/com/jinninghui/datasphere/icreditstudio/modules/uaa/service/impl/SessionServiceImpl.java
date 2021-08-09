package com.jinninghui.datasphere.icreditstudio.modules.uaa.service.impl;

import com.jinninghui.datasphere.icreditstudio.modules.uaa.common.constant.CommonConstant;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.common.em.UaaCodeBean;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.BusinessUserAccountService;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.SessionService;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.TokenService;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.param.UserAccountGetsForm;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.param.UserRoleResRequest;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result.OperateLoginResponse;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result.PlatformUserAuthResponse;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result.UserAccountEntityResult;
import com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result.UserRoleResResult;
import com.hashtech.businessframework.exception.interval.AppException;
import com.hashtech.businessframework.log.Logable;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.utils.sm4.SM4Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jidonglin
 */
@Slf4j
@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private BusinessUserAccountService businessUserAccountService;

    @Resource
    private HttpServletRequest request;


    @Logable
    @Override
    public void logout(String token) {
        tokenService.removeToken(token);
    }

    /**
     * UAA_KEY 外部用户登录token前缀标识
     */
    private static final String UAA_KEY = "UAA:PLATFORM";
    /**
     * 过期时间 单位 分钟
     */
    private static final Integer EXPIRE_TIME = 60 * 8;
    /**
     * 过期时间 单位 秒
     */
    private static final Integer VALID_TIME = 8 * 60 * 60;

    @Value("${sm4.secretKey}")
    private String secretKey;


    @Override
    public BusinessResult<OperateLoginResponse> backstageUserLogin(String username, String password,
                                                                   String applicationId) {
        UserAccountGetsForm userAccountGetsForm = new UserAccountGetsForm();
        userAccountGetsForm.setIdentifier(username);
        BusinessResult<UserAccountEntityResult> businessResult = businessUserAccountService.get(userAccountGetsForm);
        if (!businessResult.isSuccess()) {
            throw new AppException(businessResult.getReturnCode(), businessResult.getReturnMsg());
        }
        UserAccountEntityResult userAccountResult = businessResult.getData();
        //校验用户合法性
        if (userAccountResult == null || !userAccountResult.getAccountCredential().equals(password)) {
            throw new AppException(UaaCodeBean.UaaCode.PWD_OR_ACCOUNT_ERROR.code);
        }
        //更改加密方式  前端加密
       /* if (!userAccountResult.getAccountCredential().equals(password)) {
            throw new AppException(UaaCodeBean.UaaCode.ERROR_LOGINPWD.code);
        }*/
        if (CommonConstant.DELETE_FLAG_Y.equals(userAccountResult.getDeleteFlag())) {
            throw new AppException(UaaCodeBean.UaaCode.INVALID_LOGIN_USER_STATE.code);
        }

        // 校验用户角色
        if (!businessUserAccountService.isVerifyRoleEffective(userAccountResult.getUserId())) {
            throw new AppException(UaaCodeBean.UaaCode.INVALID_LOGIN_USER_ROLE_STATE.code);
        }

        // 校验用户 部门
        if (!businessUserAccountService.isVerifyOrgEffective(userAccountResult.getUserId())) {
            throw new AppException(UaaCodeBean.UaaCode.INVALID_LOGIN_USER_ORG_STATE.code);
        }

//		if (!BCrypt.checkpw(password, userAccountResult.getAccountCredential())){
//			throw new AppException(UaaCodeBean.UaaCode.ERROR_LOGINPWD.code);
//		}

        // 用户id
        String userId = userAccountResult.getUserId();
        // 申请token
        String token = tokenService.applyToken(Long.valueOf(userId), null, null, "extra",
                "BUSINESS_RES", null, "").getToken();
//		//获取用户角色信息
//		UserInfoGetsForm userInfoGetsForm = new UserInfoGetsForm();
//		List<String> ids = new ArrayList<>();
//		ids.add(userAccountResult.getUserId());
//		userInfoGetsForm.setIds(ids);
//		BusinessResult<List<UserInfoResult>> userInfoResult = businessUserAccountService.userGets(userInfoGetsForm);
//
//		UserRoleQueryForm userRoleQueryForm = new UserRoleQueryForm();
//		userRoleQueryForm.setUserId(userAccountResult.getUserId());
//		userRoleQueryForm.setApplicationId(applicationId);
//		BusinessResult<List<RoleInfoResult>> roleInfoResult = businessUserAccountService.roleInfoQuery(userRoleQueryForm);
//
//		//更新登录成功时间
//		UserAccountEditForm userAccountEditForm = new UserAccountEditForm();
//		userAccountEditForm.setId(userAccountResult.getId());
//		userAccountEditForm.setUserId(userAccountResult.getUserId());
//		userAccountEditForm.setLastLoginTime(System.currentTimeMillis());
//		businessUserAccountService.edit(userAccountEditForm);
        UserRoleResRequest userRoleResRequest = new UserRoleResRequest();
        userRoleResRequest.setUserAccountId(userAccountResult.getId());
        userRoleResRequest.setUserId(userId);
        userRoleResRequest.setApplicationId(applicationId);
        BusinessResult<UserRoleResResult> userRoleResResult = businessUserAccountService.getRoleResAndUpdateLoginTime(userRoleResRequest);
        OperateLoginResponse operateLoginResponse = new OperateLoginResponse();
        operateLoginResponse.setToken(token);
        operateLoginResponse.setUserId(userId);
        operateLoginResponse.setUserAccountId(userAccountResult.getId());
        operateLoginResponse.setUserInfoResult(userRoleResResult.getData().getUserInfoResults().get(0));
        operateLoginResponse.setRoleInfoResultList(userRoleResResult.getData().getRoleInfoResults());
        return BusinessResult.success(operateLoginResponse);
    }

    @Override
    public BusinessResult<PlatformUserAuthResponse> getToken(String username, String password) {
        PlatformUserAuthResponse platformUserAuthResponse = new PlatformUserAuthResponse();

        UserAccountGetsForm userAccountGetsForm = new UserAccountGetsForm();
        userAccountGetsForm.setIdentifier(username);
        BusinessResult<UserAccountEntityResult> businessResult = businessUserAccountService.get(userAccountGetsForm);
        if (!businessResult.isSuccess()) {
            throw new AppException(businessResult.getReturnCode(), businessResult.getReturnMsg());
        }
        UserAccountEntityResult userAccountResult = businessResult.getData();
        if (userAccountResult == null) {
            throw new AppException(UaaCodeBean.UaaCode.NOT_EXIST_USER.code);
        }
        if (CommonConstant.DELETE_FLAG_Y.equals(userAccountResult.getDeleteFlag())) {
            throw new AppException(UaaCodeBean.UaaCode.INVALID_LOGIN_USER_STATE.code);
        }

        //数据库密码先解密，在匹配
        SM4Utils sm4 = new SM4Utils(secretKey);
        String tablePassWord = sm4.decryptData_ECB(userAccountResult.getAccountCredential());
        if (!tablePassWord.equals(password)) {
            throw new AppException(UaaCodeBean.UaaCode.ERROR_LOGINPWD.code);
        }
        // 用户id
        String userId = userAccountResult.getUserId();
        // 申请token
        String token = tokenService.applyTokenNew(Long.valueOf(userId), null, null, "extra",
                "BUSINESS_PLATFORM", null, "", UAA_KEY, EXPIRE_TIME).getToken();

        platformUserAuthResponse.setAccessToken(token);
        platformUserAuthResponse.setValidTime(VALID_TIME);
        return BusinessResult.success(platformUserAuthResponse);
    }

    @Override
    public BusinessResult<PlatformUserAuthResponse> refreshToken(String token) {
        PlatformUserAuthResponse platformUserAuthResponse = new PlatformUserAuthResponse();

        tokenService.refreshToken(token, UAA_KEY, EXPIRE_TIME);

        platformUserAuthResponse.setAccessToken(token);
        platformUserAuthResponse.setValidTime(VALID_TIME);
        return BusinessResult.success(platformUserAuthResponse);
    }

    @Override
    public String getUserId() {
        String userId = "";
        try {
            userId = request.getHeader("x-userid");
        } catch (Exception e) {
        }
        return userId;
    }

    @Override
    public String getToken() {
        String token = request.getHeader("x-token");
        if (StringUtils.isEmpty(token)) {
            token = request.getHeader("x-Access-Token");
        }
        return token;
    }


}
