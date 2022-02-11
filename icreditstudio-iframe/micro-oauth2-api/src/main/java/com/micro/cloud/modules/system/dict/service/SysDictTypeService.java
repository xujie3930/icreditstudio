package com.micro.cloud.modules.system.dict.service;

import com.micro.cloud.modules.system.dict.dataobject.SysDictType;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeCreateReqVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeExportReqVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypePageReqVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeRespVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeSimpleRespVO;
import com.micro.cloud.modules.system.dict.vo.type.SysDictTypeUpdateReqVO;
import java.util.List;

/**
 * 字典类型 Service 接口
 *
 * @author roy
 */
public interface SysDictTypeService {

  /**
   * 创建字典类型
   *
   * @param reqVO 字典类型信息
   * @return 字典类型编号
   */
  String createDictType(SysDictTypeCreateReqVO reqVO);

  /**
   * 更新字典类型
   *
   * @param reqVO 字典类型信息
   */
  void updateDictType(SysDictTypeUpdateReqVO reqVO);

  /**
   * 删除字典类型
   *
   * @param id 字典类型编号
   */
  void deleteDictType(String id);

  /**
   * 获得字典类型分页列表
   *
   * @param reqVO 分页请求
   * @return 字典类型分页列表
   */
  List<SysDictTypeRespVO> getDictTypePage(SysDictTypePageReqVO reqVO);

  /**
   * 获得字典类型列表
   *
   * @param reqVO 列表请求
   * @return 字典类型列表
   */
  List<SysDictType> getDictTypeList(SysDictTypeExportReqVO reqVO);

  /**
   * 获得字典类型详情
   *
   * @param id 字典类型编号
   * @return 字典类型
   */
  SysDictTypeRespVO getDictType(String id);

  /**
   * 获得字典类型详情
   *
   * @param type 字典类型
   * @return 字典类型详情
   */
  SysDictType getDictTypeByType(String type);

  /**
   * 获得全部字典类型列表
   *
   * @return 字典类型列表
   */
  List<SysDictTypeSimpleRespVO> getDictTypeList();
}
