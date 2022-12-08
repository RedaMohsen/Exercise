package com.toters.exercise.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.toters.exercise.helper.LocaleHelper;
import com.toters.exercise.provider.AppProvider;


public class exercise extends Application {
    AppProvider appProvider;
    private static Context mContext;

    private final ActivityLifecycleCallbacks lifecycleCallbacks = new ActivityLifecycleCallbacks();
    private static exercise instance;

    public exercise() {
        instance = this;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        appProvider = new AppProvider(this);
        mContext = this;
        registerActivityLifecycleCallbacks(lifecycleCallbacks);

    }

    public static Context getContext(){
        return mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        LocaleHelper.onAttach(base);
    }

    public static exercise getInstance() {
        return instance;
    }

    public Activity getCurrentActivity() {
        return instance.lifecycleCallbacks.getCurrentActivity();
    }
    static class ActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

        private Activity currentActivity;

        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
            currentActivity = activity;
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {
            currentActivity = activity;


        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            currentActivity = activity;


        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {
            currentActivity = activity;

        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {

        }

        public Activity getCurrentActivity() {
            return currentActivity;
        }
    }
}
