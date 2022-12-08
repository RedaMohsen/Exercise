package com.toters.exercise.network.clients;


import static com.toters.exercise.BuildConfig.PRIV_KEY;
import static com.toters.exercise.BuildConfig.PUB_KEY;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.PagingRx;
import androidx.paging.rxjava3.RxPagingSource;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.toters.exercise.helper.FileHelper;
import com.toters.exercise.network.clients.volley.BaseRequest;
import com.toters.exercise.network.model.responseBody.CharacterModel;
import com.toters.exercise.network.model.responseBody.CharactersResponse;
import com.toters.exercise.network.model.responseBody.PaginationResponseBody;
import com.toters.exercise.network.model.responseBody.ResponseBody;
import com.toters.exercise.utils.Utils;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.OptIn;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ExperimentalCoroutinesApi;

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
                                    "&ts=1&apikey=",PUB_KEY,"&hash=", Utils.md5("1"+PRIV_KEY+PUB_KEY)))
                            .setListener(listener).build(), new TypeToken<ResponseBody<CharactersResponse>>() {
                    }
                            .getType()
            );
        } catch (Exception ignored) {
        }
    }

}