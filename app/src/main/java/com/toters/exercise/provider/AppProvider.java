package com.toters.exercise.provider;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bluelinelabs.conductor.Controller;
import com.toters.exercise.helper.FileHelper;
import com.toters.exercise.helper.IntentHelper;
import com.toters.exercise.helper.JsonParserHelper;
import com.toters.exercise.helper.PermissionsHelper;
import com.toters.exercise.helper.PrefHelper;
import com.toters.exercise.helper.ResourceHelper;
import com.toters.exercise.network.clients.UserClient;
import com.toters.exercise.network.clients.UserClientImpl;
import com.toters.exercise.network.clients.volley.VolleyApiClient;
import com.toters.exercise.utils.ErrorHandler;


public class AppProvider {
    private Context context;

    public AppProvider(Context context) {
        this.context = context;
    }

    public VolleyApiClient getApiClient() {
        return VolleyApiClient.getInstance(context);
    }


    public ErrorHandler getErrorHandler() {
        return new ErrorHandler(context);
    }


    public PrefHelper getPrefHelper() {
        return new PrefHelper(context);
    }

    public ResourceHelper getResourceHelper() {
        return new ResourceHelper(context);
    }

    public IntentHelper getIntentHelper(Controller controller) {
        return new IntentHelper(controller, getResourceHelper());
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public PermissionsHelper getPermissionsHelper(Controller controller) {
        return new PermissionsHelper(controller);
    }



    public JsonParserHelper getJsonParserHelper() {
        return new JsonParserHelper(getErrorHandler());
    }

    public FileHelper getFileHelper() {
        return new FileHelper(context);
    }

    public UserClient getUserClient() {
        return new UserClientImpl(context, getFileHelper());
    }

}
