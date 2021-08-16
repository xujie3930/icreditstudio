package com.jinninghui.datasphere.icreditstudio.system.modules.system.org.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.param.OrgTreeQueryParams;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result.SelectInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.allinterface.result.SelectTreeInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.entity.OrganizationEntityExpert;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.service.param.OrganizationEntityQueryParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.request.OrgChildrenQueryRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.result.OrganizationInfoResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 *
 * @author hzh
 */
@Mapper
public interface OrganizationDao extends BaseMapper<OrganizationEntity> {

    List<OrganizationInfoResult> getOrgInfoByUserId(OrganizationEntityQueryParam request);

    List<SelectTreeInfoResult> getAllOrgTreeInfo(OrgTreeQueryParams params);


    List<SelectInfoResult> getAllOrgInfo();


    List<OrganizationEntityExpert> queryOrgInfoByParams(OrganizationEntity organization);


    List<OrganizationEntity> getChildrenOrgInfoByParams(OrgChildrenQueryRequest request);

}
