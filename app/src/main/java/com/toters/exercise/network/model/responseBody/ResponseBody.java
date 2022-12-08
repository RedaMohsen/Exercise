package com.toters.exercise.network.model.responseBody;

import com.google.gson.annotations.SerializedName;

public class ResponseBody<T> {

    @SerializedName("data")
    private T data;

    @SerializedName("code")
    private int statusCode;

    @SerializedName("message")
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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
