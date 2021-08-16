package com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.interfaces.entity.InterfacesEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.interfaces.service.InterfacesService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.entity.ResourcesEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.service.ResourcesService;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.common.dto.Interface;
import com.jinninghui.datasphere.icreditstudio.system.modules.uaa.service.InterfaceLoadService;
import com.jinninghui.datasphere.icreditstudio.framework.log.Logable;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hzh
 * @description
 * @date 2021/2/24 13:24
 */
@Service
public class UaaInterfaceLoadServiceImpl implements InterfaceLoadService {

    @Autowired
    private InterfacesService interfacesService;

    @Autowired
    private ResourcesService resourcesService;

    @Logable
    @Override
    public List<Interface> loadInterface() {
        List<InterfacesEntity> list = interfacesService.list();
        List<Interface> copy = BeanCopyUtils.copy(list, Interface.class);
        return copy;
    }

    @Override
    public List<String> loadButtonMenuUrlList() {
        // 查询 有效的 按钮类型url
        List<ResourcesEntity> resourcesEntityList = resourcesService.list(new QueryWrapper<ResourcesEntity>()
                .eq("type", "B")
                .eq("delete_flag", "N")
        );

        if (CollectionUtils.isNotEmpty(resourcesEntityList)) {
            return resourcesEntityList.stream().map(ResourcesEntity::getUrl).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }


}
