package com.jinninghui.datasphere.icreditstudio.system.modules.system.information.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.information.entity.InformationEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.information.service.param.*;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.information.service.result.*;

import java.util.List;

/**
 * @author 1
 */
public interface InformationService extends IService<InformationEntity> {

    BusinessResult<BusinessPageResult<InformationInBoxResult>> inBoxPage(InformationInBoxPageParam param);

    BusinessResult<BusinessPageResult<InformationResult>> outBoxPage(InformationOutBoxPageParam param);

    BusinessResult<Boolean> managerDelInfo(InformationManagerDelInfoParam param);

    BusinessResult<BusinessPageResult<InformationResult>> infoManagerPage(InformationManagerPageParam param);

    BusinessResult<Boolean> sendInfo(InformationSendParam param);

    BusinessResult<InfoCountResult> pollingUnreadInfos(InformationUnreadParam param);

    BusinessResult<BusinessPageResult<InfoNoticeResult>> infoNoticePage(InformationNoticePageParam param);

    BusinessResult<Boolean> deleteUserInfo(InformationDelUserInfoParam param);

    BusinessResult<InfoNoticeCountResult> infoCount(InformationCountParam param);

    BusinessResult<Boolean> deleteAllUserInfos(InformationDelAllUserInfoParam param);

    BusinessResult<Boolean> deleteOutBoxInfo(InformationDelUserInfoParam param);

    BusinessResult<Boolean> userAllReadInfo(InformationAllReadParam param);

    BusinessResult<InfoNoticeResult> userInfo(InformationUserInfoParam param);

    List<InformationEntity> selectInformationFromDataBase(InformationDataParam param);
}

