package com.jinninghui.datasphere.icreditstudio.modules.system.shortcutmenu.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.modules.system.allinterface.service.AllInterfacesService;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.entity.ResourcesEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.resources.mapper.ResourcesDao;
import com.jinninghui.datasphere.icreditstudio.modules.system.shortcutmenu.entity.ShortcutMenuEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.shortcutmenu.mapper.ShortcutMenuMapper;
import com.jinninghui.datasphere.icreditstudio.modules.system.shortcutmenu.service.ShortcutMenuService;
import com.jinninghui.datasphere.icreditstudio.modules.system.shortcutmenu.service.param.EnableCustomMenuParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.shortcutmenu.web.request.ShortcutMenuEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.shortcutmenu.web.request.ShortcutMenuEntityRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.shortcutmenu.web.result.ShortCutMenuResult;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.entity.UserEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.mapper.UserDao;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.Query;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.sequence.api.SequenceService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("shortcutMenuService")
public class ShortcutMenuServiceImpl extends ServiceImpl<ShortcutMenuMapper, ShortcutMenuEntity>
        implements ShortcutMenuService {

    @Autowired
    private ShortcutMenuMapper shortcutMenuMapper;

    @Autowired
    private UserDao userMapper;

    @Autowired
    private ResourcesDao resourcesDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AllInterfacesService allInterfacesService;

    @Autowired
    private SequenceService sequenceService;

    @Override
    public BusinessPageResult queryPage(ShortcutMenuEntityPageRequest pageRequest) {
        IPage<ShortcutMenuEntity> page =
                this.page(
                        new Query<ShortcutMenuEntity>().getPage(pageRequest),
                        new QueryWrapper<ShortcutMenuEntity>());

        return BusinessPageResult.build(page, pageRequest);
    }

    /**
     * 保存用户个性化设置
     *
     * @param request 个性化设置参数
     * @return 个性化设置参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<ShortcutMenuEntityRequest> saveShortCutMenuSetting(
            ShortcutMenuEntityRequest request) {
        List<String> resourceIds = request.getResourceIds();
        if (CollectionUtils.isNotEmpty(resourceIds)) {
            // 快捷菜单不为空，则批量保存至快捷菜单表
            List<ShortcutMenuEntity> shortcutMenuEntityList = new ArrayList<>();
            resourceIds.forEach(
                    resourceId -> {
                        ShortcutMenuEntity shortcutMenuBatch =
                                BeanCopyUtils.copyProperties(request, ShortcutMenuEntity.class);
                        assignValueForAdd(request, shortcutMenuBatch);
                        shortcutMenuBatch.setResourceId(resourceId);
                        shortcutMenuEntityList.add(shortcutMenuBatch);
                    });
            super.saveBatch(shortcutMenuEntityList);
        } else {
            // 快捷菜单为空,则插入单条记录
            ShortcutMenuEntity shortcutMenuSingle =
                    BeanCopyUtils.copyProperties(request, ShortcutMenuEntity.class);
            assignValueForAdd(request, shortcutMenuSingle);
            super.save(shortcutMenuSingle);
        }
        // 保存用户字号、主题
        updateUserPreference(request);

        // 用户已设置快捷菜单
        if (CollectionUtils.isNotEmpty(resourceIds)) {
            List<ResourcesEntity> resourcesEntityList = resourcesDao.selectBatchIds(resourceIds);
            // 设置用户快捷菜单
            request.setMenus(resourcesEntityList);
        }
        return BusinessResult.success(request);
    }

    /**
     * 更改用户个性化设置
     *
     * @param request 个性化设置参数
     * @return 个性化设置参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<ShortcutMenuEntityRequest> updateShortCutMenuSetting(
            ShortcutMenuEntityRequest request) {
        // 删除用户原有个性化设置
        shortcutMenuMapper.delete(
                new QueryWrapper<ShortcutMenuEntity>().eq("USER_ID", request.getUserId()));
        List<String> resourceIds = request.getResourceIds();
        if (CollectionUtils.isNotEmpty(resourceIds)) {
            // 快捷菜单不为空，则批量保存至快捷菜单表
            List<ShortcutMenuEntity> shortcutMenuEntityList = new ArrayList<>();
            resourceIds.forEach(
                    resourceId -> {
                        ShortcutMenuEntity shortcutMenu =
                                BeanCopyUtils.copyProperties(request, ShortcutMenuEntity.class);
                        assignmentValueForUpdate(request, shortcutMenu);
                        shortcutMenu.setResourceId(resourceId);
                        shortcutMenu.setId(null);
                        shortcutMenuEntityList.add(shortcutMenu);
                    });
            super.saveBatch(shortcutMenuEntityList);
        } else {
            // 用户菜单为空，则保存单条记录
            ShortcutMenuEntity shortcutMenu =
                    BeanCopyUtils.copyProperties(request, ShortcutMenuEntity.class);
            assignmentValueForUpdate(request, shortcutMenu);
            super.save(shortcutMenu);
        }
        // 保存用户字号、主题
        updateUserPreference(request);

        // 用户已设置快捷菜单
        if (CollectionUtils.isNotEmpty(resourceIds)) {
            List<ResourcesEntity> resourcesEntityList = resourcesDao.selectBatchIds(resourceIds);
            // 设置用户快捷菜单
            request.setMenus(resourcesEntityList);
        }
        return BusinessResult.success(request);
    }

    /**
     * 获取用户快捷设置
     *
     * @param userId 用户id
     * @return 个性化配置信息
     */
    @Override
    public ShortCutMenuResult getShortCutMenuInfo(String userId) throws IOException {
        // 用户首次查看个性化配置返回信息为空
        ShortCutMenuResult shortCutMenuResult = null;
        if (StringUtils.isNotBlank(userId)) {
            List<ShortcutMenuEntity> entityList =
                    shortcutMenuMapper.selectList(
                            new QueryWrapper<ShortcutMenuEntity>().eq("USER_ID", userId));
            if (CollectionUtils.isNotEmpty(entityList)) {
                // 获取用户个性化布局设置
                UserEntity userEntity = userDao.selectById(userId);
                shortCutMenuResult =
                        BeanCopyUtils.copyProperties(entityList.get(0), ShortCutMenuResult.class);
                // 设置用户布局
                shortCutMenuResult.setCssId(userEntity.getCssId());
                shortCutMenuResult.setFontSize(userEntity.getFontSize());
                shortCutMenuResult.setLayoutId(userEntity.getLayout());
                // 获取用户所有快捷菜单id
                List<String> resourceIds =
                        entityList.stream().map(ShortcutMenuEntity::getResourceId).collect(Collectors.toList());
                // 用户已设置快捷菜单
                if (CollectionUtils.isNotEmpty(resourceIds)) {
                    List<ResourcesEntity> resourcesEntityList = resourcesDao.selectBatchIds(resourceIds);
                    // 设置用户快捷菜单
                    shortCutMenuResult.setMenus(resourcesEntityList);
                } /*else {
          // 用户快捷菜单为空，展示默认菜单
          UserAuthParams userAuthParams = new UserAuthParams();
          userAuthParams.setUserId(userId);
          BusinessResult<AuthResult> serviceAuth = allInterfacesService.getAuth(userAuthParams);
          AuthResult authData = serviceAuth.getData();
          // 设置用户菜单
          shortCutMenuResult.setMenus(authData.getMenus());
        }*/
            }
        }
        return shortCutMenuResult;
    }

    @Override
    public BusinessResult<Boolean> enableCustomMenu(EnableCustomMenuParam param) {
        UserEntity userEntity = userDao.selectById(param.getUserId());
        userEntity.setEnableCustomMenu(param.getEnableCustomMenu());
        userDao.updateById(userEntity);
        return BusinessResult.success(true);
    }

    /**
     * 更新用户个性化设置
     *
     * @param request 个性化请求参数
     */
    private void updateUserPreference(ShortcutMenuEntityRequest request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(request.getUserId());
        userEntity.setFontSize(request.getFontSize());
        userEntity.setCssId(request.getCssId());
        userEntity.setLayout(request.getLayout());
        // 保存用户个性化菜单设置
        userMapper.updateById(userEntity);
    }

    private void assignValueForAdd(
            ShortcutMenuEntityRequest request, ShortcutMenuEntity shortcutMenu) {
        if (StringUtils.isBlank(shortcutMenu.getId())) {
            shortcutMenu.setId(sequenceService.nextValueString());
        }
        shortcutMenu.setCreateUserId(request.getUserId());
        shortcutMenu.setCreateTime(System.currentTimeMillis());
    }

    private void assignmentValueForUpdate(
            ShortcutMenuEntityRequest request, ShortcutMenuEntity shortcutMenu) {
        if (StringUtils.isBlank(shortcutMenu.getId())) {
            shortcutMenu.setId(sequenceService.nextValueString());
        }
        shortcutMenu.setLastUpdateTime(System.currentTimeMillis());
        shortcutMenu.setLastUpdateUserId(request.getUserId());
    }
}
