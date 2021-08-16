package com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.impl;

import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.common.em.UaaCodeBean;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.common.em.UaaConstants;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.InterfaceService;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.InterfaceUserAuthService;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.TokenService;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.param.InterfaceUserAuthParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.result.BusinessToken;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.result.InterfaceUserAuthResult;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.log.Logable;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.sequence.api.SequenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * 描述 ：令牌服务，两个主要功能，一个是申请令牌，另外一个是令牌鉴权
 *
 * @author jidonglin
 */
@Service
public class TokenServiceImpl implements TokenService {

    static private Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);

    private static final String TOKEN = "token";

    /**
     * 超管角色编码为0，数据库初始设定的
     */
    private static final String SUPERADMIN_ROLECODE = "0";

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private TokenService self;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    @Autowired(required = false)
    private InterfaceUserAuthService interfaceUserAuthService;

    /**
     * UAA_KEY 外部用户登录token前缀标识
     */
    private static final String UAA_KEY = "UAA:PLATFORM";
    /**
     * 过期时间 单位 分钟
     */
    private static final Integer EXPIRE_TIME = 60 * 8;

    /**
     * 1 内部管理系统  2  外部登录用户
     */
    private static final String REQUEST_TYPE = "1";
    @Autowired
    private InterfaceService interfaceService;

    /**
     * 申请token
     *
     * @param userId           用户ID
     * @param customerCode
     * @param roles
     * @param extra
     * @param customerTypeCode 后台用户给一个默认的客户类型编码
     * @param roleIdList       用户角色
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Logable
    @Override
    public BusinessToken applyToken(Long userId, String customerCode, String roles,
                                    String extra, String customerTypeCode, List<Long> roleIdList, String platformUserId) {
        String uaaKey = "UAA:";
        int expireTime = 30;
        return applyTokenNew(userId, customerCode, roles, extra, customerTypeCode, roleIdList, platformUserId, uaaKey, expireTime);
    }

    @Override
    public BusinessToken applyTokenNew(Long userId, String customerCode, String roles, String extra, String customerTypeCode, List<Long> roleIdList, String platformUserId, String uaaKey, int expireTime) {
        // 生成令牌
        BusinessToken businessToken = new BusinessToken();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        calendar.add(Calendar.MINUTE, expireTime);
        businessToken.setTokenId(sequenceService.nextValue(UaaConstants.UaaSeqName.BUSINESS_TOKEN_ID.name));
        businessToken.setToken(token);
        businessToken.setApplyDate(date);
        businessToken.setUpdateDate(date);
        // 有效期
        businessToken.setEffectiveDate(calendar.getTime());
        // 使用次数
        businessToken.setUseTimes(0L);
        // 用户id
        businessToken.setUserId(userId);
        // 扩展字段
        businessToken.setExtra(extra);
        // customerCode
        businessToken.setCustomerCode(customerCode);
        // 用户角色
        businessToken.setRoles(roles);
        businessToken.setPlatformUserId(platformUserId);
        businessToken.setCustomerTypeCode(customerTypeCode);
        if (roleIdList != null) {
            businessToken.setRoleId(roleIdList.get(0));
        }

        //String userIdWithType = uaaKey + businessType + ":" + userId;
        String userIdWithType = uaaKey + customerTypeCode + ":" + userId;
        String tokenWithUserId = (String) redisTemplate.opsForValue().get(userIdWithType);
        //踢掉已有的用户
        if (tokenWithUserId != null) {
            redisTemplate.delete(uaaKey + tokenWithUserId);
        }
        //token的缓存时间应该为businessType的有效期时间
        redisTemplate.opsForValue().set(userIdWithType, token, expireTime, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(uaaKey + token, businessToken, expireTime, TimeUnit.MINUTES);
        return businessToken;
    }

    /**
     * 校验令牌
     * 校验通过，返回BusinessToken对象
     *
     * @param token
     * @return
     */
    @Logable
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BusinessToken validateToken(String token) {
        String uaaKey = "UAA:";
        int expireTime = 30;
        // 查询令牌
        BusinessToken businessToken = (BusinessToken) redisTemplate.opsForValue().get(uaaKey + token);
        if (businessToken == null) {
            throw new AppException(UaaCodeBean.UaaCode.NOT_EXSIT_TOKEN.code);
        }
        if (System.currentTimeMillis() > businessToken.getEffectiveDate().getTime()) {
            // 已经过期,redis可能还存在，统一令牌不存在
//				throw new AppException(UaaCodeBean.UaaCode.EXPIRED_TOKEN.code);
            throw new AppException(UaaCodeBean.UaaCode.NOT_EXSIT_TOKEN.code);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expireTime);
        businessToken.setEffectiveDate(calendar.getTime());

        // 使用次数+1
        businessToken.setUseTimes(businessToken.getUseTimes() + 1);
        businessToken.setUpdateDate(new Date());
        // 更新缓存中的令牌
        if (updateToken(businessToken) == null) {
            throw new AppException(UaaCodeBean.UaaCode.FAIL.code);
        }
        return businessToken;
    }

    @Logable
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BusinessToken validateTokenNew(String token, String uaaKey, int expireTime) {
        // 查询令牌
        BusinessToken businessToken = (BusinessToken) redisTemplate.opsForValue().get(uaaKey + token);
        if (businessToken == null) {
            throw new AppException(UaaCodeBean.UaaCode.NOT_EXSIT_TOKEN.code);
        }
        if (System.currentTimeMillis() > businessToken.getEffectiveDate().getTime()) {
            // 已经过期,redis可能还存在，统一令牌不存在
//				throw new AppException(UaaCodeBean.UaaCode.EXPIRED_TOKEN.code);
            throw new AppException(UaaCodeBean.UaaCode.NOT_EXSIT_TOKEN.code);
        }
        return businessToken;
    }

    /**
     * 移除令牌
     *
     * @param token
     * @return
     */
    @Logable
    @Override
    public String removeToken(String token) {
        String uaa = "UAA:";
        redisTemplate.delete(uaa + token);
        return token;
    }

    /**
     * token鉴权
     * 本质上是根据入参token找到一个BusinessToken,如果找不到或者找到但是已经失效，则抛出异常
     *
     * @param interfaceUrl    接口地址
     * @param interfaceMethod 接口方法类型
     * @param token           token值
     * @param requestType     1 内部管理系统  2  外部登录用户
     * @return
     */
    @Logable
    @Override
    public BusinessToken tokenAuth(String interfaceUrl, String interfaceMethod, String token, String requestType) {
        // 通过token读取缓存的userType
        BusinessToken businessToken;
        if (REQUEST_TYPE.equals(requestType)) {
            businessToken = self.validateToken(token);
            // 校验按钮权限,只校验按钮类型的url
            authButtonUrl(interfaceUrl, businessToken);
        } else {
            businessToken = self.validateTokenNew(token, UAA_KEY, EXPIRE_TIME);
        }

        return businessToken;
    }

    private void authButtonUrl(String interfaceUrl, BusinessToken businessToken) {
        // 校验该用户是否能访问改路径 解决垂直越权问题
        // 1、是不是跟按钮绑定 直接访问的URL跟按钮写的通配符或者变量能够匹配
        // 2、判断USERID有没有接口对应的按钮的功能权限
        // 如果请求的路径是按钮类型 则校验是否有操作权限权限
        List<String> cachedButtonUrlList = interfaceService.getCachedButtonUrlList();
        if (interfaceUserAuthService != null && cachedButtonUrlList != null && cachedButtonUrlList.contains(interfaceUrl)) {
            boolean isPassAuth = false;
            InterfaceUserAuthParam interfaceUserAuthParam = new InterfaceUserAuthParam();
            interfaceUserAuthParam.setUserId(String.valueOf(businessToken.getUserId()));
            //查询用户可访问的所有按钮类型接口
            BusinessResult<List<InterfaceUserAuthResult>> userAuthInterfaceResult =
                    interfaceUserAuthService.getUserAuthInterfaceIdList(interfaceUserAuthParam);

            if (!userAuthInterfaceResult.isSuccess()) {
                log.error("查询用户可访问的所有接口---{}", userAuthInterfaceResult.getReturnMsg());
                throw new AppException(UaaCodeBean.UaaCode.INVALID_INTERFACE_ROLE.code);
            }
            List<InterfaceUserAuthResult> interfaceResultData = userAuthInterfaceResult.getData();
            for (InterfaceUserAuthResult anInterface : interfaceResultData) {
                // 通过接口url 校验接口是否可以访问
                if (interfaceUrl.equals(anInterface.getUri())) {
                    isPassAuth = true;
                    break;
                }
            }
            if (!isPassAuth) {
                throw new AppException(UaaCodeBean.UaaCode.INVALID_INTERFACE_ROLE.code);
            }
        }
    }

    /**
     * 更新令牌
     *
     * @param businessToken
     * @return
     */
    @Override
    public BusinessToken updateToken(BusinessToken businessToken) {
        redisTemplate.opsForValue().set("UAA:" + businessToken.getToken(), businessToken, 30, TimeUnit.MINUTES);
        return businessToken;
    }

    @Override
    public BusinessToken refreshToken(String token, String uaaKey, Integer expireTime) {
        // 查询令牌
        BusinessToken businessToken = (BusinessToken) redisTemplate.opsForValue().get(uaaKey + token);
        if (businessToken == null) {
            throw new AppException(UaaCodeBean.UaaCode.NOT_EXSIT_TOKEN.code);
        }
        if (System.currentTimeMillis() > businessToken.getEffectiveDate().getTime()) {
            // 已经过期,redis可能还存在，统一令牌不存在
//				throw new AppException(UaaCodeBean.UaaCode.EXPIRED_TOKEN.code);
            throw new AppException(UaaCodeBean.UaaCode.NOT_EXSIT_TOKEN.code);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expireTime);
        businessToken.setEffectiveDate(calendar.getTime());

        // 使用次数+1
        businessToken.setUseTimes(businessToken.getUseTimes() + 1);
        businessToken.setUpdateDate(new Date());
        // 更新缓存中的令牌
        redisTemplate.opsForValue().set(uaaKey +businessToken, businessToken, expireTime, TimeUnit.MINUTES);
        return businessToken;
    }
}
