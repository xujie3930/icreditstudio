package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.Query;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.sequence.api.SequenceService;
import com.jinninghui.datasphere.icreditstudio.framework.utils.Base64Utils;
import com.jinninghui.datasphere.icreditstudio.framework.utils.excel.EasyExcelUtil;
import com.jinninghui.datasphere.icreditstudio.framework.utils.sm4.SM4Utils;
import com.jinninghui.datasphere.icreditstudio.framework.validate.BusinessParamsValidate;
import com.jinninghui.datasphere.icreditstudio.system.common.enums.DeleteFlagEnum;
import com.jinninghui.datasphere.icreditstudio.system.common.enums.IdentityTypeEnum;
import com.jinninghui.datasphere.icreditstudio.system.common.utils.ExcelConvertUtil;
import com.jinninghui.datasphere.icreditstudio.system.common.utils.MobileUtil;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result.SelectInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.service.OrganizationService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.service.param.OrganizationEntityQueryParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.result.ExpertInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.result.OrganizationInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.entity.RoleEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.mapper.RoleDao;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.service.RoleService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.service.result.RoleEntityInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.web.request.RoleEntityQueryParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity.*;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.mapper.UserDao;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.mapper.UserRoleMapDao;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.UserAccountService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.UserOrgMapService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.UserRoleMapService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.UserService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.param.*;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.SessionService;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.TokenService;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.result.PlatformUserAuthResponse;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.request.*;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.result.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.jinninghui.datasphere.icreditstudio.system.modules.system.common.code.CommonConstant.DELETE_FLAG_N;


@Service("userService")
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserRoleMapService userRoleMapService;
    @Autowired
    private UserOrgMapService userOrgMapService;
    @Autowired
    private SequenceService generalSequence;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserRoleMapDao userRoleMapDao;
    @Autowired
    private RoleDao roleDao;

    @Value("${account.defaultPassword}")
    private String defaultPassword;
    @Value("${sm4.secretKey}")
    private String secretKey;

    @Override
    @SuppressWarnings("all")
    public BusinessPageResult queryPage(UserEntityPageRequest pageRequest, String loginUserId) {
        Set<String> userIds = Sets.newHashSet(loginUserId);
        List<UserOrgMapEntity> orgByUserIds = userOrgMapService.getOrgByUserIds(userIds);
        Set<String> currOrgs = orgByUserIds.parallelStream()
                .filter(Objects::nonNull)
                .map(UserOrgMapEntity::getOrgId)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());
        QueryWrapper<OrganizationEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(pageRequest.getDeleteFlag())) {
            wrapper.eq(OrganizationEntity.DELETE_FLAG, pageRequest.getDeleteFlag());
        }
        List<OrganizationEntity> allOrgs = organizationService.list(wrapper);
        Set<String> currAndSonOrgs = loopFindSonOrgId(currOrgs, allOrgs);
        Set<String> orgIds = pageRequest.getOrgIds();
        if (CollectionUtils.isNotEmpty(orgIds)) {
            currAndSonOrgs.retainAll(orgIds);
        }
        List<UserEntityResult> userEntityResults = Lists.newArrayList();
        long total = 0;
        if (CollectionUtils.isNotEmpty(currAndSonOrgs)) {
            QueryWrapper<UserOrgMapEntity> queryUserWrapper = new QueryWrapper<>();
            queryUserWrapper.in(UserOrgMapEntity.ORG_ID, currAndSonOrgs);
            List<UserOrgMapEntity> userOrgMapEntities = userOrgMapService.list(queryUserWrapper);
            Set<String> findUserIds = userOrgMapEntities.parallelStream()
                    .filter(Objects::nonNull)
                    .map(UserOrgMapEntity::getUserId)
                    .collect(Collectors.toSet());
            if (StringUtils.isNotBlank(pageRequest.getAccountIdentifier())) {
                QueryWrapper<UserAccountEntity> accountWrapper = new QueryWrapper<>();
                accountWrapper.like(UserAccountEntity.ACCOUNT_IDENTIFIER, pageRequest.getAccountIdentifier());
                List<UserAccountEntity> accountEntities = userAccountService.list(accountWrapper);
                Set<String> getUserIds = accountEntities.parallelStream()
                        .map(UserAccountEntity::getUserId)
                        .collect(Collectors.toSet());
                Set<String> temp = Sets.newHashSet();
                for (String findUserId : findUserIds) {
                    for (String getUserId : getUserIds) {
                        if (getUserId.equals(findUserId)) {
                            temp.add(getUserId);
                        }
                    }
                }
                findUserIds = temp;
            }
            if (CollectionUtils.isNotEmpty(findUserIds)) {
                QueryWrapper<UserEntity> pageWrapper = new QueryWrapper<>();
                pageWrapper.in(UserEntity.ID, findUserIds);
//        pageWrapper.orderByAsc("SORT_NUMBER");
                if (StringUtils.isNotBlank(pageRequest.getUserName())) {
                    pageWrapper.like(UserEntity.USER_NAME, pageRequest.getUserName());
                }
                if (StringUtils.isNotBlank(pageRequest.getTelPhone())) {
                    pageWrapper.like(UserEntity.TEL_PHONE, pageRequest.getTelPhone());
                }
                IPage<UserEntity> page = page(new Query<UserEntity>().getPage(pageRequest), pageWrapper.orderByDesc("create_time"));
                userEntityResults = BeanCopyUtils.copy(page.getRecords(), UserEntityResult.class);
                total = page.getTotal();
                userEntityResults = userEntityResults.stream()
                        .filter(Objects::nonNull)
                        .map(userEntityResult -> {
                            InputStream picturePath = userEntityResult.getPicturePath();
                            if (picturePath != null) {
                                try {
                                    String encode = Base64Utils.encode(IOUtils.toByteArray(picturePath));
                                    userEntityResult.setPhoto(encode);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    userEntityResult.setPicturePath(null);
                                }
                            }
                            return userEntityResult;
                        }).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(userEntityResults)) {
                    userEntityResults.stream().forEach(f -> {
                        RoleEntityQueryParam params = new RoleEntityQueryParam();
                        params.setUserId(f.getId());
                        // 查询用户对用的角色信息
                        List<RoleEntityInfoResult> roleInfoByUserId = roleService.getRoleInfoByUserId(params);

                        if (CollectionUtils.isNotEmpty(roleInfoByUserId)) {
                            f.setRoleList(roleInfoByUserId.stream().map(RoleEntityInfoResult::getId).collect(Collectors.toList()));
                            f.setRoleName(roleInfoByUserId.stream().map(RoleEntityInfoResult::getRoleName).collect(Collectors.joining(",")));
                        }

                        OrganizationEntityQueryParam orgParams = new OrganizationEntityQueryParam();
                        orgParams.setUserId(f.getId());
                        // 查询用户对应的组织机构信息
                        List<OrganizationInfoResult> organizationEntityList = organizationService.getOrgInfoByUserId(orgParams);
                        if (CollectionUtils.isNotEmpty(organizationEntityList)) {
                            List<UserOrgListResult> results = new ArrayList<>();
                            organizationEntityList.forEach(orgResult -> {
                                UserOrgListResult result = new UserOrgListResult();
                                result.setOrgId(orgResult.getId());
                                result.setOrgPath(orgResult.getOrgPath());
                                results.add(result);
                            });
                            f.setOrgList(results);
                            f.setOrgName(organizationEntityList.stream().map(OrganizationInfoResult::getOrgName).collect(Collectors.joining(",")));
                        }

                        // 查询用户对应的账号信息
                        UserAccountEntity userAccountEntity =
                                userAccountService.getOne(new QueryWrapper<UserAccountEntity>().eq("user_id", f.getId()));
                        if (Objects.nonNull(userAccountEntity)) {
                            f.setAccountIdentifier(userAccountEntity.getAccountIdentifier());
                        }
                    });
                }
            }
        }
        return BusinessPageResult.build(userEntityResults, pageRequest, total);
    }

    private Set<String> findCurrOrgIdsByUserId(String userId, List<UserOrgMapEntity> userOrgMaps) {
        return Sets.newHashSet();
    }

    private Set<String> loopFindSonOrgId(Set<String> currOrgIds, List<OrganizationEntity> allOrgs) {
        Set<String> result = Sets.newHashSet();
        result.addAll(currOrgIds);
        Set<String> temp = currOrgIds;
        while (CollectionUtils.isNotEmpty(temp)) {
            Set<String> tempChildOrgIds = Sets.newHashSet();
            for (String orgId : temp) {
                Set<String> childOrgIds = Optional.ofNullable(allOrgs).orElse(Lists.newArrayList())
                        .parallelStream()
                        .filter(org -> orgId.equals(org.getParentId()))
                        .map(OrganizationEntity::getId)
                        .collect(Collectors.toSet());
                result.addAll(childOrgIds);
                tempChildOrgIds.addAll(childOrgIds);
            }
            temp = tempChildOrgIds;
        }
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public List<UserEntityResult> queryList(UserEntityExportRequest exportRequest, String loginUserId) {
        UserEntityPageRequest pageRequest = new UserEntityPageRequest();
        BeanCopyUtils.copyProperties(exportRequest, pageRequest);
        pageRequest.setPageSize(Integer.MAX_VALUE);
        pageRequest.setPageNum(1);
        BusinessPageResult businessPageResult = queryPage(pageRequest, loginUserId);
        List<UserEntityResult> list = businessPageResult.getList();
        return list;
    }

    @Override
    public List<SelectInfoResult> getAllUserInfo() {

        return userDao.getAllUserInfo();
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> saveUserEntity(UserEntitySaveParam param) {
        //校验账号
        QueryWrapper<UserAccountEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(UserAccountEntity.ACCOUNT_IDENTIFIER, param.getAccountIdentifier());
        List<UserAccountEntity> userAccounts = userAccountService.list(wrapper);
        if (CollectionUtils.isNotEmpty(userAccounts)) {
            throw new AppException("50008016");
        }
        //校验用户名
        /*QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.eq(UserEntity.USER_NAME, param.getUserName());
        List<UserEntity> userEntities = list(userEntityQueryWrapper);
        if (CollectionUtils.isNotEmpty(userEntities)) {
            throw new AppException("50009335");
        }*/
        //新增user信息
        UserEntity userEntity = new UserEntity();
        String userGender = param.getUserGender();
        BeanCopyUtils.copyProperties(param, userEntity);
        userEntity.setUserGender(userGender);
        save(userEntity);
        //新增账号信息
        SM4Utils sm4 = new SM4Utils(secretKey);
        UserAccountEntity userAccountEntity = new UserAccountEntity();
        userAccountEntity.setUserId(userEntity.getId());
        userAccountEntity.setAccountLocked(DeleteFlagEnum.N.getCode());
        userAccountEntity.setIdentityType(IdentityTypeEnum.USER_NAME.getCode());
        userAccountEntity.setLoginMode(0);
        userAccountEntity.setAccountIdentifier(param.getAccountIdentifier());
        userAccountEntity.setAccountCredential(sm4.encryptData_ECB(defaultPassword));
        userAccountService.save(userAccountEntity);
        // 新增用户组织机构
        if (CollectionUtils.isNotEmpty(param.getOrgList())) {
            List<UserOrgMapEntity> userOrgMapEntities = new ArrayList<>();
            param.getOrgList().stream().forEach(f -> {
                UserOrgMapEntity userOrgMapEntity = new UserOrgMapEntity();
                userOrgMapEntity.setUserId(userEntity.getId());
                userOrgMapEntity.setOrgId(f.getOrgId());
                userOrgMapEntity.setOrgPath(f.getOrgPath());
                userOrgMapEntities.add(userOrgMapEntity);
            });
            userOrgMapService.saveBatch(userOrgMapEntities);
        }
        return BusinessResult.success(true);
    }

    @SuppressWarnings("all")
    @Override
    @BusinessParamsValidate
    public BusinessResult<Boolean> updateUserEntity(UserEntitySaveParam param) {
        if (StringUtils.isBlank(param.getId())) {
            throw new AppException("50000021");
        }
        String userId = param.getId();
        List<UserInfoAndAccountResult> userInfoAndAccount = getUserInfoAndAccount(Sets.newHashSet(userId));
        Map<String, String> userAccountMap = userInfoAndAccount.parallelStream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(UserInfoAndAccountResult::getUserName, UserInfoAndAccountResult::getAccountIdentifier));
        //校验账号
        QueryWrapper<UserAccountEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(UserAccountEntity.ACCOUNT_IDENTIFIER, param.getAccountIdentifier());
        List<UserAccountEntity> userAccounts = userAccountService.list(wrapper);
        long count = userAccounts.parallelStream()
                .filter(Objects::nonNull)
                .filter(userAccountEntity -> {
                    boolean flag = true;
                    if (MapUtils.isNotEmpty(userAccountMap)) {
                        flag = !userAccountMap.values().contains(userAccountEntity.getAccountIdentifier());
                    }
                    return flag;
                }).count();
        if (count > 0) {
            throw new AppException("50008016");
        }
        //校验用户名
        /*QueryWrapper<UserEntity> userEntityQueryWrapper = new QueryWrapper<>();
        userEntityQueryWrapper.eq(UserEntity.USER_NAME, param.getUserName());
        List<UserEntity> userEntities = list(userEntityQueryWrapper);
        long userCount = userEntities.parallelStream()
                .filter(Objects::nonNull)
                .filter(userEntity -> {
                    boolean flag = true;
                    if (MapUtils.isNotEmpty(userAccountMap)) {
                        flag = !userAccountMap.containsKey(userEntity.getUserName());
                    }
                    return flag;
                }).count();
        if (userCount > 0) {
            throw new AppException("50009335");
        }*/
        List<UserEntity> users = getUsers(UserEntityConditionParam.builder().ids(Sets.newHashSet(param.getId())).build());

        UserEntity userEntity = BeanCopyUtils.copyProperties(param, UserEntity.class);
        userEntity.setUserGender(param.getUserGender());
        updateById(userEntity);

        userOrgMapService.remove(new QueryWrapper<UserOrgMapEntity>().eq(UserOrgMapEntity.USER_ID, userId));
        if (CollectionUtils.isNotEmpty(param.getOrgList())) {
            List<UserOrgMapEntity> userOrgMapEntities = new ArrayList<>();
            param.getOrgList().stream().forEach(f -> {
                UserOrgMapEntity userOrgMapEntity = new UserOrgMapEntity();
                userOrgMapEntity.setUserId(userId);
                userOrgMapEntity.setOrgId(f.getOrgId());
                userOrgMapEntity.setOrgPath(f.getOrgPath());
                userOrgMapEntity.setId(generalSequence.nextValueString());
                userOrgMapEntities.add(userOrgMapEntity);
            });
            userOrgMapService.saveBatch(userOrgMapEntities);
        }
//        UserEntityResult userEntityResult = BeanCopyUtils.copyProperties(users.get(0), UserEntityResult.class);
        return BusinessResult.success(true);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response, Class<UserImportEntity> userEntityClass) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        ExpertInfoResult expertInfoResult = new ExpertInfoResult();
        List<UserEntity> passList = new ArrayList<>();
        List<UserAccountEntity> accountList = new ArrayList<>();
        List<UserRoleMapEntity> userRoleList = new ArrayList<>();
        List<UserOrgMapEntity> userOrgList = new ArrayList<>();
        List<Object> noPassList = new ArrayList<>();

        List<String> accountIdentifierList = new ArrayList<>();

        List<RoleEntity> roleList = roleService.list();
        HashMap<String, String> roleMap = new HashMap<>();
        for (RoleEntity r : roleList) {
            roleMap.put(r.getRoleName(), r.getId());
        }
        List<OrganizationEntity> orgList = organizationService.list();
        HashMap<String, String> orgMap = new HashMap<>();
        for (OrganizationEntity o : orgList) {
            orgMap.put(o.getOrgName(), o.getId());
        }

        List<UserAccountEntity> userAccountEntities = userAccountService.list();
        if (CollectionUtils.isNotEmpty(userAccountEntities)) {
            accountIdentifierList =
                    userAccountEntities.stream().map(UserAccountEntity::getAccountIdentifier).collect(Collectors.toList());
        }

        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 获取上传文件对象
            MultipartFile file = entity.getValue();
            try {
                List<T> importExcelList = EasyExcelUtil.syncReadModel(file.getInputStream(), userEntityClass, 0, 1);
                List<UserImportEntity> infoExperts = BeanCopyUtils.copy(importExcelList, UserImportEntity.class);

                // 去除导入时 导入账号的重复
                Map<String, List<UserImportEntity>> accountNameGroupBy = infoExperts
                        .stream().filter(f -> f.getLoginUsername() != null)
                        .collect(Collectors.groupingBy(UserImportEntity::getLoginUsername));
                accountNameGroupBy.forEach((key, value) -> {
                    if (value.size() > 1) {
                        value.forEach(f -> {
                            String format = String.format("导入账号[%S]重复!", f.getLoginUsername());
                            f.setErrorMsg(format);
                            noPassList.add(f);
                        });
                        infoExperts.removeAll(value);
                    }
                });


                for (UserImportEntity userExcelRow : infoExperts) {
                    //校验
                    if (!importUserCheck(accountIdentifierList, noPassList, userExcelRow)) {
                        continue;
                    }
                    // 通过保存
                    UserEntity userEntity = BeanCopyUtils.copyProperties(userExcelRow, UserEntity.class);
                    userEntity.setId(generalSequence.nextValueString());
                    userEntity.setDeleteFlag(DELETE_FLAG_N);
                    passList.add(userEntity);

                    UserAccountEntity uae = new UserAccountEntity();
                    uae.setId(generalSequence.nextValueString());
                    uae.setUserId(userEntity.getId());
                    uae.setAccountIdentifier(userExcelRow.getLoginUsername());
                    uae.setAccountCredential(new SM4Utils(secretKey).encryptData_ECB(defaultPassword));
                    accountList.add(uae);

                    if (StringUtils.isNotBlank(roleMap.get(userExcelRow.getRoleName()))) {
                        UserRoleMapEntity ure = new UserRoleMapEntity();
                        ure.setId(generalSequence.nextValueString());
                        ure.setUserId(userEntity.getId());
                        ure.setRoleId(roleMap.get(userExcelRow.getRoleName()));
                        userRoleList.add(ure);
                    }

                    if (StringUtils.isNotBlank(orgMap.get(userExcelRow.getOrgName()))) {
                        UserOrgMapEntity uoe = new UserOrgMapEntity();
                        uoe.setId(generalSequence.nextValueString());
                        uoe.setUserId(userEntity.getId());
                        uoe.setOrgId(orgMap.get(userExcelRow.getOrgName()));
                        userOrgList.add(uoe);
                    }

                }
                expertInfoResult.setNoPassList(noPassList);
                expertInfoResult.setErrorCount(noPassList.size());
                expertInfoResult.setSuccessCount(passList.size());

                this.saveBatch(passList);
                userAccountService.saveBatch(accountList);
                userRoleMapService.saveBatch(userRoleList);
                userOrgMapService.saveBatch(userOrgList);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                //手动开启事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return BusinessResult.fail("00", "文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return BusinessResult.success(expertInfoResult);
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<ExpertInfoResult> importExcel(MultipartFile file, UserOrgListResult param) throws IOException {
        ExpertInfoResult result = new ExpertInfoResult();
        try (InputStream in = file.getInputStream()) {
            List<T> ts = EasyExcelUtil.syncReadModel(in, UserImportEntity.class, 0, 1);
            List<UserImportEntity> userImportEntitys = BeanCopyUtils.copy(ts, UserImportEntity.class);
            if (CollectionUtils.isNotEmpty(userImportEntitys)) {
                long count = userImportEntitys.parallelStream()
                        .filter(Objects::nonNull)
                        .filter(userImportEntity -> {
                            String userName = userImportEntity.getUserName();
                            String loginUsername = userImportEntity.getLoginUsername();
                            return (StringUtils.isBlank(userName) || StringUtils.isBlank(loginUsername));
                        }).count();
                if (count > 0) {
                    throw new AppException("50009332");
                }
                //导入的用户
                List<UserEntity> userEntities = transferTo(userImportEntitys);
                List<UserEntity> allUsers = list(new QueryWrapper<>());
                if (checkDuplicateUser(userEntities, allUsers)) {
                    throw new AppException("50008040");
                }
                //excel字段格式校验
                checkColumnFormat(userEntities);
                saveBatch(userEntities, 10);
                //导入的账户
                List<UserAccountEntity> userAccountEntities = transferToAccountEntity(userImportEntitys);
                List<UserAccountEntity> allUserAccounts = userAccountService.list(new QueryWrapper<>());
                if (checkDuplicateAccount(userAccountEntities, allUserAccounts)) {
                    throw new AppException("50008016");
                }
                userAccountEntities = userAccountEntities.stream()
                        .map(userAccountEntity -> {
                            String userId = findUserByAccountIdentifier(userAccountEntity.getAccountIdentifier(), userEntities, userImportEntitys);
                            userAccountEntity.setUserId(userId);
                            return userAccountEntity;
                        }).collect(Collectors.toList());
                userAccountService.saveBatch(userAccountEntities, 10);
                //添加部门
                List<UserOrgMapEntity> userOrgMapEntities = transferToUserOrgEntity(userEntities, param);
                userOrgMapService.saveBatch(userOrgMapEntities, 10);
            }
        }
        result.setErrorCount(0);
        return BusinessResult.success(result);
    }

    private void checkColumnFormat(List<UserEntity> users) {
        if (CollectionUtils.isNotEmpty(users)) {
            for (UserEntity user : users) {
                if (StringUtils.isBlank(user.getTelPhone())) {
                    continue;
                }
                boolean b = MobileUtil.checkPhone(user.getTelPhone());
                if (!b) {
                    throw new AppException("50009352");
                }
                if (StringUtils.isBlank(user.getUserBirth())) {
                    continue;
                }
                boolean e = ExcelConvertUtil.userBirthExamine(user.getUserBirth());
                if (!e) {
                    throw new AppException("50009364");
                }
            }
        }
    }

    private String findUserByAccountIdentifier(String accountIdentifier, List<UserEntity> userEntities, List<UserImportEntity> userImportEntities) {
        String result = "";
        List<String> userNames = userImportEntities.parallelStream()
                .filter(Objects::nonNull)
                .filter(userImportEntity -> accountIdentifier.equals(userImportEntity.getLoginUsername()))
                .map(UserImportEntity::getUserName)
                .collect(Collectors.toList());
        List<String> userIds = userEntities.parallelStream()
                .filter(Objects::nonNull)
                .filter(userEntity -> userNames.contains(userEntity.getUserName()))
                .map(UserEntity::getId)
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(userIds)) {
            result = userIds.get(0);
        }
        return result;
    }

    private boolean checkDuplicateAccount(List<UserAccountEntity> src, List<UserAccountEntity> base) {
        Set<String> accountIdentifiers = src.parallelStream()
                .filter(Objects::nonNull)
                .map(UserAccountEntity::getAccountIdentifier)
                .collect(Collectors.toSet());
        long count = base.parallelStream()
                .filter(userAccountEntity -> accountIdentifiers.contains(userAccountEntity.getAccountIdentifier()))
                .count();
        return count > 0;
    }

    private boolean checkDuplicateUser(List<UserEntity> src, List<UserEntity> base) {
        //校验username
        Set<String> userNames = src.parallelStream()
                .filter(Objects::nonNull)
                .map(UserEntity::getUserName)
                .collect(Collectors.toSet());
        long count = base.parallelStream()
                .filter(userEntity -> userNames.contains(userEntity.getUserName()))
                .count();
        return count > 0;

    }

    private List<UserOrgMapEntity> transferToUserOrgEntity(List<UserEntity> userEntities, UserOrgListResult orgId) {
        return userEntities.parallelStream()
                .filter(Objects::nonNull)
                .map(userEntity -> {
                    UserOrgMapEntity entity = new UserOrgMapEntity();
                    entity.setId(generalSequence.nextValueString());
                    entity.setUserId(userEntity.getId());
                    entity.setOrgId(orgId.getOrgId());
                    entity.setOrgPath(orgId.getOrgPath());
                    return entity;
                })
                .collect(Collectors.toList());
    }

    private List<UserAccountEntity> transferToAccountEntity(List<UserImportEntity> entities) {
        SM4Utils sm4 = new SM4Utils(secretKey);
        return entities.parallelStream()
                .filter(Objects::nonNull)
                .map(userImportEntity -> {
                    UserAccountEntity userAccountEntity = new UserAccountEntity();
                    userAccountEntity.setAccountLocked(DeleteFlagEnum.N.getCode());
                    userAccountEntity.setIdentityType(IdentityTypeEnum.USER_NAME.getCode());
                    userAccountEntity.setLoginMode(0);
                    userAccountEntity.setAccountIdentifier(userImportEntity.getLoginUsername());
                    userAccountEntity.setAccountCredential(sm4.encryptData_ECB(defaultPassword));
                    userAccountEntity.setId(generalSequence.nextValueString());
                    return userAccountEntity;
                }).collect(Collectors.toList());
    }

    private List<UserEntity> transferTo(List<UserImportEntity> entities) {
        return entities.parallelStream()
                .filter(Objects::nonNull)
                .map(userImportEntity -> {
                    UserEntity entity = new UserEntity();
                    entity.setId(generalSequence.nextValueString());
                    entity.setTelPhone(userImportEntity.getTelPhone());
                    entity.setUserGender(userImportEntity.getUserGender());
                    entity.setDeleteFlag(userImportEntity.getDeleteFlag());
                    entity.setUserBirth(userImportEntity.getUserBirth());
                    entity.setUserName(userImportEntity.getUserName());
                    entity.setUserCode(userImportEntity.getUserCode());
                    entity.setUserRemark(userImportEntity.getUserRemark());
                    return entity;
                }).collect(Collectors.toList());
    }

    @Override
    public List<OrganizationEntity> getOrgInfoByUsrId(UserInfoRequest params) {

        return userDao.getOrgInfoByUsrId(params);
    }

    @Override
    public List<UserEntity> getUserInfoByOrgId(OrgUserRequest params) {

        return userDao.getUserInfoByOrgId(params);
    }

    @Override
    public List<UserEntity> getOrgChildUserInfoByOrgId(OrgUserRequest params) {

        return userDao.getOrgChildUserInfoByOrgId(params);
    }

    @Override
    public List<RoleEntity> getRoleInfoByUserId(UserInfoRequest params) {

        return userDao.getRoleInfoByUserId(params);
    }

    @Override
    public List<LikeQueryUserListResult> queryUserInfoByName(LikeQueryUserInfoRequest params) {

        if (StringUtils.isEmpty(params.getName())) {
            return new ArrayList<>();
        }

        return userDao.queryUserInfoByName(params);
    }


    private boolean importUserCheck(List<String> accountIdentifierList, List<Object> noPassList, UserImportEntity userExcelRow) {
        //校验账号是否已存在
        if (accountIdentifierList.contains(userExcelRow.getLoginUsername())) {
            String format = String.format("账号[%S]已存在!", userExcelRow.getLoginUsername());
            userExcelRow.setErrorMsg(format);
            noPassList.add(userExcelRow);
            return false;
        }

        return true;
    }

    @Override
    public BusinessResult<List<UserEntityInfoResult>> getUserInfosByOrgIds(UserInfosByOrgIdsQueryParam param) {
        List<UserEntityInfoResult> results = Lists.newArrayList();
        Set<String> orgIds = param.getOrgIds();
        if (CollectionUtils.isNotEmpty(orgIds)) {
            String deleteFlag = param.getDeleteFlag();
            DeleteFlagEnum deleteFlagEnum = DeleteFlagEnum.find(deleteFlag);
            //根据条件过滤从orgIds中过滤
            Set<String> filterOrgIds = organizationService.filterIdsByDelFlag(orgIds, deleteFlagEnum);
            //根据组织id获取用户id
            Set<String> userIds = userOrgMapService.getUserIdsByOrgIds(filterOrgIds);
            //获取用户列表
            results = getUserByIdsAndDelFlag(userIds, deleteFlagEnum);
        }
        return BusinessResult.success(results);
    }

    @SuppressWarnings("all")
    private List<UserEntityInfoResult> getUserByIdsAndDelFlag(Set<String> ids, DeleteFlagEnum deleteFlag) {
        List<UserEntityInfoResult> infoResults = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(ids) && deleteFlag != null) {
            QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
            wrapper.in(UserEntity.ID, ids);
            if (!DeleteFlagEnum.ALL.getCode().equals(deleteFlag.getCode())) {
                wrapper.eq(UserEntity.DELETE_FLAG, deleteFlag.getCode());
            }
            //根据userIds查询所有的roleIds,key:userId,value:roleId
            List<Map<String, String>> roleIdMapTemp = userRoleMapDao.getRoleIds(ids);
            Map<String, String> roleIdMap = new HashMap();
            for (Map<String, String> m : roleIdMapTemp) {
                roleIdMap.put(m.get("userId"), m.get("roleId"));
            }
            /*Map<String, String> roleIdMap = roleIdMapTemp.stream().collect(
                    Collectors.toMap(s->s.get("userId"), s -> s.get("roleId")));*/
            Set<String> roleIds = getAllRoleIds(roleIdMap);
            //根据roleIds查询所有的roleName,key:roleId,value:roleName
            List<Map<String, String>> roleNameTemp = roleDao.getRoleNameByRoleIds(roleIds);
            /*Map<String, String> roleName = roleNameTemp.stream().collect(
                    Collectors.toMap(s->s.get("roleId"), s -> s.get("roleName")));*/
            Map<String, String> roleName = new HashMap();
            for (Map<String, String> m : roleNameTemp) {
                roleName.put(m.get("roleId"), m.get("roleName"));
            }
            List<UserEntity> userEntities = userDao.selectList(wrapper);
            List<UserOrgMapEntity> allUserOrgMap = userOrgMapService.list(new QueryWrapper<>());
            List<OrganizationEntity> organizationEntities = organizationService.list(new QueryWrapper<>());
            infoResults = userEntities.parallelStream()
                    .filter(Objects::nonNull)
                    .map(userEntity -> {
                        UserEntityInfoResult result = new UserEntityInfoResult();
                        BeanCopyUtils.copyProperties(userEntity, result);
                        Set<String> orgIds = allUserOrgMap.parallelStream()
                                .filter(Objects::nonNull)
                                .filter(userOrgMapEntity -> userEntity.getId().equals(userOrgMapEntity.getUserId()))
                                .map(UserOrgMapEntity::getOrgId)
                                .collect(Collectors.toSet());
                        result.setOrgIds(orgIds);
                        Set<String> orgNames = organizationEntities.parallelStream()
                                .filter(Objects::nonNull)
                                .filter(o -> orgIds.contains(o.getId()))
                                .map(OrganizationEntity::getOrgName)
                                .collect(Collectors.toSet());
                        result.setOrgNames(orgNames);
                        InputStream picturePath = userEntity.getPicturePath();
                        if (picturePath != null) {
                            try {
                                String encode = Base64Utils.encode(IOUtils.toByteArray(picturePath));
                                userEntity.setPhoto(encode);
                            } catch (IOException e) {
                                log.error(e.getMessage(), e);
                            }
                        }
                        String roleId = roleIdMap.get(userEntity.getId());
                        result.setRoleId(roleId);
                        result.setRoleName(roleName.get(roleId));
                        return result;
                    }).collect(Collectors.toList());

        }
        return infoResults;
    }

    private Set<String> getAllRoleIds(Map<String, String> roleIdMap) {
        Iterator it = roleIdMap.entrySet().iterator();
        Set<String> set = new HashSet<>();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            set.add((String) entry.getValue());
        }
        return set;
    }

    /**
     * 根据条件过滤用户id
     *
     * @param ids        id集合
     * @param deleteFlag 删除标识
     * @return 过滤后的id集合
     */
    private Set<String> filterIdsByDelFlag(Set<String> ids, DeleteFlagEnum deleteFlag) {
        Set<String> filterIds = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(ids) && deleteFlag != null) {
            //删除标识为ALL则返回原集合
            if (DeleteFlagEnum.ALL.getCode().equals(deleteFlag.getCode())) {
                filterIds = ids;
            } else {
                List<UserEntityInfoResult> users = getUserByIdsAndDelFlag(ids, deleteFlag);
                filterIds = Optional.ofNullable(users).orElse(Lists.newArrayList())
                        .parallelStream()
                        .filter(user -> Objects.nonNull(user))
                        .map(UserEntityInfoResult::getId)
                        .collect(Collectors.toSet());
            }
        }
        return filterIds;
    }

    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    @Override
    @SuppressWarnings("all")
    public BusinessResult setUserConferredRoles(UserConferredRolesSaveParam param) {
        List<String> roleIds = param.getRoleIds();
        String userId = param.getUserId();

        QueryWrapper<UserRoleMapEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(UserRoleMapEntity.USER_ID, userId);
        List<UserRoleMapEntity> userRoleMap = userRoleMapService.list(wrapper);
        //该用户下已存在的roleIds
        List<String> existRoleIds = Optional.ofNullable(userRoleMap).orElse(Lists.newArrayList())
                .parallelStream()
                .map(UserRoleMapEntity::getRoleId)
                .collect(Collectors.toList());
        //移除的角色列表
        List<String> removeRoleIds = existRoleIds.stream()
                .filter(roleId -> !roleIds.contains(roleId))
                .collect(Collectors.toList());
        Set<String> removeIds = Optional.ofNullable(userRoleMap).orElse(Lists.newArrayList())
                .parallelStream()
                .filter(userRoleMapEntity -> removeRoleIds.contains(userRoleMapEntity.getRoleId()))
                .map(UserRoleMapEntity::getId)
                .collect(Collectors.toSet());
        if (CollectionUtils.isNotEmpty(removeIds)) {
            userRoleMapService.removeByIds(removeIds);
        }
        //新增的用户列表
        List<String> newRoleIds = roleIds.parallelStream()
                .filter(roleId -> !existRoleIds.contains(roleId))
                .collect(Collectors.toList());
        //组装用户角色列表
        Set<UserRoleMapEntity> newUserRoleMap = transferTo(newRoleIds, userId);
        //批量存入
        userRoleMapService.saveBatch(newUserRoleMap, 5);
        return BusinessResult.success(null);
    }

    @SuppressWarnings("all")
    private Set<UserRoleMapEntity> transferTo(final List<String> roleIds, final String userId) {
        return Optional.ofNullable(roleIds).orElse(Lists.newArrayList())
                .parallelStream()
                .filter(StringUtils::isNoneBlank)
                .map(roleId -> {
                    UserRoleMapEntity entity = new UserRoleMapEntity();
                    entity.setId(generalSequence.nextValueString());
                    entity.setUserId(userId);
                    entity.setRoleId(roleId);
                    return entity;
                }).collect(Collectors.toSet());
    }

    @Override
    public BusinessResult<Boolean> uploadPhoto(PhotoSaveParam param) throws IOException {
        QueryWrapper<UserEntity> userWrapper = new QueryWrapper<>();
        userWrapper.eq(UserEntity.ID, param.getUserId());
        List<UserEntity> userEntities = list(userWrapper);
        if (CollectionUtils.isEmpty(userEntities)) {
            throw new AppException("50008037");
        }
        UserEntity userEntity = userEntities.get(0);
        if (DeleteFlagEnum.Y.getCode().equals(userEntity.getDeleteFlag())) {
            throw new AppException("50008038");
        }
        String photo = param.getPhoto();
        byte[] decode = Base64Utils.decode(photo);
        try {
            for (int i = 0; i < decode.length; ++i) {
                if (decode[i] < 0) {// 调整异常数据
                    decode[i] += 256;
                }
            }
        } catch (Exception e) {
            log.error("图片数据异常", e);
        }
        InputStream in = new ByteArrayInputStream(decode);
        int available = in.available();
        if (available > 2 * 1024 * 1024) {
            throw new AppException("50008039");
        }
        UserEntity entity = new UserEntity();
        entity.setId(userEntity.getId());
        entity.setPicturePath(in);
        return BusinessResult.success(super.updateById(entity));
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> editBase(UserEntityEditBaseParam param) {
        UserEntity userEntity = BeanCopyUtils.copyProperties(param, UserEntity.class);
        boolean result = super.updateById(userEntity);
        return BusinessResult.success(result);
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<UserEntityInfoResult> info(@Valid @NotBlank(message = "10000001") String id) {
        UserEntityInfoResult result = null;
        UserEntity user = getById(id);
        if (Objects.nonNull(user)) {
            result = new UserEntityInfoResult();
            BeanCopyUtils.copyProperties(user, result);
            InputStream picturePath = user.getPicturePath();
            if (Objects.nonNull(picturePath)) {
                try {
                    String encode = Base64Utils.encode(IOUtils.toByteArray(picturePath));
                    result.setPhoto(encode);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return BusinessResult.success(result);
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> delete(UserEntityDelParam param) {
        List<UserEntity> users = getUsers(UserEntityConditionParam.builder().build());
        //校验启用状态
        verifyUserStatus(param.getIds(), users);
        removeByIds(param.getIds());
        List<UserAccountEntity> userAccounts = getUserAccounts(UserAccountEntityConditionParam.builder()
                .userIds(param.getIds()).build());
        Set<String> ids = getIds(userAccounts);
        userAccountService.removeByIds(ids);
        //清楚用户token
//        deleteAccountToken(userAccounts);
        return BusinessResult.success(true);
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> status(UserChangeStatusParam param) {
        UserEntity entity = new UserEntity();
        entity.setId(param.getUserId());
        entity.setDeleteFlag(param.getDeleteFlag());
        updateById(entity);
        List<UserAccountEntity> userAccounts = getUserAccounts(UserAccountEntityConditionParam.builder()
                .userIds(Sets.newHashSet(param.getUserId())).build());
        if (CollectionUtils.isNotEmpty(userAccounts)) {
            /*if (DeleteFlagEnum.Y.getCode().equals(param.getDeleteFlag())) {
                deleteAccountToken(userAccounts);
            }*/
            List<UserAccountEntity> collect = userAccounts.parallelStream()
                    .filter(Objects::nonNull)
                    .map(accountEntity -> {
                        accountEntity.setDeleteFlag(param.getDeleteFlag());
                        return accountEntity;
                    }).collect(Collectors.toList());
            userAccountService.updateBatchById(collect);
        }
        return BusinessResult.success(true);
    }

    @Override
    public List<LikeQueryUserRoleListResult> queryUserRoleByName(LikeQueryUserRoleRequest params) {
        if (StringUtils.isEmpty(params.getName())) {
            return new ArrayList<>();
        }

        return userDao.queryUserRoleByName(params);
    }

    private void deleteAccountToken(List<UserAccountEntity> accounts) {
        if (CollectionUtils.isNotEmpty(accounts)) {
            accounts.parallelStream()
                    .filter(Objects::nonNull)
                    .forEach(account -> {
                        SM4Utils sm4 = new SM4Utils(secretKey);
                        String passWord = sm4.decryptData_ECB(account.getAccountCredential());
                        BusinessResult<PlatformUserAuthResponse> token = sessionService.getToken(account.getAccountIdentifier(), passWord);
                        PlatformUserAuthResponse data = token.getData();
                        if (token.isSuccess() && Objects.nonNull(data)) {
                            tokenService.removeToken(data.getAccessToken());
                        }
                    });
        }
    }

    private void verifyUserStatus(Set<String> currUserIds, List<UserEntity> users) {
        if (CollectionUtils.isNotEmpty(currUserIds) && CollectionUtils.isNotEmpty(users)) {
            List<UserEntity> userEntityByUserId = findUserEntityByUserId(currUserIds, users);
            List<UserEntity> collect = userEntityByUserId.parallelStream()
                    .filter(userEntity -> DeleteFlagEnum.N.getCode().equals(userEntity.getDeleteFlag()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)) {
                throw new AppException("50009351");
            }
        }
    }

    private List<UserEntity> findUserEntityByUserId(Set<String> currUserIds, List<UserEntity> users) {
        List<UserEntity> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(currUserIds) && CollectionUtils.isNotEmpty(users)) {
            results = users.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(userEntity -> currUserIds.contains(userEntity.getId()))
                    .collect(Collectors.toList());
        }
        return results;
    }

    private List<UserInfoAndAccountResult> getUserInfoAndAccount(Set<String> userIds) {
        List<UserInfoAndAccountResult> results = Lists.newArrayList();
        List<UserEntity> userEntities = (List<UserEntity>) listByIds(userIds);
        QueryWrapper<UserAccountEntity> wrapper = new QueryWrapper<>();
        wrapper.in(UserAccountEntity.USER_ID, userIds);
        List<UserAccountEntity> accountEntities = userAccountService.list(wrapper);
        results = accountEntities.parallelStream()
                .filter(Objects::nonNull)
                .map(userAccountEntity -> {
                    UserInfoAndAccountResult result = new UserInfoAndAccountResult();
                    BeanCopyUtils.copyProperties(userAccountEntity, result);
                    result.setAccountId(userAccountEntity.getId());
                    result.setAccountDeleteFlag(userAccountEntity.getDeleteFlag());
                    result.setAccountCreateTime(userAccountEntity.getCreateTime());
                    userEntities.parallelStream()
                            .filter(Objects::nonNull)
                            .forEach(userEntity -> {
                                if (userEntity.getId().equals(userAccountEntity.getUserId())) {
                                    BeanCopyUtils.copyProperties(userEntity, result);
                                    result.setUserId(userEntity.getId());
                                    result.setUserCreateTime(userEntity.getCreateTime());
                                    result.setUserDeleteFlag(userEntity.getDeleteFlag());
                                }
                            });
                    return result;
                }).collect(Collectors.toList());
        return results;
    }

    private Set<String> getIds(List<UserAccountEntity> userAccounts) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(userAccounts)) {
            results = userAccounts.parallelStream()
                    .filter(Objects::nonNull)
                    .map(UserAccountEntity::getId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    private List<UserEntity> getUsers(UserEntityConditionParam param) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(UserEntity.ID, param.getIds());
        }
        return list(wrapper);
    }

    private List<UserAccountEntity> getUserAccounts(UserAccountEntityConditionParam param) {
        QueryWrapper<UserAccountEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getUserIds())) {
            wrapper.in(UserAccountEntity.USER_ID, param.getUserIds());
        }
        if (StringUtils.isNotBlank(param.getDeleteFlag())) {
            wrapper.eq(UserAccountEntity.DELETE_FLAG, param.getDeleteFlag());
        }
        return userAccountService.list(wrapper);
    }
}
