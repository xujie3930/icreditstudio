package com.micro.cloud.api;

import java.util.Collection;
import java.util.List;

public class PageUtils<T> {

    private int pageNum;
    private int pageSize;
    private int total;
    private int totalPage;
    private List<T> list;
    public PageUtils()  {
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public static <T> PageUtils<T> restPage(List<T> list, int pageNum, int pageSize) throws Exception {
        PageUtils<T> pageUtil=new PageUtils<T>();
        if (pageNum<1||pageSize<1||(pageNum-1)*pageSize>=list.size()){
            throw new Exception("分页错误");
        }

        if (list instanceof Collection) {
            pageUtil.setPageNum(pageNum) ;
            pageUtil.setPageSize(pageSize) ;
            //        this.pages = this.pageSize > 0 ? 1 : 0;
            pageUtil.setTotal(list.size()) ;
            int totalPage=list.size()%pageUtil.pageSize==0?list.size()/pageUtil.pageSize:list.size()/pageUtil.pageSize+1;
            pageUtil.setTotalPage(totalPage);
            pageUtil.setList(list.subList((pageNum-1)*pageSize,pageNum*pageSize>=list.size()? list.size() : pageNum*pageSize));
        }

        return pageUtil;
    }
}
