package com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.controller;

import com.jinninghui.datasphere.icreditstudio.framework.result.BaseController;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.sequence.api.SequenceService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.entity.UserRoleMapEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.service.UserRoleMapService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.request.UserRoleMapEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.user.web.request.UserRoleMapEntityRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


/**
 *
 *
 * @author hzh
 */
@RestController
@RequestMapping("user/userrolemap")
@RequiredArgsConstructor
public class UserRoleMapController extends BaseController<UserRoleMapEntity, UserRoleMapService> {

    private final  UserRoleMapService userRoleMapService;
    private final SequenceService sequenceService;
    /**
     * 分页查询列表
     */
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", httpMethod = "POST")
    @PostMapping("/pageList")
    public BusinessResult<BusinessPageResult> pageList(@ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody UserRoleMapEntityPageRequest pageRequest){

        BusinessPageResult page = userRoleMapService.queryPage(pageRequest);

        return BusinessResult.success(page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "信息", notes = "信息", httpMethod = "GET")
    @GetMapping("/info/{id}")
    public BusinessResult<UserRoleMapEntity> info(@ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") String id){

        UserRoleMapEntity userRoleMap = userRoleMapService.getById(id);

        return BusinessResult.success(userRoleMap);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping("/save")
    public BusinessResult<UserRoleMapEntity> save(@ApiParam(name = "保存对象", value = "传入json格式", required = true) @RequestBody UserRoleMapEntityRequest request){

        UserRoleMapEntity userRoleMap = BeanCopyUtils.copyProperties(request, UserRoleMapEntity.class);

        userRoleMap.setId(String.valueOf(sequenceService.nextValue(null)));
		userRoleMapService.save(userRoleMap);

        return BusinessResult.success(userRoleMap);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @PostMapping("/update")
    public BusinessResult<UserRoleMapEntity> update(@ApiParam(name = "修改对象", value = "传入json格式", required = true) @RequestBody UserRoleMapEntityRequest request){

        UserRoleMapEntity userRoleMap = BeanCopyUtils.copyProperties(request, UserRoleMapEntity.class);

		userRoleMapService.updateById(userRoleMap);

        return BusinessResult.success(userRoleMap);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping("/delete")
    public BusinessResult<Boolean> delete(@ApiParam(name = "删除ID", value = "传入json格式", required = true) @RequestBody String[] ids){

        userRoleMapService.removeByIds(Arrays.asList(ids));

        return BusinessResult.success(true);
    }

    /**
     * 导出excel
     */
    @ApiOperation(value = "导出excel", notes = "导出excel", httpMethod = "GET")
    @GetMapping(value = "/exportExcel")
    public BusinessResult<?> exportExcel(HttpServletRequest request,HttpServletResponse response, UserRoleMapEntity userRoleMap) {

        return super.exportExcel(request,response, userRoleMap, UserRoleMapEntity.class, "userRoleMap");
    }

    /**
     * 通过excel导入数据
     */
    @ApiOperation(value = "通过excel导入数据", notes = "通过excel导入数据", httpMethod = "POST")
    @PostMapping(value = "/importExcel")
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return super.importExcel(request, response, UserRoleMapEntity.class);
    }

}
