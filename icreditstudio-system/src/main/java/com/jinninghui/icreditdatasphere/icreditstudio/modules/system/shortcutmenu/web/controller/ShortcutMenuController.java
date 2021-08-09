package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.shortcutmenu.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.shortcutmenu.entity.ShortcutMenuEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.shortcutmenu.service.ShortcutMenuService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.shortcutmenu.service.param.EnableCustomMenuParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.shortcutmenu.web.request.EnableCustomMenuRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.shortcutmenu.web.request.ShortcutMenuEntityDelRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.shortcutmenu.web.request.ShortcutMenuEntityPageRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.shortcutmenu.web.request.ShortcutMenuEntityRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.shortcutmenu.web.result.ShortCutMenuResult;
import com.hashtech.businessframework.log.Logable;
import com.hashtech.businessframework.result.BaseController;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 快捷菜单
 *
 * @author 1
 */
@RestController
@RequestMapping("system/shortcutmenu")
public class ShortcutMenuController
        extends BaseController<ShortcutMenuEntity, ShortcutMenuService> {

    @Autowired
    private ShortcutMenuService shortcutMenuService;

    /**
     * 分页查询列表
     */
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", httpMethod = "POST")
    @PostMapping("/pageList")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<BusinessPageResult> pageList(
            @ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody
                    ShortcutMenuEntityPageRequest pageRequest) {

        BusinessPageResult page = shortcutMenuService.queryPage(pageRequest);

        return BusinessResult.success(page);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "信息", notes = "信息", httpMethod = "GET")
    @GetMapping("/info")
    @Logable
    public BusinessResult<ShortCutMenuResult> info(@RequestHeader(value = "x-userid") String userId)
            throws IOException {
        ShortCutMenuResult shortCutMenuInfo = shortcutMenuService.getShortCutMenuInfo(userId);
        return BusinessResult.success(shortCutMenuInfo);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping("/save")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<ShortcutMenuEntityRequest> save(
            @ApiParam(name = "保存对象", value = "传入json格式", required = true) @RequestBody
                    ShortcutMenuEntityRequest request) {

        return shortcutMenuService.saveShortCutMenuSetting(request);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @PostMapping("/update")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<ShortcutMenuEntityRequest> update(
            @ApiParam(name = "修改对象", value = "传入json格式", required = true) @RequestBody
                    ShortcutMenuEntityRequest request) {

        return shortcutMenuService.updateShortCutMenuSetting(request);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping("/delete")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<Boolean> delete(
            @ApiParam(name = "删除ID", value = "传入json格式", required = true) @RequestBody
                    ShortcutMenuEntityDelRequest params) {

        shortcutMenuService.removeByIds(params.getIds());

        return BusinessResult.success(true);
    }

    /**
     * 导出excel
     */
    @ApiOperation(value = "导出excel", notes = "导出excel", httpMethod = "GET")
    @GetMapping(value = "/exportExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> exportExcel(
            HttpServletRequest request, HttpServletResponse response, ShortcutMenuEntity shortcutMenu) {

        return super.exportExcel(
                request, response, shortcutMenu, ShortcutMenuEntity.class, "shortcutMenu");
    }

    /**
     * 通过excel导入数据
     */
    @ApiOperation(value = "通过excel导入数据", notes = "通过excel导入数据", httpMethod = "POST")
    @PostMapping(value = "/importExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return super.importExcel(request, response, ShortcutMenuEntity.class);
    }

    @PostMapping("/enableCustomMenu")
    public BusinessResult<Boolean> enableCustomMenu(@RequestBody EnableCustomMenuRequest request,
                                                    @RequestHeader("x-userid") String userId) {
        EnableCustomMenuParam param = new EnableCustomMenuParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setUserId(userId);
        return shortcutMenuService.enableCustomMenu(param);
    }
}
