package com.jinninghui.datasphere.icreditstudio.modules.system.allinterface.result;

import com.jinninghui.datasphere.icreditstudio.modules.system.resources.entity.ResourcesEntity;
import lombok.Data;

import java.util.List;

/**
 * @author hzh
 * @description
 * @date 2021/2/24 16:06
 */
@Data
public class AuthResult {

    UserEntityAuthResult userInfo;
    List<ResourcesEntity> menus;
    List<String> authList;

    /**
     * 用户个性化快捷菜单
     */
    List<ResourcesEntity> shortMenus;

    UserSettings setting;
}
