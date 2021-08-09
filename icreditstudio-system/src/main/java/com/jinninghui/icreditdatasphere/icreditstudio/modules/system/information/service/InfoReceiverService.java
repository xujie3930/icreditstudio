package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.entity.InfoReceiverEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.service.param.InfoReceiverDataParam;

import java.util.List;

/**
 * 
 *
 * @author 1
 */
public interface InfoReceiverService extends IService<InfoReceiverEntity> {

    List<InfoReceiverEntity> getInfoReceiverFromDatabase(InfoReceiverDataParam param);
}

