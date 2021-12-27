package org.apache.dolphinscheduler.api.result;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleLogPageResult<T> implements java.io.Serializable {
    private static final long serialVersionUID = -362886586790221218L;

    //目标表
    private String targetTable;
    //源表
    private String sourceTables;
    //任务调度类型
    private String scheduleTypeStr;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 总页数
     */
    private long pageCount;
    /**
     * 当前页号
     */
    private long pageNum;
    /**
     * 页面大小
     */
    private long pageSize;

    /**
     * 数据列表
     */
    private List<T> list;

    private ScheduleLogPageResult(){}

    public static <T> ScheduleLogPageResult<T> build(List<T> list, BusinessBasePageForm pageQueryRequest, long total) {

        int pageSize = pageQueryRequest.getPageSize();
        int pageNum = pageQueryRequest.getPageNum();
        if (total < 0 || pageSize <= 0 || pageNum < 0) {
            throw new IllegalArgumentException("total must more than 0");
        }

        ScheduleLogPageResult<T> result = new ScheduleLogPageResult<>();

        result.setList(list);
        result.setTotal(total);

        result.setPageSize(pageSize);
        result.setPageNum(pageNum);

        if (total == 0) {
            result.setPageCount(0);
        } else {
            if (total % pageSize > 0) {
                result.setPageCount(total / pageSize + 1);
            } else {
                result.setPageCount(total / pageSize);
            }
        }

        return result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

}
