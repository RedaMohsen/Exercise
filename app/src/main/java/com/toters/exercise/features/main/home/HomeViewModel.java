package com.toters.exercise.features.main.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.PagingRx;
import androidx.paging.rxjava3.RxPagingSource;

import com.android.volley.VolleyError;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.toters.exercise.base.ViewModel;
import com.toters.exercise.network.clients.UserClient;
import com.toters.exercise.network.clients.volley.BaseRequest;
import com.toters.exercise.network.model.responseBody.CharacterModel;
import com.toters.exercise.network.model.responseBody.CharactersResponse;
import com.toters.exercise.network.model.responseBody.PaginationResponseBody;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.OptIn;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ExperimentalCoroutinesApi;

public class HomeViewModel extends ViewModel {
    UserClient userClient;
    private final BehaviorRelay<CharactersResponse> charactersRelay = BehaviorRelay.create();

    public HomeViewModel(UserClient userClient) {
        this.userClient = userClient;
    }


    public void getCharacters(int offset) {
        userClient.getMarvelCharacters(new BaseRequest.ResponseListener<CharactersResponse>() {
            @Override
            public void onSuccess(CharactersResponse response, int statusCode, String message) {
                charactersRelay.accept(response);
            }

            @Override
            public void onError(VolleyError error) {

            }
        }, offset);
    }


    Observable<CharactersResponse> getCharactersRelay() {
        return charactersRelay.observeOn(AndroidSchedulers.mainThread());
    }
}
