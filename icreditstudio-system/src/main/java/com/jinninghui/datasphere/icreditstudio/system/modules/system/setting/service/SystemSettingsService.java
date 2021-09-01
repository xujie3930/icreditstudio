package com.jinninghui.datasphere.icreditstudio.system.modules.system.setting.service;

import java.io.IOException;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.setting.entity.SystemSettingsEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.setting.web.request.SystemLogoRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.setting.web.request.SystemSettingsEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.setting.web.result.SystemSettingResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;

/**
 * @author 1
 */
public interface SystemSettingsService extends IService<SystemSettingsEntity> {

    /**
     * 分页查询
     *
     * @param pageRequest
     * @return
     */
    BusinessPageResult queryPage(SystemSettingsEntityPageRequest pageRequest);

    /**
     * 保存系统设置
     *
     * @param setting 系统设置参数
     * @return 系统设置参数
     */
    BusinessResult<SystemSettingsEntity> saveSystemSetting(SystemSettingsEntity setting);

    /**
     * 上传系统logo
     *
     * @param request 系统logo
     * @return Boolean
     */
    BusinessResult<Boolean> uploadSystemLogo(SystemLogoRequest request) throws IOException;

    /**
     * 获取系统配置
     *
     * @return 系统配置信息
     */
    SystemSettingResult getSystemSetting();
}
