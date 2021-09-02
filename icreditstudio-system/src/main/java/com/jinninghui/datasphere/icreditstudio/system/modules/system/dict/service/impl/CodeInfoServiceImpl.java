package com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.system.common.enums.DeleteFlagEnum;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.entity.CodeInfoEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.mapper.CodeInfoMapper;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.CodeInfoService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.param.CodeInfoEntityDelParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.param.CodeInfoEntityPageParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.param.CodeInfoEntitySaveParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.param.CodeInfoEntityStatusParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.result.CodeInfoEntityResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.dict.service.result.CodeInfoResult;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.Query;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.validate.BusinessParamsValidate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Service("geCodeInfoService")
public class CodeInfoServiceImpl extends ServiceImpl<CodeInfoMapper, CodeInfoEntity> implements CodeInfoService {

    @Autowired
    private CodeInfoMapper codeInfoMapper;

    @Override
    @BusinessParamsValidate
    public BusinessPageResult queryPage(CodeInfoEntityPageParam param) {
        QueryWrapper<CodeInfoEntity> wrapper = new QueryWrapper();
        if (StringUtils.isNoneBlank(param.getCodeName())) {
            wrapper.like(CodeInfoEntity.CODE_NAME, StringUtils.trim(param.getCodeName()));
        }
        wrapper.orderByAsc(CodeInfoEntity.CODE_SORT);
        wrapper.orderByDesc(CodeInfoEntity.CREATE_TIME);
        IPage<CodeInfoEntity> page = this.page(
                new Query<CodeInfoEntity>().getPage(param),
                wrapper
        );
        return BusinessPageResult.build(page, param);
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> status(CodeInfoEntityStatusParam param) {
        String deleteFlag = param.getDeleteFlag();
        if (DeleteFlagEnum.ALL.getCode().equals(DeleteFlagEnum.find(deleteFlag))) {
            throw new AppException("10000000");
        }
        CodeInfoEntity entity = new CodeInfoEntity();
        entity.setId(param.getId());
        entity.setDeleteFlag(deleteFlag);
        return BusinessResult.success(updateById(entity));
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> deleteByIds(CodeInfoEntityDelParam param) {
        boolean result = true;
        Set<String> ids = param.getIds();
        if (CollectionUtils.isNotEmpty(ids)) {
            QueryWrapper<CodeInfoEntity> wrapper = new QueryWrapper<>();
            wrapper.eq(CodeInfoEntity.DELETE_FLAG, DeleteFlagEnum.N.getCode());
            wrapper.in(CodeInfoEntity.ID, ids);
            List<CodeInfoEntity> openCodes = list(wrapper);
            if (CollectionUtils.isNotEmpty(openCodes)) {
                throw new AppException("50009337");
            }
            result = removeByIds(ids);
        }
        return BusinessResult.success(result);
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<CodeInfoEntityResult> updateEntity(CodeInfoEntitySaveParam param) {
        if (StringUtils.isBlank(param.getId())) {
            throw new AppException("10000001");
        }
        CodeInfoEntity entity = new CodeInfoEntity();
        BeanCopyUtils.copyProperties(param, entity);
        updateById(entity);

        CodeInfoEntityResult codeInfoEntityResult = BeanCopyUtils.copyProperties(entity, CodeInfoEntityResult.class);
        return BusinessResult.success(codeInfoEntityResult);
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> addEntity(CodeInfoEntitySaveParam param) {
        boolean result = true;
        CodeInfoEntity entity = BeanCopyUtils.copyProperties(param, CodeInfoEntity.class);
        result = save(entity);
        return BusinessResult.success(result);
    }

    @Override
    public List<CodeInfoResult> getInfoByKey(String key) {
        return codeInfoMapper.getInfoByKey(key);
    }

}
