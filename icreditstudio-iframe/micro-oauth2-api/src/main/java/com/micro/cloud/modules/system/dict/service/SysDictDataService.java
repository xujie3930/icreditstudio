package com.micro.cloud.modules.system.dict.service;

import com.micro.cloud.modules.system.dict.dataobject.SysDictData;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataCreateReqVO;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataExportReqVO;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataPageReqVO;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataRespVO;
import com.micro.cloud.modules.system.dict.vo.data.SysDictDataUpdateReqVO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * 字典数据 Service 接口
 *
 * @author ruoyi
 */
public interface SysDictDataService {

  /**
   * 创建字典数据
   *
   * @param reqVO 字典数据信息
   * @return 字典数据编号
   */
  @Transactional(rollbackFor = Exception.class)
  String createDictData(SysDictDataCreateReqVO reqVO);

  /**
   * 更新字典数据
   *
   * @param reqVO 字典数据信息
   * @return true/false
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean updateDictData(SysDictDataUpdateReqVO reqVO);

  /**
   * 删除字典数据
   *
   * @param id 字典数据编号
   */
  @Transactional(rollbackFor = Exception.class)
  Boolean deleteDictData(String id);

  /**
   * 获得字典数据列表
   *
   * @return 字典数据全列表
   */
  List<SysDictData> getDictDatas();

  /**
   * 获得字典数据分页列表
   *
   * @param reqVO 分页请求
   * @return 字典数据分页列表
   */
  List<SysDictDataRespVO> getDictDataPage(SysDictDataPageReqVO reqVO);

  /**
   * 获得字典数据列表
   *
   * @param reqVO 列表请求
   * @return 字典数据列表
   */
  List<SysDictData> getDictDatas(SysDictDataExportReqVO reqVO);

  /**
   * 获得字典数据详情
   *
   * @param id 字典数据编号
   * @return 字典数据
   */
  SysDictData getDictData(String id);

  /**
   * 获得指定字典类型的数据数量
   *
   * @param dictType 字典类型
   * @return 数据数量
   */
  int countByDictType(String dictType);
}
