package com.micro.cloud.modules.system.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.micro.cloud.api.CommonPage;
import com.micro.cloud.api.CommonResult;
import com.micro.cloud.modules.system.resource.controller.SysResourceController;
import com.micro.cloud.modules.system.user.service.InternalUserService;
import com.micro.cloud.modules.system.user.vo.SysUserPageReqVO;
import com.micro.cloud.modules.system.user.vo.SysUserUpdateReqVO;
import com.micro.cloud.modules.system.user.vo.UserDelReqVO;
import com.micro.cloud.modules.system.user.vo.external.ExternalUserUpdateStatusReqVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserCreateReqVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserInfoVO;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 个人用户管理
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Api(tags = "用户管理-系统用户(内部)")
@RestController
@RequestMapping("/sys/user/internal")
public class SysInternalUserController {
    private final Logger logger = LoggerFactory.getLogger(SysResourceController.class);

    private final InternalUserService userService;

    public SysInternalUserController(InternalUserService userService) {
        this.userService = userService;
    }

    /**
     * 用户分页查询
     *
     * @param vo     分页请求参数
     * @param userId 用户id
     * @return 分页记录
     */
    @ApiOperation(value = "系统用户分页查询", notes = "分页查询", httpMethod = "POST")
    @ApiResponses(@ApiResponse(code = 200, message = "执行成功", response = InternalUserInfoVO.class))
    @PostMapping("/page")
    public CommonResult<CommonPage<InternalUserInfoVO>> page(
            @ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody
                    SysUserPageReqVO vo,
            @RequestHeader(value = "userId") String userId) {
        CommonPage<InternalUserInfoVO> result = userService.page(vo, userId);
        return CommonResult.success(result);
    }

    /**
     * 根据用户id查看用户详情
     *
     * @param id 用户id
     * @return 用户详情
     */
    @ApiOperation(value = "系统用户信息查询", notes = "信息", httpMethod = "GET")
    @GetMapping("/info/{id}")
    public CommonResult<InternalUserInfoVO> info(
            @ApiParam(name = "id", value = "用户id", required = true) @PathVariable("id") String id /*,
      @RequestHeader(value = "orgId") String orgId*/) {
        String orgId = "";
        InternalUserInfoVO info = userService.info(id, orgId);
        return CommonResult.success(info);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "新增系统用户", notes = "新增", httpMethod = "POST")
    @PostMapping("/create")
    public CommonResult<String> create(
            @ApiParam(name = "保存对象", value = "传入json格式", required = true) @RequestBody
                    InternalUserCreateReqVO vo /*,
      @RequestHeader(value = "id") String creatorId,
      @RequestHeader(value = "orgId") String orgId*/) {
        try {
            String creatorId = "";
            String orgId = "";
            String userId = userService.create(vo, creatorId, orgId);
            return CommonResult.success(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改用户信息", notes = "修改", httpMethod = "POST")
    @PostMapping("/update")
    public CommonResult<Boolean> update(
            @RequestBody SysUserUpdateReqVO reqVO, @RequestHeader(value = "userId") String updaterId) {
        try {
            Boolean result = userService.updateUser(reqVO, updaterId);
            return CommonResult.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 是否成功
     */
    @ApiOperation(value = "删除")
    @GetMapping("/delete/{id}")
    public CommonResult<Boolean> delete(@PathVariable(value = "id") String id) {
        try {
            Boolean result = userService.removeById(id);
            return CommonResult.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }

    /**
     * 批量删除用户
     *
     * @param dto 批量删除信息
     * @return 是否成功
     */
    @ApiOperation(value = "批量删除")
    @PostMapping("/delete/batch")
    public CommonResult<Boolean> deleteBatch(@RequestBody UserDelReqVO dto) {
        try {
            Boolean result = userService.removeByIds(dto.getIds());
            return CommonResult.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }

    /**
     * 修改用户状态
     *
     * @param reqVO
     * @return
     */
    @ApiOperation("修改用户状态")
    @PostMapping("/update-status")
    public CommonResult<Boolean> updateUserStatus(
            @Valid @RequestBody ExternalUserUpdateStatusReqVO reqVO) {
        try {
            userService.updateUserStatusBatch(reqVO.getIds(), reqVO.getStatus());
            return CommonResult.success(true);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }

    /**
     * 批量修改用户状态
     *
     * @param reqVO
     * @return
     */
    @ApiOperation("批量修改用户状态")
    @PostMapping("/update-status/batch")
    public CommonResult<Boolean> updateUserStatusBatch(
            @Valid @RequestBody ExternalUserUpdateStatusReqVO reqVO) {
        try {
            Boolean result = userService.updateUserStatusBatch(reqVO.getIds(), reqVO.getStatus());
            return CommonResult.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }
}
