package com.toters.exercise.network.clients.volley;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorResponseBody {
    @SerializedName("status")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("errors")
    private List<APIError> errors;

    public ErrorResponseBody(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrors(List<APIError> errors) {
        this.errors = errors;
    }

    public List<APIError> getErrors() {
        return errors;
    }

   public static class APIError {
        @SerializedName("field")
        private String field;

        @SerializedName("message")
        private String message;

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}
