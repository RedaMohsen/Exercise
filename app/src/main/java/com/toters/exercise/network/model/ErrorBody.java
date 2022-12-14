package com.toters.exercise.network.model;

import com.google.gson.annotations.SerializedName;


public class ErrorBody {


    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("message")
    private String message;

    public ErrorBody( int statusCode, String message) {

        this.statusCode = statusCode;
        this.message = message;
    }



    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
