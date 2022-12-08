package com.toters.exercise.features.splash;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.toters.exercise.R;
import com.toters.exercise.base.BaseRestoreController;
import com.toters.exercise.features.main.home.HomeController;
import com.toters.exercise.provider.AppProvider;

public class SplashController extends BaseRestoreController<SplashViewModel> {

    public SplashController(Bundle args) {
        super(args);
    }

    @Override
    protected SplashViewModel onCreateViewModel(AppProvider appProvider) {
        return new SplashViewModel();
    }

    @Override
    protected void onBindView(Activity activity, AppProvider appProvider, SplashViewModel viewModel, Bundle savedViewState) {
        new Handler().postDelayed(this::loadNextController, 1000);
    }

    @Override
    protected void cleanView() {

    }

    @Override
    protected int layoutRes() {
        return R.layout.controller_splash;
    }

    @Override
    public void onViewCreated(View view, Bundle savedViewState) {

    }

    @Override
    public void onToolbarBind(View view) {

    }


    private void loadNextController() {
        if (getActivity() != null) {
            getRouter().setRoot(RouterTransaction.with(new HomeController()).popChangeHandler(new HorizontalChangeHandler()).pushChangeHandler(new HorizontalChangeHandler()));

        }
    }
}
