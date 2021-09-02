package com.jinninghui.datasphere.icreditstudio.system.modules.system.setting.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jinninghui.datasphere.icreditstudio.system.modules.system.setting.entity.SystemSettingsEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.setting.service.SystemSettingsService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.setting.web.request.SystemLogoRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.setting.web.request.SystemSettingsEntityDelRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.setting.web.request.SystemSettingsEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.setting.web.request.SystemSettingsEntityRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.setting.web.result.SystemSettingResult;
import com.jinninghui.datasphere.icreditstudio.framework.log.Logable;
import com.jinninghui.datasphere.icreditstudio.framework.result.BaseController;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.validate.BusinessParamsValidate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 1
 */
@Slf4j
@RestController
@RequestMapping("system/settings")
public class SystemSettingsController
        extends BaseController<SystemSettingsEntity, SystemSettingsService> {

    @Autowired
    private SystemSettingsService systemSettingsService;

    /**
     * 分页查询列表
     */
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", httpMethod = "POST")
    @PostMapping("/pageList")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<BusinessPageResult> pageList(
            @ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody
                    SystemSettingsEntityPageRequest pageRequest) {

        BusinessPageResult page = systemSettingsService.queryPage(pageRequest);

        return BusinessResult.success(page);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "信息", notes = "信息", httpMethod = "GET")
    @GetMapping("/info")
    @Logable
    public BusinessResult<SystemSettingResult> info(
            @RequestHeader(value = "x-userid") String userId) {
        log.info("######### x-userid:{}", userId);
        SystemSettingResult systemSetting = systemSettingsService.getSystemSetting();
        return BusinessResult.success(systemSetting);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping("/save")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<SystemSettingsEntity> save(
            @RequestHeader(value = "x-userid") String userId,
            @ApiParam(name = "保存对象", value = "传入json格式", required = true) @RequestBody
                    SystemSettingsEntityRequest request) {
        SystemSettingsEntity systemSettings =
                BeanCopyUtils.copyProperties(request, SystemSettingsEntity.class);
        systemSettings.setLastUpdateUserId(userId);
        return systemSettingsService.saveSystemSetting(systemSettings);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @PostMapping("/update")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<SystemSettingsEntity> update(
            @RequestHeader(value = "x-userid") String userId,
            @ApiParam(name = "修改对象", value = "传入json格式", required = true) @RequestBody
                    SystemSettingsEntityRequest request) {
        SystemSettingsEntity systemSettings =
                BeanCopyUtils.copyProperties(request, SystemSettingsEntity.class);
        systemSettings.setLastUpdateUserId(userId);
        systemSettings.setLastUpdateTime(System.currentTimeMillis());
        systemSettingsService.updateById(systemSettings);

        return BusinessResult.success(systemSettings);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping("/delete")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<Boolean> delete(
            @ApiParam(name = "删除ID", value = "传入json格式", required = true) @RequestBody
                    SystemSettingsEntityDelRequest params) {

        return BusinessResult.success(systemSettingsService.removeByIds(params.getIds()));
    }

    /**
     * 导出excel
     */
    @ApiOperation(value = "导出excel", notes = "导出excel", httpMethod = "GET")
    @GetMapping(value = "/exportExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> exportExcel(
            HttpServletRequest request,
            HttpServletResponse response,
            SystemSettingsEntity systemSettings) {

        return super.exportExcel(
                request, response, systemSettings, SystemSettingsEntity.class, "systemSettings");
    }

    /**
     * 通过excel导入数据
     */
    @ApiOperation(value = "通过excel导入数据", notes = "通过excel导入数据", httpMethod = "POST")
    @PostMapping(value = "/importExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return super.importExcel(request, response, SystemSettingsEntity.class);
    }

    @ApiOperation(value = "上传系统logo", notes = "上传系统logo", httpMethod = "POST")
    @PostMapping(value = "/uploadLogo")
    @BusinessParamsValidate
//  @Logable
    public BusinessResult<Boolean> uploadLogo(@RequestBody SystemLogoRequest request)
            throws Exception {

        return systemSettingsService.uploadSystemLogo(request);
    }
}
