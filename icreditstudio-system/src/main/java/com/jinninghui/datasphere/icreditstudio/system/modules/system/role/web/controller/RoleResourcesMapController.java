package com.jinninghui.datasphere.icreditstudio.system.modules.system.role.web.controller;

import com.jinninghui.datasphere.icreditstudio.system.common.log.Log;
import com.jinninghui.datasphere.icreditstudio.framework.result.BaseController;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.sequence.api.SequenceService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.entity.RoleResourcesMapEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.service.RoleResourcesMapService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.web.request.RoleResourcesMapEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.role.web.request.RoleResourcesMapEntityRequest;
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
@RequestMapping("role/roleresourcesmap")
@RequiredArgsConstructor
public class RoleResourcesMapController extends BaseController<RoleResourcesMapEntity, RoleResourcesMapService> {

    private final  RoleResourcesMapService roleResourcesMapService;
    private final SequenceService sequenceService;
    /**
     * 分页查询列表
     */
    @Log(type = Log.Type.AUDIT,operateType = Log.OperateType.SELECT)
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", httpMethod = "POST")
    @PostMapping("/pageList")
    public BusinessResult<BusinessPageResult> pageList(@ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody RoleResourcesMapEntityPageRequest pageRequest){

        BusinessPageResult page = roleResourcesMapService.queryPage(pageRequest);

        return BusinessResult.success(page);
    }


    /**
     * 信息
     */
    @Log(type = Log.Type.AUDIT,operateType = Log.OperateType.SELECT)
    @ApiOperation(value = "信息", notes = "信息", httpMethod = "GET")
    @GetMapping("/info/{id}")
    public BusinessResult<RoleResourcesMapEntity> info(@ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") String id){

        RoleResourcesMapEntity roleResourcesMap = roleResourcesMapService.getById(id);

        return BusinessResult.success(roleResourcesMap);
    }

    /**
     * 保存
     */
    @Log(type = Log.Type.AUDIT,operateType = Log.OperateType.ADD)
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping("/save")
    public BusinessResult<RoleResourcesMapEntity> save(@ApiParam(name = "保存对象", value = "传入json格式", required = true) @RequestBody RoleResourcesMapEntityRequest request){

        RoleResourcesMapEntity roleResourcesMap = BeanCopyUtils.copyProperties(request, RoleResourcesMapEntity.class);

        roleResourcesMap.setId(String.valueOf(sequenceService.nextValue(null)));
		roleResourcesMapService.save(roleResourcesMap);

        return BusinessResult.success(roleResourcesMap);
    }

    /**
     * 修改
     */
    @Log(type = Log.Type.AUDIT,operateType = Log.OperateType.UPDATE)
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @PostMapping("/update")
    public BusinessResult<RoleResourcesMapEntity> update(@ApiParam(name = "修改对象", value = "传入json格式", required = true) @RequestBody RoleResourcesMapEntityRequest request){

        RoleResourcesMapEntity roleResourcesMap = BeanCopyUtils.copyProperties(request, RoleResourcesMapEntity.class);

		roleResourcesMapService.updateById(roleResourcesMap);

        return BusinessResult.success(roleResourcesMap);
    }

    /**
     * 删除
     */
    @Log(type = Log.Type.AUDIT,operateType = Log.OperateType.DEL)
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping("/delete")
    public BusinessResult<Boolean> delete(@ApiParam(name = "删除ID", value = "传入json格式", required = true) @RequestBody String[] ids){

        roleResourcesMapService.removeByIds(Arrays.asList(ids));

        return BusinessResult.success(true);
    }

    /**
     * 导出excel
     */
    @ApiOperation(value = "导出excel", notes = "导出excel", httpMethod = "GET")
    @GetMapping(value = "/exportExcel")
    public BusinessResult<?> exportExcel(HttpServletRequest request,HttpServletResponse response, RoleResourcesMapEntity roleResourcesMap) {

        return super.exportExcel(request,response, roleResourcesMap, RoleResourcesMapEntity.class, "roleResourcesMap");
    }

    /**
     * 通过excel导入数据
     */
    @ApiOperation(value = "通过excel导入数据", notes = "通过excel导入数据", httpMethod = "POST")
    @PostMapping(value = "/importExcel")
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return super.importExcel(request, response, RoleResourcesMapEntity.class);
    }

}
