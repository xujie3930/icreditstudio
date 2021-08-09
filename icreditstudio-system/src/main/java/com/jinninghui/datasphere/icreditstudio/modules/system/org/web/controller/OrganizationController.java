package com.jinninghui.datasphere.icreditstudio.modules.system.org.web.controller;

import com.hashtech.businessframework.log.Logable;
import com.hashtech.businessframework.result.BaseController;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.common.log.Log;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.entity.OrganizationEntityExpert;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.service.OrganizationService;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.service.param.OrgEntityDelParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.service.param.OrganizationEntityQueryParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.service.param.OrganizationEntitySaveParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.service.param.OrganizationEntityStatusParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.web.request.*;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.web.result.OrgQueryChildrenResult;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.web.result.OrganizationEntityResult;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.web.result.OrganizationInfoResult;
import com.jinninghui.datasphere.icreditstudio.modules.system.user.service.UserOrgMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @author hzh
 */
@RestController
@RequestMapping("org/organization")
@RequiredArgsConstructor
public class OrganizationController extends BaseController<OrganizationEntity, OrganizationService> {

    private final OrganizationService organizationService;
    private final UserOrgMapService userOrgMapService;

    /**
     * 分页查询列表
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @PostMapping("/pageList")
    public BusinessResult<BusinessPageResult> pageList(@RequestBody OrganizationEntityPageRequest request) {

        BusinessPageResult page = organizationService.queryPage(request);

        return BusinessResult.success(page);
    }

    /**
     * 查询列表
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT,extend = "部门")
    @PostMapping("/queryList")
    @Logable
    public BusinessResult<List<OrganizationEntityResult>> queryList(@RequestBody OrganizationEntityQueryRequest request,
                                                                    @RequestHeader("x-userid") String userId) {

        OrganizationEntityQueryParam params = new OrganizationEntityQueryParam();
        BeanCopyUtils.copyProperties(request, params);
        params.setAccessUserId(userId);
        return organizationService.listQuery(params);
    }


    /**
     * 信息
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @GetMapping("/info/{id}")
    public BusinessResult<OrganizationEntity> info(@PathVariable("id") String id) {

        OrganizationEntity organization = organizationService.getById(id);

        return BusinessResult.success(organization);
    }

    /**
     * 保存
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.ADD, extend = "部门")
    @PostMapping("/save")
    public BusinessResult<Boolean> save(@RequestBody OrganizationEntitySaveRequest request,
                                        @RequestHeader("x-userid") String accessUserId) {
        OrganizationEntitySaveParam param = new OrganizationEntitySaveParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setAccessUserId(accessUserId);
        return organizationService.save(param);
    }

    /**
     * 修改
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.UPDATE, extend = "部门")
    @PostMapping("/update")
    public BusinessResult<OrganizationEntityResult> update(@RequestBody OrganizationEntitySaveRequest request,
                                                           @RequestHeader("x-userid") String accessUserId) {
        OrganizationEntitySaveParam param = new OrganizationEntitySaveParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setAccessUserId(accessUserId);
        return organizationService.edit(param);
    }

    /**
     * 删除
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.DEL, extend = "部门")
    @PostMapping("/delete")
    public BusinessResult<List<OrganizationEntityResult>> delete(@RequestBody OrgEntityDelRequest request,
                                                                 @RequestHeader("x-userid") String accessUserId) {
        OrgEntityDelParam param = new OrgEntityDelParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setAccessUserId(accessUserId);
        return organizationService.delete(param);
    }

    /**
     * 导出excel
     */
    @GetMapping(value = "/exportExcel")
    public BusinessResult<?> exportExcel(HttpServletRequest request,
                                         HttpServletResponse response,
                                         OrganizationEntity organization,
                                         @RequestHeader("x-userid") String userId) {

        return organizationService.exportExcel(request, response, organization, userId);

    }

    /**
     * 通过excel导入数据
     */
    @PostMapping(value = "/importExcel")
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response) {

        return organizationService.importExcel(request, response, OrganizationEntityExpert.class);
    }

    /**
     * 根据userId查询组织机构信息
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @PostMapping("/getOrgInfoByUserId")
    public BusinessResult<List<OrganizationInfoResult>> getOrgInfoByUserId(@RequestBody OrganizationEntityQueryParam request) {

        List<OrganizationInfoResult> organizationEntities = organizationService.getOrgInfoByUserId(request);

        return BusinessResult.success(organizationEntities);
    }

    /**
     * 启/禁用
     */
    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.STATUS, extend = "部门")
    @PostMapping("/status")
    public BusinessResult<Boolean> status(@RequestBody OrganizationEntityStatusRequest request,
                                          @RequestHeader("x-userid") String accessUserId) {
        OrganizationEntityStatusParam param = new OrganizationEntityStatusParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setAccessUserId(accessUserId);
        return organizationService.status(param);
    }


    /**
     * 根据条件获取的部门信息
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @RequestMapping(value = {"/getOrgInfoByParams"}, method = {RequestMethod.POST})
    public BusinessResult<List<OrganizationEntity>> getOrgInfoByParams(OrgQueryRequest request) {

        List<OrganizationEntity> selectInfoResult = organizationService.getOrgInfoByParams(request);

        return BusinessResult.success(selectInfoResult);
    }

    /**
     * 根据当前部门编码或主键id获取下一级部门列表
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @RequestMapping(value = {"/getChildrenOrgInfoByParams"}, method = {RequestMethod.POST})
    public BusinessResult<List<OrganizationEntity>> getChildrenOrgInfoByParams(OrgChildrenQueryRequest request) {

        List<OrganizationEntity> selectInfoResult = organizationService.getChildrenOrgInfoByParams(request);

        return BusinessResult.success(selectInfoResult);
    }


    /**
     * 根据当前部门编码或主键id获取所有子部门列表
     */
//    @Log(type = Log.Type.AUDIT, operateType = Log.OperateType.SELECT)
    @RequestMapping(value = {"/getOrgTreeInfoByParams"}, method = {RequestMethod.POST})
    public BusinessResult<List<OrgQueryChildrenResult>> getOrgTreeInfoByParams(OrgChildrenQueryRequest request) {

        List<OrgQueryChildrenResult> selectInfoResult = organizationService.getOrgTreeInfoByParams(request);

        return BusinessResult.success(selectInfoResult);
    }


}
