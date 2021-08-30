package com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result;

import com.jinninghui.datasphere.icreditstudio.system.modules.system.resources.entity.ResourcesEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;

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

    List<Map<String, String>>workspaceList;

    private Boolean workspaceCreateAuth = false;
}
