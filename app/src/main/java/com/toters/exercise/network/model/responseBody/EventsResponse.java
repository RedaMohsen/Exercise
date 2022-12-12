package com.toters.exercise.network.model.responseBody;

import com.google.gson.annotations.SerializedName;
import com.toters.exercise.network.model.ComicModel;
import com.toters.exercise.network.model.EventModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventsResponse implements Serializable {

    @SerializedName("offset")
    private int offset;

    @SerializedName("limit")
    private int limit;

    @SerializedName("total")
    private int total;

    @SerializedName("count")
    private int count;

    @SerializedName("results")
    private List<EventModel> results = new ArrayList<>();


    public List<EventModel> getResults() {
        return results;
    }

}
