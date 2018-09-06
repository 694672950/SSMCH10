package cn.bdqn.util;

import java.util.List;

/**
 * 分页显示
 * @param <T>
 */
public class PageBean<T> {
    private int pageIndex;//当前页面
    private int pageSize;//页面大小
    private int totalCount;//总记录数
    private int totalPageCount;//总页数
    private List<T> pageIndexList;//当前页记录

    public PageBean() {
    }

    public PageBean(int pageIndex, int pageSize, int totalCount, int totalPageCount, List<T> pageIndexList) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPageCount = totalPageCount;
        this.pageIndexList = pageIndexList;
    }

    public void setPageIndex(int pageIndex) {
        if (pageIndex<=0){
                this.pageIndex = 1;
            }else if (pageIndex>totalPageCount){
                this.pageIndex = totalPageCount;
            }else {
                this.pageIndex = pageIndex;
        }
    }
    public int getPageIndex() {
        return pageIndex;
    }
    public List<T> getPageIndexList() {
        return pageIndexList;
    }

    public void setPageIndexList(List<T> pageIndexList) {
        this.pageIndexList = pageIndexList;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if(pageSize>0){
            this.pageSize = pageSize;
        }else {
            this.pageSize=5;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        if (totalCount > 0) {
            this.totalCount = totalCount;
            // 计算总页数
            this.totalPageCount=(int) Math.ceil((this.totalCount*1.0)/(pageSize*1.0));
        }
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount=totalPageCount;
    }

}
