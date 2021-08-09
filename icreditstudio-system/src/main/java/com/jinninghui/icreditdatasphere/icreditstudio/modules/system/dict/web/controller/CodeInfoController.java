package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.dict.web.controller;

import com.jinninghui.icreditdatasphere.icreditstudio.common.log.Log;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.dict.entity.CodeInfoEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.dict.service.CodeInfoService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.dict.service.param.CodeInfoEntityDelParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.dict.service.param.CodeInfoEntityPageParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.dict.service.param.CodeInfoEntitySaveParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.dict.service.param.CodeInfoEntityStatusParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.dict.service.result.CodeInfoEntityResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.dict.service.result.CodeInfoResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.dict.web.request.CodeInfoEntityDelRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.dict.web.request.CodeInfoEntityPageRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.dict.web.request.CodeInfoEntitySaveRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.dict.web.request.CodeInfoEntityStatusRequest;
import com.hashtech.businessframework.log.Logable;
import com.hashtech.businessframework.result.BaseController;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * @author 1
 */
@RestController
@RequestMapping("/code/code")
public class CodeInfoController extends BaseController<CodeInfoEntity, CodeInfoService> {

    @Autowired
    private CodeInfoService codeInfoService;

    /**
     * 分页查询列表
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT,extend = "字典")
    @PostMapping("/pageList")
    @Logable
    public BusinessResult<BusinessPageResult> pageList(@RequestBody CodeInfoEntityPageRequest pageRequest) {

        CodeInfoEntityPageParam param = new CodeInfoEntityPageParam();
        BeanCopyUtils.copyProperties(pageRequest, param);
        BusinessPageResult page = codeInfoService.queryPage(param);

        return BusinessResult.success(page);
    }


    /**
     * 信息
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @GetMapping("/info/{id}")
    @Logable
    public BusinessResult<CodeInfoEntity> info(@Valid @NotBlank(message = "10000001") @PathVariable("id") String id) {
        CodeInfoEntity geCodeInfo = codeInfoService.getById(id);
        return BusinessResult.success(geCodeInfo);
    }

    /**
     * 保存
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.ADD, extend = "字典")
    @PostMapping("/save")
    @Logable
    public BusinessResult<Boolean> save(@RequestBody CodeInfoEntitySaveRequest request) {
        CodeInfoEntitySaveParam param = new CodeInfoEntitySaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return codeInfoService.addEntity(param);
    }

    /**
     * 修改
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.UPDATE, extend = "字典")
    @PostMapping("/update")
    @Logable
    public BusinessResult<CodeInfoEntityResult> update(@RequestBody CodeInfoEntitySaveRequest request) {

        CodeInfoEntitySaveParam param = new CodeInfoEntitySaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return codeInfoService.updateEntity(param);
    }

    /**
     * 删除
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.DEL, extend = "字典")
    @PostMapping("/delete")
    @Logable
    public BusinessResult<Boolean> delete(@RequestBody CodeInfoEntityDelRequest request) {
        CodeInfoEntityDelParam param = new CodeInfoEntityDelParam();
        BeanCopyUtils.copyProperties(request, param);
        return codeInfoService.deleteByIds(param);
    }

    /**
     * 导出excel
     */
    @GetMapping(value = "/exportExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> exportExcel(HttpServletRequest request, HttpServletResponse response, CodeInfoEntity geCodeInfo) {

        return super.exportExcel(request, response, geCodeInfo, CodeInfoEntity.class, "geCodeInfo");
    }

    /**
     * 通过excel导入数据
     */
    @PostMapping(value = "/importExcel")
    @BusinessParamsValidate
    @Logable
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return super.importExcel(request, response, CodeInfoEntity.class);
    }

    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.STATUS, extend = "字典")
    @PostMapping("/status")
    @Logable
    public BusinessResult<Boolean> status(@RequestBody CodeInfoEntityStatusRequest request) {
        CodeInfoEntityStatusParam param = new CodeInfoEntityStatusParam();
        BeanCopyUtils.copyProperties(request, param);
        return codeInfoService.status(param);
    }

    @GetMapping("/getInfoByKey")
    public BusinessResult<List<CodeInfoResult>> getInfoByKey(@RequestParam("key") String key){
        List<CodeInfoResult> codeInfos = codeInfoService.getInfoByKey(key);
        return BusinessResult.success(codeInfos);
    }

}
