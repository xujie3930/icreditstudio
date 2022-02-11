package com.micro.cloud.modules.system.dict.service.impl;

import com.google.common.annotations.VisibleForTesting;
import com.micro.cloud.constant.SysErrorCodeConstants;
import com.micro.cloud.enums.SysCommonStatusEnum;
import com.micro.cloud.exception.ApiException;
import com.micro.cloud.modules.system.dict.convert.SysDictDataConvert;
import com.micro.cloud.modules.system.dict.dataobject.SysDictData;
import com.micro.cloud.modules.system.dict.dataobject.SysDictType;
import com.micro.cloud.modules.system.dict.mapper.SysDictDataMapper;
import com.micro.cloud.modules.system.dict.service.SysDictDataService;
import com.micro.cloud.modules.system.dict.service.SysDictTypeService;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataCreateReqVO;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataExportReqVO;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataPageReqVO;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataRespVO;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataUpdateReqVO;
import com.micro.cloud.snowflake.sequence.SequenceService;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 字典数据 Service 实现类
 *
 * @author ruoyi
 */
@Service
public class SysDictDataServiceImpl implements SysDictDataService {

  private final Logger logger = LoggerFactory.getLogger(SysDictDataServiceImpl.class);

  /** 排序 dictType > sort */
  private static final Comparator<SysDictData> COMPARATOR_TYPE_AND_SORT =
      Comparator.comparing(SysDictData::getDictType).thenComparingInt(SysDictData::getSort);

  @Autowired private SequenceService sequenceService;

  @Autowired private SysDictTypeService dictTypeService;

  @Autowired private SysDictDataMapper dictDataMapper;

  /**
   * 如果字典数据发生变化，从数据库中获取最新的全量字典数据。 如果未发生变化，则返回空
   *
   * @param maxUpdateTime 当前字典数据的最大更新时间
   * @return 字典数据列表
   */
  private List<SysDictData> loadDictDataIfUpdate(Date maxUpdateTime) {
    // 第一步，判断是否要更新。
    if (maxUpdateTime == null) { // 如果更新时间为空，说明 DB 一定有新数据
      logger.info("[loadDictDataIfUpdate][首次加载全量字典数据]");
    } else { // 判断数据库中是否有更新的字典数据
      if (!dictDataMapper.selectExistsByUpdateTimeAfter(maxUpdateTime)) {
        return null;
      }
      logger.info("[loadDictDataIfUpdate][增量加载全量字典数据]");
    }
    // 第二步，如果有更新，则从数据库加载所有字典数据
    return dictDataMapper.selectList();
  }

  @Override
  public List<SysDictData> getDictDatas() {
    List<SysDictData> list = dictDataMapper.selectList();
    list.sort(COMPARATOR_TYPE_AND_SORT);
    return list;
  }

  @Override
  public List<SysDictDataRespVO> getDictDataPage(SysDictDataPageReqVO reqVO) {
    List<SysDictData> result = dictDataMapper.selectPage(reqVO);
    return SysDictDataConvert.INSTANCE.convert(result);
  }

  @Override
  public List<SysDictData> getDictDatas(SysDictDataExportReqVO reqVO) {
    List<SysDictData> list = dictDataMapper.selectList(reqVO);
    list.sort(COMPARATOR_TYPE_AND_SORT);
    return list;
  }

  @Override
  public SysDictData getDictData(String id) {
    return dictDataMapper.selectById(id);
  }

  @Override
  public String createDictData(SysDictDataCreateReqVO reqVO) {
    // 校验正确性
    this.checkCreateOrUpdate(null, reqVO.getValue(), reqVO.getDictType());
    // 插入字典类型
    SysDictData dictData = SysDictDataConvert.INSTANCE.convert(reqVO);
    dictData.setSysDictDataId(sequenceService.nextStringValue(null));
    dictDataMapper.insert(dictData);
    return dictData.getSysDictDataId();
  }

  @Override
  public Boolean updateDictData(SysDictDataUpdateReqVO reqVO) {
    // 校验正确性
    this.checkCreateOrUpdate(reqVO.getId(), reqVO.getValue(), reqVO.getDictType());
    // 更新字典类型
    SysDictData updateObj = SysDictDataConvert.INSTANCE.convert(reqVO);
    dictDataMapper.updateById(updateObj);
    return true;
  }

  @Override
  public Boolean deleteDictData(String id) {
    // 校验是否存在
    this.checkDictDataExists(id);
    // 删除字典数据
    dictDataMapper.deleteById(id);
    return true;
  }

  @Override
  public int countByDictType(String dictType) {
    return dictDataMapper.selectCountByDictType(dictType);
  }

  private void checkCreateOrUpdate(String id, String value, String dictType) {
    // 校验自己存在
    checkDictDataExists(id);
    // 校验字典类型有效
    checkDictTypeValid(dictType);
    // 校验字典数据的值的唯一性
    checkDictDataValueUnique(id, dictType, value);
  }

  @VisibleForTesting
  public void checkDictDataValueUnique(String id, String dictType, String value) {
    SysDictData dictData = dictDataMapper.selectByDictTypeAndValue(dictType, value);
    if (dictData == null) {
      return;
    }
    // 如果 id 为空，说明不用比较是否为相同 id 的字典数据
    if (id == null) {
      throw new ApiException(SysErrorCodeConstants.DICT_TYPE_NAME_DUPLICATE);
    }
    if (!dictData.getSysDictDataId().equals(id)) {
      throw new ApiException(SysErrorCodeConstants.DICT_TYPE_NAME_DUPLICATE);
    }
  }

  @VisibleForTesting
  public void checkDictDataExists(String id) {
    if (id == null) {
      return;
    }
    SysDictData dictData = dictDataMapper.selectById(id);
    if (dictData == null) {
      throw new ApiException(SysErrorCodeConstants.DICT_TYPE_NOT_EXISTS);
    }
  }

  @VisibleForTesting
  public void checkDictTypeValid(String type) {
    SysDictType dictType = dictTypeService.getDictTypeByType(type);
    if (dictType == null) {
      throw new ApiException(SysErrorCodeConstants.DICT_TYPE_NOT_EXISTS);
    }
    if (!SysCommonStatusEnum.ENABLE.getStatus().equals(dictType.getStatus())) {
      throw new ApiException(SysErrorCodeConstants.DICT_TYPE_NOT_ENABLE);
    }
  }
}
