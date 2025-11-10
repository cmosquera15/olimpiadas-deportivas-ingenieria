package com.ingenieria.olimpiadas.olimpiadas_deportivas.dto.common;

import java.util.List;

public class PageResponse<T> {
    private List<T> content;
    private long total_elements;
    private int total_pages;
    private int page;
    private int size;
    private String sort;

    public PageResponse() {}

    public PageResponse(List<T> content, long totalElements, int totalPages, int page, int size, String sort) {
        this.content = content;
        this.total_elements = totalElements;
        this.total_pages = totalPages;
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public List<T> getContent() { return content; }
    public void setContent(List<T> content) { this.content = content; }
    public long getTotal_elements() { return total_elements; }
    public void setTotal_elements(long total_elements) { this.total_elements = total_elements; }
    public int getTotal_pages() { return total_pages; }
    public void setTotal_pages(int total_pages) { this.total_pages = total_pages; }
    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }
    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
    public String getSort() { return sort; }
    public void setSort(String sort) { this.sort = sort; }
}
