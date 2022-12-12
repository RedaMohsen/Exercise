package com.toters.exercise.network.model;

import com.google.gson.annotations.SerializedName;
import com.toters.exercise.network.model.responseBody.CharacterModel;

import java.io.Serializable;

public class StoryModel implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;


    @SerializedName("description")
    private String description;

    @SerializedName("thumbnail")
    private CharacterModel.thumbNail thumbNail;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public CharacterModel.thumbNail getThumbNail() {
        return thumbNail;
    }
}
