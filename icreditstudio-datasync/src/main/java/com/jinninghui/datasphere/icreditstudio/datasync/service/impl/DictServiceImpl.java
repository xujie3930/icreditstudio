package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.datasync.dto.DictQueryDTO;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.DictEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.DeleteFlagEnum;
import com.jinninghui.datasphere.icreditstudio.datasync.mapper.DictMapper;
import com.jinninghui.datasphere.icreditstudio.datasync.service.DictColumnService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.DictService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DictColumnSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DictQueryParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DictSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictColumnResult;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictQueryResult;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, DictEntity> implements DictService {

    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private DictColumnService dictColumnService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> save(DictSaveParam param) {
        DictEntity dict = new DictEntity();
        BeanCopyUtils.copyProperties(param, dict);
        Date nowDate = new Date();
        dict.setCreateTime(nowDate);
        dict.setDelFlag(0);
        boolean isSaved = saveOrUpdate(dict);
        List<DictColumnSaveParam> saveParams = BeanCopyUtils.copy(param.getDictColumns(), DictColumnSaveParam.class);
        dictColumnService.saveBatch(dict.getId(), saveParams);
        return isSaved ? BusinessResult.success(isSaved) : BusinessResult.fail("", "保存失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> del(String id) {
        dictColumnService.delBatchByDictId(DeleteFlagEnum.DELETED.getCode(), id);
        boolean isRemoved = dictMapper.delById(DeleteFlagEnum.DELETED.getCode(), id);
        return isRemoved ? BusinessResult.success(isRemoved) : BusinessResult.fail("", "删除失败");
    }

    @Override
    public BusinessResult<DictResult> getInfoById(String id) {
        DictResult dict = dictMapper.getInfoById(id);
        dict.setDictColumns(getColumnList(id));
        return BusinessResult.success(dict);
    }

    private List<DictColumnResult> getColumnList(String dictId){
        return dictColumnService.getColumnListByDictId(dictId);
    }

    @Override
    public BusinessResult<List<DictColumnResult>> lookInfo(String dictId) {
        return BusinessResult.success(getColumnList(dictId));
    }

    @Override
    public BusinessResult<BusinessPageResult<DictQueryResult>> pageList(DictQueryParam param) {
        DictQueryDTO dictQueryDTO = new DictQueryDTO();
        BeanCopyUtils.copyProperties(param, dictQueryDTO);
        long dictCount = dictMapper.countDict(dictQueryDTO);
        List<DictQueryResult> dictQuerys = dictMapper.pageList(dictQueryDTO);
        return BusinessResult.success(BusinessPageResult.build(dictQuerys, param, dictCount));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> update(DictSaveParam param) {
        DictEntity dict = new DictEntity();
        BeanCopyUtils.copyProperties(param, dict);
        dictColumnService.truthDelBatchByDictId(param.getId());
        List<DictColumnSaveParam> saveParams = BeanCopyUtils.copy(param.getDictColumns(), DictColumnSaveParam.class);
        dictColumnService.saveBatch(param.getId(), saveParams);
        boolean isUpdated = updateById(dict);
        return isUpdated ? BusinessResult.success(isUpdated) : BusinessResult.fail("", "更新失败");
    }

    @Override
    public BusinessResult<Boolean> importDict(HttpServletRequest request) {

        return null;
    }
}