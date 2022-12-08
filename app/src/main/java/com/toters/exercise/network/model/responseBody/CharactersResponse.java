package com.toters.exercise.network.model.responseBody;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CharactersResponse implements Serializable {

    @SerializedName("offset")
    private int offset;

    @SerializedName("limit")
    private int limit;

    @SerializedName("total")
    private int total;

    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private List<CharacterModel> results=new ArrayList<>();

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CharacterModel> getResults() {
        return results;
    }

    public void setResults(List<CharacterModel> results) {
        this.results = results;
    }
}
