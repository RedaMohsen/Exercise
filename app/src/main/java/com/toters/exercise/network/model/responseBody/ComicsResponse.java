package com.toters.exercise.network.model.responseBody;

import com.google.gson.annotations.SerializedName;
import com.toters.exercise.network.model.ComicModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComicsResponse implements Serializable {

    @SerializedName("offset")
    private int offset;

    @SerializedName("limit")
    private int limit;

    @SerializedName("total")
    private int total;

    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private List<ComicModel> results = new ArrayList<>();


    public List<ComicModel> getResults() {
        return results;
    }

}
