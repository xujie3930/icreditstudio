package com.jinninghui.datasphere.icreditstudio.modules.system.log.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.common.enums.DeleteFlagEnum;
import com.jinninghui.datasphere.icreditstudio.common.log.Log;
import com.jinninghui.datasphere.icreditstudio.common.log.LogHandler;
import com.jinninghui.datasphere.icreditstudio.common.log.LogHandlerUtil;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.entity.CodeInfoEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.service.CodeInfoService;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.service.param.CodeInfoEntityConditionParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.web.request.CodeInfoEntityDelRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.web.request.CodeInfoEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.web.request.CodeInfoEntitySaveRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.dict.web.request.CodeInfoEntityStatusRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.entity.InterfacesEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.service.InterfacesService;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.service.param.InterfacesEntityConditionParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.service.param.InterfacesEntitySaveRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.web.request.InterfacesDelRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.web.request.InterfacesEntitySaveParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.log.entity.AuditLogEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.log.service.AuditLogService;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.service.OrganizationService;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.service.param.OrganizationEntityConditionParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.web.request.OrgEntityDelRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.web.request.OrganizationEntityQueryRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.web.request.OrganizationEntitySaveRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.web.request.OrganizationEntityStatusRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.entity.ResourcesEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.service.ResourcesService;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.service.param.ResourcesEntityConditionParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.web.request.*;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.entity.RoleEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.service.RoleService;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.service.param.RoleEntityConditionParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.role.web.request.*;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserOrgMapEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.UserOrgMapService;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.UserService;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.param.UserEntityConditionParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.web.request.*;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.web.result.UserOrgListResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by PPai on 2021/6/2 10:59
 */
@Component
public class AuditLogHandler implements LogHandler<AuditLogEntity> {
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private CodeInfoService codeInfoService;
    @Autowired
    private InterfacesService interfacesService;
    @Autowired
    private UserOrgMapService userOrgMapService;

    @Override
    public AuditLogEntity pre(HttpServletRequest request, Method method, Object[] args) {
        LogHandlerUtil util = LogHandlerUtil.build(method);
        util.filter((type, operateType) -> Log.Type.AUDIT.equals(type));

        AuditLogEntity entity = new AuditLogEntity();
        Optional<AuditLogEntity> map = util.map((type, operateType) -> {
            String userId = request.getHeader("x-userid");
            AuditLogEntity logEntity = new AuditLogEntity();
            logEntity.setUserId(userId);
            if (Log.OperateType.STATUS.equals(operateType)) {
                logEntity.setOprateType(Log.OperateType.UPDATE.getCode());
            } else {
                logEntity.setOprateType(operateType.getCode());
            }
            UserEntity byId = userService.getById(userId);
            if (Objects.nonNull(byId)) {
                logEntity.setUserName(byId.getUserName());
            }
            logEntity.setOprateTime(String.valueOf(System.currentTimeMillis()));
//            String requestURI = request.getRequestURI();

            String extend = util.getExtend();
            String operateInfo = findOperateInfoByOperateType(operateType, extend, args);
            logEntity.setOprateInfo(operateInfo);
            auditLogService.save(logEntity);
            return logEntity;
        });
        if (map.isPresent()) {
            entity = map.get();
        }
        return entity;
    }

    @Override
    public void post(HttpServletRequest request, Method method, Object o, AuditLogEntity logEntity) {
        LogHandlerUtil util = LogHandlerUtil.build(method);
        util.filter((type, operateType) -> Log.Type.AUDIT.equals(type) && o instanceof BusinessResult);
        util.consumer((type, operateType) -> {
            BusinessResult result = (BusinessResult) o;
            if (result.isSuccess() && StringUtils.isNotBlank(logEntity.getId())) {
                logEntity.setOprateResult("S");
            } else {
                logEntity.setOprateResult("F");
            }
            auditLogService.saveOrUpdate(logEntity);
        });
    }

    @Override
    public void ex(HttpServletRequest request, Method method, Throwable t, AuditLogEntity logEntity) {
        LogHandlerUtil util = LogHandlerUtil.build(method);
        util.filter((type, operateType) -> Log.Type.AUDIT.equals(type) && StringUtils.isNotBlank(logEntity.getId()));
        util.consumer((type, operateType) -> {
            logEntity.setOprateResult("F");
            String oprateInfo = logEntity.getOprateInfo();
            StringBuilder sb = new StringBuilder(oprateInfo);
            sb.append(",失败原因:")
                    .append(LogHandlerUtil.getAppExceptionErrorMsg(t));
            logEntity.setOprateInfo(sb.toString());
            auditLogService.saveOrUpdate(logEntity);
        });
    }

    private String findOperateInfoByOperateType(Log.OperateType operateType, String extend, Object[] args) {
        String info = "";
        if (Log.OperateType.ADD.equals(operateType)) {
            info = addOperateInfo(extend, args);
        }
        if (Log.OperateType.UPDATE.equals(operateType)) {
            info = updateOperateInfo(extend, args);
        }
        if (Log.OperateType.STATUS.equals(operateType)) {
            info = statusOperateInfo(extend, args);
        }
        if (Log.OperateType.DEL.equals(operateType)) {
            info = deleteOperateInfo(extend, args);
        }
        if (Log.OperateType.SELECT.equals(operateType)) {
            info = selectOperateInfo(extend, args);
        }
        return info;
    }

    private String updateOperateInfo(String extend, Object[] args) {
        String info = "";
        for (Object arg : args) {
            //更新部门
            String updateOrg = updateOrg(extend, arg);
            if (StringUtils.isNotBlank(updateOrg)) {
                info = updateOrg;
            }
            //更新角色
            String updateRole = updateRole(extend, arg);
            if (StringUtils.isNotBlank(updateRole)) {
                info = updateRole;
            }
            //更新用户
            String updateUser = updateUser(extend, arg);
            if (StringUtils.isNotBlank(updateUser)) {
                info = updateUser;
            }
            //更新用户基本信息
            String editBaseUser = updateEditBaseUser(extend, arg);
            if (StringUtils.isNotBlank(editBaseUser)) {
                info = editBaseUser;
            }
            //重置用户密码
            String resetPassword = updateResetPassword(extend, arg);
            if (StringUtils.isNotBlank(resetPassword)) {
                info = resetPassword;
            }
            //更新模块信息
            String resourcesInfo = updateResourcesInfo(extend, arg);
            if (StringUtils.isNotBlank(resourcesInfo)) {
                info = resourcesInfo;
            }
            //更新字典信息
            String dictInfo = updateDictInfo(extend, arg);
            if (StringUtils.isNotBlank(dictInfo)) {
                info = dictInfo;
            }
            //更新接口信息
            String anInterface = updateInterface(extend, arg);
            if (StringUtils.isNotBlank(anInterface)) {
                info = anInterface;
            }
            //给角色配置用户
            String usersToRole = updateUsersToRole(extend, arg);
            if (StringUtils.isNotBlank(usersToRole)) {
                info = usersToRole;
            }
            //给角色配置模块
            String resourceToRole = updateResourcesToRole(extend, arg);
            if (StringUtils.isNotBlank(resourceToRole)) {
                info = resourceToRole;
            }
            //给模块添加角色
            String rolesToResource = updateRolesToResource(extend, arg);
            if (StringUtils.isNotBlank(rolesToResource)) {
                info = rolesToResource;
            }
            String updatePassword = updateAccountPassword(extend, arg);
            if (StringUtils.isNotBlank(updatePassword)) {
                info = updatePassword;
            }
        }
        return info;
    }

    private String updateInterface(String extend, Object arg) {
        String info = "";
        if (Log.Extend.INTERFACE.getDesc().equals(extend)
                && arg instanceof InterfacesEntitySaveRequest) {
            InterfacesEntitySaveRequest request = (InterfacesEntitySaveRequest) arg;
            InterfacesEntitySaveRequest newEntity = BeanCopyUtils.copyProperties(request, InterfacesEntitySaveRequest.class);
            String interfaceId = newEntity.getInterfaceId();
            List<InterfacesEntity> interfaces = getInterfaces(InterfacesEntityConditionParam.builder().id(interfaceId).build());
            String updateInterfaceInfo = "";
            if (CollectionUtils.isNotEmpty(interfaces)) {
                updateInterfaceInfo = updateInterfaceInfo(newEntity, interfaces.get(0));
                updateInterfaceInfo = trimStringWith(updateInterfaceInfo, ',');
            }
            info = updateInfo(extend, updateInterfaceInfo);
        }

        return info;
    }

    private String updateUsersToRole(String extend, Object arg) {
        String info = "";
        if (Log.Extend.ROLE.getDesc().equals(extend)
                && arg instanceof RoleToUsersSaveRequest) {
            RoleToUsersSaveRequest request = (RoleToUsersSaveRequest) arg;
            RoleToUsersSaveRequest newEntity = BeanCopyUtils.copyProperties(request, RoleToUsersSaveRequest.class);
            String roleId = newEntity.getRoleId();
            List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder().ids(Sets.newHashSet(roleId)).build());
            String roleName = "";
            if (CollectionUtils.isNotEmpty(roles)) {
                roleName = roles.get(0).getRoleName();
            }
            String updateRoleInfo = updateRoleToUsersInfo(newEntity);
            info = roleToUsersInfo(extend, roleName, updateRoleInfo);
        }

        return info;
    }

    private String roleToUsersInfo(String extend, String role, String info) {
        StringBuilder sb = new StringBuilder(extend);
        sb.append(":").append(role).append(",配置用户:").append(info);
        return sb.toString();
    }

    private String updateResourcesToRole(String extend, Object arg) {
        String info = "";
        if (Log.Extend.ROLE.getDesc().equals(extend)
                && arg instanceof ResourcesToRoleSaveRequest) {
            ResourcesToRoleSaveRequest request = (ResourcesToRoleSaveRequest) arg;
            ResourcesToRoleSaveRequest newEntity = BeanCopyUtils.copyProperties(request, ResourcesToRoleSaveRequest.class);
            String roleId = newEntity.getRoleId();
            List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder().ids(Sets.newHashSet(roleId)).build());
            String roleName = "";
            if (CollectionUtils.isNotEmpty(roles)) {
                roleName = roles.get(0).getRoleName();
            }
            String updateResourcesInfo = updateResourcesToRoleInfo(newEntity);
            info = resourceToRoleInfo(extend, roleName, updateResourcesInfo);
        }
        return info;
    }

    private String resourceToRoleInfo(String extend, String role, String info) {
        StringBuilder sb = new StringBuilder(extend);
        sb.append(":").append(role).append(",配置功能:").append(info);
        return sb.toString();
    }

    private String updateResourcesToRoleInfo(ResourcesToRoleSaveRequest request) {
        String info = "";
        Set<String> resourcesIds = request.getResourcesIds();
        if (CollectionUtils.isNotEmpty(resourcesIds)) {
            List<ResourcesEntity> resources = getResources(ResourcesEntityConditionParam.builder().ids(resourcesIds).build());
            Set<String> resourcesNames = Optional.ofNullable(resources).orElse(Lists.newArrayList())
                    .parallelStream()
                    .filter(Objects::nonNull)
                    .map(ResourcesEntity::getName)
                    .collect(Collectors.toSet());
            info = JSONObject.toJSONString(resourcesNames);
        }
        return info;
    }

    private String updateRolesToResource(String extend, Object arg) {
        String info = "";
        if (Log.Extend.ROLE.getDesc().equals(extend)
                && arg instanceof RolesToResourceSaveRequest) {
            RolesToResourceSaveRequest request = (RolesToResourceSaveRequest) arg;
            RolesToResourceSaveRequest newEntity = BeanCopyUtils.copyProperties(request, RolesToResourceSaveRequest.class);
            String resourcesId = newEntity.getResourcesId();
            List<ResourcesEntity> resources = getResources(ResourcesEntityConditionParam.builder().ids(Sets.newHashSet(resourcesId)).build());
            String resourceName = "";
            if (CollectionUtils.isNotEmpty(resources)) {
                resourceName = resources.get(0).getName();
            }
            String updateRolesToResourceInfo = updateRolesToResource(newEntity);
            info = rolesToResources(extend, resourceName, updateRolesToResourceInfo);

        }
        return info;
    }

    private String updateAccountPassword(String extend, Object arg) {
        String info = "";
        if (Log.Extend.ACCOUNT.getDesc().equals(extend)
                && arg instanceof UserAccountRequestParams) {
            UserAccountRequestParams request = (UserAccountRequestParams) arg;
            UserAccountRequestParams newEntity = BeanCopyUtils.copyProperties(request, UserAccountRequestParams.class);
            String userId = newEntity.getUserId();
            List<UserEntity> users = getUsers(UserEntityConditionParam.builder().ids(Sets.newHashSet(userId)).build());
            String userName = "";
            if (CollectionUtils.isNotEmpty(users)) {
                userName = users.get(0).getUserName();
            }
            info = updateAccountPasswordInfo(userName);
        }
        return info;
    }

    private String updateAccountPasswordInfo(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("修改密码");
        return sb.toString();
    }

    private String rolesToResources(String extend, String name, String info) {
        StringBuilder sb = new StringBuilder(extend);
        sb.append(":").append(name).append(",配置角色:").append(info);
        return sb.toString();
    }

    private String updateRolesToResource(RolesToResourceSaveRequest request) {
        String info = "";
        Set<String> roleIds = request.getRoleIds();
        if (CollectionUtils.isNotEmpty(roleIds)) {
            List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder().ids(roleIds).build());
            Set<String> roleNames = Optional.ofNullable(roles).orElse(Lists.newArrayList())
                    .parallelStream()
                    .filter(Objects::nonNull)
                    .map(RoleEntity::getRoleName)
                    .collect(Collectors.toSet());
            info = JSONObject.toJSONString(roleNames);
        }
        return info;
    }

    private String updateUser(String extend, Object arg) {
        String info = "";
        if (Log.Extend.USER.getDesc().equals(extend)
                && arg instanceof UserEntitySaveRequest) {
            UserEntitySaveRequest request = (UserEntitySaveRequest) arg;
            UserEntitySaveRequest newEntity = BeanCopyUtils.copyProperties(request, UserEntitySaveRequest.class);
            String id = newEntity.getId();
            List<UserEntity> users = getUsers(UserEntityConditionParam.builder().ids(Sets.newHashSet(id)).build());
            String updateUserInfo = "";
            if (CollectionUtils.isNotEmpty(users)) {
                updateUserInfo = updateUserInfo(newEntity, users.get(0));
                updateUserInfo = trimStringWith(updateUserInfo, ',');
            }
            info = updateInfo(extend, updateUserInfo);
        }

        return info;
    }

    private String updateRole(String extend, Object arg) {
        String info = "";
        if (Log.Extend.ROLE.getDesc().equals(extend)
                && arg instanceof RoleEntitySaveRequest) {
            RoleEntitySaveRequest request = (RoleEntitySaveRequest) arg;
            RoleEntitySaveRequest newEntity = BeanCopyUtils.copyProperties(request, RoleEntitySaveRequest.class);
            String id = newEntity.getId();
            List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder().ids(Sets.newHashSet(id)).build());
            String updateRoleInfo = "";
            if (CollectionUtils.isNotEmpty(roles)) {
                updateRoleInfo = updateRoleInfo(newEntity, roles.get(0));
                updateRoleInfo = trimStringWith(updateRoleInfo, ',');
            }
            info = updateInfo(extend, updateRoleInfo);
        }

        return info;
    }

    private String updateOrg(String extend, Object arg) {
        String info = "";
        if (Log.Extend.ORGANIZATION.getDesc().equals(extend) && arg instanceof OrganizationEntitySaveRequest) {
            OrganizationEntitySaveRequest request = (OrganizationEntitySaveRequest) arg;
            OrganizationEntitySaveRequest newEntity = BeanCopyUtils.copyProperties(request, OrganizationEntitySaveRequest.class);
            String id = newEntity.getId();
            List<OrganizationEntity> oldEntity = getOrganizations(OrganizationEntityConditionParam.builder().ids(Sets.newHashSet(id)).build());
            String updateData = "";
            if (CollectionUtils.isNotEmpty(oldEntity)) {
                updateData = updateOrgInfo(newEntity, oldEntity.get(0));
                updateData = trimStringWith(updateData, ',');
            }
            info = updateInfo(extend, updateData);
        }

        return info;
    }

    private String updateEditBaseUser(String extend, Object arg) {
        String info = "";
        if (Log.Extend.USER.getDesc().equals(extend)
                && arg instanceof UserEntityEditBaseRequest) {
            UserEntityEditBaseRequest request = (UserEntityEditBaseRequest) arg;
            UserEntityEditBaseRequest newEntity = BeanCopyUtils.copyProperties(request, UserEntityEditBaseRequest.class);
            String id = newEntity.getId();
            List<UserEntity> users = getUsers(UserEntityConditionParam.builder().ids(Sets.newHashSet(id)).build());
            String updateUserInfo = "";
            if (CollectionUtils.isNotEmpty(users)) {
                updateUserInfo = updateUserBaseInfo(newEntity, users.get(0));
                updateUserInfo = trimStringWith(updateUserInfo, ',');
            }
            info = updateInfo(extend, updateUserInfo);
        }

        return info;
    }

    private String updateResetPassword(String extend, Object arg) {
        String info = "";
        if (Log.Extend.USER.getDesc().equals(extend)
                && arg instanceof UserAccountResetParams) {
            UserAccountResetParams newEntity = (UserAccountResetParams) arg;
            Set<String> userIdList = newEntity.getUserIdList();
            List<UserEntity> users = getUsers(UserEntityConditionParam.builder().ids(userIdList).build());
            UserEntity entity = users.get(0);
            String userName = resetPasswordInfo(entity.getUserName());
            info = updateInfo(extend, userName);
        }

        return info;
    }

    private String updateDictInfo(String extend, Object arg) {
        String info = "";
        if (Log.Extend.DICT.getDesc().equals(extend)
                && arg instanceof CodeInfoEntitySaveRequest) {
            CodeInfoEntitySaveRequest request = (CodeInfoEntitySaveRequest) arg;
            CodeInfoEntitySaveRequest newEntity = BeanCopyUtils.copyProperties(request, CodeInfoEntitySaveRequest.class);
            String id = newEntity.getId();
            List<CodeInfoEntity> dicts = getDicts(CodeInfoEntityConditionParam.builder().ids(Sets.newHashSet(id)).build());
            String updateDictInfo = "";
            if (CollectionUtils.isNotEmpty(dicts)) {
                CodeInfoEntity entity = dicts.get(0);
                updateDictInfo = updateDictInfo(newEntity, entity);
                updateDictInfo = trimStringWith(updateDictInfo, ',');
            }
            info = updateInfo(extend, updateDictInfo);
        }

        return info;
    }

    private String updateResourcesInfo(String extend, Object arg) {
        String info = "";
        if (Log.Extend.RESOURCES.getDesc().equals(extend)
                && arg instanceof ResourcesEntitySaveRequest) {
            ResourcesEntitySaveRequest request = (ResourcesEntitySaveRequest) arg;
            ResourcesEntitySaveRequest newEntity = BeanCopyUtils.copyProperties(request, ResourcesEntitySaveRequest.class);
            String id = newEntity.getId();
            List<ResourcesEntity> resources = getResources(ResourcesEntityConditionParam.builder().ids(Sets.newHashSet(id)).build());
            String updateResourcesInfo = "";
            if (CollectionUtils.isNotEmpty(resources)) {
                ResourcesEntity entity = resources.get(0);
                updateResourcesInfo = updateResourcesInfo(newEntity, entity);
                updateResourcesInfo = trimStringWith(updateResourcesInfo, ',');
            }
            info = updateInfo(extend, updateResourcesInfo);
        }
        return info;
    }

    private String resetPasswordInfo(String info) {
        StringBuilder sb = new StringBuilder("重置");
        sb.append(info).append("的密码");
        return sb.toString();
    }

    private String updateInfo(String target, String msg) {
        StringBuilder sb = new StringBuilder(Log.OperateType.UPDATE.getDesc());
        sb.append(target).append(":").append(msg);
        return sb.toString();
    }

    private String updateOrgInfo(OrganizationEntitySaveRequest newEntity, OrganizationEntity oldEntity) {
//        return updateInfo(newEntity, oldEntity);
        String parentIdN = newEntity.getParentId();
        if (StringUtils.isNotBlank(parentIdN)) {
            List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().ids(Sets.newHashSet(parentIdN)).build());
            if (CollectionUtils.isNotEmpty(organizations)) {
                newEntity.setParentId(organizations.get(0).getOrgName());
            }
        }
        String parentIdO = oldEntity.getParentId();
        if (StringUtils.isNotBlank(parentIdO)) {
            List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().ids(Sets.newHashSet(parentIdO)).build());
            if (CollectionUtils.isNotEmpty(organizations)) {
                oldEntity.setParentId(organizations.get(0).getOrgName());
            }
        }
        return getChangeFiledInfo(newEntity, oldEntity, k -> getOrgFieldNameFromMap(k));
    }

    private String getOrgFieldNameFromMap(String field) {
        Map<String, String> map = Maps.newHashMap();
        map.put("linkManName", ",联系人:");
        map.put("linkManTel", ",联系方式:");
        map.put("orgAddress", ",部门地址:");
        map.put("orgCode", ",部门编号:");
        map.put("orgName", ",部门名称:");
        map.put("orgRemark", ",备注:");
        map.put("parentId", ",父部门:");
        map.put("sortNumber", ",排序:");
        map.put("type", ",类型:");
        return map.get(field);
    }

    private Map<String, String>[] updateChangesToFind(Map<String, Object> newData, Map<String, Object> oldData) {
        Map<String, String> old = Maps.newHashMap();
        Map<String, String> news = Maps.newHashMap();
        newData.forEach((k, v) -> {
            Object o = oldData.get(k);
            if (Objects.nonNull(o)) {
                if (!o.equals(v)) {
                    old.put(k, o + "");
                    news.put(k, v + "");
                }
            }
        });
        Map<String, String>[] a = new Map[2];
        a[0] = old;
        a[1] = news;
        return a;
    }

    private Map<String, Object> objToMap(Object o) {
        Map<String, Object> map = Maps.newHashMap();
        Map<String, Object> stringObjectMap = JSONObject.parseObject(JSONObject.toJSONString(o), new TypeReference<Map<String, Object>>() {
        });
        if (MapUtils.isNotEmpty(stringObjectMap)) {
            map = stringObjectMap;
        }
        return map;
    }

    private Map<String, String>[] updateInfo(Object nData, Object oData) {
        Map<String, Object> news = objToMap(nData);
        Map<String, Object> olds = objToMap(oData);
        Map<String, String>[] lists = updateChangesToFind(news, olds);
        /*Map<String, String> o = lists[0];
        Map<String, String> n = lists[1];
        StringBuilder sb = new StringBuilder();
        sb.append("将").append(JSONArray.toJSONString(o))
                .append("修改为：").append(JSONArray.toJSONString(n));
        return sb.toString();*/
        return lists;
    }

    private String getInterfaceFieldNameFromMap(String field) {
        Map<String, String> map = Maps.newHashMap();
        map.put("method", ",请求类型:");
        map.put("module", ",所属模块:");
        map.put("name", ",接口名称:");
        map.put("needAuth", ",是否鉴权:");
        map.put("remark", ",备注:");
        map.put("supportAuthType", ",鉴权方式:");
        map.put("uri", ",接口地址:");
        map.put("uriType", ",类型:");
        return map.get(field);
    }

    private String updateUserBaseInfo(UserEntityEditBaseRequest newEntity, UserEntity oldEntity) {
//        return updateInfo(newEntity, oldEntity);
        return getChangeFiledInfo(newEntity, oldEntity, k -> getUserBaseFieldNameFromMap(k));
    }

    private String getUserBaseFieldNameFromMap(String field) {
        Map<String, String> map = Maps.newHashMap();
        map.put("telPhone", ",手机号码:");
        map.put("userBirth", ",生日:");
        map.put("userCode", ",工号:");
        map.put("userGender", ",性别:");
        map.put("userName", ",用户名称:");
        map.put("userRemark", ",备注:");
        return map.get(field);
    }

    private String updateUserInfo(UserEntitySaveRequest newEntity, UserEntity oldEntity) {
//        return updateInfo(newEntity, oldEntity);
        List<UserOrgListResult> orgList = newEntity.getOrgList();
        Set<String> newOrgIds = Optional.ofNullable(orgList).orElse(Lists.newArrayList())
                .parallelStream()
                .filter(Objects::nonNull)
                .map(UserOrgListResult::getOrgId)
                .collect(Collectors.toSet());
        List<OrganizationEntity> newOrgs = getOrganizations(OrganizationEntityConditionParam.builder().ids(newOrgIds).build());
        Set<String> newOrgNames = newOrgs.parallelStream().filter(Objects::nonNull)
                .map(OrganizationEntity::getOrgName)
                .collect(Collectors.toSet());
        newEntity.setTempOrg(JSONObject.toJSONString(newOrgNames));

        String id = oldEntity.getId();
        List<UserOrgMapEntity> userOrgMaps = getUserOrgMaps(Sets.newHashSet(id));
        Set<String> orgIds = Optional.ofNullable(userOrgMaps).orElse(Lists.newArrayList())
                .parallelStream()
                .filter(Objects::nonNull)
                .map(UserOrgMapEntity::getOrgId)
                .collect(Collectors.toSet());
        List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().ids(orgIds).build());
        Set<String> oldOrgNames = organizations.parallelStream()
                .filter(Objects::nonNull)
                .map(OrganizationEntity::getOrgName)
                .collect(Collectors.toSet());
        oldEntity.setTempOrg(JSONObject.toJSONString(oldOrgNames));
        return getChangeFiledInfo(newEntity, oldEntity, k -> getUserFieldNameFromMap(k));
    }

    private String getUserFieldNameFromMap(String field) {
        Map<String, String> map = Maps.newHashMap();
        map.put("accountIdentifier", ",账号:");
        map.put("deleteFlag", ",状态:");
        map.put("telPhone", ",电话:");
        map.put("userBirth", ",生日:");
        map.put("userCode", ",工号:");
        map.put("userGender", ",性别:");
        map.put("userName", ",用户名称:");
        map.put("userRemark", ",备注:");
        map.put("tempOrg", "所属部门");
        return map.get(field);
    }

    private String updateInterfaceInfo(InterfacesEntitySaveRequest newEntity, InterfacesEntity oldEntity) {
        return getChangeFiledInfo(newEntity, oldEntity, k -> getInterfaceFieldNameFromMap(k));
    }

    private String updateRoleToUsersInfo(RoleToUsersSaveRequest newEntity) {
        String info = "";
        List<String> userIds = newEntity.getUserIds();
        if (CollectionUtils.isNotEmpty(userIds)) {
            List<UserEntity> users = getUsers(UserEntityConditionParam.builder().ids(Sets.newHashSet(userIds)).build());
            Set<String> roleNames = Optional.ofNullable(users).orElse(Lists.newArrayList())
                    .parallelStream()
                    .filter(Objects::nonNull)
                    .map(UserEntity::getUserName)
                    .collect(Collectors.toSet());
            info = JSONObject.toJSONString(roleNames);
        }
        return info;
    }

    private String updateChangeInfo(Object nd, Object od, BiFunction<Map<String, String>, Map<String, String>, String> function) {
        Map<String, String>[] maps = updateInfo(nd, od);
        Map<String, String> o = maps[0];
        Map<String, String> n = maps[1];
        return function.apply(n, o);
    }

    private String getChangeFiledInfo(Object nd, Object od, Function<String, String> getFieldName) {
        return updateChangeInfo(nd, od, (n, o) -> {
            StringBuilder ns = new StringBuilder();
            n.forEach((k, v) -> {
//                String fieldName = getInterfaceFieldNameFromMap(k);
                String fieldName = getFieldName.apply(k);
                if (StringUtils.isNotBlank(fieldName)) {
                    ns.append(fieldName).append(v);
                }
            });
            StringBuilder os = new StringBuilder();
            o.forEach((k, v) -> {
//                String fieldName = getInterfaceFieldNameFromMap(k);
                String fieldName = getFieldName.apply(k);
                if (StringUtils.isNotBlank(fieldName)) {
                    os.append(fieldName).append(v);
                }
            });

            StringBuilder sb = new StringBuilder();
            String olds = trimStringWith(os.toString(), ',');
            String news = trimStringWith(ns.toString(), ',');
            sb.append("将").append("[").append(olds).append("]")
                    .append(",修改为:").append("[").append(news).append("]");
            return sb.toString();
        });
    }

    private String updateRoleInfo(RoleEntitySaveRequest newEntity, RoleEntity oldEntity) {
//        return updateInfo(newEntity, oldEntity);
        String parentIdN = newEntity.getParentId();
        if (StringUtils.isNotBlank(parentIdN)) {
            List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder().ids(Sets.newHashSet(parentIdN)).build());
            if (CollectionUtils.isNotEmpty(roles)) {
                newEntity.setParentId(roles.get(0).getRoleName());
            }
        }
        String parentIdO = oldEntity.getParentId();
        if (StringUtils.isNotBlank(parentIdO)) {
            List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder().ids(Sets.newHashSet(parentIdO)).build());
            if (CollectionUtils.isNotEmpty(roles)) {
                oldEntity.setParentId(roles.get(0).getRoleName());
            }
        }
        return getChangeFiledInfo(newEntity, oldEntity, k -> getRoleFieldNameFromMap(k));
    }

    private String getRoleFieldNameFromMap(String field) {
        Map<String, String> map = Maps.newHashMap();
        map.put("roleName", ",角色名称:");
        map.put("roleRemark", ",备注:");
        map.put("parentId", ",父角色:");
        return map.get(field);
    }

    private String updateResourcesInfo(ResourcesEntitySaveRequest newEntity, ResourcesEntity oldEntity) {
//        return updateInfo(newEntity, oldEntity);
        String parentIdN = newEntity.getParentId();
        if (StringUtils.isNotBlank(parentIdN)) {
            List<ResourcesEntity> resources = getResources(ResourcesEntityConditionParam.builder().ids(Sets.newHashSet(parentIdN)).build());
            if (CollectionUtils.isNotEmpty(resources)) {
                newEntity.setParentId(resources.get(0).getName());
            }
        }
        String parentIdO = oldEntity.getParentId();
        if (StringUtils.isNotBlank(parentIdO)) {
            List<ResourcesEntity> resources = getResources(ResourcesEntityConditionParam.builder().ids(Sets.newHashSet(parentIdO)).build());
            if (CollectionUtils.isNotEmpty(resources)) {
                oldEntity.setParentId(resources.get(0).getName());
            }
        }
        return getChangeFiledInfo(newEntity, oldEntity, k -> getResourcesFieldNameFromMap(k));
    }

    private String getResourcesFieldNameFromMap(String field) {
        Map<String, String> map = Maps.newHashMap();
        map.put("authIdentification", ",字典名称:");
        map.put("filePath", ",文件路径:");
        map.put("iconPath", ",图标:");
        map.put("isShow", ",是否在菜单显示:");
        map.put("keepAlive", ",是否缓存:");
        map.put("name", ",模块名称:");
        map.put("parentId", ",父模块:");
        map.put("redirectPath", ",重定向地址:");
        map.put("remark", ",备注:");
        map.put("sortNumber", ",排序:");
        map.put("type", ",模块类型:");
        map.put("url", ",路径:");
        return map.get(field);
    }

    private String updateDictInfo(CodeInfoEntitySaveRequest newEntity, CodeInfoEntity oldEntity) {
        return getChangeFiledInfo(newEntity, oldEntity, k -> getDictFieldNameFromMap(k));
    }

    private String getDictFieldNameFromMap(String field) {
        Map<String, String> map = Maps.newHashMap();
        map.put("codeName", ",字典名称:");
        map.put("codeRemark", ",备注:");
        map.put("codeSort", ",排序:");
        map.put("codeType", ",字段类型:");
        map.put("codeValue", ",字典值:");
        return map.get(field);
    }

    private String addOperateInfo(String extend, Object[] args) {
        String info = "";
        for (Object arg : args) {
            if (Log.Extend.ORGANIZATION.getDesc().equals(extend) && arg instanceof OrganizationEntitySaveRequest) {
                OrganizationEntitySaveRequest request = (OrganizationEntitySaveRequest) arg;
                info = addInfo(extend, request.getOrgName());
                break;
            }
            if (Log.Extend.ROLE.getDesc().equals(extend) && arg instanceof RoleEntitySaveRequest) {
                RoleEntitySaveRequest request = (RoleEntitySaveRequest) arg;
                info = addInfo(extend, request.getRoleName());
                break;
            }
            if (Log.Extend.USER.getDesc().equals(extend) && arg instanceof UserEntitySaveRequest) {
                UserEntitySaveRequest request = (UserEntitySaveRequest) arg;
                info = addInfo(extend, request.getUserName());
                break;
            }
            if (Log.Extend.RESOURCES.getDesc().equals(extend) && arg instanceof ResourcesEntitySaveRequest) {
                ResourcesEntitySaveRequest request = (ResourcesEntitySaveRequest) arg;
                info = addInfo(extend, request.getName());
            }
            if (Log.Extend.INTERFACE.getDesc().equals(extend) && arg instanceof InterfacesEntitySaveParam) {
                InterfacesEntitySaveParam request = (InterfacesEntitySaveParam) arg;
                info = addInfo(extend, request.getName());
                break;
            }
            if (Log.Extend.DICT.getDesc().equals(extend) && arg instanceof CodeInfoEntitySaveRequest) {
                CodeInfoEntitySaveRequest request = (CodeInfoEntitySaveRequest) arg;
                info = addInfo(extend, request.getCodeName());
                break;
            }
        }
        return info;
    }

    private String addInfo(String target, String msg) {
        StringBuilder sb = new StringBuilder(Log.OperateType.ADD.getDesc());
        sb.append(target).append(":").append(msg);
        return sb.toString();
    }

    private String statusOperateInfo(String extend, Object[] args) {
        String info = "";
        for (Object arg : args) {
            if (Log.Extend.ORGANIZATION.getDesc().equals(extend) && arg instanceof OrganizationEntityStatusRequest) {
                OrganizationEntityStatusRequest request = (OrganizationEntityStatusRequest) arg;
                String id = request.getId();
                List<OrganizationEntity> oldEntity = getOrganizations(OrganizationEntityConditionParam.builder().ids(Sets.newHashSet(id)).build());
                String name = "";
                if (CollectionUtils.isNotEmpty(oldEntity)) {
                    OrganizationEntity entity = oldEntity.get(0);
                    name = entity.getOrgName();
                }
                String status = DeleteFlagEnum.find(request.getDeleteFlag()).getDesc();
                info = statusInfo(extend, status, name);
                break;
            }
            if (Log.Extend.ROLE.getDesc().equals(extend) && arg instanceof RoleEntityStatusRequest) {
                RoleEntityStatusRequest request = (RoleEntityStatusRequest) arg;
                String id = request.getId();
                List<RoleEntity> oldEntity = getRoles(RoleEntityConditionParam.builder().ids(Sets.newHashSet(id)).build());
                String name = "";
                if (CollectionUtils.isNotEmpty(oldEntity)) {
                    RoleEntity entity = oldEntity.get(0);
                    name = entity.getRoleName();
                }
                String status = DeleteFlagEnum.find(request.getDeleteFlag()).getDesc();
                info = statusInfo(extend, status, name);
                break;
            }
            if (Log.Extend.USER.getDesc().equals(extend) && arg instanceof UserChangeStatusRequest) {
                UserChangeStatusRequest request = (UserChangeStatusRequest) arg;
                String id = request.getUserId();
                List<UserEntity> oldEntity = getUsers(UserEntityConditionParam.builder().ids(Sets.newHashSet(id)).build());
                String name = "";
                if (CollectionUtils.isNotEmpty(oldEntity)) {
                    UserEntity entity = oldEntity.get(0);
                    name = entity.getUserName();
                }
                String status = DeleteFlagEnum.find(request.getDeleteFlag()).getDesc();
                info = statusInfo(extend, status, name);
                break;
            }
            if (Log.Extend.RESOURCES.getDesc().equals(extend) && arg instanceof ResChangeStatusRequest) {
                ResChangeStatusRequest request = (ResChangeStatusRequest) arg;
                List<String> resourcesIdList = request.getResourcesIdList();
                String id = resourcesIdList.get(0);
                List<ResourcesEntity> oldEntity = getResources(ResourcesEntityConditionParam.builder().ids(Sets.newHashSet(id)).build());
                String name = "";
                if (CollectionUtils.isNotEmpty(oldEntity)) {
                    ResourcesEntity entity = oldEntity.get(0);
                    name = entity.getName();
                }
                String status = DeleteFlagEnum.find(request.getDeleteFlag()).getDesc();
                info = statusInfo(extend, status, name);
            }
            /*if (Log.Extend.INTERFACE.getDesc().equals(extend) && arg instanceof InterfacesEntityRequest) {
                InterfacesEntityRequest request = (InterfacesEntityRequest) arg;
                info = addInfo(extend, request.getName());
                break;
            }*/
            if (Log.Extend.DICT.getDesc().equals(extend) && arg instanceof CodeInfoEntityStatusRequest) {
                CodeInfoEntityStatusRequest request = (CodeInfoEntityStatusRequest) arg;
                String id = request.getId();
                List<CodeInfoEntity> oldEntity = getDicts(CodeInfoEntityConditionParam.builder().ids(Sets.newHashSet(id)).build());
                String name = "";
                if (CollectionUtils.isNotEmpty(oldEntity)) {
                    CodeInfoEntity entity = oldEntity.get(0);
                    name = entity.getCodeName();
                }
                String status = DeleteFlagEnum.find(request.getDeleteFlag()).getDesc();
                info = statusInfo(extend, status, name);
                break;
            }
        }
        return info;
    }

    private String statusInfo(String extend, String status, String name) {
        StringBuilder sb = new StringBuilder(status);
        sb.append(extend).append(":").append(name);
        return sb.toString();
    }

    private String selectOperateInfo(String extend, Object[] args) {
        String info = "";
        for (Object arg : args) {
            if (Log.Extend.USER.getDesc().equals(extend) && arg instanceof UserEntityPageRequest) {
                UserEntityPageRequest selectRequest = (UserEntityPageRequest) arg;
                String selectUserInfo = selectUserInfo(selectRequest);
                info = selectInfo(extend, selectUserInfo);
                break;
            }
            if (Log.Extend.DICT.getDesc().equals(extend) && arg instanceof CodeInfoEntityPageRequest) {
                CodeInfoEntityPageRequest selectRequest = (CodeInfoEntityPageRequest) arg;
                String selectDictInfo = selectDictInfo(selectRequest);
                info = selectInfo(extend, selectDictInfo);
                break;
            }
            if (Log.Extend.RESOURCES.getDesc().equals(extend) && arg instanceof ResourcesEntityQueryRequest) {
                ResourcesEntityQueryRequest selectRequest = (ResourcesEntityQueryRequest) arg;
                String selectResourcesInfo = selectResourcesInfo(selectRequest);
                info = selectInfo(extend, selectResourcesInfo);
                break;
            }
            if (Log.Extend.ROLE.getDesc().equals(extend) && arg instanceof RoleEntityQueryRequest) {
                RoleEntityQueryRequest selectRequest = (RoleEntityQueryRequest) arg;
                String selectRoleInfo = selectRoleInfo(selectRequest);
                info = selectInfo(extend, selectRoleInfo);
                break;
            }
            if (Log.Extend.ORGANIZATION.getDesc().equals(extend) && arg instanceof OrganizationEntityQueryRequest) {
                OrganizationEntityQueryRequest selectRequest = (OrganizationEntityQueryRequest) arg;
                String selectOrganizationInfo = selectOrganizationInfo(selectRequest);
                info = selectInfo(extend, selectOrganizationInfo);
                break;
            }
        }
        return info;
    }

    private String selectInfo(String extend, String msg) {
        StringBuilder sb = new StringBuilder(Log.OperateType.SELECT.getDesc());
        sb.append(extend).append("列表");
        if (StringUtils.isNotBlank(msg)) {
            sb.append("查询条件:").append(msg);
        }
        return sb.toString();
    }

    private String selectUserInfo(UserEntityPageRequest selectRequest) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(selectRequest.getUserName())) {
            sb.append("用户姓名:").append(selectRequest.getUserName());
        }
        if (StringUtils.isNotBlank(selectRequest.getAccountIdentifier())) {
            sb.append(" 账号:").append(selectRequest.getAccountIdentifier());
        }
        if (StringUtils.isNotBlank(selectRequest.getTelPhone())) {
            sb.append(" 手机号码:").append(selectRequest.getTelPhone());
        }
        if (CollectionUtils.isNotEmpty(selectRequest.getOrgIds())) {
            sb.append(" 部门:");
            List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().ids(Sets.newHashSet(selectRequest.getOrgIds())).build());
            Set<String> orgNames = organizations.stream()
                    .map(OrganizationEntity::getOrgName)
                    .collect(Collectors.toSet());
            sb.append(JSONObject.toJSONString(orgNames));
        }
        return sb.toString();
    }

    private String selectRoleInfo(RoleEntityQueryRequest selectRequest) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(selectRequest.getRoleName())) {
            sb.append("角色名称:").append(selectRequest.getRoleName());
        }
        if (StringUtils.isNotBlank(selectRequest.getDeleteFlag())) {
            sb.append(" 状态:").append(selectRequest.getDeleteFlag());
        }
        return sb.toString();
    }

    private String selectDictInfo(CodeInfoEntityPageRequest selectRequest) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(selectRequest.getCodeName())) {
            sb.append("字典名称:").append(selectRequest.getCodeName());
        }
        return sb.toString();
    }

    private String selectResourcesInfo(ResourcesEntityQueryRequest selectRequest) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(selectRequest.getName())) {
            sb.append("模块名称:").append(selectRequest.getName());
        }
        return sb.toString();
    }

    private String selectOrganizationInfo(OrganizationEntityQueryRequest selectRequest) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(selectRequest.getOrgName())) {
            sb.append("部门名称:").append(selectRequest.getOrgName());
        }
        if (StringUtils.isNotBlank(selectRequest.getLinkManName())) {
            sb.append(" 联系人:").append(selectRequest.getLinkManName());
        }
        return sb.toString();
    }

    private String deleteOperateInfo(String extend, Object[] args) {
        String info = "";
        for (Object arg : args) {
            if (Log.Extend.USER.getDesc().equals(extend) && arg instanceof UserEntityDelRequest) {
                UserEntityDelRequest delRequest = (UserEntityDelRequest) arg;
                String userNames = delUserInfo(delRequest);
                info = delInfo(extend, userNames);
                break;
            }
            if (Log.Extend.DICT.getDesc().equals(extend) && arg instanceof CodeInfoEntityDelRequest) {
                CodeInfoEntityDelRequest delRequest = (CodeInfoEntityDelRequest) arg;
                String dictNames = delDictInfo(delRequest);
                info = delInfo(extend, dictNames);
                break;
            }
            if (Log.Extend.RESOURCES.getDesc().equals(extend) && arg instanceof ResEntityDelRequest) {
                ResEntityDelRequest delRequest = (ResEntityDelRequest) arg;
                String resourceNames = delResourcesInfo(delRequest);
                info = delInfo(extend, resourceNames);
                break;
            }
            if (Log.Extend.ROLE.getDesc().equals(extend) && arg instanceof RoleEntityDelRequest) {
                RoleEntityDelRequest delRequest = (RoleEntityDelRequest) arg;
                String roleNames = delRoleInfo(delRequest);
                info = delInfo(extend, roleNames);
                break;
            }
            if (Log.Extend.ORGANIZATION.getDesc().equals(extend) && arg instanceof OrgEntityDelRequest) {
                OrgEntityDelRequest delRequest = (OrgEntityDelRequest) arg;
                String delOrgNames = delOrgInfo(delRequest);
                info = delInfo(extend, delOrgNames);
                break;
            }
            if (Log.Extend.INTERFACE.getDesc().equals(extend) && arg instanceof InterfacesDelRequest) {
                InterfacesDelRequest delRequest = (InterfacesDelRequest) arg;
                String delInterfaceNames = delInterfaceInfo(delRequest);
                info = delInfo(extend, delInterfaceNames);
                break;
            }
        }
        return info;
    }

    private String delInfo(String target, String msg) {
        StringBuilder sb = new StringBuilder(Log.OperateType.DEL.getDesc());
        sb.append(target);
        if (StringUtils.isNotBlank(msg)) {
            sb.append(":").append(msg);
        }
        return sb.toString();
    }

    private String delUserInfo(UserEntityDelRequest request) {
        String info = "";
        Set<String> ids = request.getIds();
        if (CollectionUtils.isNotEmpty(ids)) {
            List<UserEntity> users = getUsers(UserEntityConditionParam.builder().ids(ids).build());
            StringBuilder sb = new StringBuilder();
            for (UserEntity user : users) {
                sb = sb.append(user.getUserName()).append(",");
            }
            info = sb.toString();
        }
        info = trimStringWith(info, ',');
        return info;
    }

    private String delRoleInfo(RoleEntityDelRequest request) {
        String info = "";
        Set<String> ids = request.getIds();
        if (CollectionUtils.isNotEmpty(ids)) {
            List<RoleEntity> roles = getRoles(RoleEntityConditionParam.builder().ids(ids).build());
            StringBuilder sb = new StringBuilder();
            for (RoleEntity role : roles) {
                sb = sb.append(role.getRoleName()).append(",");
            }
            info = sb.toString();
        }
        info = trimStringWith(info, ',');
        return info;
    }

    private String delInterfaceInfo(InterfacesDelRequest request) {
        String info = "";
        Set<String> ids = request.getIds();
        if (CollectionUtils.isNotEmpty(ids)) {
            List<InterfacesEntity> interfaces = getInterfaces(InterfacesEntityConditionParam.builder().ids(ids).build());
            StringBuilder sb = new StringBuilder();
            for (InterfacesEntity anInterface : interfaces) {
                sb = sb.append(anInterface.getName()).append(",");
            }
            info = sb.toString();
        }
        info = trimStringWith(info, ',');
        return info;
    }

    private String trimStringWith(String str, char beTrim) {
        int st = 0;
        int len = str.length();
        char[] val = str.toCharArray();
        char sbeTrim = beTrim;
        while ((st < len) && (val[st] <= sbeTrim)) {
            st++;
        }
        while ((st < len) && (val[len - 1] <= sbeTrim)) {
            len--;
        }
        return ((st > 0) || (len < str.length())) ? str.substring(st, len) : str;
    }

    private String delOrgInfo(OrgEntityDelRequest request) {
        String info = "";
        Set<String> ids = request.getIds();
        if (CollectionUtils.isNotEmpty(ids)) {
            List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().ids(ids).build());
            StringBuilder sb = new StringBuilder();
            for (OrganizationEntity organization : organizations) {
                sb = sb.append(organization.getOrgName()).append(",");
            }
            info = sb.toString();
        }
        info = trimStringWith(info, ',');
        return info;
    }

    private String delResourcesInfo(ResEntityDelRequest request) {
        String info = "";
        Set<String> ids = request.getIds();
        if (CollectionUtils.isNotEmpty(ids)) {
            List<ResourcesEntity> resources = getResources(ResourcesEntityConditionParam.builder().ids(ids).build());
            StringBuilder sb = new StringBuilder();
            for (ResourcesEntity resource : resources) {
                sb = sb.append(resource.getName()).append(",");
            }
            info = sb.toString();
        }
        info = trimStringWith(info, ',');
        return info;
    }

    private String delDictInfo(CodeInfoEntityDelRequest request) {
        String info = "";
        Set<String> ids = request.getIds();
        if (CollectionUtils.isNotEmpty(ids)) {
            List<CodeInfoEntity> dicts = getDicts(CodeInfoEntityConditionParam.builder().ids(ids).build());
            StringBuilder sb = new StringBuilder();
            for (CodeInfoEntity dict : dicts) {
                sb = sb.append(dict.getCodeName()).append(",");
            }
            info = sb.toString();
        }
        info = trimStringWith(info, ',');
        return info;
    }

    private List<UserEntity> getUsers(UserEntityConditionParam param) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(UserEntity.ID, param.getIds());
        }
        return userService.list(wrapper);
    }

    private List<RoleEntity> getRoles(RoleEntityConditionParam param) {
        QueryWrapper<RoleEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(UserEntity.ID, param.getIds());
        }
        return roleService.list(wrapper);
    }

    private List<OrganizationEntity> getOrganizations(OrganizationEntityConditionParam param) {
        QueryWrapper<OrganizationEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(OrganizationEntity.ID, param.getIds());
        }
        return organizationService.list(wrapper);
    }

    private List<ResourcesEntity> getResources(ResourcesEntityConditionParam param) {
        QueryWrapper<ResourcesEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(ResourcesEntity.ID, param.getIds());
        }
        return resourcesService.list(wrapper);
    }

    private List<CodeInfoEntity> getDicts(CodeInfoEntityConditionParam param) {
        QueryWrapper<CodeInfoEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(CodeInfoEntity.ID, param.getIds());
        }
        return codeInfoService.list(wrapper);
    }

    private List<InterfacesEntity> getInterfaces(InterfacesEntityConditionParam param) {
        QueryWrapper<InterfacesEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getId())) {
            wrapper.eq(InterfacesEntity.INTERFACE_ID, param.getId());
        }
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(InterfacesEntity.INTERFACE_ID, param.getIds());
        }
        return interfacesService.list(wrapper);
    }

    private List<UserOrgMapEntity> getUserOrgMaps(Set<String> userIds) {
        QueryWrapper<UserOrgMapEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(userIds)) {
            wrapper.in(UserOrgMapEntity.USER_ID, userIds);
        }
        return userOrgMapService.list(wrapper);
    }
}
