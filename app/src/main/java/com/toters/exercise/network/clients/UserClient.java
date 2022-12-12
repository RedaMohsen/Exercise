package com.toters.exercise.network.clients;


import com.toters.exercise.network.clients.volley.BaseRequest;

public interface UserClient {

    <T> void getMarvelCharacters(BaseRequest.ResponseListener<T> listener, int offset);
    <T> void getComics(BaseRequest.ResponseListener<T> listener, int id);
    <T> void getEvents(BaseRequest.ResponseListener<T> listener, int id);
    <T> void getSeries(BaseRequest.ResponseListener<T> listener, int id);
    <T> void getStories(BaseRequest.ResponseListener<T> listener, int id);
}
