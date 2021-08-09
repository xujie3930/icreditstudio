package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.result.SelectInfoResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.web.result.ExpertInfoResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.role.entity.RoleEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.entity.UserEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.entity.UserImportEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.param.*;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.result.LikeQueryUserListResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.result.UserEntityInfoResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.result.UserEntityResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.result.UserOrgListResult;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.request.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author hzh
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 分页查询
     *
     * @param pageRequest
     * @param loginUserId
     * @return
     */
    BusinessPageResult queryPage(UserEntityPageRequest pageRequest, String loginUserId);

    List<UserEntityResult> queryList(UserEntityExportRequest exportRequest, String loginUserId);

    List<SelectInfoResult> getAllUserInfo();

    BusinessResult<Boolean> saveUserEntity(UserEntitySaveParam param);

    BusinessResult<Boolean> updateUserEntity(UserEntitySaveParam param);

    BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response, Class<UserImportEntity> userEntityClass);

    BusinessResult<ExpertInfoResult> importExcel(MultipartFile file, UserOrgListResult param) throws IOException;

    List<OrganizationEntity> getOrgInfoByUsrId(UserInfoRequest params);

    List<UserEntity> getUserInfoByOrgId(OrgUserRequest params);

    List<UserEntity> getOrgChildUserInfoByOrgId(OrgUserRequest params);


    List<RoleEntity> getRoleInfoByUserId(UserInfoRequest params);

    List<LikeQueryUserListResult> queryUserInfoByName(LikeQueryUserInfoRequest params);

    /**
     * 根据部门id集合查询每个部门员工信息，返回聚合员工信息列表
     *
     * @param param 请求参数：orgIds：部门Id集合，deleteFlag：删除标识
     * @return 员工信息列表
     */
    BusinessResult<List<UserEntityInfoResult>> getUserInfosByOrgIds(UserInfosByOrgIdsQueryParam param);

    /**
     * 用户设置多个角色
     *
     * @param param 用户id和角色id列表
     */
    BusinessResult setUserConferredRoles(UserConferredRolesSaveParam param);

    /**
     * 上传头像
     *
     * @param param
     * @return
     */
    BusinessResult<Boolean> uploadPhoto(PhotoSaveParam param) throws IOException;

    BusinessResult<Boolean> editBase(UserEntityEditBaseParam param);

    BusinessResult<UserEntityInfoResult> info(String id);

    BusinessResult<Boolean> delete(UserEntityDelParam param);

    BusinessResult<Boolean> status(UserChangeStatusParam param);
}

