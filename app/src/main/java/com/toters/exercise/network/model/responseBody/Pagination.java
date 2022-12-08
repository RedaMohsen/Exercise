package com.toters.exercise.network.model.responseBody;

import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("offset")
    private int offset;

    @SerializedName("limit")
    private long maxCount;

    public Pagination() {
    }

    public Pagination(int offset) {
        this.offset = offset;
    }

    public Pagination(int offset, int pageCount) {
        this.offset = offset;
        this.maxCount = pageCount;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public long getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}

