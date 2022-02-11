package com.micro.cloud.modules.system.dict.service.impl;

import com.google.common.annotations.VisibleForTesting;
import com.micro.cloud.constant.SysErrorCodeConstants;
import com.micro.cloud.exception.ApiException;
import com.micro.cloud.modules.system.dict.convert.SysDictTypeConvert;
import com.micro.cloud.modules.system.dict.dataobject.SysDictType;
import com.micro.cloud.modules.system.dict.mapper.SysDictTypeMapper;
import com.micro.cloud.modules.system.dict.service.SysDictDataService;
import com.micro.cloud.modules.system.dict.service.SysDictTypeService;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeCreateReqVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeExportReqVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypePageReqVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeRespVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeSimpleRespVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeUpdateReqVO;
import com.micro.cloud.snowflake.sequence.SequenceService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 字典类型 Service 实现类
 *
 * @author roy
 */
@Service
public class SysDictTypeServiceImpl implements SysDictTypeService {

  @Autowired private SequenceService sequenceService;

  @Resource private SysDictDataService dictDataService;

  @Resource private SysDictTypeMapper dictTypeMapper;

  @Override
  public List<SysDictTypeRespVO> getDictTypePage(SysDictTypePageReqVO reqVO) {
    List<SysDictType> dictTypeList = dictTypeMapper.selectPage(reqVO);
    return SysDictTypeConvert.INSTANCE.convert(dictTypeList);
  }

  @Override
  public List<SysDictType> getDictTypeList(SysDictTypeExportReqVO reqVO) {
    return dictTypeMapper.selectList(reqVO);
  }

  @Override
  public SysDictTypeRespVO getDictType(String id) {
    SysDictType sysDictType = dictTypeMapper.selectById(id);
    return SysDictTypeConvert.INSTANCE.convert(sysDictType);
  }

  @Override
  public SysDictType getDictTypeByType(String type) {
    return dictTypeMapper.selectByType(type);
  }

  @Override
  public String createDictType(SysDictTypeCreateReqVO reqVO) {
    // 校验正确性
    this.checkCreateOrUpdate(null, reqVO.getName(), reqVO.getType());
    // 插入字典类型
    SysDictType dictType = SysDictTypeConvert.INSTANCE.convert(reqVO);
    dictType.setSysDictTypeId(sequenceService.nextStringValue(null));
    dictTypeMapper.insert(dictType);
    return dictType.getSysDictTypeId();
  }

  @Override
  public void updateDictType(SysDictTypeUpdateReqVO reqVO) {
    // 校验正确性
    this.checkCreateOrUpdate(reqVO.getId(), reqVO.getName(), null);
    // 更新字典类型
    SysDictType updateObj = SysDictTypeConvert.INSTANCE.convert(reqVO);
    dictTypeMapper.updateById(updateObj);
  }

  @Override
  public void deleteDictType(String id) {
    // 校验是否存在
    SysDictType dictType = this.checkDictTypeExists(id);
    // 校验是否有字典数据
    if (dictDataService.countByDictType(dictType.getType()) > 0) {
      throw new ApiException(SysErrorCodeConstants.DICT_TYPE_HAS_CHILDREN);
    }
    // 删除字典类型
    dictTypeMapper.deleteById(id);
  }

  @Override
  public List<SysDictTypeSimpleRespVO> getDictTypeList() {
    List<SysDictType> dictTypeList = dictTypeMapper.selectList();
    return SysDictTypeConvert.INSTANCE.convertSimpleList(dictTypeList);
  }

  private void checkCreateOrUpdate(String id, String name, String type) {
    // 校验自己存在
    checkDictTypeExists(id);
    // 校验字典类型的名字的唯一性
    checkDictTypeNameUnique(id, name);
    // 校验字典类型的类型的唯一性
    checkDictTypeUnique(id, type);
  }

  @VisibleForTesting
  public void checkDictTypeNameUnique(String id, String name) {
    SysDictType dictType = dictTypeMapper.selectByName(name);
    if (dictType == null) {
      return;
    }
    // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
    if (id == null) {
      throw new ApiException(SysErrorCodeConstants.DICT_TYPE_NAME_DUPLICATE);
    }
    if (!dictType.getSysDictTypeId().equals(id)) {
      throw new ApiException(SysErrorCodeConstants.DICT_TYPE_NAME_DUPLICATE);
    }
  }

  @VisibleForTesting
  public void checkDictTypeUnique(String id, String type) {
    SysDictType dictType = dictTypeMapper.selectByType(type);
    if (dictType == null) {
      return;
    }
    // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
    if (id == null) {
      throw new ApiException(SysErrorCodeConstants.DICT_TYPE_TYPE_DUPLICATE);
    }
    if (!dictType.getSysDictTypeId().equals(id)) {
      throw new ApiException(SysErrorCodeConstants.DICT_TYPE_TYPE_DUPLICATE);
    }
  }

  @VisibleForTesting
  public SysDictType checkDictTypeExists(String id) {
    if (id == null) {
      return null;
    }
    SysDictType dictType = dictTypeMapper.selectById(id);
    if (dictType == null) {
      throw new ApiException(SysErrorCodeConstants.DICT_TYPE_NOT_EXISTS);
    }
    return dictType;
  }
}
