package com.toters.exercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.toters.exercise.features.splash.SplashController;
import com.toters.exercise.helper.LocaleHelper;
import com.toters.exercise.helper.PrefHelper;

public class MainActivity extends AppCompatActivity {
    private Router router;
    private final Bundle bundle = new Bundle();
    private PrefHelper prefHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefHelper = new PrefHelper(this);

        LocaleHelper.setLocale(this, prefHelper.getCurrentLanguage());
        setContentView(R.layout.activity_main);
        router = Conductor.attachRouter(this, findViewById(R.id.controllerContainer), savedInstanceState).setPopRootControllerMode(Router.PopRootControllerMode.NEVER);
        proceed();
    }


    private void proceed() {
        router.setRoot(RouterTransaction.with(new SplashController(bundle)));
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        router.onActivityResult(requestCode, resultCode, data);
    }
}