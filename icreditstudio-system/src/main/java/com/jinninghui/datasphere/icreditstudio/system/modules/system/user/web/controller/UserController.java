package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jinninghui.datasphere.icreditstudio.framework.result.BaseController;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.utils.excel.ExcelUtil;
import com.jinninghui.datasphere.icreditstudio.framework.validate.BusinessParamsValidate;
import com.jinninghui.datasphere.icreditstudio.system.common.log.Log;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.result.ExpertInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.entity.RoleEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity.UserEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity.UserImportEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.UserService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.param.*;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.result.*;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.request.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;


/**
 * @author hzh
 */
@RestController("res_userController")
@RequestMapping("user/user")
@RequiredArgsConstructor
public class UserController extends BaseController<UserEntity, UserService> {
    private final UserService userService;

    /**
     * 分页查询列表
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT, extend = "用户")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", httpMethod = "POST")
    @PostMapping("/pageList")
    public BusinessResult<BusinessPageResult> pageList(@ApiParam(name = "查询条件对象", value = "传入json格式", required = true)
                                                       @RequestBody UserEntityPageRequest pageRequest,
                                                       @RequestHeader(value = "x-userid") String loginUserId) {

        BusinessPageResult page = userService.queryPage(pageRequest, loginUserId);

        return BusinessResult.success(page);
    }


    /**
     * 信息
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @ApiOperation(value = "信息", notes = "信息", httpMethod = "GET")
    @GetMapping("/info/{id}")
    public BusinessResult<UserEntityInfoResult> info(@ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") String id) throws IOException {
        return userService.info(id);
    }

    /**
     * 保存
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.ADD, extend = "用户")
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping("/save")
    @BusinessParamsValidate
    public BusinessResult<Boolean> save(@ApiParam(name = "保存对象", value = "传入json格式", required = true) @RequestBody UserEntitySaveRequest request) {

        UserEntitySaveParam param = new UserEntitySaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return userService.saveUserEntity(param);
    }

    /**
     * 修改
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.UPDATE, extend = "用户")
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @PostMapping("/update")
    public BusinessResult<Boolean> update(@RequestBody UserEntitySaveRequest request) {

        UserEntitySaveParam param = new UserEntitySaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return userService.updateUserEntity(param);
    }

    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.UPDATE, extend = "用户")
    @PostMapping("/editBase")
    public BusinessResult<Boolean> editBase(@RequestBody UserEntityEditBaseRequest request) {
        UserEntityEditBaseParam param = new UserEntityEditBaseParam();
        BeanCopyUtils.copyProperties(request, param);
        return userService.editBase(param);
    }

    /**
     * 删除
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.DEL, extend = "用户")
    @PostMapping("/delete")
    public BusinessResult<Boolean> delete(@RequestBody UserEntityDelRequest request) {
        UserEntityDelParam param = new UserEntityDelParam();
        BeanCopyUtils.copyProperties(request, param);
        return userService.delete(param);
    }

    /**
     * 导出excel
     */
    @GetMapping(value = "/exportExcel")
    public BusinessResult<?> exportExcel(@RequestHeader(value = "x-userid") String loginUserId,
                                         UserEntityExportRequest export,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {

        List<UserEntityResult> userEntities = userService.queryList(export, loginUserId);
        ExcelUtil.exportExcel(response, "用户导出数据", userEntities, UserEntityResult.class);
        return BusinessResult.success("导出成功！");
    }

    @PostMapping(value = "/importExcel")
    public BusinessResult<ExpertInfoResult> importExcel(@RequestParam("file") MultipartFile file, @Valid @NotNull(message = "10000000") UserOrgListResult param) throws IOException {
        return userService.importExcel(file, param);
    }

    /**
     * 禁用用户 启用用户
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.STATUS, extend = "用户")
    @PostMapping("/changeUserStatusByUserId")
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<?> changeUserStatusByUserId(@RequestBody UserChangeStatusRequest request) {
        UserChangeStatusParam param = new UserChangeStatusParam();
        BeanCopyUtils.copyProperties(request, param);
        return userService.status(param);
    }


    /**
     * 获取当前登录用户信息
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @RequestMapping(value = {"/getLoginUserInfo"}, method = {RequestMethod.POST})
    public BusinessResult<UserEntity> getLoginUserInfo(@RequestHeader(value = "x-userid") String userId) {

        return BusinessResult.success(userService.getOne(new QueryWrapper<UserEntity>().eq("id", userId)));
    }

    /**
     * 根据用户主键id获取用户信息
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @RequestMapping(value = {"/getUserInfoByUsrId"}, method = {RequestMethod.POST})
    public BusinessResult<UserEntity> getUserInfoByUsrId(@RequestBody UserInfoRequest params) {

        return BusinessResult.success(userService.getOne(new QueryWrapper<UserEntity>().eq("id", params.getUserId())));
    }


    /**
     * 根据用户主键id获取用户所在部门信息
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @RequestMapping(value = {"/getOrgInfoByUsrId"}, method = {RequestMethod.POST})
    public BusinessResult<List<OrganizationEntity>> getOrgInfoByUsrId(@RequestBody UserInfoRequest params) {

        return BusinessResult.success(userService.getOrgInfoByUsrId(params));
    }

    /**
     * 根据部门编码或部门id获取本部门人员列表
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @RequestMapping(value = {"/getUserInfoByOrgId"}, method = {RequestMethod.POST})
    public BusinessResult<List<UserEntity>> getUserInfoByOrgId(@RequestBody OrgUserRequest params) {

        return BusinessResult.success(userService.getUserInfoByOrgId(params));
    }

    /**
     * 根据部门编码或部门id获取所有下级部门人员列表
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @RequestMapping(value = {"/getOrgChildUserInfoByOrgId"}, method = {RequestMethod.POST})
    public BusinessResult<List<UserEntity>> getOrgChildUserInfoByOrgId(@RequestBody OrgUserRequest params) {

        return BusinessResult.success(userService.getOrgChildUserInfoByOrgId(params));
    }

    /**
     * 根据用户主键id获取该用户的角色列表
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @RequestMapping(value = {"/getRoleInfoByUserId"}, method = {RequestMethod.POST})
    public BusinessResult<List<RoleEntity>> getRoleInfoByUserId(@RequestBody UserInfoRequest params) {

        return BusinessResult.success(userService.getRoleInfoByUserId(params));
    }

    /**
     * 模糊查询用户 姓名账号或者手机号
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @RequestMapping(value = {"/queryUserInfoByLikeName"}, method = {RequestMethod.POST})
    public BusinessResult<List<LikeQueryUserListResult>> queryUserInfoByLikeName(@RequestBody LikeQueryUserInfoRequest params) {

        return BusinessResult.success(userService.queryUserInfoByName(params));
    }


    @RequestMapping(value = {"/queryUserRoleByLikeName"}, method = {RequestMethod.POST})
    public BusinessResult<List<LikeQueryUserRoleListResult>> queryUserRoleByLikeName(@RequestBody LikeQueryUserRoleRequest params) {

        return BusinessResult.success(userService.queryUserRoleByName(params));
    }

    /**
     * 通过部门id集合取得员工列表
     *
     * @param request
     * @return
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @PostMapping("/getUserInfosByOrgIds")
    @BusinessParamsValidate
    public BusinessResult<List<UserEntityInfoResult>> getUserInfosByOrgIds(@RequestBody UserInfosByOrgIdsQueryRequest request) {
        UserInfosByOrgIdsQueryParam params = new UserInfosByOrgIdsQueryParam();
        BeanCopyUtils.copyProperties(request, params);
        return userService.getUserInfosByOrgIds(params);
    }

    //    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.UPDATE, extend = "用户")
    @PostMapping("/setUserToRoles")
    public BusinessResult setUserConferredRoles(@RequestBody UserConferredRolesSaveRequest request) {
        UserConferredRolesSaveParam param = new UserConferredRolesSaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return userService.setUserConferredRoles(param);
    }

    @GetMapping(value = "/exportModel")
    public BusinessResult<?> exportModel(HttpServletResponse response) {
        ExcelUtil.exportExcel(response, "用户导入模板", null, UserImportEntity.class);
        return BusinessResult.success("导出成功！");
    }

    @PostMapping("/uploadPhoto")
    @ApiOperation(value = "用户头像上传", notes = "用户头像上传", httpMethod = "POST")
    public BusinessResult<Boolean> uploadPhoto(@RequestBody PhotoSaveRequest request) throws IOException {
        PhotoSaveParam param = new PhotoSaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return userService.uploadPhoto(param);
    }
}
