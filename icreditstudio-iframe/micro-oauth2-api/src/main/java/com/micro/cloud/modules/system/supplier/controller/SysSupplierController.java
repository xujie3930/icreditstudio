package com.micro.cloud.modules.system.supplier.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.micro.cloud.api.CommonPage;
import com.micro.cloud.api.CommonResult;
import com.micro.cloud.modules.system.org.controller.SysOrgController;
import com.micro.cloud.modules.system.org.vo.OrgUpdateStatusReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgCreateReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgPageReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgRespVO;
import com.micro.cloud.modules.system.supplier.service.SysSupplierService;
import com.micro.cloud.modules.system.supplier.vo.SysSupLowerVo;
import com.micro.cloud.modules.system.supplier.vo.SysSupPageReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "供应商管理")
@RestController
@RequestMapping("/sys/supplier")
@Validated
public class SysSupplierController {

    private Logger logger = LoggerFactory.getLogger(SysOrgController.class);

    @Resource
    private SysSupplierService sysSupplierService;

    @ApiOperation("创建部门")
    @PostMapping("/create")
    public CommonResult<String> create(@Valid @RequestBody SysOrgCreateReqVO reqVO) {
        try {
            String sup = sysSupplierService.createSup(reqVO);
            return CommonResult.success(sup);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }

    @ApiOperation("获取部门分页")
    @PostMapping("/page")
    public CommonResult<CommonPage<SysOrgRespVO>> page(
            @RequestBody SysOrgPageReqVO reqVO, @RequestHeader(value = "userInfo") String userInfo) {
        IPage<SysOrgRespVO> page = sysSupplierService.page(reqVO);
        return CommonResult.success(CommonPage.restPage(page, reqVO));
    }

    @ApiOperation("获取下级部门列表分页")
    @PostMapping("/loweList")
    public CommonResult<CommonPage<SysOrgRespVO>> lowerSupList(@Valid @RequestBody SysSupPageReqVo reqVO) {
        try {
            IPage<SysOrgRespVO> page = sysSupplierService.selLowerOrg(reqVO);
            return CommonResult.success(CommonPage.restPage(page, reqVO));
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }

    @ApiOperation("设置下级部门")
    @PostMapping("/setParentId")
    public CommonResult<Boolean> setLowerSup(@Valid @RequestBody SysSupLowerVo vo) {
        try {
            return CommonResult.success(sysSupplierService.setLowerSup(vo)>0);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }

    @ApiOperation("获取所有部门")
    @GetMapping("/allOrg")
    public CommonResult<List<SysOrgRespVO>> getAll() {
        try {
//            IPage<SysOrgRespVO> page = sysSupplierService.getAllSup();
            return CommonResult.success(sysSupplierService.getAllSup());
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }

    @ApiOperation("删除部门")
    @PostMapping("/deleteOrgBatch")
    public CommonResult<Boolean> deleteBatch(@RequestBody List<String> ids) {
        try {
            sysSupplierService.deleteDept(ids);
            return CommonResult.success(true);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }

    @ApiOperation("移除下级部门")
    @GetMapping("/deleteLowOrg")
    public CommonResult<Boolean> deleteLowOrg(@RequestParam String id) {
        try {
            return CommonResult.success(sysSupplierService.deleteLowOrg(id)>0);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }

    @ApiOperation("更新部门状态")
    @PostMapping("/update-status")
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody OrgUpdateStatusReqVO reqVO) {
        try {
            Boolean result = sysSupplierService.changeStatusBatch(reqVO);
            return CommonResult.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }


}
