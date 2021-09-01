package com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service;

import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.result.BusinessToken;

import java.util.List;


/**
 * 描述 ：令牌服务，两个主要功能，一个是申请令牌，另外一个是令牌鉴权
 *
 * @author jidonglin
 */
public interface TokenService {


    /**
     * 申请token
     *
     * @param userId           用户ID
     * @param customerCode
     * @param roles
     * @param extra
     * @param customerTypeCode
     * @param roleIdList       用户角色
     * @return
     */
    BusinessToken applyToken(Long userId, String customerCode, String roles,
                             String extra, String customerTypeCode, List<Long> roleIdList, String platformUserId);

    /**
     * 申请token
     *
     * @param userId           用户ID
     * @param customerCode
     * @param roles
     * @param extra
     * @param customerTypeCode
     * @param roleIdList       用户角色
     * @param uaaKey           uaaKey
     * @param expireTime       过期时间 单位分钟
     * @return
     */
    BusinessToken applyTokenNew(Long userId, String customerCode, String roles,
                                String extra, String customerTypeCode, List<Long> roleIdList, String platformUserId,
                                String uaaKey, int expireTime);

    /**
     * 校验令牌
     * 校验通过，返回BusinessToken对象
     *
     * @param token
     * @return
     */
    BusinessToken validateToken(String token);

    /**
     * 校验令牌
     * 校验通过，返回BusinessToken对象
     *
     * @param token
     * @param uaaKey
     * @param expireTime
     * @return
     */
    BusinessToken validateTokenNew(String token, String uaaKey, int expireTime);

    /**
     * 移除令牌
     *
     * @param token
     * @return
     */
    String removeToken(String token);

    /**
     * token鉴权
     * 本质上是根据入参token找到一个BusinessToken,如果找不到或者找到但是已经失效，则抛出异常
     *
     * @param interfaceUrl    接口地址
     * @param interfaceMethod 接口方法类型
     * @param token           token值
     * @param requestType
     * @return
     */
    BusinessToken tokenAuth(String interfaceUrl, String interfaceMethod, String token, String requestType);

    /**
     * 更新令牌
     *
     * @param businessToken
     * @return
     */
    BusinessToken updateToken(BusinessToken businessToken);

    BusinessToken refreshToken(String token, String uaaKey, Integer expireTime);


}
