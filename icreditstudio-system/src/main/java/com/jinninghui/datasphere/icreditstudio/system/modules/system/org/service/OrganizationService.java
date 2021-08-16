package com.jinninghui.datasphere.icreditstudio.system.modules.system.org.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinninghui.datasphere.icreditstudio.system.common.enums.DeleteFlagEnum;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.service.param.OrgEntityDelParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.service.param.OrganizationEntityQueryParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.service.param.OrganizationEntitySaveParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.service.param.OrganizationEntityStatusParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.result.OrganizationEntityResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.param.OrgTreeQueryParams;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result.SelectInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result.SelectTreeInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.entity.OrganizationEntityExpert;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.result.OrgQueryChildrenResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.result.OrganizationInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.request.OrgChildrenQueryRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.request.OrgQueryRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.request.OrganizationEntityPageRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * @author hzh
 */
public interface OrganizationService extends IService<OrganizationEntity> {

    /**
     * 分页查询
     *
     * @param pageRequest
     * @return
     */
    BusinessPageResult queryPage(OrganizationEntityPageRequest pageRequest);

    List<OrganizationInfoResult> getOrgInfoByUserId(OrganizationEntityQueryParam request);


    List<SelectTreeInfoResult> getAllOrgTreeInfo(OrgTreeQueryParams params);


    List<SelectInfoResult> getAllOrgInfo();


    BusinessResult<?> exportExcel(HttpServletRequest request, HttpServletResponse response, OrganizationEntity organization,String userId);


    BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response, Class<OrganizationEntityExpert> organizationEntityExpertClass);

    /**
     * 根据条件获取的部门信息
     */
    List<OrganizationEntity> getOrgInfoByParams(OrgQueryRequest request);


    List<OrganizationEntity> getChildrenOrgInfoByParams(OrgChildrenQueryRequest request);


    List<OrgQueryChildrenResult> getOrgTreeInfoByParams(OrgChildrenQueryRequest request);

    /**
     * 取得组织信息列表，通过id集合和删除标识
     *
     * @param ids        组织信息id列表
     * @param deleteFlag 删除标识：Y.已删除 N.未删除 ALL.全部
     * @return 组织信息列表
     */
    List<OrganizationEntity> getOrgByIdsAndDelFlag(Set<String> ids, DeleteFlagEnum deleteFlag);

    /**
     * 通过deleteFlag过滤ids，取得不重复的id集合
     *
     * @param ids        org id列表
     * @param deleteFlag 删除标识：Y.已删除 N.未删除 ALL.全部
     * @return
     */
    Set<String> filterIdsByDelFlag(Set<String> ids, DeleteFlagEnum deleteFlag);

    /**
     * 查询部门列表
     *
     * @return
     */
    BusinessResult<List<OrganizationEntityResult>> listQuery(OrganizationEntityQueryParam param);

    BusinessResult<Boolean> save(OrganizationEntitySaveParam param);

    BusinessResult<OrganizationEntityResult> edit(OrganizationEntitySaveParam param);

    BusinessResult<Boolean> status(OrganizationEntityStatusParam param);

    BusinessResult<List<OrganizationEntityResult>> delete(OrgEntityDelParam param);

    List<OrganizationEntity> getOrganizationByUserId(String userId);
}

