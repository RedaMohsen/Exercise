package com.toters.exercise.network.clients;


import static com.toters.exercise.BuildConfig.PRIV_KEY;
import static com.toters.exercise.BuildConfig.PUB_KEY;

import android.content.Context;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.toters.exercise.helper.FileHelper;
import com.toters.exercise.network.clients.volley.BaseRequest;
import com.toters.exercise.network.model.responseBody.CharactersResponse;
import com.toters.exercise.network.model.responseBody.ComicsResponse;
import com.toters.exercise.network.model.responseBody.EventsResponse;
import com.toters.exercise.network.model.responseBody.ResponseBody;
import com.toters.exercise.network.model.responseBody.SeriesResponse;
import com.toters.exercise.network.model.responseBody.StoriesResponse;
import com.toters.exercise.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;

public class UserClientImpl extends BaseApiClient implements UserClient {
    private final FileHelper fileHelper;


    public UserClientImpl(Context context, FileHelper fileHelper) {
        super(context);
        this.fileHelper = fileHelper;
    }


    @Override
    public <T> void getMarvelCharacters(BaseRequest.ResponseListener<T> listener, int offset) {
        try {

            makeRequest(new BaseRequest.Builder()
                            .setType(BaseRequest.RequestType.JSON_OBJECT)
                            .setMethod(Request.Method.GET)
                            .setJsonRequest(new JSONObject(new Gson().toJson(new HashMap<String, Object>())))
                            .setUrl(String.format("https://gateway.marvel.com:443/v1/public/characters?offset=%s%s%s%s%s", offset,
                                    "&ts=1&apikey=", PUB_KEY, "&hash=", Utils.md5("1" + PRIV_KEY + PUB_KEY)))
                            .setListener(listener).build(), new TypeToken<ResponseBody<CharactersResponse>>() {
                    }
                            .getType()
            );
        } catch (Exception ignored) {
        }
    }

    @Override
    public <T> void getComics(BaseRequest.ResponseListener<T> listener, int id) {
        try {

            makeRequest(new BaseRequest.Builder()
                            .setType(BaseRequest.RequestType.JSON_OBJECT)
                            .setMethod(Request.Method.GET)
                            .setJsonRequest(new JSONObject(new Gson().toJson(new HashMap<String, Object>())))
                            .setUrl(String.format("https://gateway.marvel.com:443/v1/public/characters/%s%s%s%s%s", id,
                                    "/comics?limit=3&ts=1&apikey=", PUB_KEY, "&hash=", Utils.md5("1" + PRIV_KEY + PUB_KEY)))
                            .setListener(listener).build(), new TypeToken<ResponseBody<ComicsResponse>>() {
                    }
                            .getType()
            );
        } catch (Exception ignored) {
        }
    }

    @Override
    public <T> void getEvents(BaseRequest.ResponseListener<T> listener, int id) {
        try {

            makeRequest(new BaseRequest.Builder()
                            .setType(BaseRequest.RequestType.JSON_OBJECT)
                            .setMethod(Request.Method.GET)
                            .setJsonRequest(new JSONObject(new Gson().toJson(new HashMap<String, Object>())))
                            .setUrl(String.format("https://gateway.marvel.com:443/v1/public/characters/%s%s%s%s%s", id,
                                    "/events?limit=3&ts=1&apikey=", PUB_KEY, "&hash=", Utils.md5("1" + PRIV_KEY + PUB_KEY)))
                            .setListener(listener).build(), new TypeToken<ResponseBody<EventsResponse>>() {
                    }
                            .getType()
            );
        } catch (Exception ignored) {
        }
    }

    @Override
    public <T> void getSeries(BaseRequest.ResponseListener<T> listener, int id) {
        try {

            makeRequest(new BaseRequest.Builder()
                            .setType(BaseRequest.RequestType.JSON_OBJECT)
                            .setMethod(Request.Method.GET)
                            .setJsonRequest(new JSONObject(new Gson().toJson(new HashMap<String, Object>())))
                            .setUrl(String.format("https://gateway.marvel.com:443/v1/public/characters/%s%s%s%s%s", id,
                                    "/series?limit=3&ts=1&apikey=", PUB_KEY, "&hash=", Utils.md5("1" + PRIV_KEY + PUB_KEY)))
                            .setListener(listener).build(), new TypeToken<ResponseBody<SeriesResponse>>() {
                    }
                            .getType()
            );
        } catch (Exception ignored) {
        }
    }

    @Override
    public <T> void getStories(BaseRequest.ResponseListener<T> listener, int id) {
        try {

            makeRequest(new BaseRequest.Builder()
                            .setType(BaseRequest.RequestType.JSON_OBJECT)
                            .setMethod(Request.Method.GET)
                            .setJsonRequest(new JSONObject(new Gson().toJson(new HashMap<String, Object>())))
                            .setUrl(String.format("https://gateway.marvel.com:443/v1/public/characters/%s%s%s%s%s", id,
                                    "/stories?limit=3&ts=1&apikey=", PUB_KEY, "&hash=", Utils.md5("1" + PRIV_KEY + PUB_KEY)))
                            .setListener(listener).build(), new TypeToken<ResponseBody<StoriesResponse>>() {
                    }
                            .getType()
            );
        } catch (Exception ignored) {
        }
    }

}