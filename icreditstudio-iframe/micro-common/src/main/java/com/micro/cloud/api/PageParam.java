package com.micro.cloud.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel("分页参数")
public class PageParam implements Serializable {

  private static final Integer PAGE_NO = 1;
  private static final Integer PAGE_SIZE = 10;

  @ApiModelProperty(value = "页码，从 1 开始", required = true, example = "1")
  @NotNull(message = "页码不能为空")
  @Min(value = 1, message = "页码最小值为 1")
  private Integer pageNo = PAGE_NO;

  @ApiModelProperty(value = "每页条数，最大值为 100", required = true, example = "10")
  @NotNull(message = "每页条数不能为空")
  @Min(value = 1, message = "页码最小值为 1")
  @Max(value = 100, message = "页码最大值为 100")
  private Integer pageSize = PAGE_SIZE;

  private Integer offSet;

  public Integer getPageNo() {
    return this.pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public Integer getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public int getOffSet() {
    return (pageNo - 1) * pageSize;
  }

  @Override
  public String toString() {
    return "PageParam{"
        + "pageNo="
        + pageNo
        + ", pageSize="
        + pageSize
        + ", offSet="
        + offSet
        + '}';
  }
}
