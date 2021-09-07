package com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.web.controller;

import com.jinninghui.datasphere.icreditstudio.framework.log.Logable;
import com.jinninghui.datasphere.icreditstudio.framework.result.BaseController;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.validate.BusinessParamsValidate;
import com.jinninghui.datasphere.icreditstudio.system.common.log.Log;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.entity.CodeInfoEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.CodeInfoService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.param.*;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.result.AssociatedDictInfo;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.result.CodeInfoEntityResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.result.CodeInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.web.request.*;
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
    public BusinessResult<List<CodeInfoResult>> getInfoByKey(@RequestParam("key") String key) {
        List<CodeInfoResult> codeInfos = codeInfoService.getInfoByKey(key);
        return BusinessResult.success(codeInfos);
    }

    /**
     * 关联字典表（数据同步模块宽表）
     *
     * @param request
     * @return
     */
    @PostMapping("/associatedDict")
    public BusinessResult<List<AssociatedDictInfo>> associatedDict(@RequestBody CodeInfoAssociatedDictRequest request) {
        CodeInfoAssociatedDictParam param = new CodeInfoAssociatedDictParam();
        BeanCopyUtils.copyProperties(request, param);
        return codeInfoService.associatedDict(param);
    }
}
