package com.toters.exercise.network.model.responseBody;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaginationResponseBody<T> {

    @SerializedName("pages")
    private Pagination pagination;

    @SerializedName("data")
    private List<T> data;

    public Pagination getPagination() {
        return pagination;
    }

    public List<T> getData() {
        return data;
    }
}
