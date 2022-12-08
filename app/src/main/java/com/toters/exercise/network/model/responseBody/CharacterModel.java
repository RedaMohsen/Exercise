package com.toters.exercise.network.model.responseBody;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CharacterModel implements Serializable {

    @SerializedName("id")
    private int id;


    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("thumbnail")
    private thumbNail thumbNail;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public CharacterModel.thumbNail getThumbNail() {
        return thumbNail;
    }

    public class thumbNail implements Serializable{

        @SerializedName("path")
        private String path;

        @SerializedName("extension")
        private String extension;

        public String getPath() {
            return path;
        }

        public String getExtension() {
            return extension;
        }
    }
}
