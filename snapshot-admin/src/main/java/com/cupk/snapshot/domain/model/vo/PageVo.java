package com.cupk.snapshot.domain.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装类
 * Created by Guo Tianyou on 2023/6/17.
 */
public class PageVo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<T> list;
    private Long current;
    private Long size;
    private Long total;
    private Long pages;
    private Boolean hasPrevious;
    private Boolean hasNext;

    public PageVo() {
    }

    public PageVo(List<T> list, Page page) {
        this.list = list;
        this.current = page.getCurrent();
        this.size = page.getSize();
        this.total = page.getTotal();
        this.pages = page.getPages();
        this.hasPrevious = page.hasPrevious();
        this.hasNext = page.hasNext();
    }

    @Override
    public String toString() {
        return "PageVo{" +
                "list=" + list +
                ", current=" + current +
                ", size=" + size +
                ", total=" + total +
                ", pages=" + pages +
                ", hasPrevious=" + hasPrevious +
                ", hasNext=" + hasNext +
                '}';
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public Boolean getHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(Boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }
}
