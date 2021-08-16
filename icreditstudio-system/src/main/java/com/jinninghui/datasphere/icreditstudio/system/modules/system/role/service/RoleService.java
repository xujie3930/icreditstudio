package com.jinninghui.datasphere.icreditstudio.system.modules.system.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.param.UserRoleDataQueryParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result.SelectInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.entity.ResourcesEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.service.result.ResourcesEntityResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.entity.RoleEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.service.param.*;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.service.result.RoleEntityInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.result.UserEntityInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.result.RoleEntityResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.web.request.RoleEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.web.request.RoleEntityQueryParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.web.request.RoleEntityStatusParams;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.web.request.RoleUserQueryParam;

import java.util.List;
import java.util.Set;

/**
 * @author hzh
 */
public interface RoleService extends IService<RoleEntity> {

    /**
     * 分页查询
     *
     * @param pageRequest
     * @return
     */
    BusinessPageResult queryPage(RoleEntityPageRequest pageRequest);

    List<RoleEntityInfoResult> getRoleInfoByUserId(RoleEntityQueryParam request);


    /**
     * 获取全部角色信息
     *
     * @return
     */
    List<SelectInfoResult> getAllRoleInfo();

    List<SelectInfoResult> getChildrenByParentId(RoleEntityQueryParam request);


    boolean isAdmin(UserRoleDataQueryParam param);


    List<UserEntityInfoResult> getUserInfoByRoleId(RoleUserQueryParam request);

    /**
     * 给多个用户设置同一权限
     *
     * @param params userIds 用户id集合,roleId角色id
     * @return 失败会回滚
     */
    BusinessResult<Boolean> setRoleToUsers(RoleToUsersSaveParam params);

    /**
     * 获取角色树
     *
     * @return
     */
    BusinessResult<List<SelectInfoResult>> getRoleTree(RoleEntityQueryParam param);

    /**
     * 修改角色状态(禁用角色及子角色,启用角色及子角色)
     *
     * @param params
     * @return
     */
    BusinessResult<Boolean> status(RoleEntityStatusParams params);

    BusinessResult<RoleEntity> save(RoleEntitySaveParam param);

    BusinessResult<Boolean> updateRole(RoleEntitySaveParam param);

    BusinessResult<Boolean> deleteRole(RoleEntityDelParam param);

    BusinessResult<List<ResourcesEntityResult>> getResourcesFromRole(ResourcesFromRoleQueryParam param);

    BusinessResult<Boolean> setResourcesToRole(ResourcesToRoleSaveParam param);

    BusinessResult<List<RoleEntityResult>> getCurrAndSonRoles(CurrAndSonRoleQueryParam param);
    /**
     * 取得用户当前角色及子角色
     * @param userId
     * @return
     */
    Set<String> findRoleAndSonByUserId(String userId);

    List<ResourcesEntity> findResourcesByUserId(String userId);
}

