package com.jinninghui.datasphere.icreditstudio.modules.system.setting.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.modules.system.setting.entity.SystemSettingsEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.setting.mapper.SystemSettingsMapper;
import com.jinninghui.datasphere.icreditstudio.modules.system.setting.service.SystemSettingsService;
import com.jinninghui.datasphere.icreditstudio.modules.system.setting.web.request.SystemLogoRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.setting.web.request.SystemSettingsEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.setting.web.result.SystemSettingResult;
import com.hashtech.businessframework.exception.interval.AppException;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.Query;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.sequence.api.SequenceService;
import com.hashtech.businessframework.utils.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Slf4j
@Service("systemSettingsService")
public class SystemSettingsServiceImpl
        extends ServiceImpl<SystemSettingsMapper, SystemSettingsEntity>
        implements SystemSettingsService {
    @Autowired
    private SequenceService sequenceService;

    @Override
    public BusinessPageResult queryPage(SystemSettingsEntityPageRequest pageRequest) {
        IPage<SystemSettingsEntity> page =
                this.page(
                        new Query<SystemSettingsEntity>().getPage(pageRequest),
                        new QueryWrapper<SystemSettingsEntity>());

        return BusinessPageResult.build(page, pageRequest);
    }

    /**
     * 保存系统设置
     *
     * @param setting 系统设置参数
     * @return 系统设置参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<SystemSettingsEntity> saveSystemSetting(SystemSettingsEntity setting) {
        // id为空，设置系统id
        if (StringUtils.isBlank(setting.getId())) {
            List<SystemSettingsEntity> list = super.list();
            if (CollectionUtils.isEmpty(list)) {
                setting.setId(sequenceService.nextValueString());
            } else {
                setting.setId(list.get(0).getId());
            }
        }
        setting.setCreateTime(System.currentTimeMillis());
        setting.setLastUpdateTime(System.currentTimeMillis());
        super.saveOrUpdate(setting);
        return BusinessResult.success(setting);
    }

    /**
     * 上传系统logo
     *
     * @param request 系统logo
     * @return Boolean
     */
    @Override
    public BusinessResult<Boolean> uploadSystemLogo(SystemLogoRequest request) throws IOException {
        String logo = request.getLogo();
        SystemSettingsEntity systemSettingsEntity = null;
        ByteArrayInputStream imageInputStream = null;
        byte[] decode = Base64Utils.decode(logo);
        Boolean imageType = validateImageType(decode);
        log.info("####### imageType:{}", imageType);
        if (!imageType) {
            throw new AppException("50009378");
        }
        for (int i = 0; i < decode.length; i++) {
            if (decode[i] < 0) {
                decode[i] += 256;
            }
        }
        // 图片格式校验
        imageInputStream = new ByteArrayInputStream(decode);
        int available = imageInputStream.available();
        if (available > 2 * 1024 * 1024) {
            throw new AppException("50009377");
        }
        systemSettingsEntity = new SystemSettingsEntity();
        if (StringUtils.isBlank(request.getId())) {
            request.setId(sequenceService.nextValueString());
        }
        systemSettingsEntity.setId(request.getId());
        systemSettingsEntity.setLastUpdateUserId(request.getUserId());
        systemSettingsEntity.setLastUpdateTime(System.nanoTime());
        systemSettingsEntity.setLogoPath(imageInputStream);
        log.info("systemSettingsEntity:" + systemSettingsEntity);
        return BusinessResult.success(super.updateById(systemSettingsEntity));
    }

    /**
     * 获取系统配置
     *
     * @return 系统配置信息
     */
    @Override
    public SystemSettingResult getSystemSetting() {
        SystemSettingResult systemSettingResult = null;
        // 系统首次获取配置信息时，返回系统配置信息为空
        List<SystemSettingsEntity> systemSettingsEntities = super.list();
        if (CollectionUtils.isNotEmpty(systemSettingsEntities)) {
            SystemSettingsEntity systemSettingsEntity = systemSettingsEntities.get(0);
            systemSettingResult =
                    BeanCopyUtils.copyProperties(systemSettingsEntity, SystemSettingResult.class);
            InputStream logo = systemSettingsEntity.getLogoPath();
            if (Objects.nonNull(logo)) {
                try {
                    String encode = Base64Utils.encode(IOUtils.toByteArray(logo));
                    systemSettingResult.setLogo(encode);
                    // log.info("systemSettingResult:" + systemSettingResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return systemSettingResult;
    }

    /**
     * 根据Base64格式解码转16进制后判断文件头部是否符合logo图片格式
     *
     * @param bytes Base64解码字节数组
     * @return 是否符合
     */
    private Boolean validateImageType(byte[] bytes) {
        StringJoiner joiner = new StringJoiner("");
        if (bytes != null) {
            for (Byte b : bytes) {
                joiner.add(String.format("%02X", b.intValue() & 0xFF));
            }
        }
        // log.info("########## joiner:" + joiner);
        String hexString = joiner.toString();
        // 判断上传图片格式
        if (hexString.startsWith("89504E470D0A1A0A")
                || hexString.startsWith("FFD8FF")
                || hexString.startsWith("49460001")
                || hexString.startsWith("69660000")
                || hexString.startsWith("424D")
                || hexString.startsWith("524946")
                || hexString.startsWith("46")
                || hexString.startsWith("57454250")) {
            return true;
        }
        return false;
    }
}
