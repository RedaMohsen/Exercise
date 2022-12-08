package com.toters.exercise.helper;


import static com.toters.exercise.constants.StatusCodes.STATUS_SUCCESS;
import static com.toters.exercise.constants.StatusCodes.STATUS_VIOLATION;

import android.util.Log;

import com.android.volley.ParseError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.toters.exercise.network.model.responseBody.ResponseBody;
import com.toters.exercise.utils.ErrorHandler;

import java.lang.reflect.Type;



public class JsonParserHelper {
    private final Gson gson;
    private final ErrorHandler errorHandler;

    public JsonParserHelper(ErrorHandler errorHandler) {
        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        this.errorHandler = errorHandler;
    }

    public <T> T parseBaseObject(Object response, Type type) {
        try {
            Log.e("APP", response.toString());
            ResponseBody<T> responseData = gson.fromJson(response.toString(), type);

            switch (responseData.getStatusCode()) {
                case 201:

                case STATUS_SUCCESS:
                    return responseData.getData();
                case STATUS_VIOLATION:
                    errorHandler.parseUnauthorizedError(new VolleyError(responseData.getMessage()));
                default:
                    errorHandler.showUnexpectedError(new VolleyError(responseData.getMessage()));
                    return null;
            }

        } catch (JsonParseException | ClassCastException e) {
            e.printStackTrace();
            errorHandler.showUnexpectedError(new ParseError());
            return null;
        }
    }

    public <T> T parseCustomObject(Object response, Type type) {
        try {
            return gson.fromJson(response.toString(), type);
        } catch (JsonParseException e) {
            e.printStackTrace();
            errorHandler.showUnexpectedError(new ParseError());
            return null;
        }
    }
}
