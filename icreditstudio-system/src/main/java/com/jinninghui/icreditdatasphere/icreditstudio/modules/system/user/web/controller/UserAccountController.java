package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.controller;

import com.jinninghui.icreditdatasphere.icreditstudio.common.log.Log;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.entity.UserAccountEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.UserAccountService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.request.UserAccountEntityPageRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.request.UserAccountEntityRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.request.UserAccountRequestParams;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.request.UserAccountResetParams;
import com.hashtech.businessframework.result.BaseController;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.sequence.api.SequenceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


/**
 * @author hzh
 */
@RestController
@RequestMapping("user/useraccount")
@RequiredArgsConstructor
public class UserAccountController extends BaseController<UserAccountEntity, UserAccountService> {

    private final UserAccountService userAccountService;
    private final SequenceService sequenceService;

    /**
     * 分页查询列表
     */
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", httpMethod = "POST")
    @PostMapping("/pageList")
    public BusinessResult<BusinessPageResult> pageList(@ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody UserAccountEntityPageRequest pageRequest) {

        BusinessPageResult page = userAccountService.queryPage(pageRequest);

        return BusinessResult.success(page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "信息", notes = "信息", httpMethod = "GET")
    @GetMapping("/info/{id}")
    public BusinessResult<UserAccountEntity> info(@ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") String id) {

        UserAccountEntity userAccount = userAccountService.getById(id);

        return BusinessResult.success(userAccount);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping("/save")
    public BusinessResult<UserAccountEntity> save(@ApiParam(name = "保存对象", value = "传入json格式", required = true) @RequestBody UserAccountEntityRequest request) {

        UserAccountEntity userAccount = BeanCopyUtils.copyProperties(request, UserAccountEntity.class);

        userAccount.setId(String.valueOf(sequenceService.nextValue(null)));
        userAccountService.save(userAccount);

        return BusinessResult.success(userAccount);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @PostMapping("/update")
    public BusinessResult<UserAccountEntity> update(@ApiParam(name = "修改对象", value = "传入json格式", required = true) @RequestBody UserAccountEntityRequest request) {

        UserAccountEntity userAccount = BeanCopyUtils.copyProperties(request, UserAccountEntity.class);

        userAccountService.updateById(userAccount);

        return BusinessResult.success(userAccount);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping("/delete")
    public BusinessResult<Boolean> delete(@ApiParam(name = "删除ID", value = "传入json格式", required = true) @RequestBody String[] ids) {

        userAccountService.removeByIds(Arrays.asList(ids));

        return BusinessResult.success(true);
    }

    /**
     * 导出excel
     */
    @ApiOperation(value = "导出excel", notes = "导出excel", httpMethod = "GET")
    @GetMapping(value = "/exportExcel")
    public BusinessResult<?> exportExcel(HttpServletRequest request, HttpServletResponse response, UserAccountEntity userAccount) {

        return super.exportExcel(request, response, userAccount, UserAccountEntity.class, "userAccount");
    }

    /**
     * 通过excel导入数据
     */
    @ApiOperation(value = "通过excel导入数据", notes = "通过excel导入数据", httpMethod = "POST")
    @PostMapping(value = "/importExcel")
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return super.importExcel(request, response, UserAccountEntity.class);
    }


    /**
     * 重置密码
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.UPDATE, extend = "账户")
    @ApiOperation(value = "重置密码", notes = "重置密码", httpMethod = "POST")
    @PostMapping("/resetPassword")
    public BusinessResult<Boolean> resetPassword(@ApiParam(name = "重置密码userID", value = "传入json格式", required = true)
                                                 @RequestBody UserAccountResetParams params,
                                                 @RequestHeader("x-token") String token) {

        userAccountService.resetPassword(params.getUserIdList(), token);

        return BusinessResult.success(true);
    }

    /**
     * 修改密码
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.UPDATE,extend = "账户")
    @ApiOperation(value = "修改密码", notes = "修改密码", httpMethod = "POST")
    @PostMapping("/changePassword")
    public BusinessResult<?> changePassword(@ApiParam(name = "修改密码", value = "传入json格式", required = true) @RequestBody UserAccountRequestParams userParams) {

        return userAccountService.changePassword(userParams);

    }


}
