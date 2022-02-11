package com.micro.cloud.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 通用分页数据封装类
 *
 * @author EDZ
 */
public class CommonPage<T> {

  @ApiModelProperty(value = "页码", required = true, example = "1")
  private Integer pageNum;

  @ApiModelProperty(value = "分页记录数", required = true, example = "5")
  private Integer pageSize;

  @ApiModelProperty(value = "总页数", required = true, example = "100")
  private Integer totalPage;

  @ApiModelProperty(value = "总记录数", required = true, example = "500")
  private Long total;

  @ApiModelProperty(value = "返回数据", required = true)
  private List<T> list;

  /**
   * pageHelper分页数据转换
   *
   * @param list 分页数据
   * @param <T>
   * @return 转换后分页数据
   */
  public static <T> CommonPage<T> restPage(List<T> list) {
    CommonPage<T> result = new CommonPage<>();
    PageInfo<T> pageInfo = new PageInfo<>(list);
    result.setTotalPage(pageInfo.getPages());
    result.setPageNum(pageInfo.getPageNum());
    result.setPageSize(pageInfo.getPageSize());
    result.setTotal(pageInfo.getTotal());
    result.setList(pageInfo.getList());
    return result;
  }

  public static <T> CommonPage<T> restPage(IPage<T> pageInfo, PageParam param) {
    CommonPage<T> result = new CommonPage<>();
    result.setTotalPage((int) pageInfo.getPages());
    result.setPageNum(param.getPageNo());
    result.setPageSize(param.getPageSize());
    result.setTotal(pageInfo.getTotal());
    result.setList(pageInfo.getRecords());
    return result;
  }

  public static <T> CommonPage<T> restPage(PageParam pageParam, long total, List<T> data) {
    CommonPage<T> result = new CommonPage<>();
    result.setTotal(total);
    result.setPageNum(pageParam.getPageNo());
    result.setPageSize(pageParam.getPageSize());
    result.setList(data);
    result.setTotalPage(initPages(pageParam.getPageSize(), total));
    return result;
  }

  private static int initPages(int pageSize, long total) {
    if (total % pageSize == 0) {
      return (int) (total / pageSize);
    } else {
      return (int) (total / pageSize) + 1;
    }
  }

  public Integer getPageNum() {
    return pageNum;
  }

  public void setPageNum(Integer pageNum) {
    this.pageNum = pageNum;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(Integer totalPage) {
    this.totalPage = totalPage;
  }

  public Long getTotal() {
    return total;
  }

  public void setTotal(Long total) {
    this.total = total;
  }

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  @Override
  public String toString() {
    return "CommonPage{"
        + "pageNum="
        + pageNum
        + ", pageSize="
        + pageSize
        + ", totalPage="
        + totalPage
        + ", total="
        + total
        + ", list="
        + list
        + '}';
  }
}
