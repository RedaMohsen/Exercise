package com.toters.exercise.base;

import com.android.volley.VolleyError;
import com.jakewharton.rxrelay2.PublishRelay;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class ViewModel extends androidx.lifecycle.ViewModel {

    protected CompositeDisposable disposables = new CompositeDisposable();
    private PublishRelay<Boolean> loadingRelay = PublishRelay.create();
    private PublishRelay<String> InfoRelay = PublishRelay.create();
    protected PublishRelay<VolleyError> errorRelay = PublishRelay.create();
    protected PublishRelay<Boolean> logoutRelay = PublishRelay.create();

    public Observable<Boolean> getLoadingObs = loadingRelay;
    public Observable<String> getInfoObs = InfoRelay;

    public Observable<VolleyError> getErrorObservable() {
        return errorRelay.observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> getLogoutRelay() {
        return logoutRelay.observeOn(AndroidSchedulers.mainThread());
    }

    public void onCleared() {
        disposables.clear();
    }

    public void showLoading(Boolean show) {
        loadingRelay.accept(show);
    }

    public void showInfoDialog(String message){
        InfoRelay.accept(message);
    }
}
