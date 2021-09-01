package com.jinninghui.datasphere.icreditstudio.system.modules.system.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.log.entity.LoginLogEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.log.service.param.LoginLogEntitySaveParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.log.web.request.LoginLogEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;

import java.util.List;

/**
 * @author 1
 */
public interface LoginLogService extends IService<LoginLogEntity> {

    /**
     * 分页查询
     *
     * @param pageRequest
     * @return
     */
    BusinessPageResult queryPage(LoginLogEntityPageRequest pageRequest);

    LoginLogEntity log(LoginLogEntitySaveParam param);

    List<LoginLogEntity> getLogByToken(String token);

}

