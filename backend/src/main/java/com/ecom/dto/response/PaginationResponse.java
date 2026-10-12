package com.ecom.dto.response;

import java.util.List;

public class PaginationResponse<T> {

    private int pageNumber;
    private long totalCount;
    private int pageSize;
    private List<T> data;

    public PaginationResponse() {
    }

    public PaginationResponse(int pageNumber, long totalCount, int pageSize, List<T> data) {
        this.pageNumber = pageNumber;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.data = data;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
