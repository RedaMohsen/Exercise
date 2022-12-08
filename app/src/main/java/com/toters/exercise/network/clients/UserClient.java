package com.toters.exercise.network.clients;


import android.location.Location;


import com.toters.exercise.network.clients.volley.BaseRequest;
import com.toters.exercise.network.model.responseBody.CharacterModel;
import com.toters.exercise.network.model.responseBody.PaginationResponseBody;

import org.json.JSONArray;

import io.reactivex.rxjava3.core.Single;

public interface UserClient {

    <T> void getMarvelCharacters(BaseRequest.ResponseListener<T> listener, int offset);
}
