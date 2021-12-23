package com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.utils.Base64Utils;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.mapper.AllInterfacesDao;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.param.InterfaceAuthParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.param.OrgTreeQueryParams;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.param.UserAuthParams;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.param.UserRoleDataQueryParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result.*;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.service.AllInterfacesService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.web.request.OrgEntityQueryRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.web.request.RoleEntityQueryRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.common.code.CommonConstant;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.feign.UserWorkspaceFeignClient;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.interfaces.service.InterfacesService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.service.OrganizationService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.entity.ResourcesEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.service.ResourcesService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.web.request.ResourcesQueryParams;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.entity.RoleEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.service.RoleService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.setting.service.SystemSettingsService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.setting.web.result.SystemSettingResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.shortcutmenu.service.ShortcutMenuService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.shortcutmenu.web.result.ShortCutMenuResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity.UserEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity.UserOrgMapEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity.UserRoleMapEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.UserAccountService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.UserOrgMapService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.UserRoleMapService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.UserService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.result.AuthorityResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.common.em.UaaCodeBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author EDZ
 */
@Slf4j
@Service("allInterfacesService")
public class AllInterfacesServiceImpl implements AllInterfacesService {

    @Autowired
    private AllInterfacesDao allInterfacesDao;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleMapService userRoleMapService;

    @Autowired
    private ResourcesService resourcesService;

    @Autowired
    private InterfacesService interfacesService;

    @Autowired
    private UserOrgMapService userOrgMapService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ShortcutMenuService shortcutMenuService;

    @Autowired
    private SystemSettingsService systemSettingsService;

    @Autowired
    private UserWorkspaceFeignClient workspaceFeignClient;

    @Override
    public BusinessResult<AuthResult> getAuth(UserAuthParams userAuthParams) throws IOException {

        AuthResult authResult = new AuthResult();
        List<ResourcesEntity> menuInfoByRoleIds = new ArrayList<>();
        List<ResourcesEntity> shortCutMenuList = new ArrayList<>();
        // 用户信息
        UserEntity userEntity = userService.getById(userAuthParams.getUserId());
        //用户个性化配置
        authResult.setSetting(userSettings(userEntity));
        // 判断用户是否被禁用
        String deleteFlag = userEntity.getDeleteFlag();
        if (CommonConstant.DELETE_FLAG_Y.equals(deleteFlag)) {
            throw new AppException(
                    UaaCodeBean.UaaCode.INVALID_LOGIN_USER_STATE.code,
                    UaaCodeBean.UaaCode.INVALID_LOGIN_USER_STATE.message);
        }
        InputStream picturePath = userEntity.getPicturePath();
        String encode = null;
        if (picturePath != null) {
            encode = Base64Utils.encode(IOUtils.toByteArray(picturePath));
        }
        UserEntityAuthResult userEntityAuthResult =
                BeanCopyUtils.copyProperties(userEntity, UserEntityAuthResult.class);
        userEntityAuthResult.setPicturePath(null);
        userEntityAuthResult.setPhoto(encode);
        // 判断是否第一次登陆
        userEntityAuthResult.setFirstLogin(userAccountService.isFirstLogin(userAuthParams.getUserId()));
        // 获取系统默认配置
//        addDefaultSettings(userEntityAuthResult);

        List<UserRoleMapEntity> userRoleMapEntityList =
                userRoleMapService.list(
                        new QueryWrapper<UserRoleMapEntity>().eq("user_id", userAuthParams.getUserId()));
        if (CollectionUtils.isNotEmpty(userRoleMapEntityList)) {
            List<String> roleIdList =
                    userRoleMapEntityList.stream()
                            .map(UserRoleMapEntity::getRoleId)
                            .collect(Collectors.toList());
            List<RoleEntity> roleEntities = new ArrayList<>();
            // 用户角色信息 去除禁用的角色信息
            if (isAdmin(userAuthParams.getUserId())) {
                authResult.setWorkspaceCreateAuth(true);
                roleEntities = roleService.list(new QueryWrapper<RoleEntity>().in("id", roleIdList));
            } else {
                roleEntities =
                        roleService.list(
                                new QueryWrapper<RoleEntity>()
                                        .eq("delete_flag", CommonConstant.DELETE_FLAG_N)
                                        .in("id", roleIdList));
            }
            // 角色信息
            userEntityAuthResult.setRoleList(roleEntities);
            if (CollectionUtils.isNotEmpty(roleEntities)) {
                ResourcesQueryParams params = new ResourcesQueryParams();
                // 获取有效角色id 查询菜单
                params.setRoleIdList(
                        roleEntities.stream().map(RoleEntity::getId).collect(Collectors.toList()));
                params.setDeleteFlag(CommonConstant.DELETE_FLAG_N);
                // 获取菜单信息
                menuInfoByRoleIds = resourcesService.getMenuInfoByRoleIds(params);

                // 获取用户快捷菜单
                ShortCutMenuResult shortCutMenuInfo =
                        shortcutMenuService.getShortCutMenuInfo(userAuthParams.getUserId());
                log.info("############# shortCutMenuInfo:{}", shortCutMenuInfo);
                if (Objects.nonNull(shortCutMenuInfo)) {
                    shortCutMenuList = shortCutMenuInfo.getMenus();
                }
            }

            if (CollectionUtils.isNotEmpty(menuInfoByRoleIds)) {
                // 获取按钮权限的标识
                List<String> buttonList =
                        menuInfoByRoleIds.stream()
                                .filter(f -> CommonConstant.MENU_TYPE_B.equals(f.getType()) && f.getAuthIdentification() != null)
                                .map(ResourcesEntity::getAuthIdentification)
                                .collect(Collectors.toList());
                authResult.setAuthList(buttonList);

                // 获取按钮类型的菜单 赋值给父类的 permissionList 字段
                Map<String, List<ResourcesEntity>> collect =
                        menuInfoByRoleIds.stream()
                                .filter(f -> CommonConstant.MENU_TYPE_B.equals(f.getType()))
                                .collect(Collectors.groupingBy(ResourcesEntity::getParentId));
                menuInfoByRoleIds.forEach(
                        f -> {
                            if (collect.get(f.getId()) != null) {
                                // 权限 URL 赋值给父类的 permissionList 字段，前台权限校验
                                f.setPermissionList(
                                        collect.get(f.getId()).stream()
                                                .filter(ff -> ff.getAuthIdentification() != null)
                                                .map(ResourcesEntity::getAuthIdentification)
                                                .collect(Collectors.toList()));
                            }
                        });

                // 过滤按钮类型的 菜单
                menuInfoByRoleIds =
                        menuInfoByRoleIds.stream()
                                .filter(menu -> (!CommonConstant.MENU_TYPE_B.equals(menu.getType())))
                                .collect(Collectors.toList());
            }
            authResult.setMenus(menuInfoByRoleIds);
            // 2021-06-25 initAuth返回结果新增用户快捷菜单列表，方便用户登录时展示
            authResult.setShortMenus(shortCutMenuList);
        }
        // 获取用户的组织机构信息
        List<UserOrgMapEntity> userOrgMapEntities =
                userOrgMapService.list(
                        new QueryWrapper<UserOrgMapEntity>().eq("user_id", userAuthParams.getUserId()));
        if (CollectionUtils.isNotEmpty(userOrgMapEntities)) {
            List<String> orgIdList =
                    userOrgMapEntities.stream().map(UserOrgMapEntity::getOrgId).collect(Collectors.toList());
            Collection<OrganizationEntity> organizationEntities =
                    organizationService.listByIds(orgIdList);
            userEntityAuthResult.setOrgList((List<OrganizationEntity>) organizationEntities);
        }

        AuthorityResult authorityResult = userService.getAuthorityResult(userAuthParams.getUserId());
        userEntityAuthResult.setAuthorityResult(authorityResult);
        // 给用户信息赋值
        authResult.setUserInfo(userEntityAuthResult);

        //查询用户所有的工作空间并赋值
        String userId = userAuthParams.getUserId();
//        if (authResult.getWorkspaceCreateAuth()){
//            userId = "";
//        }
        BusinessResult<List<Map<String, String>>> workspaceList = workspaceFeignClient.getWorkspaceListByUserId(userId);
        if (workspaceList.isSuccess() && CollectionUtils.isNotEmpty(workspaceList.getData())) {
            authResult.setWorkspaceList(workspaceList.getData());
        }
        return BusinessResult.success(authResult);
    }

    private UserSettings userSettings(UserEntity entity) {
        String cssId = entity.getCssId();
        String fontSize = entity.getFontSize();
        String enableCustomMenu = entity.getEnableCustomMenu();
        String logo = null;
        String copyRight = null;
        String appName = null;
        SystemSettingResult systemSetting = systemSettingsService.getSystemSetting();
        if (Objects.nonNull(systemSetting)) {
            logo = systemSetting.getLogo();
            copyRight = systemSetting.getCopyRight();
            appName = systemSetting.getAppName();
            if (StringUtils.isBlank(cssId)) {
                cssId = systemSetting.getDefaultCssId();
            }
            if (StringUtils.isBlank(fontSize)) {
                fontSize = systemSetting.getDefaultFontSize();
            }
            if (StringUtils.isBlank(enableCustomMenu)) {
                enableCustomMenu = "Y";
            }
        }

        return UserSettings.builder()
                .appName(appName)
                .copyRight(copyRight)
                .fontSize(fontSize)
                .cssId(cssId)
                .logo(logo)
                .enableCustomMenu(enableCustomMenu)
                .build();
    }

    /**
     * 为用户设置系统配置
     *
     * @param userEntityAuthResult 用户信息
     */
    private void addDefaultSettings(UserEntityAuthResult userEntityAuthResult) {
        // 获取系统配置信息
        SystemSettingResult systemSetting = systemSettingsService.getSystemSetting();
        log.info("########### systemSetting:" + systemSetting);
        if (Objects.isNull(userEntityAuthResult.getFontSize())) {
            userEntityAuthResult.setFontSize(systemSetting.getDefaultFontSize());
        }
        if (Objects.isNull(userEntityAuthResult.getCssId())) {

            userEntityAuthResult.setCssId(systemSetting.getDefaultCssId());
        }
        if (Objects.isNull(userEntityAuthResult.getLayout())) {
            userEntityAuthResult.setLayout(systemSetting.getDefaultLayout());
        }
    }

    @Override
    public List<InterfaceAuthResult> getUserAuthInterfaceIdList(InterfaceAuthParam param) {

        return interfacesService.getUserAuthInterfaceIdList(param);
    }

    @Override
    public boolean isAdmin(String userId) {
        UserRoleDataQueryParam param = new UserRoleDataQueryParam();
        param.setUserId(userId);
        param.setRoleCode(CommonConstant.SUPER_ADMIN_ROLE_CODE);
        return roleService.isAdmin(param);
    }

    @Override
    public List<SelectInfoResult> getAllRoleInfo() {

        return roleService.getAllRoleInfo();
    }

    @Override
    public List<SelectTreeInfoResult> getAllOrgTreeInfo() {

        OrgTreeQueryParams params = new OrgTreeQueryParams();
        List<SelectTreeInfoResult> allOrgInfo = organizationService.getAllOrgTreeInfo(params);
        getChildOrgInfo(allOrgInfo);
        return allOrgInfo;
    }

    private void getChildOrgInfo(List<SelectTreeInfoResult> allOrgInfo) {
        for (SelectTreeInfoResult selectInfoResult : allOrgInfo) {
            OrgTreeQueryParams params = new OrgTreeQueryParams();
            params.setParentId(selectInfoResult.getId());
            List<SelectTreeInfoResult> childOrgInfo = organizationService.getAllOrgTreeInfo(params);
            if (CollectionUtils.isNotEmpty(childOrgInfo)) {
                selectInfoResult.setChildren(childOrgInfo);
                // 递归调用
                getChildOrgInfo(childOrgInfo);
            }
        }
    }

    @Override
    public List<SelectInfoResult> getAllUserInfo() {
        return userService.getAllUserInfo();
    }

    @Override
    public List<SelectInfoResult> getAllOrgInfo() {

        return organizationService.getAllOrgInfo();
    }

    @Override
    public List<SelectInfoResult> getFlowableApproveGroupInfoByGroupIds(List<String> ids) {

        return allInterfacesDao.getFlowableApproveGroupInfoByGroupIds(ids);
    }

    @Override
    public List<SelectInfoResult> getFlowableApproveUserInfoByGroupIds(List<String> ids) {

        return allInterfacesDao.getFlowableApproveUserInfoByGroupIds(ids);
    }

    @Override
    public List<SelectInfoResult> getFlowableApproveGroupInfoByUserIds(List<String> ids) {

        return allInterfacesDao.getFlowableApproveGroupInfoByUserIds(ids);
    }

    @Override
    public List<UserGroupMapResult> getFlowableUserGroupMapByUserIds(List<String> ids) {
        return allInterfacesDao.getFlowableUserGroupMapByUserIds(ids);
    }

    @Override
    public List<UserGroupMapResult> getFlowableUserGroupMapByGroupIds(List<String> ids) {

        return allInterfacesDao.getFlowableUserGroupMapByGroupIds(ids);
    }

    @Override
    public List<RoleEntity> getRoleInfoByUserId(RoleEntityQueryRequest request) {

        return allInterfacesDao.getRoleInfoByUserId(request);
    }

    @Override
    public List<OrganizationEntity> getOrgInfoByUserId(
            OrgEntityQueryRequest orgEffectiveQueryParams) {

        return allInterfacesDao.getOrgInfoByUserId(orgEffectiveQueryParams);
    }
}
