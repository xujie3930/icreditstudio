package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.controller;

import com.hashtech.businessframework.result.BaseController;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.sequence.api.SequenceService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.entity.UserOrgMapEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.UserOrgMapService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.request.UserOrgMapEntityPageRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.web.request.UserOrgMapEntityRequest;
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
@RequestMapping("user/userorgmap")
@RequiredArgsConstructor
public class UserOrgMapController extends BaseController<UserOrgMapEntity, UserOrgMapService> {

    private final  UserOrgMapService userOrgMapService;
    private final SequenceService sequenceService;
    /**
     * 分页查询列表
     */
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", httpMethod = "POST")
    @PostMapping("/pageList")
    public BusinessResult<BusinessPageResult> pageList(@ApiParam(name = "查询条件对象", value = "传入json格式", required = true) @RequestBody UserOrgMapEntityPageRequest pageRequest){

        BusinessPageResult page = userOrgMapService.queryPage(pageRequest);

        return BusinessResult.success(page);
    }


    /**
     * 信息
     */
    @ApiOperation(value = "信息", notes = "信息", httpMethod = "GET")
    @GetMapping("/info/{id}")
    public BusinessResult<UserOrgMapEntity> info(@ApiParam(name = "id", value = "主键", required = true) @PathVariable("id") String id){

        UserOrgMapEntity userOrgMap = userOrgMapService.getById(id);

        return BusinessResult.success(userOrgMap);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存", notes = "保存", httpMethod = "POST")
    @PostMapping("/save")
    public BusinessResult<UserOrgMapEntity> save(@ApiParam(name = "保存对象", value = "传入json格式", required = true) @RequestBody UserOrgMapEntityRequest request){

        UserOrgMapEntity userOrgMap = BeanCopyUtils.copyProperties(request, UserOrgMapEntity.class);

        userOrgMap.setId(String.valueOf(sequenceService.nextValue(null)));
		userOrgMapService.save(userOrgMap);

        return BusinessResult.success(userOrgMap);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @PostMapping("/update")
    public BusinessResult<UserOrgMapEntity> update(@ApiParam(name = "修改对象", value = "传入json格式", required = true) @RequestBody UserOrgMapEntityRequest request){

        UserOrgMapEntity userOrgMap = BeanCopyUtils.copyProperties(request, UserOrgMapEntity.class);

		userOrgMapService.updateById(userOrgMap);

        return BusinessResult.success(userOrgMap);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "POST")
    @PostMapping("/delete")
    public BusinessResult<Boolean> delete(@ApiParam(name = "删除ID", value = "传入json格式", required = true) @RequestBody String[] ids){

        userOrgMapService.removeByIds(Arrays.asList(ids));

        return BusinessResult.success(true);
    }

    /**
     * 导出excel
     */
    @ApiOperation(value = "导出excel", notes = "导出excel", httpMethod = "GET")
    @GetMapping(value = "/exportExcel")
    public BusinessResult<?> exportExcel(HttpServletRequest request,HttpServletResponse response, UserOrgMapEntity userOrgMap) {

        return super.exportExcel(request,response, userOrgMap, UserOrgMapEntity.class, "userOrgMap");
    }

    /**
     * 通过excel导入数据
     */
    @ApiOperation(value = "通过excel导入数据", notes = "通过excel导入数据", httpMethod = "POST")
    @PostMapping(value = "/importExcel")
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return super.importExcel(request, response, UserOrgMapEntity.class);
    }

}
