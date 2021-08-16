package com.jinninghui.datasphere.icreditstudio.system.modules.system.shortcutmenu.service;

import java.io.IOException;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.shortcutmenu.entity.ShortcutMenuEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.shortcutmenu.service.param.EnableCustomMenuParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.shortcutmenu.web.request.ShortcutMenuEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.shortcutmenu.web.request.ShortcutMenuEntityRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.shortcutmenu.web.result.ShortCutMenuResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;

/**
 * 快捷菜单
 *
 * @author 1
 */
public interface ShortcutMenuService extends IService<ShortcutMenuEntity> {

  /**
   * 分页查询
   *
   * @param pageRequest
   * @return
   */
  BusinessPageResult queryPage(ShortcutMenuEntityPageRequest pageRequest);

  /**
   * 保存用户个性化设置
   *
   * @param request 个性化设置参数
   * @return 个性化设置参数
   */
  BusinessResult<ShortcutMenuEntityRequest> saveShortCutMenuSetting(
          ShortcutMenuEntityRequest request);

  /**
   * 更改用户个性化设置
   *
   * @param request 个性化设置参数
   * @return 个性化设置参数
   */
  BusinessResult<ShortcutMenuEntityRequest> updateShortCutMenuSetting(
          ShortcutMenuEntityRequest request);

  /**
   * 获取用户个性化设置信息
   *
   * @param userId 用户id
   * @return 用户快捷设置
   */
  ShortCutMenuResult getShortCutMenuInfo(String userId) throws IOException;

  /**
   * 启用自定义菜单
   * @param param
   * @return
   */
  BusinessResult<Boolean> enableCustomMenu(EnableCustomMenuParam param);
}
