package com.toters.exercise.features.main.details;

import com.android.volley.VolleyError;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.toters.exercise.base.ViewModel;
import com.toters.exercise.network.clients.UserClient;
import com.toters.exercise.network.clients.volley.BaseRequest;
import com.toters.exercise.network.model.SeriesModel;
import com.toters.exercise.network.model.responseBody.ComicsResponse;
import com.toters.exercise.network.model.responseBody.EventsResponse;
import com.toters.exercise.network.model.responseBody.SeriesResponse;
import com.toters.exercise.network.model.responseBody.StoriesResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class DetailsViewModel extends ViewModel {
    UserClient userClient;

    private final BehaviorRelay<ComicsResponse> comicsRelay = BehaviorRelay.create();
    private final BehaviorRelay<EventsResponse> eventsRelay = BehaviorRelay.create();
    private final BehaviorRelay<SeriesResponse> seriesRelay = BehaviorRelay.create();
    private final BehaviorRelay<StoriesResponse> storiesRelay = BehaviorRelay.create();

    public DetailsViewModel(UserClient userClient) {
        this.userClient = userClient;
    }


    public void getComics(int characterId) {
        userClient.getComics(new BaseRequest.ResponseListener<ComicsResponse>() {
            @Override
            public void onSuccess(ComicsResponse response, int statusCode, String message) {
                comicsRelay.accept(response);
            }

            @Override
            public void onError(VolleyError error) {

            }
        }, characterId);
    }


    public void getEvents(int characterId) {
        userClient.getEvents(new BaseRequest.ResponseListener<EventsResponse>() {
            @Override
            public void onSuccess(EventsResponse response, int statusCode, String message) {
                eventsRelay.accept(response);
            }

            @Override
            public void onError(VolleyError error) {

            }
        }, characterId);
    }


    public void getSeries(int characterId) {
        userClient.getSeries(new BaseRequest.ResponseListener<SeriesResponse>() {
            @Override
            public void onSuccess(SeriesResponse response, int statusCode, String message) {
                seriesRelay.accept(response);
            }

            @Override
            public void onError(VolleyError error) {

            }
        }, characterId);
    }

    public void getStories(int characterId) {
        userClient.getStories(new BaseRequest.ResponseListener<StoriesResponse>() {
            @Override
            public void onSuccess(StoriesResponse response, int statusCode, String message) {
                storiesRelay.accept(response);
            }

            @Override
            public void onError(VolleyError error) {

            }
        }, characterId);
    }

    Observable<ComicsResponse> getComicsRelay() {
        return comicsRelay.observeOn(AndroidSchedulers.mainThread());
    }

    Observable<EventsResponse> getEventsRelay() {
        return eventsRelay.observeOn(AndroidSchedulers.mainThread());
    }


    Observable<SeriesResponse> getSeriesRelay() {
        return seriesRelay.observeOn(AndroidSchedulers.mainThread());
    }


    Observable<StoriesResponse> getStoriesRelay() {
        return storiesRelay.observeOn(AndroidSchedulers.mainThread());
    }
}
