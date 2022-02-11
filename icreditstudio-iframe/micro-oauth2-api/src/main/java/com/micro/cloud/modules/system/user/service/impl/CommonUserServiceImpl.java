package com.micro.cloud.modules.system.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.cloud.api.CommonResult;
import com.micro.cloud.api.ResultCode;
import com.micro.cloud.cache.localCache.LayoutCache;
import com.micro.cloud.constant.AuthConstant;
import com.micro.cloud.constant.SysErrorCodeConstants;
import com.micro.cloud.constant.SysOauthConstant;
import com.micro.cloud.domian.dto.Oauth2TokenDto;
import com.micro.cloud.domian.dto.SysLoginLogCreateReqDTO;
import com.micro.cloud.domian.dto.UserDTO;
import com.micro.cloud.domian.dto.UserRoles;
import com.micro.cloud.enums.SysCommonStatusEnum;
import com.micro.cloud.enums.SysLoginResultEnum;
import com.micro.cloud.exception.ApiException;
import com.micro.cloud.modules.system.common.service.SysCaptchaService;
import com.micro.cloud.modules.system.common.service.SysSmsCodeService;
import com.micro.cloud.modules.system.log.service.SysLoginLogService;
import com.micro.cloud.modules.system.org.dataobject.SysOrg;
import com.micro.cloud.modules.system.org.mapper.SysOrgMapper;
import com.micro.cloud.modules.system.resource.vo.SysResourceRespVO;
import com.micro.cloud.modules.system.role.mapper.SysRoleResourceRefMapper;
import com.micro.cloud.modules.system.user.convert.LoginParamConvert;
import com.micro.cloud.modules.system.user.convert.SysUserConvert;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.modules.system.user.dataobject.SysUserAccount;
import com.micro.cloud.modules.system.user.mapper.SysUserAccountMapper;
import com.micro.cloud.modules.system.user.mapper.SysUserMapper;
import com.micro.cloud.modules.system.user.mapper.SysUserOrgRefMapper;
import com.micro.cloud.modules.system.user.service.AuthService;
import com.micro.cloud.modules.system.user.service.CommonUserService;
import com.micro.cloud.modules.system.user.validate.UserCommonOperateValidate;
import com.micro.cloud.modules.system.user.vo.*;
import com.micro.cloud.modules.system.user.vo.org.OrgUserPageRepVO;
import com.micro.cloud.redis.service.RedisService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.micro.cloud.util.servlet.ServletUtils.getClientIP;

/**
 * 外部用户管理
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Service("commonUserServiceImpl")
public class CommonUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements CommonUserService {

    private final Logger logger = LoggerFactory.getLogger(CommonUserServiceImpl.class);

    @Value("${idCard.address}")
    private String authAddress;

    @Autowired
    private AuthService authService;

    @Autowired
    private SysCaptchaService captchaService;

    @Autowired
    private SysLoginLogService loginLogService;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysOrgMapper orgMapper;

    @Autowired
    private SysUserOrgRefMapper userOrgRefMapper;

    @Autowired
    private SysUserAccountMapper accountMapper;

    @Autowired
    private SysRoleResourceRefMapper roleResourceRefMapper;

    @Autowired
    private UserCommonOperateValidate operateValidate;

//  @Autowired private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SysSmsCodeService smsCodeService;

    /**
     * 个人用户注册服务
     *
     * @param vo 个人用户注册请求
     * @return 注册是否成功
     */
    @Override
    public Boolean auth(SysUserAuthReqVO vo) {
        // 已实名用户无需再次验证
        // 校验用户是否存在
        operateValidate.checkUserExists(vo.getUserId());
        // todo 图片验证码校验 待定
        // todo 短信码校验 待定
        // 调用实名认证接口
        return true;
    }

    @Override
    public SysLoginRepVo login(SysLoginReqVo reqVo) {
        // 校验验证码是否合法
        captchaService.verifyCaptcha(reqVo.getCaptchaCode());
        return getSysLoginRepVo(reqVo);
    }

    private SysLoginRepVo getSysLoginRepVo(SysLoginReqVo reqVo) {
        // 校验用户合法性
        SysUser user = userMapper.selectOne("user_name", reqVo.getUsername());
        operateValidate.checkUserLegality(user, reqVo.getPassword());
        // 目前请求token关键信息均存放入于SysOauthConstant中，后续可改为DB存储或使用nacos配置中心进行统一配置
        Map<String, String> params = new HashMap<>();
        params.put("client_id", SysOauthConstant.JNH_CLIENT_ID);
        params.put("client_secret", SysOauthConstant.JNH_CLIENT_SECRET);
        params.put("grant_type", "password");
        params.put("password", reqVo.getPassword());
        params.put("scope", "all");
        UserDTO userDTO = getUserInfoByName(user.getUserName());
        String userInfo = JSON.toJSONString(userDTO);
        params.put("username", userInfo);
        CommonResult<Oauth2TokenDto> restResult = authService.getAccessToken(params);
        logger.info("####### restResult:{}", restResult);
        if (ResultCode.SUCCESS.getCode() == restResult.getCode()
                && Objects.nonNull(restResult.getData())) {
            SysLoginRepVo result =
                    JSON.parseObject(JSON.toJSONString(restResult.getData()), SysLoginRepVo.class);
            // 获取用户通用信息
            CommonUserInfoRepVO commonInfo = userMapper.getCommonInfo(user.getSysUserId());
            //获取组织机构信息
            if (StringUtils.isNotBlank(commonInfo.getOrgId())) {
                SysOrg topOrg = getTopOrg(commonInfo.getOrgId());
                if (null != topOrg) {
                    commonInfo.setTopOrgId(topOrg.getSysOrgId());
                    commonInfo.setTopOrgName(topOrg.getOrgName());
                }
            }
            commonInfo.setRoles(userDTO.getRoles());
            result.setUserInfo(commonInfo);
            // 封装用户权限信息
            List<SysResourceRespVO> resources =
                    roleResourceRefMapper.getUserResourceById(user.getSysUserId());
            Optional.ofNullable(resources)
                    .ifPresent(
                            resource -> {
                                Map<Integer, List<SysResourceRespVO>> resourceMap =
                                        resource.stream()
                                                .filter(respVO -> Objects.nonNull(respVO.getLayout()))
                                                .collect(Collectors.groupingBy(SysResourceRespVO::getLayout));
                                Map<String, List<String>> resultMap = new TreeMap<>();
                                resourceMap.entrySet().stream()
                                        .forEach(
                                                entry -> {
                                                    List<String> codes =
                                                            entry.getValue().stream()
                                                                    .map(SysResourceRespVO::getCode)
                                                                    .collect(Collectors.toList());
                                                    resultMap.put(LayoutCache.LAYOUT_CACHE.get(entry.getKey()), codes);
                                                });

                                result.setResources(resultMap);
                            });
            // 添加登录成功日志
            SysLoginLogCreateReqDTO loginLog = new SysLoginLogCreateReqDTO();
            loginLog.setUsername(user.getUserName());
            loginLog.setResult(SysLoginResultEnum.SUCCESS.getResult());
            loginLogService.createLoginLog(loginLog);
            return result;
        }
        // 添加登录失败日志
        SysLoginLogCreateReqDTO loginLog = new SysLoginLogCreateReqDTO();
        loginLog.setUsername(user.getUserName());
        loginLog.setResult(SysLoginResultEnum.FAILURE.getResult());
        loginLogService.createLoginLog(loginLog);
        return null;
    }

    /**
     * 递归查询最上级部门
     */
    private SysOrg getTopOrg(String orgId) {
        SysOrg sysOrg = orgMapper.selectById(orgId);
        String parentId = sysOrg.getParentId();
        if (StringUtils.isNotBlank(parentId)) {
            sysOrg = getTopOrg(parentId);
        }
        return sysOrg;
    }

    /**
     * 用户登录-无验证码
     *
     * @param reqVo 用户登录请求参数
     * @return 登录结果
     */
    @Override
    public SysLoginRepVo loginWithNoCaptcha(SysLoginWithoutCaptchaReqVo reqVo) {
        SysLoginReqVo sysLoginReqVo = LoginParamConvert.INSTANCE.convertVo(reqVo);
        return getSysLoginRepVo(sysLoginReqVo);
    }

    /**
     * 用户重置密码
     *
     * @param reqVO 重置密码请求参数
     * @return 是否成功
     */
    @Override
    public Boolean passwordReset(PasswordResetReqVO reqVO) {
        // 校验验证码是否合法
        captchaService.verifyCaptcha(reqVO.getCaptchaCode());
        // confirmPassword待校验
        // 校验用户合法性
        SysUser user = userMapper.selectOne("phone", reqVO.getMobile());
        operateValidate.checkUserLegality(user, null);
        // 更新用户账户密码
        SysUserAccount userAccount = accountMapper.selectOne("sys_user_id", user.getSysUserId());
        userAccount.setCredential(reqVO.getPassword());
        accountMapper.updateById(userAccount);
        return true;
    }

    /**
     * 用户更改密码(内部使用)
     *
     * @param reqVO 更改密码请求参数
     * @return 是否成功
     */
    @Override
    public Boolean updatePassword(UpdatePasswordReqVO reqVO) {
        // 校验用户是否存在
        operateValidate.checkUserExists(reqVO.getUserId());
        SysUser sysUser = userMapper.selectById(reqVO.getUserId());
        // 判断当前用户输入密码是否正确
        operateValidate.checkUserLegality(sysUser, reqVO.getOriginal());
        // 获取账户愿密码
        SysUserAccount userAccount = accountMapper.selectOne("sys_user_id", reqVO.getUserId());
        // 校验密码是否一致
        operateValidate.checkUserPassword(
                reqVO.getUserId(),
                reqVO.getNewPassword(),
                reqVO.getConfirmPassword(),
                userAccount.getCredential());
        // 更新用户账户密码
        userAccount.setCredential(reqVO.getNewPassword());
        accountMapper.updateById(userAccount);
        return true;
    }

    /**
     * 根据用户名获取用户相关信息
     *
     * @param username 用户名
     * @return 用户相关信息
     */
    @Override
    public UserDTO getUserInfoByName(String username) {
        // 获取用户信息
        SysUser sysUser = userMapper.selectOne("user_name", username);
        // 用户状态是否启用/账号是否正常
        // checkUserStatus(sysUser);
        String userId = sysUser.getSysUserId();
        Boolean status = sysUser.getStatus();
        // 获取用户账户信息
        SysUserAccount userAccount = accountMapper.selectOne("sys_user_id", sysUser.getSysUserId());
        String credential = userAccount.getCredential();
        // 获取用户部门信息
        SysOrg org = userOrgRefMapper.getDepartByUserId(userId);
        // 获取用户角色
        List<UserRoles> userRoles = userMapper.getUserRoles(userId);
        return new UserDTO(
                Long.valueOf(userId),
                username,
                sysUser.getRealName(),
                sysUser.getPhone(),
                sysUser.getEmail(),
                org == null ? "Default" : org.getSysOrgId(),
                SysOauthConstant.JNH_CLIENT_ID,
                org == null ? "" : org.getOrgName(),
                sysUser.getType(),
                credential,
                status,
                userRoles);
    }

    private void checkUserStatus(SysUser sysUser) {
        if (Objects.isNull(sysUser)) {
            throw new ApiException(SysErrorCodeConstants.USER_NOT_EXISTS);
        }
        // 用户状态判断
        if (SysCommonStatusEnum.DISABLE.getStatus().booleanValue()
                == sysUser.getStatus().booleanValue()) {
            throw new ApiException(SysErrorCodeConstants.USER_DISABLE);
        }
        // 用户账号是否正常
        SysUserAccount userAccount = accountMapper.selectOne("sys_user_id", sysUser.getSysUserId());
        if (SysCommonStatusEnum.DISABLE.getStatus().booleanValue()
                == userAccount.getStatus().booleanValue()) {
            throw new ApiException(SysErrorCodeConstants.USER_ACCOUNT_DISABLE);
        }
        if (SysCommonStatusEnum.ENABLE.getStatus().equals(userAccount.getAccountLocked())) {
            throw new ApiException(SysErrorCodeConstants.USER_ACCOUNT_LOCKED);
        }
    }

    /**
     * 用户登出
     *
     * @param request HttpServletRequest请求
     * @return true/false
     */
    @Override
    public Boolean logout(HttpServletRequest request) {
        String authorization = request.getHeader(AuthConstant.JWT_TOKEN_HEADER);
        logger.info("######### authorization:{}", authorization);
        if (StringUtils.isBlank(authorization)) {
            return false;
        }
        String token = authorization.replace("Bearer ", "");
        logger.info("####### token:{}", token);
        // 移除token,refresh_token等相关信息
        return redisService.del(AuthConstant.ACCESS_TOKEN + token);
    }

    /**
     * 根据用户名密码获取token(外部系统对接使用)
     *
     * @param reqVo 请求token参数
     * @return token
     */
    @Override
    public GenerateTokenRepVo generateToken(GenerateTokenReqVo reqVo) {
        // 校验用户合法性
        SysUser user = userMapper.selectOne("user_name", reqVo.getUsername());
        operateValidate.checkUserLegality(user, reqVo.getPassword());
        // 目前请求token关键信息均存放入于SysOauthConstant中，后续可改为DB存储或使用nacos配置中心进行统一配置
        Map<String, String> params = new HashMap<>();
        params.put("client_id", SysOauthConstant.JNH_CLIENT_ID);
        params.put("client_secret", SysOauthConstant.JNH_CLIENT_SECRET);
        params.put("grant_type", "password");
        params.put("password", reqVo.getPassword());
        params.put("scope", "all");
        UserDTO userDTO = getUserInfoByName(user.getUserName());
        String userInfo = JSON.toJSONString(userDTO);
        params.put("username", userInfo);
        CommonResult<Oauth2TokenDto> restResult = authService.getAccessToken(params);
        if (ResultCode.SUCCESS.getCode() == restResult.getCode()
                && Objects.nonNull(restResult.getData())) {
            GenerateTokenRepVo result =
                    JSON.parseObject(JSON.toJSONString(restResult.getData()), GenerateTokenRepVo.class);
            // 添加登录成功日志
            SysLoginLogCreateReqDTO loginLog = new SysLoginLogCreateReqDTO();
            loginLog.setUsername(user.getUserName());
            loginLog.setResult(SysLoginResultEnum.SUCCESS.getResult());
            loginLogService.createLoginLog(loginLog);
            return result;
        }
        // 添加登录失败日志
        SysLoginLogCreateReqDTO loginLog = new SysLoginLogCreateReqDTO();
        loginLog.setUsername(user.getUserName());
        loginLog.setResult(SysLoginResultEnum.FAILURE.getResult());
        loginLogService.createLoginLog(loginLog);
        return null;
    }

    @Override
    public CommonUserInfoVo getCurrentUserInfo(HttpServletRequest request) {
        String userId = request.getHeader(AuthConstant.USER_ID_HEADER);
        if (org.apache.commons.lang3.StringUtils.isBlank(userId)) {
            throw new ApiException(SysErrorCodeConstants.USER_NOT_EXISTS);
        }
        // 获取用户个人信息
        SysUser sysUser = userMapper.selectById(userId);
        CommonUserInfoVo commonUserInfoVo = SysUserConvert.INSTANCE.convertCommonInfoVO(sysUser);
        // 获取用户角色
        commonUserInfoVo.setRoles(userMapper.getUserRoles(userId));
        return commonUserInfoVo;
    }

    /**
     * 更新用户信息
     *
     * @param vo
     * @return
     */
    @Override
    public Boolean updateCommonInfo(CommonUserInfoVo vo) {
        operateValidate.checkEmailUnique(vo.getUserId(), vo.getEmail());
        SysUser sysUser = SysUserConvert.INSTANCE.convertDO(vo);
        userMapper.updateById(sysUser);
        return true;
    }

    /**
     * 用户名密码校验
     *
     * @param reqVo 用户名密码校验请求参数
     * @return 是否成功
     */
    @Override
    public Boolean validate(UserValidateReqVo reqVo) {
        // 校验用户合法性
        SysUser user = userMapper.selectOne("user_name", reqVo.getUsername());
        operateValidate.checkUserLegality(user, reqVo.getPassword());
        return true;
    }

    /**
     * 用户更换手机号校验
     *
     * @param reqVo 更换手机号请求参数
     * @return 校验过后的手机号
     */
    @Override
    public Boolean validatePhone(PhoneValidateReqVo reqVo) {
        // 校验手机号是否重复
        operateValidate.checkMobileUnique(reqVo.getUserId(), reqVo.getPhone());
        // 校验验证码是否合法
        captchaService.verifyCaptcha(reqVo.getCaptchaCode());
        // 短信码校验,场景目前固定为1， 表示注册验证
        smsCodeService.useSmsCode(reqVo.getPhone(), 1, reqVo.getMessageCode(), getClientIP());
        SysUser sysUser = new SysUser();
        sysUser.setSysUserId(reqVo.getUserId());
        sysUser.setPhone(reqVo.getPhone());
        userMapper.updateById(sysUser);
        return true;
    }

    /**
     * 根据用户id集合批量获取用户部门等信息
     *
     * @param ids 用户id集合
     * @return 用户部门等信息
     */
    @Override
    public Map<String, OrgUserPageRepVO> getOrgInfoByUserIds(List<String> ids) {
        return orgMapper.getOrgByUserIds(ids);
    }
}
