package com.yueyue.readhub.model;

import java.util.List;

/**
 * author : yueyue on 2018/4/4 18:38
 * desc   :
 */
public class ApiData {
    private List<Topic> data;
    private int pageSize;
    private int totalItems;
    private int totalPages;

    public List<Topic> getData() {
        return data;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public String getNextPageUrl() {
        return "?lastCursor=" + data.get(data.size() - 1).getOrder() + "&pageSize=10";
    }
}
